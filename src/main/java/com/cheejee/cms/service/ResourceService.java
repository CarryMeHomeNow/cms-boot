package com.cheejee.cms.service;

import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.pojo.Resource;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author CARRY ME HOME
 * 2019年12月19日下午12:16:16
 */
public interface ResourceService {

	/**
	 * 根据id获取资源
	 * @param id
	 * @return
	 */
	Resource getResourceById(int id);
	
	/**
	 * 根据资源名称模糊查询资源
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @return
	 */
	PageInfo<Resource> getResourceByName(int pageNum, int pageSize, String name);
	
	/**
	 * 根据资源名称精确查找资源
	 * @param name
	 * @return
	 */
	Resource getResourceByName(String name);
	
	/**
	 * 根据资源的desName模糊查询资源
	 * @param pageNum
	 * @param pageSize
	 * @param DesName
	 * @return
	 */
	PageInfo<Resource> getResourceByDesName(int pageNum, int pageSize, String DesName);
	
	/**
	 * 获取所有的资源
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Resource> getResourceAll(int pageNum, int pageSize);
	
	/**
	 * 修改资源，可以修改资源的desName和describe
	 * @param res
	 * @return
	 * @throws IncompleteException 资源id为空
	 */
	boolean changeResourc(Resource res) throws IncompleteException;
}
