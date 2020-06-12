package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@ApiModel(value = "标签DTO-GET-后台", description = "")
public class BackTagGetDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("标签id")
	private int id;

	@ApiModelProperty("标签名称")
	private String name;

	@ApiModelProperty("标签描述")
	private String describe;

	@ApiModelProperty("标签被使用次数")
	private Integer contentCount;

	public static BackTagGetDto parse(Tag tag) {
		if (tag == null) {
			return null;
		}
		return new BackTagGetDto(tag.getId(), tag.getName(), tag.getDescribe(), tag.getContentCount());
	}

	public static List<BackTagGetDto> parse(List<Tag> tags) {
		List<BackTagGetDto> lt = new ArrayList<BackTagGetDto>();
		tags.forEach(t -> lt.add(parse(t)));

		return lt;
	}

}
