package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.isNull;
import static com.cheejee.cms.tools.CheckTool.matchUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cheejee.cms.apply.AttachApply;
import com.cheejee.cms.apply.SuffixApply;
import com.cheejee.cms.apply.UserApply;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.NotSupportedException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.pojo.Type;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.service.AttachService;
import com.github.pagehelper.PageInfo;

public class AttachServiceImpl implements AttachService {

	private AttachApply aa;

	public AttachServiceImpl() {
		this.aa = new AttachApply();
	}

	@Override
	public Attach getAttachByIdByManage(int id) {
		Attach a = aa.selectAttach(id);
		if (a != null)
			a.getUser();
		return a;
	}

	@Override
	public Attach getAttachById(int id, User user) throws IncompleteException {

		isNull(user, "当前登录用户为空");
		checkId(user.getId(), "当前登录用户id为空");

		Attach a = aa.selectAttach(id, user);
		if (a != null)
			a.getUser();
		return a;
	}

	@Override
	public PageInfo<Attach> getAttachByNameManage(int pageNum, int pageSize, String name) {
		isNull(name, "名称为空");
		return aa.selectAttachByName(pageNum, pageSize, name);
	}

	@Override
	public PageInfo<Attach> getAttachByName(int pageNum, int pageSize, String name, User user)
			throws IncompleteException {

		isNull(user, "当前登录用户为空");
		isNull(name, "名称为空");
		checkId(user.getId(), "当前登录用户id为空");

		return aa.selectAttachByName(pageNum, pageSize, name, user);
	}

	@Override
	public PageInfo<Attach> getAttachByTypeManage(int pageNum, int pageSize, Type type) throws IncompleteException {

		isNull(type, "类型对象为空");
		checkId(type.getId(), "类型id为空");

		return aa.selectAttachByTypeManage(pageNum, pageSize, type);
	}

	@Override
	public PageInfo<Attach> getAttachByType(int pageNum, int pageSize, Type type, User user)
			throws IncompleteException {

		isNull(type, "类型对象为空");
		isNull(user, "当前登录用户为空");
		checkId(user.getId(), "当前登录用户id为空");
		checkId(type.getId(), "类型id为空");

		return aa.selectAttachByType(pageNum, pageSize, type, user);
	}

	@Override
	public PageInfo<Attach> getAttachByUser(int pageNum, int pageSize, User user)
			throws IncompleteException, NotFoundException {

		isNull(user, "用户对象为空");
		checkId(user.getId(), "用户id为空");

		UserApply ua = new UserApply();
		ua.checkUserIsExit(user);

		return aa.selectAttachByUser(pageNum, pageSize, user);
	}

	@Override
	public PageInfo<Attach> getAttachAllByManage(int pageNum, int pageSize) {
		return aa.listAttach(pageNum, pageSize);
	}

	@Override
	public boolean addAttach(User user, Attach... attachs) throws DataDuplicationException,
			InsufficientPermissionException, IncompleteException, NotSupportedException {

		isNull("添加的附件对象为空", attachs);
		isNull(user, "当前登录用户对象为空");
		checkId(user.getId(), "当前用户id为空");

		for (Attach a : attachs) {
			isNull(a.getUser(), "附加所属用户为空");
			checkId(a.getUser()
					.getId(), "附件创建者的id为空");
			matchUser(a.getUser()
					.getId(), user, "此附件不属于当前用户：" + a);
		}
		addAttachSuffix(attachs);

		return aa.addAttachs(Arrays.asList(attachs)) == attachs.length ? true : false;
	}

	@Override
	public void deleteAttachs(User user, Attach... attachs)
			throws NotFoundException, IncompleteException, OperationsException, InsufficientPermissionException {

		isNull("删除的附件对象为空", attachs);
		isNull(user, "当前登录用户对象为空");
		checkId(user.getId(), "当前用户id为空");
		List<Attach> as = new ArrayList<Attach>();

		for (Attach a : attachs) {
			checkId(a.getId(), "附件id为空");
			
			Attach t = aa.selectAttach(a.getId());
			if (t == null) {
				continue;
			}
			
			matchUser(t.getUser().getId(), user, "此附件不属于当前用户：" + a);
			as.add(a);
		}
		if (!aa.checkAttachNotUse(attachs))
			throw new OperationsException("还有内容在使用这个附件，不能删除");

		aa.deleteAttachs(as);
	}

