package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.checkRoleIsReplicate;
import static com.cheejee.cms.tools.CheckTool.isNull;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cheejee.cms.apply.RoleApply;
import com.cheejee.cms.apply.UserApply;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.PersonalInfo;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.service.UserService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author CARRY ME HOME 2019年12月10日上午10:06:48
 */
public class UserServiceImpl implements UserService {

	private UserApply ua;

	public UserServiceImpl() {
		this.ua = new UserApply();
	}

	@Override
	public User login(User user) throws IncompleteException, OperationsException {

		isNull(user, "登录的用户对象为空");
		if (user.getName() == null || "".equals(user.getName()))
			throw new IncompleteException("用户名称为空");
		if (user.getPassword() == null || "".equals(user.getPassword()))
			throw new IncompleteException("用户密码为空");

		if (ua.checkUserPass(user)) {
			User u = ua.selectUserByName(user.getName());
			PersonalInfo p = u.getPersonalInfo();
			p.setLastLoginTime(Timestamp.from(Instant.now()));
			ua.updateUserPersonalInfo(p);

			return u;
		} else {
			return null;
		}
	}

	@Override
	public User loginAdmin(User user) throws IncompleteException, DataDuplicationException, OperationsException {

		isNull(user, "登录的用户对象为空");
		if (user.getName() == null || "".equals(user.getName()))
			throw new IncompleteException("用户名称为空");
		if (user.getPassword() == null || "".equals(user.getPassword()))
			throw new IncompleteException("用户密码为空");

		if (ua.checkUserPass(user)) {
			User u = ua.selectUserByName(user.getName());

			if (!ua.checkIsAdmin(u))
				throw new OperationsException("此用户不是管理员");

			return u;
		} else {
			return null;
		}
	}

	@Override
	public void logout(User user)
			throws IncompleteException, DataDuplicationException, OperationsException, NotFoundException {

	}

	@Override
	public void nullify(User user)
			throws IncompleteException, DataDuplicationException, OperationsException, NotFoundException {

		isNull(user, "用户对象为空");
		checkId(user.getId(), "用户对象id为空");
		checkIsSuperAdmin(user);
		ua.checkUserIsExit(user);

		user.setState(99);
		ua.updateUserOnly(user);
	}

	@Override
	public User getUserByIdManage(int id) {

		User user = ua.selectUser(id);

		return user;
	}

	@Override
	public User getUserEditByIdManage(int id) {
		User user = ua.selectUser(id);
		if (user != null) {
			user.getRoles();
			user.getCategorys();
			user.getContents();
			user.getGroups();
			user.getPersonalInfo();
		}
		return user;
	}

	@Override
	public User getUserById(int id) {

		User user = ua.selectUser(id);
		if (user != null) {
			user.setRoles(null);
			user.setState(null);
			user.getPersonalInfo();
		}
		return user;
	}

	@Override
	public User getUserEditById(int id) {
		User user = ua.selectUser(id);
		if (user != null) {
			user.getPersonalInfo();
			user.getRoles();
			user.setState(null);
		}
		return user;
	}

	@Override
	public User getUserByName(String name) {
		isNull(name, "用户名为空");
		return ua.selectUserByName(name);
	}

	@Override
	public PageInfo<User> getUserByName(int pageNum, int pageSize, String name) {
		isNull(name, "用户名为空");
		return ua.selectUserByName(pageNum, pageSize, name);
	}

	@Override
	public PageInfo<User> getUserAllByManage(int pageNum, int pageSize) {
		return ua.listUser(pageNum, pageSize);
	}

	@Override
	public PageInfo<User> getUserByStateByManage(int pageNum, int pageSize, Integer state) {
		isNull(state, "状态值为空");
		return ua.selectUserByState(pageNum, pageSize, state);
	}

	@Override
	public boolean register(User user) throws DataDuplicationException, IncompleteException, OperationsException {

		isNull(user, "添加的用户对象为空");

		if (user.getName() == null || user.getName().equals(""))
			throw new IncompleteException("用户名为空");

		if (user.getPassword() == null || user.getPassword().equals(""))
			throw new IncompleteException("用户密码为空");

		user.setState(1);
		return ua.addUser(user) == 1 ? true : false;
	}

