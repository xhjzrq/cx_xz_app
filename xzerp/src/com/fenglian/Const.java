package com.fenglian;
/**
 *	模块名称 
 *	insert into syifrp (GWDM,ms,zxtbm ) values ('IM41','M1的值','IM')
 */
public final class Const {
	public static final String USER_YHDM="USER_YHDM";
	public static final String USER_ROOTS="USER_ROOTS";
	public static final String USER_TOKEN="USER_TOKEN";
	
	/**
	 * 系统初始化时向模块表中ms 字段的值必须有 M*值相同
	 */
	public static final String M1="综合信息查询1";
	public static final String M2="垛位信息平面图";
	public static final String M3="作业计划查询";
	public static final String M4="计划执行统计";
	public static final String M5="合同执行情况";
	public static final String M6="合同子项查询";
	public static final String M7="生产计划查询";
	public static final String M8="质量计划查询";
	public static final String M9="作业文档查询";
	
	public static String LOGINRQSTR="and SYIFRP.ms in ('"+M1+"','"+M2+"','"+M3+"','"+M4+"','"+M5+"','"+M6+"','"+M7+"','"+M8+"','"+M9+"')";
	
	
	public static final int PAGESIZE=20;
}
