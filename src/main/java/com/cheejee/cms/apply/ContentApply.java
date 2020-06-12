package com.cheejee.cms.apply;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.ContentDAO;
import com.cheejee.cms.dao.TagDAO;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.pojo.Category;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.Tag;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class ContentApply {

	SqlSessionFactory ssf;

	public ContentApply() {
		this.ssf = SSF.getSSF();
	}

	public Content selectContent(int id) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			return cd.selectContent(id);
		}
	}

	public Content selectContent(int id, User user) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			return cd.selectContentByIdAndUser(id, user);
		}
	}

	/**
	 * 根据title进行模糊查询
	 * 
	 * @param title
	 * @return
	 */
	public PageInfo<Content> selectContentByTitle(int pagNum, int pageSize, String title) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			return new PageInfo<Content>(cd.selectContentByTitle(pagNum, pageSize, title));
		}
	}

	public PageInfo<Content> selectContentByTitle(int pagNum, int pageSize, String title, User user) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			return new PageInfo<Content>(cd.selectContentByTitleAndUser(pagNum, pageSize, title, user));
		}
	}

	public PageInfo<Content> searchContent(int pageNum, int pageSize, String title, Integer state) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			return new PageInfo<Content>(cd.searchContent(pageNum, pageSize, title, state));
		}
	}

	public PageInfo<Content> selectContentByUser(int pagNum, int pageSize, User user) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			return new PageInfo<Content>(cd.selectContentByUser(pagNum, pageSize, user));
		}
	}

	public PageInfo<Content> selectContentByState(int pagNum, int pageSize, Integer state) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			return new PageInfo<Content>(cd.selectContentByState(pagNum, pageSize, state));
		}
	}

	public PageInfo<Content> selectContentByUserAndState(int pagNum, int pageSize, User user, Integer state) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			return new PageInfo<Content>(cd.selectContentByUserAndState(pagNum, pageSize, user, state));
		}
	}

	public PageInfo<Content> selectContentNoCategory(int pageNum, int pageSize, User user) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);

			return new PageInfo<Content>(cd.selectContentNoCategory(pageNum, pageSize, user));
		}
	}
	
	public PageInfo<Content> selectContentByCategory(int pageNum, int pageSize, Category category, User user) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			
			return new PageInfo<Content>(cd.selectContentForCategory(pageNum, pageSize, category, user));
		}
	}

	public PageInfo<Content> listContent(int pagNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			return new PageInfo<Content>(cd.listContent(pagNum, pageSize));
		}
	}

	/**
	 * 添加内容，同步添加内容与其他对象的关系
	 * 
	 * @param content
	 * @return 添加成功返回1
	 * @throws OperationsException 添加关系时失败则会抛出此异常
	 */
	public int addContent(Content content) throws OperationsException {

		if (content == null)
			return 0;

		try (SqlSession session = ssf.openSession()) {

			ContentDAO contentd = session.getMapper(ContentDAO.class);
			content.setState(1);
			int a = contentd.addContent(content);

			if (a != 1)
				return a;

			addContentAttach(content, session);
			addContentCategory(content, session);
			addContentTags(content, session);

			session.commit();
			return a;
		}
	}

	private void addContentTags(Content content, SqlSession session) throws OperationsException {

		int tag = 0;
		ContentDAO cd = session.getMapper(ContentDAO.class);
		List<Tag> lt = content.getTags();

		if (lt != null && !lt.isEmpty())
			tag = cd.addContentTags(content, lt);

		if (lt != null && tag != lt.size())
			throw new OperationsException("添加内容的标签时失败：" + content.toString());
	}

	private void addContentAttach(Content content, SqlSession session) throws OperationsException {

		int attach = 0;
		ContentDAO cd = session.getMapper(ContentDAO.class);
		List<Attach> la = content.getAttachs();

		if (la != null && !la.isEmpty())
			attach = cd.addContentAttachs(content, la);

		if (la != null && attach != la.size())
			throw new OperationsException("添加内容的附件时失败：" + content.toString());
	}

	private void addContentCategory(Content content, SqlSession session) throws OperationsException {

		int categroy = 0;
		ContentDAO cd = session.getMapper(ContentDAO.class);
		List<Category> lc = content.getCategorys();

		if (lc != null && !lc.isEmpty())
			categroy = cd.addContentCategorys(content, lc);

		if (lc != null && categroy != lc.size())
			throw new OperationsException("添加内容到分类时失败：" + content.toString());
	}

	public int addContentTags(Content c, List<Tag> tags) {

		if (tags == null || tags.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			int a = cd.addContentTags(c, tags);

			if (a != tags.size())
				return 0;

			session.commit();
			return a;

		}
	}

	public int addContentTag(Content c, Tag tag) {
		List<Tag> lt = new ArrayList<Tag>();
		lt.add(tag);
		return addContentTags(c, lt);
	}

	public int addContentAttachs(Content c, List<Attach> attachs) {

		if (attachs == null || attachs.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			int a = cd.addContentAttachs(c, attachs);

			if (a != attachs.size())
				return 0;

			session.commit();
			return a;
		}
	}

	public int addContentAttach(Content c, Attach attach) {
		List<Attach> lt = new ArrayList<Attach>();
		lt.add(attach);
		return addContentAttachs(c, lt);
	}

	public int addContentCategorys(Content c, List<Category> categorys) {

		if (categorys == null || categorys.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			int a = cd.addContentCategorys(c, categorys);

			if (a != categorys.size())
				return 0;

			session.commit();
			return a;
		}
	}

	public int addContentCategory(Content c, Category category) {
		List<Category> lc = new ArrayList<Category>();
		lc.add(category);
		return addContentCategorys(c, lc);
	}

	public int deleteContentCategory(Content c, Category category) {

		List<Category> lc = new ArrayList<Category>();
		lc.add(category);
		return deleteContentCategorys(c, lc);
	}

	public int deleteContentCategorys(Content c, List<Category> categorys) {

		if (categorys == null || categorys.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			int a = cd.deleteContentCategorys(c, categorys);
			session.commit();
			return a;
		}
	}

	public int deleteContentAttach(Content c, Attach attach) {
		List<Attach> lt = new ArrayList<Attach>();
		lt.add(attach);
		return deleteContentAttachs(c, lt);
	}

	public int deleteContentAttachs(Content c, List<Attach> attachs) {

		if (attachs == null || attachs.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			int a = cd.deleteContentAttachs(c, attachs);
			session.commit();
			return a;
		}
	}

	public int deleteContentTags(Content c, List<Tag> tags) {

		if (tags == null || tags.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			int a = cd.deleteContentTags(c, tags);
			session.commit();
			return a;
		}
	}

	public int deleteContentTag(Content c, Tag tag) {
		List<Tag> lt = new ArrayList<Tag>();
		lt.add(tag);
		return deleteContentTags(c, lt);
	}

	/**
	 * 删除内容，并删除所有此内容的关联表的信息
	 * 
	 * @param contents
	 * @return
	 */
	public int deleteContents(List<Content> contents) {

		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			TagDAO td = session.getMapper(TagDAO.class);
			int a = cd.deleteContents(contents);

			for (Content c : contents) {
				cd.deleteContentAttachsAll(c);
				cd.deleteContentCategorysAll(c);

				List<Tag> lt = td.selectTagForContent(c);
				if (!lt.isEmpty())
					cd.deleteContentTags(c, lt);
			}

			session.commit();
			return a;
		}
	}

	/**
	 * 更新内容，会同步更新与其他对象的关系
	 * 
	 * @param c
	 * @return
	 * @throws OperationsException 更新关系表时出错则会抛出此异常
	 */
	public int updateContent(Content c) throws OperationsException {

		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);

			if (cd.updateContent(c) != 1)
				return 0;

			updateOthers(c, session);
			session.commit();
			return 1;
		}
	}

	public int updateContentByManage(Content c) throws OperationsException {

		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);

			if (c.getTitle() != null || c.getSummary() != null || c.getWords() != null || c.getState() != null) {
				if (cd.updateContentByManage(c) != 1)
					return 0;
			}

			updateOthers(c, session);
			session.commit();
			return 1;
		}
	}

	public int updateContentOnly(Content c) throws OperationsException {

		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);

			if (c.getTitle() != null || c.getSummary() != null || c.getWords() != null || c.getState() != null) {
				if (cd.updateContent(c) != 1)
					return 0;
			}

			session.commit();
			return 1;
		}
	}

	public int updateContentOnlyByManage(Content c) throws OperationsException {

		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);

			if (c.getTitle() != null || c.getSummary() != null || c.getWords() != null || c.getState() != null) {
				if (cd.updateContentByManage(c) != 1)
					return 0;
			}

			session.commit();
			return 1;
		}
	}

	private void updateOthers(Content c, SqlSession session) throws OperationsException {
		ContentDAO cd = session.getMapper(ContentDAO.class);
		TagDAO td = session.getMapper(TagDAO.class);

		updateContentTags(c, cd, td);
		updateContentAttachs(c, cd);
		updateContentCategroys(c, cd);
	}

	private void updateContentTags(Content c, ContentDAO cd, TagDAO td) throws OperationsException {

		int a = 0;
		int b = 0;
		int d = 0;
		List<Tag> lts = c.getTags();
		List<Tag> ats = td.selectTagForContent(c);

		if (lts != null)
			d = lts.size();

		if (ats != null && !ats.isEmpty())
			a = cd.deleteContentTags(c, ats);

		if (lts != null && !lts.isEmpty())
			b = cd.addContentTags(c, lts);

		if (a != ats.size() || b != d)
			throw new OperationsException("更新内容标签时出错");
	}

	private void updateContentAttachs(Content c, ContentDAO cd) throws OperationsException {

		int a = 0;
		int b = 0;
		List<Attach> las = c.getAttachs();

		if (las != null)
			b = las.size();

		cd.deleteContentAttachsAll(c);

		if (las != null && !las.isEmpty())
			a = cd.addContentAttachs(c, las);

		if (a != b)
			throw new OperationsException("更新内容附件时出错");
	}

	private void updateContentCategroys(Content c, ContentDAO cd) throws OperationsException {

		int a = 0;
		int b = 0;
		List<Category> lcs = c.getCategorys();

		if (lcs == null)
			return;

		b = lcs.size();
		cd.deleteContentCategorysAll(c);

		if (lcs != null && !lcs.isEmpty())
			a = cd.addContentCategorys(c, lcs);

		if (a != b)
			throw new OperationsException("更新内容分类时出错");
	}

	public boolean updateContentTags(Content content) {
		try (SqlSession session = ssf.openSession()) {

			ContentDAO cd = session.getMapper(ContentDAO.class);
			TagDAO td = session.getMapper(TagDAO.class);

			updateContentTags(content, cd, td);
			session.commit();
			return true;

		} catch (OperationsException e) {
			return false;
		}
	}

	public boolean updateContentCategorys(Content content) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);

			updateContentCategroys(content, cd);
			session.commit();
			return true;

		} catch (OperationsException e) {
			return false;
		}
	}

	public boolean updateContentAttachs(Content content) {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);

			updateContentAttachs(content, cd);
			session.commit();
			return true;

		} catch (OperationsException e) {
			return false;
		}
	}

	/**
	 * 检测内容在数据库是否存在，查找时以传入对象的id为条件
	 * 
	 * @param content
	 * @throws NotFoundException 内容不存在时抛出此异常
	 */
	public Content checkContentIsEx(Content content) throws NotFoundException {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);
			Content c = cd.selectContent(content.getId());
			
			if (c == null) {
				throw new NotFoundException("内容不存在" + content);
				
			} else {
				return c;
			}
		}
	}

	/**
	 * 检测传入的内容对象的创建者和数据库的数据是否匹配
	 * 
	 * @param content
	 * @throws DataException 创建者和数据库不匹配时抛出此异常
	 */
	public void checkContentUserIsTrue(Content content) throws DataException {
		try (SqlSession session = ssf.openSession()) {
			ContentDAO cd = session.getMapper(ContentDAO.class);

			User user = cd.selectContent(content.getId()).getUser();
			if (user.getId() != content.getUser().getId())
				throw new DataException("内容创建者与数据库不匹配");
		}
	}

}