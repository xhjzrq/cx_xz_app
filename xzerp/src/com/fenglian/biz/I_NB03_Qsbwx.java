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
public class I_NB03_Qsbwx extends  I_Command{
	/**
	 * 设备维修信息查询
	 * @params 查询参数
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * title ：显示标题 （ztitle主标题 stitle 副标题）
	 * sbwx ： list 设备维修信息列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("设备信息查询 NB03_Qsbwx----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("title", Collections.EMPTY_LIST); //标题
		m.put("sbwx", Collections.EMPTY_LIST); //设备维修信息
		
		try{
			String ksrq = req.getParameter("ksrq");  //开始日期
			String jsrq = req.getParameter("jsrq");  //结束日期
			String sbdw = req.getParameter("sbdw");  //申报部门
			String sbry = req.getParameter("sbry");  //申报人员
			String sbbh = req.getParameter("sbbh");  //设备编号
			String sbmc = req.getParameter("sbmc");  //设备名称
			String dwmc = "";
		
			if(CString.isEmpty(ksrq)){
				throw new AppException("请选择开始日期！");
			}
			if(CString.isEmpty(jsrq)){
				throw new AppException("请选择结束日期！");
			}
			if(CString.isEmpty(sbdw)){
				sbdw="";
			}
			if(CString.isEmpty(sbry)){
				sbry="";
			}
			if(CString.isEmpty(sbbh)){
				sbbh="";
			}
			if(CString.isEmpty(sbmc)){
				sbmc="";
			}
			
			ksrq = ksrq.trim();
			jsrq = jsrq.trim();
			sbdw = sbdw.trim();
			sbry = sbry.trim();
			sbbh = sbbh.toUpperCase().trim();
			sbmc = sbmc.trim();
			
			Date d1 = new Date();
			//1、主、副标题  根据WHERE条件
						
			if (!sbdw.equals("")){
				String dwsql="SELECT BMMC FROM SYBMXX WHERE BMBM='"+sbdw+"'";
				Iterator it = jt.queryForList(dwsql).iterator();
				if(it.hasNext()){
					Map bm = (Map)it.next();
					dwmc = CString.rep(bm.get("BMMC")); 
				}else{
					throw new AppException("选择单位无效！");
				}
			}
			
			String ztitle = CString.rep(ksrq+" - "+jsrq);
			String stitle = CString.rep((CString.isEmpty(dwmc)?"":"申报单位:"+dwmc)+(CString.isEmpty(sbry)?"":" "+sbry));
									
			Map title = new HashMap();
			title.put("ZTITLE", ztitle);
			title.put("STITLE", stitle);
			m.put("title", title);
						
			//2、设备维修记录列表 --  ryxx  
			String ssql = processSql(ksrq,jsrq,sbdw,sbry,sbbh,sbmc);	
			
			List wxxxlist =  jt.queryForList(ssql);
			if(wxxxlist.isEmpty()){
				//throw new AppException("没有满足查询条件的计划数据！");
				m.put("code", true); 
				m.put("msg","改设备无维修记录！");
			}else{
				m.put("sbwx", wxxxlist);
			}
			
			Date d4 = new Date();
			FileLogger.debug("NB03_Qsbwx查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("设备信息查询 NB03_Qsbwx----end");
		return m;
	}
	
	
	/**
	 * 
	 * @params where参数
	 * @return 查询拼接where条件后的SQL语句
	 */
	private String processSql(String ksrq,String jsrq,String sbdw,String sbry,String sbbh,String sbmc){
		String sql = "";
		String where = "";
		
		where = " sbrq>='"+ksrq+"' and sbrq<='"+jsrq+"' ";
		if(!CString.isEmpty(sbdw)){
			where +=" and sbdwbm='"+sbdw+"' ";
		} 
		if(!CString.isEmpty(sbry)){
			where +=" and sbry like '%"+sbry+"%' ";
		}
		if(!CString.isEmpty(sbbh)){
			where +=" and sbbh like '%"+sbbh+"%' ";
		} 
		if(!CString.isEmpty(sbmc)){
			where +=" and sbmc like '%"+sbmc+"%' ";
		}  
				
		sql = 	" SELECT SBID, "+ //申报ID
				"		 SBDH, "+ //申报单号
				"		 SBDWBM, "+ //申报单位编码
				"		 SBDWMC, "+ //申报单位名称
				"		 SBRY, "+ //申报人员
				"		 SBRQ, "+ //申报日期
				"		 SBBH, "+ //设备编号
				"		 SBMC, "+ //设备名称
				"		 WXSX, "+ //维修事项
				"		 GZMS, "+ //故障描述
				"		 SYDW, "+ //使用单位
				"		 AZQY, "+ //安装区域
				"		 CZZ, "+ //操作人员
				"		 CZZDH, "+ //操作人电话
				"		 TJRYID, "+ //提交人员ID
				"		 WXNR, "+ //维修内容
				"		 WXDW, "+ //维修单位
				"		 WXRY, "+ //维修人员
				"		 WXSJ, "+ //维修时间
				"		 LXDH, "+ //维修人联系电话
				"		 QRRY, "+ //确认人员
				"		 QRRQ, "+ //确认日期
				"		 WXJG, "+ //维修结果
				"		 JSRY, "+ //接收人员
				"		 JSRQ, "+ //接收日期
				"		 ZT, "+   //状态
		   		"        JDESC "+ //列表显示信息
		        " FROM V_APP_NB_SBWXXX " +
		        " WHERE "+ where +
		        " ORDER BY SBRQ DESC,ID DESC,SBBH DESC";	
		
		FileLogger.debug("NB03_Qsbwx where:"+where);	
		return sql;
	}
	
}
