package com.cheejee.cms.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Notice;
import com.cheejee.cms.pojo.User;

public interface NoticeDAO {

	Notice selectNotice(int id);

	List<Notice> selectNoticeByUser(int pageNum, int pageSize, @Param("user")User user);
	
	List<Notice> listNotice(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize);

	List<Notice> selectNoticeByTime(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize,
			@Param("begin") Timestamp beginTime, @Param("end") Timestamp endTime);
	
	List<Notice> selectNoticeByState(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize, @Param("state")Integer state);

	int addNotice(Notice notice);
	
	int deleteNotice(Notice notice);
	
	int updateNotice(Notice notice);

	
}
