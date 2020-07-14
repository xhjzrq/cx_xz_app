package com.fenglian.exception;

import java.util.Calendar;
import java.util.Date;

import com.fenglian.tools.util.CString;

public class C {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar c=Calendar.getInstance(); 
		//Date date=c.getTime();
		int cishu=5;
		Date date;
		for(int i=0;i<cishu;i++){
			if(i!=0)
				c.add(Calendar.DATE, 7);
			date=c.getTime();
			System.out.println(CString.getDate(date, "yyyyMMdd"));
		}
	}
}
