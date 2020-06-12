package com.cheejee.cms.service;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.pojo.Category;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.Tag;
import com.cheejee.cms.pojo.User;
import com.github.pagehelper.PageInfo;

/**
 * 校验传入的对象的关系的正确性。 一些逻辑判断。
 * 如果想要知道抛出异常的详细信息可以使用getMessage方法查看。
 * <p>
 * 如果需要修改内容，最好使用changeContent方法。
 * 这个方法可以修改内容的所有信息，其他的对内容修改方法有很多不必要的检验。
 * @author CARRY ME HOME 2019年12月11日下午12:26:59
 */
public interface ContentService {

	/**
	 * 管理员根据id获取内容，只加载内容的基本属性
	 * 
	 * @param id
	 * @return
	 */
	Content getContentByIdManage(int id);
	
	/**
	 * 管理员编辑页面的内容获取。加载出内容的所有属性。
	 * @param id
	 * @return
	 */
	Content getContentEditByIdManage(int id);

	/**
	 * 根据id获取内容， 只加载内容的基本属性
	 * 
	 * @param id   内容id
	 * @param user 当前登入用户
	 * @return 内容对象
	 * @throws IncompleteException 对象信息不足
	 */
	Content getContentById(int id, User user) throws IncompleteException;
	
	/**
	 * 编辑页面的内容获取，加载出内容的所有属性。
	 * @param id
	 * @param user
	 * @return
	 * @throws IncompleteException
	 */
	Content getContentEditById(int id, User user) throws IncompleteException;

	/**
	 * 根据标题模糊查找内容
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param title
	 * @return 分页对象
	 */
	PageInfo<Content> getContentByTitleManage(int pageNum, int pageSize, String title);

	/**
	 * 根据标题模糊查找当前登入用户的内容
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param title
	 * @param user     当前登入用户
	 * @return
	 * @throws IncompleteException 对象信息不足
	 */
	PageInfo<Content> getContentByTitle(int pageNum, int pageSize, String title, User user) throws IncompleteException;
	
	/**
	 * 搜索内容， 根据标题模糊查找指定状态的内容。
	 * @param pageNum
	 * @param pageSize
	 * @param title
	 * @param state
	 * @return
	 */
	PageInfo<Content> searchContent(int pageNum, int pageSize, String title, Integer state);

	/**
	 * 管理员获取指定状态的内容
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param state
	 * @return 分页对象
	 */
	PageInfo<Content> getContentByStateManage(int pageNum, int pageSize, Integer state);
	

	/**
	 * 获取指定用户指定状态的内容
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param user
	 * @param state
	 * @return 分页对象
	 * @throws IncompleteException 用户对象没有id
	 */
	PageInfo<Content> getContentByState(int pageNum, int pageSize, User user, Integer state) throws IncompleteException;

	/**
	 * 获取指定用户的内容
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param user
	 * @return 分页对象
	 * @throws IncompleteException 用户对象没有id则会抛出此异常
	 */
	PageInfo<Content> getContentByUser(int pageNum, int pageSize, User user) throws IncompleteException;
	
	/**
	 * 获取所有没有分类的内容
	 * @param pageNum
	 * @param pageSize
	 * @param user
	 * @return
	 * @throws IncompleteException
	 */
	PageInfo<Content> getContentNoCategory(int pageNum, int pageSize, User user) throws IncompleteException;
	
	/**
	 * 按分类获取内容
	 * @param pageNum
	 * @param pageSize
	 * @param user
	 * @return
	 * @throws IncompleteException
	 */
	PageInfo<Content> getContentByCategory(int pageNum, int pageSize, Category category, User user) throws IncompleteException;


	/**
	 * 获取所有内容
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return 分页对象
	 */
	PageInfo<Content> getContentAll(int pageNum, int pageSize);

