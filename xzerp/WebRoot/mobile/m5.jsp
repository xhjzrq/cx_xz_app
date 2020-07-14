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
    <title>�ֳ���������������ϵͳ</title>  
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
    			khmc:$('#khmc').val(),//�ͻ�����
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
    				alert('���Ժ����ԣ�');
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
	        		
	        		<li><span>�ͻ�����</span><br>
	        			<select id="khmc" style="width: 80%">
	        				<option value="">��ѡ��</option>
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
	        		<li><span>��ͬ���</span><br><input name="HTBH" id="HTBH" class="easyui-textbox" style="width:80%"></li>
	        		<li><span>��ͬ����</span><br><input name="HTZX" id="HTZX" class="easyui-textbox" style="width:80%"></li>
	        		
	        		<li><span>�깤��ʶ</span><br>
	        			<select id="wgbs" style="width: 80%">
	        				<option value="A">ȫ��</option>
	        				<option value="N">δ��</option>
	        				<option value="W">����</option>
	        			</select>
					</li>
	        		
	        	</ul>
	        </form>            
        </div>
        <div title="�����б�">
        	<table id="dg" data-options="showFooter:true,singleSelect:true,border:false,scrollbarSize:18,rownumbers:true,pagination:true,pagePosition:'top'" height="100%">
        		<thead data-options="frozen:true">
		            <tr>
		            	<!-- <th data-options="field:'XH',width:40,align:'center'">���</th> -->
		            	<th data-options="field:'HTBH',width:150,align:'center'">��ͬ���</th>
		            	<th data-options="field:'HTZX',width:150,align:'center'">��ͬ����</th>
		               
		                		                
		            </tr>
		        </thead>
        		<thead>
		            <tr> 
		            	<th data-options="field:'KHMC',width:120,align:'center'">�ͻ�����</th>
		            	<th data-options="field:'WLLB',width:80,align:'center'">�������</th>
		            	<th data-options="field:'WL',width:80,align:'right',halign:'center'">����</th>
		            	<th data-options="field:'DW',width:80,align:'center'">��λ</th>
		            	<th data-options="field:'JHKGRQ',width:80,align:'center'">�ƻ���������</th>
		            	<th data-options="field:'JHWGRQ',width:80,align:'center'">�ƻ��깤����</th>
		            	<th data-options="field:'JHGQ',width:80,align:'center'">�ƻ�����/��</th>
		            	<th data-options="field:'RKL',width:80,align:'right',halign:'center'">�����</th>
		            	<th data-options="field:'YCLL',width:80,align:'right',halign:'center'">Ԥ������</th>
		            	<th data-options="field:'QGL',width:80,align:'right',halign:'center'">�и���</th>
		            	<th data-options="field:'FCL',width:80,align:'right',halign:'center'">������</th>
		            	<th data-options="field:'KC',width:80,align:'right',halign:'center'">���</th>
		            	
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