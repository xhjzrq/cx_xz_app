/******************************************************************************
 * @(#)CommandFactory.java 1.0  2010-11-01
 *
 * 版权(c) 2005-2010  沈阳丰联数码科技有限公司
 * 中国·辽宁·沈阳市和平区中华�?188号中进大�?14�?
 * �?有权限被保留�?
 *
 *     本软件为丰联公司�?拥有的保密信息�?�在未经过本公司许可的情况下，任何人�?
 * 机构不可以将该软件的使用权和原代码泄露给其他人或机构�?
 *********************************com.fenglian.command*******************/
package com.fenglian.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;
import org.stringtree.json.JSONWriter;

import com.fenglian.tools.extent.ApplicationContextUtil;
import com.fenglian.tools.logger.FileLogger;
import com.fenglian.tools.util.Msg2Zip;

/**
 * 类描述：指令处理工程，将Socket接收到的指令进行分类处理�?
 * 
 * @author Administrator
 * 
 */
public class JsonCommandFactory {

	
	/**
	 *工厂方法，执行PAD提交上来的指令，并根据指令来处理相应的内容和类�?? CMD参数来指定调用的类，类定义在Spring中�??
	 * @param res
	 * @param req
	 */
	public void doCommand(HttpServletResponse res,HttpServletRequest req) {
		String cmd = req.getParameter("CMD"); 
		
		I_Command command = (I_Command) ApplicationContextUtil.getInstance()
				.getBean(cmd);
		try{
			Object obj =  command.doCommand(res,req);
			returnJson(obj, res);
		}catch(Exception e)
		{
			returnError("Exception:"+e.getMessage(),res);
			FileLogger.error("Exception:执行指令异常�?"+cmd,e);
		}
		
	}
	
	public void doCommand(FormFile ff,HttpServletResponse res,HttpServletRequest req) {
		String cmd = req.getParameter("CMD"); 
		
		I_Command command = (I_Command) ApplicationContextUtil.getInstance()
				.getBean(cmd);
		try{
			List list = Msg2Zip.convertFormFile2List(ff);
			command.setUplist(list);
			Object obj =  command.doCommand(res,req);
			returnJson(obj, res);
		}catch(Exception e)
		{
			returnError("Exception:"+e.getMessage(),res);
			FileLogger.error("Exception:执行指令异常�?"+cmd,e);
		}
		
	}
	
	public void doCommandFile(FormFile ff,HttpServletResponse res,HttpServletRequest req) {
		String cmd = req.getParameter("CMD"); 
		
		I_Command command = (I_Command) ApplicationContextUtil.getInstance()
				.getBean(cmd);
		try{
			String filepath = Msg2Zip.saveFile(ff);
			command.setFilepath(filepath);
			Object obj =  command.doCommand(res,req);
			returnJson(obj, res);
		}catch(Exception e)
		{
			returnError("Exception:"+e.getMessage(),res);
			FileLogger.error("Exception:执行指令异常�?"+cmd,e);
		}
		
	}
	
	
	/**
	 * 返回JSON对象
	 * @param obj
	 * @param response
	 */
	public void returnJson(Object obj,HttpServletResponse response)
	{
		JSONWriter w = new JSONWriter();
		String json = w.write(obj);
		try {
//			response.setContentType("text/plain");
//			response.setContentType("application/json");
//			response.addHeader("ContentType", "Content-Type: text/html;charset=UTF-8");
			response.getOutputStream().write(json.getBytes("UTF-8"));
		} catch (Exception e) {
			FileLogger.error("输出JSON异常",e);
		}
	}

	/**
	 * 返回JSON对象
	 * @param obj
	 * @param response
	 */
	public void returnError(String message,HttpServletResponse response)
	{
		try {
//			response.setContentType("text/plain");
//			response.addHeader("ContentType", "Content-Type: text/html;charset=UTF-8");
			response.getOutputStream().write(message.getBytes("UTF-8"));
		} catch (Exception e) {
			FileLogger.error("输出JSON异常",e);
		}
	}


}
