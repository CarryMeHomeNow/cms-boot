package com.cheejee.cms.service;

import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.pojo.Notice;
import com.cheejee.cms.pojo.User;
import com.github.pagehelper.PageInfo;

/**
 * @author CARRY ME HOME
 * 2019年12月7日下午4:33:30
 */
@Deprecated
public interface NoticeService {

	/**
	 * 按id获取公告
	 * @param id 要获取的公告id
	 * @return
	 */
	Notice getNoticeById(int id);
	
	/**
	 * 获取指定用户发布的公告
	 * @param pageNum 
	 * @param pageSize
	 * @param user
	 * @return 分页对象
	 */
	PageInfo<Notice> getNoticeByUser(int pageNum, int pageSize, User user);
	
	/**
	 * 以分页形式获取所有的公告
	 * @param pageNum 第几页
	 * @param pageSize 每页内容数
	 * @return
	 */
	PageInfo<Notice> getAllNotice(int pageNum, int pageSize);
	
	
	/**
	 * 分页获取指定状态的公告
	 * @param state 状态值
	 * @return
	 */
	PageInfo<Notice> getNoticeByState(int pageNum, int pageSize, Integer state);
	
	/**
	 * 新增公告
	 * @param notice
	 * @return
	 * @throws IncompleteException 对象信息不足
	 */
	boolean addNotice(Notice...notices) throws IncompleteException;
	
	/**
	 * 管理删除公告
	 * @param notice
	 * @return 
	 * @throws NotFoundException 删除的公告数据库不存在则会抛出此异常
	 * @throws NullObjectException 删除的公告对象为空则会抛出此异常
	 * @throws IncompleteException 对象信息不足
	 */
	boolean deleteNoticeByManage(Notice notice) throws NotFoundException, IncompleteException;
	
	/**
	 * 管理更新公告
	 * @param notice
	 * @return
	 * @throws NullObjectException 更新的公告对象为空则会抛出此异常
	 * @throws NotFoundException 更新的公告对象在数据库不存在则会抛出此异常
	 * @throws IncompleteException 对象信息不足
	 */
	boolean changeNoticeByManage(Notice notice) throws NotFoundException, IncompleteException;
	

}
