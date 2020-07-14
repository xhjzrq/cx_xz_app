package com.fenglian.tools.extent;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fenglian.tools.logger.FileLogger;

public class SpringTest {
	public static void main(String[] s) {
		
		JdbcTemplate jt =(JdbcTemplate)ApplicationContextUtil.getInstance().getBean("jdbcTemplate");
		List list = jt.queryForList("select * from Users");
		System.out.println(list);
		FileLogger.debug(list.toString());
		
		HibernateTemplate ht =(HibernateTemplate)ApplicationContextUtil.getInstance().getBean("hibernateTemplate");
		List list1 = ht.find("from Users");
		System.out.println(list1);
		System.out.println("success!");
	}
}
