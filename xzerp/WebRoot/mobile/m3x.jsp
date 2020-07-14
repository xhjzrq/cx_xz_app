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
    			JHBH:$('#JHBH').textbox('getValue'),
    			rkrq_s:$('#rkrq_s').datebox('getValue'),
    			rkrq_e:$('#rkrq_e').datebox('getValue'),
    			khdm:$('#khdm').val(),
    			shdw:$('#shdw').val(),
    			HTZX:$('#HTZX').textbox('getValue'),
    			zylx:$('#zylx').val(),
    			jhzt:$('#jhzt').val(),
    			tjlsdwl:$('#tjlsdwl').val(),
    			orderkey:$('#orderkey').val()
    		});
    		
    		$('#tab').tabs('select',1);
    	}
    	
    	function cellStyler(value,row,index){    		
    		return 'background-color:#fdfde1;color:red;';
        }
    	
    	    	    
    	$(document).ready(function () {
    		//$('#rkrq_s').datebox('setValue', '<%=start%>');
    		//$('#rkrq_e').datebox('setValue', '<%=end%>');    
    		$('#dg').datagrid({
    			url:'../jsonAction.do?method=exe&CMD=M3',
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
                <div class="m-title"><%=Const.M3 %></div>                
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
	        		<li><span>�ƻ����</span><br><input name="JHBH" id="JHBH" class="easyui-textbox" style="width:80%"></li>
	        		<li><span>��ͬ����</span><br><input name="HTZX" id="HTZX" class="easyui-textbox" style="width:80%"></li>
	        	    <li><span>��ʼʱ��--����ʱ��</span><br><input id="rkrq_s" name="rkrq_s" class="easyui-datebox" style="width:40%" data-options="editable:false,panelWidth:220,panelHeight:240,iconWidth:30">--<input id="rkrq_e" name="rkrq_e" class="easyui-datebox" style="width:40%" data-options="editable:false,panelWidth:220,panelHeight:240,iconWidth:30"></li>
	        	    <li><span>�ͻ�����</span><br>
	        				<select id="khdm" style="width: 80%">
	        					<option value="">��ѡ��</option>
	        				<%
	        					String zyls="";
	        					it=jt.queryForList("SELECT RTRIM(SD_KHXXP.KHDM) AS KHDM,RTRIM(SD_KHXXP.KHMC) AS KHMC FROM SD_KHXXP").iterator();
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
	        		<li><span>�ջ���λ</span><br>
	        			<select id="shdw" style="width: 80%">
	        				<option value="">��ѡ��</option>
	        				<%
	        					zyls="";
	        					it=jt.queryForList("SELECT RTRIM(SD_SHDW.KHDM) AS KHDM,RTRIM(SD_SHDW.KHMC) AS KHMC FROM SD_SHDW").iterator();
	        					while(it.hasNext()){
	        						m= (Map)it.next();
	        						out.print("<option value='"+CString.rep(m.get("KHDM"))+"'>"+CString.rep(m.get("KHMC"))+"</option>");
	        						zyls+="if(value=='"+CString.rep(m.get("KHDM"))+"'){return '"+CString.rep(m.get("KHMC"))+"'}";
	        					}
	        				 %>
	        				 
	        			</select>
	        			<script>
		        			function dwfmt(value,row,index){
		        				<%=zyls %>
		        			}
		        			
		        			<%
		        				zyls="";
		        				it=jt.queryForList("SELECT RTRIM(MS_YSGSJBP.GSBM) AS KHDM ,RTRIM(MS_YSGSJBP.GSQC) AS KHMC FROM MS_YSGSJBP").iterator();
		        				while(it.hasNext()){
	        						m= (Map)it.next();
	        						zyls+="if(value=='"+CString.rep(m.get("KHDM"))+"'){return '"+CString.rep(m.get("KHMC"))+"'}";
	        					}
		        			%>
		        			
		        			function ysdwfmt(value,row,index){
		        				<%=zyls %>
		        			}
		        			
		        			<%
		        				zyls="";
		        				it=jt.queryForList("SELECT RTRIM(MSGHSJBP.GHSBM) AS KHDM , RTRIM(MSGHSJBP.GHSQC) AS KHMC FROM MSGHSJBP").iterator();
		        				while(it.hasNext()){
	        						m= (Map)it.next();
	        						zyls+="if(value=='"+CString.rep(m.get("KHDM"))+"'){return '"+CString.rep(m.get("KHMC"))+"'}";
	        					}
		        			%>
		        			
		        			function scsfmt(value,row,index){
		        				<%=zyls %>
		        			}
		        		</script>
	        		</li>
	        		
	        		<li><span>��ҵ����</span><br>
	        			<select id="zylx" style="width: 80%">
	        			 
	        				<option value="">��ѡ��</option>
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
	        			</script>
	        		</li>
	        		<li><span>�ƻ�״̬</span><br>
	        			<select id="jhzt" style="width: 80%">
	        				<option value="">��ѡ��</option>
	        				<%
	        					it=jt.queryForList("SELECT RTRIM(DM_ZHBMP.BM) AS BM , RTRIM(DM_ZHBMP.BMSM) AS BMSM FROM DM_ZHBMP WHERE DM_ZHBMP.BMLB = 'ZT41'").iterator();
	        					zyls="";
	        					while(it.hasNext()){
	        						m= (Map)it.next();
	        						out.print("<option value='"+CString.rep(m.get("BM"))+"'>"+CString.rep(m.get("BMSM"))+"</option>");
	        						zyls+="if(value=='"+CString.rep(m.get("BM"))+"'){return '"+CString.rep(m.get("BMSM"))+"'}";
	        					}
	        				 %>
	        			</select>
	        			<script>
	        				function ztfmt(value,row,index){
	        					<%=zyls %>
	        				}
	        			</script>
	        		</li>
	        		<!-- <li><span>ͳ����ʱ��λ��</span><br>
	        			<select id="tjlsdwl" style="width: 80%">
	        				<option value="0">��</option>
	        				<option value="1">��</option>
	        			</select>
	        		</li> -->
	        		<li><span>����</span><br>
	        			<select id="orderkey" style="width: 80%">
	        				<option value="HTZX">��ͬ����</option>
	        				<option value="KHMC">�ͻ�����</option>
	        				<option value="KSRQ desc" selected="selected">��ʼʱ�䵹��</option>
	        				<option value="KSRQ asc">��ʼʱ������</option>
	        				<option value="ZYLX">��ҵ����</option>
	        			</select>
	        		</li>
	        		<!-- <li><span>�ͻ�����</span><br>
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
	        		<li><span>�������</span><br>
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
	        		  -->
	        	</ul>
	        </form>            
        </div>
        <div title="�����б�">
        	<table id="dg" data-options="singleSelect:true,border:false,scrollbarSize:18,rownumbers:true,pagination:true,pagePosition:'top'" height="100%">
        		<thead data-options="frozen:true">
		            <tr>
		                <th data-options="field:'JHBH',width:100,align:'center',styler:cellStyler">�ƻ����</th>
		                <th data-options="field:'HTZX',width:150,align:'center',styler:cellStyler">��ͬ����</th>
		                		                
		            </tr>
		        </thead>
        		<thead>
		            <tr>
		            	<th data-options="field:'KHMC',width:100,align:'center',formatter:khmcfmt">�ͻ�����</th><!-- ,formatter:dwfmt -->
		            	<th data-options="field:'SHDW',width:100,align:'center',formatter:dwfmt">�ջ���λ</th>
		            	<th data-options="field:'SCS',width:100,align:'center',formatter:scsfmt">������</th>
		            	<th data-options="field:'YSDW',width:100,align:'center',formatter:ysdwfmt">���䵥λ</th>
		            	<th data-options="field:'KSRQ',width:100,align:'center',styler:cellStyler">��ʼ����</th>
		            	<th data-options="field:'JSRQ',width:100,align:'center',styler:cellStyler">��������</th>
		            	<th data-options="field:'ZYLX',width:100,align:'center',formatter:zylffmt">��ҵ����</th>
		            	<th data-options="field:'SL',width:100,align:'right',halign:'center',styler:cellStyler">����</th>
		            	<th data-options="field:'ZL',width:100,align:'right',halign:'center',styler:cellStyler">����</th>
		            	<th data-options="field:'JHCX',width:100,align:'center'">�ƻ���ѯ</th>
		            	<th data-options="field:'ZYD',width:100,align:'center'">��ҵ��</th>
		            	<th data-options="field:'ZLJHCX',width:60,align:'center'">�����ƻ�</th>
		            	<th data-options="field:'JHZT',width:100,align:'center',formatter:ztfmt">�ƻ�״̬</th>
		            	<th data-options="field:'DJBZ',width:100,align:'center',styler:cellStyler">���ݱ�ע</th>
		            	<th data-options="field:'BZRY',width:100,align:'center',styler:cellStyler">������Ա</th>
		            	<th data-options="field:'BZRQ',width:150,align:'center',styler:cellStyler">��������</th>
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