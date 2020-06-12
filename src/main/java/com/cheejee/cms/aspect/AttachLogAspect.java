package com.cheejee.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cheejee.cms.common.BasicLog;
import com.cheejee.cms.dto.AttachGetDto;
import com.cheejee.cms.response.ApiResponseEntity;

/**
 * 
 * @author CARRY ME HOME 2020年5月4日下午4:29:49
 */
@Aspect
@Component
public class AttachLogAspect extends BasicLog {

	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeAttachController.uploadAttach(..))")
	private void uploadPoint() {
	}
	
	@Pointcut("execution(* com.cheejee.cms.controller.fore.ForeAttachController.deleteAttach(..))")
	private void deletePoint() {
	}
	
	@Pointcut("execution(* com.cheejee.cms.controller.back.BackAttachController.deleteAttach(..))")
	private void adminDeletePoint() {
	}

	@AfterReturning(value = "uploadPoint()", returning = "responseEntity")
	public void uploadAttachLog(ApiResponseEntity<AttachGetDto> responseEntity) {
		if (responseEntity.getCode() == 200) {
			AttachGetDto a = responseEntity.getData();
			addLog("上传附件：" + a.getName());
		}
	}
	
	@AfterReturning(value = "deletePoint()")
	public void deleteAttachLog(JoinPoint joinPoint) {
			addLog("删除附件：id=" + joinPoint.getArgs()[0]);
	}
	
	@AfterReturning(value = "adminDeletePoint()")
	public void adminDeleteAttachLog(JoinPoint joinPoint) {
		addLog("删除附件【后台】：id=" + joinPoint.getArgs()[0]);
	}

}
