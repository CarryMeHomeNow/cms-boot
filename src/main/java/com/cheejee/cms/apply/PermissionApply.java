package com.cheejee.cms.apply;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.PermissionDAO;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.pojo.Permission;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class PermissionApply {

	SqlSessionFactory ssf;

	public PermissionApply() {
		this.ssf = SSF.getSSF();
	}

	public Permission selectPermission(int id) {
		try (SqlSession session = ssf.openSession()) {
			PermissionDAO pd = session.getMapper(PermissionDAO.class);
			return pd.selectPermission(id);
		}
	}

	public Permission selectPermisByRoleAndRes(Role role, String resName) {
		try (SqlSession session = ssf.openSession()) {
			PermissionDAO pd = session.getMapper(PermissionDAO.class);
			return pd.selectPermisByRoleAndResName(role, resName);
		}
	}

	public PageInfo<Permission> listPermission(int pageNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			PermissionDAO pd = session.getMapper(PermissionDAO.class);
			return new PageInfo<Permission>(pd.listPermission(pageNum, pageSize));
		}
	}

	public int addPermission(Permission per) throws DataDuplicationException {
		try (SqlSession session = ssf.openSession(true)) {
			PermissionDAO pd = session.getMapper(PermissionDAO.class);

			if (pd.selectCountByRoleAndRes(per.getRole(), per.getResource()) != 0)
				throw new DataDuplicationException("相同权限已经存在：" + per);

			return pd.addPermission(per);
		}
	}

	public int addPermission(List<Permission> permissions) throws DataDuplicationException {
		
		try (SqlSession session = ssf.openSession(true)) {
			
			PermissionDAO pd = session.getMapper(PermissionDAO.class);
			Set<String> set = new HashSet<String>();

			for (Permission per : permissions) {
				if (pd.selectCountByRoleAndRes(per.getRole(), per.getResource()) != 0)
					throw new DataDuplicationException("相同权限已经存在：" + per);
				
				set.add(per.getRole().toString() + per.getResource().toString());
			}
			
			if(set.size() != permissions.size())
				throw new DataDuplicationException("存在相同的权限");

			return pd.addPermissions(permissions);
		}
	}

	public int deletePermission(Permission per) {
		try (SqlSession session = ssf.openSession(true)) {
			PermissionDAO pd = session.getMapper(PermissionDAO.class);
			return pd.deletePermission(per);
		}
	}

	public void deletePermissions(List<Permission> permissions) {
		try (SqlSession session = ssf.openSession()) {
			PermissionDAO pd = session.getMapper(PermissionDAO.class);

			for (Permission p : permissions) {
				pd.deletePermission(p);
			}
			session.commit();
		}
	}
	
	public void deletePermissionByRole(Role role) {
		try (SqlSession session = ssf.openSession()) {
			PermissionDAO pd = session.getMapper(PermissionDAO.class);
			
			pd.deletePermissionByRole(role);
		}
	}

	public void updatePermission(Permission...per) {
		try (SqlSession session = ssf.openSession()) {
			PermissionDAO pd = session.getMapper(PermissionDAO.class);
			
			for(Permission p : per) {
				pd.updatePermission(p);
			}
			session.commit();
		}
	}
}
