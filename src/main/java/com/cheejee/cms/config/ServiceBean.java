package com.cheejee.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cheejee.cms.service.AttachService;
import com.cheejee.cms.service.CategoryService;
import com.cheejee.cms.service.ContentService;
import com.cheejee.cms.service.GroupService;
import com.cheejee.cms.service.LogService;
import com.cheejee.cms.service.PermissionService;
import com.cheejee.cms.service.ResourceService;
import com.cheejee.cms.service.RoleService;
import com.cheejee.cms.service.SettingService;
import com.cheejee.cms.service.SuffixService;
import com.cheejee.cms.service.TagService;
import com.cheejee.cms.service.TypeService;
import com.cheejee.cms.service.UserService;
import com.cheejee.cms.service.impl.AttachServiceImpl;
import com.cheejee.cms.service.impl.CategoryServiceImpl;
import com.cheejee.cms.service.impl.ContentServiceImpl;
import com.cheejee.cms.service.impl.GroupSerivceImpl;
import com.cheejee.cms.service.impl.LogServiceImpl;
import com.cheejee.cms.service.impl.PermissionServiceImpl;
import com.cheejee.cms.service.impl.ResourceServiceImpl;
import com.cheejee.cms.service.impl.RoleServiceImpl;
import com.cheejee.cms.service.impl.SettingServiceImpl;
import com.cheejee.cms.service.impl.SuffixServiceImpl;
import com.cheejee.cms.service.impl.TagServiceImpl;
import com.cheejee.cms.service.impl.TypeServiceImpl;
import com.cheejee.cms.service.impl.UserServiceImpl;

/**
 * 
 * @author CARRY ME HOME
 * 2020年3月8日下午4:24:06
 */
@Configuration
public class ServiceBean {
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
	
	@Bean
	public AttachService attachService() {
		return new AttachServiceImpl();
	}
	
	@Bean
	public CategoryService categoryService() {
		return new CategoryServiceImpl();
	}
	
	@Bean
	public ContentService contentService() {
		return new ContentServiceImpl();
	}
	
	@Bean
	public GroupService groupService() {
		return new GroupSerivceImpl();
	}
	
	@Bean
	public LogService logService() {
		return new LogServiceImpl();
	}
	
	@Bean 
	public PermissionService permissionService() {
		return new PermissionServiceImpl();
	}
	
	@Bean 
	public ResourceService resourceService() {
		return new ResourceServiceImpl();
	}
	
	@Bean 
	public RoleService roleService() {
		return new RoleServiceImpl();
	}
	
	@Bean 
	public SettingService settingService() {
		return new SettingServiceImpl();
	}
	
	@Bean 
	public SuffixService suffixService() {
		return new SuffixServiceImpl();
	}
	
	@Bean 
	public TagService tagService() {
		return new TagServiceImpl();
	}
	
	@Bean 
	public TypeService typeService() {
		return new TypeServiceImpl();
	}
	
}
