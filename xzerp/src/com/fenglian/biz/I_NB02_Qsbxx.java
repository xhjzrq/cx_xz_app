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
public class I_NB02_Qsbxx extends  I_Command{
	/**
	 * 人员资质 查询
	 * @params 查询参数
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * title ：显示标题 （ztitle主标题 stitle 副标题）
	 * sbxx ： list 设备信息列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("设备信息查询 NB02_Qsb----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("title", Collections.EMPTY_LIST); //标题
		m.put("sbxx", Collections.EMPTY_LIST); //设备信息
		
		try{
			
			String sybm = req.getParameter("sybm");  //使用部门编码
			String sydw = req.getParameter("sydw");  //使用单位
			String sbbh = req.getParameter("sbbh");  //设备编号
			String sbmc = req.getParameter("sbmc");  //设备名称
			String xhgg = req.getParameter("xhgg");  //规格型号
			String lxlb = req.getParameter("lxlb");  //设备类型
			String dwmc = "";
		

			if(CString.isEmpty(sybm)){
				sybm="";
			}
			if(CString.isEmpty(sydw)){
				sydw="";
			}
			if(CString.isEmpty(sbbh)){
				sbbh="";
			}
			if(CString.isEmpty(sbmc)){
				sbmc="";
			}
			if(CString.isEmpty(xhgg)){
				xhgg="";
			}
			if(CString.isEmpty(lxlb)){
				lxlb="";
			}
					
			sybm = sybm.trim();
			sbmc = sbmc.trim();
			sbbh = sbbh.toUpperCase().trim();
			xhgg = xhgg.toUpperCase().trim();
			lxlb = lxlb.trim();
			
			Date d1 = new Date();
			//1、主、副标题  根据WHERE条件
						
			if (!sybm.equals("")){
				String dwsql="SELECT BMMC FROM SYBMXX WHERE BMBM='"+sybm+"'";
				Iterator it = jt.queryForList(dwsql).iterator();
				if(it.hasNext()){
					Map bm = (Map)it.next();
					dwmc = CString.rep(bm.get("BMMC")); 
				}else{
					throw new AppException("选择单位无效！");
				}
			}
			
			String ztitle = CString.rep((CString.isEmpty(sybm)?"":dwmc)+" 设备列表");
			String stitle = "";
						
			Map title = new HashMap();
			title.put("ZTITLE", ztitle);
			title.put("STITLE", stitle);
			m.put("title", title);
						
			//2、设备信息列表 --  ryxx  
			String ssql = processSql(sybm,sydw,sbbh,sbmc,xhgg,lxlb);	
			
			List sbxxlist =  jt.queryForList(ssql);
			if(sbxxlist.isEmpty()){
				//throw new AppException("没有满足查询条件的计划数据！");
				m.put("code", true); 
				m.put("msg","没有满足查询条件的设备数据！");
			}else{
				m.put("sbxx", sbxxlist);
			}
			
			Date d4 = new Date();
			FileLogger.debug("NB02_Qsb查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("设备信息查询 NB02_Qsb----end");
		return m;
	}
	
	
	/**
	 * 
	 * @params where参数
	 * @return 查询拼接where条件后的SQL语句
	 */
	private String processSql(String sybm,String sydw,String sbbh,String sbmc,String xhgg,String lxlb){
		String sql = "";
		String where = "";
		
		where = " 1=1 ";
		if(!CString.isEmpty(sybm)){
			where +=" and sybmbm='"+sybm+"' ";
		} 
		if(!CString.isEmpty(sydw)){
			where +=" and sydw like '%"+sydw+"%' ";
		}
		if(!CString.isEmpty(sbbh)){
			where +=" and sbbh like '%"+sbbh+"%' ";
		} 
		if(!CString.isEmpty(sbmc)){
			where +=" and sbmc like '%"+sbmc+"%' ";
		}  
		if(!CString.isEmpty(xhgg)){
			where +=" and ((sbxh like '%"+xhgg+"%') or (sbgg like '%"+xhgg+"%')) ";
		} 
		if(!CString.isEmpty(lxlb)){
			where +=" and ((lx like '%"+lxlb+"%') or (lb like '%"+lxlb+"%')) ";
		} 
		
		
		sql = 	" SELECT SBBH, "+ //设备编号
		   		"        JDESC "+ //列表显示信息
		        " FROM V_APP_NB_SBJBXX " +
		        " WHERE "+ where +
		        " ORDER BY SBBH ";	
		
		FileLogger.debug("NB02_Qsb where:"+where);	
		return sql;
	}
	
}
