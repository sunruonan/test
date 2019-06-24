package com.mmall.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.mmall.service.IFileService;

public class FileServiceImpl implements IFileService{
	
	private Logger logger=LoggerFactory.getLogger(FileServiceImpl.class);
	
	public String upload(MultipartFile file,String path) {
		String fileName=file.getOriginalFilename();
		//获取扩展名
		String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
		//给上传的文件重新命名，防止两次上传的文件名一样，第二次的把第一次的覆盖掉
		String uploadFileName=UUID.randomUUID().toString()+"."+fileExtensionName;
		logger.info("开始上传文件，上传文件的文件名：{}，上传的路径：{}，新文件名：{}",fileName,path,uploadFileName);
		
		File fileDir=new File(path);
		if(!fileDir.exists()) {
			fileDir.setWritable(true);
			fileDir.mkdirs();
		}
		File targetFile=new File(path,uploadFileName);
		try {
			file.transferTo(targetFile);
			//文件已经上传成功了
		} catch (IOException e) {
			logger.error("上传文件异常",e);
			return null;
		}
		return targetFile.getName();
	}

}
