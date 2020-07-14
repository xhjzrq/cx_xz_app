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
public class I_JJ06_Qdj extends  I_Command{
	/**
	 * 舾装日报 查询
	 * @params 查询参数
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * title ：显示标题 （ztitle主标题 stitle 副标题）
	 * fkxx ： list 反馈问题明细
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("舾装日报查询 JJ06_Qdj----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("title", Collections.EMPTY_LIST); //标题
		m.put("fkxx", Collections.EMPTY_LIST); //明细信息
		
		try{
			
			String ksrq = req.getParameter("ksrq");  //开始日期
			String jsrq = req.getParameter("jsrq");  //结束日期
			String czbh = req.getParameter("czbh");  //工程编号
			String fkdw = req.getParameter("fkdw");  //反馈单位
			String zrbm = req.getParameter("zrbm");  //责任部门
			String fkry = req.getParameter("fkry");  //反馈人员
			String wjlb = req.getParameter("wjlb");  //物件类别
			String tpbh = req.getParameter("tpbh");  //托盘表号
			String fkdwmc="";  //反馈单位名称

			if(CString.isEmpty(ksrq)){
				throw new AppException("请选择开始日期！");
			}
			if(CString.isEmpty(jsrq)){
				throw new AppException("请选择结束日期！");
			}
			if(CString.isEmpty(czbh)){
				czbh="";
			}
			if(CString.isEmpty(fkdw)){
				fkdw="";
			}
			if(CString.isEmpty(zrbm)){
				zrbm="";
			}
			if(CString.isEmpty(fkry)){
				fkry="";
			}
			if(CString.isEmpty(wjlb)){
				wjlb="";
			}
			if(CString.isEmpty(tpbh)){
				tpbh="";
			}
			
			ksrq = ksrq.trim();
			jsrq = jsrq.trim();
			czbh = czbh.toUpperCase().trim();
			fkdw = fkdw.toUpperCase().trim();
			zrbm = zrbm.toUpperCase().trim();
			fkry = fkry.trim();
			wjlb = wjlb.trim();
			tpbh = tpbh.toUpperCase().trim();
			
			
			Date d1 = new Date();
			//1、主、副标题  根据WHERE条件
			
			
			if (!fkdw.equals("")){
				String fkdwsql="SELECT BMMC FROM SYBMXX WHERE BMBM='"+fkdw+"'";
				//xqdw=CString.rep(jt.queryForObject(xqdwsql,java.lang.String.class));
				Iterator it = jt.queryForList(fkdwsql).iterator();
				if(it.hasNext()){
					Map bm = (Map)it.next();
					fkdwmc = CString.rep(bm.get("BMMC")); 
				}else{
					throw new AppException("选择反馈单位无效！");
				}
			}
			
			String ztitle = CString.rep(ksrq+" - "+jsrq);
			String stitle = CString.rep((CString.isEmpty(czbh)?"":"工程:"+czbh)+" "+(CString.isEmpty(fkdw)?"":"反馈:"+fkdwmc+" "+fkry));
						
			Map title = new HashMap();
			title.put("ZTITLE", ztitle);
			title.put("STITLE", stitle);
			m.put("title", title);
						
			//2、反馈问题明细 --  fkxx  
			String ssql = processSql(ksrq,jsrq,czbh,fkdw,zrbm,fkry,wjlb,tpbh);	
			
			List fkxxlist =  jt.queryForList(ssql);
			if(fkxxlist.isEmpty()){
				//throw new AppException("没有满足查询条件的计划数据！");
				m.put("code", true); 
				m.put("msg","没有满足查询条件的计划数据！");
			}else{
				m.put("fkxx", fkxxlist);
			}
			
			Date d4 = new Date();
			FileLogger.debug("JJ06_Qdj查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("舾装日报查询 JJ06_Qdj----end");
		return m;
	}
	
	
	/**
	 * 
	 * @params where参数
	 * @return 查询拼接where条件后的SQL语句
	 */
	private String processSql(String ksrq,String jsrq,String czbh,String fkdw,String zrbm,String fkry,String wjlb,String tpbh){
		String sql = "";
		String where = "";
		
		where = " fkrq>='"+ksrq+"' and fkrq<='"+jsrq+"' ";
		if(!CString.isEmpty(czbh)){
			where +=" and czbh='"+czbh+"' ";
		} 
		if(!CString.isEmpty(fkdw)){
			where +=" and fkdw='"+fkdw+"' ";
		} 
		if(!CString.isEmpty(zrbm)){
			where +=" and zrbm='"+zrbm+"' ";
		} 
		if(!CString.isEmpty(fkry)){
			where +=" and fkry like '%"+fkry+"%' ";
		} 
		if(!CString.isEmpty(wjlb)){
			where +=" and wjlb like '%"+wjlb+"%' ";
		} 
		if(!CString.isEmpty(tpbh)){
			where +=" and tpbh like '%"+tpbh+"%' ";
		} 
		
		where +=" and ( zt>='02' ) ";
		
		sql = 	" SELECT MXID, "+ //明细ID 
				"        CZBH, "+ //工程编号
				"        RTRIM(TPBH + ' ('+WJLB+')') as TPBH, "+ //托盘表号 + 物件类别
				"        FKDW AS FKDWBM, "+ //反馈单位编码
				"        FKDWMC AS FKDW, "+ //反馈单位名称
				"        (FKRY + ' '+FKRQ) AS FKRY, "+ //反馈人员+日期
				"        FKWT, "+ //反馈问题
				"        ZRDW AS LC, "+ //反馈问题
				"        ZRBM, "+ //反馈问题
				"        (HFRY + ' '+HFRQ) AS HFRY, "+ //回复人员+日期
				"        HFNR, "+ //回复内容
				"        (QRRY + ' '+QRRQ) AS QRRY, "+ //确认人员+日期
				"        QRBZ, "+ //确认备注
				"        ZT, "+ //状态
		   		"        JDESC "+ //反馈问题显示信息
		        " FROM V_APP_JJ_QFFKRB " +
		        " WHERE "+ where +
		        " ORDER BY CZBH,FKRQ,WJLB,TPBH,MXID ";	
		
		FileLogger.debug("JJ06_Qdj where:"+where);	
		return sql;
	}
	
}
