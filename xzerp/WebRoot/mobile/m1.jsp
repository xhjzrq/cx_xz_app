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
    <title>现场物流及生产制造系统</title>  
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
    		$('#tab').tabs('select',1)//切换到列表页面	
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
                    <a href="javascript:void(0);window.history.back();" class="easyui-linkbutton m-back" plain="true" outline="true" style="width:50px">返回</a>
                </div>
                <div class="m-right">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" id="btnsearch" onclick="dosearch()">查询</a>
                </div>               
            </div>
        </header>
        <div class="easyui-tabs" data-options="fit:true,border:false,tabWidth:80,tabHeight:35" id="tab">
        <div title="查询条件" style="padding:10px">
        	<form id="m1form">
	        	<ul class="m-list">
		            <li><span>入库日期</span><br><input id="rkrq_s" name="rkrq_s" class="easyui-datebox" style="width:40%" data-options="editable:false,panelWidth:220,panelHeight:240,iconWidth:30">--<input id="rkrq_e" name="rkrq_e" class="easyui-datebox" style="width:40%" data-options="editable:false,panelWidth:220,panelHeight:240,iconWidth:30"></li>
		            <li><span>库位</span><br><input name="kwh" id="kwh" class="easyui-textbox" style="width:80%"></li>
		            <li><span>物料号</span><br><input name="GHSWPH" id="GHSWPH" class="easyui-textbox" style="width:80%"></li>	           	            
		            <li><span>钢牌号</span><br><input name="WPGPH" id="WPGPH" class="easyui-textbox" style="width:80%"></li>
		            <li><span>船级社</span><br><input name="WPCJS" id="WPCJS" class="easyui-textbox" style="width:80%"></li>
		            <li><span>购单号</span><br><input name="dc_fd" id="dc_fd" class="easyui-textbox" style="width:80%"></li>
		            <li><span>入库单号</span><br><input name="nbsbm" id="nbsbm" class="easyui-textbox" style="width:80%"></li>
		            <li><span>优先库位</span><br><input name="lkwh" id="lkwh" class="easyui-textbox" style="width:80%"></li>
		            <li><span>炉批号</span><br><input name="lph" id="lph" class="easyui-textbox" style="width:80%"></li>
		            <li><span>厚度</span><br><input name="B1" id="B1" class="easyui-textbox" style="width:80%"></li>
		            <li><span>宽度</span><br><input name="T1" id="T1" class="easyui-textbox" style="width:80%"></li>
		            <li><span>长度</span><br><input name="T2" id="T2" class="easyui-textbox" style="width:80%"></li>
		            <li><span>小合同号</span><br><input name="cgddh" id="cgddh" class="easyui-textbox" style="width:80%"></li>
		            <li><span>录入人</span><br><input name="xgr" id="xgr" class="easyui-textbox" style="width:80%"></li>
	        	</ul>
        	</form>            
        </div>
        <div title="内容列表">
        	<table id="dg" data-options="singleSelect:true,border:false,scrollbarSize:18,rownumbers:true,pagination:true,pagePosition:'top'" height="100%">  
		        <thead data-options="frozen:true">
		            <tr>
		                <th data-options="field:'CGDDH',width:100,align:'center'">小合同号</th>		                
		            </tr>
		        </thead>
		        <thead>
		            <tr>
		                <th data-options="field:'GHSWPH',align:'center'">物料号</th>
		                <th data-options="field:'DC_FD',align:'center'">购单号</th>
		                <th data-options="field:'WPCJS',align:'center'">材质</th>
		                <th data-options="field:'B1',align:'right'">厚度</th>
		                <th data-options="field:'T1',align:'right'">宽度</th>
		                <th data-options="field:'T2',align:'right'">长度</th>
		                <th data-options="field:'QXKCL',align:'right'">数量</th>
		                <th data-options="field:'QXKCL1',align:'right'">重量</th>
		                <th data-options="field:'DC_CH'">工程</th>
		                <th data-options="field:'DC_PL'">批量</th>
		                <th data-options="field:'LPH',align:'center'">炉批号</th>
		                <th data-options="field:'RKRQ'">收货日期</th>
		                <th data-options="field:'KWH'">库位</th>
		                <th data-options="field:'LKWH'">新库位</th>
		                <th data-options="field:'CFCS1',align:'center'">层数</th>
		                <th data-options="field:'YSCH',align:'center'">车号</th>
		                <th data-options="field:'JSR',align:'center'">收货人</th>
		                <th data-options="field:'CKH',align:'center'">仓库</th>
		                <th data-options="field:'KHFL',align:'center',formatter:khfl">客户分类</th>
		                <th data-options="field:'WPFLM',align:'center',formatter:wzlb">物资类别</th>
		                <th data-options="field:'JBGJH',align:'center'">钢卷号</th>
		                <th data-options="field:'CGDDXH',align:'center'">子项</th>
		                <th data-options="field:'WPGPH',align:'center'">钢牌号</th>
		                <th data-options="field:'WPXH',align:'center'">材质</th>
		                <th data-options="field:'JYPH',align:'center'">检验批号</th>
		                <th data-options="field:'CFCS1',align:'center'">层数</th>
		                <th data-options="field:'WPGG',align:'center'">规格</th>
		                <th data-options="field:'ZYCH',align:'center'">需求项目</th>
		                <th data-options="field:'ZYLX',align:'center'">流向</th>
		                <th data-options="field:'PWWZ',align:'center'">抛丸位置</th>
		                <th data-options="field:'FHPHH',align:'center'">配货计划号</th>
		                <th data-options="field:'FHZZH',align:'center'">装载号</th>
		                <th data-options="field:'ZJH',align:'center'">质量证明书号</th>
		                <th data-options="field:'FHZFH',align:'center'">准发计划号</th>
		                <th data-options="field:'FHZT',align:'center'">状态</th>
		                <th data-options="field:'KHMC',align:'center'">客户名称</th>
		                <th data-options="field:'YJXQF',align:'center'">收货单位</th>
		                <th data-options="field:'YJJHDD',align:'center'">预计交货地点</th>
		                <th data-options="field:'BZ',align:'center'">备注</th>
		                <th data-options="field:'TMXX',align:'center'">条码</th>
		                <th data-options="field:'NBSBM',align:'center'">入库批号</th>
		                <th data-options="field:'WPZT',align:'center'">物品状态</th>
		                <th data-options="field:'WPSX',align:'center'">物品属性</th>
		                <th data-options="field:'YLZD1',align:'center'">剩余修改人员</th>
		                <th data-options="field:'YLZD2',align:'center'">剩余修改日期</th>
		                <th data-options="field:'XGR',align:'center'">剩余审批人员</th>
		                <th data-options="field:'XGRQ',align:'center'">剩余审批日期</th>
		                <th data-options="field:'YLZD3',align:'center'">利库修改人员</th>
		                <th data-options="field:'YLZD4',align:'center'">利库修改日期</th>
		                <th data-options="field:'WPH',align:'center'">物品号</th>
		                <th data-options="field:'WPMC',align:'center'">物品名称</th>
		                <th data-options="field:'DDH',align:'center'">入库单号</th>
		                <th data-options="field:'DDXH',align:'center'">项号</th>
		                <th data-options="field:'XGR',align:'center'">录入人</th>
		                <th data-options="field:'XGRQ',align:'center'">录入日期</th>
		                <th data-options="field:'YBGR',align:'center'">条码扫描人</th>
		                <th data-options="field:'YBGRQ',align:'center'">条码扫描日期</th>		               
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
        </footer>-->
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