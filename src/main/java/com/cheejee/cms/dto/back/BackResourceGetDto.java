package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cheejee.cms.pojo.Resource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME
 * 2020年4月27日下午10:21:55
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "资源DTO-GET-后台", description = "")
public class BackResourceGetDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3658662626541062288L;

	@ApiModelProperty("资源id")
	private int id;
	
	@ApiModelProperty(value = "描述名称", notes = "资源使用资源名称进行权限校验，资源名称不在返回信息中显示")
	private String desName;
	
	@ApiModelProperty("描述")
	private String describe;
	
	public static BackResourceGetDto parse(Resource resource) {
		return new BackResourceGetDto(resource.getId(), resource.getDesName(), resource.getDescribe());
	}
	
	public static List<BackResourceGetDto> parse(List<Resource> resources){
		List<BackResourceGetDto> ards = new ArrayList<BackResourceGetDto>();
		resources.forEach(r -> ards.add(parse(r)));
		
		return ards;
	}

}
