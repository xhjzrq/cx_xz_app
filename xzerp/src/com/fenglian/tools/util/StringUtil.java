package com.fenglian.tools.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StringUtil {

	/**
	 * 获取支付传�??:aaa=bbb|ccc=ddd
	 * 
	 * @param name
	 *            要获取的字段名称�?
	 * @return
	 */
	public static String getPara(String src, String name) {
		src = "|" + src + "|";
		int begin = src.indexOf("|" + name + "=");
		if (begin > -1) {
			int end = src.indexOf("|", begin + 2);
			return src.substring(begin + 2 + name.length(), end);
		}
		return "";
	}

	/**
	 * 将一定时间范围内的所有日期转换成x坐标�?
	 * 
	 * @param date1
	 *            起始日期 yyyy-MM-dd
	 * @param date2
	 *            结束日期 yyyy-MM-dd
	 * @param type
	 *            周期：DAILY 日�?�WEEKLY 周�?�MONTHLY �?
	 * @param map
	 *            传入查询出来的数�?
	 * @return 返回list数组 0是lables 1是要显示的数据�??
	 */
	public static List[] getLabels(String date1, String date2, String type,
			Map map) {
		List lables = new ArrayList();
		List values = new ArrayList();
		if (CString.isEmpty(type))
			type = "Daily";

		if ("Daily".equals(type)) {
			int begin = DateUtil.date2DayID(date1);
			int end = DateUtil.date2DayID(date2);
			for (int i = begin; i <= end; i++) {
				String date = DateUtil.dayID2Date(i);
				// date = date.substring(5).replaceAll("0","")
				// ;//.replaceAll("-",",").replaceAll("0","");
				lables.add(date);
				values.add(map.get(i + ""));
			}
		}

		if ("Weekly".equals(type)) {
			int begin = DateUtil.date2WeekID(date1);
			int end = DateUtil.date2WeekID(date2);
			for (int i = begin; i <= end; i++) {
				String date = DateUtil.weekID2BeginDate(i);
				// date = date.substring(5); //.replaceAll("-","�?")+"�?";
				lables.add(date);
				values.add(map.get(i + ""));
			}
		}

		if ("Monthly".equals(type)) {
			int begin = DateUtil.date2MonthID(date1);
			int end = DateUtil.date2MonthID(date2);
			for (int i = begin; i <= end; i++) {
				String date = DateUtil.monthID2BeginDate(i);
				// date = date.substring(5);//.replaceAll("-","�?")+"�?";
				lables.add(date);
				values.add(map.get(i + ""));
			}
		}

		return new List[] { lables, values };
	}

	/**
	 * 将一定时间范围内的所有日期转换成x坐标�?
	 * 
	 * @param date1
	 *            起始日期 yyyy-MM-dd
	 * @param date2
	 *            结束日期 yyyy-MM-dd
	 * @param type
	 *            周期：DAILY 日�?�WEEKLY 周�?�MONTHLY �?
	 * @param map
	 *            传入查询出来的数�?
	 * @return 返回list数组 0是lables 1是要显示的数据�??
	 */
	public static List[] getLabels(String date1, String date2, String type,
			Map map, String para) {
		List lables = new ArrayList();
		List values = new ArrayList();
		List links = new ArrayList();
		if (CString.isEmpty(type))
			type = "Daily";

		if ("Daily".equals(type)) {
			int begin = DateUtil.date2DayID(date1);
			int end = DateUtil.date2DayID(date2);
			for (int i = begin; i <= end; i++) {
				String date = DateUtil.dayID2Date(i);
				// date = date.substring(5).replaceAll("0","")
				// ;//.replaceAll("-",",").replaceAll("0","");
				lables.add(date);
				values.add(map.get(i + ""));
				String para1 = para + "|time_type=Daily|time=" + date;
				links.add("javascript:showPost('" + para1 + "')");
			}
		}

		if ("Weekly".equals(type)) {
			int begin = DateUtil.date2WeekID(date1);
			int end = DateUtil.date2WeekID(date2);
			for (int i = begin; i <= end; i++) {
				String date = DateUtil.weekID2BeginDate(i);
				// date = date.substring(5); //.replaceAll("-","�?")+"�?";
				lables.add(date);
				values.add(map.get(i + ""));
				String para1 = para + "|time_type=Weekly|time=" + date;
				links.add("javascript:showPost('" + para1 + "')");
			}
		}

		if ("Monthly".equals(type)) {
			int begin = DateUtil.date2MonthID(date1);
			int end = DateUtil.date2MonthID(date2);
			for (int i = begin; i <= end; i++) {
				String date = DateUtil.monthID2BeginDate(i);
				// date = date.substring(5);//.replaceAll("-","�?")+"�?";
				lables.add(date);
				values.add(map.get(i + ""));
				String para1 = para + "|time_type=Monthly|time=" + date;
				links.add("javascript:showPost('" + para1 + "');");
			}
		}

		return new List[] { lables, values, links };
	}

	public static String toISO(String data) {
		if (data == null || "".equals(data))
			return "";

		String ret = "";
		try {

			byte[] b = data.getBytes("ISO-8859-1");
			ret = new String(b);
		} catch (UnsupportedEncodingException e) {

		}
		return ret;

	}

	public static String convert(String str) {
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c > 255) {
				sb.append("\\u");
				j = (c >>> 8);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
				j = (c & 0xFF);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
			} else {
				sb.append(c);
			}

		}
		return (new String(sb));
	}

	/**
	 * 临时的方法，获取默认库的编号�?
	 * 
	 * @return
	 */
	public static String getDefaultIndustryId() {
		return "121";
	}

	public static String subString(Object src1, int i) {

		if (src1 == null || "".equals(src1))
			return "";
		String src = src1 + "";
		if (src.length() > i) {
			return src.substring(0, i) + "...";
		} else {
			return src;
		}
	}

	public static void main(String[] args) {

		System.out.println(StringUtil.convert("恭喜！用户注册成功，可以登录�?"));
		System.out.println(StringUtil.convert("您无法限访问该页面，请先登陆�?"));
		System.out.println(StringUtil.convert("口令长度必须大于6位！"));
		System.out.println(StringUtil.convert("该邮箱地�?已存在！"));
		System.out.println(StringUtil.convert("您添加的用户显示名称不能为空�?"));
		System.out.println(StringUtil.convert("您�?�择的订阅站点有误！"));
		System.out.println(StringUtil.convert("该记录有关联记录无法删除�?"));

	}

	public static String rep(Object s) {
		if (s == null)
			return "";
		if (s instanceof String) {
			String new_name = (String) s;
			return new_name.trim();
		}
		return s + "";
	}

	public static String rep(Object s, String mark) {
		if (s == null)
			return mark;
		if (s instanceof String) {
			String new_name = (String) s;
			return new_name.trim();
		}
		return s + "";
	}

	public static String print(Object[] objs) {
		String s = "";
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null) {
				s += objs[i].toString() + ",";
			} else {
				s += "null,";
			}
		}
		return s;

	}

	public static String toMonth(Object m) {

		if (m == null)
			return "";

		String month = m.toString();
		if (month.length() == 1) {
			month = "0" + month;
		}

		return month;
	}

	/**
	 * 将数据内容分组存储并返回分组后数据�??
	 * 
	 * @param list
	 * @param groupField
	 * @return Map<String,List>
	 */
	public static Map grouyBy(List list, String groupField) {

		HashMap ht = new HashMap();

		if (list == null || list.isEmpty())
			return ht;

		for (Iterator it = list.iterator(); it.hasNext();) {
			Map map = (Map) it.next();
			String groupValue = map.get(groupField) + "";
			if (ht.containsKey(groupValue)) {
				((List) ht.get(groupValue)).add(map);
			} else {
				List groupList = new ArrayList();
				groupList.add(map);
				ht.put(groupValue, groupList);
			}
		}

		return ht;
	}

	public static String formatNumber(Object value) {
		DecimalFormat df = new DecimalFormat("###,###,###.00");
		if (value == null)
			return "";
		try {
			if (value instanceof String) {

				float f = Float.parseFloat((String) value);
				String var = df.format(f);
				return var;

			}
			String var = df.format(value);
			return var;

		} catch (Exception e) {
			return value + "";
		}
	}
	public static String formatNumber(Object value,String format) {
		DecimalFormat df = new DecimalFormat(format);
		if (value == null)
			return "";
		try {
			if (value instanceof String) {

				float f = Float.parseFloat((String) value);
				String var = df.format(f);
				return var;

			}
			String var = df.format(value);
			return var;

		} catch (Exception e) {
			return value + "";
		}
	}
	
	public static String isOver(Object date)
	{
		Date nowDate = new Date();
		String strdate = (String)date;
		if(nowDate.getTime() > DateUtil.string2Date(strdate).getTime())
		{
			return "&nbsp;<font color='#FF0000'>过期</font>";			
		}else
		{
			return "";
		}
		
	}
}
