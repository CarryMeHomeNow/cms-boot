package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Suffix;

public interface SuffixDAO {
	
	Suffix selectSuffix(int id);
	
	List<Suffix> listSuffix(@Param("pageNumKey")int pagNum, @Param("pageSizeKey")int pageSize);
	
	List<Suffix> selectSuffixNoType(@Param("pageNumKey")int pageNum, @Param("pageSizeKey")int pageSize);
	
	int selectCountBySuffix(String suffix);
	
	Suffix selectSuffixBySuff(String suffix);
	
	int addSuffixs(@Param("suffixs")List<Suffix> suffixs);
	
	int deleteSuffixs(@Param("suffixs")List<Suffix> suffixs);
	
	int updateSuffix(Suffix suffix);
}
