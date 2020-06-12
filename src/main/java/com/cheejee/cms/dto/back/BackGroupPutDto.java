package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.List;

import com.cheejee.cms.dto.fore.ForeUserGetDto;
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
@ApiModel(value = "分组DTO-PUT-后台", description = "用于修改的分组DTO")
public class BackGroupPutDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2029535011415327861L;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("描述")
	private String describe;

	@ApiModelProperty("父组")
	private BackGroupBasicDto parent;

	@ApiModelProperty("子组")
	private List<BackGroupBasicDto> sons;

	@ApiModelProperty("组员")
	private List<ForeUserGetDto> users;

	@ApiModelProperty("拥有的角色")
	private List<BackRoleBasicDto> roles;

	public Group toGroup(int id) {
		Group g = new Group();
		g.setId(id);
		g.setName(name);
		g.setDescribe(describe);
		g.setParent(parent.toGroup());
		g.setUsers(ForeUserGetDto.toUser(users));
		g.setRoles(BackRoleBasicDto.toRole(roles));

		return g;
	}

}
