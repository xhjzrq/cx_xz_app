<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.fenglian.tools.extent.JdbcTemplatePage"%>
<%@page import="com.fenglian.tools.extent.ApplicationContextUtil"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.io.InputStream"%>
<%@page import="com.fenglian.tools.logger.FileLogger"%>
<%@page import="java.net.URLEncoder"%>
<%
	JdbcTemplatePage jt =(JdbcTemplatePage) ApplicationContextUtil.getInstance().getBean("jdbcTemplatePage");
	response.addHeader("pragma","no-cache");
	response.addHeader("cache-control","no-cache;");
	
	response.addDateHeader("expries",0);
	response.setContentType("*/*");
	
	String key = request.getParameter("key");

	String sql = "SELECT  FILENAME,FILEB FROM DM_BINARY_FILE WHERE TKEY = '"+key+"'";
	System.out.println(sql);
	Connection conn =null;
	java.io.OutputStream stream = null;
	Statement stm =null;
	  ResultSet rs = null;  
	try{
	 conn = jt.getDataSource().getConnection();	    
     stm =  conn.createStatement();
     rs = stm.executeQuery(sql);
     stream = response.getOutputStream();
    
	if(rs.next())
	{
		String filename=rs.getString("FILENAME");
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {  
		    filename = URLEncoder.encode(filename, "UTF-8");  
		} else {  
		    filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");  
		}  
		filename=filename.toLowerCase();
		//String fileType = filename.substring()
		if("doc".endsWith(filename)||"docx".endsWith(filename)){  
                    response.setContentType("application/msword");  
                }else if("xls".endsWith(filename)||"xlsx".endsWith(filename)){  
                    response.setContentType("application/msexcel");   
                }else{  
                    response.setContentType("application/*");  
                }  
	 	response.addHeader("Content-Disposition","filename="+ filename);
	 	Blob blob = rs.getBlob("FILEB");	 	
	    InputStream instream = blob.getBinaryStream();
        byte[] nb=new byte[2024];
        
        while(instream.read(nb)!=-1){
       	 	try{
          	  	stream.write(nb);
            }catch(Exception e)
            {
           	 	stream = response.getOutputStream();
            	stream.write(nb);
            }
        }   
        instream.close();  

	 }
	conn.close();
	conn=null;
	}catch(Exception e)
	{
		e.printStackTrace();
		FileLogger.error("获取图片异常。"+e.toString());
	}finally{
				
		stream.flush();
		//stream.close();
		response.flushBuffer();
		out.clear();
		out = pageContext.pushBody();
	}
 %>