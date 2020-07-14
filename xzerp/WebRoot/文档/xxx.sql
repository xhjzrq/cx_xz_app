SELECT DISTINCT RTRIM(SYIFRP.MS) AS MS 
FROM SYUIRP,SYIFRP
 WHERE SYUIRP.GWDM = SYIFRP.GWDM 
 AND SYUIRP.YHDM = 'sccyf'
 
 
 select * from SYUIRP

  [SELECT count(0) from (SELECT * FROM IM_CKWPKWP WHERE  1=1  and (IM_CKWPKWP.RKRQ>='2014/12/03') and (IM_CKWPKWP.RKRQ<='2015/04/02') ORDER BY IM_CKWPKWP.NBSBM) a]; nested exception is com.microsoft.sqlserver.jdbc.SQLServerException: 除非另外还指定了 TOP 或 FOR XML，否则，ORDER BY 子句在视图、内联函数、派生表、子查询和公用表表达式中无效。
 SELECT count(0) from (SELECT * FROM IM_CKWPKWP WHERE  1=1  and (IM_CKWPKWP.RKRQ>='2014/12/03') and (IM_CKWPKWP.RKRQ<='2015/04/02') ORDER BY IM_CKWPKWP.NBSBM) a
 select * from syuserp
 
 select * from SYIFRP
 --1 新增加权限
insert into syifrp (GWDM,ms,zxtbm ) values ('IM41','入库作业查询','IM')
insert into syifrp (GWDM,ms,zxtbm ) values ('IM41','垛位信息平面图','IM')
update syifrp set ms='入库作业查询' where GWDM=''

select * from syifrp where GWDM='IM41'
--1 新增加权限 列表
SELECT DISTINCT RTRIM(SYIFRP.MS) AS MS 
FROM SYUIRP,SYIFRP
 WHERE SYUIRP.GWDM = SYIFRP.GWDM 
 AND SYUIRP.YHDM = 'sccyf'
and SYIFRP.ms in ('入库作业查询','垛位信息平面图')

SELECT DISTINCT RTRIM(SYIFRP.MS) AS MS FROM SYUIRP,SYIFRP WHERE SYUIRP.GWDM = SYIFRP.GWDM AND SYUIRP.YHDM = 'sccyf' and SYIFRP.ms in ('远程访问功能1','远程访问功能2')
select * from SYUIRP WHERE yhdm='SCCYF'

