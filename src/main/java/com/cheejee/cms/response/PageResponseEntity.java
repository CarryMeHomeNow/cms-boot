package com.cheejee.cms.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author CARRY ME HOME
 * 2020年4月13日下午8:46:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分页返回实体")
public class PageResponseEntity<T> extends BasicResponseEntity {

	private static final long serialVersionUID = -8659474030578253376L;

	@ApiModelProperty(value = "当前页码")
	private int pageNum;
	
	@ApiModelProperty(value = "当前页数据条数")
	private int size;
	
	@ApiModelProperty(value = "总记录数")
	private long total;
	
	@ApiModelProperty(value = "总页数")
	private int pages;
	
	@ApiModelProperty(value = "结果集")
	private List<T> list;
	
	@Builder
	private PageResponseEntity(int code, String message, int pageNum, int size, long total, int pages, List<T> list) {
		super(code, message);
		this.pageNum = pageNum;
		this.size = size;
		this.total = total;
		this.pages = pages;
		this.list = list;
	}
	
	
}
