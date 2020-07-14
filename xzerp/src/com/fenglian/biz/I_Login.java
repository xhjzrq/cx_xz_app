package com.fenglian.biz;

import java.util.ArrayList;
import java.util.Collections;
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
public class I_Login extends  I_Command{
	/**
	 * @param yhdm 用户名 
	 * @param pwd 密码
	 * @return json
	 * code : 标识码 true 为正常反之false
	 * msg : 错误信息 将会返回给用户
	 * user ：用户基本
	 * modulelist : list 用户权限编码组
	 * cwly ：list 问题类型列表
	 * bmxx ：list 部门信息列表
	 */
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("登录接口----start");	
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8");  
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("ver", ""); // 最新版本号
		m.put("apkurl", ""); // apk升级地址
		m.put("downloadurl", ""); // apk下载地址
		m.put("user", Collections.EMPTY_LIST); //1、用户信息
//		m.put("modulelist", Collections.EMPTY_LIST); //2、用户权限--模块
		m.put("funlist", Collections.EMPTY_LIST); //3、用户权限 -- 模块功能
		m.put("bmxx", Collections.EMPTY_LIST); //4、前方部门信息列表 --根据用户限制
		m.put("sscj", Collections.EMPTY_LIST); //5、舾装所属部门信息列表 --根据用户限制
		m.put("czbh", Collections.EMPTY_LIST); //6、工程列表
		m.put("wtlx", Collections.EMPTY_LIST); //7、问题类型列表
		m.put("fxlx", Collections.EMPTY_LIST); //8、风险类型列表
		m.put("tglx", Collections.EMPTY_LIST); //9、特殊管件类型列表
		m.put("sscj_sb", Collections.EMPTY_LIST); //10、舾装所属部门信息列表_设备 --根据用户限制
		
