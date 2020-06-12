package com.cheejee.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cheejee.cms.common.BasicLog;
import com.cheejee.cms.dto.fore.ForeCategoryGetDto;
import com.cheejee.cms.response.ApiResponseEntity;

/**
 * 
 * @author CARRY ME HOME 2020年5月5日上午10:26:07
 */
@Aspect
@Component
public class CategoryLogAspect extends BasicLog {

	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeCategoryController.addCategry(..))")
	private void addPoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeCategoryController.changeCategoryName(..))")
	private void changePoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeCategoryController.deleteCategory*(..))")
	private void deletePoint() {
	}

	@AfterReturning(value = "addPoint()", returning = "responseEntity")
	public void addCategryLog(ApiResponseEntity<ForeCategoryGetDto> responseEntity) {
		if (responseEntity.getCode() == 200) {
			ForeCategoryGetDto a = responseEntity.getData();
			addLog("添加分类：" + a.getName());
		}
	}

	@AfterReturning(value = "changePoint()")
	public void changeCategoryNameLog(JoinPoint joinPoint) {
		String message = String.format("修改id=%s的分类名称为：%s", joinPoint.getArgs()[0], joinPoint.getArgs()[1]);
		addLog(message);
	}

	@AfterReturning(value = "deletePoint()", returning = "responseEntity")
	public void deleteCategoryLog(JoinPoint joinPoint) {
		addLog("删除分类：id=" + joinPoint.getArgs()[0]);
	}

}
