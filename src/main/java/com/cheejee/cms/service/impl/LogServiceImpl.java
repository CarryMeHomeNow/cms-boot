package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.isNull;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import com.cheejee.cms.apply.LogApply;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.pojo.Log;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.service.LogService;
import com.github.pagehelper.PageInfo;

public class LogServiceImpl implements LogService {
	
	private LogApply la;
	
	public LogServiceImpl() {
		this.la = new LogApply();
	}
	

	@Override
	public Log getLogById(int id) {
		return la.selectLog(id);
	}

	@Override
	public PageInfo<Log> getLogBefore(int pageNum, int pageSize, Timestamp time) {
		
		isNull(time, "查找的时间为空");
		
		return la.selectLogByTime(pageNum, pageSize, new Timestamp(0), time);
	}

	@Override
	public PageInfo<Log> getLogAfter(int pageNum, int pageSize, Timestamp time) {
		
		isNull(time, "查找的时间为空");
		return la.selectLogByTime(pageNum, pageSize, time, Timestamp.from(Instant.now()));
	}

	@Override
	public PageInfo<Log> getLogBetween(int pageNum, int pageSize, Timestamp beginTime, Timestamp endTime) {
		
		isNull(beginTime, "查找的时间为空");
		isNull(endTime, "查找的时间为空");
		
		return la.selectLogByTime(pageNum, pageSize, beginTime, endTime);
	}

	@Override
	public PageInfo<Log> getLogByUser(int pageNum, int pageSize, User user) throws IncompleteException {
		
		isNull(user, "用户为空");
		checkId(user.getId(), "用户id为空");
		
		return la.selectLogByUser(pageNum, pageSize, user);
	}

	@Override
	public PageInfo<Log> getLogByModule(int pageNum, int pageSize, String module) {
		
		if(module == null || module.equals(""))
			throw new NullPointerException();
		
		return la.selectLogByModule(pageNum, pageSize, module);
	}

	@Override
	public PageInfo<Log> getLogByResult(int pageNum, int pageSize, String result) {
		
		if(result == null || result.equals(""))
			throw new NullPointerException();
		
		return la.selectLogByResult(pageNum, pageSize, result);
	}

	@Override
	public PageInfo<Log> getLogByIp(int pageNum, int pageSize, String ip) {
		
		if(ip == null || ip.equals(""))
			throw new NullPointerException();
		
		return la.selectLogByIP(pageNum, pageSize, ip);
	}

	@Override
	public PageInfo<Log> getLogByOperate(int pageNum, int pageSize, String operate) {
		
		if(operate == null || operate.equals(""))
			throw new NullPointerException();
		
		return la.selectLogByOperate(pageNum, pageSize, operate);
	}

	@Override
	public void addLog(Log log) throws IncompleteException {
		
		isNull(log, "日志为空");
		isNull(log.getUser(), "日志添加人为空");
		checkId(log.getUser().getId(), "日志添加人id为空");
		
		if(log.getMessage() == null && log.getModule() == null && log.getOperate() == null)
			throw new NullPointerException("添加的日志信息为空");

		la.addLog(log);
	}


	@Override
	public int deleteLogBefore(Timestamp time) {
		if(time == null) {
			return 0;
		}
		
		return la.deleteLogBefore(time);
	}


	@Override
	public int deleteLogBeforeDay(int day) {
		LocalDateTime date = LocalDateTime.now();
		
		return la.deleteLogBefore(Timestamp.valueOf(date.plusDays(-Math.abs(day))));
	}

}
