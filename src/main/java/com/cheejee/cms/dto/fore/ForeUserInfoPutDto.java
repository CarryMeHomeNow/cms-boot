package com.cheejee.cms.dto.fore;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.cheejee.cms.pojo.PersonalInfo;
import com.cheejee.cms.pojo.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME 2020年4月5日下午3:06:06
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户信息DTO-PUT-前台", description = "用于修改的用户信息dto")
public class ForeUserInfoPutDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7733503497222982799L;

	@ApiModelProperty(value = "昵称")
	private String pName;

	@ApiModelProperty(value = "个性签名")
	private String signatrue;

	@ApiModelProperty(value = "个人简介")
	private String profile;

	@ApiModelProperty(value = "从事行业")
	private String business;

	@ApiModelProperty(value = "手机号码")
	@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误")
	private String tel;

	@ApiModelProperty(value = "邮箱")
	@Email(message = "邮箱格式错误")
	private String email;


	public User toUser(int id) {
		
		PersonalInfo info = new PersonalInfo(pName, signatrue, profile, business, tel, email, null, null, id);
		User user = new User();
		user.setPersonalInfo(info);
		user.setId(id);
		
		return user;
	}
}
