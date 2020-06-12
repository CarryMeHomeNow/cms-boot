package com.cheejee.cms.dto.fore;

import java.io.Serializable;

import com.cheejee.cms.pojo.Type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "类型DTO-GET-前台", description = "")
public class ForeTypeGetDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7187254425765956467L;


	@ApiModelProperty(value = "类型id")
	private int id;
	
	@ApiModelProperty(value = "类型名称")
	private String name;
	
	@ApiModelProperty(value = "类型描述")
	private String describe;
	
	public static ForeTypeGetDto parse(Type type) {
		if(type == null) {
			return null;
		}
		return new ForeTypeGetDto(type.getId(), type.getName(), type.getDescribe());
	}

}
