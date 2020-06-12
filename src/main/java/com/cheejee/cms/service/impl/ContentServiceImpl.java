package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkAttachIsReplicate;
import static com.cheejee.cms.tools.CheckTool.checkCategoryIsReplicate;
import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.checkTagIsReplicate;
import static com.cheejee.cms.tools.CheckTool.isNull;
import static com.cheejee.cms.tools.CheckTool.matchUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cheejee.cms.apply.AttachApply;
import com.cheejee.cms.apply.CategoryApply;
import com.cheejee.cms.apply.ContentApply;
import com.cheejee.cms.apply.TagApply;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.pojo.Category;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.Tag;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.service.ContentService;
import com.cheejee.cms.tools.CheckTool;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author CARRY ME HOME 2019年12月12日下午4:47:17
 */
public class ContentServiceImpl implements ContentService {

	private ContentApply ca;

	public ContentServiceImpl() {
		ca = new ContentApply();
	}

	@Override
	public Content getContentByIdManage(int id) {
		Content c = ca.selectContent(id);
		if (c != null)
			c.getUser();

		return c;
	}

	@Override
	public Content getContentEditByIdManage(int id) {

		Content c = ca.selectContent(id);
		if (c != null) {
			c.getAttachs();
			c.getCategorys();
			c.getTags();
			c.getUser();
		}
		return c;
	}

	@Override
	public Content getContentById(int id, User user) throws IncompleteException {

		isNull(user, "用户对象为空");
		checkId(user.getId(), "用户id为空");

		Content c = ca.selectContent(id, user);
		if (c != null)
			c.getUser();
		return c;
	}

	@Override
	public Content getContentEditById(int id, User user) throws IncompleteException {

		isNull(user, "用户对象为空");
		checkId(user.getId(), "用户id为空");

		Content c = ca.selectContent(id);
		if (c != null) {
			c.getAttachs();
			c.getCategorys();
			c.getTags();
			c.getUser();
		}
		return c;
	}

	@Override
	public PageInfo<Content> getContentByTitleManage(int pageNum, int pageSize, String title) {

		if (title == null || title.equals(""))
			throw new NullPointerException("标题为空");

		return ca.selectContentByTitle(pageNum, pageSize, title);
	}

	@Override
	public PageInfo<Content> getContentByTitle(int pageNum, int pageSize, String title, User user)
			throws IncompleteException {

		isNull(user, "用户对象为空");
		checkId(user.getId(), "用户id为空");

		return ca.selectContentByTitle(pageNum, pageSize, title, user);
	}

	@Override
	public PageInfo<Content> searchContent(int pageNum, int pageSize, String title, Integer state) {

		if (title == null || title.equals(""))
			throw new NullPointerException("标题为空");

		return ca.searchContent(pageNum, pageSize, title, state);
	}

	@Override
	public PageInfo<Content> getContentByStateManage(int pageNum, int pageSize, Integer state) {
		isNull(state, "状态值为空");
		return ca.selectContentByState(pageNum, pageSize, state);
	}

	@Override
	public PageInfo<Content> getContentByUser(int pageNum, int pageSize, User user) throws IncompleteException {

		isNull(user, "用户对象为空");
		checkId(user.getId(), "用户对象没有id");

		return ca.selectContentByUser(pageNum, pageSize, user);
	}

	@Override
	public PageInfo<Content> getContentByState(int pageNum, int pageSize, User user, Integer state)
			throws IncompleteException {

		isNull(user, "用户对象为空");
		checkId(user.getId(), "用户对象没有id");
		isNull(state, "状态值为空");

		return ca.selectContentByUserAndState(pageNum, pageSize, user, state);
	}

	@Override
	public PageInfo<Content> getContentNoCategory(int pageNum, int pageSize, User user) throws IncompleteException {

		isNull(user, "当前用户为空");
		checkId(user.getId(), "用户id为空");

		return ca.selectContentNoCategory(pageNum, pageSize, user);
	}
	
