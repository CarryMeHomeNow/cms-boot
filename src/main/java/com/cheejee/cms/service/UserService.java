package com.cheejee.cms.service;


import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;
import com.github.pagehelper.PageInfo;

/**
 * @author CARRY ME HOME
 * 2019年12月9日下午4:13:21
 */
public interface UserService {
	
	/**
	 * 登录成功返回用户对象，失败返回null。
	 * @param user
	 * @return
	 * @throws IncompleteException 对象属性不足
	 * @throws OperationsException 设置用户状态时失败
	 * @throws DataDuplicationException 设置用户状态时失败
	 */
	User login(User user) throws IncompleteException, DataDuplicationException, OperationsException;
	
	/**
	 * 后台管理员登录， 如果登录的账户不是管理员则会抛出异常。
	 * @param user
	 * @return
	 * @throws IncompleteException 对象属性不足
	 * @throws OperationsException 设置用户状态时失败或者登录的用户不是管理员。
	 * @throws DataDuplicationException 设置用户状态时失败
	 */
	User loginAdmin(User user) throws IncompleteException, DataDuplicationException, OperationsException;
	
	/**
	 * 用户退出登录，设置用户状态为未登录。
	 * 状态值为1
	 * @param user
	 * @throws IncompleteException 对象信息不足
	 * @throws OperationsException 更新用户状态值失败
	 * @throws DataDuplicationException 更新用户状态值失败
	 * @throws NotFoundException 用户信息不存在。
	 */
	void logout(User user) throws IncompleteException, DataDuplicationException, OperationsException, NotFoundException;
	
	/**
	 * 用户注销账户。设置用户状态为已注销。把状态值设置为99。
	 * @param user
	 * @throws IncompleteException 对象信息不足
	 * @throws OperationsException 更新用户状态值失败
	 * @throws DataDuplicationException 更新用户状态值失败
	 * @throws NotFoundException 用户不存在。
	 */
	void nullify(User user) throws IncompleteException, DataDuplicationException, OperationsException, NotFoundException;
	
	/**
	 * 管理根据id查找用户对象
	 * @param id
	 * @return
	 */
	User getUserByIdManage(int id);
	
	String getPasswordByUserName(String name);
	
	/**
	 * 普通用户根据id获取对象,获取到的用户对象没有状态信息和角色信息。有用户的个人信息。
	 * @param id
	 * @return
	 */
	User getUserById(int id);
	
	/**
	 * 管理员获取用于编辑的用户对象。
	 * @param id
	 * @return
	 */
	User getUserEditByIdManage(int id);
	
	/**
	 * 获取用于编辑的用户对象，获取到的用户没有状态信息
	 * @param id
	 * @return
	 */
	User getUserEditById(int id);
	
	/**
	 * 根据用户名精确查找用户对象
	 * @param name 用户名
	 * @return 
	 */
	User getUserByName(String name);
	
	/**
	 * 使用用户名模糊查找用户
	 * @param pageNum 页码
	 * @param pageSize 每页的内容数
	 * @param name 查找的用户名
	 * @return 分页对象
	 */
	PageInfo<User> getUserByName(int pageNum, int pageSize, String name);
	
	/**
	 * 获取所有的管理员
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<User> getAllAdmin(int pageNum, int pageSize);
	
	/**
	 * 获取所有的用户
	 * @param pageNum 页码
	 * @param pageSize 每页内容数
	 * @return 分页对象
	 */
	PageInfo<User> getUserAllByManage(int pageNum, int pageSize);
	
	/**
	 * 获取指定状态的用户
	 * @param pageNum 页码
	 * @param pageSize 每页内容数
	 * @param state 状态
	 * @return 分页对象
	 */
	PageInfo<User> getUserByStateByManage(int pageNum, int pageSize, Integer state);
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 * @throws DataDuplicationException 添加的用户名数据库已存在抛出此异常。 
	 * @throws IncompleteException 添加的用户信息不全则会抛出此异常 
	 * @throws OperationsException 添加用户的个人信息失败则会抛出此异常
	 */
	boolean register(User user) throws DataDuplicationException, IncompleteException, OperationsException;
	
