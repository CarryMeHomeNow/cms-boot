package com.cheejee.cms.controller.fore;

import static com.cheejee.cms.tools.FileTool.downloadFile;
import static com.cheejee.cms.tools.FileTool.getFileSuffix;
import static com.cheejee.cms.tools.FileTool.uploadFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cheejee.cms.common.CurrentAppInfo;
import com.cheejee.cms.dto.fore.ForePasswordPutDto;
import com.cheejee.cms.dto.fore.ForeUserGetDto;
import com.cheejee.cms.dto.fore.ForeUserInfoGetDto;
import com.cheejee.cms.dto.fore.ForeUserInfoPutDto;
import com.cheejee.cms.dto.fore.ForeUserPostDto;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.PersonalInfo;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.BasicResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.response.SimpleResponseEntity;
import com.cheejee.cms.response.UploadResponseEntity;
import com.cheejee.cms.service.UserService;
import com.cheejee.cms.tools.BuliderTool;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user")
@Api(tags = "用户")
@Validated
public class ForeUserController {

	@Value("${cheejee.cms.avatar-path}")
	private String avatarPath;

	@Value("${cheejee.cms.avatar-extension}")
	private String extension;

	@Resource
	private UserService userService;

	@Resource
	private CurrentAppInfo appInfo;

