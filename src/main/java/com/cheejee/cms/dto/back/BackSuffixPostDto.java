package com.cheejee.cms.dto.back;

import java.io.Serializable;

import com.cheejee.cms.pojo.Suffix;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME
 * 2020年5月2日上午10:47:24
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "后缀DTO-POST-后台", description = "用于添加的后缀DTO")
public class BackSuffixPostDto implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1616449690123542724L;

	@ApiModelProperty("后缀字符串")
	private String suffix;
	
	@ApiModelProperty("描述")
	private String describe;
	
	
	
	public Suffix toSuffix() {
		
		return new Suffix(suffix, describe);
	}


}
