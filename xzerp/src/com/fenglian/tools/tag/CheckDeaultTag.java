package com.fenglian.tools.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import com.fenglian.exception.BizException;
import com.fenglian.tools.logger.FileLogger;
import com.fenglian.tools.util.CString;
import com.fenglian.tools.util.DateUtil;

public class CheckDeaultTag extends ApplicationContextTag {
	private static final long serialVersionUID = 1L;
	
	private String currentCode;//当前编码
	
	private String hiddenCodes;//被隐藏的字符串
	
	private String currentIndex;//当前的索引号
	
	private String cclass;//类样式
	
	private String style;//style 样式
	
	private String method;//自定义方法
	
	private String defultValue;//默认值 
	
	private String type;//文本框的类型
	
	
	public int doEndTag() throws JspException {
		return Tag.EVAL_BODY_INCLUDE;
	}

	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try{
//			System.out.println(currentCode+"当前编码");
//			System.out.println(hiddenCodes+"====隐藏列串");
//			System.out.println(currentIndex+"当前索引号");
			
			if(CString.isEmpty(currentCode)){
				throw new BizException("没有传入进行检测的编码");
			}
			
			if(CString.isEmpty(currentIndex)){
				throw new BizException("没有传入正确的所有编号");
			}
			
			if(!CString.isEmpty(hiddenCodes)){
				int hiddencodelength=hiddenCodes.length();
				if(hiddenCodes.indexOf(",")!=0){//如果不以英文,开始添加
					hiddenCodes=","+hiddenCodes;
				}
				if(hiddenCodes.lastIndexOf(",")!=(hiddencodelength-1)){
					hiddenCodes=hiddenCodes+",";
				}
			}
			
			String currentCode2=","+currentCode+",";//使传入的标签编码以英文,开始结束
			
			String outstr="";
			if(hiddenCodes.indexOf(currentCode2)!=-1){//表示该显示 ——
				outstr+="——"+"<input type='hidden' name='"+currentCode+"' value='——' />";
			}else{//输出文本框
				if(CString.isEmpty(defultValue)){
					defultValue="";
				}
				
				if("textarea".equals(type)){//传入类型为textarea 类型 显示为多行文本框 默认为单行
					outstr+="<textarea name='"+currentCode+"' id='"+currentCode+"_"+currentIndex+"'";
					if(!CString.isEmpty(method)){
						outstr+=" "+method+" ";
					}
					
					if(!CString.isEmpty(cclass)){
						outstr+=" class='"+cclass+"' ";
					}
					
					if(!CString.isEmpty(style)){
						outstr+=" style='"+style+"' ";
					}
					outstr+=">";
					outstr+=defultValue;
					outstr+="</textarea>";
				}else {
					outstr+="<input type='text' name='"+currentCode+"' id='"+currentCode+"_"+currentIndex+"'";
					
					outstr+=" value='"+defultValue+"' ";
					
					if(!CString.isEmpty(method)){
						outstr+=" "+method+" ";
					}
					
					if(!CString.isEmpty(cclass)){
						outstr+=" class='"+cclass+"' ";
					}
					
					if(!CString.isEmpty(style)){
						outstr+=" style='"+style+"' ";
					}
					
					outstr+=" />";
				}
				
			}
			
			
			out.print(outstr);
		}catch (Exception e) {
			FileLogger.error("格式化空列说明", e);
		}
		return Tag.EVAL_BODY_INCLUDE;
	}
	
	public String getCurrentCode() {
		return currentCode;
	}

	public void setCurrentCode(String currentCode) throws JspException {
		this.currentCode = (String) evaluator.evaluate("currentCode", currentCode, String.class,
				null, pageContext);
	}

	public String getHiddenCodes() {
		return hiddenCodes;
	}

	public void setHiddenCodes(String hiddenCodes) throws JspException {
		this.hiddenCodes = (String) evaluator.evaluate("hiddenCodes", hiddenCodes, String.class,
				null, pageContext);
	}
	
	
	public String getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(String currentIndex) throws JspException {
		this.currentIndex = (String) evaluator.evaluate("currentIndex", currentIndex, String.class,
				null, pageContext);
	}

	public String getCclass() {
		return cclass;
	}

	public void setCclass(String cclass) {
		this.cclass = cclass;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDefultValue() {
		return defultValue;
	}

	public void setDefultValue(String defultValue) throws JspException {
		this.defultValue =(String) evaluator.evaluate("defultValue", defultValue, String.class,
				null, pageContext);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
