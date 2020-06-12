package com.cheejee.cms.dto.fore;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.cheejee.cms.dto.AttachBasicDto;
import com.cheejee.cms.dto.TagBasicDto;
import com.cheejee.cms.pojo.Content;
import com.cheejee.cms.pojo.User;

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
 * 2020年5月5日下午5:53:52
 */
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "内容DTO-POST-PUT-前台", description = "用于内容添加和修改的dto")
public class ForeContentGeneralDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4263845951861102335L;

	@ApiModelProperty("标题不能为空")
	@NotBlank(message = "标题不能为空")
	@Length(max = 15, message = "标题过长（超过15个字符）")
	protected String title;

	@ApiModelProperty("摘要")
	protected String summary;

	@ApiModelProperty("文字")
	@NotBlank(message = "内容文字不能为空")
	protected String words;

	@ApiModelProperty("标签列表")
	protected List<TagBasicDto> tags;

	@ApiModelProperty("附件列表")
	protected List<AttachBasicDto> attachs;
	
	/**
	 * 
	 * @param user 当前登录用户
	 * @return
	 */
	public Content toPostContent(User user) {
		
		Content c = new Content();
		c.setTitle(title);
		c.setSummary(summary);
		c.setWords(words);
		c.setAttachs(AttachBasicDto.toAttach(attachs));
		c.setTags(TagBasicDto.toTag(tags));
		c.setUser(user);
		
		return c;
	}
	
	public Content toPutContent(int id) {
		
		Content c = new Content();
		c.setId(id);
		c.setTitle(title);
		c.setSummary(summary);
		c.setWords(words);
		c.setAttachs(AttachBasicDto.toAttach(attachs));
		c.setTags(TagBasicDto.toTag(tags));
		
		return c;
	}
	
}