	@Override
	public PageInfo<Content> getContentByCategory(int pageNum, int pageSize, Category category, User user) throws IncompleteException{
		
		isNull(user, "当前用户为空");
		isNull(category, "分类为空");
		checkId(user.getId(), "用户id为空");
		checkId(category.getId(), "分类id为空");
		
		return ca.selectContentByCategory(pageNum, pageSize, category, user);
	}

	@Override
	public PageInfo<Content> getContentAll(int pageNum, int pageSize) {
		return ca.listContent(pageNum, pageSize);
	}

	@Override
	public boolean addContent(User user, Content content) throws IncompleteException, NullPointerException,
			OperationsException, DataDuplicationException, NotFoundException, InsufficientPermissionException {

		isNull(user, "用户对象为空");
		checkId(user.getId(), "用户对象没有id");
		isNull(content, "添加的内容对象为空");
		isNull(content.getUser(), "添加的内容没有所属用户");

		if (content.getTitle() == null || content.getTitle().equals(""))
			throw new IncompleteException("内容标题为空");

		if (content.getWords() == null || content.getWords().equals(""))
			throw new IncompleteException("内容文字为空");

		checkTagsSize(content);
		checkReplicate(content);

		if (content.getTags() != null)
			checkTags(content.getTags().toArray(new Tag[0]));

		if (content.getAttachs() != null)
			checkAttachExist(content.getAttachs().toArray(new Attach[0]));

		if (content.getCategorys() != null)
			checkCategroyExist(content.getCategorys().toArray(new Category[0]));
		checkContentContainObj(content, user);

		return ca.addContent(content) == 1;
	}

	@Override
	public void deleteContentByManage(Content... contents)
			throws NullPointerException, NotFoundException, IncompleteException {

		isNull(contents, "要删除的内容对象为空");

		for (Content c : contents) {
			isNull(contents, "要删除的内容对象为空");
			checkId(c.getId(), "要删除的内容对象没有id");
		}

		ca.deleteContents(Arrays.asList(contents));
	}

	@Override
	public void deleteContent(User user, Content... contents)
			throws NotFoundException, InsufficientPermissionException, IncompleteException, DataException {

		isNull(user, "用户对象为空");
		checkId(user.getId(), "用户对象没有id");
		isNull("要删除的内容对象为空", contents);
		List<Content> lc = new ArrayList<Content>();

		for (Content c : contents) {
			checkId(c.getId(), "内容对象没有id");
			
			Content cc = ca.selectContent(c.getId());
			if(cc == null) {
				continue;
			}
			
			matchUser(cc.getUser().getId(), user, "没有删除这个内容的权限：" + user + c);
			lc.add(c);
		}

		ca.deleteContents(lc);
	}

	@Override
	public boolean changeContentStateByManage(Content content)
			throws IncompleteException, OperationsException, NotFoundException {

		isNull(content, "内容对象为空");
		isNull(content.getState(), "内容状态为空");
		checkId(content.getId(), "内容id为空");
		ca.checkContentIsEx(content);

		Content c = new Content();
		c.setId(content.getId());
		c.setState(content.getState());

		return ca.updateContentOnlyByManage(c) == 1;
	}

	@Override
	public boolean changeContentByManage(Content content) throws NullPointerException, NotFoundException,
			OperationsException, DataException, IncompleteException, DataDuplicationException {

		isNull(content, "要更新的内容对象为空");
		isNull(content.getUser(), "更新的内容对象的创建者为空");
		checkId(content.getId(), "内容对象没有id");

		if (content.getTitle() == null || content.getTitle().equals(""))
			throw new IncompleteException("内容标题为空");

		if (content.getWords() == null || content.getWords().equals(""))
			throw new IncompleteException("内容文字为空");

		ca.checkContentIsEx(content);
		checkTagsSize(content);

		if (content.getTags() != null)
			checkTags(content.getTags().toArray(new Tag[0]));
		
		if (content.getAttachs() != null)
			checkAttachExist(content.getAttachs().toArray(new Attach[0]));
		
		if (content.getCategorys() != null)
			checkCategroyExist(content.getCategorys().toArray(new Category[0]));

		checkReplicate(content);

		return ca.updateContentByManage(content) == 1;
	}

