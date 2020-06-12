package com.cheejee.cms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cheejee.cms.dto.fore.ForeUserGetDto;
import com.cheejee.cms.pojo.Attach;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 因为service会对所属用户进行校验，所以添加了用户信息。
 * @author CARRY ME HOME
 * 2020年4月15日下午11:14:20
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "附件DTO-GET-通用", description = "前后台通用的附件dto")
public class AttachGetDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3100648467735882365L;

	@ApiModelProperty("附件id")
	private int id;
	
	@ApiModelProperty("附件名称")
	private String name;
	
	@ApiModelProperty("附件url")
	private String url;
	
	@ApiModelProperty("附件所属用户")
	private ForeUserGetDto user;
	
	public static AttachGetDto parse(Attach attach) {
		if(attach == null) {
			return null;
		}
		return new AttachGetDto(attach.getId(), attach.getName(), attach.getUrl(), ForeUserGetDto.parse(attach.getUser()));
	}
	
	public static List<AttachGetDto> parse(List<Attach> attachs){
		if(CollectionUtils.isEmpty(attachs)) {
			return null;
		}
		
		List<AttachGetDto> ld = new ArrayList<AttachGetDto>();
		attachs.forEach(a -> ld.add(parse(a)));
		
		return ld;
	}

}
