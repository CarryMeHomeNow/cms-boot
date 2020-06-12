
package com.cheejee.cms.controller.back;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheejee.cms.dto.back.BackTypeGetDto;
import com.cheejee.cms.dto.back.BackTypePostDto;
import com.cheejee.cms.dto.back.BackTypePutDto;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.pojo.Type;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.response.SimpleResponseEntity;
import com.cheejee.cms.service.TypeService;
import com.cheejee.cms.tools.BuliderTool;
import com.cheejee.cms.tools.WashTool;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author CARRY ME HOME 2020年5月2日下午4:08:25
 */
@RestController
@RequestMapping("/admin/type")
@Api(tags = "附件类型-后台")
@Validated
public class BackTypeController {

	@Resource
	private TypeService typeService;

	@GetMapping("/")
	@RequiresPermissions("type:get")
	@ApiOperation("获取所有类型")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
		@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<BackTypeGetDto> getAll(int pageNum, int pageSize) {
		PageInfo<Type> info = typeService.getAllType(pageNum, pageSize);

		return buildResponse(info);
	}

	@GetMapping("/{id}")
	@RequiresPermissions("type:get")
	@ApiOperation("获取某个类型")
	@ApiImplicitParam(name = "id", value = "类型id", required = true)
	public ApiResponseEntity<BackTypeGetDto> getType(@PathVariable int id) {
		Type t = typeService.getTypeEditById(id);
		String message = t == null ? "没有获取到相关信息" : "获取完成";

		return ApiResponseEntity.<BackTypeGetDto>builder()
				.code(200)
				.message(message)
				.data(BackTypeGetDto.parse(t))
				.build();
	}

	@GetMapping("/search")
	@RequiresPermissions("type:get")
	@ApiOperation(value = "搜索类型", notes = "搜索名称与关键词匹配的类型")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "name", value = "搜索关键字", required = true, paramType = "query", dataType = "string", example = "图片"),
		@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
		@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<BackTypeGetDto> searchType(@NotBlank String name, int pageNum, int pageSize) {
		PageInfo<Type> info = typeService.getTypeByName(pageNum, pageSize, name);

		return buildResponse(info);
	}

	@PostMapping("/add")
	@RequiresPermissions("type:add")
	@ApiOperation(value = "添加类型", notes = "")
	public ApiResponseEntity<BackTypeGetDto> addType(@RequestBody BackTypePostDto type)
			throws IncompleteException, DataDuplicationException {
		
		Type t = type.toType();
		typeService.addType(t);

		return ApiResponseEntity.<BackTypeGetDto>builder()
				.code(200)
				.message("添加完成")
				.data(BackTypeGetDto.parse(t))
				.build();
	}

	@PutMapping("/{id}")
	@RequiresPermissions("type:edit")
	@ApiOperation(value = "修改类型", notes = "类型名称不能为空，可以修改类型包含的后缀。后缀如果不存在则会事务添加后缀。")
	public ApiResponseEntity<BackTypeGetDto> changeType(@PathVariable int id, @RequestBody BackTypePutDto type)
			throws IncompleteException, DataDuplicationException, NotFoundException {
		
		Type t = type.toType(id);
		WashTool.washType(t);
		typeService.changeType(t);

		return ApiResponseEntity.<BackTypeGetDto>builder()
				.code(200)
				.message("修改完成")
				.data(BackTypeGetDto.parse(typeService.getTypeById(id)))
				.build();
	}

	@DeleteMapping("/{id}")
	@RequiresPermissions("type:delete")
	@ApiOperation("删除类型")
	@ApiImplicitParam(name = "id", value = "类型id", required = true)
	public SimpleResponseEntity deleteType(@PathVariable int id) throws IncompleteException {
		typeService.deleteType(BuliderTool.buildType(id));

		return SimpleResponseEntity.builder()
				.code(200)
				.message("删除完成")
				.build();
	}

	private PageResponseEntity<BackTypeGetDto> buildResponse(PageInfo<Type> info) {

		return PageResponseEntity.<BackTypeGetDto>builder()
				.code(200)
				.message("获取完成")
				.list(BackTypeGetDto.parse(info.getList()))
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.build();
	}
}
