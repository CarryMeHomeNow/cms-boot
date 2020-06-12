package com.cheejee.cms.service.impl;

import com.cheejee.cms.apply.SettingApply;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.pojo.Setting;
import com.cheejee.cms.service.SettingService;
import com.cheejee.cms.tools.CheckTool;
import com.github.pagehelper.PageInfo;

/**
 * 添加了增加删除方法
 * @author CARRY ME HOME
 * 2019年12月26日下午6:37:59
 */
public class SettingServiceImpl implements SettingService {
	
	private SettingApply sa;
	
	public SettingServiceImpl() {
		this.sa = new SettingApply();
	}

	@Override
	public Setting getSettingById(int id) {
		return sa.selectSetting(id);
	}

	@Override
	public PageInfo<Setting> getSettingByName(int pageNum, int pageSize, String name) {
		CheckTool.isNull(name, "名称为空");
		return sa.selectSettingByName(pageNum, pageSize, name);
	}

	@Override
	public PageInfo<Setting> getSettingAll(int pageNum, int pageSize) {
		return sa.listSetting(pageNum, pageSize);
	}

	@Override
	public boolean changeSetting(Setting setting) throws IncompleteException, NotFoundException {
		
		CheckTool.isNull(setting, "设置为空");
		CheckTool.checkId(setting.getId(), "设置id为空");
		
		if(sa.selectSetting(setting.getId()) == null)
			throw new NotFoundException("设置不存在");
		
		return sa.updateSetting(setting) == 1;
	}

	@Override
	public boolean addSetting(Setting... settings) throws DataDuplicationException, IncompleteException {
		CheckTool.isNull("添加的设置为空", settings);
		for(Setting s : settings) {
			if(s.getName() == null || s.getName().equals(""))
				throw new IncompleteException("设置名称为空");
		}
		return sa.addSetting(settings);
	}

	@Override
	public void deleteSetting(Setting... settings) throws IncompleteException {
		CheckTool.isNull("删除的设置为空", settings);
		
		for(Setting s : settings) {
			CheckTool.checkId(s.getId(), "设置id为空");
		}
		
		sa.deleteSetting(settings);
	}

}
