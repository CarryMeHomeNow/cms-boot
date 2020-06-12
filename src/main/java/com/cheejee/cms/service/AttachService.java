package com.cheejee.cms.service;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.NotSupportedException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.pojo.Type;
import com.cheejee.cms.pojo.User;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author CARRY ME HOME 2019年12月11日下午12:26:39
 */
public interface AttachService {

	/**
	 * 管理员通过id获取附件对象
	 * 
	 * @param id
	 * @return
	 */
	Attach getAttachByIdByManage(int id);

	
	/**
	 * 通过id获取附件
	 * @param id
	 * @param user 当前登录用户
	 * @return
	 * @throws IncompleteException 对象数据不足
	 */
	Attach getAttachById(int id, User user) throws IncompleteException;
	

	/**
	 * 管理员使用名称模糊查找附件
	 * 
	 * @param pageNum  页码
	 * @param pageSize 每页内容数
	 * @param name     附件名称
	 * @return 分页对象
	 */
	PageInfo<Attach> getAttachByNameManage(int pageNum, int pageSize, String name);
	
	
	/**
	 * 使用名称模糊查找附件，只会查找当前用户的附件
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param user 当前登录用户
 	 * @return 分页对象
	 * @throws IncompleteException 对象数据不足
	 */
	PageInfo<Attach> getAttachByName(int pageNum, int pageSize, String name, User user) throws IncompleteException;

	/**
	 * 管理员获取指定类型的附件
	 * 
	 * @param pageNum  页码
	 * @param pageSize 每页内容数
	 * @param type     附件类型
	 * @return 分页对象
	 * @throws IncompleteException 对象数据不全
	 */
	PageInfo<Attach> getAttachByTypeManage(int pageNum, int pageSize, Type type) throws IncompleteException;
	
	/**
	 * 获取指定类型的附件， 只会获取当前用户的附件
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @param user 当前登录用户
	 * @return 分页对象
	 * @throws IncompleteException 对象数据不全
	 */
	PageInfo<Attach> getAttachByType(int pageNum, int pageSize, Type type, User user) throws IncompleteException;

	/**
	 * 获取指定用户的所有附件
	 * 
	 * @param pageNum  页码
	 * @param pageSize 每页内容数
	 * @param user     附件所属用户
	 * @return 分页对象
	 * @throws IncompleteException 对象数据不足
	 * @throws NotFoundException 用户不存在
	 */
	PageInfo<Attach> getAttachByUser(int pageNum, int pageSize, User user) throws IncompleteException, NotFoundException;

	/**
	 * 获取所有的附件
	 * 
	 * @param pageNum  页码
	 * @param pageSize 每页内容数
	 * @return 分页对象
	 */
	PageInfo<Attach> getAttachAllByManage(int pageNum, int pageSize);

	/**
	 * 添加附件，添加时会检查附件格式是否受支持。格式正确且受系统支持的附件，会添加附件的后缀属性。
	 * 添加附件时需要保证附件的url格式正确。
	 * 
	 * @param attachs
	 * @param user 当前登录用户
	 * @return
	 * @throws DataDuplicationException 存在相同url的附件时会抛出此异常
	 * @throws InsufficientPermissionException 添加了不属于自己的附件
	 * @throws IncompleteException 对象信息不全
	 * @throws NotSupportedException 添加的附件格式不受系统支持
	 */
	boolean addAttach(User user, Attach...attachs) throws DataDuplicationException, InsufficientPermissionException, IncompleteException, NotSupportedException;

	/**
	 * 删除附件，只能删除没有使用的附件
	 * 
	 * @param attachs
	 * @param user 当前登录的用户
	 * @return
	 * @throws NotFoundException 要删除的附件不存在则会抛出此异常
	 * @throws IncompleteException 对象信息不足
	 * @throws InsufficientPermissionException 权限不足
	 * @throws OperationsException 还有内容使用该附件
	 */
	void deleteAttachs(User user, Attach...attachs) throws NotFoundException, IncompleteException, InsufficientPermissionException, OperationsException;
	
	/**
	 * 删除附件
	 * 
	 * @param attachs
	 * @param user 当前登录的用户
	 * @return
	 * @throws NotFoundException 要删除的附件不存在则会抛出此异常
	 * @throws IncompleteException 对象信息不足
	 * @throws InsufficientPermissionException 权限不足
	 * @throws OperationsException 还有内容使用该附件
	 */
	void deleteAttachsForce(User user, Attach...attachs) throws NotFoundException, IncompleteException, InsufficientPermissionException, OperationsException;
	
	/**
	 * 管理删除附件，只能删除没有使用的附件
	 * @param attachs
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws OperationsException 附件还在使用
	 */
	void deleteAttachsByManage(Attach...attachs) throws IncompleteException, OperationsException;
	
	/**
	 * 管理员强制删除附件
	 * @param attachs
	 * @return
	 * @throws IncompleteException 对象信息不足
	 */
	boolean deleteAttachsByManageForce(Attach...attachs) throws IncompleteException;

	/**
	 * 更新附件，只能更改名称和url。
	 * 
	 * @param attach
	 * @param user 当前登录的用户
	 * @return
	 * @throws DataDuplicationException 修改后的url在数据库中已存在则会抛出此异常
	 * @throws NotFoundException 要修改的附件不存在则会抛出此异常
	 * @throws IncompleteException 对象信息不全
	 * @throws InsufficientPermissionException 权限不足
	 * @throws DataException 对象数据和数据库不匹配
	 * @throws NotSupportedException 
	 */
	boolean changeAttach(Attach attach, User user) throws DataDuplicationException, NotFoundException, IncompleteException, InsufficientPermissionException, DataException, NotSupportedException;
	
	/**
	 * 管理员修改附件
	 * @param attach
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws DataDuplicationException 数据重复
	 * @throws NotFoundException 附件不存在。
	 * @throws NotSupportedException 附件后缀不受支持
	 */
	boolean changeAttachByManage(Attach attach) throws IncompleteException, DataDuplicationException, NotFoundException, NotSupportedException;
	
	/**
	 * 检查附件是否被系统所支持。根据附件的url进行判断，如果url的格式不正确会返回false。
	 * @param attachs
	 * @return 附件可以使用返回true
	 * @throws IncompleteException 
	 */
	boolean checkAttachIsSupported(Attach...attachs) throws IncompleteException;
}
