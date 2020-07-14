package com.fenglian.tools.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fenglian.tools.logger.FileLogger;

/**
 * @version 	1.0
 * @author
 */
public class CheckAuthFilter implements Filter
{
	 private String exception="";
	 
	
	/**
	* @see javax.servlet.Filter#void ()
	*/
	public void destroy()
	{

	}

	/**
	* 功能：执行用户权限验证过滤器，判断用户是否有权限访问该页面。
	*/
	public void doFilter(
		ServletRequest req,
		ServletResponse resp,
		FilterChain chain)
		throws ServletException, IOException
	{
		try{

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String page = request.getRequestURI(); //获得申请页面
		HttpSession session = request.getSession(true);
		String webRoot = request.getContextPath();
		response.setCharacterEncoding("UTF-8");
		

//		FileLogger.debug(
//			"---------------执行权限判断过滤器：" + page + "-----------------------"+webRoot);
//
//		FileLogger.debug(
//				"---------------例外：" + exception + "-----------------------");
//		
		//例外允许访问页面
//		String[] exceptions =  exception.split(":");
//		for(int i=0;i<exceptions.length;i++)
//		{
//			if(page.indexOf(exceptions[i]) > 0 )
//			{
//				chain.doFilter(req, resp);
//				return;
//			}
//		}
//		
//		//异常页面运行进入
//		if (page.indexOf("error") > 0)  
//		{
//			chain.doFilter(req, resp);
//			return;
//		}
//		//跟页面允许访问
//		if (page.equals(webRoot+"/"))  
//		{
//			chain.doFilter(req, resp);
//			return;
//		}
//	
//		
//		String user = (String) session.getAttribute("SESSION_USER_ID");
//		//判断用户是否登陆
//		if (user == null)
//		{	
//			//FileLogger.debug("Session time out!!!");
//			response.sendRedirect(webRoot+"/login.jsp?ErrorMssage=SessionTimeOut");
//			return;
//		}

		
		chain.doFilter(req, resp);
}catch(Exception e)
{
	e.printStackTrace();
}
		return;
	
		
	}

	/**
	* Method init.
	* @param config
	* @throws javax.servlet.ServletException
	*/
	public void init(FilterConfig config) throws ServletException
	{
		exception = config.getInitParameter("exception");
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}


}
