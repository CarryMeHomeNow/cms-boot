package com.cheejee.cms.tools;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SSF {
	
	static SqlSessionFactory ssf;
	
	/**
	 * 获取SqlSessionFactory对象，从根目录获取mybatis-config.xml配置文件
	 * @return SqlsessionFactory
	 */
	public static SqlSessionFactory getSSF() {
		if(ssf == null)
			try {
				ssf = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return ssf;
	}

}
