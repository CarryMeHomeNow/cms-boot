package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

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
@ApiModel(value = "后缀DTO-GET-后台", description = "")
public class BackSuffixGetDto implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3013575067011376948L;

	@ApiModelProperty("后缀id")
	private int id;
	
	@ApiModelProperty("后缀字符串")
	private String suffix;
	
	@ApiModelProperty("描述")
	private String describe;
	
	public static BackSuffixGetDto parse(Suffix suffix) {
		if(suffix == null) {
			return null;
		}
		
		return new BackSuffixGetDto(suffix.getId(), suffix.getSuffix(), suffix.getDescribe());
	}
	
	public static List<BackSuffixGetDto> parse(List<Suffix> suffix){
		if(CollectionUtils.isEmpty(suffix)) {
			return null;
		}
		
		List<BackSuffixGetDto> lsd = new ArrayList<BackSuffixGetDto>();
		suffix.forEach(s -> lsd.add(parse(s)));
		
		return lsd;
	}



}
