package com.cheejee.cms.controller.back;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheejee.cms.dto.AttachGetDto;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.response.SimpleResponseEntity;
import com.cheejee.cms.service.AttachService;
import com.cheejee.cms.tools.BuliderTool;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author CARRY ME HOME 2020年4月16日下午11:50:52
 */
@RestController
@RequestMapping("/admin/attach")
@Api(tags = "附件-后台")
@Validated
public class BackAttachController {

	@Resource
	private AttachService attachService;

	@GetMapping("/")
	@RequiresPermissions("attach:get")
	@ApiOperation("获取所有附件")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<AttachGetDto> getAllAttach(int pageNum, int pageSize) {
		PageInfo<Attach> info = attachService.getAttachAllByManage(pageNum, pageSize);

		return PageResponseEntity.<AttachGetDto>builder()
				.code(200)
				.message("获取完成")
				.list(AttachGetDto.parse(info.getList()))
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.build();
	}

	@GetMapping("/{id}")
	@RequiresPermissions("attach:get")
	@ApiOperation("获取某个附件")
	@ApiImplicitParam(name = "id", value = "附件id", required = true)
	public ApiResponseEntity<AttachGetDto> getAttach(@PathVariable int id) {

		Attach a = attachService.getAttachByIdByManage(id);
		String message = a == null ? "附件不存在" : "获取成功";

		return ApiResponseEntity.<AttachGetDto>builder()
				.code(200)
				.message(message)
				.data(AttachGetDto.parse(a))
				.build();
	}

	@GetMapping("/search")
	@RequiresPermissions("attach:get")
	@ApiOperation(value = "搜索附件", notes = "搜索出名称和关键字 匹配的附件")
	@ApiImplicitParams({@ApiImplicitParam(name = "key", value = "关键字", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<AttachGetDto> searchAttach(@NotBlank String key, int pageNum, int pageSize)
			throws IncompleteException {
		PageInfo<Attach> info = attachService.getAttachByNameManage(pageNum, pageSize, key);

		return PageResponseEntity.<AttachGetDto>builder()
				.code(200)
				.message("查找完成")
				.list(AttachGetDto.parse(info.getList()))
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.build();
	}

	@GetMapping("/type-specific")
	@RequiresPermissions("attach:get")
	@ApiOperation(value = "按类型获取附件", notes = "获取指定类型的附件")
	@ApiImplicitParams({ @ApiImplicitParam(name = "typeId", value = "类型id", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<AttachGetDto> getAttachByType(int typeId, int pageNum, int pageSize)
			throws IncompleteException {
		PageInfo<Attach> info = attachService.getAttachByTypeManage(pageNum, pageSize, BuliderTool.buildType(typeId));

		return PageResponseEntity.<AttachGetDto>builder()
				.code(200)
				.message("查找完成")
				.list(AttachGetDto.parse(info.getList()))
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.build();
	}

	@DeleteMapping("/{id}")
	@RequiresPermissions("attach:delete")
	@ApiOperation(value = "删除附件", notes = "删除指定id的附件")
	@ApiImplicitParam(name = "id", value = "附件id", required = true)
	public SimpleResponseEntity deleteAttach(@PathVariable int id) throws IncompleteException, OperationsException {
		Attach attach = new Attach();
		attach.setId(id);
		attachService.deleteAttachsByManageForce(attach);

		return SimpleResponseEntity.builder()
				.code(200)
				.message("删除完成")
				.build();
	}
}
