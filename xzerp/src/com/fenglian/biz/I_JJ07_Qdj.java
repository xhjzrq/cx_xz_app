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
public class I_JJ07_Qdj extends  I_Command{
	/**
	 * 特殊管件生产进度 查询
	 * @params 查询参数
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * title ：显示标题 （ztitle主标题 stitle 副标题）
	 * bjxx ： list 管件信息列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("特殊管件查询 JJ07_Qdj----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("title", Collections.EMPTY_LIST); //标题
		m.put("bjxx", Collections.EMPTY_LIST); //管件信息
		
		try{
			
			String ksrq = req.getParameter("ksrq");  //开始日期
			String jsrq = req.getParameter("jsrq");  //结束日期
			String czbh = req.getParameter("czbh");  //工程编号
			String tglx = req.getParameter("tglx");  //管件类型编码
			String xqdw = req.getParameter("xqdw");  //需求单位编码
			String bh = req.getParameter("bh");  //编号
			String bjdh = req.getParameter("bjdh");  //管件号
			String tglxmc="";  //管件类型名称
			String xqdwmc="";  //需求单位名称

			if(CString.isEmpty(ksrq)){
				throw new AppException("请选择开始日期！");
			}
			if(CString.isEmpty(jsrq)){
				throw new AppException("请选择结束日期！");
			}
			if(CString.isEmpty(czbh)){
				throw new AppException("请选择工程编号！");
			}
			if(CString.isEmpty(tglx)){
				tglx="";
			}
			if(CString.isEmpty(xqdw)){
				xqdw="";
			}
			if(CString.isEmpty(bh)){
				bh="";
			}
			if(CString.isEmpty(bjdh)){
				bjdh="";
			}
			
			ksrq = ksrq.trim();
			jsrq = jsrq.trim();
			czbh = czbh.toUpperCase().trim();
			tglx = tglx.toUpperCase().trim();
			xqdw = xqdw.toUpperCase().trim();
			bh = bh.toUpperCase().trim();
			bjdh = bjdh.toUpperCase().trim();
			
			
			Date d1 = new Date();
			//1、主、副标题  根据WHERE条件
			
			//需求单位名称
			if (!xqdw.equals("")){
				String xqdwsql="SELECT BMMC FROM SYBMXX WHERE BMBM='"+xqdw+"'";
				//xqdw=CString.rep(jt.queryForObject(xqdwsql,java.lang.String.class));
				Iterator it = jt.queryForList(xqdwsql).iterator();
				if(it.hasNext()){
					Map bm = (Map)it.next();
					xqdwmc = CString.rep(bm.get("BMMC")); 
				}else{
					throw new AppException("选择需求单位无效！");
				}
			}
			if (!tglx.equals("")){
				String tglxsql="SELECT MC FROM DM_CSBMB WHERE BMLB='TGLX' AND BM='"+tglx+"'";
				//xqdw=CString.rep(jt.queryForObject(xqdwsql,java.lang.String.class));
				Iterator it1 = jt.queryForList(tglxsql).iterator();
				if(it1.hasNext()){
					Map bm1 = (Map)it1.next();
					tglxmc = CString.rep(bm1.get("MC")); 
				}else{
					throw new AppException("选择管件类型无效！");
				}
			}
			
			String ztitle = CString.rep(czbh+(CString.isEmpty(tglx)?"":" ("+tglxmc+")"));
			String stitle = CString.rep((CString.isEmpty(xqdw)?"":xqdwmc)+" ("+ksrq+" - "+jsrq+")");
						
			Map title = new HashMap();
			title.put("ZTITLE", ztitle);
			title.put("STITLE", stitle);
			m.put("title", title);
						
			//2、管件信息列表 --  bjxx  
			String ssql = processSql(ksrq,jsrq,czbh,tglx,xqdw,bh,bjdh);	
			
			List bjxxlist =  jt.queryForList(ssql);
			if(bjxxlist.isEmpty()){
				//throw new AppException("没有满足查询条件的计划数据！");
				m.put("code", true); 
				m.put("msg","没有满足查询条件的计划数据！");
			}else{
				m.put("bjxx", bjxxlist);
			}
			
			Date d4 = new Date();
			FileLogger.debug("JJ07_Qdj查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("特殊管件查询 JJ07_Qdj----end");
		return m;
	}
	
	
	/**
	 * 
	 * @params where参数
	 * @return 查询拼接where条件后的SQL语句
	 */
	private String processSql(String ksrq,String jsrq,String czbh,String tglx,String xqdw,String bh,String bjdh){
		String sql = "";
		String where = "";
		
		where = " sscj='02' and tcrq>='"+ksrq+"' and tcrq<='"+jsrq+"' ";
		if(!CString.isEmpty(czbh)){
			where +=" and czbh='"+czbh+"' ";
		} 
		if(!CString.isEmpty(tglx)){
			where +=" and tglx='"+tglx+"' ";
		} 
		if(!CString.isEmpty(xqdw)){
			where +=" and (xqdw='"+xqdw+"' or  xqdw='')";
		} 
		if(!CString.isEmpty(bh)){
			where +=" and bh like '%"+bh+"%' ";
		} 
		if(!CString.isEmpty(bjdh)){
			where +=" and bjdh like '%"+bjdh+"%' ";
		} 
		
		
		sql = 	" SELECT SSCJ, "+ //所属车间
				"        CZBH, "+ //工程编号
				"        BH, "+ //编号
				"        BJDH, "+ //管件号
				"        ZT, "+ //状态
		   		"        JDESC "+ //管件显示信息
		        " FROM V_APP_JJ_TSGJGZ " +
		        " WHERE "+ where +
		        " ORDER BY SSCJ,CZBH,BH,BJDH ";	
		
		FileLogger.debug("JJ07_Qdj where:"+where);	
		return sql;
	}
	
}
