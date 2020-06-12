package com.cheejee.cms.apply;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.AttachDAO;
import com.cheejee.cms.dao.SuffixDAO;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.pojo.Type;
import com.cheejee.cms.pojo.User;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class AttachApply {

	SqlSessionFactory ssf;

	public AttachApply() {
		this.ssf = SSF.getSSF();
	}

	public Attach selectAttach(int id) {
		try (SqlSession session = ssf.openSession()) {
			AttachDAO ad = session.getMapper(AttachDAO.class);
			return ad.selectAttach(id);
		}
	}

	public Attach selectAttach(int id, User user) {
		try (SqlSession session = ssf.openSession()) {
			AttachDAO ad = session.getMapper(AttachDAO.class);
			return ad.selectAttachByIdAndUser(id, user);
		}
	}

	public PageInfo<Attach> selectAttachByName(int pagNum, int pageSize, String name) {
		try (SqlSession session = ssf.openSession()) {
			AttachDAO ad = session.getMapper(AttachDAO.class);
			return new PageInfo<Attach>(ad.selectAttachByName(pagNum, pageSize, name));
		}
	}

	public PageInfo<Attach> selectAttachByName(int pagNum, int pageSize, String name, User user) {
		try (SqlSession session = ssf.openSession()) {
			AttachDAO ad = session.getMapper(AttachDAO.class);
			return new PageInfo<Attach>(ad.selectAttachByNameAndUser(pagNum, pageSize, name, user));
		}
	}

	public PageInfo<Attach> selectAttachByUser(int pagNum, int pageSize, User user) {
		try (SqlSession session = ssf.openSession()) {
			AttachDAO ad = session.getMapper(AttachDAO.class);
			return new PageInfo<Attach>(ad.selectAttachByUser(pagNum, pageSize, user));
		}
	}

	public PageInfo<Attach> selectAttachByTypeManage(int pagNum, int pageSize, Type type) {
		try (SqlSession session = ssf.openSession()) {
			AttachDAO ad = session.getMapper(AttachDAO.class);
			return new PageInfo<Attach>(ad.selectAttachByType(pagNum, pageSize, type));
		}
	}

	public PageInfo<Attach> selectAttachByType(int pagNum, int pageSize, Type type, User user) {
		try (SqlSession session = ssf.openSession()) {
			AttachDAO ad = session.getMapper(AttachDAO.class);
			return new PageInfo<Attach>(ad.selectAttachByTypeAndUser(pagNum, pageSize, type, user));
		}
	}

	public PageInfo<Attach> listAttach(int pagNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			AttachDAO ad = session.getMapper(AttachDAO.class);
			return new PageInfo<Attach>(ad.listAttach(pagNum, pageSize));
		}
	}

	public int addAttachs(List<Attach> attachs) throws DataDuplicationException {

		if (attachs == null || attachs.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession(true)) {

			AttachDAO ad = session.getMapper(AttachDAO.class);
			Set<String> ss = new HashSet<String>();

			for (Attach a : attachs) {
				if (ad.selectAttachCountByURL(a.getUrl()) != 0)
					throw new DataDuplicationException("存在相同URL的附件：" + a.getUrl());

				ss.add(a.getUrl());
			}

			if (ss.size() != attachs.size())
				throw new DataDuplicationException("存在相同URL的附件");

			return ad.addAttachs(attachs);
		}
	}

	public int addAttach(Attach attach) throws DataDuplicationException {
		List<Attach> la = new ArrayList<Attach>();
		la.add(attach);
		return addAttachs(la);
	}

	public int deleteAttachs(List<Attach> attachs) {

		if (attachs == null || attachs.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession()) {
			AttachDAO ad = session.getMapper(AttachDAO.class);
			int a = ad.deleteAttachs(attachs);
			ad.deleteAttachsRelate(attachs);

			session.commit();
			return a;
		}
	}

	public int deleteAttach(Attach attach) {
		List<Attach> la = new ArrayList<Attach>();
		la.add(attach);
		return deleteAttachs(la);
	}

	public int updateAttach(Attach attach) throws DataDuplicationException {

		if (attach == null)
			return 0;

		try (SqlSession session = ssf.openSession()) {

			AttachDAO ad = session.getMapper(AttachDAO.class);

			if (ad.selectAttachCountByURLNoSelf(attach) != 0)
				throw new DataDuplicationException("存在相同URL的附件：" + attach.getUrl());

			int a = ad.updateAttach(attach);
			session.commit();
			return a;
		}
	}

	/**
	 * 检测是否还有内容使用了这个附件，如果没有则返回true
	 * 
	 * @param attachs
	 * @return
	 */
	public boolean checkAttachNotUse(Attach... attachs) {

		try (SqlSession session = ssf.openSession()) {
			AttachDAO ad = session.getMapper(AttachDAO.class);

			if (ad.selectAttachCountByContent(attachs) == 0)
				return true;

			return false;
		}
	}

	/**
	 * 检测附件是否可用，使用附件url取得后缀进行查询。
	 * 
	 * @param attachs
	 * @return 可以使用返回true
	 */
	public boolean checkAttachCanUse(Attach... attachs) {

		try (SqlSession session = ssf.openSession()) {
			SuffixDAO sd = session.getMapper(SuffixDAO.class);

			for (Attach a : attachs) {
				String suff = a.getUrl().split("\\.")[1];

				if (sd.selectCountBySuffix(suff) == 0)
					return false;
			}
			return true;
		}

	}

}
