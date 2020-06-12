package com.cheejee.cms.pojo;

import java.sql.Timestamp;
import java.util.List;

/**
 * 内容实体类
 * 
 * @author CARRY ME HOME
 * @date 2019年11月11日 上午9:42:19
 */
public class Content {

	private int id;
	private String title;
	private String summary;
	private String words;
	private Integer state;
	private Timestamp addTime;
	private Timestamp updateTime;
	private User user;
	private List<Tag> tags;
	private List<Attach> attachs;
	private List<Category> categorys;

	public Content() {
		super();
	}

	public Content(String title, String words, User user) {
		super();
		this.title = title;
		this.words = words;
		this.user = user;
	}

	public Content(String title, String summary, String words, User user) {
		super();
		this.title = title;
		this.summary = summary;
		this.words = words;
		this.user = user;
	}

	public Content(String title, String summary, String words, List<Tag> tags) {
		super();
		this.title = title;
		this.summary = summary;
		this.words = words;
		this.tags = tags;
	}

	public Content(int id, String title, String summary, String words, Integer state, Timestamp addTime,
			Timestamp updateTime, User user, List<Tag> tags, List<Attach> attachs, List<Category> categorys) {
		super();
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.words = words;
		this.state = state;
		this.addTime = addTime;
		this.updateTime = updateTime;
		this.user = user;
		this.tags = tags;
		this.attachs = attachs;
		this.setCategorys(categorys);
	}

	@Override
	public String toString() {
		return "Content [id=" + id + ", title=" + title + ", summary=" + summary + ", words=" + words + ", state="
				+ state + ", addTime=" + addTime + ", updateTime=" + updateTime + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Attach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<Attach> accachs) {
		this.attachs = accachs;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<Category> categorys) {
		this.categorys = categorys;
	}
}
