package com.fenglian.biz;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.util.AppException;

import com.fenglian.command.I_Command;
import com.fenglian.tools.util.*;
import com.fenglian.tools.logger.*;

/**
 * 质量反馈确认接口 UpJJ05
 */
public class I_UpJJ05 extends I_Command{
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("质量反馈确认 JJ05_Up----start");
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		
		
		//入参
		String wtid = req.getParameter("wtid") ;//问题id
		String hfqrry = req.getParameter("hfqrry") ;//回复确认人员
		String hfqrrq = req.getParameter("hfqrrq") ;//回复确认日期
		//入参
		
		try{
			
			if(CString.isEmpty(wtid)){
				throw new AppException("问题ID为空！");
			}
			
			if(CString.isEmpty(hfqrry)){
				throw new AppException("回复确认人员为空！");
			}
			
			if(CString.isEmpty(hfqrrq)){
				throw new AppException("回复确认日期为空！");
			}
			
			wtid = wtid.toUpperCase().trim();
			////更新确认回复
			String sql="update APP_JJ_WTFKGZ "+
			           "set wtzt='10', "+
			           "    hfqrry='"+hfqrry+"', "+
			           "    hfqrrq='"+hfqrrq+"' "+
			           "where wtid='"+wtid+"' ";
			
			FileLogger.debug("JJ05_Up update："+sql);
			jt.update(sql);
				
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("质量反馈确认 JJ05_Up----end");
		return m;
	}
}
