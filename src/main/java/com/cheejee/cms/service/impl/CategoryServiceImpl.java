package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.isNull;
import static com.cheejee.cms.tools.CheckTool.matchUser;

import java.util.ArrayList;
import java.util.List;

import com.cheejee.cms.apply.CategoryApply;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Category;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.service.CategoryService;
import com.github.pagehelper.PageInfo;

public class CategoryServiceImpl implements CategoryService {

	private CategoryApply ca;

	public CategoryServiceImpl() {
		this.ca = new CategoryApply();
	}

	@Override
	public Category getCategoryByIdManage(int id) {
		return ca.selectCategory(id);
	}

	@Override
	public Category getCategoryById(int id, User user) throws IncompleteException {

		isNull(user, "当前用户为空");
		checkId(user.getId(), "当前用户的id为空");

		return ca.selectCategory(id, user);
	}

	@Override
	public PageInfo<Category> getCategoryByNameManage(int pageNum, int pageSize, String name) {
		return ca.selectCategoryByName(pageNum, pageSize, name);
	}

	@Override
	public PageInfo<Category> getCategoryByName(int pageNum, int pageSize, String name, User user)
			throws IncompleteException {

		isNull(user, "当前用户为空");
		checkId(user.getId(), "当前用户的id为空");
		isNull(name, "名称为空");

		return ca.selectCategoryByName(pageNum, pageSize, name, user);
	}

	@Override
	public PageInfo<Category> getCategoryByUser(int pageNum, int pageSize, User user) {
		return ca.selectCategoryByUser(pageNum, pageSize, user);
	}

	@Override
	public PageInfo<Category> getCategoryAllByManage(int pageNum, int pageSize) {
		return ca.listCategory(pageNum, pageSize);
	}

	@Override
	public List<Content> getCategoryHasContentByManage(Category category) throws IncompleteException {
		isNull(category, "分类为空");
		checkId(category.getId(), "分类id为空");

		return ca.selectCategory(category.getId())
				.getContents();
	}

	@Override
	public List<Content> getCategoryHasContent(Category category, User user)
			throws IncompleteException, InsufficientPermissionException {

		isNull(category, "分类为空");
		checkId(category.getId(), "分类id为空");
		isNull(category.getUser(), "分类所属用户为空");
		matchUser(category.getUser()
				.getId(), user, "分类不属于当前用户");

		return ca.selectCategory(category.getId())
				.getContents();
	}

	@Override
	public boolean addCategory(User user, Category... categorys)
			throws DataDuplicationException, IncompleteException, InsufficientPermissionException {

		isNull("添加的分类对象为空", categorys);
		isNull(user, "当前用户为空");
		checkId(user.getId(), "当前用户对象的id为空");

		for (Category c : categorys) {
			isNull(c.getUser(), "添加的分类没有所属用户：" + categorys);
			checkId(c.getUser()
					.getId(), "分类所属用户的id为空");
			matchUser(c.getUser()
					.getId(), user, "添加的分类对象的所属者错误");

			if (c.getName() == null || c.getName()
					.equals(""))
				throw new IncompleteException("分类名称为空");
		}

		return ca.addCategory(categorys) == categorys.length;
	}

	@Override
	public void deleteCategory(User user, Category... categorys) throws OperationsException, IncompleteException,
			InsufficientPermissionException, DataException, NotFoundException {

		isNull("删除的分类对象为空", categorys);
		isNull(user, "当前用户为空");
		checkId(user.getId(), "当前用户对象的id为空");
		List<Category> lc = new ArrayList<Category>();

		for (Category c : categorys) {
			checkId(c.getId(), "删除的分类id为空");
			Category ty = ca.selectCategory(c.getId());
			if (ty == null) {
				continue;
			}

			if (!ca.checkCategoryContentIsNull(c)) {
				throw new OperationsException(c.getName() + "分类下还有内容，不能删除");
			}
			
			matchUser(ty.getUser().getId(), user, "只能删除自己的分类");
			lc.add(c);
		}

		ca.deleteCategorys(lc);
	}

	@Override
	public void deleteCategoryForce(User user, Category... categorys)
			throws IncompleteException, InsufficientPermissionException {

		isNull("删除的分类对象为空", categorys);
		isNull(user, "当前用户为空");
		checkId(user.getId(), "当前用户对象的id为空");
		List<Category> lc = new ArrayList<Category>();

		for (Category c : categorys) {
			checkId(c.getId(), "删除的分类id为空");
			Category ty = ca.selectCategory(c.getId());
			if (ty == null) {
				continue;
			}

			matchUser(ty.getUser()
					.getId(), user, "只能删除自己的分类");
			lc.add(c);
		}

		ca.deleteCategorys(lc);
	}

	@Override
	public boolean changeCategory(User user, Category c) throws DataDuplicationException, NotFoundException,
			IncompleteException, DataException, InsufficientPermissionException {
		
		Category category = ca.selectCategory(c.getId());
		category.setName(c.getName());

		isNull(category, "分类不存在");
		isNull(user, "当前用户为空");
		checkId(user.getId(), "当前用户对象的id为空");

		if (category.getName() == null || category.getName().equals(""))
			throw new IncompleteException("分类名称为空");

		matchUser(category.getUser().getId(), user, "分类不属于当前用户");

		return ca.updateCategory(category) == 1 ? true : false;
	}
}
