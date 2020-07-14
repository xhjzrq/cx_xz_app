package com.fenglian.tools.util;

public class ID {

	public static synchronized long  getId()
	{
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
		}
		return System.currentTimeMillis();
	}
	
	
	/**
	 * 时间暂停2秒
	 * @return
	 */
	public static synchronized long  getId2()
	{
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
		}
		return System.currentTimeMillis();
	}
}
