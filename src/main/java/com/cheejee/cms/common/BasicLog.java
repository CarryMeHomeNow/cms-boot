package com.cheejee.cms.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.pojo.Log;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.service.LogService;

import lombok.extern.slf4j.Slf4j;

/**
 * 提供添加日志到数据的操作
 * 
 * @author CARRY ME HOME 2020年5月4日下午3:57:12
 */
@Slf4j
public class BasicLog {

	@Resource
	private LogService logService;
	@Resource
	private CurrentAppInfo appInfo;

	protected void addLog(String message) {
		Log g = new Log(0, null, null, message, null, getAddr(), appInfo.getCurrentUser());
		addLog(g);
	}

	protected void addLog(String message, User user) {
		Log g = new Log(0, null, null, message, null, getAddr(), user);
		addLog(g);
	}

	protected void addLog(String module, String operate, String message, String result) {
		Log g = new Log(0, module, operate, message, result, getAddr(), appInfo.getCurrentUser());
		addLog(g);
	}

	private void addLog(Log g) {
		try {
			logService.addLog(g);
			log.debug("LOG => message:{}, ip：{}, userName:{}", g.getMessage(), g.getIpByString(), g.getUser()
					.getName());

		} catch (IncompleteException e) {
			log.error("记录日志时出现异常{}", e);
		}
	}

	/**
	 * 原文链接：https://blog.csdn.net/weixin_42387852/java/article/details/85228512
	 * 
	 * @return
	 */
	private String getAddr() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}
}
