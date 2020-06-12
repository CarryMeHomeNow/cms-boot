package com.cheejee.cms.apply;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cheejee.cms.dao.SuffixDAO;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.pojo.Suffix;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class SuffixApply {

	SqlSessionFactory ssf;

	public SuffixApply() {
		this.ssf = SSF.getSSF();
	}

	public Suffix selectSuffix(int id) {
		try (SqlSession session = ssf.openSession()) {
			SuffixDAO sd = session.getMapper(SuffixDAO.class);
			return sd.selectSuffix(id);
		}
	}

	public Suffix selectSuffixBySuff(String suff) {
		try (SqlSession session = ssf.openSession()) {
			SuffixDAO sd = session.getMapper(SuffixDAO.class);
			return sd.selectSuffixBySuff(suff);
		}
	}

	public PageInfo<Suffix> selectSuffixNoType(int pageNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			SuffixDAO sd = session.getMapper(SuffixDAO.class);
			return new PageInfo<Suffix>(sd.selectSuffixNoType(pageNum, pageSize));
		}
	}

	public PageInfo<Suffix> listSuffix(int pagNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			SuffixDAO sd = session.getMapper(SuffixDAO.class);
			return new PageInfo<Suffix>(sd.listSuffix(pagNum, pageSize));
		}
	}

	public int addSuffixs(List<Suffix> suffixs) throws DataDuplicationException {

		if (suffixs == null || suffixs.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession(true)) {
			SuffixDAO sd = session.getMapper(SuffixDAO.class);

			for (Suffix s : suffixs) {
				
				String str = s.getSuffix().trim();
				if(str == null || str.equals("")) {
					throw new NullPointerException("添加的后缀为空");
				}
				
				s.setSuffix(str.toLowerCase());
				if (sd.selectCountBySuffix(s.getSuffix()) != 0)
					throw new DataDuplicationException("添加的后缀已存在");
			}

			return sd.addSuffixs(suffixs);
		}
	}

	public int addSuffix(Suffix suffix) throws DataDuplicationException {
		List<Suffix> suffixs = new ArrayList<Suffix>();
		suffixs.add(suffix);
		return addSuffixs(suffixs);
	}

	public int deleteSuffixs(List<Suffix> suffixs) {

		if (suffixs == null || suffixs.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession(true)) {
			SuffixDAO sd = session.getMapper(SuffixDAO.class);
			return sd.deleteSuffixs(suffixs);
		}
	}

	public int deleteSuffix(Suffix suffix) {
		List<Suffix> suffixs = new ArrayList<Suffix>();
		suffixs.add(suffix);
		return deleteSuffixs(suffixs);
	}

	public int updateSuffix(Suffix suffix) {

		if (suffix == null)
			return 0;

		try (SqlSession session = ssf.openSession()) {
			SuffixDAO sd = session.getMapper(SuffixDAO.class);
			int a = sd.updateSuffix(suffix);

			session.commit();
			return a;
		}
	}

	/**
	 * 判断是否有附件使用了这个后缀
	 * 
	 * @param suffixs
	 * @return 有附件使用返回true
	 */
	public boolean checkSuffixNoUse(Suffix... suffixs) {

		try (SqlSession session = ssf.openSession()) {
			SuffixDAO sd = session.getMapper(SuffixDAO.class);

			for (Suffix s : suffixs) {
				Suffix sf = sd.selectSuffix(s.getId());
				if(!sf.getAttachs().isEmpty())
					return true;
			}
			return false;
		}
	}

}
