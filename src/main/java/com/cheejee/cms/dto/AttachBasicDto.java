package com.cheejee.cms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cheejee.cms.pojo.Attach;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME
 * 2020年5月9日下午5:06:38
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "附件DTO-BASIC-通用", description = "只包含附件id和附件名称")
public class AttachBasicDto implements Serializable {
	
	private static final long serialVersionUID = 1421380245116811215L;

	@ApiModelProperty("附件id")
	private int id;
	
	@ApiModelProperty("附件名称")
	private String name;
	
	public static AttachBasicDto parse(Attach attach) {
		if(attach == null) {
			return null;
		}
		return new AttachBasicDto(attach.getId(), attach.getName());
	}
	
	public static List<AttachBasicDto> parse(List<Attach> attachs){
		if(CollectionUtils.isEmpty(attachs)) {
			return null;
		}
		
		List<AttachBasicDto> ld = new ArrayList<AttachBasicDto>();
		attachs.forEach(a -> ld.add(parse(a)));
		
		return ld;
	}
	
	public static Attach toAttach(AttachBasicDto attach) {
		if(attach == null) {
			return null;
		}
		
		Attach a = new Attach();
		a.setId(attach.getId());
		a.setName(attach.getName());
		
		return a;
	}
	
	public static List<Attach> toAttach(List<AttachBasicDto> attach){
		if(CollectionUtils.isEmpty(attach)) {
			return null;
		}
		
		List<Attach> la = new ArrayList<Attach>();
		attach.forEach(a -> la.add(toAttach(a)));
		
		return la;
		
	}

}
