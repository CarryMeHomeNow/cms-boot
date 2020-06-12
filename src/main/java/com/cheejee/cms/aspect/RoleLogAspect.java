package com.cheejee.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cheejee.cms.common.BasicLog;
import com.cheejee.cms.dto.back.BackRoleGetDto;
import com.cheejee.cms.response.ApiResponseEntity;

/**
 * 
 * @author CARRY ME HOME
 * 2020年5月5日下午4:15:50
 */
@Aspect
@Component
public class RoleLogAspect extends BasicLog{

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackRoleController.addRole(..))")
	private void addPoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackRoleController.changeRole(..))")
	private void changePoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackRoleController.deleteRole(..))")
	private void deletePoint() {
	}

	@AfterReturning(value = "addPoint()", returning = "responseEntity")
	public void addRoleLog(ApiResponseEntity<BackRoleGetDto> responseEntity) {
			BackRoleGetDto a = responseEntity.getData();
			addLog("添加角色【后台】：名称=" + a.getRoleName() + "， id=" + a.getRoleId());
	}

	@AfterReturning(value = "changePoint()", returning = "responseEntity")
	public void changeRoleNameLog(ApiResponseEntity<BackRoleGetDto> responseEntity) {
		BackRoleGetDto a = responseEntity.getData();
		addLog("修改角色【后台】：名称=" + a.getRoleName() + "， id=" + a.getRoleId());
	}

	@AfterReturning(value = "deletePoint()", returning = "responseEntity")
	public void deleteRoleLog(JoinPoint joinPoint) {
		addLog("删除角色【后台】：id=" + joinPoint.getArgs()[0]);
	}

}