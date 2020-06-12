package com.cheejee.cms.apply;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.GroupDAO;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Group;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.tools.SSF;

public class GroupApply {

	SqlSessionFactory ssf;

	public GroupApply() {
		this.ssf = SSF.getSSF();
	}

	public Group selectGroup(int id) {
		try (SqlSession session = ssf.openSession()) {
			GroupDAO gd = session.getMapper(GroupDAO.class);
			return gd.selectGroup(id);
		}
	}

	public Group selectGroupByNameExact(String name) {
		try (SqlSession session = ssf.openSession()) {
			GroupDAO gd = session.getMapper(GroupDAO.class);
			return gd.selectGroupByNameExact(name);
		}
	}

	public List<Group> selectGroupByName(int pageNum, int pageSize, String name) {
		try (SqlSession session = ssf.openSession()) {
			GroupDAO gd = session.getMapper(GroupDAO.class);
			return gd.selectGroupByName(pageNum, pageSize, name);
		}
	}

	public List<Group> selectGroupNoParent(int pageNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			GroupDAO gd = session.getMapper(GroupDAO.class);
			return gd.selectGroupNoParent(pageNum, pageSize);
		}
	}

	public List<Group> listGroup(int pagNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			GroupDAO gd = session.getMapper(GroupDAO.class);
			return gd.listGroup(pagNum, pageSize);
		}
	}

	public int addGroup(Group... groups) throws DataDuplicationException {

		try (SqlSession session = ssf.openSession(true)) {

			GroupDAO gd = session.getMapper(GroupDAO.class);

			for (Group group : groups) {
				if (gd.selectGroupNameCount(group) != 0)
					throw new DataDuplicationException("组名称已存在：" + group.getName());
			}

			return gd.addGroup(groups);
		}
	}

	public int addGroupRoles(Group group, List<Role> roles) {

		if (group == null || roles == null || roles.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession(true)) {
			GroupDAO gd = session.getMapper(GroupDAO.class);
			return gd.addGroupRoles(group, roles);
		}
	}

	public int addGroupRole(Group group, Role role) {
		List<Role> lr = new ArrayList<Role>();
		lr.add(role);
		return addGroupRoles(group, lr);
	}

	public int addGroupUsers(Group group, List<User> users) {

		if (group == null || users == null || users.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession(true)) {
			GroupDAO gd = session.getMapper(GroupDAO.class);
			return gd.addGroupUsers(group, users);
		}
	}

	public int addGroupUser(Group group, User user) {
		List<User> lr = new ArrayList<User>();
		lr.add(user);
		return addGroupUsers(group, lr);
	}

	public int deleteGroupUsers(Group group, List<User> users) {

		if (group == null || users == null || users.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession(true)) {
			GroupDAO gd = session.getMapper(GroupDAO.class);
			return gd.deleteGroupUsers(group, users);
		}
	}

	public int deleteGroupUser(Group group, User user) {
		List<User> lr = new ArrayList<User>();
		lr.add(user);
		return deleteGroupUsers(group, lr);
	}

	public int deleteGroupRole(Group group, Role role) {
		List<Role> lr = new ArrayList<Role>();
		lr.add(role);
		return deleteGroupRoles(group, lr);
	}

	public int deleteGroupRoles(Group group, List<Role> roles) {

		if (group == null || roles == null || roles.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession(true)) {
			GroupDAO gd = session.getMapper(GroupDAO.class);
			return gd.deleteGroupRoles(group, roles);
		}
	}

	public int deleteGroup(Group group) {
		try (SqlSession session = ssf.openSession()) {
			GroupDAO gd = session.getMapper(GroupDAO.class);

			int a = gd.deleteGroup(group);

			if (a != 1)
				return a;

			gd.deleteGroupUserAll(group);
			gd.deleteGroupRoleAll(group);

			session.commit();
			return a;
		}
	}

	public int updateGroup(Group group) throws DataDuplicationException, OperationsException {
		try (SqlSession session = ssf.openSession()) {
			GroupDAO gd = session.getMapper(GroupDAO.class);

			if (gd.selectGroupNameCountExSelf(group) != 0)
				throw new DataDuplicationException("组名称已存在：" + group.getName());
			checkGroupParent(group, gd);

			int a = gd.updateGroup(group);

			if (a != 1)
				return a;

			updateGrouopUsers(group, gd);
			updateGroupRoles(group, gd);

			session.commit();
			return a;

		}
	}

	private void checkGroupParent(Group group, GroupDAO gd) throws OperationsException {

		Group parent = group.getParent();
		Group g = gd.selectGroup(group.getId());
		Group ap = g.getParent();

		if (parent == null)
			return;

		int pid = -1;
		if (ap != null)
			pid = ap.getId();

		if (parent.getId() != pid) {

			if (parent.getId() == group.getId())
				throw new OperationsException("父分组不能为本身：" + parent);

			if (this.checkIsSon(parent, g.getSons()))
				throw new OperationsException("此分组为子分组，不能设置为父分组：" + parent);
		}
	}

	private void updateGroupRoles(Group group, GroupDAO gd) throws OperationsException {

		List<Role> roles = group.getRoles();
		gd.deleteGroupRoleAll(group);

		int a = 0;
		int b = 0;

		if (roles != null && !roles.isEmpty()) {
			a = roles.size();
			b = gd.addGroupRoles(group, roles);
		}

		if (b != a)
			throw new OperationsException("更新分组角色失败");
	}

	private void updateGrouopUsers(Group group, GroupDAO gd) throws OperationsException {

		List<User> users = group.getUsers();
		gd.deleteGroupUserAll(group);

		int b = 0;
		int a = 0;

		if (users != null && !users.isEmpty()) {
			a = users.size();
			b = gd.addGroupUsers(group, users);
		}

		if (b != a)
			throw new OperationsException("更新分组用户失败");
	}

	public boolean updateGroupUsers(Group group) {
		try (SqlSession session = ssf.openSession(true)) {
			GroupDAO gd = session.getMapper(GroupDAO.class);

			updateGrouopUsers(group, gd);
			return true;

		} catch (OperationsException e) {
			return false;
		}
	}

	public boolean updateGroupRoles(Group group) {
		try (SqlSession session = ssf.openSession(true)) {
			GroupDAO gd = session.getMapper(GroupDAO.class);

			updateGroupRoles(group, gd);
			return true;

		} catch (OperationsException e) {
			return false;
		}
	}

	public int updateGroupOnly(Group group) throws OperationsException {
		try (SqlSession session = ssf.openSession(true)) {
			GroupDAO gd = session.getMapper(GroupDAO.class);
			checkGroupParent(group, gd);

			return gd.updateGroup(group);
		}
	}

	/**
	 * 校验是否是子分组
	 * 
	 * @param parent 父分组
	 * @param group    需要校验的分组
	 * @return 是子组返回true
	 */
	private boolean checkIsSon(Group parent, List<Group> sons) {
		
		if(sons == null || sons.isEmpty()) {
			return false;
		}

		for(Group g : sons) {
			if(g.getId() == parent.getId()) {
				return true;
			} else {
				List<Group> ss = g.getSons();
				if(ss != null && !ss.isEmpty()) {
					return checkIsSon(parent, ss);
				}
			}
		}
		
		return false;
	}
	

	public String getGroupPath(Group group) {
		try (SqlSession session = ssf.openSession(true)) {

			GroupDAO gd = session.getMapper(GroupDAO.class);
			List<String> ls = new ArrayList<String>();
			group = gd.selectGroup(group.getId());

			while (group.getParent() != null) {
				ls.add("\\" + group.getName());
				group = group.getParent();
			}
			ls.add("\\" + group.getName());

			StringBuilder sb = new StringBuilder();
			System.out.println(ls);

			for (int i = ls.size() - 1; i >= 0; i--) {
				sb.append(ls.get(i));
			}

			return sb.toString();
		}
	}

	/**
	 * 检测分组是否有子组，没有子类返回false
	 * 
	 * @param groups
	 * @return
	 */
	public boolean checkGroupHasSon(Group... groups) {
		try (SqlSession session = ssf.openSession(true)) {
			GroupDAO gd = session.getMapper(GroupDAO.class);

			for (Group group : groups) {
				if (gd.selectCountByGroupSon(group) != 0)
					return true;
			}
			return false;
		}
	}

	/**
	 * 根据分组id检测分组是否存在
	 * 
	 * @param groups
	 * @throws NotFoundException 分组不存在
	 */
	public void checkGroupExist(Group... groups) throws NotFoundException {
		try (SqlSession session = ssf.openSession(true)) {
			GroupDAO gd = session.getMapper(GroupDAO.class);

			for (Group g : groups) {
				if (gd.selectGroup(g.getId()) == null)
					throw new NotFoundException("分组不存在");
			}
		}
	}
}
