<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.fenglian.Const"%>
<%@page import="com.fenglian.tools.extent.ApplicationContextUtil"%>
<%@page import="com.fenglian.tools.extent.JdbcTemplatePage"%>
<%@page import="com.fenglian.tools.util.CString"%>
<%@page import="com.fenglian.Mobile"%>
<%
	String zyls="";
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
    			DEVTOKEN:'${USER_TOKEN}',
    			HTBH:$('#HTBH').textbox('getValue'),
    			khmc:$('#khmc').val(),//客户名称
    			HTZX:$('#HTZX').textbox('getValue'),
    			wgbs:$('#wgbs').val()
    		});
    		
    		$('#tab').tabs('select',1);
    	}
    	
    	function cellStyler(value,row,index){    		
    		//return 'background-color:#fdfde1;color:red;';
        }
    	
    	    	    
    	$(document).ready(function () {
    		$('#dg').datagrid({
    			url:'../jsonAction.do?method=exe&CMD=M5',
    			queryParams:{},
    			onLoadError:function(){
    				alert('请稍后再试！');
    			},
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
			$('#tab').tabs('select',0)		
		});
    </script> 
</head>
<body>
    <div class="easyui-navpanel" style="position:relative">
        <header>
            <div class="m-toolbar">                
                <div class="m-title"><%=Const.M5 %></div>                
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
	        		
	        		<li><span>客户名称</span><br>
	        			<select id="khmc" style="width: 80%">
	        				<option value="">请选择</option>
	        				<%
	        					//it=jt.queryForList("SELECT RTRIM(SD_KHXXP.KHDM) AS KHDM,RTRIM(SD_KHXXP.KHMC) AS KHMC FROM SD_KHXXP WHERE  KHDM in ( select distinct isnull(wpsyz,'') from im_ckwpkwp where ckh = '01' )").iterator();
	        					it=jt.queryForList("SELECT RTRIM(SD_KHXXP.KHDM) AS KHDM, RTRIM(SD_KHXXP.KHMC) AS KHMC FROM SD_KHXXP").iterator();
	        					while(it.hasNext()){
	        						m= (Map)it.next();
	        						out.print("<option value='"+CString.rep(m.get("KHDM"))+"'>"+CString.rep(m.get("KHMC"))+"</option>");
	        					}
	        				 %>
	        			</select>
	        			
	        		</li>
	        		<li><span>合同编号</span><br><input name="HTBH" id="HTBH" class="easyui-textbox" style="width:80%"></li>
	        		<li><span>合同子项</span><br><input name="HTZX" id="HTZX" class="easyui-textbox" style="width:80%"></li>
	        		
	        		<li><span>完工标识</span><br>
	        			<select id="wgbs" style="width: 80%">
	        				<option value="A">全部</option>
	        				<option value="N">未完</option>
	        				<option value="W">已完</option>
	        			</select>
					</li>
	        		
	        	</ul>
	        </form>            
        </div>
        <div title="内容列表">
        	<table id="dg" data-options="showFooter:true,singleSelect:true,border:false,scrollbarSize:18,rownumbers:true,pagination:true,pagePosition:'top'" height="100%">
        		<thead data-options="frozen:true">
		            <tr>
		            	<!-- <th data-options="field:'XH',width:40,align:'center'">序号</th> -->
		            	<th data-options="field:'HTBH',width:150,align:'center'">合同编号</th>
		            	<th data-options="field:'HTZX',width:150,align:'center'">合同子项</th>
		               
		                		                
		            </tr>
		        </thead>
        		<thead>
		            <tr> 
		            	<th data-options="field:'KHMC',width:120,align:'center'">客户名称</th>
		            	<th data-options="field:'WLLB',width:80,align:'center'">物量类别</th>
		            	<th data-options="field:'WL',width:80,align:'right',halign:'center'">物量</th>
		            	<th data-options="field:'DW',width:80,align:'center'">单位</th>
		            	<th data-options="field:'JHKGRQ',width:80,align:'center'">计划开工日期</th>
		            	<th data-options="field:'JHWGRQ',width:80,align:'center'">计划完工日期</th>
		            	<th data-options="field:'JHGQ',width:80,align:'center'">计划工期/天</th>
		            	<th data-options="field:'RKL',width:80,align:'right',halign:'center'">入库量</th>
		            	<th data-options="field:'YCLL',width:80,align:'right',halign:'center'">预处理量</th>
		            	<th data-options="field:'QGL',width:80,align:'right',halign:'center'">切割量</th>
		            	<th data-options="field:'FCL',width:80,align:'right',halign:'center'">发出量</th>
		            	<th data-options="field:'KC',width:80,align:'right',halign:'center'">库存</th>
		            	
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