
create view V_MP_WLCRJH_WEEK as
SELECT 
MP_WLCRJH_WEEK.jhbh   as  JHBH    ,
MP_WLCRJH_WEEK.GCBH	  as  HTZX    ,
MP_WLCRJH_WEEK.KHDM	  as  KHMC    ,
MP_WLCRJH_WEEK.SHDW	  as  SHDW  ,
MP_WLCRJH_WEEK.SCSDM  as  SCS   ,
MP_WLCRJH_WEEK.YSDW	  as  YSDW  ,
MP_WLCRJH_WEEK.KSRQ	  as  KSRQ  ,
MP_WLCRJH_WEEK.JSRQ	  as  JSRQ  ,
MP_WLCRJH_WEEK.HDLX	 as   ZYLX  ,
MP_WLCRJH_WEEK.sl	 as   SL      ,
MP_WLCRJH_WEEK.zl	 as   ZL	  ,
MP_WLCRJH_WEEK.JHCXBS  as JHCX	  ,
MP_WLCRJH_WEEK.JHDCXBS  as ZYD    ,
MP_WLCRJH_WEEK.ZT	  as  JHZT	  ,
MP_WLCRJH_WEEK.bz	  as  DJBZ	  ,
MP_WLCRJH_WEEK.xgry	  as  BZRY    ,
MP_WLCRJH_WEEK.xgrq	  as  BZRQ ,
'查询'	  as  ZLJHCX	 	 
								  
FROM MP_WLCRJH_WEEK


create view V_MP_TJ_HTZXQK as
SELECT 
0      as    XH       ,
MP_TJ_HTZXQK.HTBH_D	 as     HTBH    ,
MP_TJ_HTZXQK.GCBH_D	 as     HTZX    ,
MP_TJ_HTZXQK.KHDM_D	 as     KHMC   ,
MP_TJ_HTZXQK.YWLX	 as     WLLB   ,
MP_TJ_HTZXQK.YWSL_JH  as    WL	   ,
MP_TJ_HTZXQK.YWDW	 as     DW       ,
MP_TJ_HTZXQK.KSRQ_JH  as    JHKGRQ	 ,
MP_TJ_HTZXQK.JSRQ_JH as     JHWGRQ	 ,
MP_TJ_HTZXQK.TS_JH	 as     JHGQ    ,
MP_TJ_HTZXQK.RK_SJ	 as     RKL	    ,
MP_TJ_HTZXQK.YCL_SJ	 as     YCLL    ,
MP_TJ_HTZXQK.QG_SJ	 as     QGL   ,
MP_TJ_HTZXQK.CK_SJ	 as     FCL	  ,
MP_TJ_HTZXQK.KCZL	 as     KC	 
								  
FROM MP_TJ_HTZXQK


create view V_MP_SCJHZX_CX2 as
SELECT 
MP_SCJHZX_CX2.KHMC  AS    KHMC   ,
MP_SCJHZX_CX2.JHBH	AS   JHBH   ,
MP_SCJHZX_CX2.HTZX	AS   HTZX   ,
MP_SCJHZX_CX2.KSRQ	AS   KSRQ    ,
MP_SCJHZX_CX2.JSRQ	AS   JSRQ    ,
MP_SCJHZX_CX2.JHDW	AS   JHDW    ,
MP_SCJHZX_CX2.ZYLX	 AS  ZYLX    ,
MP_SCJHZX_CX2.WZLX	 AS  WZLX	 ,
MP_SCJHZX_CX2.SL	AS   SL		 ,
MP_SCJHZX_CX2.ZL		AS   ZL		  ,
MP_SCJHZX_CX2.LJSL	AS   LJSL	  ,
MP_SCJHZX_CX2.CS	AS   CS		  ,
MP_SCJHZX_CX2.CH	AS   CH      ,
MP_SCJHZX_CX2.ZYSJF	AS   ZYSJF	 ,
MP_SCJHZX_CX2.XDL        AS   XDL	 ,
MP_SCJHZX_CX2.WCL	AS   WCL	  ,
MP_SCJHZX_CX2.DJH	AS   DJH	  ,
MP_SCJHZX_CX2.SSZYDH	 AS  SSZYDH	  ,
MP_SCJHZX_CX2.BZRQ	AS   BZRQ    ,
MP_SCJHZX_CX2.DJCX	AS   DJCX	

FROM MP_SCJHZX_CX2

=================================================================================================

USE [master]
GO

CREATE LOGIN [fljk200] 
WITH PASSWORD=N'fljk20170720!', 
DEFAULT_DATABASE=[BYQ_ERP],
CHECK_EXPIRATION=OFF, 
CHECK_POLICY=ON
GO

-----------------------------------------

USE [BYQ_ERP]
GO

CREATE USER [fljk200_byq_erp] FOR LOGIN [fljk200]
GO


use [BYQ_ERP]
GO
GRANT SELECT ON [dbo].[V_MP_WLCRJH_WEEK] TO [fljk200_byq_erp]
GO

GRANT SELECT ON [dbo].[V_MP_TJ_HTZXQK] TO [fljk200_byq_erp]
GO

GRANT SELECT ON [dbo].[V_MP_SCJHZX_CX2] TO [fljk200_byq_erp]
GO

GRANT EXECUTE ON [dbo].[P_TJ_MP_TJ_HTZXQK] TO [fljk200_byq_erp]
GO