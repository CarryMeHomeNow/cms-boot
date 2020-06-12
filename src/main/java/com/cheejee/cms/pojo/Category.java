package com.cheejee.cms.pojo;

import java.util.List;

/**
 * 分类实体类
 * @author CARRY ME HOME
 * @date 2019年11月11日 上午9:42:05
 */
public class Category {

	private int id;
	private String name;
	private User user;
	private List<Content> contents;

	public Category() {
	}

	public Category(String name, User user) {
		this.user = user;
		this.name = name;
	}

	public Category(int id, String name, User user, List<Content> contents) {
		this.id = id;
		this.name = name;
		this.user = user;
		this.contents = contents;
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

	public List<Content> getContents() {
		return contents;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
