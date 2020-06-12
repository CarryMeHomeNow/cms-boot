package com.cheejee.cms.service;

import java.util.List;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Group;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;
import com.github.pagehelper.PageInfo;

/**
 * 分组操作仅开放给后台使用
 * 
 * @author CARRY ME HOME 2019年12月18日下午2:56:39
 */
public interface GroupService {

	/**
	 * 根据id获取分组对象
	 * 
	 * @param id
	 * @return
	 */
	Group getGroupById(int id);

	/**
	 * 获取用户编辑的分组对象
	 * 
	 * @param id
	 * @return
	 */
	Group getGroupEditById(int id);

	/**
	 * 使用名称模糊查询分组
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @return 分页对象
	 */
	PageInfo<Group> getGroupByName(int pageNum, int pageSize, String name);

	/**
	 * 获取没有父组的分组
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Group> getGroupNoParent(int pageNum, int pageSize);

	/**
	 * 获取分组的子组。
	 * 
	 * @param group
	 * @return
	 * @throws NotFoundException   分组不存在
	 * @throws IncompleteException 对象信息不足
	 */
	List<Group> getGroupSon(Group group) throws NotFoundException, IncompleteException;

	/**
	 * 获取所有分组
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return 分页对象
	 */
	PageInfo<Group> getGroupAll(int pageNum, int pageSize);

	/**
	 * 获取分组的继承路径
	 * 
	 * @param group
	 * @return
	 * @throws IncompleteException 分组对象没有id
	 */
	@Deprecated
	String getGroupPath(Group group) throws IncompleteException;

	/**
	 * 添加分组
	 * 
	 * @param groups
	 * @return
	 * @throws NullPointerException     添加的对象为空
	 * @throws IncompleteException      对象信息不足
	 * @throws DataDuplicationException 分组名称已存在
	 */
	boolean addGroup(Group... groups) throws NullPointerException, IncompleteException, DataDuplicationException;

	/**
	 * 删除分组， 如果分组此分组下有子分组将会抛出一个异常
	 * 
	 * @param group
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws OperationsException 分组下有子分组
	 */
	void deleteGroup(Group group) throws IncompleteException, OperationsException;

	/**
	 * 修改分组，此操作可能会修改分组的用户和角色。
	 * 
	 * @param group
	 * @return
	 * @throws IncompleteException      对象信息不全
	 * @throws DataDuplicationException 修改的名称重复
	 * @throws OperationsException      修改分组的用户或者角色时失败
	 * @throws NotFoundException        分组不存在
	 */
	boolean changeGroup(Group group)
			throws IncompleteException, DataDuplicationException, OperationsException, NotFoundException;

	/**
	 * 添加分组中的成员
	 * 
	 * @param group
	 * @param users
	 * @return
	 * @throws IncompleteException      对象信息不足
	 * @throws NotFoundException        分组不存在
	 * @throws DataDuplicationException 添加的成员已存在。
	 */
	boolean addGroupUser(Group group, User... users)
			throws IncompleteException, NotFoundException, DataDuplicationException;

	/**
	 * 踢出指定分组的成员
	 * 
	 * @param group
	 * @param users
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws NotFoundException   分组不存在
	 */
	void deleteGroupUser(Group group, User... users) throws IncompleteException, NotFoundException;

	/**
	 * 修改指定分组的成员， 新的成员在分组对象中取得
	 * 
	 * @param group
	 * @return
	 * @throws IncompleteException      对象信息不足
	 * @throws NotFoundException        分组不存在
	 * @throws DataDuplicationException 数据重复
	 */
	boolean changeGroupUser(Group group) throws IncompleteException, NotFoundException, DataDuplicationException;

	/**
	 * 添加分组的角色
	 * 
	 * @param group
	 * @param roles
	 * @return
	 * @throws IncompleteException      对象信息不足
	 * @throws NotFoundException        分组不存在
	 * @throws DataDuplicationException 添加的分组已存在
	 */
	boolean addGroupRole(Group group, Role... roles)
			throws IncompleteException, NotFoundException, DataDuplicationException;

	/**
	 * 减少分组的角色
	 * 
	 * @param group
	 * @param roles
	 * @return
	 * @throws IncompleteException      对象信息不足
	 * @throws NotFoundException        分组不存在
	 * @throws DataDuplicationException
	 */
	boolean deleteGroupRole(Group group, Role... roles)
			throws IncompleteException, NotFoundException, DataDuplicationException;

	/**
	 * 修改分组的角色， 新的角色在分组对象中取得
	 * 
	 * @param group
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws NotFoundException   分组不存在
	 */
	boolean changeGroupRole(Group group) throws IncompleteException, NotFoundException;

	/**
	 * 修改分组的父组，新的父组从分组对象中取得
	 * 
	 * @param group
	 * @return
	 * @throws IncompleteException 对象信息不足。
	 * @throws OperationsException 父组不合法。
	 * @throws NotFoundException 数据不存在
	 */
	boolean changeGroupParent(Group group) throws IncompleteException, OperationsException, NotFoundException;

}
