package com.cheejee.cms.pojo;

import java.util.List;

/**
 * 后缀实体类，数据库存后缀不带点
 * 
 * @author CARRY ME HOME
 * @date 2019年11月11日 上午9:42:29
 */
public class Suffix {

	private int id;
	private String suffix;
	private String describe;
	private List<Type> types;
	private List<Attach> attachs;

	public Suffix() {
	}

	public Suffix(String suffix) {
		super();
		this.suffix = suffix;
	}

	public Suffix(String suffix, String describe) {
		super();
		this.suffix = suffix;
		this.describe = describe;
	}

	public Suffix(int id, String suffix, String describe, List<Type> types, List<Attach> attachs) {
		super();
		this.id = id;
		this.suffix = suffix;
		this.describe = describe;
		this.types = types;
		this.attachs = attachs;
	}

	@Override
	public String toString() {
		return "Suffix [id=" + id + ", suffix=" + suffix + ", describe=" + describe + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public List<Type> getTypes() {
		return types;
	}

	public List<Attach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<Attach> attachs) {
		this.attachs = attachs;
	}

}
