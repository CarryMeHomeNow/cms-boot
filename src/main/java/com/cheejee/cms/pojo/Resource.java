package com.cheejee.cms.pojo;

/**
 * 资源类
 * 
 * @author CARRY ME HOME 2019年12月4日下午2:24:38
 */
public class Resource {

	private int id;
	private String name;
	private String desName;
	private String describe;

	public Resource(String name, String desName, String describe) {
		super();
		this.name = name;
		this.desName = desName;
		this.describe = describe;
	}

	public Resource() {
		super();
	}

	public Resource(String name, String desName) {
		super();
		this.name = name;
		this.desName = desName;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", name=" + name + ", desName=" + desName + ", describe=" + describe + "]";
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

	public String getDesName() {
		return desName;
	}

	public void setDesName(String desName) {
		this.desName = desName;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
