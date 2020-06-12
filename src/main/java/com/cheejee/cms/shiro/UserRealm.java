
package com.cheejee.cms.shiro;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Permission;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 角色填充角色id，资源填充的资源名。因为角色名称可以被用户更改。
 * @author CARRY ME HOME 2020年3月7日上午10:31:38
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

	private static final String ROLE = "role";
	private static final String PERMISSION = "permission";

	@Resource
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.debug("用户{}开始授权。。。", principals.getPrimaryPrincipal());

		int id = userService.getUserByName(principals.getPrimaryPrincipal()
				.toString())
				.getId();
		Map<String, Set<String>> roleAndPermission = getRoleAndPermission(id);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(roleAndPermission.get(ROLE));
		info.addStringPermissions(roleAndPermission.get(PERMISSION));

		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		User user = null;
		UsernamePasswordToken ut = (UsernamePasswordToken) token;

		try {
			User tu = new User(ut.getUsername(), String.valueOf(ut.getPassword()));
			log.debug("开始认证：{} : {}", tu.getName(), tu.getPassword());
			user = userService.login(tu);

		} catch (NoSuchAlgorithmException | IncompleteException | DataDuplicationException | OperationsException e) {
			log.error("用户{}认证时发生了异常{}", ut.getUsername(), e);
			return null;
		} 

		if (user != null) {
			return new SimpleAuthenticationInfo(user.getName(), userService.getPasswordByUserName(user.getName()),
					getName());
		} else {
			return null;
		}
	}

	/**
	 * 获取此用户的所有角色，包括用户拥有的角色和用户所属组的角色。
	 * 
	 * @param userId
	 * @return
	 */
	private Set<Role> getUserAllRole(int userId) {
		User user = userService.getUserByIdManage(userId);
		Set<Role> allRole = new HashSet<>(user.getRoles());
		allRole.addAll(user.getRoles());

		user.getGroups()
				.forEach(group -> {
					allRole.addAll(group.getRoles());
					
					while(group.getParent() != null) {
						group = group.getParent();
						allRole.addAll(group.getRoles());
					}
				});
		
		return allRole;
	}

	/**
	 * 获取用户所有角色的角色名称和用户拥有的权限的名称
	 * 
	 * @param userId
	 * @return
	 */
	private Map<String, Set<String>> getRoleAndPermission(int userId) {
		Set<String> roles = new HashSet<>();
		Set<String> permissions = new HashSet<>();
		Set<String> disPermission = new HashSet<String>();

		getUserAllRole(userId).forEach(role -> {
			roles.add(String.valueOf(role.getId()));

			role.getPermissions().forEach(permission -> {
				permissions.add(permission.getResource().getName());
				addDisPermission(disPermission, permission);
			});
		});

		permissions.removeAll(disPermission);
		Map<String, Set<String>> roleAndPermission = new HashMap<>(2);
		roleAndPermission.put(ROLE, roles);
		roleAndPermission.put(PERMISSION, permissions);

		return roleAndPermission;
	}
	
	private void addDisPermission(Set<String> disPermission, Permission permission) {
		if(permission.getPrivilege() == 0) {
			disPermission.add(permission.getResource().getName());
		}
	}

}
