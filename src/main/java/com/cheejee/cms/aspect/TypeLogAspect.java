package com.cheejee.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cheejee.cms.common.BasicLog;
import com.cheejee.cms.dto.back.BackTypeGetDto;
import com.cheejee.cms.response.ApiResponseEntity;

/**
 * 
 * @author CARRY ME HOME
 * 2020年5月5日上午11:13:29
 */
@Aspect
@Component
public class TypeLogAspect extends BasicLog{

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackTypeController.addType(..))")
	private void addPoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackTypeController.changeType(..))")
	private void changePoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackTypeController.deleteType(..))")
	private void deletePoint() {
	}

	@AfterReturning(value = "addPoint()", returning = "responseEntity")
	public void addTypeLog(ApiResponseEntity<BackTypeGetDto> responseEntity) {
			BackTypeGetDto a = responseEntity.getData();
			addLog("添加类型【后台】：名称=" + a.getName() + "， id=" + a.getId());
	}

	@AfterReturning(value = "changePoint()", returning = "responseEntity")
	public void changeTypeNameLog(ApiResponseEntity<BackTypeGetDto> responseEntity) {
		BackTypeGetDto a = responseEntity.getData();
		addLog("修改类型【后台】：名称=" + a.getName() + "， id=" + a.getId());
	}

	@AfterReturning(value = "deletePoint()", returning = "responseEntity")
	public void deleteTypeLog(JoinPoint joinPoint) {
		addLog("删除类型【后台】：id=" + joinPoint.getArgs()[0]);
	}

}
