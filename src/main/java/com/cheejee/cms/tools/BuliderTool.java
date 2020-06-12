package com.cheejee.cms.tools;

import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.pojo.Category;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.Group;
import com.cheejee.cms.pojo.Resource;
import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.Suffix;
import com.cheejee.cms.pojo.Type;
import com.cheejee.cms.pojo.User;

/**
 * 用来构建一些对象
 * 
 * @author CARRY ME HOME 2020年4月18日下午3:17:56
 */
public class BuliderTool {

	public static Category bulidCategory(int id, String name) {
		Category category = new Category();
		category.setId(id);
		category.setName(name);

		return category;
	}

	public static Group buildGroup(int id) {
		Group group = new Group();
		group.setId(id);

		return group;
	}

	public static User buildUser(int id) {
		User user = new User();
		user.setId(id);

		return user;
	}
	
	public static Role buildRole(int id) {
		Role role = new Role();
		role.setId(id);
		
		return role;
	}
	
	public static Resource buildResource(int id) {
		Resource resource = new Resource();
		resource.setId(id);
		
		return resource;
	}

	public static Content buildContent(int id) {
		Content content = new Content();
		content.setId(id);
		
		return content;
	}
	
	public static Content[] buildContent(int[] id) {
		Content[] c = new Content[id.length];
		for(int i = 0; i < id.length; i++) {
			if(id[i] != 0) {
				c[i] = buildContent(id[i]);
			}
		}
		
		return c;
	}

	public static Type buildType(int typeId) {
		Type type = new Type();
		type.setId(typeId);
		
		return type;
	}

	public static Suffix buildSuffix(int id, String describe) {
		Suffix suffix = new Suffix();
		suffix.setId(id);
		suffix.setDescribe(describe);
		
		return suffix;
	}

	public static Attach buildAttach(String name) {
		Attach a = new Attach();
		a.setName(name);
		
		return a;
	}
}