	@Override
	public boolean changeContent(Content content, User user)
			throws NullPointerException, NotFoundException, OperationsException, InsufficientPermissionException,
			IncompleteException, DataException, DataDuplicationException {

		isNull(content, "要更新的内容对象为空");
		isNull(user, "当前用户对象为空");

		checkId(content.getId(), "内容对象没有id");
		checkId(user.getId(), "当前用户对象没有id");

		if (content.getTitle() == null || content.getTitle().equals(""))
			throw new IncompleteException("内容标题为空");

		if (content.getWords() == null || content.getWords().equals(""))
			throw new IncompleteException("内容文字为空");

		Content c = ca.checkContentIsEx(content);
		matchUser(c.getUser().getId(), user, "没有修改这个内容的权限：" + user + content);
		checkTagsSize(content);

		if (content.getTags() != null)
			checkTags(content.getTags().toArray(new Tag[0]));

		if (content.getAttachs() != null)
			checkAttachExist(content.getAttachs().toArray(new Attach[0]));

		if (content.getCategorys() != null)
			checkCategroyExist(content.getCategorys().toArray(new Category[0]));

		checkReplicate(content);
		checkContentContainObj(content, user);
		content.setState(null);

		return ca.updateContent(content) == 1;
	}

	@Override
	public boolean addContentTag(Content content, User user, Tag... tags) throws DataException, NotFoundException,
			InsufficientPermissionException, DataDuplicationException, IncompleteException, OperationsException {

		isNull(content, "要添加标签的内容对象为空");
		isNull(user, "当前用户对象为空");
		isNull("添加的标签为空", tags);
		isNull(content.getUser(), "内容对象的创建者属性为空");

		checkId(content.getId(), "内容对象没有id");
		checkId(user.getId(), "当前用户对象没有id");

		ca.checkContentIsEx(content);
		ca.checkContentUserIsTrue(content);

		matchUser(content.getUser().getId(), user, "没有添加这个内容标签的权限：" + user + content);
		checkTags(tags);
		checkTagIsReplicate(Arrays.asList(tags));
		checkTagHas(content, tags);

		int size = tags.length + ca.selectContent(content.getId()).getTags().size();
		if (size > 10)
			throw new OperationsException("内容标签数超出限制值(10)");

		return ca.addContentTags(content, Arrays.asList(tags)) == tags.length;
	}

	@Override
	public void deleteContentTag(Content content, User user, Tag... tags) throws NotFoundException,
			java.lang.NullPointerException, DataException, InsufficientPermissionException, IncompleteException {

		isNull(content, "要删除标签的内容对象为空");
		isNull(user, "当前用户对象为空");
		isNull("删除的标签为空", tags);

		checkId(content.getId(), "内容对象没有id");
		checkId(user.getId(), "当前用户对象没有id");
		ca.checkContentIsEx(content);

		Content ct = ca.selectContent(content.getId());
		matchUser(ct.getUser().getId(), user, "没有删除这个内容标签的权限：" + user + content);
		ca.deleteContentTags(content, Arrays.asList(tags));
	}

	@Override
	public boolean changeContentTags(Content content, User user) throws NotFoundException, DataException,
			InsufficientPermissionException, DataDuplicationException, IncompleteException, OperationsException {

		isNull(content, "要修改标签的内容对象为空");
		isNull(user, "当前用户对象为空");
		isNull(content.getUser(), "内容对象的创建者属性为空");

		checkId(content.getId(), "内容对象没有id");
		checkId(user.getId(), "当前用户对象没有id");

		ca.checkContentIsEx(content);
		ca.checkContentUserIsTrue(content);

		matchUser(content.getUser().getId(), user, "没有修改这个内容标签的权限：" + user + content);
		checkTagsSize(content);
		checkTagIsReplicate(content.getTags());

		if (content.getTags() != null)
			checkTags(content.getTags().toArray(new Tag[0]));

		return ca.updateContentTags(content);
	}

