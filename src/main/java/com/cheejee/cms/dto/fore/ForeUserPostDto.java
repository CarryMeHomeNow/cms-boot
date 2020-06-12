/**
 * 
 */
package com.cheejee.cms.dto.fore;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.cheejee.cms.pojo.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author CARRY ME HOME 2020年3月6日下午11:03:54
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户DTO-POST-前台", description = "在用户登录或注册时使用，存储用户输入的用户名和密码。")
public class ForeUserPostDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3884110504276882542L;

	@ApiModelProperty(value = "用户名")
	@NotBlank(message = "用户名不能为空")
	@Size(min = 2, max = 6, message = "用户名长度必须在2到6位")
	private String name;

	@ApiModelProperty("用户密码")
	@NotEmpty(message = "密码不能为空")
	@Size(max = 32, min = 4, message = "密码长度必须在4到32位")
	private String password;
	
	
	public User toUser() throws NoSuchAlgorithmException {
		
		return new User(name, password);
	}
}
