package com.cheejee.cms.dto.fore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.cheejee.cms.pojo.PersonalInfo;
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
 * @author CARRY ME HOME 2020年4月5日下午3:06:06
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户信息DTO-GET-前台", description = "用户个人信息dto")
public class ForeUserInfoGetDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8255894590466970108L;

	@ApiModelProperty(value = "用户id", required = true)
	@Min(value = 1, message = "用户id非法（小于1）")
	@NotNull(message = "用户id不能为空")
	private Integer userId;

	@ApiModelProperty(value = "用户名")
	private String userName;

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

	@ApiModelProperty(value = "注册时间")
	private Date createTime;
	
	@ApiModelProperty("头像url")
	private String avatarUrl;

	/**
	 * 将info对象转换成user对象
	 * 
	 * @param info
	 * @return
	 */
	public static User toUser(ForeUserInfoGetDto info) {
		if (info == null) {
			return null;
		}

		User user = new User();
		user.setId(info.getUserId());
		user.setPersonalInfo(builderInfo(info, info.getUserId()));

		return user;
	}

	public static ForeUserInfoGetDto parse(User user) {
		if (user == null) {
			return null;
		}
		PersonalInfo p = user.getPersonalInfo();
		ForeUserInfoGetDto info = new ForeUserInfoGetDto();
		info.setUserId(user.getId());
		info.setUserName(user.getName());
		info.setBusiness(p.getBusiness());
		info.setEmail(p.getEmail());
		info.setPName(p.getpName());
		info.setProfile(p.getProfile());
		info.setSignatrue(p.getSignatrue());
		info.setTel(p.getTel());
		info.setCreateTime(p.getCreateTime());
		info.setAvatarUrl(p.getBigAvatarUrl());

		return info;
	}

	public static List<ForeUserInfoGetDto> parse(List<User> users, UserService userService) {
		List<ForeUserInfoGetDto> infos = new ArrayList<ForeUserInfoGetDto>();
		users.forEach(u -> infos.add(parse(u)));

		return infos;
	}

	private static PersonalInfo builderInfo(ForeUserInfoGetDto info, int userId) {
		return new PersonalInfo(info.getPName(), info.getSignatrue(), info.getProfile(), info.getBusiness(),
				info.getTel(), info.getEmail(), null, null, userId);
	}

}
