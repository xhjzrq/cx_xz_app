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
public class I_NB01_Qry extends  I_Command{
	/**
	 * 人员资质 查询
	 * @params 查询参数
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * title ：显示标题 （ztitle主标题 stitle 副标题）
	 * ryxx ： list 人员信息列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("人员资质查询 NB01_Qry----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("title", Collections.EMPTY_LIST); //标题
		m.put("ryxx", Collections.EMPTY_LIST); //人员信息
		
		try{
			
			String dwbm = req.getParameter("dwbm");  //单位编码
			String sgdw = req.getParameter("sgdw");  //施工单位
			String rybh = req.getParameter("rybh");  //职号
			String xm = req.getParameter("xm");  //姓名
			String sfzh = req.getParameter("sfzh");  //身份证号
			String hgbh = req.getParameter("hgbh");  //焊工编号
			String xybs = req.getParameter("xybs");  //下月续签标识
			String dqbs = req.getParameter("dqbs");  //3月到期标识
			String dwmc = "";
		

			if(CString.isEmpty(dwbm)){
				dwbm="";
			}
			if(CString.isEmpty(sgdw)){
				sgdw="";
			}
			if(CString.isEmpty(rybh)){
				rybh="";
			}
			if(CString.isEmpty(xm)){
				xm="";
			}
			if(CString.isEmpty(sfzh)){
				sfzh="";
			}
			if(CString.isEmpty(hgbh)){
				hgbh="";
			}
			if(CString.isEmpty(xybs)){
				xybs="";
			}
			if(CString.isEmpty(dqbs)){
				dqbs="";
			}
			
			
			dwbm = dwbm.trim();
			xm = xm.trim();
			rybh = rybh.toUpperCase().trim();
			sfzh = sfzh.toUpperCase().trim();
			hgbh = hgbh.toUpperCase().trim();
			
			Date d1 = new Date();
			//1、主、副标题  根据WHERE条件
			
			
			if (!dwbm.equals("")){
				String dwsql="SELECT BMMC FROM SYBMXX WHERE BMBM='"+dwbm+"'";
				Iterator it = jt.queryForList(dwsql).iterator();
				if(it.hasNext()){
					Map bm = (Map)it.next();
					dwmc = CString.rep(bm.get("BMMC")); 
				}else{
					throw new AppException("选择单位无效！");
				}
			}
			
			String ztitle = CString.rep((CString.isEmpty(dwbm)?"":dwmc)+" 人员列表");
			String stitle = "";
						
			Map title = new HashMap();
			title.put("ZTITLE", ztitle);
			title.put("STITLE", stitle);
			m.put("title", title);
						
			//2、人员信息列表 --  ryxx  
			String ssql = processSql(dwbm,sgdw,rybh,xm,sfzh,hgbh,xybs,dqbs);	
			
			List ryxxlist =  jt.queryForList(ssql);
			if(ryxxlist.isEmpty()){
				//throw new AppException("没有满足查询条件的计划数据！");
				m.put("code", true); 
				m.put("msg","没有满足查询条件的人员数据！");
			}else{
				m.put("ryxx", ryxxlist);
			}
			
			Date d4 = new Date();
			FileLogger.debug("NB01_Qry查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("人员资质查询 Nb01_Qry----end");
		return m;
	}
	
	
	/**
	 * 
	 * @params where参数
	 * @return 查询拼接where条件后的SQL语句
	 */
	private String processSql(String dwbm,String sgdw,String rybh,String xm,String sfzh,String hgbh,String xybs,String dqbs){
		String sql = "";
		String where = "";
		
		where = " 1=1 ";
		if(!CString.isEmpty(dwbm)){
			where +=" and dwbm='"+dwbm+"' ";
		} 
		if(!CString.isEmpty(sgdw)){
			where +=" and dw like '%"+sgdw+"%' ";
		}
		if(!CString.isEmpty(rybh)){
			where +=" and rybh like '%"+rybh+"%' ";
		} 
		if(!CString.isEmpty(xm)){
			where +=" and xm like '%"+xm+"%' ";
		}  
		if(!CString.isEmpty(sfzh)){
			where +=" and sfzh like '%"+sfzh+"%' ";
		} 
		if(!CString.isEmpty(hgbh)){
			where +=" and hgbh like '%"+hgbh+"%' ";
		} 
		if(xybs.equals("Y")){
			where +=" and ryid in (select distinct hgid " +
					"              from V_APP_NB_HGCZXX " +
					"              where xqrq=(case when  month(DATEADD(MM,1,GETDATE()))<=6 " +
					"                               then  convert(varchar(10),month(DATEADD(MM,1,GETDATE())))+'/'+ convert(varchar(10),month(DATEADD(MM,1,GETDATE()))+6) " +
					"                               else  convert(varchar(10),month(DATEADD(MM,1,GETDATE())) -6)+'/'+ convert(varchar(10),month(DATEADD(MM,1,GETDATE()))) " +
					"                               end) " +
					"              ) ";
		} 
		if(dqbs.equals("Y")){
			where +=" and ryid in (select distinct hgid from V_APP_NB_HGCZXX where datediff(mm,getdate(),yxrq)<=2 and isnull(yxrq,'')<>'' ) ";
		} 
		
		sql = 	" SELECT RYID, "+ //人员ID 
				"        XM, "+ //姓名
				"        SFZH, "+ //身份证号
				"        HGBH, "+ //焊工编号
		   		"        JDESC "+ //列表显示信息
		        " FROM V_APP_NB_HGJBXX " +
		        " WHERE "+ where +
		        " ORDER BY DWBM,XM,HGBH ";	
		
		FileLogger.debug("NB01_Qry where:"+where);	
		return sql;
	}
	
}
