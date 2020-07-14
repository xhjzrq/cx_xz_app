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
public class I_NB01_Qcz extends  I_Command{
	/**
	 * 人员持证 查询
	 * @params 查询参数
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * ryxx ： map  人员信息 
	 * czxx ： list 人员持证列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("人员持证查询 NB01_Qcz----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("ryxx", Collections.EMPTY_LIST); //人员基本信息
		m.put("czxx", Collections.EMPTY_LIST); //人员持证信息
		
		try{
			
			String sfzh = req.getParameter("sfzh");  //身份证号
			String hgbh = req.getParameter("hgbh");  //焊工编号
		

			if(CString.isEmpty(sfzh)){
				//throw new AppException("请传入人员身份证号码！");
				sfzh="";
			}
			if(CString.isEmpty(hgbh)){
				throw new AppException("请传入人员焊工编号！");
			}
			
			
			sfzh = sfzh.toUpperCase().trim();
			hgbh = hgbh.toUpperCase().trim();
			
			Date d1 = new Date();
			
			//1、ryxx 人员基本信息
			
			if (!hgbh.equals("")){
				String rysql=" SELECT TOP 1 DWMC AS DWMC, "+ //单位名称
				            "        ISNULL(DW,'') AS SGDW, "+ //施工单位
							"        (ISNULL(XM,'')+' '+ISNULL(XM_PY,'') + (CASE WHEN LEN(RYBH)>0 THEN ' ('+ ISNULL(RYBH,'')+')' ELSE '' END)) AS XM, "+ //姓名 + 拼音 + (职号)
							"        XB AS XB, "+ //姓别
							"        ISNULL(RYBH,'') AS RYBH, "+ //职号
							"        SFZH AS SFZH, "+ //身份证号
							"        HGBH AS HGBH "+ //焊工编号
					        " FROM V_APP_NB_HGJBXX " +
					        " WHERE  HGBH='"+hgbh+"' ";  //只查询焊工编号
					        //" WHERE SFZH='"+ sfzh +"' and hgbh='"+hgbh+"' ";	
				Iterator it = jt.queryForList(rysql).iterator();
				if(it.hasNext()){
					Map ry = (Map)it.next();
					m.put("ryxx", ry);
				}else{
					throw new AppException("找不到人员信息！");
				}
			}
			
						
			//2、人员持证信息列表 --  czxx  
			String ssql = processSql(sfzh,hgbh);	
			
			List czxxlist =  jt.queryForList(ssql);
			if(czxxlist.isEmpty()){
				//throw new AppException("没有满足查询条件的计划数据！");
				m.put("code", true); 
				m.put("msg","没有满足查询条件的持证数据！");
			}else{
				m.put("czxx", czxxlist);
			}
			
			Date d4 = new Date();
			FileLogger.debug("NB01_Qcz查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("人员持证查询 Nb01_Qcz----end");
		return m;
	}
	
	
	/**
	 * 
	 * @params where参数
	 * @return 查询拼接where条件后的SQL语句
	 */
	private String processSql(String sfzh,String hgbh){
		String sql = "";
		String where = "";
		
		where = " 1=1 ";
		
		if(!CString.isEmpty(sfzh)){
			//where +=" and sfzh = '"+sfzh+"' ";  //不用身份证号查询
		} 
		if(!CString.isEmpty(hgbh)){
			where +=" and hgbh = '"+hgbh+"' ";
		} 
		
		sql = 	" SELECT ZJID, "+ //证件ID 
				"        HGZH, "+ //焊工证号
		   		"        JDESC "+ //列表显示信息
		        " FROM V_APP_NB_HGCZXX " +
		        " WHERE "+ where +
		        " ORDER BY HGZH ";	
		
		FileLogger.debug("NB01_Qcz where:"+where);	
		return sql;
	}
	
}
