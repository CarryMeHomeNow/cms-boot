package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.isNull;
import static com.cheejee.cms.tools.CheckTool.matchUser;

import com.cheejee.cms.apply.ZoneApply;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.pojo.Zone;
import com.cheejee.cms.service.ZoneService;
import com.github.pagehelper.PageInfo;

@Deprecated
public class ZoneServiceImpl implements ZoneService {

	private ZoneApply za;

	public ZoneServiceImpl() {
		this.za = new ZoneApply();
	}

	@Override
	public Zone getZoneById(int id) {
		return za.selectZone(id);
	}

	@Override
	public PageInfo<Zone> getZoneByName(int pageNum, int pageSize, String name) {
		return za.selectZoneByName(pageNum, pageSize, name);
	}

	@Override
	public PageInfo<Zone> getZoneAll(int pageNum, int pageSize) {
		return za.listZone(pageNum, pageSize);
	}

	@Override
	public boolean addZone(Zone zone, User user) throws DataDuplicationException, IncompleteException, InsufficientPermissionException {
		
		isNull(zone, "添加的专栏对象为空");
		isNull(user, "单亲用户为空");
		isNull(zone.getCreator(), "专栏创建者为空");
		checkId(zone.getCreator().getId(), "专栏创建者的id为空");
		checkId(zone.getId(), "专栏id为空");
		checkId(user.getId(), "当前用户id为空");
		matchUser(zone.getCreator().getId(), user, "添加的专栏不属于当前用户");
		
		if (zone.getName() == null || zone.getName().equals(""))
			throw new IncompleteException("添加的专栏名称为空");
		
		return za.addZone(zone) == 1 ? true : false;
	}

	@Override
	public boolean deleteZoneByManage(Zone zone) throws NotFoundException {
		
		isNull(zone, "添加的专栏对象为空");
		
		if (za.selectZone(zone.getId()) == null)
			throw new NotFoundException("删除的专栏不存在");
		return za.deleteZone(zone) == 1 ? true : false;
	}
	
	@Override
	public boolean deleteZone(Zone zone, User user) throws NotFoundException, IncompleteException, NullPointerException, InsufficientPermissionException {
		
		isNull(zone, "删除的专栏对象为空");
		isNull(user, "当前用户为空");
		isNull(zone.getCreator(), "专栏创建者为空");
		checkId(zone.getCreator().getId(), "专栏创建者的id为空");
		checkId(zone.getId(), "专栏id为空");
		checkId(user.getId(), "当前用户id为空");
		matchUser(zone.getCreator().getId(), user, "删除的专栏不属于当前用户");
		
		if (za.selectZone(zone.getId()) == null)
			return true;
		
		return za.deleteZone(zone) == 1 ? true : false;
	}

	@Override
	public boolean changeZoneByManage(Zone zone) throws DataDuplicationException, NotFoundException, IncompleteException {
		
		isNull(zone, "更新的专栏对象为空");
		checkId(zone.getId(), "专栏的id为空");
		
		if (za.selectZone(zone.getId()) == null)
			throw new NotFoundException("更新的专栏不存在");
		
		return za.updateZone(zone) == 1 ? true : false;
	}

	@Override
	public boolean addContentToZoneByManage(Zone zone, Content... contents) {
		
		isNull(zone, "专栏对象为空");
		isNull("内容对象为空", contents);
		
		return za.addContentToZone(zone, contents) == contents.length;
	}

	@Override
	public boolean addContentToZone(Zone zone, User user, Content... contents) throws NotFoundException, IncompleteException, InsufficientPermissionException {
		
		isNull(zone, "专栏对象为空");
		isNull(user, "当前用户为空");
		checkId(zone.getId(), "专栏id为空");
		checkId(user.getId(), "当前用户id为空");
		
		for(Content c : contents) {
			checkId(c.getId(), "内容id为空");
			matchUser(c.getUser().getId(), user, "内容不属于当前用户");
		}
		
		if (za.selectZone(zone.getId()) == null)
			throw new NotFoundException("专栏不存在");
		
		return za.addContentToZone(zone, contents) == contents.length;
	}

	@Override
	public boolean deleteContentToZoneByManage(Zone zone, Content... contents) {
		isNull(zone, "专栏对象为空");
		isNull("内容对象为空", contents);
		
		return za.deleteContentToZone(zone, contents) == contents.length;
	}

	@Override
	public boolean deleteContentToZone(Zone zone, User user, Content... contents) throws NotFoundException, IncompleteException, InsufficientPermissionException {
		
		isNull(zone, "专栏对象为空");
		isNull(user, "当前用户为空");
		checkId(zone.getId(), "专栏id为空");
		checkId(user.getId(), "当前用户id为空");
		
		for(Content c : contents) {
			checkId(c.getId(), "内容id为空");
			matchUser(c.getUser().getId(), user, "内容不属于当前用户");
		}
		
		if (za.selectZone(zone.getId()) == null)
			throw new NotFoundException("专栏不存在");
		
		return za.deleteContentToZone(zone, contents) == contents.length;
	}

