package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

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
 * 2020年5月9日下午6:17:06
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "资源DTO-BASIC-后台", description = "")
public class BackResourceBasicDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7272511876171315024L;

	@ApiModelProperty("资源id")
	private int id;
	
	@ApiModelProperty(value = "描述名称", notes = "资源使用资源名称进行权限校验，资源名称不在返回信息中显示")
	private String desName;
	
	public static BackResourceBasicDto parse(Resource resource) {
		if(resource == null) {
			return null;
		}
		
		return new BackResourceBasicDto(resource.getId(), resource.getDesName());
	}
	
	public static List<BackResourceBasicDto> parse(List<Resource> resources){
		if(CollectionUtils.isEmpty(resources)) {
			return null;
		}
		
		List<BackResourceBasicDto> ards = new ArrayList<BackResourceBasicDto>();
		resources.forEach(r -> ards.add(parse(r)));
		
		return ards;
	}

	public static Resource toResource(BackResourceBasicDto resource) {
		if(resource == null) {
			return null;
		}
		
		Resource r = new Resource();
		r.setId(resource.getId());
		r.setDesName(resource.getDesName());
		
		return r;
	}

}
