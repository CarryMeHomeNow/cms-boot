package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.isNull;

import java.util.Arrays;

import com.cheejee.cms.apply.TagApply;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.pojo.Tag;
import com.cheejee.cms.service.TagService;
import com.github.pagehelper.PageInfo;

public class TagServiceImpl implements TagService {

	private TagApply ta;

	public TagServiceImpl() {
		this.ta = new TagApply();
	}

	@Override
	public Tag getTagById(int id) {
		return ta.selectTag(id);
	}

	@Override
	public PageInfo<Tag> getTagByName(int pageNum, int pageSize, String name) {
		return ta.selectTagForName(pageNum, pageSize, name);
	}

	@Override
	public PageInfo<Tag> getTagAll(int pageNum, int pageSize) {
		return ta.listTag(pageNum, pageSize);
	}

	@Override
	public boolean addTag(Tag... tags) throws DataDuplicationException, IncompleteException {

		isNull("添加的标签对象为空", tags);

		for (Tag tag : tags) {
			if (tag.getName() == null || tag.getName().equals(""))
				throw new IncompleteException("添加的标签名称为空");
		}
		return ta.addTags(Arrays.asList(tags)) == tags.length ? true : false;
	}

	@Override
	public void deleteTagByManage(Tag... tags) throws IncompleteException {

		isNull("标签为空", tags);
		for (Tag t : tags)
			checkId(t.getId(), "标签id为空");

		ta.deleteTag(tags);
	}

	@Override
	public boolean changeTagDescribe(Tag tag) throws DataDuplicationException, IncompleteException, NotFoundException {
		
		isNull(tag, "标签为空");
		checkId(tag.getId(), "标签id为空");
		if(tag.getDescribe() == null || tag.getDescribe().equals(""))
			throw new NullPointerException();
		
		if(ta.selectTag(tag.getId()) == null)
			throw new NotFoundException("标签不存在");
		
		Tag t = new Tag();
		t.setId(tag.getId());
		t.setDescribe(tag.getDescribe());
		
		return ta.updateTag(t) == 1;
	}

}
