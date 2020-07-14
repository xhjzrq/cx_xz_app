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
 * 质量反馈删除接口 DlJJ05
 */
public class I_DlJJ05 extends I_Command{
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("质量反馈删除 JJ05_Delete----start");
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		
		
		//入参
		String wtid = req.getParameter("wtid") ;//问题id
		//入参
		
		try{
			
			if(CString.isEmpty(wtid)){
				throw new AppException("问题ID为空！");
			}
			
			wtid = wtid.toUpperCase().trim();
			////删除记录
			String sql="delete from APP_JJ_WTFKGZ "+
			           "where wtid='"+wtid+"' ";
			
			//FileLogger.debug("JJ05_Dl delete："+sql);
			jt.update(sql);
				
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("质量反馈删除 JJ05_Delete----end");
		return m;
	}
}
