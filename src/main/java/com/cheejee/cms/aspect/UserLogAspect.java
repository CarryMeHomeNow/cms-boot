package com.cheejee.cms.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cheejee.cms.common.BasicLog;
import com.cheejee.cms.dto.fore.ForeUserGetDto;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.BasicResponseEntity;

/**
 * 
 * @author CARRY ME HOME 2020年5月4日下午3:48:06
 */
@Aspect
@Component
public class UserLogAspect extends BasicLog {

	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeUserController.login(..))")
	private void loginPoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeUserController.register(..))")
	private void registerPoint() {
	}
	
	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeUserController.changeUserInfo(..))")
	private void changeInfoPoint() {
	}
	
	@Pointcut("execution(* com.cheejee.cms.controller.back.BackUserController.changeUserInfo(..))")
	private void adminChangeInfoPoint() {
	}
	
	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeUserController.uploadAvatar(..))")
	private void changeAvatarPoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeUserController.changePassword(..))")
	private void changePasswordPoint() {
	}

	@AfterReturning(value = "loginPoint()", returning = "responseEntity")
	public void loginLog(BasicResponseEntity responseEntity) {
		if (responseEntity.getCode() == 200) {
			addLog("登录成功");
		}
	}

	@AfterReturning(value = "registerPoint()", returning = "responseEntity")
	public void registerLog(ApiResponseEntity<ForeUserGetDto> responseEntity) {
		if (responseEntity.getCode() == 200) {
			addLog("用户注册", ForeUserGetDto.toUser(responseEntity.getData()));
		}
	}

	@AfterReturning(value = "changeInfoPoint()", returning = "responseEntity")
	public void changeInfoLog(BasicResponseEntity responseEntity) {
			addLog("修改个人信息");
	}
	
	@AfterReturning(value = "adminChangeInfoPoint()", returning = "responseEntity")
	public void adminChangeInfoLog(BasicResponseEntity responseEntity) {
			addLog("修改个人信息【后台】");
	}
	
	@AfterReturning(value = "changeAvatarPoint()", returning = "responseEntity")
	public void changeAvatarLog(BasicResponseEntity responseEntity) {
		if (responseEntity.getCode() == 200) {
			addLog("上传头像");
		}
	}
	
	@SuppressWarnings("unchecked")
	@AfterReturning(value = "changePasswordPoint()", returning = "responseEntity")
	public void changePassLog(BasicResponseEntity responseEntity) {
		if (responseEntity.getCode() == 200) {
			ApiResponseEntity<ForeUserGetDto> are = (ApiResponseEntity<ForeUserGetDto>) responseEntity;
			addLog("修改了登录密码", ForeUserGetDto.toUser(are.getData()));
		}
	}

}
