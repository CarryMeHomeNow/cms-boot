package com.cheejee.cms.pojo;

import java.sql.Timestamp;

import com.cheejee.cms.tools.ConvertIP;

public class Log {

	private int id;
	private String module;
	private String operate;
	private String message;
	private String result;
	private Timestamp createTime;
	private byte[] ip;
	private User user;

	public Log() {
	}

	public Log(String message, String ip, User user) {
		super();
		this.message = message;
		this.ip = ConvertIP.toBinary(ip);
		this.user = user;
	}

	public Log(int id, String module, String operate, String message, String result, String ip, User user) {
		super();
		this.id = id;
		this.module = module;
		this.operate = operate;
		this.message = message;
		this.result = result;
		this.ip = ConvertIP.toBinary(ip);
		this.user = user;
	}

	public Log(String module, String operate, String message, String result, String ip, User user) {
		super();
		this.module = module;
		this.operate = operate;
		this.message = message;
		this.result = result;
		this.ip = ConvertIP.toBinary(ip);
		this.user = user;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", module=" + module + ", operate=" + operate + ", message=" + message + ", result="
				+ result + ", createTime=" + createTime + ", ip=" + ConvertIP.toString(ip) + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public byte[] getIp() {
		return ip;
	}

	public String getIpByString() {
		return ConvertIP.toString(ip);
	}

	public void setIp(String ip) {
		this.ip = ConvertIP.toBinary(ip);
	}

	public void setIp(byte[] ip) {
		this.ip = ip;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
