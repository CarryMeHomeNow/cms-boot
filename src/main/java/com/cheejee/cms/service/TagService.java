package com.cheejee.cms.service;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.pojo.Tag;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author CARRY ME HOME
 * 2019年12月22日下午3:30:34
 */
public interface TagService {

	/**
	 * 根据标签id获取标签对象
	 * 
	 * @param id
	 * @return 标签对象
	 */
	Tag getTagById(int id);

	/**
	 * 用标签名模糊查找标签，只匹配前段,查找结果分页显示。
	 * 
	 * @param pageNum  页码
	 * @param pageSize 每页的内容数
	 * @param name     模糊查找的名称
	 * @return 分页对象
	 */
	PageInfo<Tag> getTagByName(int pageNum, int pageSize, String name);

	/**
	 * 获取所有的标签，分页显示
	 * 
	 * @param pageNum  页码
	 * @param pageSize 每页的内容数
	 * @return 分页对象
	 */
	PageInfo<Tag> getTagAll(int pageNum, int pageSize);

	/**
	 * 添加单个标签
	 * 
	 * @param tag
	 * @return 添加成功返回true
	 * @throws DataDuplicationException 如果添加的标签数据库已存在则会抛出此异常
	 * @throws IncompleteException      添加的标签名称为空则会抛出此异常
	 */
	boolean addTag(Tag...tags) throws DataDuplicationException, IncompleteException;
	
	/**
	 * 管理删除标签
	 * @param tags
	 * @return
	 * @throws IncompleteException  对象信息不足
	 */
	void deleteTagByManage(Tag...tags) throws IncompleteException;
	
	/**
	 * 修改标签描述
	 * @param tag
	 * @return
	 * @throws DataDuplicationException 
	 * @throws IncompleteException 
	 * @throws NotFoundException 
	 */
	boolean changeTagDescribe(Tag tag) throws DataDuplicationException, IncompleteException, NotFoundException;
}