	/**
	 * 添加内容，一并添加内容和其他对象的关系。需要校验内容分类是否是自己的分类，内容的标签和专栏是否存在。
	 * 内容标题不能为空，内容文字不能为空。
	 * 
	 * @param content
	 * @param user    当前登入用户
	 * @return 添加成功返回true
	 * @throws NullPointerException 添加的内容对象为空
	 * @throws IncompleteException  内容属于的用户为空
	 * @throws OperationsException  添加内容时出错则会抛出此异常
	 * @throws DataDuplicationException 添加了重复的标签
	 * @throws NotFoundException 内容的附件或者分类数据库不存在 
	 * @throws InsufficientPermissionException 
	 */
	boolean addContent(User user, Content contents)
			throws IncompleteException, NullPointerException, OperationsException, DataDuplicationException, NotFoundException, InsufficientPermissionException;

	/**
	 * 管理员删除内容
	 * 
	 * @param content
	 * @return 删除成功返回true
	 * @throws NotFoundException    要删除的内容数据库不存在
	 * @throws NullPointerException 删除的内容对象为空
	 * @throws IncompleteException 内容对象没有id
	 */
	void deleteContentByManage(Content... contents) throws NullPointerException, NotFoundException, IncompleteException;

	/**
	 * 普通用户删除内容
	 * 
	 * @param content 要删除的内容
	 * @param user    当前登入用户
	 * @return 成功返回true
	 * @throws NotFoundException               要删除的内容数据库不存在
	 * @throws NullPointerException            删除的内容对象为空
	 * @throws InsufficientPermissionException 用户删除的不是自己的内容时抛出此异常
	 * @throws IncompleteException             内容对象信息不全
	 * @throws DataException                   对象储存的数据和数据库不匹配
	 */
	void deleteContent(User user, Content... contents) throws NullPointerException, NotFoundException,
			InsufficientPermissionException, IncompleteException, DataException;

	/**
	 * 管理员更新内容
	 * 
	 * @param content
	 * @return 更新成功返回true
	 * @throws NotFoundException    更新的内容在数据库不存在
	 * @throws NullPointerException 更新的内容对象为空
	 * @throws OperationsException  更新内容时出错则会抛出此异常
	 * @throws DataException        创建者和数据库不匹配时抛出此异常
	 * @throws NullPointerException 需要使用的对象为空时会抛出此异常
	 * @throws IncompleteException 内容对象没有id
	 * @throws DataDuplicationException 
	 */
	boolean changeContentByManage(Content content)
			throws NullPointerException, NotFoundException, OperationsException, NullPointerException, DataException, IncompleteException, DataDuplicationException;

	/**
	 * 更新内容
	 * 
	 * @param content 要更新的内容
	 * @param user    当前登入用户
	 * @return 更新成功返回true
	 * @throws OperationsException
	 * @throws NotFoundException               数据库不存在这个内容
	 * @throws NullPointerException            对象为空
	 * @throws InsufficientPermissionException 更新的不是传入对象的内容
	 * @throws IncompleteException             对象信息不足
	 * @throws DataException                   内容创建者和数据库的不匹配
	 * @throws DataDuplicationException 
	 */
	boolean changeContent(Content content, User user) throws NullPointerException, NotFoundException,
			OperationsException, InsufficientPermissionException, IncompleteException, DataException, DataDuplicationException;

	/**
	 * 修改内容状态
	 * @param content
	 * @return
	 * @throws IncompleteException 
	 * @throws OperationsException 
	 * @throws NotFoundException 
	 */
	boolean changeContentStateByManage(Content content) throws IncompleteException, OperationsException, NotFoundException;
	
	/**
	 * 添加内容的标签，如果添加的标签不存在则会动态的添加标签。如果添加的标签已存在则会抛出PersistenceException异常
	 * 
	 * @param content
	 * @param user    当前登录用户
	 * @param tags    添加的标签
	 * @return 添加成功返回true
	 * @throws DataException                   内容的创建者和数据库不匹配
	 * @throws NotFoundException               内容不存在
	 * @throws InsufficientPermissionException 内容不属于当前用户
	 * @throws DataDuplicationException        添加标签时标签名已存在
	 * @throws IncompleteException 对象信息不足
	 * @throws OperationsException 标签数量过多
	 */
	boolean addContentTag(Content content, User user, Tag... tags)
			throws DataException, NotFoundException, InsufficientPermissionException, DataDuplicationException, IncompleteException, OperationsException;

