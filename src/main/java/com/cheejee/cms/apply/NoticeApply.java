package com.cheejee.cms.apply;

import java.sql.Timestamp;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.NoticeDAO;
import com.cheejee.cms.pojo.Notice;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class NoticeApply {

	SqlSessionFactory ssf;
	
	public NoticeApply() {
		this.ssf = SSF.getSSF();
	}
	
	public Notice selectNotice(int id) {
		try(SqlSession session = ssf.openSession()){
			NoticeDAO nd = session.getMapper(NoticeDAO.class);
			return nd.selectNotice(id);
		}
	}
	
	public PageInfo<Notice> selectNoticeByUser(int pageNum, int pageSize, User user){
		try(SqlSession session = ssf.openSession()){
			NoticeDAO nd = session.getMapper(NoticeDAO.class);
			return new PageInfo<Notice>(nd.selectNoticeByUser(pageNum, pageSize, user));
		}
	}
	
	public PageInfo<Notice> listNotice(int pageNum, int pageSize){
		try(SqlSession session = ssf.openSession()){
			NoticeDAO nd = session.getMapper(NoticeDAO.class);
			return new PageInfo<Notice>(nd.listNotice(pageNum, pageSize));
		}
	}
	
	public PageInfo<Notice> selectNoticeByState(int pageNum, int pageSize, Integer state){
		try(SqlSession session = ssf.openSession()){
			NoticeDAO nd = session.getMapper(NoticeDAO.class);
			return new PageInfo<Notice>(nd.selectNoticeByState(pageNum, pageSize, state));
		}
	}
	
	public PageInfo<Notice> selectNoticeByTime(int pageNum, int pageSize, Timestamp beginTime, Timestamp endTime){
		try(SqlSession session = ssf.openSession()){
			NoticeDAO nd = session.getMapper(NoticeDAO.class);
			return new PageInfo<Notice>(nd.selectNoticeByTime(pageNum, pageSize, beginTime, endTime));
		}
	}
	
	public int addNotice(Notice...notices) {
		try(SqlSession session = ssf.openSession()){
			NoticeDAO nd = session.getMapper(NoticeDAO.class);
			int a = 0;
			
			for(Notice n : notices)
				a = a + nd.addNotice(n);
			
			session.commit();
			return a;
		}
	}
	
	public int deleteNotice(Notice notice) {
		try(SqlSession session = ssf.openSession(true)){
			NoticeDAO nd = session.getMapper(NoticeDAO.class);
			return nd.deleteNotice(notice);
		}
	}
	
	public int updateNotice(Notice notice) {
		try(SqlSession session = ssf.openSession(true)){
			NoticeDAO nd = session.getMapper(NoticeDAO.class);
			return nd.updateNotice(notice);
		}
	}
}
