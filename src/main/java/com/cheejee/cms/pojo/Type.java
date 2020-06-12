package com.cheejee.cms.pojo;

import java.util.List;

/**
 * 类型实体类
 * 
 * @author CARRY ME HOME
 * @date 2019年11月11日 上午9:42:54
 */
public class Type {

	private int id;
	private String name;
	private String describe;
	private Integer state;
	private List<Suffix> suffixs;
	

	public Type() {
	}
	
	public Type(String name) {
		this.name = name;
	}

	public Type(String name, String describe) {
		super();
		this.name = name;
		this.describe = describe;
	}

	public Type(String name, String describe, Integer state) {
		super();
		this.name = name;
		this.describe = describe;
		this.state = state;
	}

	public Type(int id, String name, String describe, Integer state, List<Suffix> suffixs) {
		super();
		this.id = id;
		this.name = name;
		this.describe = describe;
		this.state = state;
		this.suffixs = suffixs;
	}


	@Override
	public String toString() {
		return "Type [id=" + id + ", name=" + name + ", describe=" + describe + ", suffixs=" + suffixs + "]";
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

	public void setName(String naem) {
		this.name = naem;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public List<Suffix> getSuffixs() {
		return suffixs;
	}

	public void setSuffixs(List<Suffix> suffixs) {
		this.suffixs = suffixs;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
