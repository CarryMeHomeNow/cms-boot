package com.cheejee.cms.pojo;

import java.util.List;

/**
 * 标签实体类
 * 
 * @author CARRY ME HOME
 * @date 2019年11月11日 上午9:42:39
 */
public class Tag{

	private int id;
	private String name;
	private String describe;
	private Integer contentCount;
	private List<Content> contents;

	public Tag() {
	}

	public Tag(String name) {
		this.name = name;
	}

	public Tag(String name, String describe) {
		super();
		this.name = name;
		this.describe = describe;
	}

	public Tag(int id, String name, String describe, Integer contentCount, List<Content> contents) {
		super();
		this.id = id;
		this.name = name;
		this.describe = describe;
		this.contentCount = contentCount;
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + ", describe=" + describe + ", contentCount=" + contentCount + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (super.equals(obj))
			return true;
		return this.toString().equals(obj.toString());
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

	public List<Content> getContents() {
		return contents;
	}

	public Integer getContentCount() {
		return contentCount;
	}

	public void setContentCount(Integer contentCount) {
		this.contentCount = contentCount;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
