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
public class I_JJ02_Qdj extends  I_Command{
	/**
	 * 需求计划查询 查询
	 * @where WHERE条件  sscj=? and czbh=? and tpbh=?
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * title ：显示标题 （ztitle主标题 stitle 副标题）
	 * tpxx ： list 托盘明细列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("需求计划查询 JJ02_Qdj----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("title", Collections.EMPTY_LIST); //标题
		m.put("tpxx", Collections.EMPTY_LIST); //托盘信息
		
		try{
			
			String ksrq = req.getParameter("ksrq");  //开始日期
			String jsrq = req.getParameter("jsrq");  //结束日期
			String czbh = req.getParameter("czbh");  //工程编号
			String sscj = req.getParameter("sscj");  //配送部门
			String xqdwbm = req.getParameter("xqdwbm");  //需求单位编码
			String jsr = req.getParameter("jsr");  //接收人
			String tpbh = req.getParameter("tpbh");  //托盘表号
			String dylx = req.getParameter("dylx");  //调用类型
			String xqdw="";  //需求单位
			String psbm="";  //配送部门

			if(CString.isEmpty(ksrq)){
				throw new AppException("请选择开始日期！");
			}
			if(CString.isEmpty(jsrq)){
				throw new AppException("请选择结束日期！");
			}
			if(CString.isEmpty(czbh)){
				throw new AppException("请选择工程编号！");
			}
			if(CString.isEmpty(dylx)){
				throw new AppException("请输入调用类型！");
			}
			
			if(CString.isEmpty(sscj)){
				sscj="";
			}
			if(CString.isEmpty(xqdwbm)){
				xqdwbm="";
			}
			if(CString.isEmpty(jsr)){
				jsr="";
			}
			if(CString.isEmpty(tpbh)){
				tpbh="";
			}
			ksrq = ksrq.trim();
			jsrq = jsrq.trim();
			czbh = czbh.toUpperCase().trim();
			dylx = dylx.toUpperCase().trim();
			xqdwbm = xqdwbm.toUpperCase().trim();
			sscj = sscj.trim();
			jsr = jsr.trim();
			tpbh = tpbh.toUpperCase().trim();
			
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
			
			String ztitle = czbh+" ("+ksrq+"-"+jsrq+")";
			String stitle = CString.rep((CString.isEmpty(xqdw+" "+jsr)?"":"需求:"+xqdw+" "+jsr)+" "+(CString.isEmpty(psbm)?"":"配送:"+psbm));
						
			Map title = new HashMap();
			title.put("ZTITLE", ztitle);
			title.put("STITLE", stitle);
			m.put("title", title);
						
			//2、托盘group列表 --  tpxx  
			String ssql = processSql(ksrq,jsrq,czbh,xqdwbm,sscj,jsr,tpbh,dylx);	
			
			List tpxxlist =  jt.queryForList(ssql);
			if(tpxxlist.isEmpty()){
				//throw new AppException("没有满足查询条件的计划数据！");
				m.put("code", true); 
				m.put("msg","没有满足查询条件的计划数据！");
			}else{
				m.put("tpxx", tpxxlist);
			}
			
			Date d4 = new Date();
			FileLogger.debug("JJ02_Qdj查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("需求计划查询 JJ02_Qdj----end");
		return m;
	}
	
	
	/**
	 * 
	 * @params where参数
	 * @param dylx  调用查询类型
	 * @return 查询拼接where条件后的SQL语句
	 */
	private String processSql(String ksrq,String jsrq,String czbh,String xqdwbm,String sscj,String jsr,String tpbh,String dylx){
		String sql = "";
		String where = "";
		
		////查询需求计划
		if (dylx.equals("XQJH")){
			where = "czbh='"+czbh+"' and xqrq>='"+ksrq+"' and xqrq<='"+jsrq+"' ";
			if(!CString.isEmpty(xqdwbm)){
				where +=" and xqdwbm='"+xqdwbm+"' ";
			} 
			if(!CString.isEmpty(sscj)){
				where +=" and sscj='"+sscj+"' ";
			} 
			if(!CString.isEmpty(jsr)){
				where +=" and jsr like '"+jsr+"%' ";
			} 
			if(!CString.isEmpty(tpbh)){
				//判断按卡号查询
				if (tpbh.toUpperCase().startsWith("K")){
					where +=" and kh = '"+tpbh.substring(1)+"' ";
				}
				else{
					where +=" and tpbh like '"+tpbh+"%' ";
				}
				
			} 
			
			sql = " SELECT WH AS WH, "+ //明细查询WHERE条件 
			   "       (CASE WHEN XQSL>JSSL+QXSL THEN '1' "+
			   "          WHEN XQSL=JSSL+QXSL THEN '2' "+
			   "          ELSE '' END ) AS BQXH, "+ //标签页序号
			   "    JDESC "+ //列表部件显示信息
            " FROM V_APP_JJ_XQJBB " +
            " WHERE "+ where +
            " ORDER BY CZBH,TPBH,XQRQ,XQDWBM,JSR ";	
		}
		
	    ////查询配送计划
		if (dylx.equals("PSJH")){
			where = "czbh='"+czbh+"' and jhpsrq>='"+ksrq+"' and jhpsrq<='"+jsrq+"' ";
			if(!CString.isEmpty(xqdwbm)){
				where +=" and xqdwbm='"+xqdwbm+"' ";
			} 
			if(!CString.isEmpty(sscj)){
				where +=" and sscj='"+sscj+"' ";
			} 
			if(!CString.isEmpty(jsr)){
				where +=" and jsr like '"+jsr+"%' ";
			} 
			if(!CString.isEmpty(tpbh)){
				//判断按卡号查询
				if (tpbh.toUpperCase().startsWith("K")){
					where +=" and kh like '"+tpbh.substring(1)+"%' ";
				}
				else{
					where +=" and tpbh like '"+tpbh+"%' ";
				}
			} 
			
			sql = " SELECT WH AS WH, "+ //明细查询WHERE条件 
			   "       (CASE WHEN XQSL>JSSL+QXSL THEN '1' "+
			   "          WHEN XQSL=JSSL+QXSL THEN '2' "+
			   "          ELSE '' END ) AS BQXH, "+ //标签页序号
			   "    JDESC "+ //列表部件显示信息
            " FROM V_APP_JJ_PSJBB " +
            " WHERE "+ where +
            " ORDER BY CZBH,TPBH,JHPSRQ,XQDWBM,JSR ";	
		}
		
		FileLogger.debug("JJ02_Qdj where:"+where);	
		//FileLogger.debug("JJ02_Qdj dylx:"+dylx);
		return sql;
	}
	
}
