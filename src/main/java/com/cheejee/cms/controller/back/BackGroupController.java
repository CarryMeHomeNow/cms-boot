package com.cheejee.cms.controller.back;

import static com.cheejee.cms.tools.BuliderTool.buildGroup;

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

import com.cheejee.cms.common.CurrentAppInfo;
import com.cheejee.cms.dto.back.BackGroupGetDto;
import com.cheejee.cms.dto.back.BackGroupPostDto;
import com.cheejee.cms.dto.back.BackGroupPutDto;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Group;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.response.SimpleResponseEntity;
import com.cheejee.cms.service.GroupService;
import com.cheejee.cms.tools.WashTool;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author CARRY ME HOME 2020年4月18日下午3:36:14
 */
@RestController
@RequestMapping("/admin/group")
@Api(tags = "分组-后台")
@Validated
public class BackGroupController {

	@Resource
	private GroupService groupService;
	
	@Resource
	private CurrentAppInfo appInfo;

	@GetMapping("/")
	@RequiresPermissions("group:get")
	@ApiOperation("获取所有分组")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<BackGroupGetDto> getAllGroup(int pageNum, int pageSize) {
		PageInfo<Group> info = groupService.getGroupAll(pageNum, pageSize);

		return PageResponseEntity.<BackGroupGetDto>builder()
				.code(200)
				.message("获取完成")
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.list(BackGroupGetDto.parse(info.getList()))
				.build();
	}

	@GetMapping("/{id}")
	@RequiresPermissions("group:get")
	@ApiOperation("获取指定id的分组")
	@ApiImplicitParam(name = "id", value = "分组id", required = true)
	public ApiResponseEntity<BackGroupGetDto> getGroup(@PathVariable int id) {
		Group group = groupService.getGroupEditById(id);
		String message = group == null ? "没有查找到相关分组" : "查询成功";

		return ApiResponseEntity.<BackGroupGetDto>builder()
				.code(200)
				.message(message)
				.data(BackGroupGetDto.parse(group))
				.build();
	}

	@GetMapping("/search")
	@RequiresPermissions("group:get")
	@ApiOperation(value = "搜索分组", notes = "搜索出名称与关键字匹配的分组")
	@ApiImplicitParams({ @ApiImplicitParam(name = "key", value = "用于搜索的关键字", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<BackGroupGetDto> searchGroup(@NotBlank String key, int pageNum, int pageSize) {
		PageInfo<Group> info = groupService.getGroupByName(pageNum, pageSize, key);

		return PageResponseEntity.<BackGroupGetDto>builder()
				.code(200)
				.message("获取完成")
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.list(BackGroupGetDto.parse(info.getList()))
				.build();
	}

	@GetMapping("/basic")
	@RequiresPermissions("group:get")
	@ApiOperation(value = "获取基本组", notes = "获取基本组，即没有父组的组。如果是用于展示则应该使用此方法获取组，而不应该获取所有组。")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<BackGroupGetDto> getBasicGroup(int pageNum, int pageSize) {
		PageInfo<Group> info = groupService.getGroupNoParent(pageNum, pageSize);

		return PageResponseEntity.<BackGroupGetDto>builder()
				.code(200)
				.message("获取完成")
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.list(BackGroupGetDto.parse(info.getList()))
				.build();
	}

	@PostMapping("/add")
	@RequiresPermissions("group:add")
	@ApiOperation("添加分组")
	public ApiResponseEntity<BackGroupGetDto> addGroup(@RequestBody BackGroupPostDto group)
			throws NullPointerException, IncompleteException, DataDuplicationException {
		
		Group g = group.toGroup();
		g.setCreater(appInfo.getCurrentUser());
		WashTool.washGroup(g);
		groupService.addGroup(g);
		
		return ApiResponseEntity.<BackGroupGetDto>builder()
				.code(200)
				.message("添加完成")
				.data(BackGroupGetDto.parse(g))
				.build();
	}
	
	@PutMapping("/{id}")
	@RequiresPermissions("group:edit")
	@ApiOperation(value = "修改分组", notes = "修改分组的信息，包括分组的组员和分组的角色。如果组员，角色为空则不会对组员和角色进行修改。")
	@ApiImplicitParam(name = "id", value = "分组id", required = true)
	public ApiResponseEntity<BackGroupGetDto> changeGroup(@PathVariable int id, @RequestBody BackGroupPutDto group) throws IncompleteException, DataDuplicationException, OperationsException, NotFoundException {
		
		Group g = group.toGroup(id);
		WashTool.washGroup(g);
		groupService.changeGroup(g);
		
		return ApiResponseEntity.<BackGroupGetDto>builder()
				.code(200)
				.message("修改完成")
				.data(BackGroupGetDto.parse(groupService.getGroupById(id)))
				.build();
	}

	@DeleteMapping("/{id}")
	@RequiresPermissions("group:delete")
	@ApiOperation("删除分组")
	@ApiImplicitParam(name = "id", value = "分组id", required = true)
	public SimpleResponseEntity deleteGroup(@PathVariable int id) throws IncompleteException, OperationsException {
		groupService.deleteGroup(buildGroup(id));

		return SimpleResponseEntity.builder()
				.code(200)
				.message("删除完成")
				.build();
	}
	
	
}
