package com.cheejee.cms.service;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Resource;
import com.cheejee.cms.pojo.Role;
import com.github.pagehelper.PageInfo;

/**
 * 必须存在的三个角色：id=1 普通用户， id=2 超管， id=3 管理员（用于判断是否能登录系统后台）
 * @author CARRY ME HOME 2019年12月19日下午4:20:31
 */
public interface RoleService {
	
	/**
	 * 根据id获取角色对象
	 * @param id
	 * @return
	 */
	Role getRoleById(int id);
	
	/**
	 * 获取用于编辑的角色对象
	 * @param id
	 * @return
	 */
	Role getRoleEditById(int id);

	/**
	 * 根据名称模糊查找角色
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @return
	 */
	PageInfo<Role> getRoleByName(int pageNum, int pageSize, String name);

	/**
	 * 获取所有角色
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Role> getRoleAll(int pageNum, int pageSize);

	/**
	 * 添加角色，事务添加角色拥有的权限
	 * 
	 * @param role
	 * @return
	 * @throws DataDuplicationException 添加的角色名称数据库已存在
	 * @throws IncompleteException 对象信息不足
	 */
	boolean addRole(Role role) throws DataDuplicationException, IncompleteException;

	/**
	 * 删除角色，如果这个角色还有人使用则抛出异常。
	 * 
	 * @param role
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws OperationsException 删除的角色还有人使用
	 */
	boolean deleteRole(Role role) throws IncompleteException, OperationsException;

	/**
	 * 强制删除角色，如果这个角色存在以下情况，则该角色不能被删除：<br>
	 * 1、存在某个用户只拥有这一个角色。 <br>
	 * 2、这个角色是默认角色。<br>
	 * 3、这个角色是超管角色（权限最大）。<br>
	 * 4、存在某个组只拥有这一个角色。<br>
	 * 删除角色时事务删除角色的所有权限。
	 * @param role
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws OperationsException 角色不能被删除
	 */
	boolean deleteRoleForce(Role role) throws IncompleteException, OperationsException;
	
	/**
	 * 修改角色，会修改角色拥有的权限。
	 * @param role
	 * @return
	 * @throws DataDuplicationException 修改的角色名称已存在
	 * @throws IncompleteException 角色信息不足
	 * @throws NotFoundException 角色不存在
	 */
	boolean changeRole(Role role) throws DataDuplicationException, IncompleteException, NotFoundException;

	/**
	 * 添加角色能访问的资源
	 * @param role
	 * @param resources
	 * @return
	 * @throws DataDuplicationException 数据重复
	 * @throws IncompleteException 对象信息不足
	 */
	boolean addRoleAccessResource(Role role, Resource...resources) throws DataDuplicationException, IncompleteException;
	
	/**
	 * 删除角色能访问的资源
	 * @param role
	 * @param resources
	 * @return
	 * @throws IncompleteException 
	 */
	void deleteRoleAccessResource(Role role, Resource...resources) throws IncompleteException;
	
	/**
	 * 禁用角色对某些资源的访问权限。禁用之后拥有该角色的用户将无法访问这些资源。
	 * @param role
	 * @param resources
	 * @return
	 * @throws IncompleteException 对象信息不足
	 */
	void disableRoleAccessResource(Role role, Resource...resources) throws IncompleteException;
	
	/**
	 * 启用角色对资源的访问，只会启用被禁用的资源。
	 * @param role
	 * @param resources
	 * @throws IncompleteException
	 */
	void enableRoleAccessResource(Role role, Resource...resources) throws IncompleteException;
	
	
}
