package com.cheejee.cms.dto.back;

import java.io.Serializable;

import com.cheejee.cms.pojo.Type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * 
 * @author CARRY ME HOME
 * 2020年5月8日下午10:37:47
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "类型DTO-POST-后台", description = "用于添加的类型dto")
public class BackTypePostDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4884079537326631332L;

	@ApiModelProperty(value = "类型名称")
	private String name;

	@ApiModelProperty(value = "类型描述")
	private String describe;



	public Type toType() {
		Type t = new Type(name, describe);
		
		return t;
	}
}
