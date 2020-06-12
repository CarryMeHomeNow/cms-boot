package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cheejee.cms.dto.fore.ForeUserGetDto;
import com.cheejee.cms.pojo.Group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME 2020年4月18日下午3:49:52
 */
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "分组DTO-GET-后台", description = "")
public class BackGroupGetDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6981475019453078571L;

	@ApiModelProperty("分组id")
	private int id;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("描述")
	private String describe;

	@ApiModelProperty("创建者")
	private ForeUserGetDto creater;

	@ApiModelProperty("子组")
	private List<BackGroupGetDto> sons;

	@ApiModelProperty("组员")
	private List<ForeUserGetDto> users;

	@ApiModelProperty("拥有的角色")
	private List<BackRoleGetDto> roles;

	public static BackGroupGetDto parse(Group group) {
		if (group == null) {
			return null;
		}
		
		return BackGroupGetDto.builder()
				.id(group.getId())
				.name(group.getName())
				.describe(group.getDescribe())
				.creater(ForeUserGetDto.parse(group.getCreater()))
				.sons(BackGroupGetDto.parse(group.getSons()))
				.users(ForeUserGetDto.parse(group.getUsers()))
				.roles(BackRoleGetDto.parse(group.getRoles()))
				.build();
	}

	public static List<BackGroupGetDto> parse(List<Group> groups) {
		if(CollectionUtils.isEmpty(groups)) {
			return null;
		}
		
		List<BackGroupGetDto> lc = new ArrayList<BackGroupGetDto>();
		groups.forEach(g -> lc.add(parse(g)));

		return lc;
	}
}
