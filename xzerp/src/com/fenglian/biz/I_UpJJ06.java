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
 * 舾装日报确认接口 UpJJ05
 */
public class I_UpJJ06 extends I_Command{
	@Override
	public Object doCommand(HttpServletResponse res, HttpServletRequest req) {
		FileLogger.debug("舾装日报确认 JJ06_Up----start");
		Map m = new HashMap();
		m.put("code", true); //接口正常标识
		m.put("msg", ""); // 错误信息
		
		
		//入参
		String mxid = req.getParameter("mxid") ;//明细id
		String qrry = req.getParameter("qrry") ;//确认人员
		String qrbz = req.getParameter("qrbz") ;//确认备注
		//入参
		
		try{
			
			if(CString.isEmpty(mxid)){
				throw new AppException("反馈ID为空！");
			}
			
			if(CString.isEmpty(qrry)){
				throw new AppException("确认人员为空！");
			}
			
			if(CString.isEmpty(qrbz)){
				qrbz="";
			}
			
			mxid = mxid.toUpperCase().trim();
			qrbz = qrbz.toUpperCase().trim();
			////更新确认回复
			String sql="update APP_JJ_QFFKRB "+
			           "set zt='05', "+
			           "    qrry='"+qrry+"', "+
			           "    qrrq=REPLACE(LEFT(CONVERT(VARCHAR(20),GETDATE(),120),10),'-','/'), "+
			           "    qrbz='"+qrbz+"' "+
			           "where mxid="+mxid+" ";
			
			FileLogger.debug("JJ06_Up update："+sql);
			jt.update(sql);
				
		}catch (Exception e) {
			e.printStackTrace();
			m.put("code", false); // 调用接口错误
			m.put("msg", e.getMessage()); // 错误信息
		}
		FileLogger.debug("舾装日报确认 JJ06_Up----end");
		return m;
	}
}
