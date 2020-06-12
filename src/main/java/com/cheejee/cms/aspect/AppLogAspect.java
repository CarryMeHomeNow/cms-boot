package com.cheejee.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class AppLogAspect {
	
	@Pointcut("execution(* com.cheejee.cms.handler.ControllerExceptionHandler.*(..))")
	private void exceptionPoint() {
	}

	@Pointcut("execution(* com.cheejee.cms.controller.*.*(..))")
	private void controllerPoint() {
	}
	
	@After(value = "exceptionPoint()")
	public void exceptionLog(JoinPoint joinPoint) {
		Exception e = (Exception) joinPoint.getArgs()[0];
		log.debug("异常处理器{}捕捉到异常=> {}", joinPoint.getSignature().getName(), e.toString());
	}
	
	@Before(value = "controllerPoint()")
	public void controllerLog(JoinPoint joinPoint) {
		log.debug("请求映射到了{}方法", joinPoint.getSignature());
	}
	
}
