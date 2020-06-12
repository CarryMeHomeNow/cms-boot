package com.cheejee.cms.controller.back;

import javax.validation.constraints.NotBlank;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheejee.cms.dto.back.BackResourceGetDto;
import com.cheejee.cms.pojo.Resource;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.service.ResourceService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 资源只有获取方法，资源的添加操作应该在系统初始化时完成。
 * 
 * @author CARRY ME HOME 2020年4月27日下午10:18:05
 */
@RestController
@RequestMapping("/admin/resource")
@Api(tags = "资源-后台")
@Validated
public class BackResourceController {

	@javax.annotation.Resource
	private ResourceService resourceService;

	@GetMapping("/")
	@RequiresPermissions("resource:get")
	@ApiOperation("获取所有资源")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<BackResourceGetDto> getAllResource(int pageNum, int pageSize) {
		PageInfo<Resource> info = resourceService.getResourceAll(pageNum, pageSize);

		return buildResponse(info);
	}

	@GetMapping("/{id}")
	@RequiresPermissions("resource:get")
	@ApiOperation("获取某个资源")
	@ApiImplicitParam(name = "id", value = "资源id", required = true)
	public ApiResponseEntity<BackResourceGetDto> getResource(@PathVariable int id) {
		Resource r = resourceService.getResourceById(id);
		String message = r == null ? "没有获取到相关信息" : "获取完成";

		return ApiResponseEntity.<BackResourceGetDto>builder()
				.code(200)
				.message(message)
				.data(BackResourceGetDto.parse(r))
				.build();
	}

	@GetMapping("/search")
	@RequiresPermissions("resource:get")
	@ApiOperation(value = "搜索资源", notes = "搜索出名称与资源关键字匹配的资源")
	@ApiImplicitParams({ @ApiImplicitParam(name = "key", value = "用于搜索的关键字", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<BackResourceGetDto> searchResource(@NotBlank String key, int pageNum, int pageSize) {
		PageInfo<Resource> info = resourceService.getResourceByDesName(pageNum, pageSize, key);

		return buildResponse(info);
	}

	private PageResponseEntity<BackResourceGetDto> buildResponse(PageInfo<Resource> info) {
		return PageResponseEntity.<BackResourceGetDto>builder()
				.code(200)
				.message("获取完成")
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.list(BackResourceGetDto.parse(info.getList()))
				.build();
	}
}
