package com.fenglian.tools.extent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ApplicationContextUtil implements ApplicationContextAware{
	
	private static ApplicationContextUtil myself = null;
	private ApplicationContext ac =null;
	
	public ApplicationContextUtil()
	{
		if(myself ==null)
		{
			myself = this;
		}
		
	}
	public static ApplicationContextUtil getInstance()
	{
		
		if(myself ==null)
		{
			myself = new ApplicationContextUtil();
		}
		return myself;
	}

	public void setApplicationContext(ApplicationContext context)
	{
		ac = context;
	}
	
	public Object getBean(String name)
	{
	
		if(ac !=null)
		{
			return ac.getBean(name);
		}else
		{			
			ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:/context/spring*.xml");
			this.ac = ac;
			return ac.getBean(name);
		}
	}
}
