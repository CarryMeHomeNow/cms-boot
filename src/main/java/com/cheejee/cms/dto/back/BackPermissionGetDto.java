package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cheejee.cms.pojo.Permission;
import com.cheejee.cms.tools.BuliderTool;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author CARRY ME HOME 2020年4月30日下午4:09:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "权限DTO-后台", description = "与角色dto绑定使用，不单独使用")
public class BackPermissionGetDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8814104189822136853L;

	@ApiModelProperty(value = "权限值", notes = "0为关，1为开")
	private byte privilege;

	@ApiModelProperty(value = "资源")
	private BackResourceBasicDto resource;

	public static BackPermissionGetDto parse(Permission permission) {
		if (permission == null) {
			return null;
		}

		return BackPermissionGetDto.builder()
				.privilege(permission.getPrivilege())
				.resource(BackResourceBasicDto.parse(permission.getResource()))
				.build();
	}

	public static List<BackPermissionGetDto> parse(List<Permission> permissions) {
		if (CollectionUtils.isEmpty(permissions)) {
			return null;
		}

		List<BackPermissionGetDto> apd = new ArrayList<BackPermissionGetDto>();
		permissions.forEach(p -> apd.add(parse(p)));

		return apd;
	}
	
	public static Permission toPermission(BackPermissionGetDto permissionDto, int roleId) {
		if(permissionDto == null) {
			return null;
		}
		
		Permission permission = new Permission();
		permission.setRole(BuliderTool.buildRole(roleId));
		permission.setPrivilege(permissionDto.getPrivilege());
		permission.setResource(BackResourceBasicDto.toResource(permissionDto.getResource()));
		
		return permission;
	}
	
	public static List<Permission> toPermission(List<BackPermissionGetDto> permissionDto, int roleId){
		if(CollectionUtils.isEmpty(permissionDto)) {
			return null;
		}
		
		List<Permission> permission = new ArrayList<Permission>();
		permissionDto.forEach(p -> permission.add(toPermission(p, roleId)));
		
		return permission;
	}
}
