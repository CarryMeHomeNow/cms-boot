package com.cheejee.cms.controller.fore;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheejee.cms.common.CurrentAppInfo;
import com.cheejee.cms.dto.fore.ForeLogGetDto;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.pojo.Log;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.service.LogService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author CARRY ME HOME
 * 2020年4月22日下午5:07:45
 */
@RestController
@RequestMapping("/op-log")
@Api(tags = "操作日志")
public class ForeLogController {

	@Resource
	private LogService logService;
	@Resource
	private CurrentAppInfo appInfo;

	@GetMapping("/")
	@ApiOperation(value = "获取日志", notes = "获取由当前登录用户所创建的日志")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<ForeLogGetDto> getLog(int pageNum, int pageSize) throws IncompleteException {
		PageInfo<Log> info = logService.getLogByUser(pageNum, pageSize, appInfo.getCurrentUser());
		
		return PageResponseEntity.<ForeLogGetDto>builder()
				.code(200)
				.message("获取完成")
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.list(ForeLogGetDto.parse(info.getList()))
				.build();
	}

}
