package com.cheejee.cms.exception;

/**
 * 对象的某些数据和数据库不匹配时会抛出此异常
 * @author CARRY ME HOME
 * 2019年12月15日下午6:12:52
 */
public class DataException extends Exception {

	private static final long serialVersionUID = 7409265398864433351L;

	public DataException(String message) {
		super(message);
	}
}
