package com.cheejee.cms.apply;

import java.sql.Timestamp;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.LogDAO;
import com.cheejee.cms.pojo.Log;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.tools.ConvertIP;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class LogApply {

	SqlSessionFactory ssf;
	
	public LogApply() {
		this.ssf = SSF.getSSF();
	}
	
	public PageInfo<Log> listLog(int pageNum, int pageSize){
		try(SqlSession session = ssf.openSession()){
			LogDAO ld = session.getMapper(LogDAO.class);
			return new PageInfo<Log>(ld.listLog(pageNum, pageSize));
		}
	}
	
	public Log selectLog(int id) {
		try(SqlSession session = ssf.openSession()){
			LogDAO ld = session.getMapper(LogDAO.class);
			return ld.selectLog(id);
		}
	}
	
	public PageInfo<Log> selectLogByUser(int pageNum, int pageSize, User user){
		try(SqlSession session = ssf.openSession()){
			LogDAO ld = session.getMapper(LogDAO.class);
			return new PageInfo<Log>(ld.selectLogByUser(pageNum, pageSize, user));
		}
	}
	
	public PageInfo<Log> selectLogByModule(int pageNum, int pageSize, String module){
		try(SqlSession session = ssf.openSession()){
			LogDAO ld = session.getMapper(LogDAO.class);
			return new PageInfo<Log>(ld.selectLogByModule(pageNum, pageSize, module));
		}
	}
	
	public PageInfo<Log> selectLogByResult(int pageNum, int pageSize, String result){
		try(SqlSession session = ssf.openSession()){
			LogDAO ld = session.getMapper(LogDAO.class);
			return new PageInfo<Log>(ld.selectLogByResult(pageNum, pageSize, result));
		}
	}
	
	public PageInfo<Log> selectLogByOperate(int pageNum, int pageSize, String operate){
		try(SqlSession session = ssf.openSession()){
			LogDAO ld = session.getMapper(LogDAO.class);
			return new PageInfo<Log>(ld.selectLogByOperate(pageNum, pageSize, operate));
		}
	}
	
	public PageInfo<Log> selectLogByTime(int pageNum, int pageSize, Timestamp beginTime, Timestamp endTime){
		try(SqlSession session = ssf.openSession()){
			LogDAO ld = session.getMapper(LogDAO.class);
			return new PageInfo<Log>(ld.selectLogByTime(pageNum, pageSize, beginTime, endTime));
		}
	}
	
	public PageInfo<Log> selectLogByIPExact(int pageNum, int pageSize, String ip){
		try(SqlSession session = ssf.openSession()){
			LogDAO ld = session.getMapper(LogDAO.class);
			return new PageInfo<Log>(ld.selectLogByIpExact(pageNum, pageSize, ConvertIP.toBinary(ip)));
		}
	}
	
	/**
	 * 按ip段查ip，只会匹配以指定ip段为开头的ip。传入的ip地址需要以完整形式展现，不能传入省略形式。
	 * @param pageNum
	 * @param pageSize
	 * @param ip 需要查找的ip段，格式应该类似于：192.168. 或  2409:8a1e:6852:
	 * @return
	 */
	public PageInfo<Log> selectLogByIP(int pageNum, int pageSize, String ip){
		try(SqlSession session = ssf.openSession()){
			LogDAO ld = session.getMapper(LogDAO.class);
			String ipp = ConvertIP.appendIP(ip);
			return new PageInfo<Log>(ld.selectLogByIp(pageNum, pageSize, ConvertIP.toBinary(ipp)));
		}
	}
	
	public int addLog(Log log) {
		try(SqlSession session = ssf.openSession(true)){
			LogDAO ld = session.getMapper(LogDAO.class);
			return ld.addLog(log);
		}
	}
	
	public int deleteLogBefore(Timestamp time) {
		try(SqlSession session = ssf.openSession(true)){
			LogDAO ld = session.getMapper(LogDAO.class);
			return ld.deleteLogBefore(time);
		}
	}
	    
}