	@Override
	public boolean addContentTagByManage(Content content, Tag... tags)
			throws DataDuplicationException, NotFoundException, IncompleteException, OperationsException {

		isNull(content, "要添加标签的的内容对象为空");
		isNull("要添加的标签为空", tags);
		checkId(content.getId(), "内容对象的id为空");

		ca.checkContentIsEx(content);
		checkTags(tags);
		checkTagIsReplicate(Arrays.asList(tags));
		checkTagHas(content, tags);

		int size = tags.length + ca.selectContent(content.getId()).getTags().size();
		if (size > 10)
			throw new OperationsException("内容标签数超出限制值(10)");

		return ca.addContentTags(content, Arrays.asList(tags)) == tags.length;
	}

	@Override
	public boolean deleteContentTagByManage(Content content, Tag... tags)
			throws NotFoundException, IncompleteException {

		isNull(content, "要删除的内容对象为空");
		isNull("要删除的标签为空", tags);
		checkId(content.getId(), "内容对象的id为空");
		ca.checkContentIsEx(content);

		return ca.deleteContentTags(content, Arrays.asList(tags)) == tags.length;
	}

	@Override
	public boolean changeContentTagsByManage(Content content)
			throws NotFoundException, DataDuplicationException, IncompleteException, OperationsException {

		List<Tag> tags = content.getTags();

		isNull(content, "要修改标签的内容为空");
		checkId(content.getId(), "内容对象的id为空");
		ca.checkContentIsEx(content);

		if (tags != null) {
			checkTagsSize(content);
			checkTagIsReplicate(content.getTags());
			checkTags(tags.toArray(new Tag[0]));
		}

		return ca.updateContentTags(content);
	}

	@Override
	public boolean addContentAttach(Content content, User user, Attach... attachs)
			throws InsufficientPermissionException, NotFoundException, DataException, IncompleteException,
			DataDuplicationException {

		isNull(content, "添加附件的内容对象为空");
		isNull(user, "当前登录用户为空");
		isNull(content.getUser(), "内容的创建者为空");
		isNull("添加的附件为空", attachs);

		checkId(content.getId(), "内容对象没有id");
		checkId(user.getId(), "当前用户对象没有id");
		matchUser(content.getUser().getId(), user, "没有添加附件的权限");

		ca.checkContentIsEx(content);
		ca.checkContentUserIsTrue(content);

		checkAttachIsReplicate(Arrays.asList(attachs));
		checkAttachExist(attachs);
		checkContentAttachCreator(user, attachs);
		checkAttachHas(content, attachs);

		return ca.addContentAttachs(content, Arrays.asList(attachs)) == attachs.length;
	}

	@Override
	public void deleteContentAttach(Content content, User user, Attach... attachs)
			throws InsufficientPermissionException, NotFoundException, DataException, IncompleteException,
			DataDuplicationException {

		isNull(content, "删除附件的内容对象为空");
		isNull(user, "当前登录用户为空");
		isNull("删除的附件为空", attachs);

		checkId(content.getId(), "内容对象没有id");
		checkId(user.getId(), "当前用户对象没有id");

		ca.checkContentIsEx(content);
		checkAttachIsReplicate(Arrays.asList(attachs));
		Content ct = ca.selectContent(content.getId());
		matchUser(ct.getUser().getId(), user, "没有删除这个内容标签的权限：" + user + content);

		ca.deleteContentAttachs(content, Arrays.asList(attachs));
	}