	@Override
	public boolean changeZone(Zone zone, User user) throws IncompleteException, InsufficientPermissionException, NotFoundException, DataDuplicationException {
		
		isNull(zone, "修改的专栏对象为空");
		isNull(user, "当前用户为空");
		isNull(zone.getCreator(), "专栏创建者为空");
		checkId(zone.getCreator().getId(), "专栏创建者的id为空");
		checkId(zone.getId(), "专栏id为空");
		checkId(user.getId(), "当前用户id为空");
		matchUser(zone.getCreator().getId(), user, "修改的专栏不属于当前用户");
		
		if (za.selectZone(zone.getId()) == null)
			throw new NotFoundException("修改的专栏不属于当前用户");
		
		return za.updateZone(zone) == 1;
	}

	@Override
	public boolean addZoneAdminByManage(Zone zone, User... admins) throws IncompleteException {
		
		isNull(zone, "专栏对象为空");
		isNull("管理员为空", admins);
		checkId(zone.getId(), "专栏id为空");
		for(User u : admins)
			checkId(u.getId(), "管理员id为空");
		
		return za.addZoneAdmin(zone, admins) == admins.length;
	}

	@Override
	public boolean deleteZoneAdminByManage(Zone zone, User... admins) throws IncompleteException {
		
		isNull(zone, "专栏对象为空");
		isNull("管理员为空", admins);
		checkId(zone.getId(), "专栏id为空");
		for(User u : admins)
			checkId(u.getId(), "管理员id为空");
		
		return za.deleteZoneAdmin(zone, admins) == admins.length;
	}

	@Override
	public boolean changeZoneAdminByManage(Zone zone) throws IncompleteException, DataDuplicationException {
		
		isNull(zone, "专栏对象为空");
		isNull(zone.getAdmins(), "管理员为空");
		checkId(zone.getId(), "专栏id为空");
		
		for(User u : zone.getAdmins()) {
			isNull(u, "管理员对象为空");
			checkId(u.getId(), "管理员id为空");
		}
		
		Zone z = new Zone();
		z.setId(zone.getId());
		z.setAdmins(zone.getAdmins());
		
		return za.updateZone(z) == 1;
	}

	@Override
	public boolean addZoneAdmin(Zone zone, User user, User... admins) throws IncompleteException, InsufficientPermissionException {
		
		isNull(zone, "专栏对象为空");
		isNull(user, "当前用户为空");
		isNull(zone.getCreator(), "专栏创建者为空");
		isNull("管理员为空", admins);
		checkId(zone.getId(), "专栏id为空");
		checkId(zone.getCreator().getId(), "专栏创建者id为空");
		checkId(user.getId(), "当前用户id为空");
		
		for(User u : admins)
			checkId(u.getId(), "管理员id为空");
		
		matchUser(zone.getCreator().getId(), user, "专栏不属于当前用户");
		
		return za.addZoneAdmin(zone, admins) == admins.length;
	}

	@Override
	public boolean deleteZoneAdmin(Zone zone, User user, User... admins) throws IncompleteException, InsufficientPermissionException{
			
		isNull(zone, "专栏对象为空");
		isNull(user, "当前用户为空");
		isNull(zone.getCreator(), "专栏创建者为空");
		isNull("管理员为空", admins);
		checkId(zone.getId(), "专栏id为空");
		checkId(zone.getCreator().getId(), "专栏创建者id为空");
		checkId(user.getId(), "当前用户id为空");
		
		for(User u : admins)
			checkId(u.getId(), "管理员id为空");
		
		matchUser(zone.getCreator().getId(), user, "专栏不属于当前用户");
		
		return za.deleteZoneAdmin(zone, admins) == admins.length;
	}

	@Override
	public boolean changeZoneAdmin(Zone zone, User user) throws IncompleteException, InsufficientPermissionException, DataDuplicationException {
		
		isNull(zone, "专栏对象为空");
		isNull(user, "当前用户为空");
		isNull(zone.getCreator(), "专栏创建者为空");
		checkId(zone.getId(), "专栏id为空");
		checkId(zone.getCreator().getId(), "专栏创建者id为空");
		checkId(user.getId(), "当前用户id为空");
		matchUser(zone.getCreator().getId(), user, "专栏不属于当前用户");
		
		Zone z = new Zone();
		z.setId(zone.getId());
		z.setAdmins(zone.getAdmins());
		
		return za.updateZone(z) == 1;
	}
}
