<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.fenglian.Const"%>
<%@page import="com.fenglian.tools.extent.ApplicationContextUtil"%>
<%@page import="com.fenglian.tools.extent.JdbcTemplatePage"%>
<%@page import="com.fenglian.tools.util.CString"%>
<%@page import="com.fenglian.Mobile"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
 SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
 Calendar c=Calendar.getInstance();
 String end=sdf.format(c.getTime());
 c.add(Calendar.DATE,-1);
 String start=sdf.format(c.getTime());
 %>
<%
	JdbcTemplatePage jt =(JdbcTemplatePage) ApplicationContextUtil.getInstance().getBean("jdbcTemplatePage");
	Map m;
	Iterator it;
	boolean ismoblie=Mobile.JudgeIsMoblie(request);
 %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="telephone=no,email=no" name="format-detection">      
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>现场物流及生产制造系统</title>  
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.2/themes/bootstrap/easyui.css">  
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.2/themes/mobile.css">  
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.2/themes/icon.css">  
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.min.js"></script>  
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.easyui.min.js"></script> 
     <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.easyui.extends.js"></script> 
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="../jquery-easyui-1.4.2/locale/easyui-lang-zh_CN2.js" ></script>
    <script type="text/javascript">
    	function dosearch(){
    		var ckstr='';
    		
    		$("input[name='ck']:checked").each(function(){
    			var v = $(this).val();			    			
				if(ckstr!=''){
					ckstr+=','+v;
				}else{
					ckstr+=v;
				}
    		});
    		
    		//alert(ckstr);
    		//return ;
    		
    		$('#dg').datagrid('load',{
    			JHBH:$('#JHBH').textbox('getValue'),
    			rkrq_s:$('#rkrq_s').datebox('getValue'),
    			rkrq_e:$('#rkrq_e').datebox('getValue'),
    			khmc:$('#khmc').val(),//客户名称
    			HTZX:$('#HTZX').textbox('getValue'),
    			DJH:$('#DJH').textbox('getValue'),
    			zylx:$('#zylx').val(),
    		//	wcl:$('#wcl').val(),
    			cks:ckstr
    		});
    		
    		$('#tab').tabs('select',1);
    	}
    	
    	function cellStyler(value,row,index){    		
    		return 'background-color:#fdfde1;color:red;';
        }
    	
    	    	    
    	$(document).ready(function () {
 //	 $('#rkrq_s').datebox('setValue', '<%=start%>');
 //   		$('#rkrq_e').datebox('setValue', '<%=end%>');    
    		$('#dg').datagrid({
    			url:'../jsonAction.do?method=exe&CMD=M4',
    			queryParams:{orderkey:$('#orderkey').val()},
    			pageSize:20
    		});
    		pager=$('#dg').datagrid('getPager');
    		pager.pagination({showPageList:false,showRefresh:false});
    		$('#tab').tabs({
			  onSelect: function(title,index){
				if(index==0){
					$('#btnsearch').linkbutton('enable');
				}else{
					$('#btnsearch').linkbutton('disable');
				}
				return false;
			  }
			});	
			$('#tab').tabs('select',1)		
		});
    </script> 