	@Override
	public void deleteAttachsForce(User user, Attach... attachs)
			throws IncompleteException, InsufficientPermissionException {

		isNull("删除的附件对象为空", attachs);
		isNull(user, "当前登录用户对象为空");
		checkId(user.getId(), "当前用户id为空");
		List<Attach> as = new ArrayList<Attach>();

		for (Attach a : attachs) {
			checkId(a.getId(), "附件id为空");
			
			Attach t = aa.selectAttach(a.getId());
			if (t == null) {
				continue;
			}
			
			matchUser(t.getUser().getId(), user, "此附件不属于当前用户：" + a);
			as.add(a);
		}

		aa.deleteAttachs(as);
	}

	@Override
	public boolean changeAttach(Attach attach, User user) throws DataDuplicationException, NotFoundException,
			IncompleteException, InsufficientPermissionException, DataException, NotSupportedException {

		isNull(attach, "修改的附件对象为空");
		isNull(user, "当前登录用户对象为空");
		isNull(attach.getUser(), "附件所属用户为空");

		checkId(user.getId(), "当前用户id为空");
		checkId(attach.getUser()
				.getId(), "附件创建者的id为空");

		matchUser(attach.getUser()
				.getId(), user, "此附件不属于当前用户：" + attach);
		checkAttach(attach);
		addAttachSuffix(attach);

		return aa.updateAttach(attach) == 1 ? true : false;
	}

	@Override
	public boolean checkAttachIsSupported(Attach... attachs) throws IncompleteException {

		isNull("附件为空", attachs);

		for (Attach a : attachs) {

			if (a.getUrl() == null || a.getUrl()
					.equals(""))
				throw new IncompleteException("附件url为空");

			if (a.getUrl()
					.split("\\.").length != 2)
				return false;
		}

		return aa.checkAttachCanUse(attachs);
	}

	@Override
	public void deleteAttachsByManage(Attach... attachs) throws IncompleteException, OperationsException {

		isNull("附件为空", attachs);

		for (Attach a : attachs)
			checkId(a.getId(), "附件id为空");

		if (!aa.checkAttachNotUse(attachs))
			throw new OperationsException("还有内容在使用这个附件，不能删除");

		aa.deleteAttachs(Arrays.asList(attachs));
	}

	@Override
	public boolean deleteAttachsByManageForce(Attach... attachs) throws IncompleteException {

		isNull("附件为空", attachs);

		for (Attach a : attachs)
			checkId(a.getId(), "附件id为空");

		return aa.deleteAttachs(Arrays.asList(attachs)) == attachs.length;
	}

	@Override
	public boolean changeAttachByManage(Attach attach)
			throws IncompleteException, DataDuplicationException, NotFoundException, NotSupportedException {

		isNull(attach, "附件为空");
		checkId(attach.getId(), "附件id为空");

		if (aa.selectAttach(attach.getId()) == null)
			throw new NotFoundException("附件不存在");

		addAttachSuffix(attach);

		return aa.updateAttach(attach) == 1 ? true : false;
	}

	/**
	 * 校验附件是否存在，校验附件的创建者属性是否正确。
	 * 
	 * @param attach
	 * @throws NotFoundException
	 * @throws DataException
	 */
	private void checkAttach(Attach attach) throws NotFoundException, DataException {

		Attach a = aa.selectAttach(attach.getId());

		if (a == null)
			throw new NotFoundException("要修改的附件不存在");
		if (a.getId() != attach.getId())
			throw new DataException("附件创建者与数据库不匹配");
	}

	/**
	 * 添加附件的后缀，通过识别附件的url来进行添加。 添加前会先进行判断，判断附件url是否正确，附件格式是否受支持。
	 * 
	 * @param attachs
	 * @throws IncompleteException
	 * @throws NotSupportedException
	 */
	private void addAttachSuffix(Attach... attachs) throws IncompleteException, NotSupportedException {

		if (attachs == null || attachs.length == 0)
			return;

		if (!checkAttachIsSupported(attachs))
			throw new NotSupportedException("附件格式不受支持");

		SuffixApply sa = new SuffixApply();
		for (Attach a : attachs) {
			a.setsuffix(sa.selectSuffixBySuff(a.getUrl()
					.split("\\.")[1]));
		}
	}

}
