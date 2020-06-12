package com.cheejee.cms.tools;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.Group;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.Suffix;
import com.cheejee.cms.pojo.Type;
import com.cheejee.cms.pojo.User;

/**
 * 清洗对象中携带的id=0的属性
 * @author CARRY ME HOME
 * 2020年5月11日下午4:16:57
 */
public class WashTool {

	/**
	 * id=0的标签会被剔除
	 * @param c
	 */
	public static void washContent(Content c) {
		List<Attach> la = c.getAttachs();
		
		if(!CollectionUtils.isEmpty(la)) {
			la.removeIf(a -> a.getId() == 0);
		}
	}
	
	/**
	 * id=0的父组将会设置为null，id=0的子组，角色将会被剔除。
	 * @param g
	 */
	public static void washGroup(Group g) {
		Group p = g.getParent();
		List<Group> sl = g.getSons();
		List<User> ul = g.getUsers();
		List<Role> rl = g.getRoles();
		
		if(p != null) {
			if(p.getId() == 0) {
				g.setParent(null);
			}
		}
		
		if(!CollectionUtils.isEmpty(rl)) {
			rl.removeIf(r -> r.getId() == 0);
		}
		
		if(!CollectionUtils.isEmpty(ul)) {
			ul.removeIf(r -> r.getId() == 0);
		}
		
		if(!CollectionUtils.isEmpty(sl)) {
			sl.removeIf(r -> r.getId() == 0);
		}
	}
	
	/**
	 * 后缀id=0的将会被剔除
	 * @param t
	 */
	public static void washType(Type t) {
		List<Suffix> ls = t.getSuffixs();
		
		if(!CollectionUtils.isEmpty(ls)) {
			ls.removeIf(s -> s.getId() == 0);
		}
	}
	
	/**
	 * 去除id=0的角色，如果状态值为0则把状态设置为null
	 * @param user
	 */
	public static void washUser(User user) {
		if(user == null) {
			return;
		}
		
		if(user.getState() == 0) {
			user.setState(null);
		}
		
		List<Role> lr = user.getRoles();
		if(!CollectionUtils.isEmpty(lr)) {
			lr.removeIf(r -> r.getId() == 0);
		}
	}
}