	@GetMapping("/")
	@ApiOperation(value = "获取当前用户信息")
	public ApiResponseEntity<ForeUserInfoGetDto> getCurrentUser() {

		return ApiResponseEntity.<ForeUserInfoGetDto>builder()
				.code(200)
				.message("获取完成")
				.data(ForeUserInfoGetDto.parse(appInfo.getCurrentUser()))
				.build();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "获取用户信息", notes = "根据id查找出用户的信息")
	@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "用户id", required = true, example = "123")
	@ApiResponse(code = 200, message = "查找完成")
	public ApiResponseEntity<ForeUserInfoGetDto> getUserInfo(@PathVariable int id) {
		ForeUserInfoGetDto info = ForeUserInfoGetDto.parse(userService.getUserByIdManage(id));

		if (info != null) {
			return ApiResponseEntity.<ForeUserInfoGetDto>builder()
					.code(200)
					.message("查找成功")
					.data(info)
					.build();
		} else {
			return ApiResponseEntity.<ForeUserInfoGetDto>builder()
					.code(400)
					.message("没有查找到相关信息")
					.build();
		}
	}

	@GetMapping("/search")
	@ApiOperation(value = "搜索用户", notes = "搜索用户名匹配的用户")
	@ApiImplicitParams({ @ApiImplicitParam(name = "name", value = "用户名关键字", paramType = "query", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<ForeUserInfoGetDto> searchUser(@NotBlank String name, int pageSize, int pageNum) {
		PageInfo<User> info = userService.getUserByName(pageNum, pageSize, name.trim());

		return PageResponseEntity.<ForeUserInfoGetDto>builder()
				.code(200)
				.message("查找完成")
				.pageNum(info.getPageNum())
				.size(info.getSize())
				.total(info.getTotal())
				.pages(info.getPages())
				.list(ForeUserInfoGetDto.parse(info.getList(), userService))
				.build();
	}

	@GetMapping("/downloadAvatar/{id}")
	@ApiOperation(value = "头像下载", notes = "下载指定id的用户的头像。")
	@ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int", defaultValue = "0", example = "1253")
	public SimpleResponseEntity downloadAvatar(@PathVariable int id, HttpServletResponse response) throws IOException {
		User user = userService.getUserByIdManage(id);
		if (user == null) {
			return SimpleResponseEntity.builder()
					.code(400)
					.message("用户id错误")
					.build();
		}

		String path = user.getPersonalInfo()
				.getBigAvatarUrl();
		if (path == null || path.equals("")) {
			return SimpleResponseEntity.builder()
					.code(400)
					.message("此用户没有上传头像")
					.build();
		}

		File file = new File(path);
		if (file.exists()) {
			downloadFile(file, response);
			return SimpleResponseEntity.builder()
					.code(200)
					.message("下载完成")
					.build();

		} else {
			return SimpleResponseEntity.builder()
					.code(400)
					.message("文件不存在")
					.build();
		}
	}

	@PutMapping("/{id}/userInfo")
	@ApiOperation(value = "修改用户信息", notes = "修改用户个人信息，info中所包含的信息都将进行更新操作。返回用户修改后的个人信息。")
	@ApiResponse(code = 200, message = "修改完成。")
	public ApiResponseEntity<ForeUserInfoGetDto> changeUserInfo(@PathVariable int id,
			@RequestBody ForeUserInfoPutDto info)
			throws DataDuplicationException, NotFoundException, IncompleteException, OperationsException {

		User user = info.toUser(id);
		userService.changePersonalInfo(user);
		user = userService.getUserByIdManage(user.getId());

		return ApiResponseEntity.<ForeUserInfoGetDto>builder()
				.code(200)
				.message("修改完成")
				.data(ForeUserInfoGetDto.parse(user))
				.build();
	}

	@PutMapping("/{id}/password")
	@ApiOperation(value = "修改登录密码", notes = "需要提供旧密码和新密码，修改密码成功会退出登录。")
	public BasicResponseEntity changePassword(@PathVariable int id, @Valid @RequestBody ForePasswordPutDto password)
			throws NoSuchAlgorithmException, IncompleteException {

		String newp = password.getNewPass();
		String oldp = password.getOldPass();

		if (newp.equals(oldp)) {
			return SimpleResponseEntity.builder()
					.code(400)
					.message("新密码与旧密码相同")
					.build();
		}

		User user = BuliderTool.buildUser(id);
		user.setPassword(newp);

		try {
			userService.changePassword(user, oldp);

		} catch (DataException e) {
			return SimpleResponseEntity.builder()
					.code(400)
					.message("旧密码错误")
					.build();
		}

		SecurityUtils.getSubject()
				.logout();

		return ApiResponseEntity.<ForeUserGetDto>builder()
				.code(200)
				.message("修改成功，请重新登录")
				.data(ForeUserGetDto.parse(user))
				.build();

	}

	@PostMapping(value = "/register")
	@ApiOperation(value = "用户注册", notes = "普通用户注册，输入用户名和密码完成注册")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "注册成功"), @ApiResponse(code = 405, message = "用户名已存在") })
	public ApiResponseEntity<ForeUserGetDto> register(@Validated @RequestBody ForeUserPostDto userd)
			throws NoSuchAlgorithmException, IncompleteException, OperationsException, DataDuplicationException {

		User user = new User(userd.getName(), userd.getPassword());
		userService.register(user);

		return ApiResponseEntity.<ForeUserGetDto>builder()
				.code(200)
				.message("注册成功")
				.data(ForeUserGetDto.parse(user))
				.build();
	}

	@PostMapping("/login")
	@ApiOperation(value = "用户登录", notes = "")
	@ApiImplicitParam(value = "记住我", name = "rememberMe", defaultValue = "false", example = "false", dataType = "Boolean")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "登录成功"),
			@ApiResponse(code = 401, message = "用户名或密码错误") })
	public ApiResponseEntity<ForeUserGetDto> login(@Validated @RequestBody ForeUserPostDto userd, boolean rememberMe) {

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userd.getName(), userd.getPassword());
		token.setRememberMe(rememberMe);

		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			return ApiResponseEntity.<ForeUserGetDto>builder()
					.code(401)
					.message("用户名或密码错误")
					.build();
		}

		User user = userService.getUserByName(userd.getName());
		if (user.getState() != 1) {
			subject.logout();
			return ApiResponseEntity.<ForeUserGetDto>builder()
					.code(200)
					.message("用户状态异常，登录失败")
					.data(null)
					.build();
		}

		return ApiResponseEntity.<ForeUserGetDto>builder()
				.code(200)
				.message("登录成功")
				.data(ForeUserGetDto.parse(user))
				.build();
	}

	@PostMapping("/logout")
	@ApiOperation(value = "退出登录", notes = "")
	public SimpleResponseEntity logout() {
		SecurityUtils.getSubject()
				.logout();

		return SimpleResponseEntity.builder()
				.code(200)
				.message("已退出登录")
				.build();
	}

	@PostMapping(value = "/uploadAvatar", headers = "content-type=multipart/form-data")
	@ApiOperation(value = "上传头像", notes = "")
	@ApiImplicitParam(name = "avatar", value = "上传的头像文件", dataType = "__file", required = true)
	public UploadResponseEntity uploadAvatar(MultipartFile avatar) throws FileUploadException, IncompleteException, NotFoundException {

		if (!checkFileIsImage(avatar)) {
			return UploadResponseEntity.builder()
					.code(400)
					.message("文件格式不受支持")
					.build();
		}

		String newPath = uploadFile(avatar, avatarPath);
		updateAvatar(newPath);

		return UploadResponseEntity.builder()
				.code(200)
				.message("上传成功")
				.url(newPath)
				.build();
	}

	/**
	 * 更新当前登录用户在数据库中的头像路径。
	 * 
	 * @param request
	 * @param avatarPath
	 * @throws IncompleteException
	 * @throws NotFoundException
	 */
	private void updateAvatar(String avatarPath) throws IncompleteException, NotFoundException {

		User user = appInfo.getCurrentUser();
		PersonalInfo info = user.getPersonalInfo();
		deleteOldAvatarImag(info);
		info.setBigAvatarUrl(avatarPath);
		userService.changePersonalInfo(user);
	}

	/**
	 * 删除用户的旧头像文件
	 * 
	 * @param info
	 * @param request
	 */
	private void deleteOldAvatarImag(PersonalInfo info) {
		String path = info.getBigAvatarUrl();
		if (path != null) {
			new File(path).delete();
		}
	}

	/**
	 * 检查文件的格式是否受支持，受支持的后缀列表从配置文件取得。
	 * 
	 * @param file
	 * @return
	 */
	private boolean checkFileIsImage(MultipartFile file) {
		return extension.contains(getFileSuffix(file));
	}

}
