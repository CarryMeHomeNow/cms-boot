/**
 * 
 */
package com.cheejee.cms.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;



/**
 * @author CARRY ME HOME 2020年3月7日下午3:47:23
 */
@Deprecated
public class UserToken extends UsernamePasswordToken {

	private static final long serialVersionUID = -1931285016772626702L;
	private String userType;
	
	public UserToken(String username, String passworld, String userType) {
		super(username, passworld);
		this.userType = userType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
