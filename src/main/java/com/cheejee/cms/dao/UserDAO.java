package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;

public interface UserDAO {
	
	String selectPassword(String name);

	User selectUser(int id);

	List<User> selectUserByName(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize,
			@Param("name") String name);

	User selectUserByNameExact(String name);
	
	List<User> selectAllAdmin(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize);

	List<User> listUser(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize);

	int selectUserNameCount(User user);

	int selectCountByNameAndPass(User user);

	int selectUserNameCountExSelf(User user);

	List<User> selectUserByState(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize,
			@Param("state") Integer state);

	int addUser(User user);

	int addUserRoles(@Param("user") User user, @Param("roles") List<Role> roles);

	int deleteUserRoles(@Param("user") User user, @Param("roles") List<Role> roles);

	int deleteUserRoleAll(@Param("user") User user);

	int deleteUser(User user);

	int updateUser(User user);

	int updateUserPass(User user);
	
	int checkIsAdmin(User user);

	int addAdmin(@Param("users")User...users);
	
	int deleteAdmin(@Param("users")User...users);

}
