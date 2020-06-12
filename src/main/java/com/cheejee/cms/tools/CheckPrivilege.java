package com.cheejee.cms.tools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cheejee.cms.apply.PermissionApply;
import com.cheejee.cms.pojo.Group;
import com.cheejee.cms.pojo.Permission;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.service.UserService;
import com.cheejee.cms.service.impl.UserServiceImpl;

/**
 * 权限校验类
 * 
 * @author CARRY ME HOME 2019年12月5日下午3:28:52
 */
public class CheckPrivilege {

	/**
	 * 校验用户是否能够访问资源
	 * 
	 * @param user
	 * @param resName 资源名
	 * @return 能返回true；
	 */
	public static boolean check(User user, String resName) {
		
		UserService us = new UserServiceImpl();
		user = us.getUserEditByIdManage(user.getId());

		List<Group> groups = user.getGroups();
		List<Role> roles = user.getRoles();

		Set<Role> roleSet = new HashSet<Role>(roles);
		addGroupRoles(roleSet, groups);

		int rp = roleCheck(roleSet, resName);

		if (rp == 3)
			return false;

		if (rp == 2)
			return true;

		return false;
	}

	/**
	 * 把所有组的角色取到set集合中，包括父组。
	 * 
	 * @param roles
	 * @param groups
	 */
	private static void addGroupRoles(Set<Role> roles, List<Group> groups) {
		Set<Group> groupsSet = new HashSet<Group>();
		for (Group g : groups) {
			groupsSet.add(g);
			addParentGroup(groupsSet, g);
		}
		for (Group g : groupsSet) {
			roles.addAll(g.getRoles());
		}
	}

	/**
	 * 把这个组的父组添加到set中
	 * 
	 * @param groups
	 * @param group
	 */
	private static void addParentGroup(Set<Group> groups, Group group) {

		while (group.getParent() != null) {
			group = group.getParent();
			groups.add(group);
		}
	}

	/**
	 * 角色校验，否定返回3，通过返回2，默认返回1.
	 * 
	 * @param roles
	 * @param res
	 * @param pa
	 * @return
	 */
	private static int roleCheck(Set<Role> roles, String res) {

		if (roles == null || roles.isEmpty())
			return 1;

		int a = 1;
		PermissionApply pa = new PermissionApply();

		for (Role r : roles) {
			Permission p = pa.selectPermisByRoleAndRes(r, res);
			if (p == null)
				continue;

			int b = p.getPrivilege();
			if (b == 0)
				return 3;
			if (b == 1)
				a = 2;
		}
		return a;
	}
}
