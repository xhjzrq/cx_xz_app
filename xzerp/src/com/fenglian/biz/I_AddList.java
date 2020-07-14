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
public class I_AddList extends  I_Command{
	/**
	 * 通讯录部门明细 查询
	 * @params 查询参数
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * title ：显示标题 （ztitle主标题 stitle 副标题）
	 * addlist ： list 托盘明细列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("通讯录部门明细 AddList----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("title", Collections.EMPTY_LIST); //标题
		m.put("addlist", Collections.EMPTY_LIST); //问题信息
		
		try{
			
			String bmbm = req.getParameter("bmbm");  //部门编码
			

			if(CString.isEmpty(bmbm)){
				throw new AppException("请选择部门！");
			}

			bmbm = bmbm.toUpperCase().trim();
	
			Date d1 = new Date();
			//1、主、副标题  根据WHERE条件
						
			Map title = new HashMap();
									
			//2、通讯录部门明细列表 --  addlist  
			String ssql = processSql(bmbm);	
			
			List addlist =  jt.queryForList(ssql);
			if(addlist.isEmpty()){
				//throw new AppException("没有满足查询条件的计划数据！");
				m.put("code", true); 
				m.put("msg","选择部门没有通讯录数据！");
			}else{
				title.put("ZTITLE", CString.rep(((Map)(addlist.iterator().next())).get("BMMC")));
				title.put("STITLE", "");
				m.put("title", title);
				m.put("addlist", addlist);
			}
			
			Date d4 = new Date();
			FileLogger.debug("AddList 查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("通讯录部门明细 AddList----end");
		return m;
	}
	
	
	/**
	 * 
	 * @params where参数
	 * @param dylx  调用查询类型
	 * @return 查询拼接where条件后的SQL语句
	 */
	private String processSql(String bmbm){
		String sql = "";
		sql = 	"SELECT RYBH, "+  //人员编号
				"		RYXM, "+  //姓名
				"		YDDH, "+  //移动电话
				"		YDDH2, "+  //移动电话2
				"		BGDH, "+  //办公电话
				"		JTDH, "+  //办公电话
				"		EMAIL, "+  //email
				"		EMAIL2, "+  //email
				"		BMBM, "+  //部门编码
				"		BMMC, "+  //部门名称
				"		KSBM, "+  //科室编码
				"		KSZW, "+  //科室职务
				"		GZZZ, "+  //工作职责
				"		BZ, "+  //备注
				"       JDESC "+  //显示信息
		        " FROM V_APP_SYTXL " +
		        " WHERE BMBM='"+ bmbm +"' "+
		        " ORDER BY PX,RYXM ";	
		
		//FileLogger.debug("JJ03_Qdj where:"+where);	
		//FileLogger.debug("JJ03_Qdj sql:"+sql);
		return sql;
	}
	
}
