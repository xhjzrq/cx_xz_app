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
import com.fenglian.tools.util.*;
import com.fenglian.tools.logger.*;

/**
 * 设备维修数据接口 Usbwx
 * 参数 业务类型 ywlx='TJ'-提交 'QR'-确认 'JC'-接收 'GX'-更新 'SC'-删除
 */
public class I_NB03_Usbwx extends I_Command{
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("设备维修 nb03_update----start");
		
		////浏览器中测试返回中文编码格式  
		res.setHeader("Content-Type", "text/html;charset=utf-8"); 
		
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		m.put("wxxx", Collections.EMPTY_MAP); //设备维修插入记录
		
		//入参
		String ywlx = req.getParameter("ywlx") ;//业务类型
		String sbid = req.getParameter("sbid") ;//申报ID
		//新增、更新参数
		String yhdm = req.getParameter("yhdm") ;//用户账号
		String sbdwbm = req.getParameter("sbdwbm") ;//申报单位编码
		String sbdwmc= req.getParameter("sbdwmc") ;//申报单位名称
		String sbry = req.getParameter("sbry") ;//申报人员  用户账号
		String sbrq = req.getParameter("sbrq") ;//申报日期
		String sbbh = req.getParameter("sbbh") ;//设备编号
		String sbmc = req.getParameter("sbmc") ;//设备名称
		String wxsx = req.getParameter("wxsx") ;//维修事项
		String gzms = req.getParameter("gzms") ;//故障描述
		String sydw = req.getParameter("sydw") ;//使用单位
		String azqy = req.getParameter("azqy") ;//安装区域
		String czz = req.getParameter("czz") ;//操作人员
		String czzdh = req.getParameter("czzdh") ;//操作人电话
		////确认参数
		String wxnr = req.getParameter("wxnr") ;//维修内容
		String wxdw = req.getParameter("wxdw") ;//维修单位
		String wxry = req.getParameter("wxry") ;//维修人员
		String wxsj = req.getParameter("wxsj") ;//维修时间
		String lxdh = req.getParameter("lxdh") ;//联系电话
		String qrry = req.getParameter("qrry") ;//确认人员
		String qrrq = req.getParameter("qrrq") ;//确认日期
		////接收参数
		String wxjg = req.getParameter("wxjg") ;//维修效果
		String jsry = req.getParameter("jsry") ;//接收人员
		String jsrq = req.getParameter("jsrq") ;//接收日期
		
