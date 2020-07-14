<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.fenglian.Const"%>
<%@page import="com.fenglian.tools.extent.ApplicationContextUtil"%>
<%@page import="com.fenglian.tools.extent.JdbcTemplatePage"%>
<%@page import="com.fenglian.tools.util.CString"%>
<%
	JdbcTemplatePage jt =(JdbcTemplatePage) ApplicationContextUtil.getInstance().getBean("jdbcTemplatePage");
	Map m;
	Iterator it;
 %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">  
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>现场物流及生产制造系统</title>  
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.2/themes/bootstrap/easyui.css">  
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.2/themes/mobile.css">  
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.2/themes/icon.css">  
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.min.js"></script>  
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.easyui.min.js"></script> 
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="../jquery-easyui-1.4.2/locale/easyui-lang-zh_CN2.js" ></script>
    <script type="text/javascript">
    	function dosearch(){
    		$("input[name='ck']:checked").each(function(){
    			var v = $(this).val();			    			
				$('#dg').datagrid('showColumn',v+'1');
    			$('#dg').datagrid('showColumn',v+'2');
    		});
    		$("input[name='ck']").not(':checked').each(function(){
    			var v = $(this).val();			    			
				$('#dg').datagrid('hideColumn',v+'1');
    			$('#dg').datagrid('hideColumn',v+'2');
    		});
    		
    		$('#dg').datagrid('load',{
    			row1:$('#row1 option:selected').val(),
    			row2:$('#row2 option:selected').val()    			
    			});
    		
    		$('#tab').tabs('select',1);
    	}    	
    	$(document).ready(function () {
    		$("input[name='ck']").attr('checked',true);  		
    		$('#dg').datagrid({url:'../jsonAction.do?method=exe&CMD=M2',queryParams:{row1:'01',row2:'14'},
    			onClickCell:function(index,field,value){
    				if("LSH"!=field){
    					v=$.trim(value);
    					if(v.length!=0){
							alert($(this).html());
    					}
    				}    				
    			}
    		});
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
                <div class="m-title"><%=Const.M2 %></div>                
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
	        		<li><span>列选择</span><br>
	        			<table style="width: 80%">
	        				<tr>
	        					<td><input type="checkbox" name="ck" value="H"/><span>&nbsp;H&nbsp;</span></td>
	        					<td><input type="checkbox" name="ck" value="G"/><span>&nbsp;G&nbsp;</span></td>
	        					<td><input type="checkbox" name="ck" value="F"/><span>&nbsp;F&nbsp;</span></td>
	        					<td><input type="checkbox" name="ck" value="E"/><span>&nbsp;E&nbsp;</span></td>
	        				</tr>
	        				<tr>
	        					<td><input type="checkbox" name="ck" value="D"/><span>&nbsp;D&nbsp;</span></td>
	        					<td><input type="checkbox" name="ck" value="C"/><span>&nbsp;C&nbsp;</span></td>
	        					<td><input type="checkbox" name="ck" value="B"/><span>&nbsp;B&nbsp;</span></td>
	        					<td><input type="checkbox" name="ck" value="A"/><span>&nbsp;A&nbsp;</span></td>	        					
	        				</tr>
	        				
	        			</table>
					</li>
	        		<li><span>行选择</span><br>
	        			<select id="row1">
	        				<option value="01" selected="selected">01</option>
	        				<option value="02">02</option>
	        				<option value="03">03</option>
	        				<option value="04">04</option>
	        				<option value="05">05</option>
	        				<option value="06">06</option>
	        				<option value="07">07</option>
	        				<option value="08">08</option>
	        				<option value="09">09</option>
	        				<option value="10">10</option>
	        				<option value="11">11</option>
	        				<option value="12">12</option>
	        				<option value="13">13</option>
	        				<option value="14">14</option>
	        			</select>&nbsp;--&nbsp;<select id="row2">
	        				<option value="01">01</option>
	        				<option value="02">02</option>
	        				<option value="03">03</option>
	        				<option value="04">04</option>
	        				<option value="05">05</option>
	        				<option value="06">06</option>
	        				<option value="07">07</option>
	        				<option value="08">08</option>
	        				<option value="09">09</option>
	        				<option value="10">10</option>
	        				<option value="11">11</option>
	        				<option value="12">12</option>
	        				<option value="13">13</option>
	        				<option value="14" selected="selected">14</option>
	        			</select>
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
	        		</li> -->
	        	</ul>
	        </form>            
        </div>
        <div title="内容列表">
        	<table id="dg" data-options="singleSelect:true,border:false,scrollbarSize:0,rownumbers:true" height="100%">
        		<thead data-options="frozen:true">
		            <tr>
		                <th data-options="field:'LSH',width:50,align:'center'">序号</th>		                
		            </tr>
		        </thead>
        		<thead>
		            <tr>
		            	<th data-options="field:'H1',width:50,align:'center',styler:cellStyler">H1</th>
		            	<th data-options="field:'H2',width:50,align:'center',styler:cellStyler">H2</th>
		            	<th data-options="field:'G1',width:50,align:'center',styler:cellStyler">G1</th>
		            	<th data-options="field:'G2',width:50,align:'center',styler:cellStyler">G2</th>
		            	<th data-options="field:'F1',width:50,align:'center',styler:cellStyler">F1</th>
		            	<th data-options="field:'F2',width:50,align:'center',styler:cellStyler">F2</th>
		            	<th data-options="field:'E1',width:50,align:'center',styler:cellStyler">E1</th>
		            	<th data-options="field:'E2',width:50,align:'center',styler:cellStyler">E2</th>
		            	<th data-options="field:'D1',width:50,align:'center',styler:cellStyler">D1</th>
		            	<th data-options="field:'D2',width:50,align:'center',styler:cellStyler">D2</th>
		            	<th data-options="field:'C1',width:50,align:'center',styler:cellStyler">C1</th>
		            	<th data-options="field:'C2',width:50,align:'center',styler:cellStyler">C2</th>
		            	<th data-options="field:'B1',width:50,align:'center',styler:cellStyler">B1</th>
		            	<th data-options="field:'B2',width:50,align:'center',styler:cellStyler">B2</th>
		            	<th data-options="field:'A1',width:50,align:'center',styler:cellStyler">A1</th>
		            	<th data-options="field:'A2',width:50,align:'center',styler:cellStyler">A2</th>
		            </tr>
		        </thead>
        	</table>            
        </div>
        
    </div>                     
        <footer>
            <div class="m-toolbar">
                <div class="m-title">中船重工物资贸易集团鲅鱼圈有限公司</div>
            </div>
        </footer>
                            
    </div>
</body>    
</html>