	@Override
	public boolean changeContentAttachs(Content content, User user) throws InsufficientPermissionException,
			NotFoundException, DataException, IncompleteException, DataDuplicationException {

		List<Attach> attachs = content.getAttachs();
		isNull(content, "修改附件的内容对象为空");
		isNull(user, "当前登录用户为空");
		isNull(content.getUser(), "内容的创建者为空");

		checkId(content.getId(), "内容对象没有id");
		checkId(user.getId(), "当前用户对象没有id");
		matchUser(content.getUser().getId(), user, "没有修改附件的权限");

		ca.checkContentIsEx(content);
		checkAttachIsReplicate(content.getAttachs());
		ca.checkContentUserIsTrue(content);

		if (attachs != null) {
			checkAttachExist(attachs.toArray(new Attach[0]));
			checkContentAttachCreator(user, content.getAttachs().toArray(new Attach[0]));
		}

		return ca.updateContentAttachs(content);
	}

	@Override
	public boolean addContentAttachByManage(Content content, Attach... attachs)
			throws NotFoundException, IncompleteException, DataDuplicationException {

		isNull(content, "添加附件的内容对象为空");
		isNull("添加的附件为空", attachs);
		checkId(content.getId(), "内容对象没有id");

		ca.checkContentIsEx(content);
		checkAttachIsReplicate(Arrays.asList(attachs));
		checkAttachExist(attachs);
		checkAttachHas(content, attachs);

		return ca.addContentAttachs(content, Arrays.asList(attachs)) == attachs.length;
	}

	@Override
	public boolean deleteContentAttachByManage(Content content, Attach... attachs)
			throws NotFoundException, IncompleteException, DataDuplicationException {

		isNull(content, "删除附件的内容对象为空");
		isNull("删除的附件为空", attachs);

		checkId(content.getId(), "内容对象没有id");
		ca.checkContentIsEx(content);
		checkAttachIsReplicate(Arrays.asList(attachs));

		return ca.deleteContentAttachs(content, Arrays.asList(attachs)) == attachs.length;
	}

	@Override
	public boolean changeContentAttachsByManage(Content content)
			throws NotFoundException, IncompleteException, DataDuplicationException {

		List<Attach> attachs = content.getAttachs();

		isNull(content, "修改附件的内容对象为空");
		checkId(content.getId(), "内容对象没有id");

		ca.checkContentIsEx(content);
		checkAttachIsReplicate(content.getAttachs());
		if (attachs != null)
			checkAttachExist(attachs.toArray(new Attach[0]));

		return ca.updateContentAttachs(content);
	}

	@Override
	public boolean addContentCategory(Content content, User user, Category... categroys)
			throws InsufficientPermissionException, NotFoundException, DataException, IncompleteException,
			DataDuplicationException {

		isNull(content, "添加分类的内容对象为空");
		isNull(user, "当前用户为空");
		isNull("添加的分类为空", categroys);
		isNull(content.getUser(), "内容的创建者为空");

		checkId(content.getId(), "内容对象没有id");
		checkId(user.getId(), "当前用户对象没有id");
		matchUser(content.getUser().getId(), user, "没有添加分类的权限");
		checkCategoryIsReplicate(Arrays.asList(categroys));

		if (categroys != null)
			checkCategoryIsReplicate(Arrays.asList(categroys));

		ca.checkContentIsEx(content);
		ca.checkContentUserIsTrue(content);

		checkCategroyExist(categroys);
		checkContentCategoryCreator(user, categroys);
		checkCategoryHas(content, categroys);

		return ca.addContentCategorys(content, Arrays.asList(categroys)) == 1;
	}

