package com.cheejee.cms.dto.fore;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.cheejee.cms.pojo.Log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME
 * 2020年4月22日下午4:56:38
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "日志DTO-GET-前台", description = "")
public class ForeLogGetDto implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5716365773268216116L;

	@ApiModelProperty("日志信息")
	protected String message;
	
	@ApiModelProperty("创建时间")
	protected Timestamp createTime;
	
	@ApiModelProperty("创建时的ip地址")
	protected String ip;
	
	public static ForeLogGetDto parse(Log log) {
		return new ForeLogGetDto(log.getMessage(), log.getCreateTime(), log.getIpByString());
	}
	
	public static List<ForeLogGetDto> parse(List<Log> logs){
		List<ForeLogGetDto> log = new ArrayList<ForeLogGetDto>();
		logs.forEach(l -> log.add(parse(l)));
		
		return log;
	}
}
