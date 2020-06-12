package com.cheejee.cms.apply;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.ZoneDAO;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.pojo.Zone;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

@Deprecated
public class ZoneApply {

	SqlSessionFactory ssf;

	public ZoneApply() {
		this.ssf = SSF.getSSF();
	}

	public Zone selectZone(int id) {
		try (SqlSession session = ssf.openSession()) {
			ZoneDAO zd = session.getMapper(ZoneDAO.class);
			return zd.selectZone(id);
		}
	}

	public PageInfo<Zone> selectZoneByName(int pagNum, int pageSize, String name) {
		try (SqlSession session = ssf.openSession()) {
			ZoneDAO zd = session.getMapper(ZoneDAO.class);
			return new PageInfo<Zone>(zd.selectZoneByName(pagNum, pageSize, name));
		}
	}

	public PageInfo<Zone> listZone(int pagNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			ZoneDAO zd = session.getMapper(ZoneDAO.class);
			return new PageInfo<Zone>(zd.listZone(pagNum, pageSize));
		}
	}

	public int addZone(Zone zone) throws DataDuplicationException {

		if (zone == null)
			return 0;

		try (SqlSession session = ssf.openSession()) {
			ZoneDAO cd = session.getMapper(ZoneDAO.class);

			if (cd.selectCountByName(zone) != 0)
				throw new DataDuplicationException("相同的专栏名称已存在：" + zone.getName());

			int a = cd.addZone(zone);
			session.commit();

			return a;
		}
	}
	
	public int addZoneAdmin(Zone zone, User...admins) {
		try (SqlSession session = ssf.openSession()) {
			ZoneDAO cd = session.getMapper(ZoneDAO.class);
			return cd.addAdmins(zone, admins);
		}
	}
	
	public int addContentToZone(Zone zone, Content...contents) {
		try (SqlSession session = ssf.openSession()) {
			ZoneDAO cd = session.getMapper(ZoneDAO.class);
			return cd.addContentToZone(zone, contents);
		}
	}
	
	public int deleteContentToZone(Zone zone, Content...contents) {
		try (SqlSession session = ssf.openSession()) {
			ZoneDAO cd = session.getMapper(ZoneDAO.class);
			return cd.deleteContentToZone(zone, contents);
		}
	}
	
	public int deleteZoneAdmin(Zone zone, User...admins) {
		try (SqlSession session = ssf.openSession()) {
			ZoneDAO cd = session.getMapper(ZoneDAO.class);
			return cd.reduceAdmins(zone, admins);
		}
	}

	public int deleteZone(Zone zone) {

		if (zone == null)
			return 0;

		try (SqlSession session = ssf.openSession()) {
			ZoneDAO zd = session.getMapper(ZoneDAO.class);
			
			int a = zd.deleteZone(zone);
			zd.reduceAdminsAll(zone);
			zd.deleteContentToZoneAll(zone);
			
			session.commit();
			return a;
		}
	}

	public int updateZone(Zone zone) throws DataDuplicationException {

		if (zone == null)
			return 0;

		try (SqlSession session = ssf.openSession()) {
			ZoneDAO zd = session.getMapper(ZoneDAO.class);
			if (zd.selectCountByNameAndCreatorNoSelf(zone) != 0)
				throw new DataDuplicationException("修改的专栏名称已经存在：" + zone.getName());

			if (zone.getAdmins() != null) {
				zd.reduceAdminsAll(zone);
				if (!zone.getAdmins().isEmpty())
					zd.addAdmins(zone);
			}

			int a = zd.updateZone(zone);

			session.commit();
			return a;
		}
	}
	
	

}