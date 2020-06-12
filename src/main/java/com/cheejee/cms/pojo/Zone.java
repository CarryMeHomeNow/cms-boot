package com.cheejee.cms.pojo;

import java.util.List;

/**
 * 专栏实体类
 * 
 * @author CARRY ME HOME
 * @date 2019年11月11日 上午9:45:22
 */
@Deprecated
public class Zone {

	private int id;
	private String name;
	private String describe;
	private User creator;
	private List<User> admins;
	private List<Content> contents;

	public Zone() {
		super();
	}

	public Zone(String name, User creator) {
		super();
		this.name = name;
		this.creator = creator;
	}

	public Zone(String name, String describe, User creator) {
		super();
		this.name = name;
		this.describe = describe;
		this.creator = creator;
	}

	public Zone(int id, String name, String describe, User creator, List<User> admins, List<Content> contents) {
		super();
		this.id = id;
		this.name = name;
		this.describe = describe;
		this.creator = creator;
		this.admins = admins;
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Zone [id=" + id + ", name=" + name + ", describe=" + describe + "]";
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

	public List<User> getAdmins() {
		return admins;
	}
	
	public void setAdmins(List<User> admins) {
		this.admins = admins;
	}

	public List<Content> getContents() {
		return contents;
	}

	public User getCreator() {
		return creator;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
