select sjx,max(tjrq) c1,sum(rljwcl) c3,sum(rljpsl) c5 
select tb.sjx,max(tjrq),sum(tb.RLJWCL) c3,sum(tb.RLJPSL) c5 from 
(select BM,CZBH,tjrq,datediff(day,'2012/02/19',tjrq)/7 as sjx,RYGSWL,RLJWCL,RLJPSL from SC_SCWLTJB where tjrq>='2012/02/19' and tjrq<='2015/02/19') tb 
group by tb.sjx


select tb.sjx,max(tjrq),sum(tb.RLJWCL) c3,sum(tb.RLJPSL) c5 from 
(select BM,CZBH,tjrq,cast( datediff(day,'2012/02/19',tjrq) as int)/7 as sjx,RYGSWL,RLJWCL,RLJPSL from SC_SCWLTJB where tjrq>='2012/02/19' and tjrq<='2015/02/19') tb 
group by tb.sjx

select tb.sjx,max(tjrq),sum(tb.RLJWCL) c3,sum(tb.RLJPSL) c5 from 
(select BM,CZBH,tjrq,cast( datediff(day,'2012/02/19',tjrq) as int)/7 as sjx,RYGSWL,RLJWCL,RLJPSL from SC_SCWLTJB where tjrq>='2012/02/19' and tjrq<='2015/02/19') tb 
group by tb.sjx

select count(1),tb.sjx from(
select BM,CZBH,tjrq,(cast(datediff(day,'2012/02/19',tjrq) as int)/7) as sjx,RYGSWL,RLJWCL,RLJPSL from SC_SCWLTJB where tjrq>='2012/02/19' and tjrq<='2015/02/19'
) tb
group by tb.sjx



select tb.sjx,max(tjrq),min(tjrq),sum(RLJWCL),sum(RLJPSL) from 
(select BM,CZBH,tjrq,
(cast(DATEDIFF(day,'2012/02/19',convert(date,tjrq)) as int)/7) as sjx,
RYGSWL,RLJWCL,RLJPSL 
from SC_SCWLTJB
where TJRQ >= '2012/02/19' and TJRQ <= '2013/02/19') as tb

group by tb.sjx order by tb.sjx
select * from SC_SCWLTJB where tjrq like '%-%'
select convert(date,tjrq,'yyyy/mm/dd') from SC_SCWLTJB
//select max(tjrq) from SC_SCWLTJB

delete from SC_SCWLTJB where tjrq like '%-%'



select tb.sjx,max(tjrq) maxdate,min(tjrq) mindate,sum(RYGSWL) RYGSWL,sum(RLJWCL) RLJWCL,sum(RLJPSL) RLJPSL from 
(select BM,CZBH,tjrq,
(cast(DATEDIFF(day,'2012/02/19',convert(date,tjrq)) as int)/7) as sjx,
RYGSWL,RLJWCL,RLJPSL 
from SC_SCWLTJB
where TJRQ >= '2012/02/19' and TJRQ <= '2013/02/19') as tb

group by tb.sjx order by tb.sjx


select * from SC_SCWLTJB where tjrq>='2013/02/16' and tjrq<='2013/02/19'
