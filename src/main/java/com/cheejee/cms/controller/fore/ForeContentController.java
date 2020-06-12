package com.cheejee.cms.controller.fore;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheejee.cms.common.CurrentAppInfo;
import com.cheejee.cms.dto.ContentGetDto;
import com.cheejee.cms.dto.fore.ForeContentGeneralDto;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Category;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.BasicResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.response.SimpleResponseEntity;
import com.cheejee.cms.service.CategoryService;
import com.cheejee.cms.service.ContentService;
import com.cheejee.cms.tools.WashTool;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author CARRY ME HOME 2020年4月30日下午5:57:22
 */
@RestController
@RequestMapping("/content")
@Api(tags = "内容")
@Validated
public class ForeContentController {

	public static final int CONTENT_RELEASE_STATE = 2;

	@Resource
	private ContentService contentService;

	@Resource
	private CategoryService categoryService;

	@Resource
	private CurrentAppInfo appInfo;

	@GetMapping("/")
	@ApiOperation(value = "获取所有发布的内容", notes = "审核通过的内容视为发布，内容状态值为2")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<ContentGetDto> getReleaseContent(int pageNum, int pageSize) {
		PageInfo<Content> info = contentService.getContentByStateManage(pageNum, pageSize, CONTENT_RELEASE_STATE);

		return buildPageResponse(info);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "获取内容", notes = "获取特定id的内容")
	@ApiImplicitParam(name = "id", value = "内容id", required = true)
	public ApiResponseEntity<ContentGetDto> getContent(@PathVariable int id) {
		Content c = contentService.getContentByIdManage(id);
		String message = c == null ? "没有获取到相关信息" : "获取完成";

		return ApiResponseEntity.<ContentGetDto>builder()
				.code(200)
				.message(message)
				.data(ContentGetDto.parse(c))
				.build();
	}

	@GetMapping("/own")
	@ApiOperation(value = "获取自己的内容", notes = "获取当前用户所创建的所有内容")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<ContentGetDto> getOwnContent(int pageNum, int pageSize) throws IncompleteException {
		PageInfo<Content> info = contentService.getContentByUser(pageNum, pageSize, appInfo.getCurrentUser());

		return buildPageResponse(info);
	}

	@GetMapping("/state")
	@ApiOperation(value = "获取特定状态的内容", notes = "只会获取当前用户所创建的内容")
	@ApiImplicitParams({ @ApiImplicitParam(name = "state", value = "状态值", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<ContentGetDto> getOwnContentByState(int state, int pageNum, int pageSize)
			throws IncompleteException {
		PageInfo<Content> info = contentService.getContentByState(pageNum, pageSize, appInfo.getCurrentUser(), state);

		return buildPageResponse(info);
	}

	@GetMapping("/search")
	@ApiOperation(value = "搜索内容", notes = "搜索标题与关键字匹配的内容，只能搜索出已发布的内容")
	@ApiImplicitParams({ @ApiImplicitParam(name = "key", value = "关键字", paramType = "query", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<ContentGetDto> searchContent(@NotBlank String key, int pageNum, int pageSize)
			throws IncompleteException {
		PageInfo<Content> info = contentService.searchContent(pageNum, pageSize, key, CONTENT_RELEASE_STATE);

		return buildPageResponse(info);
	}

	@GetMapping("/no-category")
	@ApiOperation(value = "获取游离内容", notes = "没有分类的内容视为游离内容，只会获取当前用户的内容")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<ContentGetDto> getContentNoCategory(int pageNum, int pageSize) throws IncompleteException {
		PageInfo<Content> info = contentService.getContentNoCategory(pageNum, pageSize, appInfo.getCurrentUser());

		return buildPageResponse(info);
	}

	@GetMapping("/by-category")
	@ApiOperation(value = "按分类获取内容", notes = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "分类id", paramType = "query", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public BasicResponseEntity getContentByCategory(int id, int pageNum, int pageSize) throws IncompleteException {

		Category category = categoryService.getCategoryByIdManage(id);
		if (category == null) {
			return SimpleResponseEntity.builder()
					.code(400)
					.message("分类不存在")
					.build();
		}

		PageInfo<Content> info = contentService.getContentByCategory(pageNum, pageSize, category,
				appInfo.getCurrentUser());

		return buildPageResponse(info);
	}

	@GetMapping("/move")
	@ApiOperation(value = "移动内容", notes = "移动指定内容到指定分组")
	@ApiImplicitParams({ @ApiImplicitParam(name = "contentId", value = "内容id", paramType = "query", required = true),
			@ApiImplicitParam(name = "categoryId", value = "分类id", paramType = "query", required = true) })
	public SimpleResponseEntity moveContentToCategory(int contentId, int categoryId)
			throws InsufficientPermissionException, NotFoundException {

		Content t = contentService.getContentByIdManage(contentId);
		Category y = categoryService.getCategoryByIdManage(categoryId);

		if (t == null || y == null) {
			return SimpleResponseEntity.builder()
					.code(400)
					.message("分类或者内容不存在")
					.build();
		}

		contentService.moveContentToCategory(t, y);

		return SimpleResponseEntity.builder()
				.code(200)
				.message("移动成功")
				.build();
	}

	@PostMapping("/add")
	@ApiOperation("添加内容")
	public ApiResponseEntity<ContentGetDto> addContent(@Valid @RequestBody ForeContentGeneralDto content)
			throws NullPointerException, IncompleteException, OperationsException, DataDuplicationException,
			NotFoundException, InsufficientPermissionException {
		
		Content c = content.toPostContent(appInfo.getCurrentUser());
		WashTool.washContent(c);
		contentService.addContent(appInfo.getCurrentUser(), c);

		return ApiResponseEntity.<ContentGetDto>builder()
				.code(200)
				.message("添加完成")
				.data(ContentGetDto.parse(contentService.getContentByIdManage(c.getId())))
				.build();
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "修改内容", notes = "内容的标题和文字不能为空，内容标签总数不能超过十个。只能修改当前用户所创建的内容，并且无法修改内容状态值")
	@ApiImplicitParam(name = "id", value = "内容id", required = true)
	public ApiResponseEntity<ContentGetDto> changeContent(@PathVariable int id, @Valid @RequestBody ForeContentGeneralDto content)
			throws NullPointerException, NotFoundException, OperationsException, InsufficientPermissionException,
			IncompleteException, DataException, DataDuplicationException {
		
		Content c = content.toPutContent(id);
		WashTool.washContent(c);
		contentService.changeContent(c, appInfo.getCurrentUser());

		return ApiResponseEntity.<ContentGetDto>builder()
				.code(200)
				.message("修改完成")
				.data(ContentGetDto.parse(contentService.getContentByIdManage(id)))
				.build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除内容", notes = "只能删除当前用户创建的内容")
	@ApiImplicitParam(name = "id", value = "内容id", required = true)
	public SimpleResponseEntity deleteContent(@PathVariable int id) throws NullPointerException, NotFoundException,
			InsufficientPermissionException, IncompleteException, DataException {
		contentService.deleteContent(appInfo.getCurrentUser(), contentService.getContentByIdManage(id));

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
