package com.cheejee.cms.controller.fore;

import static com.cheejee.cms.tools.BuliderTool.bulidCategory;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheejee.cms.common.CurrentAppInfo;
import com.cheejee.cms.dto.fore.ForeCategoryGetDto;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Category;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.response.SimpleResponseEntity;
import com.cheejee.cms.service.CategoryService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/category")
@Api(tags = "分类")
@Validated
public class ForeCategoryController {

	@Resource
	private CategoryService categoryService;
	@Resource
	private CurrentAppInfo appInfo;

	@GetMapping("/")
	@ApiOperation(value = "获取所有分类", notes = "获取当前用户的所有分类")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<ForeCategoryGetDto> getCategroyList(int pageNum, int pageSize) {

		PageInfo<Category> info = categoryService.getCategoryByUser(pageNum, pageSize, appInfo.getCurrentUser());

		return PageResponseEntity.<ForeCategoryGetDto>builder()
				.code(200)
				.message("获取完成")
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.list(ForeCategoryGetDto.parse(info.getList()))
				.build();
	}

	@GetMapping("/{id}")
	@ApiOperation("获取某个分类")
	@ApiImplicitParam(name = "id", value = "分类id", required = true)
	public ApiResponseEntity<ForeCategoryGetDto> getCategory(@PathVariable int id) throws IncompleteException {
		Category c = categoryService.getCategoryById(id, appInfo.getCurrentUser());
		String message = c == null ? "分类不存在或者分类不属于当前用户" : "获取完成";

		return ApiResponseEntity.<ForeCategoryGetDto>builder()
				.code(200)
				.message(message)
				.data(ForeCategoryGetDto.parse(c))
				.build();
	}

	@PostMapping("/add")
	@ApiOperation("添加分类")
	@ApiImplicitParam(name = "name", value = "分类名称", paramType = "query", required = true)
	public ApiResponseEntity<ForeCategoryGetDto> addCategry(@NotBlank(message = "分类名称不能为空") String name)
			throws DataDuplicationException, IncompleteException, InsufficientPermissionException {

		Category c = new Category(name, appInfo.getCurrentUser());
		categoryService.addCategory(appInfo.getCurrentUser(), c);

		return ApiResponseEntity.<ForeCategoryGetDto>builder()
				.code(200)
				.message("添加成功")
				.data(ForeCategoryGetDto.parse(c))
				.build();
	}

	@PutMapping("/{id}")
	@ApiOperation("修改分类名称")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "分类id", required = true),
			@ApiImplicitParam(name = "name", value = "分类名称", paramType = "query", required = true) })
	public ApiResponseEntity<ForeCategoryGetDto> changeCategoryName(@PathVariable int id,
			@NotBlank(message = "分类名称不能为空") String name) throws DataDuplicationException, NotFoundException,
			IncompleteException, DataException, InsufficientPermissionException {

		categoryService.changeCategory(appInfo.getCurrentUser(), bulidCategory(id, name));

		return ApiResponseEntity.<ForeCategoryGetDto>builder()
				.code(200)
				.message("修改完成")
				.data(ForeCategoryGetDto.parse(categoryService.getCategoryByIdManage(id)))
				.build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除分类（只能删除空分类）")
	@ApiImplicitParam(name = "id", value = "分类id", required = true)
	public SimpleResponseEntity deleteCategory(@PathVariable int id) throws OperationsException, IncompleteException,
			InsufficientPermissionException, DataException, NotFoundException {

		categoryService.deleteCategory(appInfo.getCurrentUser(), categoryService.getCategoryByIdManage(id));

		return SimpleResponseEntity.builder()
				.code(200)
				.message("删除完成")
				.build();
	}

	@DeleteMapping("/{id}/force")
	@ApiOperation(value = "强制删除分类")
	@ApiImplicitParam(name = "id", value = "分类id", required = true)
	public SimpleResponseEntity deleteCategoryForce(@PathVariable int id) throws OperationsException,
			IncompleteException, InsufficientPermissionException, DataException, NotFoundException {

		categoryService.deleteCategoryForce(appInfo.getCurrentUser(), categoryService.getCategoryByIdManage(id));

		return SimpleResponseEntity.builder()
				.code(200)
				.message("删除完成")
				.build();
	}

}
