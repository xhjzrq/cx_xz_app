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
 * 质量反馈编辑接口 EdJJ05
 */
public class I_EdJJ05 extends I_Command{
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("质量反馈编辑 JJ05_Edit----start");
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("fxlx", ""); // 列表显示信息
		m.put("jdesc", ""); // 列表显示信息
		
		
		//入参
		String wtid = req.getParameter("wtid") ;//问题id
		String czbh = req.getParameter("czbh") ;//工程编号
		String fxlxbm = req.getParameter("fxlxbm") ;//风险类型编码
		String wtms = req.getParameter("wtms") ;//问题反馈
		//入参
		
		try{
			
			if(CString.isEmpty(wtid)){
				throw new AppException("问题ID为空！");
			}
			
			if(CString.isEmpty(czbh)){
				throw new AppException("工程编号为空！");
			}
			
			if(CString.isEmpty(fxlxbm)){
				throw new AppException("风险类型为空！");
			}
			
			if(CString.isEmpty(wtms)){
				throw new AppException("问题反馈为空！");
			}
			
			czbh = czbh.toUpperCase().trim();
			wtid = wtid.toUpperCase().trim();
			wtms = wtms.trim();
			////更新确认回复
			String sql="update APP_JJ_WTFKGZ "+
			           "set czbh='"+czbh+"', "+
			           "    wtlx='"+fxlxbm+"', "+
			           "    wtms='"+wtms+"' "+
			           "where wtid='"+wtid+"' ";
			
			//FileLogger.debug("JJ05_Edit update："+sql);
			jt.update(sql);
			
			///返回fxlx、jdesc数据处理
			String rsql="SELECT FXLX,JDESC FROM V_APP_JJ_WTFKGZ WHERE WTID='"+wtid+"'";
			Iterator it = jt.queryForList(rsql).iterator();
			if(it.hasNext()){
				Map fh = (Map)it.next();
				m.put("fxlx", CString.rep(fh.get("FXLX"))); // 返回风险类型
				m.put("jdesc", CString.rep(fh.get("JDESC"))); // 返回显示信息
			}else{
				throw new AppException("找不到问题ID！");
			}
			
				
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("质量反馈编辑 JJ05_Edit----end");
		return m;
	}
}
