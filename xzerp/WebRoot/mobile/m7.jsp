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
    <title>�ֳ���������������ϵͳ</title>  
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
    		
    		
    		$('#dg').datagrid('load',{
    			rkrq_s:$('#rkrq_s').datebox('getValue'),
    			rkrq_e:$('#rkrq_e').datebox('getValue'),
    			bz:$('#bz').val()
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
    			url:'../jsonAction.do?method=exe&CMD=M7',
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
                <div class="m-title"><%=Const.M7 %></div>                
                <div class="m-left">
                    <a href="javascript:void(0);window.history.back();" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px">����</a>
                </div>
                <div class="m-right">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" id="btnsearch" onclick="dosearch()">��ѯ</a>
                </div>
            </div>
        </header>
        <div id="tab" class="easyui-tabs" data-options="fit:true,border:false,tabWidth:80,tabHeight:35">
        <div title="��ѯ����" style="padding:10px">
        	<form id="m1form">
	        	<ul class="m-list">
	        		<li><span>��ʼʱ��--����ʱ��</span><br><input id="rkrq_s" name="rkrq_s" class="easyui-datebox" style="width:40%" data-options="editable:false,panelWidth:220,panelHeight:240,iconWidth:30">--<input id="rkrq_e" name="rkrq_e" class="easyui-datebox" style="width:40%" data-options="editable:false,panelWidth:220,panelHeight:240,iconWidth:30"></li>
	        		<li><span>����</span><br><input name="bz" id="bz" class="easyui-textbox" style="width:80%"></li>
	        		
	        	</ul>
	        </form>            
        </div>
        <div title="�����б�">
        	<table id="dg" data-options="nowrap:false,showFooter:true,singleSelect:true,border:false,scrollbarSize:18,rownumbers:true,pagination:true,pagePosition:'top'" height="100%">
        		<thead data-options="frozen:true">
		            <tr>
		            	<th data-options="field:'JHQJ',width:100,align:'center'">�ƻ��ڼ�</th>
		            </tr>
		        </thead>
        		<thead>
		            <tr> 
		            	<th data-options="field:'BZ',width:300,align:'center',styler:cellStyler">����</th>
		            	<th data-options="field:'XGRY',width:100,align:'center'">������Ա</th>
		            	<th data-options="field:'XGRQ',width:120,align:'center'">��������</th>
		            </tr>
		        </thead>
        	</table>            
        </div>
        
    </div>                     
        <!-- <footer>
            <div class="m-toolbar">
                <div class="m-title" >�д��ع�����ó�׼�������Ȧ���޹�˾</div>
            </div>
        </footer> 
        <footer style="border:0px;" >
            <div class="m-toolbar" style="background:#fff url('../images/footer.png');background-size:100% 100%;">            
                <div class="m-title" ><span>�д��ع�����ó�׼�������Ȧ���޹�˾</span></div>
            </div>
        </footer> -->
        <footer style="border:1px" >
        	
		<div style='background:#fff url(../images/footer.png);background-size:100% 100%;height:56px;'>
						
		<div class="m-toolbar" >
		<span class="m-title" style="line-height: 22px">�д��ع�����ó�׼�������Ȧ���޹�˾</span>
		</div>
		</div>
			
		</footer>            
    </div>
    
</body>    
</html>