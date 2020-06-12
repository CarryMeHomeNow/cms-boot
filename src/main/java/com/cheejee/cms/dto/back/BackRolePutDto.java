package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.List;

import com.cheejee.cms.pojo.Role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author CARRY ME HOME
 * 2020年5月8日下午10:02:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "角色DTO-PUT-后台", description = "用于修改的角色dtp")
public class BackRolePutDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6577347499853058848L;

	@ApiModelProperty(value = "角色名称")
	private String name;

	@ApiModelProperty(value = "角色描述")
	private String describe;
	
	@ApiModelProperty(value = "拥有的权限")
	private List<BackPermissionGetDto> permission;
	
	public Role toRole(int id) {

		Role r = new Role(name, describe, null);
		r.setId(id);
		r.setPermissions(BackPermissionGetDto.toPermission(permission, id));
		
		return r;
	}
}
