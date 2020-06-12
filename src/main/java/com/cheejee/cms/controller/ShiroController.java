package com.cheejee.cms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheejee.cms.response.SimpleResponseEntity;

/**
 * 用于给shiro做跳转，返回统一格式的数据
 * @author CARRY ME HOME
 * 2020年4月12日上午10:12:56
 */
@RestController
@RequestMapping("/shiro")
public class ShiroController {

	@GetMapping("/noLogin")
	public SimpleResponseEntity noLogin() {
		return SimpleResponseEntity.builder().code(401).message("用户未登录").build();
	}
	
	@GetMapping("/unauthorized")
	public SimpleResponseEntity unauthorized() {
		return SimpleResponseEntity.builder().code(403).message("权限不足").build();
	}
}
