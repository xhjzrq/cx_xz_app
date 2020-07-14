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
public class I_JJ01_Qtp extends  I_Command{
	/**
	 * 扫码配送接收 查询单据
	 * @where WHERE条件  sscj=? and czbh=? and tpbh=?
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * title ：显示标题 （ztitle主标题 stitle 副标题）
	 * bjmx ： list 部件明细列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("扫码配送接收 托盘查询 JJ01_Qtp----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("title", Collections.EMPTY_LIST); //标题
		m.put("bjmx", Collections.EMPTY_LIST); //部件明细
		
		try{
			String wh = req.getParameter("where");
			String dylx = req.getParameter("dylx"); ////调用类型 默认接收查询JSCX 需求计划XQJH 配送计划PSJH
			String xqdwbm="";  //需求单位编码
			String xqdw="";  //需求单位
			
			//FileLogger.debug("扫码配送接收 托盘查询 where:"+wh);	
			
			if(CString.isEmpty(wh)){
				throw new AppException("where条件为空！");
			}
			
			if(CString.isEmpty(dylx)){
				dylx="JSCX";
			}

			if(wh.trim().equals("1")){
				wh=" sscj='01' and czbh='T300K-80#' and tpbh='1206FF120101F' ";
			}
			wh = wh.toUpperCase().trim();
			dylx = dylx.toUpperCase().trim();
			
			Date d1 = new Date();
			//1、主、副标题  根据WHERE条件
			xqdwbm = CString.fdstr(wh,"XQDWBM");
			if (!xqdwbm.equals("")){
				String xqdwsql="SELECT BMMC FROM SYBMXX WHERE BMBM='"+xqdwbm+"'";
				//xqdw=CString.rep(jt.queryForObject(xqdwsql,java.lang.String.class));
				Iterator it = jt.queryForList(xqdwsql).iterator();
				if(it.hasNext()){
					Map bm = (Map)it.next();
					xqdw = CString.rep(bm.get("BMMC")); 
				}else{
					throw new AppException("选择需求单位无效！");
				}
			}
						
			String ztitle = CString.rep(CString.fdstr(wh,"CZBH")+" "+CString.fdstr(wh,"TPBH"));
			String stitle = "";
			if (dylx.equals("PSJH")){
				if (CString.fdstr(wh,"PSDD").indexOf(xqdw)==-1){
					stitle =  CString.rep(xqdw+" "+CString.fdstr(wh,"PSDD")+" "+CString.fdstr(wh,"JHPSRQ")+" "+CString.fdstr(wh,"JSR="));
				}else{
					stitle =  CString.rep(CString.fdstr(wh,"PSDD")+" "+CString.fdstr(wh,"JHPSRQ")+" "+CString.fdstr(wh,"JSR="));
				}
				
			}else{
				stitle = CString.rep(xqdw+" "+CString.replace(CString.fdstr(wh,"PSDD"),xqdw, "")+" "+
						 CString.fdstr(wh,"XQRQ")+" "+CString.fdstr(wh,"JSR="));
			}
			Map title = new HashMap();
			title.put("ZTITLE", ztitle);
			title.put("STITLE", stitle);
			m.put("title", title);
			
			
			
			//2、托盘明细 -- 托盘部件明细  bjmx   
			String ssql = processSql(wh,dylx);
			List bjmxlist =  jt.queryForList(ssql);
			if(bjmxlist.isEmpty()){
				//throw new AppException("没有满足条件的托盘表明细数据！");
				m.put("code", true); 
				m.put("msg","没有满足条件的托盘表明细数据！");
			}else{
				m.put("bjmx", bjmxlist);
			}
			
			Date d4 = new Date();
			FileLogger.debug("JJ01_Qtp查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("扫码配送接收 托盘查询 JJ01_Qtp----end");
		return m;
	}
	
	/**
	 * 
	 * @param where参数
	 * @param dylx  调用查询类型
	 * @return 查询拼接where条件后的SQL语句
	 */
	private String processSql(String where,String dylx){
		String sql = "";
		
	    ////扫码接收 --托盘明细
		if (dylx.equals("JSCX")){
			sql = " SELECT ISNUll(PSZT,'') AS PSZT, "+ //配送状态
			   "       (CASE WHEN ISNUll(PSZT,'')='' THEN '2' "+
			   "          WHEN ISNUll(PSZT,'') IN ('01','11','98') THEN '3' "+
			   "          WHEN ISNUll(PSZT,'') IN ('10') THEN '4' "+
			   "          WHEN ISNUll(PSZT,'') IN ('99') THEN '5' "+
			   "          ELSE '' END ) AS BQXH, "+ //标签页序号
			   "    JDESC "+ //列表部件显示信息
            " FROM V_APP_JJ_PSJHB " +
            " WHERE "+ where +" "+
            " ORDER BY SSCJ,CZBH,TPBH,BJDH,BJMC,BJGG ";
		}
		
		////需求计划 --托盘明细
		if (dylx.equals("XQJH")){
			sql = " SELECT ISNUll(PSZT,'') AS PSZT, "+ //配送状态
			   "       (CASE WHEN ISNUll(PSZT,'')='' THEN '2' "+
			   "          WHEN ISNUll(PSZT,'') IN ('01','11','98') THEN '3' "+
			   "          WHEN ISNUll(PSZT,'') IN ('10') THEN '4' "+
			   "          WHEN ISNUll(PSZT,'') IN ('99') THEN '5' "+
			   "          ELSE '' END ) AS BQXH, "+ //标签页序号
			   "    XDESC AS JDESC "+ //列表部件显示信息
         " FROM V_APP_JJ_PSJHB " +
         " WHERE "+ where +" "+
         " ORDER BY SSCJ,CZBH,TPBH,BJDH,BJMC,BJGG ";
		}
		
	    ////配送计划 --托盘明细
		if (dylx.equals("PSJH")){
			sql = " SELECT ISNUll(PSZT,'') AS PSZT, "+ //配送状态
			   "       (CASE WHEN ISNUll(PSZT,'')='' THEN '2' "+
			   "          WHEN ISNUll(PSZT,'') IN ('01','11','98') THEN '3' "+
			   "          WHEN ISNUll(PSZT,'') IN ('10') THEN '4' "+
			   "          WHEN ISNUll(PSZT,'') IN ('99') THEN '5' "+
			   "          ELSE '' END ) AS BQXH, "+ //标签页序号
			   "    PDESC AS JDESC "+ //列表部件显示信息
         " FROM V_APP_JJ_PSJHB " +
         " WHERE "+ where +" "+
         " ORDER BY SSCJ,CZBH,TPBH,BJDH,BJMC,BJGG ";
		}
		
		//FileLogger.debug("JJ01_Qtp where:"+where);	
		//FileLogger.debug("JJ01_Qtp dylx:"+dylx);
		return sql;
	}
	
}