	@Override
	public boolean deleteContentCategory(Content content, User user, Category... categroys) throws IncompleteException,
			InsufficientPermissionException, NotFoundException, DataException, DataDuplicationException {

		isNull(content, "删除分类的内容对象为空");
		isNull(user, "当前用户为空");
		isNull("删除的分类为空", categroys);
		isNull(content.getUser(), "内容的创建者为空");

		checkId(content.getId(), "内容对象没有id");
		checkId(user.getId(), "当前用户对象没有id");
		matchUser(content.getUser().getId(), user, "没有删除分类的权限");

		if (categroys != null)
			checkCategoryIsReplicate(Arrays.asList(categroys));

		ca.checkContentIsEx(content);
		ca.checkContentUserIsTrue(content);

		return ca.deleteContentCategorys(content, Arrays.asList(categroys)) == categroys.length;
	}

	@Override
	public boolean changeContentCategorys(Content content, User user) throws IncompleteException,
			InsufficientPermissionException, NotFoundException, DataException, DataDuplicationException {

		List<Category> categroys = content.getCategorys();

		isNull(content, "修改分类的内容对象为空");
		isNull(user, "当前用户为空");
		isNull(content.getUser(), "内容的创建者为空");

		checkId(content.getId(), "内容对象没有id");
		checkId(user.getId(), "当前用户对象没有id");
		matchUser(content.getUser().getId(), user, "没有修改分类的权限");
		checkCategoryIsReplicate(categroys);

		ca.checkContentIsEx(content);
		ca.checkContentUserIsTrue(content);

		if (categroys != null) {
			checkCategroyExist(categroys.toArray(new Category[0]));
			checkContentCategoryCreator(user, content.getCategorys().toArray(new Category[0]));
		}
		return ca.updateContentCategorys(content);
	}

	@Override
	public boolean reviewContent(Content... contents) throws OperationsException {

		for (Content c : contents) {
			if (c == null)
				continue;
			Content cc = new Content();
			cc.setState(2);
			cc.setId(c.getId());

			ca.updateContentOnlyByManage(cc);
		}
		return true;
	}

	/**
	 * 校验内容的分类和附件是否属于当前用户，如果他们存在的话。
	 * 
	 * @param content
	 * @param user
	 * @throws InsufficientPermissionException
	 */
	private void checkContentContainObj(Content content, User user) throws InsufficientPermissionException {

		if (content.getAttachs() != null)
			checkContentAttachCreator(user, content.getAttachs().toArray(new Attach[0]));

		if (content.getCategorys() != null)
			checkContentCategoryCreator(user, content.getCategorys().toArray(new Category[0]));
	}

	/**
	 * 校验附件的创建者是否和user对象一致。
	 * 
	 * @param user
	 * @param attachs
	 * @throws InsufficientPermissionException
	 */
	private void checkContentAttachCreator(User user, Attach... attachs) throws InsufficientPermissionException {
		if (attachs == null || attachs.length == 0)
			return;

		AttachApply as = new AttachApply();
		for (Attach a : attachs) {
			a = as.selectAttach(a.getId());
			matchUser(as.selectAttach(a.getId()).getUser().getId(), user, "内容所携带的附件不属于当前用户");
		}
	}

	private void checkContentCategoryCreator(User user, Category... categories) throws InsufficientPermissionException {
		if (categories == null || categories.length == 0)
			return;

		CategoryApply cs = new CategoryApply();
		for (Category c : categories) {
			c = cs.selectCategory(c.getId());
			matchUser(cs.selectCategory(c.getId()).getUser().getId(), user, "内容所携带的分类不属于当前用户");
		}
	}

	/**
	 * 添加不存在的tag
	 * 
	 * @param tags
	 * @throws DataDuplicationException 标签名已存在则会抛出此异常
	 */
	private void checkTags(Tag... tags) throws DataDuplicationException {

		if (tags == null || tags.length == 0)
			return;

		TagApply ta = new TagApply();

		for (Tag t : tags) {
			if (ta.selectTagByNameEx(t.getName()) == null) {
				ta.addTag(t);
			}
		}
	}

	/**
	 * 检测内容的标签数是否超过限制值
	 * 
	 * @param content
	 * @throws OperationsException
	 */
	private void checkTagsSize(Content content) throws OperationsException {
		if (content == null)
			return;

		if (content.getTags() != null && content.getTags().size() > 10)
			throw new OperationsException("内容标签数超出限制值(10)");
	}