	/**
	 * 更新用户。更新用户个人信息和用户所拥有的角色。不会更新用户名和用户密码
	 * @param user
	 * @return
	 * @throws DataDuplicationException 修改的用户名在数据库已存在抛出此异常。
	 * @throws NotFoundException 用户对象为空则会抛出此异常
	 * @throws OperationsException 更新用户角色或者个人信息时失败则会抛出此异常
	 * @throws IncompleteException 对象信息不全
	 */
	boolean changeUserByManage(User user) throws DataDuplicationException, NotFoundException, OperationsException, IncompleteException;
	
	/**
	 * 修改用户的个人信息，如果传入的用户对象没有个人信息则会抛出空指针异常
	 * @param user
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws NotFoundException 用户不存在
	 */
	boolean changePersonalInfo(User user) throws IncompleteException, NotFoundException;
	
	/**
	 * 修改用户的状态
	 * @param user
	 * @return
	 * @throws IncompleteException 对象数据不足
	 * @throws NotFoundException 用户不存在
	 * @throws OperationsException 操作失败
	 */
	boolean changeUserStateByManage(User user) throws IncompleteException, NotFoundException, OperationsException;
	
	/**
	 * 添加用户的角色
	 * @param user
	 * @param roles
	 * @return
	 * @throws IncompleteException 对象信息不全
	 * @throws OperationsException 操作失败
	 * @throws NotFoundException 数据不存在
	 * @throws DataDuplicationException 
	 */
	boolean addUserRoleByManage(User user, Role...roles) throws IncompleteException, OperationsException, NotFoundException, DataDuplicationException;
	
	/**
	 * 减少用户的角色
	 * @param user
	 * @param roles
	 * @return
	 * @throws IncompleteException 对象信息不全
	 * @throws NotFoundException 数据不存在
	 * @throws OperationsException 操作失败
	 */
	boolean deleteUserRoleByManage(User user, Role...roles) throws IncompleteException, NotFoundException, OperationsException;
	
	/**
	 * 修改用户的角色。
	 * @param user
	 * @return
	 * @throws IncompleteException 对象信息不全
	 * @throws NotFoundException 数据不存在。
	 * @throws OperationsException 操作失败
	 * @throws DataDuplicationException 
	 */
	boolean changeUserRoleByManage(User user) throws IncompleteException, NotFoundException, OperationsException, DataDuplicationException;	
	
	/**
	 * 新密码从对象属性取得
	 * @param user
	 * @param oldPass 旧密码
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws DataException 密码错误
	 * @throws NoSuchAlgorithmException 密码加密方式不支持
	 */
	boolean changePassword(User user, String oldPass) throws IncompleteException, DataException, NoSuchAlgorithmException;
	
	/**
	 * 发送验证码，不向已注销的（状态值99）用户发送
	 * @param user
	 * @return
	 */
	boolean sendVerification(User user);
	
	/**
	 * 校验验证码，通过则会设置新密码。
	 * @param user
	 * @param userCode 验证码
	 * @param sysCode 系统的验证码
	 * @param newPass 新密码
	 * @return
	 * @throws DataException 验证码错误
	 * @throws IncompleteException 对象信息不足
	 * @throws NoSuchAlgorithmException 密码加密方式不受支持
	 */
	boolean retrievePassword(User user, String userCode, String sysCode, String newPass) throws DataException, IncompleteException, NoSuchAlgorithmException;
	
	/**
	 * 设置用户为管理员
	 * @param users
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws NotFoundException 用户不存在
	 * @throws OperationsException 添加的用户已是管理员
	 */
	boolean setAdmin(User...users) throws IncompleteException, NotFoundException, OperationsException;
	
	/**
	 * 取消管理员
	 * @param users
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws NotFoundException 用户不存在
	 * @throws OperationsException 用户不是管理员
	 */
	boolean revokeAdmin(User...users) throws IncompleteException, NotFoundException, OperationsException;
	
	/**
	 * 获取用户上次登录时间，如果用户不存在则会返回null
	 * @param userName
	 * @return
	 */
	Date getUserLastLoginTime(String userName);
	
	boolean checkIsAdmin(User user);
	
}
