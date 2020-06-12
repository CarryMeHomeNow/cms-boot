package com.cheejee.cms.controller.back;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheejee.cms.dto.back.BackUserInfoGetDto;
import com.cheejee.cms.dto.back.BackUserInfoPutDto;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.service.UserService;
import com.cheejee.cms.tools.WashTool;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/admin/user")
@Api(tags = "用户-后台")
@Validated
public class BackUserController {

	@Resource
	private UserService userService;

	@GetMapping("/")
	@RequiresPermissions("user:get")
	@ApiOperation(value = "获取所有用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<BackUserInfoGetDto> getAllUser(int pageNum, int pageSize) {
		PageInfo<User> info = userService.getUserAllByManage(pageNum, pageSize);

		return PageResponseEntity.<BackUserInfoGetDto>builder()
				.code(200)
				.message("获取完成")
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.list(BackUserInfoGetDto.parse(info.getList(), userService))
				.build();
	}
	

	@GetMapping("/allAdmin")
	@RequiresPermissions("user:get")
	@ApiOperation(value = "获取所有管理员")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<BackUserInfoGetDto> getAllAdmin(int pageNum, int pageSize) {
		PageInfo<User> info = userService.getUserAllByManage(pageNum, pageSize);

		return PageResponseEntity.<BackUserInfoGetDto>builder()
				.code(200)
				.message("获取完成")
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.list(BackUserInfoGetDto.parse(info.getList(), userService))
				.build();
	}

	@GetMapping("/{id}")
	@RequiresPermissions("user:get")
	@ApiOperation(value = "获取用户信息")
	@ApiImplicitParam(name = "id", value = "用户id")
	public ApiResponseEntity<BackUserInfoGetDto> getUserInfo(@PathVariable int id) {
		BackUserInfoGetDto info = BackUserInfoGetDto.parse(userService.getUserEditByIdManage(id));

		if (info == null) {
			return ApiResponseEntity.<BackUserInfoGetDto>builder()
					.code(400)
					.message("没有查找到相关信息")
					.build();
		} else {
			return ApiResponseEntity.<BackUserInfoGetDto>builder()
					.code(200)
					.message("获取完成")
					.data(info)
					.build();
		}
	}

	@GetMapping("/search")
	@RequiresPermissions("user:get")
	@ApiOperation(value = "搜索用户", notes = "搜索用户名匹配的用户")
	@ApiImplicitParams({ @ApiImplicitParam(name = "name", value = "用户名关键字", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<BackUserInfoGetDto> searchUser(@NotBlank String name, int pageSize, int pageNum) {
		PageInfo<User> info = userService.getUserByName(pageNum, pageSize, name.trim());

		return PageResponseEntity.<BackUserInfoGetDto>builder()
				.code(200)
				.message("查找完成")
				.pageNum(info.getPageNum())
				.size(info.getSize())
				.total(info.getTotal())
				.pages(info.getPages())
				.list(BackUserInfoGetDto.parse(info.getList(), userService))
				.build();
	}

	@PutMapping("/{id}")
	@RequiresPermissions("user:edit")
	@ApiOperation(value = "修改用户信息", notes = "可以修改状态值和个人信息，状态值为1的账号才能登录成功。状态值不能设置为0，设置为0的状态值不会进行修改。")
	@ApiResponse(code = 200, message = "修改完成。")
	@ApiImplicitParam(name = "id", value = "用户id", required = true)
	public ApiResponseEntity<BackUserInfoGetDto> changeUserInfo(@PathVariable int id, @RequestBody BackUserInfoPutDto info)
			throws DataDuplicationException, NotFoundException, IncompleteException, OperationsException {
		
		User user = info.toUser(id);
		WashTool.washUser(user);
		userService.changeUserByManage(user);

		return ApiResponseEntity.<BackUserInfoGetDto>builder()
				.code(200)
				.message("修改完成")
				.data(BackUserInfoGetDto.parse(userService.getUserByIdManage(id)))
				.build();
	}

}