	/**
	 * 删除内容的标签，如果内容没有这个标签会删除失败
	 * 
	 * @param content
	 * @param user    当前登录的用户
	 * @param tags    标签
	 * @return 删除成功返回true
	 * @throws NotFoundException               内容不存在
	 * @throws InsufficientPermissionException 权限不足
	 * @throws DataException                   对象数据与数据库不匹配
	 * @throws NullPointerException            对象为空
	 * @throws IncompleteException 对象信息不足
	 */
	void deleteContentTag(Content content, User user, Tag... tags)
			throws NotFoundException, NullPointerException, DataException, InsufficientPermissionException, IncompleteException;

	/**
	 * 更改内容的标签，如果标签不存在则会动态的添加标签
	 * 
	 * @param content
	 * @param user    当前登录用户
	 * @return 更改成功返回true
	 * @throws NotFoundException               内容不存在
	 * @throws DataException                   对象数据与数据库不匹配
	 * @throws InsufficientPermissionException 权限不足
	 * @throws DataDuplicationException        添加标签时标签已存在
	 * @throws IncompleteException 对象信息不足
	 * @throws OperationsException 标签数量过多
	 */
	boolean changeContentTags(Content content, User user)
			throws NotFoundException, DataException, InsufficientPermissionException, DataDuplicationException, IncompleteException, OperationsException;

	/**
	 * 管理员添加内容标签，标签不存在会动态添加标签
	 * 
	 * @param content
	 * @param tags
	 * @return 添加成功返回true
	 * @throws DataDuplicationException 添加标签时标签名重复
	 * @throws NotFoundException        内容不存在
	 * @throws IncompleteException 内容信息不足
	 * @throws OperationsException 操作失败
	 */
	boolean addContentTagByManage(Content content, Tag... tags) throws DataDuplicationException, NotFoundException, IncompleteException, OperationsException;

	/**
	 * 管理员删除内容标签
	 * 
	 * @param content
	 * @param tags
	 * @return 删除成功返回true
	 * @throws NotFoundException 内容不存在
	 * @throws IncompleteException 对象信息不足
	 */
	boolean deleteContentTagByManage(Content content, Tag... tags) throws NotFoundException, IncompleteException;

	/**
	 * 管理员更改内容标签，标签不存在会动态创建标签
	 * 
	 * @param content
	 * @param tags
	 * @return 更改成功返回true
	 * @throws NotFoundException        内容不存在
	 * @throws DataDuplicationException 添加标签时标签名重复
	 * @throws IncompleteException 对象信息不足
	 * @throws OperationsException 标签过多
	 */
	boolean changeContentTagsByManage(Content content) throws NotFoundException, DataDuplicationException, IncompleteException, OperationsException;

	/**
	 * 添加内容的附件
	 * 
	 * @param content
	 * @param user    当前登录用户
	 * @param attachs 要添加的附件
	 * @return 添加成功返回true
	 * @throws InsufficientPermissionException 权限不足
	 * @throws NotFoundException               内容或者附件不存在
	 * @throws DataException                   对象数据与数据库不匹配
	 * @throws IncompleteException 对象信息不足
	 * @throws DataDuplicationException 添加了已存在的附件。
	 */
	boolean addContentAttach(Content content, User user, Attach... attachs)
			throws InsufficientPermissionException, NotFoundException, DataException, IncompleteException, DataDuplicationException;

	/**
	 * 删除内容的附件
	 * 
	 * @param content
	 * @param user    当前登入用户
	 * @param attachs 要删除的附件
	 * @return 删除成功返回true
	 * @throws InsufficientPermissionException 权限不足
	 * @throws NotFoundException               内容不存在
	 * @throws DataException                   对象数据和数据库不匹配
	 * @throws IncompleteException 对象信息不足
	 * @throws DataDuplicationException 删除了重复的附件
	 */
	void deleteContentAttach(Content content, User user, Attach... attachs)
			throws InsufficientPermissionException, NotFoundException, DataException, IncompleteException, DataDuplicationException;

