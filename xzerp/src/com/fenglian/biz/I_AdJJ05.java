package com.fenglian.biz;

import java.util.ArrayList;
import java.util.Collections;
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
 * 质量反馈新增接口 UpJJ05
 */
public class I_AdJJ05 extends I_Command{
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("质量反馈新增 JJ05_Add----start");
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
//		m.put("wtxx", Collections.EMPTY_LIST); //问题信息
		m.put("wtxx", Collections.EMPTY_MAP); //问题信息
		
		//入参
		String yhdm = req.getParameter("yhdm") ;//用户账号
		String tcdwbm = req.getParameter("tcdwbm") ;//提出单位编码
		String tcry = req.getParameter("tcry") ;//提出人员
		String tcrq = req.getParameter("tcrq") ;//提出日期
		String czbh = req.getParameter("czbh") ;//工程编号
		String fxlxbm = req.getParameter("fxlxbm") ;//风险类型编码
		String wtms = req.getParameter("wtms") ;//问题反馈
		
		//入参
		try{
			
			if(CString.isEmpty(yhdm)){
				throw new AppException("用户账号为空！");
			}
			if(CString.isEmpty(tcdwbm)){
				throw new AppException("提出单位为空！");
			}
			if(CString.rep(tcdwbm).length()==2){
				throw new AppException("只有前方需求单位人员可以提出反馈！");
			}
			if(CString.isEmpty(tcry)){
				throw new AppException("提出人员为空！");
			}
			if(CString.isEmpty(tcrq)){
				throw new AppException("提出日期为空！");
			}
			if(CString.isEmpty(czbh)){
				throw new AppException("工程编号为空！");
			}
			if(CString.isEmpty(fxlxbm)){
				throw new AppException("风险类型为空！");
			}
			if(CString.isEmpty(wtms)){
				throw new AppException("问题反馈！");
			}
			
			////新增质量反馈
			yhdm = yhdm.toUpperCase().trim();
			czbh = czbh.toUpperCase().trim();
			wtms = wtms.trim();
			String wtid=CString.rep(System.currentTimeMillis())+"-"+yhdm;


			String sql="insert into APP_JJ_WTFKGZ(WTID,YHDM,TCDW,TCRY,TCRQ,CZBH,WTLX,WTMS,WTZT) "+
			          "values('"+wtid+"','"+yhdm+"','"+tcdwbm+"','"+tcry+"','"+tcrq+"','"+czbh+"','"+fxlxbm+"','"+wtms+"','01')";
			           
			//FileLogger.debug("JJ05_Add insert："+sql);
			jt.update(sql);
			
			////返回插入记录  需要与JJ05_Qdj中的查询字段一样
			sql = 	" SELECT WTID, "+ //问题ID 
			"        YHDM, "+ //用户代码
			"        TCDWBM, "+ //提出单位编码
			"        TCDW, "+ //提出单位
			"        (TCRY + ' '+TCRQ) AS TCRY, "+ //提出人员+日期
			"        CZBH, "+ //工程编码
			"        FXLXBM, "+ //风险类型编码
			"        FXLX, "+ //风险类型
			"        WTZT, "+ //问题状态
			"        WTMS, "+ //问题描述
			"        (HFRY + ' '+HFRQ) AS HFRY, "+ //回复人员+日期
			"        HFNR, "+ //回复内容
			"        (HFQRRY + ' '+HFQRRQ) AS HFQRRY, "+ //回复确认人员+日期
	   		"        JDESC "+ //列表问题显示信息
	        " FROM V_APP_JJ_WTFKGZ " +
	        " WHERE WTID ='"+wtid+"' ";	
			
//			List wtxxlist =  jt.queryForList(sql);
//			if(wtxxlist.isEmpty()){
//				//throw new AppException("没有满足查询条件的计划数据！");
//				m.put("code", true); 
//				m.put("msg","质量反馈插入失败！");
//			}else{
//				m.put("wtxx", wtxxlist);
//			}
			
			Map wtxxMap =  jt.queryForMap(sql);
			if(wtxxMap.isEmpty()){
				//throw new AppException("没有满足查询条件的计划数据！");
				m.put("code", true); 
				m.put("msg","质量反馈插入失败！");
			}else{
				m.put("wtxx", wtxxMap);
			}
				
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("质量反馈新增 JJ05_Add----end");
		return m;
	}
}
