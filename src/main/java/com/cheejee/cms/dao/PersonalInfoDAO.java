package com.cheejee.cms.dao;

import com.cheejee.cms.pojo.PersonalInfo;
import com.cheejee.cms.pojo.User;

public interface PersonalInfoDAO {
	
	PersonalInfo selectPersonalInfo(User user);

	int addPersonalInfo(PersonalInfo pi);
	
	int updatePersonalInfo(PersonalInfo pi);

}
