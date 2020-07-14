/******************************************************************************
 * @(#)I_Command.java 1.0  2010-11-01
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

import com.fenglian.tools.extent.JdbcTemplatePage;

/**
 * 类描述：指令执行接口，所有jhPAD请求处理�?
 * @author zhang.yb
 */
public  abstract class I_Command {
	
	public JdbcTemplatePage jt; 
	public JdbcTemplatePage getJt() {
		return jt;
	}
	public void setJt(JdbcTemplatePage jt) {
		this.jt = jt;
	}
	
	public List uplist;	
	public String filepath;
	public List getUplist() {
		return uplist;
	}
	public void setUplist(List uplist) {
		this.uplist = uplist;
	}
	public abstract Object doCommand(HttpServletResponse res,HttpServletRequest req);
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}		
}