	@Override
	public boolean changeUserByManage(User user)
			throws DataDuplicationException, NotFoundException, OperationsException, IncompleteException {

		isNull(user, "更新的用户对象为空");
		checkId(user.getId(), "用户对象的id为空");
		checkIsSuperAdmin(user);
		ua.checkUserIsExit(user);

		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			checkRoleIsReplicate(user.getRoles());
			RoleApply ra = new RoleApply();
			ra.checkRoleIsExit(user.getRoles().toArray(new Role[0]));
		}

		return ua.updateUser(user) == 1 ? true : false;
	}

	@Override
	public boolean changePassword(User user, String oldPass)
			throws IncompleteException, DataException, NoSuchAlgorithmException {

		isNull(user, "用户对象为空");
		isNull(user.getPassword(), "用户对象的密码为空");
		isNull(oldPass, "原密码为空");
		checkId(user.getId(), "用户对象的id为空");
		User u = new User(user.getName(), oldPass);

		if (!ua.checkUserPass(u))
			throw new DataException("用户原密码错误");

		return ua.updateUserPass(user) == 1;
	}

	@Override
	public boolean changeUserStateByManage(User user)
			throws IncompleteException, NotFoundException, OperationsException {

		isNull(user, "用户对象为空");
		isNull(user.getState(), "用户状态为空");

		checkId(user.getId(), "用户对象的id为空");
		checkIsSuperAdmin(user);
		ua.checkUserIsExit(user);

		User u = new User();
		u.setId(user.getId());
		u.setState(user.getState());

		return ua.updateUserOnly(u) == 1;
	}

	@Override
	public boolean changePersonalInfo(User user) throws IncompleteException, NotFoundException {

		isNull(user, "用户为空");
		isNull(user.getPersonalInfo(), "个人信息为空");
		checkId(user.getId(), "用户id为空");
		ua.checkUserIsExit(user);

		return ua.updateUserPersonalInfo(user.getPersonalInfo()) == 1;
	}

	@Override
	public boolean addUserRoleByManage(User user, Role... roles)
			throws IncompleteException, OperationsException, NotFoundException, DataDuplicationException {

		isNull(user, "用户对象为空");
		isNull("添加的角色为空", roles);

		checkId(user.getId(), "用户对象的id为空");

		for (Role r : roles)
			checkId(r.getId(), "添加的角色id为空");

		checkIsSuperAdmin(user);
		ua.checkUserIsExit(user);

		RoleApply ra = new RoleApply();
		ra.checkRoleIsExit(roles);

		checkRoleIsReplicate(Arrays.asList(roles));
		checkUserHasRole(user, roles);

		return ua.addUserRoles(user, Arrays.asList(roles)) == roles.length;
	}

	@Override
	public boolean deleteUserRoleByManage(User user, Role... roles)
			throws IncompleteException, NotFoundException, OperationsException {

		isNull(user, "用户对象为空");
		isNull("删除的角色为空", roles);
		checkId(user.getId(), "用户对象的id为空");

		for (Role r : roles)
			checkId(r.getId(), "删除的角色id为空");

		checkIsSuperAdmin(user);
		ua.checkUserIsExit(user);

		return ua.deleteUserRoles(user, Arrays.asList(roles)) == roles.length;
	}

	@Override
	public boolean changeUserRoleByManage(User user)
			throws IncompleteException, NotFoundException, OperationsException, DataDuplicationException {

		isNull(user, "用户对象为空");
		List<Role> lr = user.getRoles();

		if (lr != null && !lr.isEmpty()) {
			for (Role r : lr)
				checkId(r.getId(), "角色id为空");

			checkRoleIsReplicate(lr);
			RoleApply ra = new RoleApply();
			ra.checkRoleIsExit(user.getRoles().toArray(new Role[0]));
		}
		checkIsSuperAdmin(user);

		return ua.updateUserRole(user);
	}

	@Override
	public boolean sendVerification(User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retrievePassword(User user, String userCode, String sysCode, String newPass)
			throws DataException, IncompleteException, NoSuchAlgorithmException {

		isNull(user, "");
		isNull(userCode, "");
		isNull(sysCode, "");
		isNull(newPass, "");
		checkId(user.getId(), "");

		if (userCode.equals(sysCode))
			throw new DataException("验证码错误");
		user.setPassword(newPass);

		return ua.updateUserPass(user) == 1;
	}

	@Override
	public boolean setAdmin(User... users) throws IncompleteException, NotFoundException, OperationsException {

		isNull(users, "用户为空");
		
		for (User u : users) {
			isNull(u, "用户为空");
			checkId(u.getId(), "用户id为空");

			if (ua.selectUser(u.getId()) == null) {
				throw new NotFoundException("用户不存在");
			}
			if (ua.checkIsAdmin(u)) {
				throw new OperationsException("该用户已经是管理员：" + u);
			}
			addUserAdminRole(u);
		}

		return ua.addAdmin(users);
	}

	@Override
	public boolean revokeAdmin(User... users) throws IncompleteException, NotFoundException, OperationsException {

		isNull(users, "用户为空");
		for (User u : users) {
			isNull(u, "用户为空");
			checkId(u.getId(), "用户id为空");

			if (ua.selectUser(u.getId()) == null) {
				throw new NotFoundException("用户不存在");
			}
			if (!ua.checkIsAdmin(u)) {
				throw new OperationsException("该用户还不是管理员：" + u);
			}
			deleteUserAdminRole(u);
		}

		return ua.deleteAdmin(users);
	}

	/**
	 * 校验用户是否是超管，如果是则抛出一个异常
	 * 
	 * @param user
	 * @throws OperationsException
	 */
	private void checkIsSuperAdmin(User user) throws OperationsException {
		if (user == null || user.getId() == 0)
			return;

		if (user.getId() == 1)
			throw new OperationsException("此用户为超管账户，不能修改");
	}

	/**
	 * 检查用户是否拥有这些角色中的某一个，如果有则抛出异常。
	 * 
	 * @param user
	 * @param role
	 * @throws OperationsException
	 */
	private void checkUserHasRole(User user, Role... roles) throws OperationsException {

		List<Role> alr = ua.selectUser(user.getId()).getRoles();
		Set<Integer> set = new HashSet<Integer>();

		for (Role r : alr)
			set.add(r.getId());

		for (Role r : roles) {
			if (set.contains(r.getId()))
				throw new OperationsException("这个角色此户已经拥有，用户：" + user + " 角色：" + r);
		}
	}

	@Override
	public Date getUserLastLoginTime(String userName) {

		User user = ua.selectUserByName(userName);
		if (user == null)
			return null;

		return user.getPersonalInfo().getLastLoginTime();
	}

	@Override
	public boolean checkIsAdmin(User user) {

		try {
			ua.checkUserIsExit(user);
		} catch (NotFoundException e) {
			return false;
		}

		return ua.checkIsAdmin(user);
	}

	@Override
	public PageInfo<User> getAllAdmin(int pageNum, int pageSize) {
		return ua.selectAllAdmin(pageNum, pageSize);
	}

	@Override
	public String getPasswordByUserName(String name) {
		if (name == null)
			return null;
		return ua.selectPassword(name);
	}

	/**
	 * 删除用户的管理员角色
	 * @param user
	 */
	private void deleteUserAdminRole(User user) {
		ua.deleteUserRoles(user, buildAdminRole());
	}
	
	/**
	 * 添加管理员角色给用户
	 * @param user
	 */
	private void addUserAdminRole(User user) {
		ua.addUserRoles(user, buildAdminRole());
	}
	
	/**
	 * 构建出管理员角色的list
	 * @return
	 */
	private List<Role> buildAdminRole(){
		Role role = new Role();
		role.setId(3);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		
		return roles;
	}

}
