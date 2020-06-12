package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Suffix;
import com.cheejee.cms.pojo.Type;

public interface TypeDAO {
	
	Type selectType(int id);
	
	List<Type> listType(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize);
	
	List<Type> selectTypeByName(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize, @Param("name")String name);
	
	Type selectTypeByNameExact(String name);
	
	int selectTypeNameCount(Type type);
	
	int selectTypeNameCountExSelf(Type type);
	
	int selectTypeCountBySuffix(@Param("type")Type type, @Param("suffix")Suffix suffix);
	
	int addTypes(@Param("types")List<Type> types);
	
	int addTypeSuffixs(@Param("type")Type type, @Param("suffixs")List<Suffix> suffixs);
	
	int deleteTypeSuffixs(@Param("type")Type type, @Param("suffixs")List<Suffix> suffixs);
	
	int deleteTypeSuffixsAll(Type type);
	
	int deleteTypes(@Param("types")List<Type> types);

	int updateType(Type type);
}
