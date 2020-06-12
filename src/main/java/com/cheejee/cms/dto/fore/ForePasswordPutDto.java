package com.cheejee.cms.dto.fore;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 适用于修改密码的dto
 * 
 * @author CARRY ME HOME 2020年2月4日下午9:59:18
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "密码DTO-PUT-前台", description = "用于修改密码")
public class ForePasswordPutDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3348313276713278253L;

	@ApiModelProperty("旧密码")
	@NotBlank(message = "旧密码不能为空")
	@Size(max = 32, min = 4, message = "密码长度必须在4到32位")
	private String oldPass;
	
	@ApiModelProperty("新密码")
	@NotBlank(message = "新密码不能为空")
	@Size(max = 32, min = 4, message = "密码长度必须在4到32位")
	private String newPass;

	
	

}
