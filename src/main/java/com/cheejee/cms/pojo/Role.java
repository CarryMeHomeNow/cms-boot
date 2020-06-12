package com.cheejee.cms.pojo;

import java.util.List;

/**
 * 角色类
 * 
 * @author CARRY ME HOME 2019年11月19日下午2:52:34
 */
public class Role {

	private int id;
	private String name;
	private String describe;
	private User creator;
	private List<Permission> permissions;
	private List<User> users;
	private List<Group> groups;

	public Role() {
	}

	public Role(String name, User creator) {
		this.name = name;
		this.creator = creator;
	}

	public Role(String name, String describe, User creator) {
		super();
		this.name = name;
		this.describe = describe;
		this.creator = creator;
	}

	public Role(int id, String name, String describe, User creator, List<Permission> permissions, List<User> users,
			List<Group> groups) {
		super();
		this.id = id;
		this.name = name;
		this.describe = describe;
		this.creator = creator;
		this.permissions = permissions;
		this.users = users;
		this.groups = groups;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", describe=" + describe + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (super.equals(obj))
			return true;

		return obj.toString().equals(this.toString());
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

}