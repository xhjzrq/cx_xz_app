package com.fenglian.biz;

import java.io.File;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.util.AppException;

import com.fenglian.command.I_Command;
import com.fenglian.tools.logger.FileLogger;
import com.fenglian.tools.util.CString;
import com.fenglian.tools.util.Msg2Zip;
import com.fenglian.tools.util.Msg2Zip.ReadFileLine;

public class I_UpJJ01 extends I_Command {
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("配送接收接口----start");
		// //浏览器中测试返回中文编码格式
		res.setHeader("Content-Type", "text/html;charset=utf-8");

		Map m = new HashMap();
		m.put("code", true); // 接口正常标识
		m.put("msg", ""); // 错误信息
		long start=System.currentTimeMillis();
		try {
			String path = getFilepath();
			System.out.println("上传文件保存路径为："+path);
			int dotindex=path.lastIndexOf(".");
			String destDir="";
			if(dotindex<path.length()){
				destDir=path.substring(0, dotindex);
			}
			System.out.println("destdir："+destDir);
			
			Msg2Zip.unZip(destDir, path);
			
			Map map=new HashMap();
			List list =  new ArrayList();
			Statement statement=null;
			////processData(destDir+File.separator+"temp.txt", sqllist,map, statement);
			updateData(destDir,list,map,statement);
			
			/**删除备份文件*/
			Msg2Zip.deleteBackUp(destDir);
			Msg2Zip.deleteBackUp(path);
			/**删除备份文件*/
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			m.put("code", false); // 接口正常标识
			m.put("msg", e.getMessage()); // 错误信息
		}
		long end=System.currentTimeMillis();
		m.put("duringtime", end-start);
		return m;
	}
	
	/**
	 * 
	 * @param dest 解析文件地址
	 * @param list 生成的sql
	 * @param where where条件
	 * @param st 代用
	 * @throws Exception
	 */
	private void processData(String dest,final List list,final Map where,final Statement st) throws Exception{
		Msg2Zip.readfile(dest, new ReadFileLine(){
			public void readLine(Map data) throws Exception {
				if(data!=null){
					if(data.containsKey("where")){
						where.putAll(data);
					}else{
						list.add(data);
					}
				}
			}
		});
	}
	
	
	/**
	 * 更新数据库
	 * @param dest 解析文件地址
	 * @param list 生成的sql
	 * @param map where条件
	 * @throws Exception
	 */
	private void updateData(String destDir,List list,Map map,Statement st) throws Exception{
		try{
			//得到传入数据
			processData(destDir+File.separator+"temp.txt",list,map,st);
			
			//更新数据
			List updatesql = new ArrayList();
			Map mx = new HashMap();
            String where="";
            String usql="";
            
            where = CString.rep(map.get("where"));
            if (where.equals("1")){
            	where=" SSCJ='01' and CZBH='T300K-80#' and PSDH='1712000569' ";
            }
            
            
            ////更新配送明细表 APP_JJ_PSMXB
            for (Object object : list){
            	mx = (Map)object;

            	if ("".equals(CString.rep(mx.get("WTLX")))){
            		usql ="UPDATE APP_JJ_PSMXB "+
		              "SET PSZT='"+CString.rep(mx.get("PSZT"))+"', "+
		              "    JSSL="+CString.rep(mx.get("JSSL"))+", "+
		              "    SJJSR='"+CString.repForNull(CString.rep(mx.get("SJJSR")),CString.rep(map.get("sjjsr")))+"', "+
		              "    SJJSRQ='"+CString.repForNull(CString.rep(mx.get("SJJSRQ")),CString.rep(map.get("sjjsrq")))+"', "+
		              "    WTLX='', "+
		              "    WTSL=0, "+
		              "    WTMS='', "+
		              "    WTZT='', "+
		              "    WTRY='', "+
		              "    WTRQ='' "+
		              "WHERE "+where +" AND PSXH='"+CString.rep(mx.get("PSXH"))+"' ";
            	}else{
            		usql ="UPDATE APP_JJ_PSMXB "+
		              "SET PSZT='"+CString.rep(mx.get("PSZT"))+"', "+
		              "    JSSL="+CString.rep(mx.get("JSSL"))+", "+
		              "    SJJSR='"+CString.repForNull(CString.rep(mx.get("SJJSR")),CString.rep(map.get("sjjsr")))+"', "+
		              "    SJJSRQ='"+CString.repForNull(CString.rep(mx.get("SJJSRQ")),CString.rep(map.get("sjjsrq")))+"', "+
		              "    WTLX='"+CString.rep(mx.get("WTLX"))+"', "+
		              "    WTSL="+CString.rep(mx.get("WTSL"))+", "+
		              "    WTMS='"+CString.rep(mx.get("WTMS"))+"', "+
		              "    WTZT='02', "+
		              "    WTRY='"+CString.repForNull(CString.rep(mx.get("SJJSR")),CString.rep(map.get("sjjsr")))+"', "+
		              "    WTRQ='"+CString.repForNull(CString.rep(mx.get("SJJSRQ")),CString.rep(map.get("sjjsrq")))+"' "+
		              "WHERE "+where +" AND PSXH='"+CString.rep(mx.get("PSXH"))+"' ";
            	}
            	updatesql.add(usql);
            }
			
            ////更新配送计划表 APP_JJ_PSJHB
            where = where.toUpperCase();
            where = where.replace("SSCJ", "APP_JJ_PSMXB.SSCJ");
            where = where.replace("CZBH", "APP_JJ_PSMXB.CZBH");
            where = where.replace("PSDH", "APP_JJ_PSMXB.PSDH");
            usql = " UPDATE APP_JJ_PSJHB "+
		           " SET  APP_JJ_PSJHB.JHZT = (CASE WHEN ISNULL(APP_JJ_PSMXB.PSZT,'01')='10' THEN '10' ELSE APP_JJ_PSJHB.JHZT END), "+
		           " 	  APP_JJ_PSJHB.PSZT = APP_JJ_PSMXB.PSZT, "+
		           "      APP_JJ_PSJHB.JSSL = APP_JJ_PSMXB.JSSL, "+
		           "      APP_JJ_PSJHB.SJJSR = APP_JJ_PSMXB.SJJSR, "+
		           "      APP_JJ_PSJHB.SJJSRQ = APP_JJ_PSMXB.SJJSRQ, "+
		           "      APP_JJ_PSJHB.WTBS = (CASE WHEN ISNULL(APP_JJ_PSMXB.WTLX,'')<>'' THEN 'Y' ELSE '' END), "+
		           "      APP_JJ_PSJHB.WTLX = APP_JJ_PSMXB.WTLX, "+
		           "      APP_JJ_PSJHB.WTMS = APP_JJ_PSMXB.WTMS "+
			       " FROM APP_JJ_PSMXB "+
			       " WHERE APP_JJ_PSJHB.SSCJ = APP_JJ_PSMXB.SSCJ AND "+
			       "      APP_JJ_PSJHB.CZBH = APP_JJ_PSMXB.CZBH AND "+
			       "      APP_JJ_PSJHB.JHBH = APP_JJ_PSMXB.JHBH AND "+where;

        	updatesql.add(usql);
            
            ////批量更新
            if(updatesql.isEmpty()){
				throw new AppException("没有更新的明细数据！");
			}else{
				jt.batchUpdate((String[])updatesql.toArray(new String[0]));
			}
            
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
