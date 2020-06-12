package com.cheejee.cms.pojo;

import java.util.List;

public class Group {

	private int id;
	private String name;
	private String describe;
	private User creater;
	private Group parent;
	private List<Group> sons;
	private List<User> users;
	private List<Role> roles;

	public Group() {
	}

	public Group(String name, User creater) {
		super();
		this.name = name;
		this.creater = creater;
	}

	public Group(String name, String describe, User creater) {
		this.name = name;
		this.describe = describe;
		this.creater = creater;
	}

	public Group(String name, String describe, User creater, Group parent) {
		super();
		this.name = name;
		this.describe = describe;
		this.creater = creater;
		this.parent = parent;
	}

	public Group(int id, String name, String describe, User creater, Group parent, List<Group> sons, List<User> users,
			List<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.describe = describe;
		this.creater = creater;
		this.parent = parent;
		this.sons = sons;
		this.users = users;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", describe=" + describe + "]";
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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Group getParent() {
		return parent;
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}

	public List<Group> getSons() {
		return sons;
	}

	public void setSons(List<Group> sons) {
		this.sons = sons;
	}

}
