package com.cheejee.cms.exception;

/**
 * 操作异常，操作失败时会抛出这个异常
 * @author CARRY ME HOME
 * 2019年12月11日下午6:05:23
 */
public class OperationsException extends Exception{

	private static final long serialVersionUID = -7005230618084166556L;
	
	public OperationsException(String message) {
		super(message);
	}

}
