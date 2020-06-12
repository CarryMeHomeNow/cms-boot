package com.cheejee.cms.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * 一些文件的工具方法
 * @author CARRY ME HOME
 * 2020年4月16日下午8:38:56
 */
@Slf4j
public class FileTool {
	
	/**
	 * 下载文件。
	 * 
	 * @param file
	 * @param response
	 */
	public static void downloadFile(File file, HttpServletResponse response) {
		response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());

		try (FileInputStream fin = new FileInputStream(file); BufferedInputStream bin = new BufferedInputStream(fin)) {
			byte[] buff = new byte[1024];
			OutputStream out = response.getOutputStream();

			int i = 0;
			while ((i = bin.read(buff)) != -1) {
				out.write(buff, 0, i);
			}
			out.flush();

		} catch (IOException e) {
			log.error("下载文件出现了异常：{}", e);
		}
	}

	/**
	 * 文件上传，文件名会用uuid替换。
	 * 
	 * @param file 需要上传的文件
	 * @param path 目标路径
	 * @return 上传成功返回url
	 * @throws FileUploadException 上传失败抛出异常 
	 */
	public static String uploadFile(MultipartFile file, String path) throws FileUploadException {
		new File(path).mkdirs();
		File newFile = new File(path + createFileName(file));

		try {
			file.transferTo(newFile);
		} catch (IllegalStateException | IOException e) {
			throw new FileUploadException(e.getMessage());
		}
		return newFile.getAbsolutePath();
	}
	
	/**
	 * 使用uuid产生文件名称，
	 * 
	 * @param avatar
	 * @param request
	 * @return
	 */
	public static String createFileName(MultipartFile avatar) {
		String suffix = getFileSuffix(avatar);
		String fileName = UUID.randomUUID()
				.toString() + suffix;

		return fileName;
	}
	
	/**
	 * 获取文件后缀
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileSuffix(MultipartFile file) {
		String originalFileName = file.getOriginalFilename();
		String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

		return suffix;
	}

}
