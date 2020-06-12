package com.cheejee.cms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cheejee.cms.pojo.Tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME 2020年4月17日下午10:35:33
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "标签DTO-BASIC-通用", description = "")
public class TagBasicDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2945143053473223775L;

	@ApiModelProperty("标签id")
	private int id;

	@ApiModelProperty("标签名称")
	private String name;

	public static TagBasicDto parse(Tag tag) {
		if (tag == null) {
			return null;
		}
		return new TagBasicDto(tag.getId(), tag.getName());
	}

	public static List<TagBasicDto> parse(List<Tag> tags) {
		List<TagBasicDto> lt = new ArrayList<TagBasicDto>();
		tags.forEach(t -> lt.add(parse(t)));

		return lt;
	}

	
	public static Tag toTag(TagBasicDto tag) {
		if(tag == null) {
			return null;
		}
		
		Tag a = new Tag();
		a.setId(tag.getId());
		a.setName(tag.getName());
		
		return a;
	}
	
	public static List<Tag> toTag(List<TagBasicDto> tag){
		if(CollectionUtils.isEmpty(tag)) {
			return null;
		}
		
		List<Tag> la = new ArrayList<Tag>();
		tag.forEach(a -> la.add(toTag(a)));
		
		return la;
		
	}
}