</head>
<body>
    <div class="easyui-navpanel" style="position:relative">
        <header>
            <div class="m-toolbar">                
                <div class="m-title"><%=Const.M4 %></div>                
                <div class="m-left">
                    <a href="javascript:void(0);window.history.back();" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px">返回</a>
                </div>
                <div class="m-right">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" id="btnsearch" onclick="dosearch()">查询</a>
                </div>
            </div>
        </header>
        <div id="tab" class="easyui-tabs" data-options="fit:true,border:false,tabWidth:80,tabHeight:35">
        <div title="查询条件" style="padding:10px">
        	<form id="m1form">
	        	<ul class="m-list">
	        		<li><span>计划编号</span><br><input name="JHBH" id="JHBH" class="easyui-textbox" style="width:80%"></li>
	        		<li><span>开始时间--结束时间</span><br><input id="rkrq_s" name="rkrq_s" class="easyui-datebox" style="width:40%" data-options="editable:false,panelWidth:220,panelHeight:240,iconWidth:30">--<input id="rkrq_e" name="rkrq_e" class="easyui-datebox" style="width:40%" data-options="editable:false,panelWidth:220,panelHeight:240,iconWidth:30"></li>
	        		<li><span>合同子项</span><br><input name="HTZX" id="HTZX" class="easyui-textbox" style="width:80%"></li>
	        		<li><span>单据号</span><br><input name="DJH" id="DJH" class="easyui-textbox" style="width:80%"></li>
	        		<li><span>客户名称</span><br>
	        			<select id="khmc" style="width: 80%">
	        				<option value="">请选择</option>
	        				<%
	        				String zyls="";
	        					//it=jt.queryForList("SELECT RTRIM(SD_KHXXP.KHDM) AS KHDM,RTRIM(SD_KHXXP.KHMC) AS KHMC FROM SD_KHXXP WHERE  KHDM in ( select distinct isnull(wpsyz,'') from im_ckwpkwp where ckh = '01' )").iterator();
	        					it=jt.queryForList("SELECT RTRIM(SD_KHXXP.KHDM) AS KHDM, RTRIM(SD_KHXXP.KHMC) AS KHMC FROM SD_KHXXP").iterator();
	        					while(it.hasNext()){
	        						m= (Map)it.next();
	        						out.print("<option value='"+CString.rep(m.get("KHDM"))+"'>"+CString.rep(m.get("KHMC"))+"</option>");
	        						zyls+="if(value=='"+CString.rep(m.get("KHDM"))+"'){return '"+CString.rep(m.get("KHMC"))+"'}";
	        					}
	        				 %>
	        			</select>
	        			<script>
		        			function khmcfmt(value,row,index){
			        			<%=zyls %>
			        		}
			        	</script>
	        		</li>
	        		<li><span>作业类型</span><br>
	        			<select id="zylx" style="width: 80%">
	        				<option value="">请选择</option>
	        				<%
	        					it=jt.queryForList("SELECT RTRIM(DM_ZHBMP.BM) AS BM , RTRIM(DM_ZHBMP.BMSM) AS BMSM FROM DM_ZHBMP WHERE DM_ZHBMP.BMLB = 'HDL3'").iterator();
	        					zyls="";
	        					while(it.hasNext()){
	        						m= (Map)it.next();
	        						out.print("<option value='"+CString.rep(m.get("BM"))+"'>"+CString.rep(m.get("BMSM"))+"</option>");
	        						zyls+="if(value=='"+CString.rep(m.get("BM"))+"'){return '"+CString.rep(m.get("BMSM"))+"'}";
	        					}
	        				 %>
	        			</select>
	        			<script>
	        				function zylffmt(value,row,index){
	        					<%=zyls %>
	        				}
	        				
	        				<%
	        					it=jt.queryForList("SELECT RTRIM(DM_ZHBMP.BM) AS BM ,RTRIM(DM_ZHBMP.BMSM) AS BMSM FROM DM_ZHBMP WHERE  DM_ZHBMP.BMLB = 'JHJB' and  bm in ('03','05')").iterator();
	        					zyls="";
	        					while(it.hasNext()){
	        						m= (Map)it.next();
	        						
	        						zyls+="if(value=='"+CString.rep(m.get("BM"))+"'){return '"+CString.rep(m.get("BMSM"))+"'}";
	        					}
	        				 %>
	        				 function jffmt(value,row,index){
	        					<%=zyls %>
	        				 }
	        				 
	        				 <%
	        					it=jt.queryForList("SELECT RTRIM(DM_ZHBMP.BM) AS BM , RTRIM(DM_ZHBMP.BMSM) AS BMSM FROM DM_ZHBMP WHERE DM_ZHBMP.BMLB = 'HDL4'").iterator();
	        					zyls="";
	        					while(it.hasNext()){
	        						m= (Map)it.next();
	        						
	        						zyls+="if(value=='"+CString.rep(m.get("BM"))+"'){return '"+CString.rep(m.get("BMSM"))+"'}";
	        					}
	        				 %>
	        				 function wzlxfmt(value,row,index){
	        					<%=zyls %>
	        				 }
	        			</script>
	        		
	        		</li>
	        		<!-- <li><span>完成率</span><br>
	        			<select id="wcl" style="width: 80%">
	        				<option value="">请选择</option>
	        			</select>
	        		</li> -->
	        		<li><span>&nbsp;</span><br>
	        			<table style="width: 80%">
	        				<tr>
	        					<td><input type="checkbox" name="ck" value="H"/><span>&nbsp;只查未完成计划&nbsp;</span></td>
	        					<td><input type="checkbox" name="ck" value="G"/><span>&nbsp;只查询作业单&nbsp;</span></td>
	        				</tr>
	        				<tr>
	        					<td><input type="checkbox" name="ck" value="F"/><span>&nbsp;合计行只统计已完成作业单&nbsp;</span></td>
	        					<td><input type="checkbox" name="ck" value="D"/><span>&nbsp;只查询当天计划&nbsp;</span></td>
	        				</tr>
	        				<tr>
	        					<td><input type="checkbox" name="ck" value="C"/><span>&nbsp;只查询未完成作业单&nbsp;</span></td>
	        					<td>&nbsp;</td>
	        				</tr>
	        				
	        			</table>
					</li>
	        		<!-- <li><span>客户分类</span><br>
	        			<select id="khfl" style="width: 80%">
	        				<%
	        					it=jt.queryForList("SELECT RTRIM(DM_ZHBMP.BM) AS BM ,RTRIM(DM_ZHBMP.BMSM) AS BMSM FROM DM_ZHBMP WHERE  DM_ZHBMP.BMLB = 'CGHT'").iterator();
	        					while(it.hasNext()){
	        						m= (Map)it.next();
	        						out.print("<option value='"+CString.rep(m.get("BM"))+"'>"+CString.rep(m.get("BMSM"))+"</option>");
	        					}
	        				 %>
	        			</select>
	        		</li>
	        		<li><span>物资类别</span><br>
	        			<select id="wzlb" style="width: 80%">
	        				<%
	        					it=jt.queryForList("SELECT RTRIM(DM_ZHBMP.BM) AS BM ,RTRIM(DM_ZHBMP.BMSM) AS BMSM FROM DM_ZHBMP WHERE  DM_ZHBMP.BMLB = 'WPLX'").iterator();
	        					while(it.hasNext()){
	        						m= (Map)it.next();
	        						out.print("<option value='"+CString.rep(m.get("BM"))+"'>"+CString.rep(m.get("BMSM"))+"</option>");
	        					}
	        				 %>
	        			</select>
	        		</li>
	        		<li><span>客户名称</span><br>
	        				<select id="wzlb" style="width: 80%">
	        				<%
	        					it=jt.queryForList("SELECT RTRIM(SD_KHXXP.KHDM) AS KHDM,RTRIM(SD_KHXXP.KHMC) AS KHMC FROM SD_KHXXP WHERE  KHDM in ( select distinct isnull(wpsyz,'') from im_ckwpkwp where ckh = '01' )").iterator();
	        					while(it.hasNext()){
	        						m= (Map)it.next();
	        						out.print("<option value='"+CString.rep(m.get("KHDM"))+"'>"+CString.rep(m.get("KHMC"))+"</option>");
	        					}
	        				 %>
	        			</select>
	        		</li>  -->
	        	</ul>
	        </form>            
        </div>
        <div title="内容列表">
        	<table id="dg" data-options="showFooter:true,singleSelect:true,border:false,scrollbarSize:18,rownumbers:true,pagination:true,pagePosition:'top'" height="100%">
        		<thead data-options="frozen:true">
		            <tr>
		            	<th data-options="field:'KHMC',width:150,align:'center',formatter:khmcfmt">客户名称</th>
		                <th data-options="field:'JHBH',width:100,align:'center',styler:cellStyler">计划编号</th>
		               
		                		                
		            </tr>
		        </thead>
        		<thead>
		            <tr> 
		            	<th data-options="field:'HTZX',width:100,align:'center',styler:cellStyler">合同子项</th>
		            	<th data-options="field:'KSRQ',width:100,align:'center',styler:cellStyler">开始日期</th>
		            	<th data-options="field:'JSRQ',width:100,align:'center',styler:cellStyler">结束日期</th>
		            	<th data-options="field:'JHDW',width:100,align:'center',formatter:jffmt">计划单位</th>
		            	<th data-options="field:'ZYLX',width:100,align:'center',formatter:zylffmt">作业类型</th>
		            	<th data-options="field:'WZLX',width:100,align:'center',formatter:wzlxfmt">物资类型</th>
		            	<th data-options="field:'SL',width:100,align:'right',halign:'center',styler:cellStyler">数量</th>
		            	<th data-options="field:'ZL',width:100,align:'right',halign:'center',styler:cellStyler">重量</th>
		            	<th data-options="field:'LJSL',width:100,align:'right',halign:'center',styler:cellStyler">零件数量</th>
		            	<th data-options="field:'CS',width:100,align:'right',halign:'center',styler:cellStyler">车数</th>
		            	<th data-options="field:'CH',width:100,align:'center'">车号</th>
		            	<th data-options="field:'ZYSJF',width:100,align:'right',halign:'center'">作业时间分</th>
		            	<th data-options="field:'XDL',width:100,align:'right',halign:'center'">下达率</th>
		            	<th data-options="field:'WCL',width:100,align:'right',halign:'center'">完成率</th>
		            	<th data-options="field:'DJH',width:100,align:'center'">单据号</th>
		            	<th data-options="field:'SSZYDH',width:100,align:'center'">所属作业单号</th>
		            	<th data-options="field:'BZRQ',width:150,align:'center',styler:cellStyler">编制日期</th>
		            	<th data-options="field:'DJCX',width:100,align:'center'">单据查询</th>
		            </tr>
		        </thead>
        	</table>            
        </div>
        
    </div>                     
        <!-- <footer>
            <div class="m-toolbar">
                <div class="m-title" >中船重工物资贸易集团鲅鱼圈有限公司</div>
            </div>
        </footer> 
        <footer style="border:0px;" >
            <div class="m-toolbar" style="background:#fff url('../images/footer.png');background-size:100% 100%;">            
                <div class="m-title" ><span>中船重工物资贸易集团鲅鱼圈有限公司</span></div>
            </div>
        </footer> -->
        <footer style="border:1px" >
        	
		<div style='background:#fff url(../images/footer.png);background-size:100% 100%;height:56px;'>
						
		<div class="m-toolbar" >
		<span class="m-title" style="line-height: 22px">中船重工物资贸易集团鲅鱼圈有限公司</span>
		</div>
		</div>
			
		</footer>            
    </div>
    
</body>    
</html>