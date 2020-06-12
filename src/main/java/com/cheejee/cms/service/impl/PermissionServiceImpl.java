package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.isNull;

import java.util.Arrays;

import com.cheejee.cms.apply.PermissionApply;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.pojo.Permission;
import com.cheejee.cms.pojo.Resource;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.service.PermissionService;
import com.github.pagehelper.PageInfo;


/**
 * 
 * @author CARRY ME HOME
 * 2020年1月5日下午3:58:14
 */
public class PermissionServiceImpl implements PermissionService {
	
	private PermissionApply pa;
	
	public PermissionServiceImpl() {
		this.pa = new PermissionApply();
	}

	@Override
	public Permission getPermissionById(int id) {
		return pa.selectPermission(id);
	}

	@Override
	public Permission getPermisionByRoleAndResource(Role role, Resource resource) throws IncompleteException {
		
		isNull(role, "角色为空");
		isNull(resource.getName(), "资源名称为空");
		checkId(role.getId(), "角色id为空");
		
		return pa.selectPermisByRoleAndRes(role, resource.getName());
	}

	@Override
	public PageInfo<Permission> getPermissionAll(int pageNum, int pageSize) {
		return pa.listPermission(pageNum, pageSize);
	}

	@Override
	public boolean addPermission(Permission... permissions) throws IncompleteException, DataDuplicationException {
		
		for(Permission p : permissions) {
			isNull(p, "权限为空");
			isNull(p.getResource(), "权限资源为空");
			isNull(p.getRole(), "权限角色为空");
			checkId(p.getResource().getId(), "权限资源id为空");
			checkId(p.getRole().getId(), "权限角色id为空");
			
			if(p.getPrivilege() != 1 && p.getPrivilege() != 0)
				p.setPrivilege((byte) 1);
			
		}
		
		return pa.addPermission(Arrays.asList(permissions)) == permissions.length;
	}

	@Override
	public void deletePermission(Permission... permissions) throws IncompleteException {
		
		for(Permission p : permissions) {
			isNull(p, "权限为空");
			checkId(p.getId(), "权限id为空");
		}
		
		pa.deletePermissions(Arrays.asList(permissions));
	}

	@Override
	public void closePermission(Permission... permissions) throws IncompleteException {
		
		for(Permission p : permissions) {
			isNull(p, "权限为空");
			checkId(p.getId(), "权限id为空");
			p.setPrivilege((byte) 0);
			pa.updatePermission(permissions);
		}
		
	}

	@Override
	public void openPermission(Permission... permissions) throws IncompleteException {
		for(Permission p : permissions) {
			isNull(p, "权限为空");
			checkId(p.getId(), "权限id为空");
			p.setPrivilege((byte) 1);
			pa.updatePermission(permissions);
		}
	}

}
