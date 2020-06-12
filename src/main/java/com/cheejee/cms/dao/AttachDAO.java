package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.pojo.Type;
import com.cheejee.cms.pojo.User;

public interface AttachDAO {
	
	Attach selectAttach(int id);
	
	Attach selectAttachByIdAndUser(@Param("id")int id, @Param("user")User user);
	
	List<Attach> selectAttachByName(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize, @Param("name")String name);
	
	List<Attach> selectAttachByNameAndUser(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize, @Param("name")String name, @Param("user")User user);
	
	List<Attach> selectAttachByType(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize, @Param("type")Type type);
	
	List<Attach> selectAttachByTypeAndUser(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize, @Param("type")Type type, @Param("user")User user);
	
	List<Attach> selectAttachByUser(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize, @Param("user")User user);
	
	List<Attach> listAttach(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize);
	
	int selectAttachCountByURL(String url);
	
	int selectAttachCountByURLNoSelf(Attach attach);
	
	int selectAttachCountByContent(@Param("attachs")Attach...attachs);
	
	int addAttachs(@Param("attachs")List<Attach> attachs);
	
	int deleteAttachs(@Param("attachs")List<Attach> attachs);
	
	int deleteAttachsRelate(@Param("attachs")List<Attach> attachs);
	
	int updateAttach(Attach attach);



}
