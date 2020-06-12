package com.cheejee.cms.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.cheejee.cms.dto.fore.ForeUserGetDto;
import com.cheejee.cms.pojo.Content;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author CARRY ME HOME 2020年4月17日下午10:30:27
 */
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "内容DTO-GET-通用", description = "前后台通用的内容dto")
public class ContentGetDto implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1066355310561540448L;

	@ApiModelProperty("内容id")
	protected Integer id;

	@ApiModelProperty("标题不能为空")
	@Length(max = 15, message = "标题过长（超过15个字符）")
	protected String title;

	@ApiModelProperty("摘要")
	protected String summary;

	@ApiModelProperty("文字")
	@NotBlank(message = "内容文字不能为空")
	protected String words;
	
	@ApiModelProperty("状态")
	protected Integer state;

	@ApiModelProperty("添加时间")
	protected Timestamp addTime;

	@ApiModelProperty("上次修改时间")
	protected Timestamp updateTime;
	
	@ApiModelProperty("内容创建者")
	private ForeUserGetDto user;

	@ApiModelProperty("标签列表")
	protected List<TagBasicDto> tags;

	@ApiModelProperty("附件列表")
	protected List<AttachBasicDto> attachs;
	
	public static ContentGetDto parse(Content content) {
		if (content == null) {
			return null;
		}
		return ContentGetDto.builder()
				.id(content.getId())
				.title(content.getTitle())
				.summary(content.getSummary())
				.words(content.getWords())
				.state(content.getState())
				.addTime(content.getAddTime())
				.updateTime(content.getUpdateTime())
				.user(ForeUserGetDto.parse(content.getUser()))
				.tags(TagBasicDto.parse(content.getTags()))
				.attachs(AttachBasicDto.parse(content.getAttachs()))
				.build();
	}

	public static List<ContentGetDto> parse(List<Content> content) {
		List<ContentGetDto> lc = new ArrayList<ContentGetDto>();
		content.forEach(c -> lc.add(parse(c)));

		return lc;
	}

}