	private void checkAttachExist(Attach... attachs) throws NotFoundException, IncompleteException {

		if (attachs == null || attachs.length == 0)
			return;

		AttachApply aa = new AttachApply();

		for (Attach a : attachs) {
			if (a.getId() == 0)
				throw new IncompleteException("附件没有id");

			if (aa.selectAttach(a.getId()) == null)
				throw new NotFoundException("附件不存在");
		}
	}

	private void checkCategroyExist(Category... categroys) throws NotFoundException, IncompleteException {

		if (categroys == null || categroys.length == 0)
			return;

		CategoryApply ca = new CategoryApply();

		for (Category c : categroys) {
			checkId(c.getId(), "分类对象没有id");

			if (ca.selectCategory(c.getId()) == null)
				throw new NotFoundException("分类不存在");
		}
	}

	/**
	 * 校验内容的标签，分类和附件是否存在重复。有为空判断，放心使用。
	 * 
	 * @param content
	 * @throws OperationsException
	 * @throws DataDuplicationException
	 */
	private void checkReplicate(Content content) throws DataDuplicationException {

		if (content == null)
			return;

		checkCategoryIsReplicate(content.getCategorys());

		checkAttachIsReplicate(content.getAttachs());

		checkTagIsReplicate(content.getTags());
	}

	private void checkTagHas(Content content, Tag... tags) throws DataDuplicationException {

		if (content == null || tags == null || tags.length == 0)
			return;

		Content c = ca.selectContent(content.getId());
		List<Tag> lt = c.getTags();
		if (c == null || lt == null || lt.isEmpty())
			return;

		List<Integer> list = new ArrayList<Integer>();
		for (Tag t : c.getTags())
			list.add(t.getId());

		for (Tag t : tags) {
			if (list.contains(t.getId()))
				throw new DataDuplicationException("标签已存在");
		}
	}

	private void checkCategoryHas(Content content, Category... categorys) throws DataDuplicationException {

		if (content == null || categorys == null || categorys.length == 0)
			return;

		Content c = ca.selectContent(content.getId());
		List<Category> lt = c.getCategorys();
		if (c == null || lt == null || lt.isEmpty())
			return;

		List<Integer> list = new ArrayList<Integer>();
		for (Category t : c.getCategorys())
			list.add(t.getId());

		for (Category t : categorys) {
			if (list.contains(t.getId()))
				throw new DataDuplicationException("分类已存在");
		}
	}

	private void checkAttachHas(Content content, Attach... attachs) throws DataDuplicationException {

		if (content == null || attachs == null || attachs.length == 0)
			return;

		Content c = ca.selectContent(content.getId());
		List<Attach> lt = c.getAttachs();
		if (c == null || lt == null || lt.isEmpty())
			return;

		List<Integer> list = new ArrayList<Integer>();
		for (Attach t : c.getAttachs())
			list.add(t.getId());

		for (Attach t : attachs) {
			if (list.contains(t.getId()))
				throw new DataDuplicationException("附件已存在");
		}
	}

	@Override
	public void moveContentToCategory(Content content, Category category) throws InsufficientPermissionException, NotFoundException {
		isNull(content, "要移动的内容为空");
		isNull(category, "分类为空");
		
		User ut = content.getUser();
		User uy = category.getUser();
		
		isNull(ut, "内容创建者属性为空");
		isNull(uy, "分类创建者属性为空");
		CheckTool.matchUser(ut.getId(), uy, "内容或者分类不属于当前用户");
		
		ca.checkContentIsEx(content);
		CategoryApply cay = new CategoryApply();
		if(!cay.checkCategoryIsExit(category)) {
			throw new NotFoundException("分类不存在");
		}
		
		ca.addContentCategory(content, category);
	}

}
