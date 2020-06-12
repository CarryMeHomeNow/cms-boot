package com.cheejee.cms.service.impl;

import static com.cheejee.cms.tools.CheckTool.checkId;
import static com.cheejee.cms.tools.CheckTool.isNull;

import java.util.Arrays;

import com.cheejee.cms.apply.SuffixApply;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Suffix;
import com.cheejee.cms.service.SuffixService;
import com.cheejee.cms.tools.CheckTool;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author CARRY ME HOME
 * 2019年12月21日下午4:50:07
 */
public class SuffixServiceImpl implements SuffixService{
	
	private SuffixApply sa;
	
	public SuffixServiceImpl() {
		this.sa = new SuffixApply();
	}

	@Override
	public Suffix getSuffixById(int id) {
		return sa.selectSuffix(id);
	}

	@Override
	public Suffix getSuffixBySuff(String suff) {
		
		isNull(suff, "后缀字符串为空");
		suff = suff.toLowerCase();
		
		return sa.selectSuffixBySuff(suff);
	}

	@Override
	public PageInfo<Suffix> getSuffixAll(int pageNum, int pageSize) {
		
		return sa.listSuffix(pageNum, pageSize);
	}

	@Override
	public PageInfo<Suffix> getSuffixNoType(int pageNum, int pageSize) {
		return sa.selectSuffixNoType(pageNum, pageSize);
	}

	@Override
	public boolean addSuffix(Suffix... suffix) throws DataDuplicationException, IncompleteException {
		
		isNull("后缀为空", suffix);
		CheckTool.checkSuffixIsReplicate(Arrays.asList(suffix));
		
		for(Suffix s : suffix) {
			if(s.getSuffix() == null || s.getSuffix().isEmpty())
				throw new IncompleteException("后缀字符串为空");
		}
		
		return sa.addSuffixs(Arrays.asList(suffix)) == suffix.length;
	}

	@Override
	public void deleteSuffix(Suffix... suffixs) throws OperationsException, IncompleteException {
		
		isNull("后缀为空", suffixs);
		
		for(Suffix s : suffixs)
			checkId(s.getId(), "后缀id为空");
		
		if(sa.checkSuffixNoUse(suffixs))
			throw new OperationsException("还有此后缀的附件存在，不能删除");
		
		sa.deleteSuffixs(Arrays.asList(suffixs));
	}

	@Override
	public boolean changeSuffixDescribe(Suffix suffix) throws IncompleteException, NotFoundException {
		
		isNull(suffix, "后缀为空");
		checkId(suffix.getId(), "后缀id为空");
		
		if(sa.selectSuffix(suffix.getId()) == null) {
			throw new NotFoundException("后缀不存在");
		}
		
		Suffix s = new Suffix();
		s.setId(suffix.getId());
		s.setDescribe(suffix.getDescribe());
		
		return sa.updateSuffix(s) == 1;
	}

}
