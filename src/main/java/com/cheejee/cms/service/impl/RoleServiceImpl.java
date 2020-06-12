package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.isNull;

import java.util.ArrayList;
import java.util.List;

import com.cheejee.cms.apply.PermissionApply;
import com.cheejee.cms.apply.RoleApply;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Permission;
import com.cheejee.cms.pojo.Resource;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.service.RoleService;
import com.github.pagehelper.PageInfo;

public class RoleServiceImpl implements RoleService {

	private RoleApply ra;

	public RoleServiceImpl() {
		this.ra = new RoleApply();
	}

	@Override
	public Role getRoleById(int id) {
		return ra.selectRole(id);
	}

	@Override
	public Role getRoleEditById(int id) {
		Role r = ra.selectRole(id);
		if(r != null)
		r.getPermissions();

		return r;
	}

	@Override
	public PageInfo<Role> getRoleByName(int pageNum, int pageSize, String name) {
		isNull(name, "名称为空");
		return ra.selectRoleByName(pageNum, pageSize, name);
	}

	@Override
	public PageInfo<Role> getRoleAll(int pageNum, int pageSize) {
		return ra.listRole(pageNum, pageSize);
	}

	@Override
	public boolean addRole(Role role) throws DataDuplicationException, IncompleteException {

		isNull(role, "角色为空");
		isNull(role.getCreator(), "角色创建者为空");

		if (role.getName() == null || "".equals(role.getName()))
			throw new NullPointerException();

		return ra.addRole(role) == 1;
	}

	@Override
	public boolean deleteRole(Role role) throws IncompleteException, OperationsException {

		isNull(role, "角色为空");
		checkId(role.getId(), "角色没有id");

		if (!ra.selectRole(role.getId()).getUsers().isEmpty())
			throw new OperationsException("删除的角色还有用户在使用");

		if (role.getId() < 4)
			throw new OperationsException("该角色不能被删除");

		return ra.deleteRole(role) == 1;
	}

	@Override
	public boolean deleteRoleForce(Role role) throws IncompleteException, OperationsException {

		isNull(role, "角色为空");
		checkId(role.getId(), "角色没有id");

		if (role.getId() == 1 || role.getId() == 2)
			throw new OperationsException("该角色不能被删除");

		return ra.deleteRole(role) == 1;
	}

	@Override
	public boolean changeRole(Role role) throws DataDuplicationException, IncompleteException, NotFoundException {

		isNull(role, "角色为空");
		checkId(role.getId(), "角色没有id");
		
		if(role.getName() == null || role.getName().equals(""))
			throw new IncompleteException("角色名称为空");
		
		ra.checkRoleIsExit(role);

		return ra.updateRole(role) == 1;
	}

	@Override
	public boolean addRoleAccessResource(Role role, Resource... resources)
			throws DataDuplicationException, IncompleteException {

		isNull(role, "角色为空");
		isNull("资源为空", resources);
		checkId(role.getId(), "角色id为空");

		List<Permission> lp = new ArrayList<Permission>();
		PermissionApply pa = new PermissionApply();

		for (Resource r : resources) {
			checkId(r.getId(), "资源id为空");
			lp.add(new Permission((byte) 1, role, r));
		}

		return pa.addPermission(lp) == resources.length;
	}

	@Override
	public void deleteRoleAccessResource(Role role, Resource... resources) throws IncompleteException {

		isNull(role, "角色为空");
		isNull("资源为空", resources);
		checkId(role.getId(), "角色为空");

		List<Permission> lp = new ArrayList<Permission>();
		PermissionApply pa = new PermissionApply();

		for (Resource r : resources) {
			checkId(r.getId(), "资源id为空");
			Permission p = pa.selectPermisByRoleAndRes(role, r.getName());

			if (p != null)
				lp.add(p);
		}
		pa.deletePermissions(lp);
	}

	@Override
	public void disableRoleAccessResource(Role role, Resource... resources) throws IncompleteException {

		isNull(role, "角色为空");
		isNull("资源为空", resources);
		checkId(role.getId(), "角色为空");

		List<Permission> lp = new ArrayList<Permission>();
		PermissionApply pa = new PermissionApply();

		for (Resource r : resources) {
			checkId(r.getId(), "资源id为空");
			Permission p = pa.selectPermisByRoleAndRes(role, r.getName());
			if (p != null) {
				p.setPrivilege((byte) 0);
				lp.add(p);
			}
		}
		pa.updatePermission(lp.toArray(new Permission[0]));
	}

	@Override
	public void enableRoleAccessResource(Role role, Resource... resources) throws IncompleteException {

		isNull(role, "角色为空");
		isNull("资源为空", resources);
		checkId(role.getId(), "角色为空");

		List<Permission> lp = new ArrayList<Permission>();
		PermissionApply pa = new PermissionApply();

		for (Resource r : resources) {
			checkId(r.getId(), "资源id为空");
			Permission p = pa.selectPermisByRoleAndRes(role, r.getName());
			if (p != null) {
				p.setPrivilege((byte) 1);
				lp.add(p);
			}
		}
		pa.updatePermission(lp.toArray(new Permission[0]));
	}

}
