package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.isNull;

import java.util.Arrays;

import com.cheejee.cms.apply.TypeApply;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Suffix;
import com.cheejee.cms.pojo.Type;
import com.cheejee.cms.service.TypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author CARRY ME HOME 2019年12月21日下午4:50:13
 */
public class TypeServiceImpl implements TypeService {

	private TypeApply ta;

	public TypeServiceImpl() {
		this.ta = new TypeApply();
	}

	@Override
	public Type getTypeById(int id) {
		return ta.selectType(id);
	}

	@Override
	public Type getTypeEditById(int id) {
		Type t = ta.selectType(id);
		if (t != null)
			t.getSuffixs();
		return t;
	}


	@Override
	public PageInfo<Type> getAllType(int pageNum, int pageSize) {
		return ta.listType(pageNum, pageSize);
	}
	
	@Override
	public PageInfo<Type> getTypeByName(int pageNum, int pageSize, String name) {
		isNull(name, "类型名称为空");
		return ta.selectTypeByName(pageNum, pageSize, name);
	}

	@Override
	public boolean addType(Type... types) throws IncompleteException, DataDuplicationException {

		isNull("类型为空", types);
		for (Type t : types) {
			if (t.getName() == null || t.getName().equals(""))
				throw new IncompleteException("类型名称为空");
		}

		return ta.addTypes(Arrays.asList(types)) == types.length;
	}

	@Override
	public boolean deleteType(Type... types) throws IncompleteException {

		for (Type t : types) {
			isNull(t, "类型对象为空");
			checkId(t.getId(), "类型对象id为空");
		}

		return ta.deleteTypes(Arrays.asList(types)) == types.length;
	}

	@Override
	public boolean changeType(Type type) throws IncompleteException, DataDuplicationException, NotFoundException {

		isNull(type, "类型对象为空");
		checkId(type.getId(), "类型id为空");

		if (ta.selectType(type.getId()) == null)
			throw new NotFoundException("类型不存在");
		
		if(type.getName() == null || type.getName().equals(""))
			throw new IncompleteException("类型名称为空");

		try {
			return ta.updateType(type) == 1;

		} catch (OperationsException e) {
			return false;
		}
	}

	@Override
	public boolean addSuffixToType(Type type, Suffix... suffixs)
			throws IncompleteException, DataDuplicationException, NotFoundException {

		isNull(type, "类型为空");
		isNull("后缀为空", suffixs);
		checkId(type.getId(), "类型id为空");

		if (ta.selectType(type.getId()) == null)
			throw new NotFoundException("类型不存在");

		return ta.addTypeSuffixs(type, Arrays.asList(suffixs)) == suffixs.length;
	}

	@Override
	public void deleteSuffixToType(Type type, Suffix... suffixs) throws IncompleteException, NotFoundException {

		isNull(type, "类型为空");
		isNull("后缀为空", suffixs);
		checkId(type.getId(), "类型id为空");

		if (ta.selectType(type.getId()) == null)
			throw new NotFoundException("类型不存在");

		for (Suffix s : suffixs)
			checkId(s.getId(), "后缀id为空");

		ta.deleteTypeSuffixs(type, Arrays.asList(suffixs));
	}

	@Override
	public boolean changeTypeSuffixs(Type type) throws IncompleteException, DataDuplicationException {

		isNull(type, "类型为空");
		checkId(type.getId(), "类型id为空");

		return ta.updateTypeSuffix(type);
	}

}
