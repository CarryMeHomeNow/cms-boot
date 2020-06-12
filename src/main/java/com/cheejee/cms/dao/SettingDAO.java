package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Setting;

public interface SettingDAO {
	
	Setting selectSetting(int id);
	
	List<Setting> selectSettingByName(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize, @Param("name")String name);
	
	List<Setting> listSetting(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize);
	
	int selectCountBySettingName(Setting setting);
	
	int addSetting(Setting setting);
	
	int deleteSetting(Setting setting);
	
	/**
	 * 不会更新name
	 * @param setting
	 * @return
	 */
	int updateSetting(Setting setting);
}
