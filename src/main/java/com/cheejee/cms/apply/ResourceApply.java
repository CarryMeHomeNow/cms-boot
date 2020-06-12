package com.cheejee.cms.apply;

import java.util.HashSet;
import java.util.Set;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.ResourceDAO;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.pojo.Resource;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class ResourceApply {
	
	SqlSessionFactory ssf;
	
	public ResourceApply() {
		this.ssf = SSF.getSSF();
	}
	
	public Resource selectResource(int id) {
		try(SqlSession session = ssf.openSession()){
			ResourceDAO rd = session.getMapper(ResourceDAO.class);
			return rd.selectResource(id);
		}
	}
	
	public Resource selectResourceByNameEx(String name) {
		try(SqlSession session = ssf.openSession()){
			ResourceDAO rd = session.getMapper(ResourceDAO.class);
			return rd.selectResourceByNaemEx(name);
		}
	}
	
	public PageInfo<Resource> selectResourceByName(int pageNum, int pageSize, String name) {
		try(SqlSession session = ssf.openSession()){
			ResourceDAO rd = session.getMapper(ResourceDAO.class);
			return new PageInfo<Resource>(rd.selectResourceByName(pageNum, pageSize, name));
		}
	}
	
	public PageInfo<Resource> selectResourceByDesName(int pageNum, int pageSize, String DesName) {
		try(SqlSession session = ssf.openSession()){
			ResourceDAO rd = session.getMapper(ResourceDAO.class);
			return new PageInfo<Resource>(rd.selectResourceByDesName(pageNum, pageSize, DesName));
		}
	}
	
	public PageInfo<Resource> listResource(int pageNum, int pageSize) {
		try(SqlSession session = ssf.openSession()){
			ResourceDAO rd = session.getMapper(ResourceDAO.class);
			return new PageInfo<Resource>(rd.listResource(pageNum, pageSize));
		}
	}
	
	public int addResource(Resource...res) throws DataDuplicationException {
		
		try(SqlSession session = ssf.openSession(true)){
			
			ResourceDAO rd = session.getMapper(ResourceDAO.class);
			Set<String> set = new HashSet<String>();
			
			for(Resource r : res) {
				if(rd.selectResourceNameCount(r.getName()) != 0)
					throw new DataDuplicationException("资源名称已存在：" + r.getName());
				
				set.add(r.getName());
			}
			
			if(set.size() != res.length)
				throw new DataDuplicationException("存在相同的资源名称");
			
			return rd.addResource(res);
		}
	}
	
	public int deleteResource(Resource res) {
		try(SqlSession session = ssf.openSession(true)){
			ResourceDAO rd = session.getMapper(ResourceDAO.class);
			return rd.deleteResource(res);
		}
	}
	
	public int updateResource(Resource res) throws PersistenceException{
		try(SqlSession session = ssf.openSession(true)){
			ResourceDAO rd = session.getMapper(ResourceDAO.class);
			return rd.updateResource(res);
		} catch (PersistenceException e) {
			throw e;
		}
	}

}
