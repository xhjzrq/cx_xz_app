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
 * 问题件跟踪确认接口 UpJJ03
 */
public class I_UpJJ03 extends I_Command{
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("问题件跟踪更新 JJ03_Up----start");
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		
		
		//入参
		String sscj = req.getParameter("sscj") ;//所属车间
		String czbh = req.getParameter("czbh") ;//工程编号
		String psdh = req.getParameter("psdh") ;//配送单号
		String psxh = req.getParameter("psxh") ;//配送序号
		String hfqrry = req.getParameter("hfqrry") ;//回复确认人员
		String hfqrrq = req.getParameter("hfqrrq") ;//回复确认日期
		//入参
		
		try{
			
			if(CString.isEmpty(sscj)){
				throw new AppException("所属车间为空！");
			}
			
			if(CString.isEmpty(czbh)){
				throw new AppException("工程编号为空！");
			}
			
			if(CString.isEmpty(psdh)){
				throw new AppException("配送单号为空！");
			}
			
			if(CString.isEmpty(psxh)){
				throw new AppException("配送序号为空！");
			}
			
			if(CString.isEmpty(hfqrry)){
				throw new AppException("回复确认人员为空！");
			}
			
			if(CString.isEmpty(hfqrrq)){
				throw new AppException("回复确认日期为空！");
			}
			
			////更新确认回复
			String sql="update APP_JJ_PSMXB "+
			           "set wtzt='10', "+
			           "    hfqrry='"+hfqrry+"', "+
			           "    hfqrrq='"+hfqrrq+"' "+
			           "where sscj='"+sscj+"' and czbh='"+czbh+"' and psdh='"+psdh+"' and psxh='"+psxh+"' ";
			
			FileLogger.debug("JJ03_Up update："+sql);
			jt.update(sql);
				
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("问题件跟踪更新 JJ03_Up----end");
		return m;
	}
}
