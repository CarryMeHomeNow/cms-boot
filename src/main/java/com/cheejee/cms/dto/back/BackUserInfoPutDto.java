package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.List;

import com.cheejee.cms.pojo.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME
 * 2020年5月8日下午5:17:21
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户信息DTO-PUT-后台", description = "用于修改的用户信息dto")
public class BackUserInfoPutDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 199632981363011323L;

	@ApiModelProperty(value = "用户状态", allowEmptyValue = true)
	private Integer userStatus;

	@ApiModelProperty(value = "角色列表")
	private List<BackRoleBasicDto> roles;

	/**
	 * 将info对象转换成user对象，填充角色和状态值。
	 * 
	 * @param info
	 * @return
	 */
	public User toUser(int id) {
		
		User user = new User();
		user.setId(id);
		user.setState(userStatus);
		user.setRoles(BackRoleBasicDto.toRole(roles));

		return user;
	}

}
