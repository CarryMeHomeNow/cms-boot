package com.cheejee.cms.service;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.pojo.Suffix;
import com.cheejee.cms.pojo.Type;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author CARRY ME HOME
 * 2019年12月20日下午5:45:42
 */
public interface TypeService {
	
	/**
	 * 根据id获取类型对象
	 * @param id
	 * @return
	 */
	Type getTypeById(int id);
	
	/**
	 * 获取用于编辑的类型对象
	 * @param id
	 * @return
	 */
	Type getTypeEditById(int id);
	
	/**
	 * 获取所有类型
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Type> getAllType(int pageNum, int pageSize);
	
	/**
	 * 根据名称获取类型对象
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @return
	 */
	PageInfo<Type> getTypeByName(int pageNum, int pageSize, String name);
	
	/**
	 * 添加type，type名称重复则会抛出异常。不会添加type对象携带的suffix
	 * @param types
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws DataDuplicationException 名称重复
	 */
	boolean addType(Type...types) throws IncompleteException, DataDuplicationException;
	
	/**
	 * 删除类型
	 * @param types
	 * @return
	 * @throws IncompleteException 对象信息不足
	 */
	boolean deleteType(Type...types) throws IncompleteException;
	
	/**
	 * 可以修改type的名称，状态，描述和所包含的后缀
	 * @param type
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws DataDuplicationException 修改的名称重复
	 * @throws NotFoundException 数据不存在。
	 */
	boolean changeType(Type type) throws IncompleteException, DataDuplicationException, NotFoundException;

	/**
	 * 添加后缀到指定的类型。如果后缀不存在则会先添加后缀。根据后缀id是否为0判断后缀是否存在。
	 * 添加了重复的后缀会抛出异常。
	 * @param type
	 * @param suffixs
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws DataDuplicationException 添加的后缀已存在
	 * @throws NotFoundException 
	 */
	boolean addSuffixToType(Type type, Suffix...suffixs) throws IncompleteException, DataDuplicationException, NotFoundException;
	
	/**
	 * 去除类型里的一些后缀。
	 * @param type
	 * @param suffixs
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws NotFoundException 
	 */
	void deleteSuffixToType(Type type, Suffix...suffixs) throws IncompleteException, NotFoundException;
	
	/**
	 * 修改此类型所拥有的后缀，新的后缀从type对象取得
	 * @param type
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws DataDuplicationException 数据重复异常
	 */
	boolean changeTypeSuffixs(Type type) throws IncompleteException, DataDuplicationException;
}
