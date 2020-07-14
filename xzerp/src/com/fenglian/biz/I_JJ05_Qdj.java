package com.fenglian.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.util.AppException;

import com.fenglian.command.I_Command;
import com.fenglian.tools.logger.FileLogger;
import com.fenglian.tools.util.*;
public class I_JJ05_Qdj extends  I_Command{
	/**
	 * 质量反馈查询 查询
	 * @params 查询参数
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * title ：显示标题 （ztitle主标题 stitle 副标题）
	 * wtxx ： list 托盘明细列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("质量反馈查询 JJ05_Qdj----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("title", Collections.EMPTY_LIST); //标题
		m.put("wtxx", Collections.EMPTY_LIST); //问题信息
		
		try{
			
			String ksrq = req.getParameter("ksrq");  //开始日期
			String jsrq = req.getParameter("jsrq");  //结束日期
			String czbh = req.getParameter("czbh");  //工程编号
			String fxlxbm = req.getParameter("fxlxbm");  //风险类型编码
			String tcdwbm = req.getParameter("tcdwbm");  //提出单位编码
			String tcry = req.getParameter("tcry");  //提出人员
			String yhdm = req.getParameter("yhdm");  //用户账号
			String tcdw="";  //提出单位

			if(CString.isEmpty(ksrq)){
				throw new AppException("请选择开始日期！");
			}
			if(CString.isEmpty(jsrq)){
				throw new AppException("请选择结束日期！");
			}
			if(CString.isEmpty(yhdm)){
				throw new AppException("请传入当前用户账号！");
			}
			if(CString.isEmpty(czbh)){
				czbh="";
			}
			if(CString.isEmpty(fxlxbm)){
				fxlxbm="";
			}
			
			if(CString.isEmpty(tcdwbm)){
				tcdwbm="";
			}
			if(CString.isEmpty(tcry)){
				tcry="";
			}
			
			ksrq = ksrq.trim();
			jsrq = jsrq.trim();
			czbh = czbh.toUpperCase().trim();
			fxlxbm = fxlxbm.toUpperCase().trim();
			tcdwbm = tcdwbm.toUpperCase().trim();
			tcry = tcry.trim();
			yhdm = yhdm.toUpperCase().trim();
			
			
			Date d1 = new Date();
			//1、主、副标题  根据WHERE条件
			
			
			if (!tcdwbm.equals("")){
				String tcdwsql="SELECT BMMC FROM SYBMXX WHERE BMBM='"+tcdwbm+"'";
				//xqdw=CString.rep(jt.queryForObject(xqdwsql,java.lang.String.class));
				Iterator it = jt.queryForList(tcdwsql).iterator();
				if(it.hasNext()){
					Map bm = (Map)it.next();
					tcdw = CString.rep(bm.get("BMMC")); 
				}else{
					throw new AppException("选择提出单位无效！");
				}
			}
			
			String ztitle = CString.rep(ksrq+" - "+jsrq);
			String stitle = CString.rep((CString.isEmpty(czbh)?"":"工程:"+czbh)+" "+(CString.isEmpty(tcdw+" "+tcry)?"":"提出:"+tcdw+" "+tcry));
						
			Map title = new HashMap();
			title.put("ZTITLE", ztitle);
			title.put("STITLE", stitle);
			m.put("title", title);
						
			//2、托盘group列表 --  tpxx  
			String ssql = processSql(ksrq,jsrq,czbh,fxlxbm,tcdwbm,tcry,yhdm);	
			
			List wtxxlist =  jt.queryForList(ssql);
			if(wtxxlist.isEmpty()){
				//throw new AppException("没有满足查询条件的计划数据！");
				m.put("code", true); 
				m.put("msg","没有满足查询条件的计划数据！");
			}else{
				m.put("wtxx", wtxxlist);
			}
			
			Date d4 = new Date();
			FileLogger.debug("JJ05_Qdj查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("质量反馈查询 JJ05_Qdj----end");
		return m;
	}
	
	
	/**
	 * 
	 * @params where参数
	 * @param dylx  调用查询类型
	 * @return 查询拼接where条件后的SQL语句
	 */
	private String processSql(String ksrq,String jsrq,String czbh,String fxlxbm,String tcdwbm,String tcry,String yhdm){
		String sql = "";
		String where = "";
		
		where = " tcrq>='"+ksrq+"' and tcrq<='"+jsrq+"' ";
		if(!CString.isEmpty(czbh)){
			where +=" and czbh='"+czbh+"' ";
		} 
		if(!CString.isEmpty(fxlxbm)){
			where +=" and fxlxbm='"+fxlxbm+"' ";
		} 
		if(!CString.isEmpty(tcdwbm)){
			where +=" and tcdwbm='"+tcdwbm+"' ";
		} 
		if(!CString.isEmpty(tcry)){
			where +=" and tcry like '"+tcry+"%' ";
		} 
		
		where +=" and (wtzt>'01' or (wtzt='01' and yhdm='"+yhdm+"')) ";
		
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
		        " WHERE "+ where +
		        " ORDER BY WTID ";	
		
		FileLogger.debug("JJ03_Qdj where:"+where);	
		//FileLogger.debug("JJ03_Qdj sql:"+sql);
		return sql;
	}
	
}
