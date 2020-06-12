package com.cheejee.cms.service;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.pojo.Zone;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author CARRY ME HOME 2019年12月10日下午3:28:01
 */
@Deprecated
public interface ZoneService {
	
	//TODO: 专栏添加文章时的审批流程。
	//TODO: 独立专栏，专栏有自己的文章表。
	//TODO:剔除系统中专栏有关

	/**
	 * 根据id获取专栏对象
	 * 
	 * @param id
	 * @return
	 */
	Zone getZoneById(int id);

	/**
	 * 使用名称模糊查询专栏
	 * 
	 * @param pageNum  页码
	 * @param pageSize 每页内容数
	 * @param name     名称
	 * @return 分页对象
	 */
	PageInfo<Zone> getZoneByName(int pageNum, int pageSize, String name);

	/**
	 * 获取所有的专栏
	 * 
	 * @param pageNum  页码
	 * @param pageSize 每页内容数
	 * @return 分页对象
	 */
	PageInfo<Zone> getZoneAll(int pageNum, int pageSize);

	/**
	 * 添加专栏
	 * 
	 * @param zone
	 * @return 添加成功返回true
	 * @throws DataDuplicationException 添加的专栏名称已存在则会抛出此异常
	 * @throws IncompleteException      添加的专栏信息不完整
	 * @throws InsufficientPermissionException 专栏创建者非当前用户
	 */
	boolean addZone(Zone zone, User user) throws DataDuplicationException, IncompleteException, InsufficientPermissionException;
	
	/**
	 * 管理员添加内容到专栏
	 * @param zone
	 * @param contents
	 * @return
	 */
	boolean addContentToZoneByManage(Zone zone, Content...contents);
	
	/**
	 * 添加内容到专栏
	 * @param zone
	 * @param user 当前登录用户
	 * @param contents
	 * @return
	 * @throws NotFoundException 专栏不存在
	 * @throws IncompleteException 对象信息不全
	 * @throws InsufficientPermissionException 权限不足
	 */
	boolean addContentToZone(Zone zone, User user, Content...contents) throws NotFoundException, IncompleteException, InsufficientPermissionException;
	
	/**
	 * 管理删除专栏的内容
	 * @param zone
	 * @param contents
	 * @return
	 */
	boolean deleteContentToZoneByManage(Zone zone, Content...contents);
	
	/**
	 * 删除专栏内容
	 * @param zone
	 * @param user 当前登录用户
	 * @param contents
	 * @return
	 * @throws NotFoundException 专栏不存在
	 * @throws IncompleteException 对象信息不全
	 * @throws InsufficientPermissionException 权限不足
	 */
	boolean deleteContentToZone(Zone zone, User user, Content...contents) throws NotFoundException, IncompleteException, InsufficientPermissionException;

	/**
	 * 管理员删除专栏。
	 * 
	 * @param zone
	 * @return 删除成功返回true
	 * @throws NotFoundException 删除的专栏不存在
	 */
	boolean deleteZoneByManage(Zone zone) throws NotFoundException;
	
	/**
	 * 删除专栏
	 * @param zone
	 * @param user 当前登录用户
	 * @return
	 * @throws NotFoundException 数据不存在
	 * @throws IncompleteException 对象信息不全
	 * @throws InsufficientPermissionException 权限不足
	 * @throws NullPointerException 需要使用的对象为空
	 */
	boolean deleteZone(Zone zone, User user) throws NotFoundException, IncompleteException, NullPointerException, InsufficientPermissionException;

	/**
	 * 事务更新专栏自身属性和专栏的管理员。
	 * 
	 * @param zone
	 * @return 全部更新成功返回true
	 * @throws DataDuplicationException 修改的专栏名称已存在则会抛出此异常
	 * @throws NotFoundException 更新的专栏不存在数据库
	 * @throws IncompleteException 对象信息不足
	 */
	boolean changeZoneByManage(Zone zone) throws DataDuplicationException, NotFoundException, IncompleteException;
	
	/**
	 * 修改专栏，会更新专栏的管理员，如果管理员不为空的话。
	 * @param zone
	 * @param user
	 * @return
	 * @throws IncompleteException
	 * @throws InsufficientPermissionException
	 * @throws NotFoundException
	 * @throws DataDuplicationException
	 */
	boolean changeZone(Zone zone, User user) throws IncompleteException, InsufficientPermissionException, NotFoundException, DataDuplicationException;
	
	/**
	 * 管理添加专栏的管理员
	 * @param zone
	 * @param admins
	 * @return
	 * @throws IncompleteException
	 */
	boolean addZoneAdminByManage(Zone zone, User...admins) throws IncompleteException;
	
	/**
	 * 管理删除专栏的管理员，如果删除的管理员不存在则会返回false
	 * @param zone
	 * @param admins
	 * @return
	 * @throws IncompleteException
	 */
	boolean deleteZoneAdminByManage(Zone zone, User...admins) throws IncompleteException;
	
	/**
	 * 管理更改专栏的管理员
	 * @param zone
	 * @return
	 * @throws IncompleteException
	 * @throws DataDuplicationException
	 */
	boolean changeZoneAdminByManage(Zone zone) throws IncompleteException, DataDuplicationException;
	
	/**
	 * 添加专栏管理员
	 * @param zone
	 * @param user 当前用户
	 * @param admins
	 * @return
	 * @throws IncompleteException
	 * @throws InsufficientPermissionException
	 */
	boolean addZoneAdmin(Zone zone, User user, User...admins) throws IncompleteException, InsufficientPermissionException;
	
	/**
	 * 删除专栏管理员，只能对自己的专栏操作
	 * @param zone
	 * @param user
	 * @param admins
	 * @return
	 * @throws IncompleteException
	 * @throws InsufficientPermissionException
	 */
	boolean deleteZoneAdmin(Zone zone, User user, User...admins) throws IncompleteException, InsufficientPermissionException;
	
	/**
	 * 更改专栏的管理员
	 * @param zone
	 * @param user
	 * @return
	 * @throws IncompleteException
	 * @throws InsufficientPermissionException
	 * @throws DataDuplicationException
	 */
	boolean changeZoneAdmin(Zone zone, User user) throws IncompleteException, InsufficientPermissionException, DataDuplicationException;
}
