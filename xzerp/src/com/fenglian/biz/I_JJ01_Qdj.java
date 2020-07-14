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
public class I_JJ01_Qdj extends  I_Command{
	/**
	 * 扫码配送接收 查询单据
	 * @where WHERE条件  sscj=? and czbh=? and psdh=?
	 * @bmbm  BMBM 用户隶属部门
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * title ：显示标题 （ztitle主标题 stitle 副标题）
	 * bjmx ： list 部件明细列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("扫码配送接收 主查询 JJ01_Qdj----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("title", Collections.EMPTY_LIST); //标题
		m.put("bjmx", Collections.EMPTY_LIST); //部件明细
		
		try{
			String wh = req.getParameter("where");
			String bmbm = req.getParameter("dwbm");
			String bmsql = "";
			String ssql = "";
			
			//FileLogger.debug("扫码配送接收 dwbm---"+bmbm);

			if(CString.isEmpty(wh)){
				throw new AppException("where条件为空！");
			}
			if(CString.isEmpty(bmbm)){
				bmbm="";
			}

			if(wh.trim().equals("1")){
				wh=" sscj='01' and czbh='T300K-80#' and psdh='1712000569' ";
			}
			
			bmbm = bmbm.trim();
			if(bmbm.equals("01") | bmbm.equals("02") | bmbm.equals("03") | bmbm.equals("05")){
				bmsql=" AND SSCJ='"+bmbm+"' ";
			}
			if(bmbm.length()>2){
				bmsql=" AND XQDWBM='"+bmbm+"' ";
			}
			
			Date d1 = new Date();
			//1、主、副标题  根据单据第一条记录返回 单据序号 0001
//			String sql = "SELECT ZTITLE,STITLE " +
//					"FROM V_APP_JJ_PSMXB  " +
//					"WHERE "+ wh + " AND PSXH='0001' ";	
			
			String sql = "SELECT TOP 1 ZTITLE,STITLE " +
			"FROM V_APP_JJ_PSMXB  " +
			"WHERE "+ wh + bmsql +" " +	
			"ORDER BY PSXH ";
			
			Iterator it = jt.queryForList(sql).iterator();
			if(it.hasNext()){
				Map title = (Map)it.next();
				m.put("title", title);
			}else{
				throw new AppException("单据不存在或非本单位单据！");
			}
			Date d2 = new Date();
			FileLogger.debug("JJ01_Qdj查询标题时间："+(d2.getTime() - d1.getTime())+"ms" );	
			
			Date d3 = new Date();
			//2、单据部件明细 -- 部件明细  bjmx   
			ssql = " SELECT SSCJ, "+ //所属车间 
				   "    CZBH, "+ //工程编号 
				   "    PSDH, "+ //配送单号
				   "    PSXH, "+ //配送单序号
				   "    TPBH, "+ //托盘表号
				   "    BJDH, "+ //部件代号
				   "    BJMC, "+ //部件名称
				   "    RTRIM(LTRIM(ISNULL(BJGG,'') + ' '+ISNULL(BJCZ,'')+ ' '+ISNULL(BMCL,'')+ ' '+ISNULL(TZDM,''))) AS GGCZ, "+ //规格材质+管子表面处理
				   "    PSSL, "+ //配送数量
				   "    PSBZ, "+ //配送备注
				   "    PSZT, "+ //配送状态
				   "    SJJSR,  "+ //实际接收人
				   "    SJJSRQ, "+ //实际接收日期
				   "    JSSL,  "+ //接收数量
				   "    WTLX, "+ //问题类型
				   "    WTSL, "+ //问题件数量
				   "    WTMS, "+ //问题描述
				   "    JDESC,  "+ //列表部件显示信息
				   "    (CASE WHEN PSZT='99' THEN 2 WHEN PSZT='10' THEN 2 ELSE 0 END) AS HXBS "+ //回写标识
                   " FROM V_APP_JJ_PSMXB " +
                   " WHERE "+ wh +
                   " ORDER BY SSCJ,CZBH,PSDH,TPBH,BJDH,BJMC,BJGG ";
			List bjmxlist =  jt.queryForList(ssql);
			if(bjmxlist.isEmpty()){
				throw new AppException("单据不存在！");
			}else{
				m.put("bjmx", bjmxlist);
			}
			
			Date d4 = new Date();
			FileLogger.debug("JJ01_Qdj查询明细时间："+(d4.getTime() - d3.getTime())+"ms" );
			FileLogger.debug("JJ01_Qdj查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("扫码配送接收 主查询 JJ01_Qdj----end");
		return m;
	}
	
}
