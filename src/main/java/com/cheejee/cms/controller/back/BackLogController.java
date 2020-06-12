package com.cheejee.cms.controller.back;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheejee.cms.dto.back.BackLogGetDto;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.pojo.Log;
import com.cheejee.cms.response.ApiResponseEntity;
import com.cheejee.cms.response.PageResponseEntity;
import com.cheejee.cms.response.SimpleResponseEntity;
import com.cheejee.cms.service.LogService;
import com.cheejee.cms.tools.BuliderTool;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 只提供了时间的条件查找
 * 
 * @author CARRY ME HOME 2020年4月22日下午5:35:09
 */
@RestController
@RequestMapping("/admin/op-log")
@Api(tags = "操作日志-后台")
@Validated
public class BackLogController {

	@Resource
	private LogService logService;

	@GetMapping("/")
	@RequiresPermissions("log:get")
	@ApiOperation("获取所有日志")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<BackLogGetDto> getAllLog(int pageNum, int pageSize) {
		PageInfo<Log> info = logService.getLogAfter(pageNum, pageSize, new Timestamp(1587548903));

		return buildPageResponse(info);
	}

	@GetMapping("/{id}")
	@RequiresPermissions("log:get")
	@ApiOperation(value = "获取日志", notes = "获取特定id的日志")
	@ApiImplicitParam(name = "id", value = "日志id", required = true)
	public ApiResponseEntity<BackLogGetDto> getLog(@PathVariable int id) {

		Log c = logService.getLogById(id);
		String message = c == null ? "没有获取到相关信息" : "获取完成";

		return ApiResponseEntity.<BackLogGetDto>builder()
				.code(200)
				.message(message)
				.data(BackLogGetDto.parse(c))
				.build();
	}

	@GetMapping("/by-user/{id}")
	@RequiresPermissions("log:get")
	@ApiOperation("获取某个用户创建的日志")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户id", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "Int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "Int", required = true, example = "1") })
	public PageResponseEntity<BackLogGetDto> getLogByUser(@PathVariable int id, int pageNum, int pageSize)
			throws IncompleteException {
		PageInfo<Log> info = logService.getLogByUser(pageNum, pageSize, BuliderTool.buildUser(id));

		return buildPageResponse(info);
	}

	@GetMapping("/between")
	@RequiresPermissions("log:get")
	@ApiOperation("获取两日期之前创建的日志")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "beginTime", value = "开始日期", dataType = "date-time", example = "2007-12-03 10:15:30", required = true, paramType = "query"),
			@ApiImplicitParam(name = "endTime", value = "截至日期", dataType = "date-time", example = "2017-12-03 10:15:30", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<BackLogGetDto> getLogBetweenTime(@NotBlank String beginTime, String endTime, int pageNum,
			int pageSize) {

		try {
			Timestamp bt = Timestamp.valueOf(beginTime);
			Timestamp et = Timestamp.valueOf(endTime);
			PageInfo<Log> info = logService.getLogBetween(pageNum, pageSize, bt, et);
			return buildPageResponse(info);

		} catch (IllegalArgumentException e) {
			
			return PageResponseEntity.<BackLogGetDto>builder()
					.code(400)
					.message("时间参数错误")
					.build();
		}
	}

	@GetMapping("/day")
	@RequiresPermissions("log:get")
	@ApiOperation("获取某一天创建的日志")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "day", value = "日期", dataType = "date-time", example = "2017-09-03", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页内容数", defaultValue = "10", dataType = "int", example = "10"),
			@ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", required = true, example = "1") })
	public PageResponseEntity<BackLogGetDto> getLogByDay(@NotBlank String day, int pageNum, int pageSize) {

		try {
			LocalDate date = LocalDate.parse(day);
			Timestamp bt = Timestamp.valueOf(date.atStartOfDay());
			Timestamp et = Timestamp.valueOf(date.plusDays(1)
					.atStartOfDay());
			PageInfo<Log> info = logService.getLogBetween(pageNum, pageSize, bt, et);

			return buildPageResponse(info);

		} catch (IllegalArgumentException | DateTimeParseException e) {

			return PageResponseEntity.<BackLogGetDto>builder()
					.code(400)
					.message("时间参数错误")
					.build();
		}
	}

	@DeleteMapping("/before-day")
	@RequiresPermissions("log:delete")
	@ApiOperation(value = "删除日志", notes = "删除day天以前的创建的日志")
	@ApiImplicitParam(name = "day", value = "天数", paramType = "query", required = true)
	public SimpleResponseEntity deleteLogBefore(int day) {

		int i = logService.deleteLogBeforeDay(day);

		return SimpleResponseEntity.builder()
				.code(200)
				.message("删除完成,删除了" + i + "条日志")
				.build();
	}

	private PageResponseEntity<BackLogGetDto> buildPageResponse(PageInfo<Log> info) {
		return PageResponseEntity.<BackLogGetDto>builder()
				.code(200)
				.message("获取完成")
				.pageNum(info.getPageNum())
				.pages(info.getPages())
				.size(info.getSize())
				.total(info.getTotal())
				.list(BackLogGetDto.parse(info.getList()))
				.build();
	}
}
