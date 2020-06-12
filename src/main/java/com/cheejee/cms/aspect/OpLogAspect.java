package com.cheejee.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cheejee.cms.common.BasicLog;

@Aspect
@Component
public class OpLogAspect extends BasicLog{

	@Pointcut("execution(* com.cheejee.cms.controller.back.BackLogController.deleteLogBefore(..))")
	private void deletePoint() {
	}
	
	@AfterReturning(value = "deletePoint()")
	public void deleteOpLog(JoinPoint joinPoint) {
			addLog("清除日志【后台】,清除了" + joinPoint.getArgs()[0] + "天以前的日志");
	}
}
