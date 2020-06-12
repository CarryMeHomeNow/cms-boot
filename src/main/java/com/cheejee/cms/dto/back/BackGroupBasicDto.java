package com.cheejee.cms.dto.back;

import java.io.Serializable;

import com.cheejee.cms.pojo.Group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME
 * 2020年5月9日下午4:10:45
 */
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "分组DTO-BASIC-后台", description = "只包含分组的基本信息")
public class BackGroupBasicDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7575392305773655105L;

	@ApiModelProperty("分组id")
	private int id;
	
	@ApiModelProperty("分组名称")
	private String name;
	
	
	public Group toGroup() {
		Group g = new Group();
		g.setId(id);
		g.setName(name);
		
		return g;
	}
}
