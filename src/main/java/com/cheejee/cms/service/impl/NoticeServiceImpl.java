package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.isNull;

import com.cheejee.cms.apply.NoticeApply;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.pojo.Notice;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.service.NoticeService;
import com.github.pagehelper.PageInfo;

@Deprecated
public class NoticeServiceImpl implements NoticeService {

	NoticeApply na;

	public NoticeServiceImpl() {
		this.na = new NoticeApply();
	}

	@Override
	public Notice getNoticeById(int id) {
		return na.selectNotice(id);
	}

	@Override
	public PageInfo<Notice> getNoticeByUser(int pageNum, int pageSize, User user) {
		return na.selectNoticeByUser(pageNum, pageSize, user);
	}

	@Override
	public PageInfo<Notice> getAllNotice(int pageNum, int pageSize) {
		return na.listNotice(pageNum, pageSize);
	}

	@Override
	public PageInfo<Notice> getNoticeByState(int pageNum, int pageSize, Integer state) {
		return na.selectNoticeByState(pageNum, pageSize, state);
	}

	@Override
	public boolean deleteNoticeByManage(Notice notice) throws NotFoundException, IncompleteException {

		isNull(notice, "删除的公告对象为空");
		checkId(notice.getId(), "公告对象的id为空");

		return na.deleteNotice(notice) == 1 ? true : false;
	}

	@Override
	public boolean changeNoticeByManage(Notice notice) throws NotFoundException, IncompleteException {

		isNull(notice, "更新的公告对象为空");
		checkId(notice.getId(), "公告对象的id为空");

		if (na.selectNotice(notice.getId()) == null)
			throw new NotFoundException("要更新的公告不存在");

		return na.updateNotice(notice) == 1 ? true : false;
	}

	@Override
	public boolean addNotice(Notice...notices) throws IncompleteException {
		
		isNull("公告为空", notices);
		for(Notice n : notices) {
			if(n.getCreator() == null || n.getTitle() == null || n.getText() == null)
				throw new IncompleteException("公告对象信息不足");
		}
		return na.addNotice(notices) == notices.length;
	}
}
