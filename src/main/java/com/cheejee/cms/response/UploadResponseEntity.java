package com.cheejee.cms.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "上传文件返回实体", description = "调用上传文件api将会返回此实体")
public class UploadResponseEntity extends BasicResponseEntity{

	private static final long serialVersionUID = -6304483406770104533L;

	@ApiModelProperty(value = "文件的url", notes = "文件在服务器的路径")
	protected String url;

	@Builder
	private UploadResponseEntity(Integer code, String message, String url) {
		super(code, message);
		this.url = url;
	}
}
