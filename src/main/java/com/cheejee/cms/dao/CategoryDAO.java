package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Category;
import com.cheejee.cms.pojo.User;

public interface CategoryDAO {
	
	Category selectCategory(int id);
	
	Category selectCategoryByIdAndUser(@Param("id")int id, @Param("user")User user);
	
	List<Category> selectCategoryByName(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize, @Param("name")String name);
	
	List<Category> selectCategoryByNameAndUser(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize, @Param("name")String name, @Param("user")User user);
	
	List<Category> selectCategoryByUser(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize, @Param("user")User user);
	
	List<Category> listCategory(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize);
	
	int selectCategoryContentCount(Category category);
	
	int selectCategroyNameCountOnCreater(Category c);
	
	int selectCategroyNameCountOnCreaterExSelf(Category c);
	
	int addCategory(@Param("categorys")Category...categorys);
		
	int deleteCategorys(@Param("categorys")List<Category> categorys);
	
	int deleteCategoryRelate(@Param("categorys")List<Category> categorys);
	
	int updateCategory(Category c);
}
