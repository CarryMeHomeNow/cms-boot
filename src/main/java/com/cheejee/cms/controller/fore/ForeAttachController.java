package com.cheejee.cms.controller.fore;

import static com.cheejee.cms.tools.FileTool.downloadFile;
import static com.cheejee.cms.tools.FileTool.uploadFile;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cheejee.cms.common.CurrentAppInfo;
import com.cheejee.cms.dto.AttachGetDto;
import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.NotSupportedException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.pojo.Attach;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.response.SimpleResponseEntity;
import com.cheejee.cms.service.AttachService;
import com.cheejee.cms.tools.BuliderTool;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author CARRY ME HOME 2020年4月15日下午10:57:26
 */
@RestController
@RequestMapping("/attach")
@Api(tags = "附件")
@Validated
public class ForeAttachController {

	@Value("${cheejee.cms.attach-path}")
	private String attachPath;

	@Resource
	private AttachService attachService;
	@Resource
	private CurrentAppInfo appInfo;

	@GetMapping("/")
	@ApiOperation(value = "获取所有附件", notes = "获取所有由当前用户上传的附件")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<AttachGetDto> getAllsearchAttach(int pageNum, int pageSize)
			throws IncompleteException, NotFoundException {
		PageInfo<Attach> info = attachService.getAttachByUser(pageNum, pageSize, appInfo.getCurrentUser());

		return buildResponse(info);
	}
	
	@GetMapping("/{id}")
	@ApiOperation("获取某个附件")
	@ApiImplicitParam(name = "id", value = "附件id", required = true)
	public ApiResponseEntity<AttachGetDto> getAttach(@PathVariable int id) throws IncompleteException {

		Attach a = attachService.getAttachById(id, appInfo.getCurrentUser());
		String message = a == null ? "附件不存在" : "获取成功";

		return ApiResponseEntity.<AttachGetDto>builder()
				.code(200)
				.message(message)
				.data(AttachGetDto.parse(a))
				.build();
	}

	@GetMapping("/search")
	@ApiOperation(value = "搜索附件", notes = "搜索出名称符合的附件，只能查找出当前用户上传的附件")
	@ApiImplicitParams({ @ApiImplicitParam(name = "name", value = "用于搜索的关键字", paramType = "query", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<AttachGetDto> searchAttach(@NotNull String name, int pageNum, int pageSize)
			throws IncompleteException {
		
		PageInfo<Attach> info = attachService.getAttachByName(pageNum, pageSize, name, appInfo.getCurrentUser());

		return buildResponse(info);
	}

	@GetMapping("/type-specific/{typeId}")
	@ApiOperation(value = "按类型获取附件", notes = "获取指定类型的附件，只能获取当前用户上传的附件")
	@ApiImplicitParams({ @ApiImplicitParam(name = "typeId", value = "类型id", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<AttachGetDto> getAttachByType(@PathVariable int typeId, int pageNum, int pageSize)
			throws IncompleteException {
		
		PageInfo<Attach> info = attachService.getAttachByType(pageNum, pageSize, BuliderTool.buildType(typeId),
				appInfo.getCurrentUser());

		return buildResponse(info);
	}

	@GetMapping("/download/{id}")
	@ApiOperation(value = "下载附件")
	@ApiImplicitParam(name = "id", value = "附件id", required = true)
	public SimpleResponseEntity downloadAttach(@PathVariable int id, HttpServletResponse response) {
		Attach attach = attachService.getAttachByIdByManage(id);
		File file = new File(attach.getUrl());

		if (file.exists()) {
			downloadFile(file, response);
			return SimpleResponseEntity.builder()
					.code(200)
					.message("下载完成")
					.build();
		} else {
			return SimpleResponseEntity.builder()
					.code(400)
					.message("文件不存在")
					.build();
		}

	}

	@PostMapping("/upload")
	@ApiOperation(value = "上传附件")
	@ApiImplicitParams({ @ApiImplicitParam(name = "attachFile", value = "上传的附件", dataType = "__file", required = true),
			@ApiImplicitParam(name = "name", value = "附件名称", required = true) })
	public ApiResponseEntity<AttachGetDto> uploadAttach(String name, MultipartFile attachFile) throws IncompleteException,
			FileUploadException, DataDuplicationException, InsufficientPermissionException, NotSupportedException {
		Attach attach = BuliderTool.buildAttach(name);
		attach.setUser(appInfo.getCurrentUser());
		attach.setUrl(attachFile.getOriginalFilename());

		if (!attachService.checkAttachIsSupported(attach)) {
			return ApiResponseEntity.<AttachGetDto>builder()
					.code(400)
					.message("不支持的附件格式")
					.build();
		}

		String url = uploadFile(attachFile, attachPath);
		attach.setUrl(url);
		attachService.addAttach(appInfo.getCurrentUser(), attach);

		return ApiResponseEntity.<AttachGetDto>builder()
				.code(200)
				.message("上传成功")
				.data(AttachGetDto.parse(attach))
				.build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除附件", notes = "只能删除自己上传的附件")
	@ApiImplicitParam(name = "id", value = "附件id", required = true)
	public SimpleResponseEntity deleteAttach(@PathVariable Integer id)
			throws IncompleteException, NotFoundException, InsufficientPermissionException, OperationsException {
		
		Attach attach = new Attach();
		attach.setId(id);
		attachService.deleteAttachsForce(appInfo.getCurrentUser(), attach);

		return SimpleResponseEntity.builder()
				.code(200)
				.message("删除完成")
				.build();
	}

	private PageResponseEntity<AttachGetDto> buildResponse(PageInfo<Attach> info) {

		return PageResponseEntity.<AttachGetDto>builder()
				.code(200)
				.message("查找完成")
				.list(AttachGetDto.parse(info.getList()))
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.build();
	}
}
