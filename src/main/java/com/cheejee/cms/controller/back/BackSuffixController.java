package com.cheejee.cms.controller.back;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

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

import com.cheejee.cms.dto.back.BackSuffixGetDto;
import com.cheejee.cms.dto.back.BackSuffixPostDto;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Suffix;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.response.SimpleResponseEntity;
import com.cheejee.cms.service.SuffixService;
import com.cheejee.cms.tools.BuliderTool;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author CARRY ME HOME 2020年5月1日下午11:19:33
 */
@RestController
@RequestMapping("/admin/suffix")
@Api(tags = "附件后缀-后台")
@Validated
public class BackSuffixController {

	@Resource
	private SuffixService suffixService;

	@GetMapping("/")
	@RequiresPermissions("suffix:get")
	@ApiOperation("获取所有的后缀")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<BackSuffixGetDto> getAll(int pageNum, int pageSize) {
		PageInfo<Suffix> info = suffixService.getSuffixAll(pageNum, pageSize);

		return buildResponse(info);
	}

	@GetMapping("/{id}")
	@RequiresPermissions("suffix:get")
	@ApiOperation("获取某个后缀")
	@ApiImplicitParam(name = "id", value = "后缀id", required = true)
	public ApiResponseEntity<BackSuffixGetDto> getSuffix(@PathVariable int id) {
		BackSuffixGetDto s = BackSuffixGetDto.parse(suffixService.getSuffixById(id));
		String message = s == null ? "没有获取到相关信息" : "获取完成";

		return ApiResponseEntity.<BackSuffixGetDto>builder()
				.code(200)
				.message(message)
				.data(s)
				.build();
	}

	@GetMapping("/no-type")
	@RequiresPermissions("suffix:get")
	@ApiOperation(value = "获取没有分类的后缀", notes = "获取不属于任何类型的后缀")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<BackSuffixGetDto> getNoType(int pageNum, int pageSize) {
		PageInfo<Suffix> info = suffixService.getSuffixNoType(pageNum, pageSize);

		return buildResponse(info);
	}

	@PostMapping("/add")
	@RequiresPermissions("suffix:add")
	@ApiOperation("添加后缀")
	public ApiResponseEntity<BackSuffixGetDto> addSuffix(@RequestBody BackSuffixPostDto suffix)
			throws DataDuplicationException, IncompleteException {
		
		Suffix s = suffix.toSuffix();
		suffixService.addSuffix(s);

		return ApiResponseEntity.<BackSuffixGetDto>builder()
				.code(200)
				.message("添加完成")
				.data(BackSuffixGetDto.parse(s))
				.build();
	}

	@PutMapping("/{id}/describe")
	@RequiresPermissions("suffix:edit")
	@ApiOperation("修改后缀描述")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "后缀id", required = true, dataType = "int"),
		@ApiImplicitParam(name = "describe", value = "后缀描述", required = true, paramType = "query", dataType = "string")
	})
	public ApiResponseEntity<BackSuffixGetDto> changeSuffixDescribe(@PathVariable int id, @NotNull String describe)
			throws IncompleteException, NotFoundException {
		suffixService.changeSuffixDescribe(BuliderTool.buildSuffix(id, describe));

		return ApiResponseEntity.<BackSuffixGetDto>builder()
				.code(200)
				.message("修改完成")
				.data(BackSuffixGetDto.parse(suffixService.getSuffixById(id)))
				.build();
	}

	@DeleteMapping("/{id}")
	@RequiresPermissions("suffix:delete")
	@ApiOperation(value = "删除后缀", notes = "只能删除没有在使用的后缀")
	public SimpleResponseEntity deleteSuffix(@PathVariable int id) throws OperationsException, IncompleteException {
		suffixService.deleteSuffix(BuliderTool.buildSuffix(id, null));

		return SimpleResponseEntity.builder()
				.code(200)
				.message("删除完成")
				.build();
	}

	private PageResponseEntity<BackSuffixGetDto> buildResponse(PageInfo<Suffix> info) {
		return PageResponseEntity.<BackSuffixGetDto>builder()
				.code(200)
				.message("获取完成")
				.list(BackSuffixGetDto.parse(info.getList()))
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.build();
	}

}
