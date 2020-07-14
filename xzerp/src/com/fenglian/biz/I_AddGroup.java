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
public class I_AddGroup extends  I_Command{
	/**
	 * 质量反馈查询 查询
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * addgroup ： list 通讯录分组信息
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("通讯录分组查询 AddGroup----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("title", Collections.EMPTY_LIST); //标题
		m.put("addgroup", Collections.EMPTY_LIST); //问题信息
		
		try{
			
			Date d1 = new Date();
			//1、主、副标题  
			Map title = new HashMap();
			title.put("ZTITLE","");
			title.put("STITLE","");
			m.put("title", title);
			//2、通讯录group列表 --  addgroup 
			String ssql = " SELECT BMBM, "+
				          " BMMC+'('+CONVERT(VARCHAR(20),COUNT(RYBH))+')' AS JDESC "+
				          " FROM V_APP_SYTXL "+
				          " GROUP BY BMBM,BMMC "+
				          " ORDER BY BMBM ";	
			
			List addgroup =  jt.queryForList(ssql);
			if(addgroup.isEmpty()){
				//throw new AppException("没有满足查询条件的计划数据！");
				m.put("code", true); 
				m.put("msg","没有通讯录数据！");
			}else{
				m.put("addgroup", addgroup);
			}
			
			Date d4 = new Date();
			FileLogger.debug("AddGroup 查询总时间："+(d4.getTime() - d1.getTime())+"ms" );	
			
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("通讯录分组查询 AddGroup----end");
		return m;
	}
	
}
