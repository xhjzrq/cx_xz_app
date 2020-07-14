/*
 * 创建日期 2005-7-21
 * 作者：张阳斌
 */
package com.fenglian.tools.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 张阳斌
 * 创建日期： 2005-7-21
 *
 * 类描述：
 * 
 * 应用场合：
 * 
 * 版本：V0.1
 */
public class CString
{
	/**
	 * 
	 * 功能：替换非法字符串
	 * @param s 要替换字符串
	 * @return 替换null字符串为“”
	 */
	public static String rep(String s)
	{
		if(s == null) return "";
		return s.trim();
	}
	
	public static String rep(Object s)
	{
		if(s == null) return "";
		if (s instanceof String) {
			String new_name = (String) s;
			return new_name.trim();
		}
		return s+"";
	}
	
	/**
	 * 
	 * 功能：替换非法字符串
	 * @param s 要替换字符串
	 * @param mark  如果字符为空则替换成makr
	 * @return 替换null字符串为 mark
	 */
	public static String repForNull(String s,String mark)
	{
		if(CString.isEmpty(s))
		{
			return mark;
		}
		
		return s.trim();
	}
	/**
	 * 
	 * 功能：替换非法字符串
	 * @param s 要替换字符串
	 * @param mark  如果字符为空则替换成makr
	 * @return 替换null字符串为 mark
	 */
	public static String rep(String s,String mark)
	{
		if(s == null) return mark;
		return s.trim();
	}
	
	/**
	*功能：字符串为空判定
	*@param str 要判定字符串
	*@return true  字符串为空或“”
		   false 字符串不为空
	*/
	public static boolean isEmpty(String str)
	{
		if (str == null || str.trim().length() ==0 )
		{
			return true;
		}
		return false;

	}

	/**
	*功能：检查字符串是否是由数字组成，不包含小数点。
	*@param str 要判定字符串
	*@return true  是数值类型,或为空
		   false 不空并且不是数字组成
	*/
	public static boolean isNumer(String s)
	{
		if (CString.isEmpty(s))
		{
			return false;
		}
		int i = s.trim().length();
		if (i == 0)
		{
			return true;
		}
		byte abyte0[] = new byte[i];
		abyte0 = s.getBytes();
		for (int j = 0; j < i; j++)
		{
			if ((abyte0[j] < 48 || abyte0[j] > 57))
			{
				return false;
			}
		}

		return true;
	}

	/**
	*功能：检测字符串是否是整型数。
	*@param  str 要判定字符串  范围：0～2147483647 
	*@return true  是整型数，或为空
		   false 不是
	*/
	public static boolean isInteger(String s)
	{
		if (CString.isEmpty(s))
		{
			return false;
		}
		try
		{
			Integer.parseInt(s);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}

	}
	/**
	*功能：将字符串转换成中文字符集
	*@param  str 要判定字符串
	*@return 转换完的字符串
	*/
	public static String toChinese(String s)
	{
		String s1 = "";
		if (s == null)
		{
			return "";
		}
		s = s.trim();
		if (s.equals(""))
		{
			return "";
		}
		try
		{
			s1 = new String(s.getBytes("8859_1"));
		}
		catch (Exception exception)
		{
			throw new RuntimeException("tools.CString.toChinese.err");
		}
		s1 = s1.replaceAll("'", "''");

		return s1;
	}

	/**	 * 
	 * @param date 时间类
	 * @param str 要转换的格式：例：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDate(Object date, String str)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(str);
			return sdf.format((Date) date);
		}
		catch (Exception e)
		{
			return "";
		}

	}
	
	/**
	 * 获取当前日期和时间。
	 * @return
	 */
	public static String getDateTimeNow()
	{
		try
		{
			return CString.getDate(new Date(),"yyyy-MM-dd HH:mm:ss");
		}
		catch (Exception e)
		{
			return "";
		}
	}
	/**
	 * 获取当前日期。
	 * @return
	 */
	public static String getDateNow()
	{
		try
		{
			return CString.getDate(new Date(),"yyyy-MM-dd");
		}
		catch (Exception e)
		{
			return "";
		}

	}
	
	
	