		try{
			String yhdm = req.getParameter("yhdm");
			String pwd = req.getParameter("pwd");
			
			String ssql = "";
			String yhlx = ""; ////账户类型 01-管理人员 10-业务人员
			String bmls = ""; //部门类型 01-前方单位 02-舾装单位
			String yhbm = "";
			
			if(CString.isEmpty(yhdm)){
				throw new AppException("用户名为空！");
			}
			
			if(CString.isEmpty(pwd)){
				throw new AppException("密码为空！");
			}
			
//			yhdm=yhdm.toUpperCase();
			
			//1、用户信息 user  用户基本信息
			String sql = "SELECT SYUSERP.YHDM," + //用户账号
					"ISNULL(SYUSERP.YHMS,'') AS YHMS," +  //用户姓名
					"ISNULL(SYUSERP.LXDH,'') AS LXDH," +  //联系电话
					"ISNULL(SYUSERP.YHLX,'10') AS YHLX," +  //用户类型 01-管理人员 10 业务人员
					"ISNULL(SYBMXX.BMBM,'') AS BMBM," +   //部门编码
					"ISNULL(SYBMXX.BMLS,'02') AS BMLS," +  //部门隶属 01-前方单位 02-舾装公司
					"ISNULL(SYBMXX.BMMC,'') AS BMMC " +   //部门名称
					"FROM SYUSERP JOIN SYBMXX ON(SYUSERP.BMBM = SYBMXX.BMBM) " +
					"WHERE SYUSERP.ZXBS='N' And SYUSERP.YHDM=? And SYUSERP.PWD=?";
			Iterator it = jt.queryForList(sql,new Object[]{yhdm.toUpperCase(),Encrypt.md5(pwd).toLowerCase()}).iterator();
			if(it.hasNext()){
				Map user = (Map)it.next();
				m.put("user", user);
				
				yhlx = CString.rep(user.get("YHLX")); 
				bmls = CString.rep(user.get("BMLS")); 
				yhbm = CString.rep(user.get("BMBM")); 
				
			}else{
				throw new AppException("用户名或者密码错误！");
			}
			
			
			//2、APP最新版本号及下载地址
			String versql = "SELECT ISNULL(MC,'') AS VER,ISNULL(BZ,'') AS APKURL,ISNULL(YLZD1,'') AS DWNURL FROM DM_CSBMB WHERE BMLB='APPB' AND BM='01' "; //系统参数表中APP版本号
			Iterator itver = jt.queryForList(versql).iterator();
			if(itver.hasNext()){
				Map mver = (Map)itver.next();
				m.put("ver", CString.rep(mver.get("VER")));
				m.put("apkurl", CString.rep(mver.get("APKURL")));
				m.put("downloadurl", CString.rep(mver.get("DWNURL")));
			}else{
				m.put("ver", "");
				m.put("apkurl", "");
				m.put("downloadurl", "");
			}
			
//			//2、用户权限--模块 modulelist  用户有权限的模块
//			ssql = " SELECT DISTINCT "+
//				   " 		SYFUNP.MKBM as MKBM, "+ //模块编码
//				   "		SYFUNP.MKMC as MKMC  "+ //模块名称
//				   " FROM SYUFRP JOIN SYFUNP on (SYUFRP.ZXTBM = SYFUNP.ZXTBM and "+
//				   "                            SYUFRP.MKBM = SYFUNP.MKBM and "+
//				   "                            SYUFRP.GNMC = SYFUNP.GNMC and "+
//				   "                            SYUFRP.GNLB = SYFUNP.GNLB ) "+
//				   " WHERE SYFUNP.ZXTBM='AP' and SYFUNP.GNLB='' and SYUFRP.YHDM='"+yhdm+"' "+
//				   " ORDER BY SYFUNP.MKBM,SYFUNP.MKMC " ;	
//			//List list = jt.queryForList("select * from SYUFRP where yhdm=?" ,new Object[]{yhdm.toUpperCase()});
//			List listmodule =  jt.queryForList(ssql);
//			if(listmodule.isEmpty()){
//				throw new AppException("用户“"+yhdm+"”无模块访问权限请联系管理员！");
//			}else{
//				m.put("modulelist", listmodule);
//			}
			
			//3、用户权限 -- 模块功能  funlist   用户有权限的模块及模块下功能
			ssql =  " SELECT 	SYFUNP.MKBM as MKBM, " +
					"           SYFUNP.GNXH as GNXH, "+ //功能序号（字符型JJ01、JJ02...）
					"	        SYFUNP.GNMC as GNMC, "+ //功能名称
					"			ISNULL(SYFUNP.GNMS,'') as GNMS, "+ //功能描述（）
					"			ISNULL(SYFUNP.GNDZ,'') as GNDZ, "+ //功能动作（）
					"			ISNULL(SYFUNP.GNCS,'') as GNCS, "+ //功能参数（）
					"			ISNULL(SYFUNP.GNTP,'') as GNTP  "+ //功能图片（）
					"	FROM SYUFRP JOIN SYFUNP on (SYUFRP.ZXTBM = SYFUNP.ZXTBM and "+ 
					"	                            SYUFRP.MKBM = SYFUNP.MKBM and "+ 
					"	                            SYUFRP.GNMC = SYFUNP.GNMC and "+ 
					"	                            SYUFRP.GNLB = SYFUNP.GNLB ) "+ 
					"	WHERE SYFUNP.ZXTBM='AP' and SYFUNP.GNLB='' and  "+ 
					"	      SYUFRP.YHDM='"+yhdm+"' "+ 
					"	ORDER BY SYFUNP.MKBM,SYFUNP.GNXH " ;
			//List list = jt.queryForList("select * from SYUFRP where yhdm=?" ,new Object[]{yhdm.toUpperCase()});
			List listfun =  jt.queryForList(ssql);
			if(listfun.isEmpty()){
				throw new AppException("用户“"+yhdm+"”无模块功能访问权限，请联系管理员！");
			}else{
				m.put("funlist", listfun);
			}
			
		    //4、前方部门信息列表 bmxx --根据用户限制   只有1条记录的时候 查询计划等业务数据时 默认限制前方部门
			//编码BM ；名称 MC
			//需要修改 可以做出根据用户类型显示他可以查看的部门信息
			if (bmls.equals("01")){
				ssql = "SELECT BMBM AS BM,BMMC AS MC FROM SYBMXX WHERE BMLS='01' AND BMBM='"+yhbm+"' ORDER BY BMBM ";
			}else{
				ssql = "SELECT BMBM AS BM,BMMC AS MC FROM SYBMXX WHERE BMLS='01' ORDER BY BMBM ";	
				}
			List listbmxx = jt.queryForList(ssql);
			if(listbmxx.isEmpty()){
				throw new AppException("用户“"+yhdm+"”无前方部门问权限，请联系管理员！");
			}else{
				m.put("bmxx", listbmxx);
			}
			
			//5、舾装所属部门信息列表 sscj --根据用户限制   舾装公司业务部门需要限制 只有1条记录的时候 查询计划等业务数据时 默认限制所属车间
			//编码BM ；名称 MC
//			if (bmls.equals("02") & !yhbm.equals("00")){
//				ssql = "SELECT BMBM AS BM,BMMC AS MC FROM SYBMXX WHERE BMLS='02' AND BMBM<>'00' AND BMBM='"+yhbm+"' ORDER BY BMBM ";
//			}else{
//				ssql = "SELECT BMBM AS BM,BMMC AS MC FROM SYBMXX WHERE BMLS='02' AND BMBM<>'00' ORDER BY BMBM ";	
//				}
			
			if (bmls.equals("02") & (yhbm.equals("01") | yhbm.equals("02") | yhbm.equals("03") | yhbm.equals("05") )){
				ssql = "SELECT BMBM AS BM,BMMC AS MC FROM SYBMXX WHERE BMLS='02' AND BMBM<>'00' AND BMBM='"+yhbm+"' ORDER BY BMBM ";
			}else{
				ssql = "SELECT BMBM AS BM,BMMC AS MC FROM SYBMXX WHERE BMLS='02' AND BMBM in ('01','02','03','05') ORDER BY BMBM ";	
				}
			
			List listsscj = jt.queryForList(ssql);
			if(listsscj.isEmpty()){
				throw new AppException("用户“"+yhdm+"”无舾装部门问权限，请联系管理员！");
			}else{
				m.put("sscj", listsscj);
			}
			
			//6、工程列表 czbh
			//编码BM ；名称 MC
			ssql = "SELECT CZBH AS BM,CZBH AS MC FROM DM_CZJBB WHERE ISNULL(WGBS,'N')<>'Y' ORDER BY WGBS,CZBH ";
			List listczbh = jt.queryForList(ssql);
			m.put("czbh", listczbh);

			
			/**错误类型列表是字典需要定义**/
			//7、问题类型列表 wtlx
			//编码BM ；名称 MC
			ssql = "SELECT BM ,MC FROM DM_CSBMB WHERE BMLB='WTLX' ORDER BY BM ";
			List listwtlx = jt.queryForList(ssql);
			m.put("wtlx", listwtlx);
			
			//8、风险类型列表 fxlx
			//编码BM ；名称 MC
			ssql = "SELECT BM ,MC FROM DM_CSBMB WHERE BMLB='FXLX' ORDER BY BM ";
			List listfxlx = jt.queryForList(ssql);
			m.put("fxlx", listfxlx);
			
			//9、特殊管件类型列表 tglx
			//编码BM ；名称 MC
			ssql = "SELECT BM ,MC FROM DM_CSBMB WHERE BMLB='TGLX' ORDER BY BM ";
			List listtglx = jt.queryForList(ssql);
			m.put("tglx", listtglx);
			
			//10、舾装所属部门信息列表_设备 sscj_sb --根据用户限制   舾装公司业务部门需要限制 只有1条记录的时候 查询计划等业务数据时 默认限制所属车间
			//编码BM ；名称 MC
			if (bmls.equals("02") & !(yhbm.equals("00")|(yhbm.equals("08") & yhlx.equals("01")))){
				ssql = "SELECT BMBM AS BM,BMMC AS MC FROM SYBMXX WHERE BMLS='02' AND BMBM<>'00' AND BMBM='"+yhbm+"' ORDER BY BMBM ";
				FileLogger.debug("限制");
			}else{
				ssql = "SELECT BMBM AS BM,BMMC AS MC FROM SYBMXX WHERE BMLS='02' AND BMBM<>'00' ORDER BY BMBM ";	
				FileLogger.debug("不限制");
				}
			
			List listsscj_sb = jt.queryForList(ssql);
			if(listsscj_sb.isEmpty()){
				throw new AppException("用户“"+yhdm+"”无舾装设备部门问权限，请联系管理员！");
			}else{
				m.put("sscj_sb", listsscj_sb);
			}
			
			
			//最好改成从数据库读取字典
			/*
			List listcwlx = new ArrayList();
			Map o1 = new HashMap();
			o1.put("key", "key1");
			o1.put("value", "问题描述1");
			listcwlx.add(o1);
			
			Map o2 = new HashMap();
			o2.put("key", "key2");
			o2.put("value", "问题描述2");
			listcwlx.add(o2);
			m.put("cwly", listcwlx);
			*/
			/**错误类型列表是字典需要定义**/
			
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("登录接口----end");
		return m;
	}
	
}