SELECT DISTINCT RTRIM(SYIFRP.MS) AS MS FROM SYUIRP,SYIFRP WHERE SYUIRP.GWDM = SYIFRP.GWDM AND SYUIRP.YHDM = 'SCCYF' and SYIFRP.ms in ('入库作业查询','垛位信息平面图')

 CREATE TABLE [dbo].[DM_KWJBP](
	[CKH] [varchar](10) NOT NULL,
	[KWH] [varchar](10) NOT NULL,
	[KWSM] [varchar](30) NULL,
	[TJ] [varchar](40) NULL,
	[SX] [varchar](10) NULL,
	[CFWPF] [varchar](1) NULL,
	[CZZL] [decimal](15, 6) NULL,
	[YLZD1] [varchar](100) NULL,
	[YLZD2] [varchar](100) NULL,
	[YLZD3] [varchar](100) NULL,
	[YLZD4] [decimal](15, 6) NULL,
	[YLZD5] [decimal](15, 6) NULL,
	[YLZD6] [decimal](15, 6) NULL,
	[YXJ] [numeric](8, 2) NULL,
	[MAXKD] [decimal](15, 3) NULL,
	[MAXCD] [decimal](15, 3) NULL,
 CONSTRAINT [PK_DM_KWJBP] PRIMARY KEY CLUSTERED 
(
	[CKH] ASC,
	[KWH] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[SD_KHXXP](
	[KHDM] [varchar](10) NOT NULL,
	[KHMC] [varchar](20) NULL,
	[MC] [varchar](60) NULL,
	[YWMC] [varchar](60) NULL,
	[TXDZ] [varchar](120) NULL,
	[YZBM] [varchar](16) NULL,
	[DH] [varchar](50) NULL,
	[FAX] [varchar](25) NULL,
	[EMAIL] [varchar](60) NULL,
	[WEB] [varchar](30) NULL,
	[LXR] [varchar](20) NULL,
	[KHLX] [varchar](4) NULL,
	[KHDJ] [varchar](1) NULL,
	[XYXD] [decimal](12, 2) NULL,
	[XYRQ] [varchar](10) NULL,
	[XYQX] [decimal](3, 0) NULL,
	[ZY] [varchar](4) NULL,
	[GJ] [varchar](4) NULL,
	[HY] [varchar](2) NULL,
	[YH] [varchar](40) NULL,
	[ZH] [varchar](30) NULL,
	[SH] [varchar](20) NULL,
	[BZ] [varchar](4) NULL,
	[JYR] [varchar](20) NULL,
	[JYRDH] [varchar](25) NULL,
	[DHR] [varchar](20) NULL,
	[DHRDH] [varchar](25) NULL,
	[FKR] [varchar](20) NULL,
	[FKRDH] [varchar](25) NULL,
	[FPDZ] [varchar](120) NULL,
	[JSFS] [varchar](4) NULL,
	[JSQX] [decimal](3, 0) NULL,
	[JGDM] [varchar](4) NULL,
	[XM] [varchar](20) NULL,
	[KHZT] [varchar](1) NULL,
	[XGR] [varchar](20) NOT NULL,
	[XGRQ] [varchar](10) NOT NULL,
	[BZXX] [varchar](60) NULL,
	[YJXS] [decimal](5, 3) NULL,
	[CFLAG] [varchar](1) NULL,
	[YLZD1] [varchar](100) NULL,
	[YLZD2] [varchar](100) NULL,
	[YLZD3] [varchar](100) NULL,
	[YLZD4] [decimal](15, 6) NULL,
	[YLZD5] [decimal](15, 6) NULL,
	[YLZD6] [decimal](15, 6) NULL,
	[PTJE] [decimal](15, 6) NULL,
PRIMARY KEY CLUSTERED 
(
	[KHDM] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]



客户分类

SELECT
        RTRIM(DM_ZHBMP.BM) AS BM ,
        RTRIM(DM_ZHBMP.BMSM) AS BMSM
      FROM DM_ZHBMP WHERE  DM_ZHBMP.BMLB = 'CGHT'


物资类型

SELECT
        RTRIM(DM_ZHBMP.BM) AS BM ,
        RTRIM(DM_ZHBMP.BMSM) AS BMSM
      FROM DM_ZHBMP WHERE  DM_ZHBMP.BMLB = 'WPLX'

客户名称

SELECT
      RTRIM(SD_KHXXP.KHDM) AS KHDM,
      RTRIM(SD_KHXXP.KHMC) AS KHMC
      FROM SD_KHXXP WHERE  KHDM in ( select distinct isnull(wpsyz,'') from im_ckwpkwp where ckh = '01' ) 


 SELECT       	IM_CKWPKWP.KWH			     as 	sValue1		,	 	isnull(IM_CKWPKWP.wpsyz,'') as 	sValue2			,	 	( select isnull(KHMC,'') from SD_KHXXP where khdm = IM_CKWPKWP.wpsyz ) as 	sValue3		,	 	SUM(IM_CKWPKWP.B1)  as 	dValue1			, 	MAX(IM_CKWPKWP.T1)  as 	dValue2			,		MAX(IM_CKWPKWP.T2) as 	dValue3			,		SUM(isnull(IM_CKWPKWP.QXKCL,0))		 as 	dValue4			,		SUM(isnull(IM_CKWPKWP.QXKCL1,0))		 as 	dValue5				 FROM IM_CKWPKWP WHERE CKH = '01' and isnull(fhzt,'1') = '1'  AND KHFL like '%'  AND WPFLM like '%'  AND isnull(wpsyz,'') like '%'  AND KWH IN ( SELECT KWH FROM DM_KWJBP WHERE CKH = '01') GROUP BY  CKH ,KWH ,wpsyz  ORDER BY KWH
 
 
 SELECT
        H1
        ,H2
        ,G1
        ,G2

        ,F1
        ,F2
        ,E1
        ,E2

        ,D1
        ,D2
        ,C1
        ,C2

        ,B1
        ,B2
        ,A1
        ,A2
        ,LSH1
        ,LSH
        FROM IM_KWXXP
        WHERE   (IM_KWXXP.LSH1>='01') And (IM_KWXXP.LSH1<='14') 
        ORDER BY LSH DESC
 

 