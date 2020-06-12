package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.checkRoleIsReplicate;
import static com.cheejee.cms.tools.CheckTool.checkUserIsReplicate;
import static com.cheejee.cms.tools.CheckTool.isNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cheejee.cms.apply.GroupApply;
import com.cheejee.cms.apply.RoleApply;
import com.cheejee.cms.apply.UserApply;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Group;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.service.GroupService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author CARRY ME HOME 2019年12月18日下午4:18:52
 */
public class GroupSerivceImpl implements GroupService {

	private GroupApply gp;

	public GroupSerivceImpl() {
		this.gp = new GroupApply();
	}

	@Override
	public Group getGroupById(int id) {
		Group g = gp.selectGroup(id);
		if (g != null)
			g.getCreater();

		return g;
	}

	@Override
	public Group getGroupEditById(int id) {

		Group group = gp.selectGroup(id);
		if (group != null) {
			group.getCreater();
			group.getUsers();
			group.getRoles();
			group.getSons();
		}
		return group;
	}

	@Override
	public PageInfo<Group> getGroupByName(int pageNum, int pageSize, String name) {
		isNull(name, "名称为空");
		return new PageInfo<Group>(gp.selectGroupByName(pageNum, pageSize, name));
	}

	@Override
	public List<Group> getGroupSon(Group group) throws NotFoundException, IncompleteException {

		isNull(group, "分组为空");
		checkId(group.getId(), "分组id为空");

		Group g = gp.selectGroup(group.getId());

		if (g != null) {
			return g.getSons();
		} else {
			throw new NotFoundException("分组不存在");
		}
	}

	@Override
	public PageInfo<Group> getGroupNoParent(int pageNum, int pageSize) {
		return new PageInfo<Group>(gp.selectGroupNoParent(pageNum, pageSize));
	}

	@Override
	public PageInfo<Group> getGroupAll(int pageNum, int pageSize) {
		return new PageInfo<Group>(gp.listGroup(pageNum, pageSize));
	}

	@Override
	@Deprecated
	public String getGroupPath(Group group) throws IncompleteException {
		isNull(group, "分组为空");
		checkId(group.getId(), "分组没有id");

		return gp.getGroupPath(group);
	}

	@Override
	public boolean addGroup(Group... groups)
			throws NullPointerException, IncompleteException, DataDuplicationException {

		isNull("添加的group为空", groups);

		for (Group group : groups) {
			if (group.getName() == null || "".equals(group.getName()) || group.getCreater() == null)
				throw new IncompleteException("添加的group名称或者创建者为空");
		}

		return gp.addGroup(groups) == groups.length;
	}

	@Override
	public void deleteGroup(Group group) throws IncompleteException, OperationsException {

		isNull(group, "删除的分组为空");
		checkId(group.getId(), "分组id为空");

		if (gp.checkGroupHasSon(group))
			throw new OperationsException("删除的分组还有子组");

		gp.deleteGroup(group);
	}

	@Override
	public boolean changeGroup(Group group)
			throws IncompleteException, DataDuplicationException, OperationsException, NotFoundException {

		isNull(group, "分组对象为空");
		checkId(group.getId(), "分组id为空");

		if (group.getName() == null || "".equals(group.getName()))
			throw new IncompleteException("分组名称为空");

		gp.checkGroupExist(group);

		if (group.getParent() != null)
			gp.checkGroupExist(group.getParent());

		UserApply ua = new UserApply();
		if (group.getUsers() != null)
			ua.checkUserIsExit(group.getUsers().toArray(new User[0]));

		RoleApply ra = new RoleApply();
		if (group.getRoles() != null)
			ra.checkRoleIsExit(group.getRoles().toArray(new Role[0]));

		checkUserIsReplicate(group.getUsers());
		checkRoleIsReplicate(group.getRoles());

		return gp.updateGroup(group) == 1;
	}

	@Override
	public boolean addGroupUser(Group group, User... users)
			throws IncompleteException, NotFoundException, DataDuplicationException {

		isNull(group, "分组为空");
		isNull("添加的用户为空", users);
		checkId(group.getId(), "分组id为空");
		gp.checkGroupExist(group);

		checkUserIsReplicate(Arrays.asList(users));
		checkUsersHas(group, users);

		return gp.addGroupUsers(group, Arrays.asList(users)) == users.length;
	}

