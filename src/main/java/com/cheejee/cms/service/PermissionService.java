package com.cheejee.cms.service;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.pojo.Permission;
import com.cheejee.cms.pojo.Resource;
import com.cheejee.cms.pojo.Role;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author CARRY ME HOME
 * 2019年12月26日上午10:56:27
 */
public interface PermissionService {
	
	/**
	 * 根据id获取权限
	 * @param id
	 * @return
	 */
	Permission getPermissionById(int id);
	
	/**
	 * 获取指定角色对指定资源的权限
	 * @param role
	 * @param resource
	 * @return
	 * @throws IncompleteException
	 */
	Permission getPermisionByRoleAndResource(Role role, Resource resource) throws IncompleteException;
	
	/**
	 * 获取所有权限
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Permission> getPermissionAll(int pageNum, int pageSize);
	
	/**
	 * 添加权限，确保添加的权限包含角色对象和资源对象。如果权限值不合法，则会将权限值设置为打开（1）状态。
	 * @param permissions
	 * @return
	 * @throws IncompleteException
	 * @throws DataDuplicationException
	 */
	boolean addPermission(Permission...permissions) throws IncompleteException, DataDuplicationException;
	
	/**
	 * 删除权限
	 * @param permissions
	 * @throws IncompleteException
	 */
	void deletePermission(Permission...permissions) throws IncompleteException;
	
	/**
	 * 将权限设置为关闭状态，禁用角色对资源的访问。
	 * @param permissions
	 * @throws IncompleteException
	 */
	void closePermission(Permission...permissions) throws IncompleteException;
	
	/**
	 * 将权限设置为打开状态，如果权限状态就是打开状态则无影响。
	 * @param permissions
	 * @throws IncompleteException
	 */
	void openPermission(Permission...permissions) throws IncompleteException;

}
