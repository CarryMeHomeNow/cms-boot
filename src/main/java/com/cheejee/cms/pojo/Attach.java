package com.cheejee.cms.pojo;

import java.util.List;

/**
 * 附件实体类
 * 
 * @author CARRY ME HOME
 * @date 2019年11月11日 上午9:41:33
 */
public class Attach {

	private int id;
	private String name;
	private String url;
	private User user;
	private Suffix suffix;
	private List<Content> contents;

	public Attach() {
	}

	public Attach(String name, String url, User user) {
		super();
		this.name = name;
		this.url = url;
		this.user = user;
	}

	public Attach(String name, String url, User user, Suffix suffix) {
		super();
		this.name = name;
		this.url = url;
		this.user = user;
		this.suffix = suffix;
	}

	public Attach(int id, String name, String url, User user, Suffix suffix, List<Content> contents) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.user = user;
		this.suffix = suffix;
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Attach [id=" + id + ", name=" + name + ", url=" + url + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Suffix getsuffix() {
		return suffix;
	}

	public void setsuffix(Suffix suffix) {
		this.suffix = suffix;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

}
