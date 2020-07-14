<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.fenglian.Const"%>
<%@page import="com.fenglian.tools.util.CString"%>
<%
	String from =CString.rep( request.getParameter("devtoken"));
	String ismo =CString.rep( request.getParameter("ismo"));
	//System.out.println(from);
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
    <link rel="stylesheet" type="text/css" href="../promptumenu/promptumenu.css">
    <style type="text/css">
    	.panel-body {
		  background: url('../images/bg.jpg') repeat-x;
		  color: #333;
		  font-size: 12px;
		}
    </style>  
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.min.js"></script>  
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.easyui.min.js"></script> 
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="../promptumenu/jquery.promptumenu.min.js"></script>
    <script type="text/javascript">
    	function logout(){
    		if(confirm('您确定要退出此系统？')){
    			window.location="logout.jsp";
    		}
    	}
    	$(document).ready(function () {    		
			$('#dl').promptumenu({				
				'height': $('.panel-body').height()*0.8,
				rows: 3,
				columns: 2,
				direction: 'horizontal',
				pages: true
			});
			
			
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
               
        <ul id=dl style="text-align: center;"
                >
			<%
            	List list = (List)session.getAttribute(Const.USER_ROOTS);
            	if(list!=null){
            		Map obj;
            		String ms;
            		for(int i=0;i<list.size();i++){
            			obj = (Map)list.get(i);
            			ms = CString.rep(obj.get("ms"));
            		//	System.out.println(ms+(!CString.isEmpty(from)||"true".equals(ismo)));
            			//if("true".equals(ismo)||!CString.isEmpty(from)){
            			if(false){
	            			if(Const.M1.equals(ms)){
	            				out.print("<li><div ontouchstart=\"window.location.href='m1.jsp'\" class='la' ><div><div style='width: 100%'><img src='../images/x.bmp'/></div><span class='m-title headeraaa'>"+Const.M1+"</span></div></div></li>");
	            			}else if(Const.M2.equals(ms)){
	            				out.print("<li><div ontouchstart=\"window.location.href='m2.jsp'\" class='la' ><div><div style='width: 100%'><img src='../images/x.bmp'/></div><span class='m-title headeraaa'>"+Const.M2+"</span></div></div></li>");
	            			}else if(Const.M3.equals(ms)){
	            				out.print("<li><div ontouchstart=\"window.location.href='m2.jsp'\" class='la' ><div><div style='width: 100%'><img src='../images/x.bmp'/></div><span class='m-title headeraaa'>"+Const.M3+"</span></div></div></li>");
	            			}else if(Const.M4.equals(ms)){
	            				out.print("<li><div ontouchstart=\"window.location.href='m2.jsp'\" class='la' ><div><div style='width: 100%'><img src='../images/x.bmp'/></div><span class='m-title headeraaa'>"+Const.M4+"</span></div></div></li>");
	            			}else if(Const.M5.equals(ms)){
	            				out.print("<li><div ontouchstart=\"window.location.href='m2.jsp'\" class='la' ><div><div style='width: 100%'><img src='../images/x.bmp'/></div><span class='m-title headeraaa'>"+Const.M5+"</span></div></div></li>");
	            			}
            			}else{
            				if(Const.M1.equals(ms)){
	            				out.print("<li><a href=\"m1.jsp\" class='la' onclick='gt(\"m1\")'><div><div style='width: 100%'><img src='../images/x.bmp'/></div><span class='m-title headeraaa'>"+Const.M1+"</span></div></a></li>");
	            			}else if(Const.M2.equals(ms)){
	            				out.print("<li><a href=\"m2.jsp\" class='la' onclick='gt(\"m2\")'><div><div style='width: 100%'><img src='../images/x.bmp'/></div><span class='m-title headeraaa'>"+Const.M2+"</span></div></a></li>");
	            			}else if(Const.M3.equals(ms)){
	            				out.print("<li><a href=\"m3.jsp\" class='la' onclick='gt(\"m3\")'><div><div style='width: 100%'><img src='../images/x.bmp'/></div><span class='m-title headeraaa'>"+Const.M3+"</span></div></a></li>");
	            			}else if(Const.M4.equals(ms)){
	            				out.print("<li><a href=\"m4.jsp\" class='la' onclick='gt(\"m4\")'><div><div style='width: 100%'><img src='../images/x.bmp'/></div><span class='m-title headeraaa'>"+Const.M4+"</span></div></a></li>");
	            			}else if(Const.M5.equals(ms)){
	            				out.print("<li><a href=\"m5.jsp\" class='la'  onclick='gt(\"m1\")'><div><div style='width: 100%'><img src='../images/x.bmp'/></div><span class='m-title headeraaa'>"+Const.M5+"</span></div></a></li>");
	            			}
            				
            			}
            		}
            	}
             %> 
           
        </ul>       
         <footer style="border:1px" >
        	
		<div style='background:#fff url(../images/footer.png);background-size:100% 100%;height:56px;'>
						
		<div class="m-toolbar" >
		<span class="m-title" style="line-height: 22px">中船重工物资贸易集团鲅鱼圈有限公司</span>
		</div>
		</div>
			
		</footer> 
    </div>
    <style scoped>
    	.la{
    		text-decoration: none;
    		color:#000000;    	
    	}
		
		.headeraaa{
			font-size: 16px;
			font-weight: bold;
			line-height: 22px;
		}		
	</style>
</body>    
</html>