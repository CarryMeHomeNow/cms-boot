package com.cheejee.cms.controller.back;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheejee.cms.common.CurrentAppInfo;
import com.cheejee.cms.dto.back.BackRoleGetDto;
import com.cheejee.cms.dto.back.BackRolePostDto;
import com.cheejee.cms.dto.back.BackRolePutDto;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.response.SimpleResponseEntity;
import com.cheejee.cms.service.RoleService;
import com.cheejee.cms.tools.BuliderTool;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author CARRY ME HOME 2020年4月27日下午10:42:13
 */
@RestController
@RequestMapping("/admin/role")
@Api(tags = "角色-后台")
public class BackRoleController {

	@Resource
	private RoleService roleService;

	@Resource
	private CurrentAppInfo appInfo;

	@GetMapping("/")
	@RequiresPermissions("role:get")
	@ApiOperation("获取所有角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<BackRoleGetDto> getAllRole(int pageNum, int pageSize) {
		PageInfo<Role> info = roleService.getRoleAll(pageNum, pageSize);

		return PageResponseEntity.<BackRoleGetDto>builder()
				.code(200)
				.message("获取完成")
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.list(BackRoleGetDto.parse(info.getList()))
				.build();
	}

	@GetMapping("/{id}")
	@RequiresPermissions("role:get")
	@ApiOperation("获取指定id的角色")
	@ApiImplicitParam(name = "id", value = "角色id", required = true)
	public ApiResponseEntity<BackRoleGetDto> getRole(@PathVariable int id) {
		Role role = roleService.getRoleEditById(id);
		String message = role != null ? "获取完成" : "角色不存在";

		return ApiResponseEntity.<BackRoleGetDto>builder()
				.code(200)
				.message(message)
				.data(BackRoleGetDto.parse(role))
				.build();
	}

	@PostMapping("/add")
	@RequiresPermissions("role:add")
	@ApiOperation(value = "添加角色", notes = "")
	public ApiResponseEntity<BackRoleGetDto> addRole(@RequestBody BackRolePostDto role)
			throws DataDuplicationException, IncompleteException {

		Role r = role.toRole(appInfo.getCurrentUser());
		roleService.addRole(r);

		return ApiResponseEntity.<BackRoleGetDto>builder()
				.code(200)
				.message("添加完成")
				.data(BackRoleGetDto.parse(r))
				.build();
	}

	@PutMapping("/{id}")
	@RequiresPermissions("role:edit")
	@ApiOperation(value = "修改角色", notes = "修改角色信息同时也会修改角色拥有的权限。")
	@ApiImplicitParam(name = "id", value = "角色id", required = true)
	public ApiResponseEntity<BackRoleGetDto> changeRole(@PathVariable int id, @RequestBody BackRolePutDto role)
			throws DataDuplicationException, IncompleteException, NotFoundException {

		roleService.changeRole(role.toRole(id));

		return ApiResponseEntity.<BackRoleGetDto>builder()
				.code(200)
				.message("修改完成")
				.data(BackRoleGetDto.parse(roleService.getRoleEditById(id)))
				.build();
	}

	@DeleteMapping("/{id}")
	@RequiresPermissions("role:delete")
	@ApiOperation(value = "删除角色", notes = "只能删除没有用户使用的角色")
	@ApiImplicitParam(name = "id", value = "角色id", required = true)
	public SimpleResponseEntity deleteRole(@PathVariable int id) throws IncompleteException, OperationsException {
		roleService.deleteRole(BuliderTool.buildRole(id));

		return SimpleResponseEntity.builder()
				.code(200)
				.message("删除完成")
				.build();
	}

}
