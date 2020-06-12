package com.cheejee.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.pojo.Zone;

@Deprecated
public interface ZoneDAO {

	Zone selectZone(int id);

	List<Zone> selectZoneByName(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize,
			@Param("name") String name);

	List<Zone> selectZoneByCreator(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize,
			@Param("user")User user);
	
	List<Zone> listZone(@Param("pageNumKey") int pagNum, @Param("pageSizeKey") int pageSize);
	
	int selectCountByName(Zone zone);
	
	int selectCountByNameAndCreatorNoSelf(Zone zone);

	int addZone(Zone c);

	int addAdmins(@Param("zone")Zone z, @Param("admins")User...admins);
	
	int addContentToZone(@Param("zone")Zone zone, @Param("content")Content...contents);
	
	int deleteContentToZone(@Param("zone")Zone zone, @Param("content")Content...contents);
	
	int deleteContentToZoneAll(@Param("zone")Zone...zones);
	
	int reduceAdmins(@Param("zone")Zone z, @Param("admins")User...admins);

	int reduceAdminsAll(@Param("zone") Zone z);

	int deleteZone(Zone zone);

	int updateZone(Zone c);

}
