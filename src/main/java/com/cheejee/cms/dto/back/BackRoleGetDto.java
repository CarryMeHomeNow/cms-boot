package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cheejee.cms.pojo.Role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author CARRY ME HOME 2020年4月14日下午2:55:17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "角色DTO-后台")
public class BackRoleGetDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2559365282108024110L;

	@ApiModelProperty(value = "角色id")
	private Integer roleId;

	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@ApiModelProperty(value = "角色描述")
	private String describe;

	@ApiModelProperty(value = "角色拥有的权限")
	private List<BackPermissionGetDto> permission;

	public static BackRoleGetDto parse(Role role) {
		if(role == null) {
			return null;
		}
		
		return BackRoleGetDto.builder()
				.roleId(role.getId())
				.roleName(role.getName())
				.describe(role.getDescribe())
				.permission(BackPermissionGetDto.parse(role.getPermissions()))
				.build();
	}

	public static List<BackRoleGetDto> parse(List<Role> roles) {
		if(CollectionUtils.isEmpty(roles)) {
			return null;
		}
		
		List<BackRoleGetDto> rds = new ArrayList<BackRoleGetDto>();
		roles.forEach(r -> rds.add(parse(r)));

		return rds;
	}
	


	
}
