package com.cheejee.cms.apply;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.TagDAO;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.Tag;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class TagApply {

	SqlSessionFactory ssf;

	public TagApply() {
		this.ssf = SSF.getSSF();
	}

	public Tag selectTag(int id) {
		try (SqlSession session = ssf.openSession()) {
			TagDAO td = session.getMapper(TagDAO.class);
			return td.selectTag(id);
		}
	}

	public PageInfo<Tag> selectTagForName(int pagNum, int pageSize, String name) {
		try (SqlSession session = ssf.openSession()) {
			TagDAO td = session.getMapper(TagDAO.class);
			return new PageInfo<Tag>(td.selectTagForName(pagNum, pageSize, name));
		}
	}
	
	public Tag selectTagByNameEx(String name) {
		try (SqlSession session = ssf.openSession()) {
			TagDAO td = session.getMapper(TagDAO.class);
			
			return td.selectTagByNameEx(name);
		}
	}

	public PageInfo<Tag> listTag(int pagNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			TagDAO td = session.getMapper(TagDAO.class);
			return new PageInfo<Tag>(td.listTag(pagNum, pageSize));
		}
	}

	public PageInfo<Tag> selectTagForContent(Content content) {
		try (SqlSession session = ssf.openSession()) {
			TagDAO td = session.getMapper(TagDAO.class);
			return new PageInfo<Tag>(td.selectTagForContent(content));
		}
	}

	public int addTags(List<Tag> tags) throws DataDuplicationException {

		if (tags == null || tags.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession(true)) {
			
			TagDAO td = session.getMapper(TagDAO.class);
			Set<String> set = new HashSet<String>();

			for (Tag t : tags) {
				if (td.selectTagNameCount(t) != 0)
					throw new DataDuplicationException("标签名已存在：" + t.getName());
				
				set.add(t.getName());
			}
			
			if(set.size() != tags.size())
				throw new DataDuplicationException("存在相同的标签");
			
			return td.addTags(tags);
		}
	}

	public int addTag(Tag tag) throws DataDuplicationException {
		List<Tag> lt = new ArrayList<Tag>();
		lt.add(tag);
		return addTags(lt);
	}

	public void deleteTag(Tag...tag) {

		try (SqlSession session = ssf.openSession()) {
			TagDAO td = session.getMapper(TagDAO.class);
			
			for(Tag t :tag)
				td.deleteTag(t);
			
			td.deleteTagRelate(tag);
			session.commit();
		}
	}

	public int updateTag(Tag tag) throws DataDuplicationException {

		if (tag == null)
			return 0;

		try (SqlSession session = ssf.openSession()) {
			TagDAO td = session.getMapper(TagDAO.class);

//			if (td.selectTagNameCountExSelf(tag) != 0)
//				throw new DataDuplicationException("标签名称已存在：" + tag.getName());

			int a = td.updateTag(tag);
			session.commit();
			return a;
		}
	}
}
