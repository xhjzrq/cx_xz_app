package com.fenglian.biz;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.util.AppException;

import com.fenglian.command.I_Command;
import com.fenglian.tools.logger.FileLogger;
import com.fenglian.tools.util.CString;
import com.fenglian.tools.util.Encrypt;

public class I_Yhfun extends  I_Command{
	/**
	 * @param yhdm 用户名 
	 * @param pwd 密码
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * user ：用户基本
	 * modulelist : list 用户权限编码组
	 * cwly ：list 问题类型列表
	 * bmxx ：list 部门信息列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("用户模块功能----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("funlist", Collections.EMPTY_LIST);  //用户有权限的指定模块下的功能列表
		
		try{
			String yhdm = req.getParameter("yhdm");
			String mkbm = req.getParameter("mkbm");
			String ssql = "";
			
			
			if(CString.isEmpty(yhdm)){
				throw new AppException("用户为空！");
			}
			if(CString.isEmpty(mkbm)){
				throw new AppException("模块为空！");
			}
			
			//用户权限 -- 模块功能  funlist   用户有权限的模块及模块下功能
			ssql =  " SELECT 	SYFUNP.MKBM as MKBM, " +  //模块编码
					"           SYFUNP.GNXH as GNXH, "+ //功能序号（字符型JJ01、JJ02...）
					"	        SYFUNP.GNMC as GNMC, "+ //功能名称
					"			ISNULL(SYFUNP.GNMS,'') as GNMS, "+ //功能描述（）
					"			ISNULL(SYFUNP.GNDZ,'') as GNDZ, "+ //功能动作（）
					"			ISNULL(SYFUNP.GNCS,'') as GNCS, "+ //功能参数（）
					"			ISNULL(SYFUNP.GNTP,'') as GNTP  "+ //功能图片（）
					"	FROM SYUFRP JOIN SYFUNP on (SYUFRP.ZXTBM = SYFUNP.ZXTBM and "+ 
					"	                            SYUFRP.MKBM = SYFUNP.MKBM and "+ 
					"	                            SYUFRP.GNMC = SYFUNP.GNMC and "+ 
					"	                            SYUFRP.GNLB = SYFUNP.GNLB ) "+ 
					"	WHERE SYFUNP.ZXTBM='AP' and SYFUNP.GNLB='' and  "+ 
					"	      SYUFRP.YHDM=? and  SYUFRP.MKBM=? "+ 
					"	ORDER BY SYFUNP.MKBM,SYFUNP.GNXH " ;
			
			List listfun =  jt.queryForList(ssql,new Object[]{yhdm.toUpperCase(),mkbm.toUpperCase()});
			if(listfun.isEmpty()){
				throw new AppException("用户“"+yhdm+"”无模块功能访问权限，请联系管理员！");
			}else{
				m.put("funlist", listfun);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("用户模块功能----end");
		return m;
	}
	
}