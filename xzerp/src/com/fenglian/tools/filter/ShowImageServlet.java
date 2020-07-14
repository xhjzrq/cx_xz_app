package com.fenglian.tools.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fenglian.tools.logger.FileLogger;
import com.fenglian.tools.util.Config;

public class ShowImageServlet extends HttpServlet {
	
	private static String filePath="";
	
	public void init() throws ServletException {
		filePath = Config.getFileConfig(getServletConfig().getInitParameter("filePath"));
		FileLogger.debug("init upload file path:"+filePath);
		
	}
	
    public void service(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {

    	String sDir = filePath;
    	String sFilename = request.getParameter("filename");
    	java.io.FileInputStream hFile = new java.io.FileInputStream(sDir+"/"+sFilename);
    	java.io.OutputStream stream = null;
    	try{
    		stream = response.getOutputStream();
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	response.addHeader("pragma","no-cache");
    	response.addHeader("cache-control","no-cache;");
    	response.addHeader("Content-Disposition","filename="+sFilename);
    	response.addDateHeader("expries",0);
    	response.setContentType("image/*");

    	byte[] b = new byte[1024];
    	try {
    		while(hFile.read(b)!=-1){
    			stream.write(b);
    		}
    	} catch (java.io.IOException e) {
    		FileLogger.error(e.getMessage());
    	}finally{
    		if(stream!=null)
    		{
	    		stream.flush();
	    		stream.close();
    		}
    		
    	}    	 
    }

	public static String getFilePath() {
		return filePath;
	}
	
}
