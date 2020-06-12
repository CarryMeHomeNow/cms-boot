/**
 * 
 */
package com.cheejee.cms.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cheejee.cms.shiro.UserRealm;

/**
 * @author CARRY ME HOME 2020年3月7日上午10:43:11
 */
@Configuration
public class ShiroConfig {

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
		// swagger
		chainDefinition.addPathDefinition("/v2/api-docs", "anon");
		chainDefinition.addPathDefinition("/webjars/**", "anon");
		chainDefinition.addPathDefinition("/swagger-resources/**", "anon");
		chainDefinition.addPathDefinition("/swagger-ui.html", "anon");

		chainDefinition.addPathDefinition("/shiro/**", "anon");
		chainDefinition.addPathDefinition("/*/login", "anon");
		chainDefinition.addPathDefinition("/user/register", "anon");

		chainDefinition.addPathDefinition("/admin/**", "authc, roles[3]");

		chainDefinition.addPathDefinition("/**", "user");

		return chainDefinition;
	}

	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
		hcm.setHashAlgorithmName("SHA-256");
		hcm.setHashIterations(1);
		hcm.setStoredCredentialsHexEncoded(true);

		return hcm;
	}

	@Bean
	public UserRealm userRealm() {
		UserRealm realm = new UserRealm();
		realm.setCredentialsMatcher(hashedCredentialsMatcher());

		return realm;
	}
	
	@Bean
	public DefaultWebSessionManager sessionManager() {
	    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
	    sessionManager.setGlobalSessionTimeout(3600000L);
	    
	    return sessionManager;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager sm = new DefaultWebSecurityManager();

		List<Realm> realms = new ArrayList<>();
		realms.add(userRealm());
		
		sm.setRealms(realms);
		sm.setRememberMeManager(cookieRememberMeManager());
		sm.setSessionManager(sessionManager());

		return sm;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	protected CacheManager cacheManager() {
		return new MemoryConstrainedCacheManager();
	}

	/**
	 * 在登录时设置记住我后，重启项目和网页后shiro读取cookie信息时会报一个错，而且记住我功能也会失效。
	 * 有两种解决办法，一个是使用自己设置的RememberMeManager，二是将RememberMeManager设置为null。
	 * 两种方法经测试都会使记住我功能失效，但是shiro不再报错。
	 * @return
	 */
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(2592000);
        cookieRememberMeManager.setCookie(simpleCookie);
        cookieRememberMeManager.setCipherKey(Base64.decode("6ZmI6I2j5Y+R5aSn5ZOlAA=="));
        
        return cookieRememberMeManager;
    }
}