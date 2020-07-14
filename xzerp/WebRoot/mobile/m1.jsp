<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.fenglian.Const"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.fenglian.tools.extent.JdbcTemplatePage"%>
<%@page import="com.fenglian.tools.extent.ApplicationContextUtil"%>
<%@page import="com.fenglian.tools.util.CString"%>
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
 %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">  
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta content="telephone=no,email=no" name="format-detection">
    <title>�ֳ���������������ϵͳ</title>  
    <meta http-equiv="Content-Type" content="textml; charset=GBK">
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.2/themes/bootstrap/easyui.css">  
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.2/themes/mobile.css">  
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.2/themes/icon.css">  
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.min.js"></script>  
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.easyui.min.js"></script> 
    <script type="text/javascript" src="../jquery-easyui-1.4.2/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="../jquery-easyui-1.4.2/locale/easyui-lang-zh_CN2.js" ></script>
    <script type="text/javascript">
    	function wzlb(val,row){
    		val=$.trim(val);    		
    		<%
	        	it=jt.queryForList("SELECT RTRIM(DM_ZHBMP.BM) AS BM ,RTRIM(DM_ZHBMP.BMSM) AS BMSM FROM DM_ZHBMP WHERE  DM_ZHBMP.BMLB = 'WPLX'").iterator();
	        		while(it.hasNext()){
	        			m= (Map)it.next();
						out.print("if(val==\""+CString.rep(m.get("bm"))+"\"){return '"+CString.rep(m.get("BMSM"))+"'}\r\n");
	        		}
	        %>
	        return val;
    	}
    	function khfl(val,row){
    		val=$.trim(val);
    		 <%
	        	it=jt.queryForList("SELECT RTRIM(DM_ZHBMP.BM) AS BM ,RTRIM(DM_ZHBMP.BMSM) AS BMSM FROM DM_ZHBMP WHERE  DM_ZHBMP.BMLB = 'CGHT'").iterator();
	        		while(it.hasNext()){
	        			m= (Map)it.next();
						out.print("if(val==\""+CString.rep(m.get("bm"))+"\"){return '"+CString.rep(m.get("BMSM"))+"'}");
	        		}
	        %>
	        return val;
    	}
    	
    	
    	
    	function dosearch(){    		
    		$('#dg').datagrid('load',{
    			rkrq_s:$('#rkrq_s').datebox('getValue'),
    			rkrq_e:$('#rkrq_e').datebox('getValue'),
    			GHSWPH:$('#GHSWPH').textbox('getValue'),
    			WPGPH:$('#WPGPH').textbox('getValue'),
    			WPCJS:$('#WPCJS').textbox('getValue'),
    			dc_fd:$('#dc_fd').textbox('getValue'),
    			nbsbm:$('#nbsbm').textbox('getValue'),
    			lkwh:$('#lkwh').textbox('getValue'),    			
    			lph:$('#lph').textbox('getValue'),
    			B1:$('#B1').textbox('getValue'),
    			T1:$('#T1').textbox('getValue'),
    			T2:$('#T2').textbox('getValue'),
    			cgddh:$('#cgddh').textbox('getValue'),
    			xgr:$('#xgr').textbox('getValue'),
    			kwh:$('#kwh').textbox('getValue')
    			});
    		$('#tab').tabs('select',1)//�л����б�ҳ��	
    	}    	
    	$(document).ready(function () {
    		$('#rkrq_s').datebox('setValue', '<%=start%>');
    		$('#rkrq_e').datebox('setValue', '<%=end%>');    		
    		$('#dg').datagrid({url:'../jsonAction.do?method=exe&CMD=M1',queryParams:{rkrq_s:'<%=start%>',rkrq_e:'<%=end%>'},pageSize:20});
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
                <div class="m-title"><%=Const.M1 %></div>                
                <div class="m-left">
                    <a href="javascript:void(0);window.history.back();" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px">����</a>
                </div>
                <div class="m-right">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" id="btnsearch" onclick="dosearch()">��ѯ</a>
                </div>               
            </div>
        </header>
        <div class="easyui-tabs" data-options="fit:true,border:false,tabWidth:80,tabHeight:35" id="tab">
        <div title="��ѯ����" style="padding:10px">
        	<form id="m1form">
	        	<ul class="m-list">
		            <li><span>�������</span><br><input id="rkrq_s" name="rkrq_s" class="easyui-datebox" style="width:40%" data-options="editable:false,panelWidth:220,panelHeight:240,iconWidth:30">--<input id="rkrq_e" name="rkrq_e" class="easyui-datebox" style="width:40%" data-options="editable:false,panelWidth:220,panelHeight:240,iconWidth:30"></li>
		            <li><span>��λ</span><br><input name="kwh" id="kwh" class="easyui-textbox" style="width:80%"></li>
		            <li><span>���Ϻ�</span><br><input name="GHSWPH" id="GHSWPH" class="easyui-textbox" style="width:80%"></li>	           	            
		            <li><span>���ƺ�</span><br><input name="WPGPH" id="WPGPH" class="easyui-textbox" style="width:80%"></li>
		            <li><span>������</span><br><input name="WPCJS" id="WPCJS" class="easyui-textbox" style="width:80%"></li>
		            <li><span>������</span><br><input name="dc_fd" id="dc_fd" class="easyui-textbox" style="width:80%"></li>
		            <li><span>��ⵥ��</span><br><input name="nbsbm" id="nbsbm" class="easyui-textbox" style="width:80%"></li>
		            <li><span>���ȿ�λ</span><br><input name="lkwh" id="lkwh" class="easyui-textbox" style="width:80%"></li>
		            <li><span>¯����</span><br><input name="lph" id="lph" class="easyui-textbox" style="width:80%"></li>
		            <li><span>���</span><br><input name="B1" id="B1" class="easyui-textbox" style="width:80%"></li>
		            <li><span>���</span><br><input name="T1" id="T1" class="easyui-textbox" style="width:80%"></li>
		            <li><span>����</span><br><input name="T2" id="T2" class="easyui-textbox" style="width:80%"></li>
		            <li><span>С��ͬ��</span><br><input name="cgddh" id="cgddh" class="easyui-textbox" style="width:80%"></li>
		            <li><span>¼����</span><br><input name="xgr" id="xgr" class="easyui-textbox" style="width:80%"></li>
	        	</ul>
        	</form>            
        </div>
        <div title="�����б�">
        	<table id="dg" data-options="singleSelect:true,border:false,scrollbarSize:18,rownumbers:true,pagination:true,pagePosition:'top'" height="100%">  
		        <thead data-options="frozen:true">
		            <tr>
		                <th data-options="field:'CGDDH',width:100,align:'center'">С��ͬ��</th>		                
		            </tr>
		        </thead>
		        <thead>
		            <tr>
		                <th data-options="field:'GHSWPH',align:'center'">���Ϻ�</th>
		                <th data-options="field:'DC_FD',align:'center'">������</th>
		                <th data-options="field:'WPCJS',align:'center'">����</th>
		                <th data-options="field:'B1',align:'right'">���</th>
		                <th data-options="field:'T1',align:'right'">���</th>
		                <th data-options="field:'T2',align:'right'">����</th>
		                <th data-options="field:'QXKCL',align:'right'">����</th>
		                <th data-options="field:'QXKCL1',align:'right'">����</th>
		                <th data-options="field:'DC_CH'">����</th>
		                <th data-options="field:'DC_PL'">����</th>
		                <th data-options="field:'LPH',align:'center'">¯����</th>
		                <th data-options="field:'RKRQ'">�ջ�����</th>
		                <th data-options="field:'KWH'">��λ</th>
		                <th data-options="field:'LKWH'">�¿�λ</th>
		                <th data-options="field:'CFCS1',align:'center'">����</th>
		                <th data-options="field:'YSCH',align:'center'">����</th>
		                <th data-options="field:'JSR',align:'center'">�ջ���</th>
		                <th data-options="field:'CKH',align:'center'">�ֿ�</th>
		                <th data-options="field:'KHFL',align:'center',formatter:khfl">�ͻ�����</th>
		                <th data-options="field:'WPFLM',align:'center',formatter:wzlb">�������</th>
		                <th data-options="field:'JBGJH',align:'center'">�־��</th>
		                <th data-options="field:'CGDDXH',align:'center'">����</th>
		                <th data-options="field:'WPGPH',align:'center'">���ƺ�</th>
		                <th data-options="field:'WPXH',align:'center'">����</th>
		                <th data-options="field:'JYPH',align:'center'">��������</th>
		                <th data-options="field:'CFCS1',align:'center'">����</th>
		                <th data-options="field:'WPGG',align:'center'">���</th>
		                <th data-options="field:'ZYCH',align:'center'">������Ŀ</th>
		                <th data-options="field:'ZYLX',align:'center'">����</th>
		                <th data-options="field:'PWWZ',align:'center'">����λ��</th>
		                <th data-options="field:'FHPHH',align:'center'">����ƻ���</th>
		                <th data-options="field:'FHZZH',align:'center'">װ�غ�</th>
		                <th data-options="field:'ZJH',align:'center'">����֤�����</th>
		                <th data-options="field:'FHZFH',align:'center'">׼���ƻ���</th>
		                <th data-options="field:'FHZT',align:'center'">״̬</th>
		                <th data-options="field:'KHMC',align:'center'">�ͻ�����</th>
		                <th data-options="field:'YJXQF',align:'center'">�ջ���λ</th>
		                <th data-options="field:'YJJHDD',align:'center'">Ԥ�ƽ����ص�</th>
		                <th data-options="field:'BZ',align:'center'">��ע</th>
		                <th data-options="field:'TMXX',align:'center'">����</th>
		                <th data-options="field:'NBSBM',align:'center'">�������</th>
		                <th data-options="field:'WPZT',align:'center'">��Ʒ״̬</th>
		                <th data-options="field:'WPSX',align:'center'">��Ʒ����</th>
		                <th data-options="field:'YLZD1',align:'center'">ʣ���޸���Ա</th>
		                <th data-options="field:'YLZD2',align:'center'">ʣ���޸�����</th>
		                <th data-options="field:'XGR',align:'center'">ʣ��������Ա</th>
		                <th data-options="field:'XGRQ',align:'center'">ʣ����������</th>
		                <th data-options="field:'YLZD3',align:'center'">�����޸���Ա</th>
		                <th data-options="field:'YLZD4',align:'center'">�����޸�����</th>
		                <th data-options="field:'WPH',align:'center'">��Ʒ��</th>
		                <th data-options="field:'WPMC',align:'center'">��Ʒ����</th>
		                <th data-options="field:'DDH',align:'center'">��ⵥ��</th>
		                <th data-options="field:'DDXH',align:'center'">���</th>
		                <th data-options="field:'XGR',align:'center'">¼����</th>
		                <th data-options="field:'XGRQ',align:'center'">¼������</th>
		                <th data-options="field:'YBGR',align:'center'">����ɨ����</th>
		                <th data-options="field:'YBGRQ',align:'center'">����ɨ������</th>		               
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
        </footer>-->
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