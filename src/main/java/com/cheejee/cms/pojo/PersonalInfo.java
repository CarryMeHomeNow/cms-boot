package com.cheejee.cms.pojo;

import java.sql.Timestamp;

/**
 * 用户的个人信息类
 * 
 * @author CARRY ME HOME 2019年12月13日下午4:16:07
 */
public class PersonalInfo {

	private int userId;
	private String pName;
	private String signatrue;
	private String profile;
	private String business;
	private String tel;
	private String email;
	private String miniAvatarUrl;
	private String bigAvatarUrl;
	private Timestamp createTime;
	private Timestamp lastLoginTime;

	public PersonalInfo() {
		super();
	}

	public PersonalInfo(String pName, String tel, int userId) {
		super();
		this.pName = pName;
		this.tel = tel;
		this.userId = userId;
	}

	public PersonalInfo(String pName, String signatrue, String profile, String business, String tel, String email,
			int userId) {
		super();
		this.pName = pName;
		this.signatrue = signatrue;
		this.profile = profile;
		this.business = business;
		this.tel = tel;
		this.email = email;
		this.userId = userId;
	}

	public PersonalInfo(String pName, String signatrue, String profile, String business, String tel, String email,
			 String miniAvatarUrl, String bigAvatarUrl, int userId) {
		super();
		this.pName = pName;
		this.signatrue = signatrue;
		this.profile = profile;
		this.business = business;
		this.tel = tel;
		this.email = email;
		this.userId = userId;
		this.miniAvatarUrl = miniAvatarUrl;
		this.bigAvatarUrl = bigAvatarUrl;
	}

	@Override
	public String toString() {
		return "PersonalInfo [userId=" + userId + ", pName=" + pName + ", signatrue=" + signatrue + ", profile="
				+ profile + ", business=" + business + ", tel=" + tel + ", email=" + email + ", miniAvatarUrl="
				+ miniAvatarUrl + ", bigAvatarUrl=" + bigAvatarUrl + ", createTime=" + createTime + ", lastLoginTime="
				+ lastLoginTime + "]";
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getSignatrue() {
		return signatrue;
	}

	public void setSignatrue(String signatrue) {
		this.signatrue = signatrue;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMiniAvatarUrl() {
		return miniAvatarUrl;
	}

	public void setMiniAvatarUrl(String miniAvatarUrl) {
		this.miniAvatarUrl = miniAvatarUrl;
	}

	public String getBigAvatarUrl() {
		return bigAvatarUrl;
	}

	public void setBigAvatarUrl(String bigAvatarUrl) {
		this.bigAvatarUrl = bigAvatarUrl;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
