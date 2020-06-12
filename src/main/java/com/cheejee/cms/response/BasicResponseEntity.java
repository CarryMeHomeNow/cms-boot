package com.cheejee.cms.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基本返回实体
 * @author CARRY ME HOME
 * 2020年4月10日下午6:55:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "通用返回实体", description = "Common Api Response")
public class BasicResponseEntity implements Serializable{
	
	private static final long serialVersionUID = -7096862595122135497L;
	
	/**
     * 通用返回状态
     */
    @ApiModelProperty(value = "通用返回状态", required = true)
    protected Integer code;
    
    /**
     * 通用返回信息
     */
    @ApiModelProperty(value = "通用返回信息", required = true)
    protected String message;
}
