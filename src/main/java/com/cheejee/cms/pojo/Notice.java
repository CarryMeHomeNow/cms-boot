package com.cheejee.cms.pojo;

import java.sql.Timestamp;

public class Notice {

	private int id;
	private String title;
	private String text;
	private Integer state;
	private Timestamp addTime;
	private User creator;

	public Notice() {
		super();
	}

	public Notice(String title, String text, User creator) {
		super();
		this.title = title;
		this.text = text;
		this.creator = creator;
	}

	public Notice(String title, String text, Integer state, User creator) {
		super();
		this.title = title;
		this.text = text;
		this.state = state;
		this.creator = creator;
	}

	public Notice(int id, String title, String text, Integer state, Timestamp addTime, User creator) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.state = state;
		this.addTime = addTime;
		this.creator = creator;
	}

	@Override
	public String toString() {
		return "Notice [id=" + id + ", title=" + title + ", text=" + text + ", state=" + state + ", addTime=" + addTime
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public User getCreator() {
		return creator;
	}


}
