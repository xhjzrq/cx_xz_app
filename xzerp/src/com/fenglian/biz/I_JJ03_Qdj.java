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
public class I_JJ03_Qdj extends  I_Command{
	/**
	 * 问题件跟踪查询 查询
	 * @params 查询参数
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * title ：显示标题 （ztitle主标题 stitle 副标题）
	 * wtxx ： list 托盘明细列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("问题件跟踪查询 JJ03_Qdj----start");	
		
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
			String wtlxbm = req.getParameter("wtlxbm");  //问题类型编码
			String sscj = req.getParameter("sscj");  //配送部门
			String tcdwbm = req.getParameter("tcdwbm");  //提出单位编码
			String tcry = req.getParameter("tcry");  //提出人员
			String tcdw="";  //提出单位
			String psbm="";  //配送部门

			if(CString.isEmpty(ksrq)){
				throw new AppException("请选择开始日期！");
			}
			if(CString.isEmpty(jsrq)){
				throw new AppException("请选择结束日期！");
			}
			if(CString.isEmpty(czbh)){
				czbh="";
			}
			if(CString.isEmpty(wtlxbm)){
				wtlxbm="";
			}
			if(CString.isEmpty(sscj)){
				sscj="";
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
			wtlxbm = wtlxbm.toUpperCase().trim();
			tcdwbm = tcdwbm.toUpperCase().trim();
			sscj = sscj.trim();
			tcry = tcry.trim();
			
			
			Date d1 = new Date();
			//1、主、副标题  根据WHERE条件
			if (sscj.equals("01")){
				psbm="铁舾制造部";
			}else if(sscj.equals("02")){
				psbm="管系制造部";
			}else if(sscj.equals("03")){
				psbm="单元制造部";
			}else if(sscj.equals("04")){
				psbm="生产管理部";
			}else if(sscj.equals("05")){
				psbm="营销部";
			}else if(sscj.equals("06")){
				psbm="质量管理部";
			}else{
				psbm="";
			}
			
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
			String stitle = CString.rep((CString.isEmpty(czbh)?"":"工程:"+czbh)+" "+(CString.isEmpty(tcdw+" "+tcry)?"":"提出:"+tcdw+" "+tcry)+" "+(CString.isEmpty(psbm)?"":"配送:"+psbm));
						
			Map title = new HashMap();
			title.put("ZTITLE", ztitle);
			title.put("STITLE", stitle);
			m.put("title", title);
						
			//2、托盘group列表 --  tpxx  
			String ssql = processSql(ksrq,jsrq,czbh,wtlxbm,sscj,tcdwbm,tcry);	
			
			List wtxxlist =  jt.queryForList(ssql);
			if(wtxxlist.isEmpty()){
				//throw new AppException("没有满足查询条件的计划数据！");
				m.put("code", true); 
				m.put("msg","没有满足查询条件的计划数据！");
			}else{
				m.put("wtxx", wtxxlist);
			}
			
			Date d4 = new Date();
			FileLogger.debug("JJ03_Qdj查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("问题件跟踪查询 JJ03_Qdj----end");
		return m;
	}
	
	
	/**
	 * 
	 * @params where参数
	 * @param dylx  调用查询类型
	 * @return 查询拼接where条件后的SQL语句
	 */
	private String processSql(String ksrq,String jsrq,String czbh,String wtlxbm,String sscj,String tcdwbm,String tcry){
		String sql = "";
		String where = "";
		
		where = " tcrq>='"+ksrq+"' and tcrq<='"+jsrq+"' ";
		if(!CString.isEmpty(czbh)){
			where +=" and czbh='"+czbh+"' ";
		} 
		if(!CString.isEmpty(wtlxbm)){
			where +=" and wtlxbm='"+wtlxbm+"' ";
		} 
		if(!CString.isEmpty(sscj)){
			where +=" and sscj='"+sscj+"' ";
		} 
		if(!CString.isEmpty(tcdwbm)){
			where +=" and tcdwbm='"+tcdwbm+"' ";
		} 
		if(!CString.isEmpty(tcry)){
			where +=" and tcry like '"+tcry+"%' ";
		} 
		
		sql = 	" SELECT SSCJ, "+ //所属车间 
				"        CZBH, "+ //工程编号
				"        PSDH, "+ //配送单号
				"        PSXH, "+ //配送序号
				"        TPBH, "+ //托盘表号
				"        BJMS, "+ //部件描述
				"        TCDW, "+ //提出单位
				"        (TCRY + ' '+TCRQ) AS TCRY, "+ //提出人员+日期
				"        WTLX, "+ //问题类型
				"        WTZT, "+ //问题状态
				"        WTMS, "+ //问题描述
				"        (HFRY + ' '+HFRQ) AS HFRY, "+ //回复人员+日期
				"        HFNR, "+ //回复内容
				"        (HFQRRY + ' '+HFQRRQ) AS HFQRRY, "+ //回复确认人员+日期
		   		"        JDESC "+ //列表问题显示信息
		        " FROM V_APP_JJ_WTJGZ " +
		        " WHERE "+ where +
		        " ORDER BY TCRQ,TCDWBM,TCRY,CZBH,TPBH,BJDH,BJMC,BJGG ";	
		
		FileLogger.debug("JJ03_Qdj where:"+where);	
		//FileLogger.debug("JJ02_Qdj dylx:"+dylx);
		return sql;
	}
	
}
