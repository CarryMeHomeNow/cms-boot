package com.cheejee.cms.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cheejee.cms.common.BasicLog;
import com.cheejee.cms.dto.ContentGetDto;
import com.cheejee.cms.response.ApiResponseEntity;

/**
 * 
 * @author CARRY ME HOME 2020年5月5日上午10:41:35
 */
@Aspect
@Component
public class ContentLogAspect extends BasicLog {

	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeContentController.addContent(..))")
	private void addPoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeContentController.changeContent(..))")
	private void changePoint() {
	}
	
	@Pointcut("execution(* com.cheejee.cms.controller.back.BackContentController.changeContent(..))")
	private void adminChangePoint() {
	}
	
	@Pointcut("execution(* com.cheejee.cms.controller.back.BackContentController.reviewContent(..))")
	private void adminEeviewPoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeContentController.deleteContent(..))")
	private void deletePoint() {
	}
	
	@Pointcut("execution(* com.cheejee.cms.controller.back.BackContentController.deleteContent(..))")
	private void adminDeletePoint() {
	}

	@AfterReturning(value = "addPoint()", returning = "responseEntity")
	public void addCategryLog(ApiResponseEntity<ContentGetDto> responseEntity) {
		ContentGetDto c = responseEntity.getData();
		addLog("新增内容：标题=" + c.getTitle() + "， id=" + c.getId());
	}

	@AfterReturning(value = "changePoint()", returning = "responseEntity")
	public void changeContentNameLog(ApiResponseEntity<ContentGetDto> responseEntity) {
		ContentGetDto c = responseEntity.getData();
		addLog("编辑内容：标题=" + c.getTitle() + "， id=" + c.getId());
	}
	
	@AfterReturning(value = "adminChangePoint()", returning = "responseEntity")
	public void adminChangeContentNameLog(ApiResponseEntity<ContentGetDto> responseEntity) {
		ContentGetDto c = responseEntity.getData();
		addLog("编辑内容【后台】：标题=" + c.getTitle() + "， id=" + c.getId());
	}
	
	@AfterReturning(value = "adminEeviewPoint()")
	public void adminEeviewLog(JoinPoint joinPoint) {
		int[] ids = (int[]) joinPoint.getArgs()[0];
		addLog("审核内容【后台】：" + Arrays.toString(ids));
	}

	@AfterReturning(value = "deletePoint()", returning = "responseEntity")
	public void deleteContentLog(JoinPoint joinPoint) {
		addLog("删除内容：id=" + joinPoint.getArgs()[0]);
	}
	
	@AfterReturning(value = "adminDeletePoint()", returning = "responseEntity")
	public void adminDeleteContentLog(JoinPoint joinPoint) {
		addLog("删除内容【后台】：id=" + joinPoint.getArgs()[0]);
	}
}
