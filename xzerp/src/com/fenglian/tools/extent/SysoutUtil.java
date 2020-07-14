package com.fenglian.tools.extent;

public class SysoutUtil {
	/**
	 * 是否输出日志
	 */
	private static final boolean isout=true;
	
	public static void out(Object obj){
		if(isout){
			if(obj!=null){
				System.out.println(obj);
			}
		}
	}
}
