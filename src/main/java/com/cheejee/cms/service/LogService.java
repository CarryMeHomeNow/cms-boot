package com.cheejee.cms.service;

import java.sql.Timestamp;

import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.pojo.Log;
import com.cheejee.cms.pojo.User;
import com.github.pagehelper.PageInfo;

//TODO: 日志分类
//TODO: 异常处理，不同的层使用不同的异常。最好抛出java自带异常

/**
 * @author CARRY ME HOME
 * 2019年12月19日上午10:23:45
 */
public interface LogService {
	
	/**
	 * 根据日志id获取日志
	 * @param id
	 * @return
	 */
	Log getLogById(int id);
	
	/**
	 * 获取指定时间之前创建的日志信息
	 * @param pageNum
	 * @param pageSize
	 * @param time
	 * @return
	 */
	PageInfo<Log> getLogBefore(int pageNum, int pageSize, Timestamp time);
	
	/**
	 * 获取指定时间之后创建的日志信息
	 * @param pageNum
	 * @param pageSize
	 * @param time
	 * @return
	 */
	PageInfo<Log> getLogAfter(int pageNum, int pageSize, Timestamp time);
	
	/**
	 * 获取从beginTime到endTime创建的日志信息
	 * @param pageNum
	 * @param pageSize
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	PageInfo<Log> getLogBetween(int pageNum, int pageSize, Timestamp beginTime, Timestamp endTime);
	
	/**
	 * 获取指定用户所创建的日志
	 * @param pageNum
	 * @param pageSize
	 * @param user
	 * @return
	 * @throws IncompleteException 用户对象信息不足
	 */
	PageInfo<Log> getLogByUser(int pageNum, int pageSize, User user) throws IncompleteException;
	
	/**
	 * 获取操作指定模块的日志信息
	 * @param pageNum
	 * @param pageSize
	 * @param module
	 * @return
	 */
	PageInfo<Log> getLogByModule(int pageNum, int pageSize, String module);
	
	/**
	 * 获取指定结果的日志信息
	 * @param pageNum
	 * @param pageSize
	 * @param result
	 * @return
	 */
	PageInfo<Log> getLogByResult(int pageNum, int pageSize, String result);
	
	/**
	 * 获取指定ip的日志信息
	 * @param pageNum
	 * @param pageSize
	 * @param ip
	 * @return
	 */
	PageInfo<Log> getLogByIp(int pageNum, int pageSize, String ip);
	
	/**
	 * 获取指定操作的日志信息
	 * @param pageNum
	 * @param pageSize
	 * @param operate
	 * @return
	 */
	PageInfo<Log> getLogByOperate(int pageNum, int pageSize, String operate);
	
	/**
	 * 添加日志，如果日志的message， module 和 operate都为空的话，则视为空日志，将会抛出空指针异常。
	 * @param log
	 * @throws IncompleteException 对象信息不足。
	 */
	void addLog(Log log) throws IncompleteException;
	
	/**
	 * 删除time之前创建的日志
	 * @param time
	 * @return
	 */
	int deleteLogBefore(Timestamp time);
	
	/**
	 * 删除day天之前的日志
	 * @param day 天数
	 * @return
	 */
	int deleteLogBeforeDay(int day);
}
