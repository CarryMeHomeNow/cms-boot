package com.cheejee.cms.pojo;

/**
 * 权限类
 * 
 * @author CARRY ME HOME 2019年12月4日下午2:24:17
 */
public class Permission {

	private int id;
	private byte privilege;
	private Role role;
	private Resource resource;

	public Permission(byte privilege, Role role, Resource resource) {
		super();
		this.privilege = privilege;
		this.role = role;
		this.resource = resource;
	}

	public Permission() {
		super();
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", privilege=" + privilege + ", role=" + role + ", resource=" + resource + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getPrivilege() {
		return privilege;
	}

	public void setPrivilege(byte privilege) {
		this.privilege = privilege;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}
