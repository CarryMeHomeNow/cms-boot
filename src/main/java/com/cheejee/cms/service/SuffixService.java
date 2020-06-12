package com.cheejee.cms.service;


import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Suffix;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author CARRY ME HOME
 * 2019年12月21日上午10:02:43
 */
public interface SuffixService {

	/**
	 * 根据id获取后缀
	 * @param id
	 * @return
	 */
	Suffix getSuffixById(int id);
	
	/**
	 * 根据后缀的字符串获取后缀（不带点）
	 * @param suff
	 * @return
	 */
	Suffix getSuffixBySuff(String suff);
	
	/**
	 * 获取所有的后缀
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Suffix> getSuffixAll(int pageNum, int pageSize);
	
	/**
	 * 获取不属于任何类型的后缀
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Suffix> getSuffixNoType(int pageNum, int pageSize);
	
	/**
	 * 添加后缀
	 * @param suffix
	 * @return
	 * @throws DataDuplicationException 添加的后缀已经存在
	 * @throws IncompleteException 
	 */
	boolean addSuffix(Suffix...suffix) throws DataDuplicationException, IncompleteException;
	
	/**
	 * 删除后缀，只能删除没有附件使用的后缀
	 * @param suffixs
	 * @return
	 * @throws OperationsException 有附件使用此后缀
	 * @throws IncompleteException 
	 */
	void deleteSuffix(Suffix...suffixs) throws OperationsException, IncompleteException;

	/**
	 * 修改后缀的描述,如果描述为空的话会抛出空指针异常
	 * @param suffix
	 * @return
	 * @throws IncompleteException 后缀id为空
	 * @throws NotFoundException 
	 * @throws com.cheejee.cms.exception.NotFoundException 
	 */
	boolean changeSuffixDescribe(Suffix suffix) throws IncompleteException, NotFoundException;
	
}
