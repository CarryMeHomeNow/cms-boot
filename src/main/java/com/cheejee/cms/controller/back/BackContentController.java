package com.cheejee.cms.controller.back;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheejee.cms.dto.ContentGetDto;
import com.cheejee.cms.dto.back.BackContentPutDto;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.response.SimpleResponseEntity;
import com.cheejee.cms.service.ContentService;
import com.cheejee.cms.tools.BuliderTool;
import com.cheejee.cms.tools.WashTool;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 通过修改内容的状态值实现内容的审核。状态值=2为审核通过。
 * 
 * @author CARRY ME HOME 2020年5月1日下午10:08:12
 */
@RestController
@RequestMapping("/admin/content")
@Api(tags = "内容-后台")
@Validated
public class BackContentController {

	@Resource
	private ContentService contentService;

	@GetMapping("/")
	@RequiresPermissions("content:get")
	@ApiOperation("获取所有内容")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<ContentGetDto> getAllContent(int pageNum, int pageSize) {
		PageInfo<Content> info = contentService.getContentAll(pageNum, pageSize);

		return buildPageResponse(info);
	}

	@GetMapping("/state")
	@RequiresPermissions("content:get")
	@ApiOperation(value = "获取特定状态的内容", notes = "1是未审核内容，2是审核通过的内容。")
	@ApiImplicitParams({ @ApiImplicitParam(name = "state", value = "状态值", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<ContentGetDto> getOwnContentByState(int state, int pageNum, int pageSize)
			throws IncompleteException {
		PageInfo<Content> info = contentService.getContentByStateManage(pageNum, pageSize, state);

		return buildPageResponse(info);
	}

	@GetMapping("/search")
	@RequiresPermissions("content:get")
	@ApiOperation(value = "搜索内容", notes = "搜索标题与关键字匹配的内容")
	@ApiImplicitParams({ @ApiImplicitParam(name = "key", value = "关键字", paramType = "query", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<ContentGetDto> searchContent(@NotBlank String key, int pageNum, int pageSize)
			throws IncompleteException {
		PageInfo<Content> info = contentService.getContentByTitleManage(pageNum, pageSize, key);

		return buildPageResponse(info);
	}

	@PutMapping("/{id}")
	@RequiresPermissions("content:edit")
	@ApiOperation(value = "修改内容", notes = "内容的标题和文字不能为空，内容标签总数不能超过十个。")
	@ApiImplicitParam(name = "id", value = "内容id", required = true)
	public ApiResponseEntity<ContentGetDto> changeContent(@PathVariable int id,
			@Valid @RequestBody BackContentPutDto content)
			throws NullPointerException, NotFoundException, OperationsException, InsufficientPermissionException,
			IncompleteException, DataException, DataDuplicationException {

		Content c = content.toContent(id);
		WashTool.washContent(c);
		contentService.changeContentByManage(c);

		return ApiResponseEntity.<ContentGetDto>builder()
				.code(200)
				.message("修改完成")
				.data(ContentGetDto.parse(contentService.getContentByIdManage(id)))
				.build();
	}

	@PutMapping("/review")
	@RequiresPermissions("content:edit")
	@ApiOperation(value = "审核内容", notes = "将内容的状态修改为发布(state=2)")
	@ApiImplicitParam(name = "id", value = "要审核的内容id", required = true, dataType = "int", allowMultiple = true)
	public SimpleResponseEntity reviewContent(@RequestBody @NotEmpty int[] id) throws OperationsException {
		
		contentService.reviewContent(BuliderTool.buildContent(id));

		return SimpleResponseEntity.builder()
				.code(200)
				.message("审核完成")
				.build();
	}

	@DeleteMapping("/{id}")
	@RequiresPermissions("content:delete")
	@ApiOperation(value = "删除内容", notes = "")
	@ApiImplicitParam(name = "id", value = "内容id", required = true)
	public SimpleResponseEntity deleteContent(@PathVariable int id) throws NullPointerException, NotFoundException,
			InsufficientPermissionException, IncompleteException, DataException {
		contentService.deleteContentByManage(BuliderTool.buildContent(id));

		return SimpleResponseEntity.builder()
				.code(200)
				.message("删除完成")
				.build();
	}

	private PageResponseEntity<ContentGetDto> buildPageResponse(PageInfo<Content> info) {
		return PageResponseEntity.<ContentGetDto>builder()
				.code(200)
				.message("获取完成")
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.list(ContentGetDto.parse(info.getList()))
				.build();
	}
}
