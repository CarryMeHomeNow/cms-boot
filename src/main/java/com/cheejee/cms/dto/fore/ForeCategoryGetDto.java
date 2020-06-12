package com.cheejee.cms.dto.fore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cheejee.cms.pojo.Category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME
 * 2020年4月17日下午10:17:59
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "分类DTO-GET-前台", description = "")
public class ForeCategoryGetDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2596846893087892625L;

	@ApiModelProperty("分类id")
	private int id;
	
	@ApiModelProperty("分类名称")
	private String name;
	
	public static ForeCategoryGetDto parse(Category category) {
		if(category == null) {
			return null;
		}
		return new ForeCategoryGetDto(category.getId(), category.getName());
	}
	
	public static List<ForeCategoryGetDto> parse(List<Category> categorys){
		List<ForeCategoryGetDto> lc = new ArrayList<ForeCategoryGetDto>();
		categorys.forEach(c -> lc.add(parse(c)));
		
		return lc;
	}
}
