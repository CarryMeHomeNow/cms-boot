package com.cheejee.cms.pojo;

public class Setting {

	private int id;
	private String name;
	private String value;
	private String describe;

	public Setting(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public Setting(String name, String value, String describe) {
		super();
		this.name = name;
		this.value = value;
		this.describe = describe;
	}

	public Setting() {
		super();
	}

	@Override
	public String toString() {
		return "Setting [id=" + id + ", name=" + name + ", value=" + value + ", describe=" + describe + "]";
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
