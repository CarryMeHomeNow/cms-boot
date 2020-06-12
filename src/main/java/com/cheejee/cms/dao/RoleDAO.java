package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;

public interface RoleDAO {
	
	Role selectRole(int id);
	
	List<Role> selectRoleByName(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize, @Param("name")String name);
	
	Role selectRoleByNameExact(String name);
	
	int selectRoleNameCount(Role role);
	
	int selectRoleNameCountExSelf(Role role);
	
	List<Role> selectRoleByCreator(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize, @Param("user")User user);
	
	List<Role> listRole(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize);

	int addRole(Role role);
	
	int deleteRole(Role role);
	
	int updateRole(Role role);
}
