/**
 * 
 */
package com.cheejee.cms.dto.fore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cheejee.cms.pojo.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author CARRY ME HOME 2020年3月6日下午11:03:54
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户DTO-GET-前台", description = "")
public class ForeUserGetDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1123061909660581818L;

	@ApiModelProperty(value = "用户id")
	private Integer id;

	@ApiModelProperty(value = "用户名")
	private String name;

	public static ForeUserGetDto parse(User user) {
		ForeUserGetDto User_d = new ForeUserGetDto();

		if (user != null) {
			User_d.setId(user.getId());
			User_d.setName(user.getName());
		}
		return User_d;
	}
	
	public static List<ForeUserGetDto> parse(List<User> user){
		if(user == null || user.isEmpty()) {
			return null;
		}
		
		List<ForeUserGetDto> lu = new ArrayList<ForeUserGetDto>();
		user.forEach(u -> lu.add(parse(u)));
		
		return lu;
	}
	
	public static User toUser(ForeUserGetDto user) {
		if(user == null) {
			return null;
		}
		
		User u = new User();
		u.setId(user.getId());
		u.setName(user.getName());
		
		return u;
	}

	public static List<User> toUser(List<ForeUserGetDto> users) {
		if(CollectionUtils.isEmpty(users)) {
			return null;
		}
		
		List<User> lu = new ArrayList<User>();
		users.forEach(u -> lu.add(toUser(u)));
		
		return lu;
	}

	
}
