<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.fenglian.Mobile"%>
<%boolean ismoblie=Mobile.JudgeIsMoblie(request); %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">  
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>�ֳ���������������ϵͳ</title>  
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.2/themes/bootstrap/easyui.css">  
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.2/themes/mobile.css">  
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.2/themes/icon.css">  
    <script type="text/javascript" src="jquery-easyui-1.4.2/jquery.min.js"></script>  
    <script type="text/javascript" src="jquery-easyui-1.4.2/jquery.easyui.min.js"></script> 
    <script type="text/javascript" src="jquery-easyui-1.4.2/jquery.easyui.mobile.js"></script>
    <script type="text/javascript">
    	function doLogin(){    		
    		var a = $('#loginform').form('validate');
    		if(a==true){
				//alert('��֤�ɹ�');
				var d=new Object();
				d.yhdm = $('#yhdm').textbox('getValue');
				d.pwd  = $('#pwd').textbox('getValue');
				$('#btn').linkbutton('disable');
				$.ajax({
					url:"jsonAction.do?method=exe&CMD=doLogin&d="+Math.random(),
					data:d,
					dataType:'json',
					type:'post',
					cache: false})
					.done(function(data) {
					   if(data){
							if(data.flag==true){
								//alert('��¼�ɹ�');
								window.location="mobile/main.jsp";
								//$.mobile.go('mobile/main.jsp');
							}else{
								alert(data.msg);
							}
						}else{
							alert('���������쳣�����Ժ����ԡ�����������');
						}
					})
					.fail(function() {
					    alert('���������쳣�����Ժ����ԡ�����������');
					})
					.always(function() {
					    $('#btn').linkbutton('enable');					    
					});
				}
							
    	}
    	$(document).ready(function () {
			
		});
    </script> 
</head>
<body>
    <div class="easyui-navpanel">
        <header>
            <div class="m-toolbar">
                <span class="m-title">��ӭ��¼�ֳ���������������ϵͳ</span>
            </div>
        </header>
        <div style="margin:20px auto;width:200px;height:154px;overflow:hidden">
            <img src="./images/logo.jpg" style="margin:0">
        </div>
        <div style="padding:0 20px;">
        	<form id="loginform">
            <div style="margin-bottom:10px">
                <input class="easyui-textbox" data-options="prompt:'�û���',iconCls:'icon-man',required:true,missingMessage:'�������û���'" style="width:100%;height:38px" id="yhdm">
            </div>
            <div>
                <input class="easyui-textbox" type="password" data-options="prompt:'����',iconCls:'icon-lock',required:true,missingMessage:'����������'" style="width:100%;height:38px" id="pwd">
            </div>
            <div style="text-align:center;margin-top:30px">
            	<%if(!ismoblie){%>
					  <a href="#" class="easyui-linkbutton" style="width:100%;height:40px" onclick="doLogin()" id="btn"><span style="font-size:16px">��¼</span></a> 		
    			<%}else{%>
    				  <a href="javascript:void(0);" class="easyui-linkbutton" style="width:100%;height:40px" onclick="doLogin()" id="btn"><span style="font-size:16px">��¼</span></a>
    			<%}%> 
                
            </div>            
            </form>
        </div>
        <footer>
            <div class="m-toolbar">
                <div class="m-title">�д��ع�����ó�׼�������Ȧ���޹�˾</div>
            </div>
        </footer>
    </div>
</body>    
</html>