		try{
			if(CString.isEmpty(ywlx)){
				throw new AppException("未指定业务类型！");
			}
			if(CString.isEmpty(sbid)){
				sbid = "";
			}
			if(CString.isEmpty(yhdm)){
				yhdm = "";
			}
			if(CString.isEmpty(sbdwbm)){
				sbdwbm = "";
			}
			if(CString.isEmpty(sbdwmc)){
				sbdwmc = "";
			}
			if(CString.isEmpty(sbry)){
				sbry = "";
			}
			if(CString.isEmpty(sbrq)){
				sbrq = "";
			}
			if(CString.isEmpty(sbbh)){
				sbbh = "";
			}
			if(CString.isEmpty(sbmc)){
				sbmc = "";
			}
			if(CString.isEmpty(wxsx)){
				wxsx = "";
			}
			if(CString.isEmpty(gzms)){
				gzms = "";
			}
			if(CString.isEmpty(sydw)){
				sydw = "";
			}
			if(CString.isEmpty(azqy)){
				azqy = "";
			}
			if(CString.isEmpty(czz)){
				czz = "";
			}
			if(CString.isEmpty(czzdh)){
				czzdh = "";
			}
			
			
			if(CString.isEmpty(wxnr)){
				wxnr = "";
			}
			if(CString.isEmpty(wxdw)){
				wxdw = "";
			}
			if(CString.isEmpty(wxry)){
				wxry = "";
			}
			if(CString.isEmpty(wxsj)){
				wxsj = "";
			}
			if(CString.isEmpty(lxdh)){
				lxdh = "";
			}
			if(CString.isEmpty(qrry)){
				qrry = "";
			}
			if(CString.isEmpty(qrrq)){
				qrrq = "";
			}
			
			
			if(CString.isEmpty(wxjg)){
				wxjg = "";
			}
			if(CString.isEmpty(jsry)){
				jsry = "";
			}
			if(CString.isEmpty(jsrq)){
				jsrq = "";
			}
			
			////参数判断
			
			if (ywlx.equals("TJ")){      ////新增提交
				if(CString.isEmpty(yhdm)){
					throw new AppException("用户账号为空！");
				}
				if(CString.isEmpty(sbdwbm)){
					throw new AppException("申报单位为空！");
				}
				if(CString.isEmpty(sbry)){
					throw new AppException("申报人员为空！");
				}
				if(CString.isEmpty(sbrq)){
					throw new AppException("申报日期为空！");
				}
				if(CString.isEmpty(sbbh)){
					throw new AppException("设备编号为空！");
				}
				if(CString.isEmpty(wxsx)){
					throw new AppException("维修事项为空！");
				}
				////新增申报ID
				sbid=CString.rep(System.currentTimeMillis())+"-"+yhdm.toUpperCase().trim();;
			}else if (ywlx.equals("GX")){  ////更新
				if(CString.isEmpty(sbid)){
					throw new AppException("申报ID为空！");
				}
				if(CString.isEmpty(wxsx)){
					throw new AppException("维修事项为空！");
				}
			}else if (ywlx.equals("SC")){  ////删除
				if(CString.isEmpty(sbid)){
					throw new AppException("申报ID为空！");
				}
			}else if (ywlx.equals("QR")){  ////确认
				if(CString.isEmpty(sbid)){
					throw new AppException("申报ID为空！");
				}
				if(CString.isEmpty(wxnr)){
					throw new AppException("维修内容为空！");
				}
			}else if (ywlx.equals("JS")){  ////接收
				if(CString.isEmpty(sbid)){
					throw new AppException("申报ID为空！");
				}
				if(CString.isEmpty(wxjg)){
					throw new AppException("维修效果为空！");
				}
			}else {
				throw new AppException("无效的业务类型！");
			}
			
						
			////处理参数
			yhdm = yhdm.toUpperCase().trim();
			sbdwbm = sbdwbm.toUpperCase().trim();
			sbdwmc = sbdwmc.trim();
			sbry = sbry.trim();
			sbrq = sbrq.trim();
			sbbh = sbbh.toUpperCase().trim();
			sbmc = sbmc.trim();
			wxsx = wxsx.trim();
			gzms = gzms.trim();
			sydw = sydw.trim();
			azqy = azqy.trim();
			czz = czz.trim();
			czzdh = czzdh.trim();
			
			wxnr = wxnr.trim();
			wxdw = wxdw.trim();
			wxry = wxry.trim();
			wxsj = wxsj.trim();
			lxdh = lxdh.trim();
			qrry = qrry.trim();
			qrrq = qrrq.trim();

			wxjg = wxjg.trim();
			jsry = jsry.trim();
			jsrq = jsrq.trim();

			////1、更新维修数据
			String sql="";
			if (ywlx.equals("TJ")){
				////插入设备维修信息  直接提交zt='02'
				sql = "insert into APP_NB_SBWXXX(sbid,id,tjryid,sbdh,sbdwbm,sbdw,sbry,sbrq,sbbh,sbmc,sydw,azqy,czz,czzdh,wxsx,gzms,tjry,tjrq,zt) "+
				          "values('"+sbid+"',0,'"+yhdm+"','','"+sbdwbm+"','"+sbdwmc+"','"+sbry+"','"+sbrq+"','"+sbbh+"','"+sbmc+
				                  "','"+sydw+"','"+azqy+"','"+czz+"','"+czzdh+"','"+wxsx+"','"+gzms+"','"+sbry+"','"+sbrq+"','02')";
			}else if (ywlx.equals("GX")){
				////更新设备维修记录
				sql =  "update APP_NB_SBWXXX "+
			           "set wxsx='"+wxsx+"', "+
			           //"    sbbh='"+sbbh+"', "+
			           //"    sbmc='"+sbmc+"', "+
			           "    gzms='"+gzms+"', "+
			           "    sydw='"+sydw+"', "+
			           "    azqy='"+azqy+"', "+
			           "    czz='"+czz+"', "+
			           "    czzdh='"+czzdh+"' "+
			           "where sbid='"+sbid+"' and zt<='02' ";
			}else if (ywlx.equals("SC")){
				////删除设备维修记录
				sql = "delete from APP_NB_SBWXXX "+
		              "where sbid='"+sbid+"' ";
			}else if (ywlx.equals("QR")){
				////确认设备维修记录
				sql =  "update APP_NB_SBWXXX "+
			           "set wxnr='"+wxnr+"', "+
			           "    wxdw='"+wxdw+"', "+
			           "    wxry='"+wxry+"', "+
			           "    wxsj='"+wxsj+"', "+
			           "    wxje=0, "+
			           "    lxdh='"+lxdh+"', "+
			           "    qrry='"+qrry+"', "+
			           "    qrrq='"+qrrq+"', "+
			           "    zt='03' "+
			           "where sbid='"+sbid+"' and zt='02' ";
			}else if (ywlx.equals("JS")){
				////接收设备维修记录
				sql =  "update APP_NB_SBWXXX "+
			           "set wxjg='"+wxjg+"', "+
			           "    jsry='"+jsry+"', "+
			           "    jsrq='"+jsrq+"', "+
			           "    zt='04' "+
			           "where sbid='"+sbid+"' and zt='03' ";
			}
			//FileLogger.debug("nb03_update sql："+sql);
			jt.update(sql);
			
			//2、同步ERP数据 调用同步存储过程  参数业务类型-ywlx 申报ID-sbid
			sql = "execute p_app2erp_sbwx '"+ywlx+"','"+sbid+"' ";
			//FileLogger.debug("nb03_update sql："+sql);
			jt.execute(sql);
			
			//FileLogger.debug("nb03_update sql："+sql);
			
		////3、插入、更新后返回维修记录，删除不能返回  需要与NB03_Qsbwx中的查询字段一样 
			if (!ywlx.equals("SC")){
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
		        " WHERE SBID ='"+sbid+"' ";	
				
				//FileLogger.debug("nb03_update sql："+sql);
				
				Map wxxxMap =  jt.queryForMap(sql);
				if(wxxxMap.isEmpty()){
					//throw new AppException("没有满足查询条件的计划数据！");
					m.put("code", true); 
					m.put("msg","设备维修插入失败！");
				}else{
					m.put("wxxx", wxxxMap);
				}
			}
			
				
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("设备维修 nb03_update----end");
		return m;
	}
}
