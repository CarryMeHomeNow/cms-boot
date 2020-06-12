package com.cheejee.cms.exception;

/**
 * 权限不足抛出此异常
 * @author CARRY ME HOME
 * 2019年12月11日下午3:42:30
 */
public class InsufficientPermissionException extends Exception{
	
	private static final long serialVersionUID = -3289923174882057531L;

	public InsufficientPermissionException(String message) {
		super(message);
	}
}
