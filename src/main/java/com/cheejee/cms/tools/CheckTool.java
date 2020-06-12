package com.cheejee.cms.tools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.pojo.Category;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.Suffix;
import com.cheejee.cms.pojo.Tag;
import com.cheejee.cms.pojo.User;

/**
 * 对数据进行一些基本的校验
 * 
 * @author CARRY ME HOME 2019年12月15日下午6:17:32
 */
public class CheckTool {

	/**
	 * 遍历数组判断是否有对象为空
	 * 
	 * @param obj
	 * @param message
	 * @throws NullPointerException 对象为空则会抛出此异常
	 */
	public static void isNull(String message, Object[] obj) throws NullPointerException {

		if (obj == null)
			throw new NullPointerException(message);
		for (Object o : obj) {
			if (o == null)
				throw new NullPointerException(message);
		}
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param object
	 * @param message
	 */
	public static void isNull(Object obj, String message) throws NullPointerException {
		if (obj == null)
			throw new NullPointerException(message);
	}

	/**
	 * 校验用户与id与传入的id是否相同
	 * 
	 * @param id
	 * @param user
	 * @param message
	 * @throws InsufficientPermissionException id不想同则会抛出此异常
	 */
	public static void matchUser(int id, User user, String message) throws InsufficientPermissionException {
		if (id != user.getId())
			throw new InsufficientPermissionException(message);
	}

	/**
	 * 校验id是否为0
	 * 
	 * @param id
	 * @param message
	 * @throws IncompleteException id为0时抛出此异常，表示对象信息不完全
	 */
	public static void checkId(int id, String message) throws IncompleteException {
		if (id == 0)
			throw new IncompleteException(message);
	}

	/**
	 * 校验角色是否有重复，通过判断角色id是否重复进行判断
	 * 
	 * @param roles
	 * @return 存在重复返回true
	 * @throws DataDuplicationException 
	 */
	public static boolean checkRoleIsReplicate(List<Role> roles) throws DataDuplicationException {

		if (roles == null || roles.isEmpty())
			return false;

		Set<Integer> set = new HashSet<Integer>();
		for (Role r : roles) {
			set.add(r.getId());
		}

		if (roles.size() != set.size()) {
			throw new DataDuplicationException("角色重复");
		} else {
			return true;
		}
	}

	public static boolean checkCategoryIsReplicate(List<Category> category) throws DataDuplicationException {

		if (category == null || category.isEmpty())
			return false;

		Set<Integer> set = new HashSet<Integer>();
		for (Category r : category) {
			set.add(r.getId());
		}

		if (category.size() != set.size()) {
			throw new DataDuplicationException("分类重复");
		} else {
			return true;
		}
	}

	public static boolean checkTagIsReplicate(List<Tag> tags) throws DataDuplicationException {

		if (tags == null || tags.isEmpty())
			return false;

		Set<Integer> set = new HashSet<Integer>();
		for (Tag r : tags) {
			set.add(r.getId());
		}

		if (tags.size() != set.size()) {
			throw new DataDuplicationException("标签重复");
		} else {
			return true;
		}
	}

	public static boolean checkAttachIsReplicate(List<Attach> attach) throws DataDuplicationException {

		if (attach == null || attach.isEmpty())
			return false;

		Set<Integer> set = new HashSet<Integer>();
		for (Attach r : attach) {
			set.add(r.getId());
		}

		if (attach.size() != set.size()) {
			throw new DataDuplicationException("附件重复");
		} else {
			return true;
		}
	}
	
	public static boolean checkUserIsReplicate(List<User> users) throws DataDuplicationException {
		
		if (users == null || users.isEmpty())
			return false;
		
		Set<Integer> set = new HashSet<Integer>();
		for (User r : users) {
			set.add(r.getId());
		}
		
		if (users.size() != set.size()) {
			throw new DataDuplicationException("用户重复");
		} else {
			return true;
		}
	}
	
	public static boolean checkSuffixIsReplicate(List<Suffix> suffixs) throws DataDuplicationException {
		
		if (suffixs == null || suffixs.isEmpty())
			return false;
		
		Set<Integer> set = new HashSet<Integer>();
		for (Suffix r : suffixs) {
			set.add(r.getId());
		}
		
		if (suffixs.size() != set.size()) {
			throw new DataDuplicationException("后缀重复");
		} else {
			return true;
		}
	}
}
