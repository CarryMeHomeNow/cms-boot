package com.cheejee.cms.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Log;
import com.cheejee.cms.pojo.User;

public interface LogDAO {
	
	Log selectLog(int id);
	
	List<Log> selectLogByTime(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize,
			@Param("beginTime") Timestamp beginTime, @Param("endTime") Timestamp endTime);

	
	List<Log> selectLogByUser(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize, @Param("user")User user);
	
	List<Log> selectLogByModule(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize, @Param("module")String module);
	
	List<Log> selectLogByResult(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize, @Param("result")String result);
	
	List<Log> selectLogByIpExact(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize, @Param("ip")byte[] ip);
	
	List<Log> selectLogByIp(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize, @Param("ip")byte[] ip);
	
	List<Log> selectLogByOperate(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize, @Param("operate")String operate);
	
	List<Log> listLog(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize);
	
	int addLog(Log log);
	
	int deleteLogBefore(@Param("time") Timestamp time);
	
}
