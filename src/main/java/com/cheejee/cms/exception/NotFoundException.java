package com.cheejee.cms.exception;

/**
 * 要操作的数据在数据库不存在时将会抛出此异常
 * @author CARRY ME HOME
 * 2019年12月12日上午9:44:12
 */
public class NotFoundException  extends Exception{
	
	private static final long serialVersionUID = 3711842200011644103L;

	public NotFoundException(String message) {
		super(message);
	}
}
