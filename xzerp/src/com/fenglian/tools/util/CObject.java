package com.fenglian.tools.util;

/**
 * 功能：将Object转换为相应的String对象或数据类型。<br>
 * 作者：我是小木鱼(Lag)<br>
 */
public class CObject 
{

    /**
     * <b>功能：将Object对象转换为字符串对象</b><br>
     * @param obj - 传入的对象<br>
     * @return 字符串对象（NULL值转换为""）<br>
     */
	public static String getString(Object obj)
	{
		if(obj == null)
		{
			return "";
		}
		else
		{
			return obj.toString();
		}
	}
	
    /**
     * <b>功能：将Object对象转换为网页上可视字符串对象</b><br>
     * @param obj - 传入的对象<br>
     * @return 网页上可视字符串对象（NULL值转换为""）<br>
     */
	public static String getStringHtml(Object obj)
	{
		return CString.stoh(CObject.getString(obj));
	}
	
    /**
     * <b>功能：将Object对象转换为double数据类型</b><br>
     * @param obj - 传入的对象<br>
     * @return double数据类型（NULL值或空字符串对象转换为0）<br>
     */
	public static double getDouble(Object obj)
	{
		if(obj == null || obj.equals(""))
		{
			return 0;
		}
		
		return new Double(obj.toString()).doubleValue();
	}

    /**
     * <b>功能：将Object对象转换为int数据类型</b><br>
     * @param obj - 传入的对象<br>
     * @return int数据类型（NULL值或空字符串对象转换为0）<br>
     */
	public static int getInt(Object obj)
	{
		if(obj == null || obj.equals(""))
		{
			return 0;
		}
		
		return new Integer(obj.toString()).intValue();
	}

    /**
     * <b>功能：将Object对象转换为long数据类型</b><br>
     * @param obj - 传入的对象<br>
     * @return long数据类型（NULL值或空字符串对象转换为0）<br>
     */
	public static long getLong(Object obj)
	{
		if(obj == null || obj.equals(""))
		{
			return 0;
		}
		
		return new Long(obj.toString()).longValue();
	}
	
	

	
	
	
	
	
}
