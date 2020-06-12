package com.cheejee.cms.exception;

/**
 * 对象不完全异常，传入的对象没有包含方法所必须的值时将会抛出此异常
 * @author CARRY ME HOME
 * 2019年12月12日上午9:43:19
 */
public class IncompleteException extends Exception {

	private static final long serialVersionUID = 4101951625965774643L;

	public IncompleteException(String message) {
		super(message);
	}
}
