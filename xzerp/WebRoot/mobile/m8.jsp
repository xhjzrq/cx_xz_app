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
 c.add(Calendar.DATE,-30);
 String start=sdf.format(c.getTime());
 String zyls="";
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
    			GCBH:$('#GCBH').val()
    		});
    		
    		$('#tab').tabs('select',1);
    	}
    	
    	function cellStyler(value,row,index){    		
    		return 'background-color:#fdfde1;color:red;';
        }
    	
    	    	    
    	$(document).ready(function () {
    	
    		$('#dg').datagrid({
    			url:'../jsonAction.do?method=exe&CMD=M8',
    			queryParams:{},
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
                <div class="m-title"><%=Const.M8 %></div>                
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
	        		
	        		<li><span>��ͬ����</span><br><input name="GCBH" id="GCBH" class="easyui-textbox" style="width:80%"></li>
	        	  
	        				<%
	        					zyls="";
	        					it=jt.queryForList("SELECT RTRIM(SD_KHXXP.KHDM) AS KHDM,RTRIM(SD_KHXXP.KHMC) AS KHMC FROM SD_KHXXP").iterator();
	        					while(it.hasNext()){
	        						m= (Map)it.next();
	        						zyls+="if(value=='"+CString.rep(m.get("KHDM"))+"'){return '"+CString.rep(m.get("KHMC"))+"'}";
	        					}
	        				 %>
	        			
	        			<script>
		        			function khmcfmt(value,row,index){
			        			<%=zyls %>
			        		}
		        	
	        		
	        				<%
	        					zyls="";
	        					it=jt.queryForList("SELECT RTRIM(SD_SHDW.KHDM) AS KHDM,RTRIM(SD_SHDW.KHMC) AS KHMC FROM SD_SHDW").iterator();
	        					while(it.hasNext()){
	        						m= (Map)it.next();
	        						zyls+="if(value=='"+CString.rep(m.get("KHDM"))+"'){return '"+CString.rep(m.get("KHMC"))+"'}";
	        					}
	        				 %>
	        			
	        				function shdwfmt(value,row,index){
	        					<%=zyls %>
	        				}
	        				
	        				
	        			</script>
	        	</ul>
	        </form>            
        </div>
        <div title="�����б�">
        	<table id="dg" data-options="nowrap:false,singleSelect:true,border:false,scrollbarSize:18,rownumbers:true,pagination:true,pagePosition:'top'" height="100%">
        		<thead data-options="frozen:true">
		            <tr>
		               <th data-options="field:'GCBH',width:150,align:'center'">��ͬ����</th>	 
		               <th data-options="field:'XSHTH',width:150,align:'center'">��ͬ���</th>               
		            </tr>
		        </thead>
        		<thead>
		            <tr>
		            	<th data-options="field:'KHDM',width:150,align:'center',formatter:khmcfmt,styler:cellStyler">�ͻ�����</th>
		            	<th data-options="field:'ZZKHDM',width:150,align:'center',formatter:shdwfmt,styler:cellStyler">�ջ���λ</th>
		            	<th data-options="field:'XH',width:50,align:'center'">���</th>
		            	<th data-options="field:'JSYQ',width:100,align:'center'">��ҵҪ��</th>
		            	<th data-options="field:'JSYQSM',width:300,align:'center'">��ҵҪ��˵��</th>
		            	<th data-options="field:'BZ',width:100,align:'center'">��ע</th>
		            	<th data-options="field:'XGR',width:100,align:'center'">�޸���</th>
		            	<th data-options="field:'XGRQ',width:100,align:'center'">�޸�����</th>
		            	
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