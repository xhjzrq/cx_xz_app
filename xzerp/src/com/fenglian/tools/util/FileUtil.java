package com.fenglian.tools.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.fenglian.tools.logger.FileLogger;



/**
 * @date 2008-11-28
 * @author zhangyb
 * 文件上传和修改封装类，封装从struts表单提交的file对象的存储和修改方法
 */
public class FileUtil {

	/**
	 * 功能：存储照片到指定路径，并且照片名称使用时间串来存储。
	 * @param path 服务器端文件保存路径
	 * @param formfile 文件对象
	 * @return 返回存储后的文件名称
	 */
	public String saveFile(String path,FormFile formfile)
	{
		return saveFile(path,formfile,"");
	}
	public String saveFile(String path,FormFile formfile,String expName)
	{
			//1、判断path是否存在，否则创建
        File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
			//2、获取当前时间为新文件名。
		String filename = expName+System.currentTimeMillis();
			//获得文件名
		String formfilename = formfile.getFileName();
			//获得文件类型
		String fileType = (formfilename.substring(formfilename.lastIndexOf('.') + 1)).toLowerCase();
		String url = path + "/" + filename + "." + fileType;		
			//3、获取文件输入流和输出流
		InputStream streamIn = null;
		OutputStream streamOut = null;
		try 
		{
			streamIn = formfile.getInputStream();
			streamOut = new FileOutputStream(url);
			//4、写入流
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) 
			{
				streamOut.write(buffer, 0, bytesRead);
			}
			return filename + "." + fileType;
		}
		catch(Exception e){
			FileLogger.error("上传文件出现错误！文件所在位置"+url,e);
			throw new RuntimeException(e.getMessage());
		}		
		finally
		{
			//5、关闭流并返回文件名称
			if(streamOut != null)
			{
				try {
					streamOut.close();
				} catch (IOException e) {
				}
			}
			if(streamIn != null)
			{
				try {
					streamIn.close();
				} catch (IOException e) {
				}
			}
		}		
	}

	
	
	/**
	 * 功能：删除指定路径下的指定文件，如果文件不存则忽略。
	 * @param path  文件路径
	 * @param filename 文件
	 * @return 删除出现异常或无法删除则返回false 不抛出异常。
	 */
	public boolean delFile(String path,String filename)
	{
		try
		{
			File file = new File(path + filename);
			file.delete();
		}
		catch(Exception e){
			FileLogger.fatal("删除文件错误！文件所在位置:"+path + filename,e);
			return false;
		}
		return true;
	}
}
