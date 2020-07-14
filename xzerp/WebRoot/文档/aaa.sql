GRANT SELECT ON [dbo].[SD_KHXXP] TO [fljk200_byq_erp]
go

GRANT SELECT ON [dbo].[SD_SHDW] TO [fljk200_byq_erp]
go

GRANT SELECT ON [dbo].[MS_YSGSJBP] TO [fljk200_byq_erp]
go

GRANT SELECT ON [dbo].[MSGHSJBP] TO [fljk200_byq_erp]
go
DM_ZHBMP

GRANT SELECT ON [dbo].[DM_ZHBMP] TO [fljk200_byq_erp]
go


GRANT SELECT ON [dbo].[IM_CKWPKWP] TO [fljk200_byq_erp]
go

GRANT SELECT ON [dbo].[V_MP_TJ_HTZXQK] TO [fljk200_byq_erp]
go

--select * from V_MP_TJ_HTZXQK

--drop view V_MP_TJ_HTZXQK;
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