package com.cheejee.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cheejee.cms.common.BasicLog;
import com.cheejee.cms.dto.back.BackGroupGetDto;
import com.cheejee.cms.response.ApiResponseEntity;

/**
 * 
 * @author CARRY ME HOME
 * 2020年5月5日上午11:13:29
 */
@Aspect
@Component
public class GroupLogAspect extends BasicLog{

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackGroupController.addGroup(..))")
	private void addPoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackGroupController.changeGroup(..))")
	private void changePoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackGroupController.deleteGroup(..))")
	private void deletePoint() {
	}

	@AfterReturning(value = "addPoint()", returning = "responseEntity")
	public void addGroupLog(ApiResponseEntity<BackGroupGetDto> responseEntity) {
			BackGroupGetDto a = responseEntity.getData();
			addLog("添加分组【后台】：名称=" + a.getName() + "， id=" + a.getId());
	}

	@AfterReturning(value = "changePoint()", returning = "responseEntity")
	public void changeGroupNameLog(ApiResponseEntity<BackGroupGetDto> responseEntity) {
		BackGroupGetDto a = responseEntity.getData();
		addLog("修改分组【后台】：名称=" + a.getName() + "， id=" + a.getId());
	}

	@AfterReturning(value = "deletePoint()", returning = "responseEntity")
	public void deleteGroupLog(JoinPoint joinPoint) {
		addLog("删除分组【后台】：id=" + joinPoint.getArgs()[0]);
	}

}
