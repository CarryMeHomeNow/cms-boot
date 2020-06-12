package com.cheejee.cms.common;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import com.cheejee.cms.pojo.User;
import com.cheejee.cms.service.UserService;

/**
 * 
 * @author CARRY ME HOME
 * 2020年4月16日下午2:07:58
 */
@Component
public class CurrentAppInfo {
	
	@Resource
	private UserService userService;

	/**
	 * 获取当前用户
	 * @return
	 */
	public User getCurrentUser() {
		Subject subject = SecurityUtils.getSubject();
		String userName = subject.getPrincipal().toString();
		
		return userName != null ? userService.getUserByName(userName) : null;
	}
}
