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
public class I_NB02_Qsbmx extends  I_Command{
	/**
	 * 设备信息 查询
	 * @params 查询参数
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * sbxx ： map  设备信息
	 * wxxx ： list 设备维修信息列表
	 * bgxx ： list 设备变更信息列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("设备信息查询 NB02_Qwx----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("sbxx", Collections.EMPTY_LIST); //设备基本信息
		m.put("wxxx", Collections.EMPTY_LIST); //设备维修信息信息
		//m.put("bgxx", Collections.EMPTY_LIST); //设备变更信息信息
		
		try{
			
			String sbbh = req.getParameter("sbbh");  //设备编号
			String ywlx = req.getParameter("ywlx");  //业务类型

			if(CString.isEmpty(sbbh)){
				throw new AppException("请传入设备编号！");
			}
			if(CString.isEmpty(ywlx)){
				ywlx="01";
			}
			
			sbbh = sbbh.toUpperCase().trim();
			ywlx = ywlx.toUpperCase().trim();
			
			Date d1 = new Date();
			
			//1、sbxx 设备基本信息
			
			if (!sbbh.equals("")){
				String sbsql=" SELECT SBBH, "+ //编号
				            "        SBMC, "+ //名称
							"        SBZT, "+ //状态
							"        SBXH, "+ //型号
							"        SBGG, "+ //规格
							"        CCBH, "+ //出厂编号
							"        CCNY, "+ //出厂年月
							"        SYNY, "+ //始用年月
							"        NYNX, "+ //耐用年限					
							"        LXLB, "+ //类型类别
							"        LJ, "+ //类级
							"        ZZC, "+ //制造厂商
							"        SYBMBM, "+ //使用部门编码
							"        SYBMMC, "+ //使用部门名称
							"        SYDW, "+ //使用单位
							"        AZQY "+ //安装区域
					        " FROM V_APP_NB_SBJBXX " +
					        " WHERE  SBBH='"+sbbh+"' ";  //只查询设备编号

				Iterator it = jt.queryForList(sbsql).iterator();
				if(it.hasNext()){
					Map sb = (Map)it.next();
					m.put("sbxx", sb);
				}else{
					throw new AppException("找不到设备信息！");
				}
			}
			
			////同时查询设备维修、变更记录
			if (ywlx.equals("02")){		
				
				//2、设备维修信息列表 --  wxxx  
				String ssql = processSql(sbbh);	
				
				List wxxxlist =  jt.queryForList(ssql);
				if(wxxxlist.isEmpty()){
					//throw new AppException("没有满足查询条件的计划数据！");
					m.put("code", true); 
					m.put("msg","");
				}else{
					m.put("wxxx", wxxxlist);
				}
				
				/*
				//3、设备变更信息列表 --  bgxx  
				String bsql = processSql_bg(sbbh);	
				
				List bgxxlist =  jt.queryForList(bsql);
				if(bgxxlist.isEmpty()){
					//throw new AppException("没有满足查询条件的计划数据！");
					m.put("code", true); 
					m.put("msg","");
				}else{
					m.put("bgxx", bgxxlist);
				}
				*/
			}
			
			Date d4 = new Date();
			FileLogger.debug("NB02_Qwx查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("设备信息查询 Nb02_Qwx----end");
		return m;
	}
	
	
	/**
	 * 
	 * @params where参数
	 * @return 查询拼接where条件后的SQL语句
	 */
	private String processSql(String sbbh){
		String sql = "";
		String where = "";
		
		//查询已提交维修数据
		where = " ZT>'01' ";
		
		if(!CString.isEmpty(sbbh)){
			where +=" AND SBBH = '"+sbbh+"' ";
		} 
		
		sql = 	" SELECT SBID, "+ //SBID 
				"        SBBH, "+ //
		   		"        JDESC1 AS JDESC "+ //
		        " FROM V_APP_NB_SBWXXX " +
		        " WHERE "+ where +
		        " ORDER BY SBBH,SBRQ DESC,ID DESC,SBID DESC";	
		
		FileLogger.debug("Nb02_Qwx where:"+where);	
		return sql;
	}
	
	/**
	 * 
	 * @params where参数
	 * @return 查询拼接where条件后的SQL语句
	 */
	/*
	private String processSql_bg(String sbbh){
		String sql = "";
		String where = "";
		
		//查询已提交变更数据
		where = " ZT>'01' ";
		
		if(!CString.isEmpty(sbbh)){
			where +=" AND SBBH = '"+sbbh+"' ";
		} 
		
		sql = 	" SELECT BGID, "+ //SBID 
				"        SBBH, "+ //
		   		"        JDESC "+ //
		        " FROM V_APP_NB_SBBGXX " +
		        " WHERE "+ where +
		        " ORDER BY SBBH,SQSJ DESC,ID,BGID ";	
		
		FileLogger.debug("Nb02_Qwx where:"+where);	
		return sql;
	}
	*/
}
