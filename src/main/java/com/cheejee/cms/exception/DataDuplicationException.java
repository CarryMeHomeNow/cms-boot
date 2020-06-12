package com.cheejee.cms.exception;

/**
 * 数据重复异常。
 * @author CARRY ME HOME
 * 2019年11月16日上午11:26:08
 */
public class DataDuplicationException extends Exception {

	private static final long serialVersionUID = 354814054135109549L;
	
	public DataDuplicationException(String message) {
		super(message);
	}
	
}
