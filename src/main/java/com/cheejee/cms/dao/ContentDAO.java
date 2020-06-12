package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.pojo.Category;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.Tag;
import com.cheejee.cms.pojo.User;

public interface ContentDAO {

	Content selectContent(int id);

	Content selectContentByIdAndUser(@Param("id") int id, @Param("user") User user);

	List<Content> listContent(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize);

	List<Content> selectContentByTitle(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize,
			@Param("title") String title);
	
	List<Content> searchContent(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize,
			@Param("title") String title, @Param("state") Integer state);
	
	List<Content> selectContentByTitleAndUser(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize,
			@Param("title") String title, @Param("user") User user);

	List<Content> selectContentByState(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize,
			@Param("state") Integer state);

	List<Content> selectContentByUser(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize,
			@Param("user") User user);

	List<Content> selectContentForCategory(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize,
			@Param("category") Category category, @Param("user") User user);
	
	List<Content> selectContentNoCategory(@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize,
			@Param("user") User user);

	List<Content> selectContentByUserAndState(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize,
			@Param("user") User user, @Param("state") Integer state);

	int addContent(Content content);

	int addContentTags(@Param("content") Content c, @Param("tags") List<Tag> tags);

	int addContentAttachs(@Param("content") Content c, @Param("attachs") List<Attach> attachs);

	int addContentCategorys(@Param("content") Content c, @Param("categorys") List<Category> categorys);

	int deleteContentCategorys(@Param("content") Content c, @Param("categorys") List<Category> categorys);

	int deleteContentCategorysAll(Content c);

	int deleteContentAttachs(@Param("content") Content c, @Param("attachs") List<Attach> attachs);

	int deleteContentAttachsAll(Content c);

	int deleteContentZonesAll(Content c);

	int deleteContentTags(@Param("content") Content c, @Param("tags") List<Tag> tags);

	int deleteContents(@Param("contents") List<Content> contents);

	int updateContent(Content c);

	int updateContentByManage(Content c);

	

}
