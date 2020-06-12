package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cheejee.cms.dto.fore.ForeUserGetDto;
import com.cheejee.cms.pojo.Log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME 2020年4月22日下午5:13:08
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "日志实体-后台", description = "")
public class BackLogGetDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6053402794768629435L;

	@ApiModelProperty("日志id")
	private int id;

	@ApiModelProperty("记录的模块")
	private String module;

	@ApiModelProperty("操作")
	private String operate;

	@ApiModelProperty("日志信息")
	private String message;

	@ApiModelProperty("操作结果")
	private String result;

	@ApiModelProperty("创建时间")
	private Timestamp createTime;

	@ApiModelProperty("创建时的ip地址")
	private String ip;

	@ApiModelProperty("日志创建者")
	private ForeUserGetDto user;

	public static BackLogGetDto parse(Log log) {
		if(log == null) {
			return null;
		}
		
		return new BackLogGetDto(log.getId(), log.getModule(), log.getOperate(), log.getMessage(), log.getResult(),
				log.getCreateTime(), log.getIpByString(), ForeUserGetDto.parse(log.getUser()));
	}
	
	public static List<BackLogGetDto> parse(List<Log> logs){
		if(CollectionUtils.isEmpty(logs)) {
			return null;
		}
		
		List<BackLogGetDto> log = new ArrayList<BackLogGetDto>();
		logs.forEach(l -> log.add(parse(l)));
		
		return log;
	}
}
