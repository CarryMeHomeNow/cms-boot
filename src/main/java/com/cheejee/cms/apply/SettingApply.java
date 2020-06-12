package com.cheejee.cms.apply;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.SettingDAO;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.pojo.Setting;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class SettingApply {

	SqlSessionFactory ssf;

	public SettingApply() {
		this.ssf = SSF.getSSF();
	}

	public Setting selectSetting(int id) {
		try (SqlSession session = ssf.openSession()) {
			SettingDAO sd = session.getMapper(SettingDAO.class);
			return sd.selectSetting(id);
		}
	}

	public PageInfo<Setting> selectSettingByName(int pageNum, int pageSize, String name) {
		try (SqlSession session = ssf.openSession()) {
			SettingDAO sd = session.getMapper(SettingDAO.class);
			return new PageInfo<Setting>(sd.selectSettingByName(pageNum, pageSize, name));
		}
	}

	public PageInfo<Setting> listSetting(int pageNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			SettingDAO sd = session.getMapper(SettingDAO.class);
			return new PageInfo<Setting>(sd.listSetting(pageNum, pageSize));
		}
	}

	public boolean addSetting(Setting... settings) throws DataDuplicationException {
		try (SqlSession session = ssf.openSession(true)) {
			SettingDAO sd = session.getMapper(SettingDAO.class);

			for (Setting setting : settings) {

				if (sd.selectCountBySettingName(setting) != 0)
					throw new DataDuplicationException("设置名称已存在");

				if (sd.addSetting(setting) != 1)
					return false;
			}
			return true;
		}
	}

	public void deleteSetting(Setting... settings) {
		try (SqlSession session = ssf.openSession(true)) {
			SettingDAO sd = session.getMapper(SettingDAO.class);

			for (Setting setting : settings) {
				sd.deleteSetting(setting);
			}
		}
	}

	/**
	 * 不会更新name
	 * 
	 * @param setting
	 * @return
	 */
	public int updateSetting(Setting setting) {
		try (SqlSession session = ssf.openSession(true)) {
			SettingDAO sd = session.getMapper(SettingDAO.class);
			return sd.updateSetting(setting);
		}
	}

}
