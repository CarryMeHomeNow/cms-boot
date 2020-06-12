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
public class BackRoleBasicDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5698237579261951517L;

	@ApiModelProperty(value = "角色id")
	private Integer roleId;

	@ApiModelProperty(value = "角色名称")
	private String roleName;


	public static BackRoleBasicDto parse(Role role) {
		return BackRoleBasicDto.builder()
				.roleId(role.getId())
				.roleName(role.getName())
				.build();
	}

	public static List<BackRoleBasicDto> parse(List<Role> roles) {
		List<BackRoleBasicDto> rds = new ArrayList<BackRoleBasicDto>();
		roles.forEach(r -> rds.add(parse(r)));

		return rds;
	}
	
	public static Role toRole(BackRoleBasicDto role) {
		if(role == null) {
			return null;
		}
		
		Role r = new Role();
		r.setId(role.getRoleId());
		r.setName(role.getRoleName());
		
		return r;
	}

	public static List<Role> toRole(List<BackRoleBasicDto> roles) {
		if(CollectionUtils.isEmpty(roles)) {
			return null;
		}
		
		List<Role> lr = new ArrayList<Role>();
		roles.forEach(r -> lr.add(toRole(r)));
		
		return lr;
	}
	
}