	/**
	 * 更改内容的附件
	 * 
	 * @param content
	 * @param user    当前登录用户
	 * @param attachs 新的附件
	 * @return 更改成功返回true
	 * @throws InsufficientPermissionException 权限不足
	 * @throws NotFoundException               内容或者附件不存在
	 * @throws DataException                   对象数据和数据库不匹配
	 * @throws IncompleteException 对象信息不足
	 * @throws DataDuplicationException 数据重复。
	 */
	boolean changeContentAttachs(Content content, User user)
			throws InsufficientPermissionException, NotFoundException, DataException, IncompleteException, DataDuplicationException;

	/**
	 * 管理员添加内容的附件
	 * 
	 * @param content
	 * @param attachs
	 * @return
	 * @throws NotFoundException 内容或者附件不存在
	 * @throws IncompleteException 对象信息不足
	 * @throws DataDuplicationException 添加了重复的附件。
	 */
	boolean addContentAttachByManage(Content content, Attach... attachs) throws NotFoundException, IncompleteException, DataDuplicationException;

	/**
	 * 管理员删除内容的附件
	 * 
	 * @param content
	 * @param attachs
	 * @return
	 * @throws NotFoundException 内容不存在
	 * @throws IncompleteException 对象信息不足
	 * @throws DataDuplicationException 数据重复
	 */
	boolean deleteContentAttachByManage(Content content, Attach... attachs) throws NotFoundException, IncompleteException, DataDuplicationException;

	/**
	 * 管理员更改内容的附件
	 * 
	 * @param content
	 * @param attachs
	 * @return
	 * @throws NotFoundException 内容或者附件不存在
	 * @throws IncompleteException 对象信息不足
	 * @throws DataDuplicationException 数据重复
	 */
	boolean changeContentAttachsByManage(Content content) throws NotFoundException, IncompleteException, DataDuplicationException;

	/**
	 * 添加指定内容的分类
	 * 
	 * @param content
	 * @param user      当前登录的用户
	 * @param categroys
	 * @return 添加成功返回true
	 * @throws InsufficientPermissionException 权限不足
	 * @throws NotFoundException               内容或者分类不存在
	 * @throws DataException                   对象数据与数据库不匹配
	 * @throws IncompleteException 对象信息不足
	 * @throws DataDuplicationException 添加了已存在的分类
	 */
	boolean addContentCategory(Content content, User user, Category... categroys)
			throws InsufficientPermissionException, NotFoundException, DataException, IncompleteException, DataDuplicationException;

	/**
	 * 减少指定内容的分类
	 * 
	 * @param content
	 * @param user      当前登录用户
	 * @param categroys
	 * @return 成功返回true
	 * @throws IncompleteException 对象信息不全
	 * @throws InsufficientPermissionException 权限不足
	 * @throws NotFoundException 内容不存在
	 * @throws DataException 对象数据和数据库不匹配
	 * @throws DataDuplicationException 数据重复。
	 */
	boolean deleteContentCategory(Content content, User user, Category... categroys) throws IncompleteException, InsufficientPermissionException, NotFoundException, DataException, DataDuplicationException;

	/**
	 * 更改内容的分类
	 * 
	 * @param content
	 * @param user      当前登录的用户
	 * @param categroys
	 * @return 成功返回true
	 * @throws IncompleteException 对象信息不全
	 * @throws InsufficientPermissionException 权限不足
	 * @throws NotFoundException 内容不存在
	 * @throws DataException 对象内容与数据库不匹配
	 * @throws DataDuplicationException 数据重复。
	 */
	boolean changeContentCategorys(Content content, User user) throws IncompleteException, InsufficientPermissionException, NotFoundException, DataException, DataDuplicationException;
	
	/**
	 * 审核内容。 当前为全部通过审核，没有具体实现审核业务。
	 * @param contents
	 * @return
	 * @throws OperationsException 
	 */
	boolean reviewContent(Content...contents) throws OperationsException;
	
	/**
	 * 将内容移动到分类下
	 * @param content
	 * @param category
	 * @throws InsufficientPermissionException 内容分类的创建者不一致
	 * @throws NotFoundException 内容或分类不存在
	 */
	void moveContentToCategory(Content content, Category category) throws InsufficientPermissionException, NotFoundException;
}
