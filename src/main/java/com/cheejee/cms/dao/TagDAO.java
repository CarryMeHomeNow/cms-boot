package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.Tag;

public interface TagDAO {
	
	Tag selectTag(int id);
	
	Tag selectTagByNameEx(String name);
	
	List<Tag> selectTagForName(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize, @Param("name")String name);
	
	List<Tag> selectTagForContent(Content c);
	
	List<Tag> listTag(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize);
	
	int selectTagNameCount(Tag tag);
	
	int selectTagNameCountExSelf(Tag tag);
	
	int addTags(@Param("tags")List<Tag> tags);
	
	int deleteTag(Tag tags);
	
	int deleteTagRelate(@Param("tags")Tag...tags);
	
	int updateTag(Tag c);


}
