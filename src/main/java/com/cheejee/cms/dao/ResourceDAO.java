package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Resource;

public interface ResourceDAO {

	Resource selectResource(int id);

	Resource selectResourceByNaemEx(String name);
	
	List<Resource> selectResourceByName(@Param("pageNumKey")int pageNum, @Param("pageSizeKey")int pageSize, @Param("name")String name);
	
	List<Resource> selectResourceByDesName(@Param("pageNumKey")int pageNum, @Param("pageSizeKey")int pageSize, @Param("desName")String desName);
	
	List<Resource> listResource(@Param("pageNumKey")int pageNum, @Param("pageSizeKey")int pageSize);

	int selectResourceNameCount(String name);

	int addResource(@Param("resource") Resource... resources);

	int deleteResource(Resource resource);

	int updateResource(Resource resource);
}
