package com.cheejee.cms.dto.back;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.cheejee.cms.dto.AttachBasicDto;
import com.cheejee.cms.dto.TagBasicDto;
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
 * @author CARRY ME HOME
 * 2020年5月7日下午5:55:32
 */
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "内容DTO-PUT-后台", description = "用于内容修改的内容dto")
public class BackContentPutDto implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8021088684226071981L;

	@ApiModelProperty("标题")
	@Length(max = 15, message = "标题过长（超过15个字符）")
	protected String title;

	@ApiModelProperty("摘要")
	protected String summary;

	@ApiModelProperty("文字")
	@NotBlank(message = "内容文字不能为空")
	protected String words;
	
	@ApiModelProperty("状态")
	protected Integer state;
	
	@ApiModelProperty("标签列表")
	protected List<TagBasicDto> tags;

	@ApiModelProperty("附件列表")
	protected List<AttachBasicDto> attachs;
	
	/**
	 * 
	 * @param user 当前登录用户
	 * @return
	 */
	public Content toContent(int id) {
		
		Content c = new Content();
		c.setId(id);
		c.setTitle(title);
		c.setSummary(summary);
		c.setWords(words);
		c.setAttachs(AttachBasicDto.toAttach(attachs));
		c.setTags(TagBasicDto.toTag(tags));
		c.setState(state);
		
		return c;
	}
	
}
