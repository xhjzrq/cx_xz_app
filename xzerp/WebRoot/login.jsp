<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="./jquery-easyui-1.4.2/jquery.min.js"></script>;
<script>
function dotj(id){
	$('#result').html('测试开始');
	$('#result').load($('#'+id).attr('action')+"&_didsndsids="+Math.random(),$('#'+id).serialize());
}
</script>
接口测试例子

说明
<ol>
<li>
form 的id 与 提交按钮的入参相同表示要提交表单
</li>
<li>
form 的action 为接口的地址
</li>
<li>
文本框的name为参数的入参名称
</li>
<li>
需要修改spring-DS.xml的数据库连接地址
</li>
</ol>
<form id="flogin" action="jsonAction.do?method=exe&CMD=login" >
用户登录<br>
 用户名:<input type=text name=yhdm />
 密码:<input type=text name=pwd />
<a href="javascript:void(0);" onclick="dotj('flogin');">提交</a>
</form>

<form id="fxgmm" action="jsonAction.do?method=exe&CMD=uppwd" >
修改密码<br>
 用户名:<input type=text name=yhdm />
 原密码:<input type=text name=opwd />
 新密码:<input type=text name=npwd />
 确认密码:<input type=text name=npwd1 />
<a href="javascript:void(0);" onclick="dotj('fxgmm');">提交</a>
</form>

<form id="fxjj01_qdj" action="jsonAction.do?method=exe&CMD=jj01_qdj" >
配送单据查询<br>
 WHERE条件:<input type=text name=where style="width:800px"/>
 BMBM条件:<input type=text name=dwbm />
<a href="javascript:void(0);" onclick="dotj('fxjj01_qdj');">提交</a>
</form>

<form id="fxjj01_qtp" action="jsonAction.do?method=exe&CMD=jj01_qtp" >
托盘明细查询JJ01_Qtp<br>
 WHERE条件:<input type=text name=where style="width:800px"/>
 调用类型:<input type=text name=dylx />
<a href="javascript:void(0);" onclick="dotj('fxjj01_qtp');">提交</a>
</form>

<form id="fxjj02_qdj" action="jsonAction.do?method=exe&CMD=jj02_qdj" >
计划查询JJ02_Qdj<br>
 开始日期:<input type=text name=ksrq />
 结束日期:<input type=text name=jsrq />
 工程编号:<input type=text name=czbh />
 <br/>
 需求单位码:<input type=text name=xqdwbm />
 配送部门:<input type=text name=sscj />
 接收人:<input type=text name=jsr />
 托盘表号:<input type=text name=tpbh />
 调用类型:<input type=text name=dylx />
 
<a href="javascript:void(0);" onclick="dotj('fxjj02_qdj');">提交</a>
</form>

<form id="fxjj03_qdj" action="jsonAction.do?method=exe&CMD=jj03_qdj" >
问题跟踪查询JJ03_Qdj<br>
 开始日期:<input type=text name=ksrq />
 结束日期:<input type=text name=jsrq />
 工程编号:<input type=text name=czbh />
 <br/>
 问题类型码:<input type=text name=wtlxbm />
 配送部门:<input type=text name=sscj />
 提出单位码:<input type=text name=tcdwbm />
 提出人员:<input type=text name=tcry />
 
<a href="javascript:void(0);" onclick="dotj('fxjj03_qdj');">提交</a>
</form>

<form id="fxjj05_qdj" action="jsonAction.do?method=exe&CMD=jj05_qdj" >
质量反馈查询JJ05_Qdj<br>
 开始日期:<input type=text name=ksrq />
 结束日期:<input type=text name=jsrq />
 工程编号:<input type=text name=czbh />
 <br/>
 风险类型码:<input type=text name=fxlxbm />
 提出单位码:<input type=text name=tcdwbm />
 提出人员:<input type=text name=tcry />
 用户代码:<input type=text name=yhdm />
 
<a href="javascript:void(0);" onclick="dotj('fxjj05_qdj');">提交</a>
</form>

<form id="fxjj05_add" action="jsonAction.do?method=exe&CMD=adjj05" >
质量反馈新增JJ05_Edit<br>
 用户账号:<input type=text name=yhdm />
 提出单位编码:<input type=text name=tcdwbm />
 提出人员:<input type=text name=tcry />
 提出日期:<input type=text name=tcrq />
 <br/>
 工程编号:<input type=text name=czbh />
 风险类型码:<input type=text name=fxlxbm />
 问题反馈:<input type=text name=wtms />
 
<a href="javascript:void(0);" onclick="dotj('fxjj05_add');">提交</a>
</form>

<form id="fxjj05_edit" action="jsonAction.do?method=exe&CMD=edjj05" >
质量反馈编辑JJ05_Edit<br>
 问题ID:<input type=text name=wtid />
 工程编号:<input type=text name=czbh />
 风险类型码:<input type=text name=fxlxbm />
 问题反馈:<input type=text name=wtms />
 
<a href="javascript:void(0);" onclick="dotj('fxjj05_edit');">提交1</a>
</form>

<form id="fxjj06_qdj" action="jsonAction.do?method=exe&CMD=jj06_qdj" >
舾装日报查询JJ06_Qdj<br>
 开始日期:<input type=text name=ksrq />
 结束日期:<input type=text name=jsrq />
 工程编号:<input type=text name=czbh />
 反馈单位:<input type=text name=fkdw />
 反馈人员:<input type=text name=fkry />
 <br/>
责任部门:<input type=text name=zrbm />
物件类别:<input type=text name=wjlb />
托盘表号:<input type=text name=tpbh />
 
<a href="javascript:void(0);" onclick="dotj('fxjj06_qdj');">提交</a>
</form>

<form id="fxjj06_up" action="jsonAction.do?method=exe&CMD=upjj06" >
舾装日报确认JJ06_Qdj<br>
 反馈ID:<input type=text name=mxid />
 确认人员:<input type=text name=qrry />
 确认备注:<input type=text name=qrbz />
<a href="javascript:void(0);" onclick="dotj('fxjj06_up');">提交</a>
</form>

<form id="fxjj07_qdj" action="jsonAction.do?method=exe&CMD=jj07_qdj" >
特殊管件查询JJ07_Qdj<br>
 开始日期:<input type=text name=ksrq />
 结束日期:<input type=text name=jsrq />
 工程编号:<input type=text name=czbh />
 <br/>
 管件类型:<input type=text name=tglx />
 需求单位:<input type=text name=xqdw />
 编号:<input type=text name=bh />
 管件号:<input type=text name=bjdh />
 
<a href="javascript:void(0);" onclick="dotj('fxjj07_qdj');">提交</a>
</form>

<div id="result">
</div>


