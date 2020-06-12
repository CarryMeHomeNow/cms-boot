package com.cheejee.cms.pojo;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.cheejee.cms.tools.Encryption;

/**
 * 用户实体类
 * 
 * @author CARRY ME HOME
 * @date 2019年11月11日 上午9:43:07
 */
public class User implements Serializable{

	private static final long serialVersionUID = -8778493833392837058L;
	
	private int id;
	private String name;
	private String password;
	private Integer state;
	private PersonalInfo personalInfo;
	private List<Category> categorys;
	private List<Content> contents;
	private List<Role> roles;
	private List<Group> groups;

	public User() {
	}

	public User(String name, String password) throws NoSuchAlgorithmException {
		super();
		this.name = name;
		this.password = Encryption.encipher(password);
	}

	public User(String name, String password, Integer state) throws NoSuchAlgorithmException {
		super();
		this.name = name;
		this.password = Encryption.encipher(password);
		this.state = state;
	}

	public User(int id, String name, String password, Integer state, PersonalInfo personalInfo,
			List<Category> categorys, List<Content> contents, List<Role> roles, List<Group> groups) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.state = state;
		this.personalInfo = personalInfo;
		this.categorys = categorys;
		this.contents = contents;
		this.roles = roles;
		this.groups = groups;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", state=" + state + "]";
	}

	public List<Category> getCategorys() {
		return categorys;
	}

	public List<Content> getContents() {
		return contents;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setPassword(String password) throws NoSuchAlgorithmException {
		this.password = Encryption.encipher(password);
	}

	public String getPassword() {
		return password;
	}

	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}

}
