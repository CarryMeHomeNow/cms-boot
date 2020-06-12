package com.cheejee.cms.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "通用API接口返回", description = "Common Api Response")
public class ApiResponseEntity<T> extends BasicResponseEntity	 {
	
    private static final long serialVersionUID = -8987146499044811408L;
    
    @ApiModelProperty(value = "通用返回数据")
    private T data;
    
    @Builder(toBuilder = true)
    private ApiResponseEntity(Integer code, String message, T data) {
    	super(code, message);
    	this.data = data;
    }
}