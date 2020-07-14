package com.fenglian.biz;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.util.AppException;

import com.fenglian.command.I_Command;
import com.fenglian.tools.util.*;
import com.fenglian.tools.logger.*;

/**
 * 用户修改密码接口
 */
public class I_Uppwd extends I_Command{
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("修改密码接口----start");
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		
		
		//入参
		String yhdm = req.getParameter("yhdm") ;//用户编码
		String opwd = req.getParameter("opwd") ;//原密码
		String npwd = req.getParameter("npwd") ;//新密码
		String npwd1 = req.getParameter("npwd1") ;//确认密码应该与新密码相同
		//入参
		
		try{
			
			if(CString.isEmpty(opwd)){
				throw new AppException("原密码为空！");
			}
			
			if(CString.isEmpty(npwd)){
				throw new AppException("新密码为空！");
			}
			
			if(CString.isEmpty(npwd1)){
				throw new AppException("确认密码为空！");
			}
			
			if(!npwd.equals(npwd1)){
				throw new AppException("2次新密码不相同！");
			}
			
			if(opwd.equals(npwd)){
				throw new AppException("新密码不能老密码相同！");
			}
			
			String sql = "select count(1) from SYUSERP where YHDM=? and pwd=?" ;
			int i = jt.queryForInt(sql,new Object[]{yhdm.toUpperCase(),Encrypt.md5(opwd).toLowerCase()});
			FileLogger.debug("修改密码接口----I"+i);
			if(i>0){
				//密码正确进行修改密码操作
				jt.update("update SYUSERP set pwd=? where yhdm=?",new Object[]{Encrypt.md5(npwd).toLowerCase(),yhdm.toUpperCase()});
			}else{
				throw new AppException("原密码不正确！");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("修改密码接口----end");
		return m;
	}
}
