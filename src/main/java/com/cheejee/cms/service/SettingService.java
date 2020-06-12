package com.cheejee.cms.service;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.pojo.Setting;
import com.github.pagehelper.PageInfo;

/**
 * @author CARRY ME HOME
 * 2019年12月20日下午5:12:32
 */
public interface SettingService {
	
	Setting getSettingById(int id);
	
	PageInfo<Setting> getSettingByName(int pageNum, int pageSize, String name);
	
	PageInfo<Setting> getSettingAll(int pageNum, int pageSize);
	
	
	/**
	 * 添加设置
	 * @param settings
	 * @return
	 * @throws DataDuplicationException 添加的设置名称重复
	 * @throws IncompleteException 对象信息不足。
	 */
	boolean addSetting(Setting...settings) throws DataDuplicationException, IncompleteException;
	
	/**
	 * 删除设置
	 * @param settings
	 * @throws IncompleteException 对象信息不足。
	 */
	void deleteSetting(Setting...settings) throws IncompleteException;
	
	/**
	 * 修改设置，能修改设置的值或者说明。
	 * @param setting
	 * @return
	 * @throws IncompleteException 对象信息不足
	 * @throws NotFoundException 设置不存在。
	 */
	boolean changeSetting(Setting setting) throws IncompleteException, NotFoundException;
}
