package com.cheejee.cms.exception;

/**
 * 添加的附件不受系统支持时会抛出此异常。
 * @author CARRY ME HOME
 * 2019年12月21日上午9:51:32
 */
public class NotSupportedException extends Exception{

	private static final long serialVersionUID = -1457882134861074917L;

	public NotSupportedException(String message) {
		super(message);
	}
}