	/**
	 * 日期格式化，将YYYY%M -》YYYYMM
	 * @return
	 */
	public static String dateFormat(String date)
	{
		try
		{
			if(date.length() ==6)
				return date;
			
			if(date.length() ==5)
			{
				String year = date.substring(0,4);
				String month = date.substring(4);
				return year+"0"+month;
			}
			return date;
		}
		catch (Exception e)
		{
			return "";
		}

	}
	
	
	public static Date stringToDate(String date)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(date);
		}
		catch (Exception e)
		{
			throw new RuntimeException("dateToString exception:"+date);
		}

	}
	
	public static String dateToString(Date date)
	{
		try
		{
			return getDate(date, "yyyy-MM-dd");
		}
		catch (Exception e)
		{
			return "";
		}

	}
	
	/**
	 * 检测email格式。
	 * @param mail
	 * @return
	 */
	public static boolean isEmail(String mail)
	{
		
		if (CString.isEmpty(mail))
		{
			return false;
		}
		
		if(mail.indexOf("@")<0)
		{
			return false;
		}
		if(mail.indexOf(".")<0)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 将风格字符串转换成map对象
	 * 
	 * @param msg
	 * @return
	 */
	public static Map conversion(String msg,String sp1,String sp2) {
		Map map = new HashMap();
		String s[] = msg.split(sp1);
		for (int i = 0; i < s.length; i++) {
			int index = s[i].indexOf("=");
			if(index>0)
			{				
				String key = s[i].substring(0,index);
				String value = s[i].substring(index+1);
				map.put(key, value);
			}			
		}
		return map;
	}
	
	/**
     * 将通用字符替换成HTML文本串中的特殊字符。 <br>
     * 用途： <br>
     * 将数据库或其他媒介中保存的文本信息，显示到HTML文件中的“Input type=text”标记中。 <br>
     * 将 <code>commonString</code> 中包含的‘&’、‘"’、‘'’替换为‘&amp;#38;’、‘&amp;#34;’、
     * ‘&amp;#39;’。
     * 
     * @param commonString - 普通字符串
     * @return 返回替换后的字符串。
     * @since JDK1.4.2
     */
    public static String stoh(String commonString)
    {

        if (commonString == null) return null;

        commonString = CString.replace(commonString, "<", "&#60;");
        commonString = CString.replace(commonString, ">", "&#62;"); 
        commonString = CString.replace(commonString, "\"", "&#34;");
        commonString = CString.replace(commonString, "'", "&#39;");
        //commonString = StringTool.replace(commonString, "&", "&#38;");

        return commonString;

    }
    
    /**
     * 将字符串 <code>str</code> 中所含所有的 <code>oldStr</code> 用 <code>newStr</code> 替换，并返回 新串。
     * <p>
     * 调用举例： <br>
     * <br>// 结果为"123ABC456" <br>
     * StringTool.replace("123abc456", "abc", "ABC"); <br>
     * <br>
     * //结果为"123abc456" <br>
     * StringTool.replace("123abc456", "", "ABC"); <br>
     * <br>
     * //结果为"123456" <br>
     * StringTool.replace("123abc456", "abc", ""); <br>
     * <br>
     * //结果为"123ABC456" <br>
     * StringTool.replace("123AABCBC456", "ABC", ""); <br>
     * <br>
     * </p>
     * 
     * @param str - 要替换的字符串
     * @param oldStr - 旧子字符串（被替换的子串）
     * @param newStr - 新子字符串（替换为的子串）
     * @return 返回一个替换完的字符串。 如果字符串 <code>oldStr</code> 为空串""或null，则直接返回str; 如果字符串
     *         <code>newStr</code> 为空串""或null，则将在str中剔除所有包含 <code>oldStr</code> 的串。
     * @since JDK1.4.2
     */
    public static String replace(String str, String oldStr, String newStr)
    {

        if (oldStr == null) return str;
        if (oldStr.equals("")) return str;
        if (newStr == null) newStr = "";

        int len = oldStr.length();
        StringBuffer sb = new StringBuffer(str);
        StringBuffer sbReturn = new StringBuffer();
        for (int i = sb.indexOf(oldStr); i != -1; i = sb.indexOf(oldStr))
        {
            sbReturn.append(sb.substring(0, i));
            sbReturn.append(newStr);
            sb.delete(0, i + len);
        }
        sbReturn.append(sb.toString());

        return sbReturn.toString();

    }
	
    
    /**
     * 判断字符串超长后按长度截取
     * @param 支付串对象
     * @param 要保留长度
     * @return  窃取后字符串
     */
    public static String subString(Object o,int length)
    {
    	  String s = CString.rep(o);
    	  if(s.getBytes().length>length)
    	  {
    		  byte[] b = s.getBytes();
    		  return new String(b,0,length);
    	  }else
    	  {
    		  return s;
    	  }

    }
    
    /**
     * 返回查找字符串后的两个单引号中的字符
     * @param 要查找的字符串
     * @param 查找字符串
     * @return  查找单引号中的字符串
     */
    public static String fdstr(String str,String findstr)
    {
    	  String s="";
    	  int index=0;
    	  int indexs=0;
    	  int indexe=0;
    	  index=str.indexOf(findstr);
    	  if (index!=-1){
	    		indexs=str.indexOf("'", index);
		    		if (indexs>0){
		    			indexe=str.indexOf("'", indexs+1);
		    			if (indexe>0 & indexs!=indexe){
		    				s = str.substring(indexs+1, indexe );
		    			}	
	    		}	
    	  }
		  return s.trim();

    }
	
    
	public static void main(String[] args) {
		System.out.println(CString.dateFormat("20070999"));
	}
}
