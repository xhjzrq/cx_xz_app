package com.fenglian.tools.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	// 将时间转换成String
	public static String date2String(Date date) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	// 将时间转换成String
	public static String dateToString(Date date) {
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	// 将时间转换成String
	public static Date string2Date(String string) {
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.parse(string);
		} catch (Exception e) {
			return null;
		}

	}

	
	/**
	 * // 将时间转换成String
	 * @param date 时间
	 * @param simple 格式化 样式
	 */
	public static String dateToString(String date,String simple) {
		try {
			Date d=new Date(date);
			DateFormat df = new SimpleDateFormat(simple);
		    return df.format(d);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 日期转换成，天ID
	 * 
	 * @param date
	 * @return
	 */
	public static int date2DayID(String date) {
		return getBetweenDayNumber("2003-12-31", date);
	}

	/**
	 * ID 转换成日
	 * 
	 * @param dateid
	 * @return
	 */
	public static String dayID2Date(int dateid) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar C = Calendar.getInstance();// 得到当前时间
		try {
			Date date = sdf.parse("2003-12-31");

			C.setTime(date);

			C.add(C.DATE, dateid);

			return sdf.format(C.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 日期转换  周id
	 * 
	 * @param date
	 * @return
	 */
	public static int date2WeekID(String date) {
		return (getBetweenDayNumber("2003-12-21", date) / 7);
	}

	/**
	 * 周id转换成日期
	 * 
	 * @param weekid
	 * @return
	 */
	public static String weekID2BeginDate(int weekid) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar C = Calendar.getInstance();// 得到当前时间
		try {
			int id = weekid * 7;

			Date date = sdf.parse("2003-12-21");

			C.setTime(date);

			C.add(C.DATE, id);

			return sdf.format(C.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 周id转换成日期
	 * 
	 * @param weekid
	 * @return
	 */
	public static String weekID2EndDate(int weekid) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar C = Calendar.getInstance();// 得到当前时间
		try {
			int id = weekid * 7+6;

			Date date = sdf.parse("2003-12-21");

			C.setTime(date);

			C.add(C.DATE, id);

			return sdf.format(C.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 日期转换 月id
	 * 
	 * @param date
	 * @return
	 */
	public static int date2MonthID(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar C = Calendar.getInstance();// 得到当前时间
		try {
			Date date1 = sdf.parse(date);
			C.setTime(date1);
			int monthid = (C.get(C.YEAR) - 2004) * 12 + C.get(C.MONTH) + 1;
			return monthid;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 月转换成日期
	 * 
	 * @param monthid
	 * @return
	 */
	public static String monthID2BeginDate(int monthid) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar C = Calendar.getInstance();// 得到当前时间
		try {

			Date date = sdf.parse("2003-12-01");

			C.setTime(date);

			C.add(C.MONTH, monthid);

			return sdf.format(C.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 月转换成日期
	 * 
	 * @param monthid
	 * @return
	 */
	public static String monthID2EndDate(int monthid) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar C = Calendar.getInstance();// 得到当前时间
		try {

			Date date = sdf.parse("2003-12-01");

			C.setTime(date);

			C.add(C.MONTH, monthid+1);
			C.add(C.DATE, -1);

			return sdf.format(C.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static int getBetweenDayNumber(String dateA, String dateB) {
		long dayNumber = 0;

		long DAY = 24L * 60L * 60L * 1000L;

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		try {

			java.util.Date d1 = df.parse(dateA);
			java.util.Date d2 = df.parse(dateB);
			dayNumber = (d2.getTime() - d1.getTime()) / DAY;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (int) dayNumber;
	}
	
	public static String getChangeYear(String date,int change) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar C = Calendar.getInstance();// 得到当前时间

			C.setTime(sdf.parse(date));

			C.add(C.YEAR, change);
			String nextYear = sdf.format(C.getTime());

			return nextYear;

		} catch (ParseException e) {

			e.printStackTrace();
		}
		return "";
	}
	
	public static String getChangeDay(String date,int change) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			Calendar C = Calendar.getInstance();// 得到当前时间

			C.setTime(sdf.parse(date));

			C.add(C.DATE, change);
			String nextDay = sdf.format(C.getTime());

			return nextDay;

		} catch (ParseException e) {

			e.printStackTrace();
		}
		return "";
	}


	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static Timestamp  getTimestamp()
	{
		Date d=new Date();
		Timestamp t=new Timestamp(d.getTime());
		return t;
	}
	
	/**
	 * 当前时间
	 * @return YYYY年MM月DD日
	 */
	public static String getDate()
	{
		Date d=new Date();
		String date=dateToString(d);
		date=date.replaceFirst("-", "年");
		date=date.replaceFirst("-", "月");
		date=date+"日";
		return date;
	}
	public static String getDate(String date) {
		
		//Date d = string2Date(date);
		date = dateToString(date,"yyyy-MM-dd");
		date = date.replaceFirst("-", "年");
		date = date.replaceFirst("-", "月");
		date = date + "日";
		return date;
	}
	public static void main(String[] args) {
		
		System.out.println(getDate("20120102"));

	}

}
