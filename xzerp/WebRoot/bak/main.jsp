<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.fenglian.Const"%>
<%@page import="com.fenglian.tools.util.CString"%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">  
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>现场物流及生产制造系统</title>  
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.2/themes/bootstrap/easyui.css">  
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.2/themes/mobile.css">  
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.2/themes/icon.css">
    <!-- <style type="text/css">
    	.panel-body {
		  background: url('../images/bg.jpg') repeat-x;
		  color: #333;
		  font-size: 12px;
		}
    </style>   -->
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.min.js"></script>  
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.easyui.min.js"></script> 
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.easyui.mobile.js"></script>
    <script type="text/javascript">
    	function logout(){
    		if(confirm('您确定要退出此系统？')){
    			window.location="logout.jsp";
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
                <div class="m-title">现场物流及生产制造系统导航</div>                
                <div class="m-right">
                    <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" outline="true" style="width:50px"  onclick="logout()">登出</a>
                </div>
            </div>
        </header>        
        <ul id=dl class="easyui-datalist" data-options="
                fit: true,
                lines: true,
                border: false
                "
                >
            <%
            	List list = (List)session.getAttribute(Const.USER_ROOTS);
            	if(list!=null){
            		Map obj;
            		String ms;
            		for(int i=0;i<list.size();i++){
            			obj = (Map)list.get(i);
            			ms = CString.rep(obj.get("ms"));
            			if(Const.M1.equals(ms)){
            				out.print("<li><a href=\"m1.jsp\" class=\"datalist-link\"><div><img src='../images/m1.png' class='imagea'/><span class='headeraaa'>"+Const.M1+"</span></div></a></li>");
            			}else if(Const.M2.equals(ms)){
            				out.print("<li><a href=\"m2.jsp\" class=\"datalist-link\"><div><img src='../images/m2.png' class='imagea'/><span class='headeraaa'>"+Const.M2+"</span></div></a></li>");
            			}
            		}
            	}
             %>
             
        </ul>
        <!-- <footer>
            <div class="m-toolbar">
                <div class="m-title" >中船重工物资贸易集团鲅鱼圈有限公司</div>
            </div>
        </footer>
        <footer style="border:0px;" >
            <div class="m-toolbar" style="background:#fff url('../images/footer.png');background-size:100% 100%;">            
                <div class="m-title" ><span>中船重工物资贸易集团鲅鱼圈有限公司</span></div>
            </div>
        </footer>  -->
         <footer style="border:1px" >
        	
		<div style='background:#fff url(../images/footer.png);background-size:100% 100%;height:56px;'>
						
		<div class="m-toolbar" >
		<span class="m-title" style="line-height: 22px">中船重工物资贸易集团鲅鱼圈有限公司</span>
		</div>
		</div>
			
		</footer> 
    </div>
    <style scoped>
		.imagea{
			width: 32px;
			height: 32px;
			text-align: center;vertical-align: middle;
		}
		.headeraaa{
			vertical-align: middle;margin-left: 15px;font-size: 16px;
			font-weight: bold;
		}		
	</style>
</body>    
</html>