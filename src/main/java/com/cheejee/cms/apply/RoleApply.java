package com.cheejee.cms.apply;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.PermissionDAO;
import com.cheejee.cms.dao.RoleDAO;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.pojo.Group;
import com.cheejee.cms.pojo.Permission;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class RoleApply {

	SqlSessionFactory ssf;

	public RoleApply() {
		this.ssf = SSF.getSSF();
	}

	public Role selectRole(int id) {
		try (SqlSession session = ssf.openSession()) {
			RoleDAO rd = session.getMapper(RoleDAO.class);
			return rd.selectRole(id);
		}
	}

	public PageInfo<Role> selectRoleByName(int pageNum, int pageSize, String name) {
		try (SqlSession session = ssf.openSession()) {
			RoleDAO rd = session.getMapper(RoleDAO.class);
			return new PageInfo<Role>(rd.selectRoleByName(pageNum, pageSize, name));
		}
	}

	public Role selectRoleByNameExact(String name) {
		try (SqlSession session = ssf.openSession()) {
			RoleDAO rd = session.getMapper(RoleDAO.class);
			return rd.selectRoleByNameExact(name);
		}
	}

	public PageInfo<Role> selectRoleByCreator(int pagNum, int pageSize, User user) {
		try (SqlSession session = ssf.openSession()) {
			RoleDAO rd = session.getMapper(RoleDAO.class);
			return new PageInfo<Role>(rd.selectRoleByCreator(pagNum, pageSize, user));
		}
	}

	public PageInfo<Role> listRole(int pagNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			RoleDAO rd = session.getMapper(RoleDAO.class);
			return new PageInfo<Role>(rd.listRole(pagNum, pageSize));
		}
	}

	/**
	 * 添加角色同时添加角色拥有的权限
	 * 
	 * @param role
	 * @return
	 * @throws DataDuplicationException
	 */
	public int addRole(Role role) throws DataDuplicationException {

		try (SqlSession session = ssf.openSession()) {
			RoleDAO rd = session.getMapper(RoleDAO.class);
			PermissionDAO pd = session.getMapper(PermissionDAO.class);

			if (rd.selectRoleNameCount(role) != 0)
				throw new DataDuplicationException("角色名称已存在：" + role.getName());

			int a = rd.addRole(role);
			if( a != 1)
				return a;
			
			List<Permission> lp = role.getPermissions();
			if (lp != null && !lp.isEmpty())
				pd.addPermissions(lp);

			session.commit();
			return a;
		}
	}

	public int deleteRole(Role role) {

		try (SqlSession session = ssf.openSession()) {
			RoleDAO rd = session.getMapper(RoleDAO.class);
			PermissionDAO pd = session.getMapper(PermissionDAO.class);

			int a = rd.deleteRole(role);
			pd.deletePermissionByRole(role);

			session.commit();
			return a;
		}
	}

	public int updateRole(Role role) throws DataDuplicationException {
		try (SqlSession session = ssf.openSession()) {
			RoleDAO rd = session.getMapper(RoleDAO.class);

			if (rd.selectRoleNameCountExSelf(role) != 0)
				throw new DataDuplicationException("角色名已存在：" + role.getName());

			int a = rd.updateRole(role);
			
			if(a != 1)
				return a;
			
			upadteRolePermission(role, session);

			session.commit();
			return a;
		}
	}

	private void upadteRolePermission(Role role, SqlSession session) {

		PermissionDAO pd = session.getMapper(PermissionDAO.class);
		pd.deletePermissionByRole(role);
		List<Permission> lp = role.getPermissions();

		if (lp != null && !lp.isEmpty())
			pd.addPermissions(lp);
	}

	/**
	 * 校验是否存在某个组或某个用户只拥有这一个角色
	 * 
	 * @param role
	 * @return 如果存在则返回true
	 */
	public boolean checkRoleIsUseOnly(Role role) {
		try (SqlSession session = ssf.openSession()) {
			RoleDAO rd = session.getMapper(RoleDAO.class);

			Role r = rd.selectRole(role.getId());
			List<User> lu = r.getUsers();
			List<Group> lg = r.getGroups();

			for (User u : lu) {
				if (u.getRoles().size() == 1)
					return true;
			}

			for (Group g : lg) {
				if (g.getRoles().size() == 1)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 校验角色是否存在，不存在抛出异常
	 * @param roles
	 * @throws NotFoundException
	 */
	public void checkRoleIsExit(Role...roles) throws NotFoundException {
		try (SqlSession session = ssf.openSession()) {
			RoleDAO rd = session.getMapper(RoleDAO.class);
			
			for(Role r : roles) {
				if(rd.selectRole(r.getId()) == null)
					throw new NotFoundException("角色不存在");
			}
			
		}
	}
}
