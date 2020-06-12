package com.cheejee.cms.apply;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.StringUtils;

import com.cheejee.cms.dao.SuffixDAO;
import com.cheejee.cms.dao.TypeDAO;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Suffix;
import com.cheejee.cms.pojo.Type;
import com.cheejee.cms.tools.SSF;
import com.github.pagehelper.PageInfo;

public class TypeApply {

	SqlSessionFactory ssf;

	public TypeApply() {
		this.ssf = SSF.getSSF();
	}

	public Type selectType(int id) {
		try (SqlSession session = ssf.openSession()) {
			TypeDAO td = session.getMapper(TypeDAO.class);
			return td.selectType(id);
		}
	}

	public Type selectTypeByNameExact(String name) {
		try (SqlSession session = ssf.openSession()) {
			TypeDAO td = session.getMapper(TypeDAO.class);
			return td.selectTypeByNameExact(name);
		}
	}

	public PageInfo<Type> selectTypeByName(int pageNum, int pageSize, String name) {
		try (SqlSession session = ssf.openSession()) {
			TypeDAO td = session.getMapper(TypeDAO.class);
			return new PageInfo<Type>(td.selectTypeByName(pageNum, pageSize, name));
		}
	}

	public PageInfo<Type> listType(int pagNum, int pageSize) {
		try (SqlSession session = ssf.openSession()) {
			TypeDAO td = session.getMapper(TypeDAO.class);
			return new PageInfo<Type>(td.listType(pagNum, pageSize));
		}
	}

	public int addTypes(List<Type> types) throws DataDuplicationException {

		if (types == null || types.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession(true)) {

			TypeDAO td = session.getMapper(TypeDAO.class);
			Set<String> set = new HashSet<String>();

			for (Type t : types) {
				if (td.selectTypeNameCount(t) != 0)
					throw new DataDuplicationException("类型名称已存在：" + t.getName());

				set.add(t.getName());
			}

			if (set.size() != types.size())
				throw new DataDuplicationException("存在相同的类型名称");

			return td.addTypes(types);
		}
	}

	public int addType(Type type) throws DataDuplicationException {
		List<Type> lt = new ArrayList<Type>();
		lt.add(type);
		return addTypes(lt);
	}

	/**
	 * 这个方法存在bug，不要使用
	 * @param type
	 * @param suffixs
	 * @return
	 * @throws DataDuplicationException
	 */
	public int addTypeSuffixs(Type type, List<Suffix> suffixs) throws DataDuplicationException {

		if (suffixs == null || suffixs.isEmpty() || type == null)
			return 0;

		try (SqlSession session = ssf.openSession()) {

			TypeDAO td = session.getMapper(TypeDAO.class);
			SuffixDAO sd = session.getMapper(SuffixDAO.class);
			List<Suffix> ls = new ArrayList<Suffix>();

			for (Suffix s : suffixs) {
				if (s.getId() != 0) {
					if (td.selectTypeCountBySuffix(type, s) != 0)
						throw new DataDuplicationException("添加的后缀已存在：" + s);
				}
				if (sd.selectSuffixBySuff(s.getSuffix()) == null)
					ls.add(s);

			}

			if (!ls.isEmpty())
				sd.addSuffixs(ls);

			int a = td.addTypeSuffixs(type, suffixs);
			session.commit();

			return a;
		}
	}

	public int deleteTypes(List<Type> types) {

		if (types == null || types.isEmpty())
			return 0;

		try (SqlSession session = ssf.openSession()) {
			TypeDAO td = session.getMapper(TypeDAO.class);

			int i = td.deleteTypes(types);
			for (Type t : types)
				td.deleteTypeSuffixsAll(t);

			session.commit();
			return i;
		}
	}

	public int deleteType(Type type) {
		List<Type> lt = new ArrayList<Type>();
		lt.add(type);
		return deleteTypes(lt);
	}

	public int deleteTypeSuffixs(Type type, List<Suffix> suffixs) {

		if (suffixs == null || suffixs.isEmpty() || type == null)
			return 0;

		try (SqlSession session = ssf.openSession(true)) {
			TypeDAO td = session.getMapper(TypeDAO.class);
			return td.deleteTypeSuffixs(type, suffixs);
		}
	}

	public int updateType(Type type) throws DataDuplicationException, OperationsException {

		if (type == null)
			return 0;

		try (SqlSession session = ssf.openSession()) {
			TypeDAO td = session.getMapper(TypeDAO.class);

			if (td.selectTypeNameCountExSelf(type) != 0)
				throw new DataDuplicationException("类型名称已存在：" + type.getName());

			int a = td.updateType(type);

			if (a != 1)
				return a;

			updateTypeSuffix(type, session);

			session.commit();
			return a;
		}
	}

	public boolean updateTypeSuffix(Type type) {

		try (SqlSession session = ssf.openSession()) {
			updateTypeSuffix(type, session);
			session.commit();
			return true;

		} catch (OperationsException e) {
			return false;
		}

	}

	private void checkAddSuffix(SuffixDAO sd, List<Suffix> suffixs) {
		if (suffixs == null || suffixs.isEmpty()) {
			return;
		}

		List<Suffix> ls = new ArrayList<Suffix>();
		suffixs.forEach(s -> {
			fetchSuffixNotExit(ls, s, sd);
		});

		if(!ls.isEmpty()) {
			sd.addSuffixs(ls);
		}
	}

	private void fetchSuffixNotExit(List<Suffix> notExit, Suffix suffix, SuffixDAO sd) {
		if (suffix == null) {
			return;
		}
		
		int id = suffix.getId();
		String s = suffix.getSuffix().trim();
		
		if(id == 0 && StringUtils.isEmpty(s)) {
			return;
		}

		
		if(id != 0 && sd.selectSuffix(suffix.getId()) != null) {
			return;
		}

		if (sd.selectSuffixBySuff(s) == null) {
			notExit.add(suffix);
		}
	}

	private void updateTypeSuffix(Type type, SqlSession session) throws OperationsException {

		List<Suffix> ls = type.getSuffixs();
		TypeDAO td = session.getMapper(TypeDAO.class);
		SuffixDAO sd = session.getMapper(SuffixDAO.class);
		td.deleteTypeSuffixsAll(type);

		int a = 0;
		int b = 0;

		if (ls != null && !ls.isEmpty()) {
			checkAddSuffix(sd, ls);
			a = ls.size();
			b = td.addTypeSuffixs(type, ls);
		}

		if (a != b)
			throw new OperationsException("更新类型的后缀失败");
	}
}
