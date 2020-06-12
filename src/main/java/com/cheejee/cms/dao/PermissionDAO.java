package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Permission;
import com.cheejee.cms.pojo.Resource;
import com.cheejee.cms.pojo.Role;

public interface PermissionDAO {
	
	Permission selectPermission(int id);
	
	Permission selectPermisByRoleAndResName(@Param("role")Role role, @Param("resource")String resName);
	
	int selectCountByRoleAndRes( @Param("role")Role role, @Param("resource")Resource res);
	
	List<Permission> listPermission(@Param("pageNumKey")int pageNum, @Param("pageSizeKey")int pageSize);
	
	int addPermission(Permission permission);
	
	int addPermissions(@Param("permission")List<Permission> permissions);
	
	int deletePermission(Permission permission);
	
	int deletePermissionByRole(Role role);
	
	int updatePermission(Permission permission);
}
