package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cheejee.cms.pojo.User;
import com.cheejee.cms.service.UserService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME 2020年4月13日下午2:08:08
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户信息-后台", description = "")
public class BackUserInfoGetDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1587624919761271523L;

	@ApiModelProperty(value = "用户ID")
	private Integer id;

	@ApiModelProperty(value = "用户名")
	private String name;

	@ApiModelProperty(value = "用户状态")
	private Integer userStatus;

	@ApiModelProperty(value = "角色列表")
	private List<BackRoleBasicDto> roles;

	/**
	 * 从user对象中读取数据，构建出info
	 * 
	 * @param user
	 * @param userService
	 * @return
	 */
	public static BackUserInfoGetDto parse(User user) {
		if (user == null) {
			return null;
		}

		return new BackUserInfoGetDto(user.getId(), user.getName(), user.getState(),
				BackRoleBasicDto.parse(user.getRoles()));
	}

	public static List<BackUserInfoGetDto> parse(List<User> users, UserService userService) {
		List<BackUserInfoGetDto> info = new ArrayList<BackUserInfoGetDto>();
		users.forEach(u -> info.add(parse(u)));

		return info;
	}


}
