package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Group;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;

public interface GroupDAO {

	Group selectGroup(int id);

	Group selectGroupByNameExact(String name);

	List<Group> selectGroupByName(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize,
			@Param("name") String name);

	List<Group> selectGroupNoParent(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize);

	List<Group> listGroup(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize);

	int selectGroupNameCount(Group group);

	int selectGroupNameCountExSelf(Group group);

	int selectCountByGroupSon(Group group);

	int addGroup(@Param("groups") Group... groups);

	int addGroupRoles(@Param("group") Group group, @Param("roles") List<Role> roles);

	int addGroupUsers(@Param("group") Group group, @Param("users") List<User> users);

	int deleteGroupUsers(@Param("group") Group group, @Param("users") List<User> users);

	int deleteGroupUserAll(Group group);

	int deleteGroupRoles(@Param("group") Group group, @Param("roles") List<Role> roles);

	int deleteGroupRoleAll(Group group);

	int deleteGroup(Group group);

	int updateGroup(Group group);
}
