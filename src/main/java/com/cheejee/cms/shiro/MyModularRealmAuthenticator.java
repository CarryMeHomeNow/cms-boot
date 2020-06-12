package com.cheejee.cms.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.Realm;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义realme使用规则, 只会使用名称中包含指定userType的realme。
 * 
 * 使用默认规则可以完成业务逻辑，将此类标注为弃用。
 * 
 * @author CARRY ME HOME 2020年3月7日下午4:49:05
 */
@Slf4j
@Deprecated
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {

	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
			throws AuthenticationException {

		assertRealmsConfigured();
		UserToken token = (UserToken) authenticationToken;
		String userType = token.getUserType();
		List<Realm> realms = new ArrayList<>();

		getRealms().forEach(realm -> {
			if (realm.getName().contains(userType)) {
				realms.add(realm);
			}
		});

		if (realms.isEmpty()) {
			log.error("用户在认证时没有匹配到任何一个认证器（realm），需要认证的token：{}", token);
			throw new UnauthenticatedException("认证失败，没有匹配到任何一个realm");
			
		} else if (realms.size() == 1) {
			return doSingleRealmAuthentication(realms.iterator().next(), token);
			
		} else {
			return doMultiRealmAuthentication(realms, token);
		}
	}
}
