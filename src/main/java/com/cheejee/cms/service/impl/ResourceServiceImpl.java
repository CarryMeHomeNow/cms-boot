package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.isNull;

import com.cheejee.cms.apply.ResourceApply;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.pojo.Resource;
import com.cheejee.cms.service.ResourceService;
import com.github.pagehelper.PageInfo;

public class ResourceServiceImpl implements ResourceService {
	
	private ResourceApply ra;
	
	public ResourceServiceImpl() {
		this.ra = new ResourceApply();
	}

	@Override
	public Resource getResourceById(int id) {
		return ra.selectResource(id);
	}

	@Override
	public PageInfo<Resource> getResourceByName(int pageNum, int pageSize, String name) {
		
		if(name == null || name.equals(""))
			throw new NullPointerException();
		
		return ra.selectResourceByName(pageNum, pageSize, name);
	}

	@Override
	public PageInfo<Resource> getResourceByDesName(int pageNum, int pageSize, String DesName) {
		
		if(DesName == null || DesName.equals(""))
			throw new NullPointerException();
		
		return ra.selectResourceByName(pageNum, pageSize, DesName);
	}

	@Override
	public PageInfo<Resource> getResourceAll(int pageNum, int pageSize) {
		return ra.listResource(pageNum, pageSize);
	}

	@Override
	public boolean changeResourc(Resource res) throws IncompleteException {
		
		isNull(res, "修改的资源对象为空");
		checkId(res.getId(), "资源id为空");
		
		return ra.updateResource(res) == 1;
	}

	@Override
	public Resource getResourceByName(String name) {
		return ra.selectResourceByNameEx(name);
	}

}
