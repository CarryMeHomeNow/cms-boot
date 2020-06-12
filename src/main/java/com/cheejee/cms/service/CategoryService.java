package com.cheejee.cms.service;
import java.util.List;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Category;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.User;
import com.github.pagehelper.PageInfo;
 
/**
 * 
 * @author CARRY ME HOME
 * 2019年12月10日下午12:01:56
 */
public interface CategoryService {

	/**
	 * 根据id获取分类对象
	 * @param id
	 * @return
	 */
	Category getCategoryByIdManage(int id);
	
	/**
	 * 根据名称模糊查询分类
	 * @param pageNum 页码
	 * @param pageSize 每页内容数
	 * @param name 分类名称
	 * @return 分页对象
	 */
	PageInfo<Category> getCategoryByNameManage(int pageNum, int pageSize, String name);
	
	/**
	 * 根据id获取分类对象
	 * @param id
	 * @param user 当前登录用户
	 * @return
	 * @throws IncompleteException 对象信息不足
	 */
	Category getCategoryById(int id, User user) throws IncompleteException;
	
	/**
	 * 根据名称模糊查询分类
	 * @param pageNum 页码
	 * @param pageSize 每页内容数
	 * @param name 分类名称
	 * @param user 当前登录用户
	 * @return 分页对象
	 * @throws IncompleteException 对象信息不足
	 */
	PageInfo<Category> getCategoryByName(int pageNum, int pageSize, String name, User user) throws IncompleteException;
	
	/**
	 * 获取指定用户的所有分类
	 * @param pageNum 页码
	 * @param pageSize 每页内容数
	 * @param user
	 * @return 分页对象
	 */
	PageInfo<Category> getCategoryByUser(int pageNum, int pageSize, User user);
	
	/**
	 * 获取所有的分类
	 * @param pageNum 页码
	 * @param pageSize 每页内容数
	 * @return 分页对象
	 */
	PageInfo<Category> getCategoryAllByManage(int pageNum, int pageSize);
	
	/**
	 * 获取属于此分类的内容
	 * @param category
	 * @return
	 * @throws IncompleteException 
	 */
	List<Content> getCategoryHasContentByManage(Category category) throws IncompleteException;
	
	/**
	 * 获取属于此分类的内容，此分类必须属于当前用户。
	 * @param category
	 * @param user 当前用户
	 * @return
	 * @throws IncompleteException
	 * @throws InsufficientPermissionException
	 */
	List<Content> getCategoryHasContent(Category category, User user) throws IncompleteException, InsufficientPermissionException;
	
	/**
	 * 添加分类
	 * @param category
	 * @param user 当前登录用户
	 * @return 添加成功返回true
	 * @throws DataDuplicationException 添加的分类名称已存在则会抛出此异常
	 * @throws IncompleteException 添加的分类没有所属用户则会抛出此异常
	 * @throws InsufficientPermissionException 分类所属者与当前用户不匹配
	 */
	boolean addCategory(User user, Category...categorys) throws DataDuplicationException, IncompleteException, InsufficientPermissionException;
	
	/**
	 * 删除分类
	 * 删除分类时检查分类下是否还有内容，如果有则抛出异常。
	 * @param category
	 * @param user 当前登录用户
	 * @return 删除成功返回true
	 * @throws OperationsException 删除的分类下还有内容则会抛出此异常
	 * @throws IncompleteException 对象信息不全
	 * @throws InsufficientPermissionException 分类所属者与当前用户不匹配
	 * @throws DataException 对象数据与数据库不匹配
	 * @throws NotFoundException 分类不存在
	 */
	void deleteCategory(User user, Category...categorys) throws OperationsException, IncompleteException, InsufficientPermissionException, DataException, NotFoundException;
	
	/**
	 * 强制删除分类。
	 * @param user
	 * @param categories
	 * @throws IncompleteException
	 * @throws InsufficientPermissionException
	 */
	void deleteCategoryForce(User user, Category...categories) throws IncompleteException, InsufficientPermissionException;
	
	/**
	 * 更新分类，只能更新分类名称
	 * @param category
	 * @param user 当前登录用户
	 * @return 更新成功返回true
	 * @throws DataDuplicationException 修改的名称已存在则会抛出此异常 
	 * @throws NotFoundException 要修改的分类数据库不存在则会抛出此异常
	 * @throws IncompleteException 对象信息不全
	 * @throws DataException 分类不存在
	 * @throws InsufficientPermissionException 
	 */
	boolean changeCategory(User user, Category category) throws DataDuplicationException, NotFoundException, IncompleteException, DataException, InsufficientPermissionException ;
	
}
