package com.fenglian.tools.tag;

import javax.servlet.ServletContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.taglibs.standard.lang.jstl.Evaluator;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * Title: I-ZuZu<br>
 * Description:<br>
 * Copyright: Copyright (c) �ߺ���Ϣ 2008<br>
 * Company: GoHightech<br>
 * create date��2010-1-18<br>
 * 
 * �ܹ����ApplicationContext�ı�ǩ
 * 
 * @author ����
 * @version: 0.1
 */

public class ApplicationContextTag extends TagSupport
{
	private static final long serialVersionUID = 3844437842476441672L;

	protected static Evaluator evaluator = new Evaluator();

	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * ���ApplicationContext �������ͨ��ApplicationContext�����ȡSpring������beanʵ��
	 * 
	 * @return
	 */
	protected ApplicationContext getApplicationContext()
	{
		ServletContext servletContext = pageContext.getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return context;
	}
}
