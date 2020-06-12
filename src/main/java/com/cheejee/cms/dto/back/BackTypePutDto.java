package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.List;

import com.cheejee.cms.pojo.Type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME 2020年5月2日下午4:47:56
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "类型DTO-Simple-后台", description = "用于修改的类型dto")
public class BackTypePutDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2101237506826670666L;

	@ApiModelProperty(value = "类型名称")
	private String name;

	@ApiModelProperty(value = "类型描述")
	private String describe;

	@ApiModelProperty(value = "包含的后缀")
	private List<BackSuffixBasicDto> suffix;


	public Type toType(int id) {
		
		Type t = new Type(name, describe);
		t.setSuffixs(BackSuffixBasicDto.toSuffix(suffix));
		t.setId(id);
		
		return t;
	}
}
