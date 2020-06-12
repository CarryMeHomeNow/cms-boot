package com.cheejee.cms.apply;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.PersonalInfoDAO;
import com.cheejee.cms.dao.RoleDAO;
import com.cheejee.cms.dao.UserDAO;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.PersonalInfo;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class UserApply {

	SqlSessionFactory ssf;

	public UserApply() {
		this.ssf = SSF.getSSF();
	}

	public boolean checkUserPass(User user) {
		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			int a = ud.selectCountByNameAndPass(user);
			return a == 1 ? true : false;
		}
	}

	public String selectPassword(String name) {
		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			return ud.selectPassword(name);
		}
	}
	
	public User selectUser(int id) {
		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			return ud.selectUser(id);
		}
	}

	public User selectUserByName(String name) {
		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			return ud.selectUserByNameExact(name);
		}
	}

	public PageInfo<User> selectUserByName(int pagNum, int pageSize, String name) {
		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			return new PageInfo<User>(ud.selectUserByName(pagNum, pageSize, name));
		}
	}

	public PageInfo<User> selectUserByState(int pagNum, int pageSize, Integer state) {
		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			return new PageInfo<User>(ud.selectUserByState(pagNum, pageSize, state));
		}
	}
	
	public PageInfo<User> selectAllAdmin(int pagNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			return new PageInfo<User>(ud.selectAllAdmin(pagNum, pageSize));
		}
	}

	public PageInfo<User> listUser(int pageNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			return new PageInfo<User>(ud.listUser(pageNum, pageSize));
		}
	}

	/**
	 * 用户注册成功后给用户赋予默认的角色.
	 * 
	 * @param user
	 * @return
	 * @throws DataDuplicationException
	 * @throws OperationsException
	 */
	public int addUser(User user) throws DataDuplicationException, OperationsException {

		try (SqlSession session = ssf.openSession()) {

			UserDAO ud = session.getMapper(UserDAO.class);
			RoleDAO rd = session.getMapper(RoleDAO.class);
			PersonalInfoDAO pd = session.getMapper(PersonalInfoDAO.class);

			if (ud.selectUserNameCount(user) != 0)
				throw new DataDuplicationException("用户名已存在：" + user.getName());

			List<Role> lr = new ArrayList<Role>();
			lr.add(rd.selectRole(1));

			int a = ud.addUser(user);
			ud.addUserRoles(user, lr);
			addPersonalInfo(user, pd);

			session.commit();
			return a;
		}
	}

	private void addPersonalInfo(User user, PersonalInfoDAO pd) throws OperationsException {

		int a = 0;
		PersonalInfo pi = user.getPersonalInfo();

		if (pi == null)
			pi = new PersonalInfo(null, null, user.getId());

		a = pd.addPersonalInfo(pi);
		if (a != 1)
			throw new OperationsException("添加用户个人信息时失败");
	}

	public int addUserRoles(User user, List<Role> roles) {

		if (user == null || roles == null || roles.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			int a = ud.addUserRoles(user, roles);
			session.commit();
			return a;
		}
	}

	public int deleteUserRoles(User user, List<Role> roles) {

		if (user == null || roles == null || roles.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			int a = ud.deleteUserRoles(user, roles);
			session.commit();
			return a;
		}
	}

	public int deleteUserRoleAll(User user) {

		if (user == null)
			return 0;

		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			int a = ud.deleteUserRoleAll(user);
			session.commit();
			return a;
		}
	}

	public int deleteUser(User user) {

		if (user == null)
			return 0;

		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			int a = ud.deleteUser(user);
			session.commit();
			return a;
		}
	}

	/**
	 * 事务更新用户的属性和用户拥有的角色
	 * 
	 * @param user
	 * @return
	 * @throws DataDuplicationException 修改的用户名已存在
	 * @throws OperationsException      用户角色或者用户个人信息添加失败
	 */
	public int updateUser(User user) throws DataDuplicationException, OperationsException {

		try (SqlSession session = ssf.openSession()) {
			UserDAO ud = session.getMapper(UserDAO.class);
			PersonalInfoDAO pd = session.getMapper(PersonalInfoDAO.class);

			int a = ud.updateUser(user);
			updateUserRoles(user, ud);
			updatePersonalInfo(user, pd);
			session.commit();

			return a;
		}
	}

	private void updateUserRoles(User user, UserDAO ud) throws OperationsException {

		int a = 0;
		int b = 0;
		List<Role> ur = user.getRoles();

		if (ur != null)
			b = ur.size();

		ud.deleteUserRoleAll(user);
		if (ur != null && !ur.isEmpty())
			a = ud.addUserRoles(user, ur);

		if (a != b)
			throw new OperationsException("更新用户角色失败");
	}

	private void updatePersonalInfo(User user, PersonalInfoDAO pd) throws OperationsException {

		PersonalInfo pi = user.getPersonalInfo();
		if (pi == null)
			return;

		if (pd.updatePersonalInfo(pi) != 1)
			throw new OperationsException("更新用户个人信息失败");
	}

	/**
	 * 更新用户的个人信息
	 * 
	 * @param pi
	 * @return
	 */
	public int updateUserPersonalInfo(PersonalInfo pi) {
		try (SqlSession session = ssf.openSession(true)) {
			PersonalInfoDAO pd = session.getMapper(PersonalInfoDAO.class);
			return pd.updatePersonalInfo(pi);
		}
	}

	public int updateUserPass(User user) {
		try (SqlSession session = ssf.openSession(true)) {
			UserDAO ud = session.getMapper(UserDAO.class);

			return ud.updateUserPass(user);
		}
	}

	public boolean updateUserRole(User user) {

		try (SqlSession session = ssf.openSession(true)) {
			UserDAO ud = session.getMapper(UserDAO.class);
			updateUserRoles(user, ud);
			return true;

		} catch (OperationsException e) {
			return false;
		}
	}

	public int updateUserOnly(User user) {
		
		try (SqlSession session = ssf.openSession(true)) {
			UserDAO ud = session.getMapper(UserDAO.class);

			return ud.updateUser(user);
		}
	}
	
	/**
	 * 校验用户是否是管理员
	 * @param user
	 * @return 是管理员返回true
	 */
	public boolean checkIsAdmin(User user) {
		try (SqlSession session = ssf.openSession(true)) {
			UserDAO ud = session.getMapper(UserDAO.class);
			int i = ud.checkIsAdmin(user);
			return i == 1;
		}
	}
	
	
	public boolean addAdmin(User...users) {
		try (SqlSession session = ssf.openSession(true)) {
			UserDAO ud = session.getMapper(UserDAO.class);
			
			return ud.addAdmin(users) == users.length;
		}
	}
	
	public boolean deleteAdmin(User...users) {
		try (SqlSession session = ssf.openSession(true)) {
			UserDAO ud = session.getMapper(UserDAO.class);
			
			return ud.deleteAdmin(users) == users.length;
		}
	}
	
	/**
	 * 校验用户是否存在，不存在抛出异常
	 * @param users
	 * @throws NotFoundException
	 */
	public void checkUserIsExit(User...users) throws NotFoundException {
		
		if(users == null || users.length == 0)
			return;
		
		try (SqlSession session = ssf.openSession(true)) {
			UserDAO ud = session.getMapper(UserDAO.class);
			
			for(User u : users) {
				if(ud.selectUser(u.getId()) == null)
					throw new NotFoundException("用户不存在");
			}
			
		}
	}

}
