package com.cheejee.cms.response;

import io.swagger.annotations.ApiModel;
import lombok.Builder;

/**
 * 
 * @author CARRY ME HOME
 * 2020年4月10日下午7:14:13
 */
@ApiModel(value = "简单的返回实体")
public class SimpleResponseEntity extends BasicResponseEntity{

	private static final long serialVersionUID = -6001975392431606834L;
	
	@Builder
	private SimpleResponseEntity(Integer code, String message) {
		super(code, message);
	}
}
