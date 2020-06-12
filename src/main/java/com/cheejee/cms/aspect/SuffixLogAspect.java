package com.cheejee.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cheejee.cms.common.BasicLog;
import com.cheejee.cms.dto.back.BackSuffixGetDto;
import com.cheejee.cms.response.ApiResponseEntity;

/**
 * 
 * @author CARRY ME HOME
 * 2020年5月5日下午4:15:50
 */
@Aspect
@Component
public class SuffixLogAspect extends BasicLog{

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackSuffixController.addSuffix(..))")
	private void addPoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackSuffixController.changeSuffixDescribe(..))")
	private void changePoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackSuffixController.deleteSuffix(..))")
	private void deletePoint() {
	}

	@AfterReturning(value = "addPoint()", returning = "responseEntity")
	public void addSuffixLog(ApiResponseEntity<BackSuffixGetDto> responseEntity) {
			BackSuffixGetDto a = responseEntity.getData();
			addLog("添加后缀【后台】：名称=" + a.getSuffix() + "， id=" + a.getId());
	}

	@AfterReturning(value = "changePoint()", returning = "responseEntity")
	public void changeSuffixNameLog(ApiResponseEntity<BackSuffixGetDto> responseEntity) {
		BackSuffixGetDto a = responseEntity.getData();
		addLog("修改后缀描述【后台】：名称=" + a.getSuffix() + "，描述=" + a.getDescribe());
	}

	@AfterReturning(value = "deletePoint()", returning = "responseEntity")
	public void deleteSuffixLog(JoinPoint joinPoint) {
		addLog("删除后缀【后台】：id=" + joinPoint.getArgs()[0]);
	}

}