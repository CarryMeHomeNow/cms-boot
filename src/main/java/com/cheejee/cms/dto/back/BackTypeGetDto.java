package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cheejee.cms.pojo.Type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME 2020年5月2日下午4:47:56
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "类型实体-后台", description = "")
public class BackTypeGetDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3331379407255588922L;

	@ApiModelProperty(value = "类型id")
	private int id;

	@ApiModelProperty(value = "类型名称")
	private String name;

	@ApiModelProperty(value = "类型描述")
	private String describe;

	@ApiModelProperty(value = "包含的后缀")
	private List<BackSuffixGetDto> suffix;

	public static BackTypeGetDto parse(Type type) {
		if (type == null) {
			return null;
		}

		return new BackTypeGetDto(type.getId(), type.getName(), type.getDescribe(),
				BackSuffixGetDto.parse(type.getSuffixs()));
	}

	public static List<BackTypeGetDto> parse(List<Type> type) {
		if (CollectionUtils.isEmpty(type)) {
			return null;
		}

		List<BackTypeGetDto> ltd = new ArrayList<BackTypeGetDto>();
		type.forEach(t -> ltd.add(parse(t)));

		return ltd;
	}

}
