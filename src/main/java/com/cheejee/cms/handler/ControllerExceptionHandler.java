package com.cheejee.cms.handler;

import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.cheejee.cms.exception.DataDuplicationException;
import com.cheejee.cms.exception.DataException;
import com.cheejee.cms.exception.IncompleteException;
import com.cheejee.cms.exception.InsufficientPermissionException;
import com.cheejee.cms.exception.NotFoundException;
import com.cheejee.cms.exception.NotSupportedException;
import com.cheejee.cms.exception.OperationsException;
import com.cheejee.cms.response.SimpleResponseEntity;

/**
 * 全局异常处理
 * 
 * @author CARRY ME HOME 2020年3月6日下午10:54:41
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(UnauthenticatedException.class)
	public SimpleResponseEntity UnauthenticatedHandle(UnauthenticatedException e) {
		return SimpleResponseEntity.builder()
				.code(401)
				.message("用户未授权")
				.build();
	}

	@ExceptionHandler(AuthorizationException.class)
	public SimpleResponseEntity AuthroizedHandle(AuthorizationException e) {
		return SimpleResponseEntity.builder()
				.code(401)
				.message("权限不足")
				.build();
	}

	@ExceptionHandler(InsufficientPermissionException.class)
	public SimpleResponseEntity InsufficientPermissionHandle(InsufficientPermissionException e) {
		return SimpleResponseEntity.builder()
				.code(401)
				.message("权限不足")
				.build();
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public SimpleResponseEntity maxUploadSizeExceededHandle(MaxUploadSizeExceededException e) {
		return SimpleResponseEntity.builder()
				.code(400)
				.message("文件大小超出限制（20m）")
				.build();
	}

	@ExceptionHandler(Exception.class)
	public SimpleResponseEntity basicHandle(Exception e) {
		String message = e.getMessage();

		if (message == null) {
			message = e.getClass()
					.getName();
		}
		
		return SimpleResponseEntity.builder()
				.code(409)
				.message(message)
				.build();
	}

	@ExceptionHandler(FileUploadException.class)
	public SimpleResponseEntity fileUploadHandle(FileUploadException e) {
		return SimpleResponseEntity.builder()
				.code(400)
				.message("文件上传失败")
				.build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public SimpleResponseEntity MethodArgumentNotValidHandle(MethodArgumentNotValidException e) {
		String message = "";
		if (e.getBindingResult()
				.getFieldErrorCount() != 0) {
			message = e.getBindingResult()
					.getFieldError()
					.getDefaultMessage();
		} else {
			message = e.getBindingResult()
					.getGlobalError()
					.getDefaultMessage();
		}

		return SimpleResponseEntity.builder()
				.code(400)
				.message(message)
				.build();
	}

	@ExceptionHandler(value = { DataDuplicationException.class, DataException.class, IncompleteException.class,
			NotFoundException.class, NotSupportedException.class, ConstraintViolationException.class,
			OperationsException.class })
	public SimpleResponseEntity commonHandle(Exception e) {
		return SimpleResponseEntity.builder()
				.code(400)
				.message(e.getMessage())
				.build();
	}

}
