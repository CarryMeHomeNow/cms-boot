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
@ApiModel(value = "后缀DTO-BASIC-后台", description = "")
public class BackSuffixBasicDto implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4671304364475713003L;

	@ApiModelProperty("后缀id")
	private int id;
	
	@ApiModelProperty("后缀字符串")
	private String suffix;
	
	
	public static BackSuffixBasicDto parse(Suffix suffix) {
		if(suffix == null) {
			return null;
		}
		
		return new BackSuffixBasicDto(suffix.getId(), suffix.getSuffix());
	}
	
	public static List<BackSuffixBasicDto> parse(List<Suffix> suffix){
		if(CollectionUtils.isEmpty(suffix)) {
			return null;
		}
		
		List<BackSuffixBasicDto> lsd = new ArrayList<BackSuffixBasicDto>();
		suffix.forEach(s -> lsd.add(parse(s)));
		
		return lsd;
	}

	public static Suffix toSuffix(BackSuffixBasicDto suffix) {
		if(suffix == null) {
			return null;
		}
		
		Suffix s = new Suffix();
		s.setId(suffix.getId());
		s.setSuffix(suffix.getSuffix());
		
		return s;
	}
	
	public static List<Suffix> toSuffix(List<BackSuffixBasicDto> suffix){
		if(CollectionUtils.isEmpty(suffix)) {
			return null;
		}
		
		List<Suffix> ls = new ArrayList<Suffix>();
		suffix.forEach(s -> ls.add(toSuffix(s)));
		
		return ls;
	}


}
