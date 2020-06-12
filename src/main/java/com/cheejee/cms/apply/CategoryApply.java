package com.cheejee.cms.apply;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.CategoryDAO;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.pojo.Category;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class CategoryApply {

	SqlSessionFactory ssf;

	public CategoryApply() {
		this.ssf = SSF.getSSF();
	}

	public Category selectCategory(int id) {
		try (SqlSession session = ssf.openSession()) {
			CategoryDAO cd = session.getMapper(CategoryDAO.class);
			return cd.selectCategory(id);
		}
	}
	
	public Category selectCategory(int id, User user) {
		try (SqlSession session = ssf.openSession()) {
			CategoryDAO cd = session.getMapper(CategoryDAO.class);
			return cd.selectCategoryByIdAndUser(id, user);
		}
	}

	/**
	 * 模糊查询，匹配开头字母
	 * 
	 * @param name
	 * @return
	 */
	public PageInfo<Category> selectCategoryByName(int pagNum, int pageSize, String name) {
		try (SqlSession session = ssf.openSession()) {
			CategoryDAO cd = session.getMapper(CategoryDAO.class);
			return new PageInfo<Category>(cd.selectCategoryByName(pagNum, pageSize, name));
		}
	}
	
	public PageInfo<Category> selectCategoryByName(int pagNum, int pageSize, String name, User user) {
		try (SqlSession session = ssf.openSession()) {
			CategoryDAO cd = session.getMapper(CategoryDAO.class);
			return new PageInfo<Category>(cd.selectCategoryByNameAndUser(pagNum, pageSize, name, user));
		}
	}

	public PageInfo<Category> selectCategoryByUser(int pagNum, int pageSize, User user) {
		try (SqlSession session = ssf.openSession()) {
			CategoryDAO cd = session.getMapper(CategoryDAO.class);
			return new PageInfo<Category>(cd.selectCategoryByUser(pagNum, pageSize, user));
		}
	}

	public PageInfo<Category> listCategory(int pagNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			CategoryDAO cd = session.getMapper(CategoryDAO.class);
			return new PageInfo<Category>(cd.listCategory(pagNum, pageSize));
		}
	}

	public boolean checkCategoryContentIsNull(Category... categorys) {
		try (SqlSession session = ssf.openSession()) {
			CategoryDAO cd = session.getMapper(CategoryDAO.class);

			for (Category c : categorys) {
				if (cd.selectCategoryContentCount(c) != 0)
					return false;
			}
			return true;
		}
	}

	public int addCategory(Category... categorys) throws DataDuplicationException {

		try (SqlSession session = ssf.openSession(true)) {
			
			CategoryDAO cd = session.getMapper(CategoryDAO.class);
			Set<String> set = new HashSet<String>();
			
			for (Category c : categorys) {
				if (cd.selectCategroyNameCountOnCreater(c) != 0)
					throw new DataDuplicationException("分类名称已存在：" + c.getName());
				
				set.add(c.getName());
			}
			
			if(set.size() != categorys.length)
				throw new DataDuplicationException("添加的分类里有重复名称的分类");
			
			return cd.addCategory(categorys);
		}
	}

	public int deleteCategory(Category category) {
		if (category == null)
			return 0;
		List<Category> lc = new ArrayList<Category>();
		lc.add(category);
		return deleteCategorys(lc);
	}

	public int deleteCategorys(List<Category> categorys) {

		if (categorys == null || categorys.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession()) {
			CategoryDAO cd = session.getMapper(CategoryDAO.class);
			int a = cd.deleteCategorys(categorys);
			cd.deleteCategoryRelate(categorys);
			
			session.commit();
			return a;
		}
	}

	public int updateCategory(Category c) throws DataDuplicationException {

		try (SqlSession session = ssf.openSession()) {
			CategoryDAO cd = session.getMapper(CategoryDAO.class);

			if (cd.selectCategroyNameCountOnCreaterExSelf(c) != 0)
				throw new DataDuplicationException("分类名称已存在：" + c.getName());

			int a = cd.updateCategory(c);
			session.commit();
			return a;
		}
	}
	
	/**
	 * 校验分类对象的user属性和数据库是否匹配
	 * @param categorys
	 * @return
	 */
	public boolean checkCategoryCreater(Category...categorys) {
		try (SqlSession session = ssf.openSession()) {
			CategoryDAO cd = session.getMapper(CategoryDAO.class);
			
			for(Category c : categorys) {
				if(c.getUser().getId() != cd.selectCategory(c.getId()).getUser().getId())
					return false;
			}
		}
		return true;
	}
	
	public boolean checkCategoryIsExit(Category...categories) {
		try (SqlSession session = ssf.openSession()) {
			CategoryDAO cd = session.getMapper(CategoryDAO.class);
			
			for(Category c : categories) {
				if(cd.selectCategory(c.getId()) == null)
					return false;
			}
			return true;
		}
	}

}
