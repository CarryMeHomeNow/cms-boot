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
 * @author CARRY ME HOME 2020年4月18日下午3:49:52
 */
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "分组DTO-POST-后台", description = "用于添加的分组DTO")
public class BackGroupPostDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2116689657552285872L;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("描述")
	private String describe;

	@ApiModelProperty("父组")
	private BackGroupBasicDto parent;

	public Group toGroup() {
		Group g = new Group();
		g.setName(name);
		g.setDescribe(describe);
		g.setParent(parent.toGroup());

		return g;
	}

}