	@Override
	public void deleteGroupUser(Group group, User... users) throws IncompleteException, NotFoundException {

		isNull(group, "分组为空");
		isNull("添加的用户为空", users);
		checkId(group.getId(), "分组id为空");
		gp.checkGroupExist(group);

		gp.deleteGroupUsers(group, Arrays.asList(users));
	}

	@Override
	public boolean changeGroupUser(Group group)
			throws IncompleteException, NotFoundException, DataDuplicationException {

		isNull(group, "分组为空");
		checkId(group.getId(), "分组id为空");
		gp.checkGroupExist(group);

		UserApply ua = new UserApply();
		if (group.getUsers() != null) {
			for (User u : group.getUsers()) {
				isNull(u, "用户为空");
				checkId(u.getId(), "管理员id为空");
				ua.checkUserIsExit(u);
			}
		}

		checkUserIsReplicate(group.getUsers());

		return gp.updateGroupUsers(group);
	}

	@Override
	public boolean addGroupRole(Group group, Role... roles)
			throws IncompleteException, NotFoundException, DataDuplicationException {

		isNull(group, "分组为空");
		isNull("角色为空", roles);
		checkId(group.getId(), "分组id为空");

		RoleApply ra = new RoleApply();
		for (Role r : roles) {
			checkId(r.getId(), "角色id为空");
			ra.checkRoleIsExit(r);
		}
		gp.checkGroupExist(group);
		checkRoleIsReplicate(Arrays.asList(roles));
		checkRolesHas(group, roles);

		return gp.addGroupRoles(group, Arrays.asList(roles)) == roles.length;
	}

	@Override
	public boolean deleteGroupRole(Group group, Role... roles)
			throws IncompleteException, NotFoundException, DataDuplicationException {

		isNull(group, "分组为空");
		isNull("角色为空", roles);
		checkId(group.getId(), "分组id为空");

		for (Role r : roles)
			checkId(r.getId(), "角色id为空");

		gp.checkGroupExist(group);
		checkRoleIsReplicate(Arrays.asList(roles));

		return gp.deleteGroupRoles(group, Arrays.asList(roles)) == roles.length;
	}

	@Override
	public boolean changeGroupRole(Group group) throws IncompleteException, NotFoundException {

		List<Role> roles = group.getRoles();
		isNull(group, "分组为空");
		checkId(group.getId(), "分组id为空");
		gp.checkGroupExist(group);

		RoleApply ra = new RoleApply();
		for (Role r : roles) {
			isNull(r, "角色为空");
			checkId(r.getId(), "角色id为空");
			ra.checkRoleIsExit(r);
		}

		return gp.updateGroupRoles(group);
	}

	@Override
	public boolean changeGroupParent(Group group) throws IncompleteException, OperationsException, NotFoundException {

		isNull(group, "分组为空");
		checkId(group.getId(), "分组id为空");

		Group par = group.getParent();
		if (par != null)
			gp.checkGroupExist(par);

		Group g = new Group();
		g.setId(group.getId());
		g.setParent(group.getParent());

		return gp.updateGroupOnly(g) == 1;
	}

	private void checkUsersHas(Group group, User... users) throws DataDuplicationException {

		List<User> lu = gp.selectGroup(group.getId()).getUsers();
		List<Integer> lid = new ArrayList<Integer>();

		for (User u : lu)
			lid.add(u.getId());

		for (User a : users) {
			if (lid.contains(a.getId()))
				throw new DataDuplicationException("这个成员已存在");
		}
	}

	private void checkRolesHas(Group group, Role... roles) throws DataDuplicationException {

		List<Role> lr = gp.selectGroup(group.getId()).getRoles();
		List<Integer> lid = new ArrayList<Integer>();

		for (Role u : lr)
			lid.add(u.getId());

		for (Role a : roles) {
			if (lid.contains(a.getId()))
				throw new DataDuplicationException("这个角色已存在");
		}
	}

}
