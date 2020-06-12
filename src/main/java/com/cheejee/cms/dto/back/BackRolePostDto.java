package com.cheejee.cms.dto.back;

import java.io.Serializable;

import com.cheejee.cms.pojo.Role;
import com.cheejee.cms.pojo.User;

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
@ApiModel(value = "角色DTO-POST-后台", description = "用户添加的角色DTO")
public class BackRolePostDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8970607355037474310L;

	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@ApiModelProperty(value = "角色描述")
	private String describe;

	public Role toRole(User creator) {
		Role r = new Role(roleName, creator);
		r.setDescribe(describe);
		
		return r;
		
	}
	
}
