/*
 * 创建日期 2005-12-26
 * 作者：张阳斌
 */
package com.fenglian.tools.util;

import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author 张阳斌 创建日期： 2005-12-26
 * 
 * 类描述： 该组件完成系统配置信息获取和建立配置信息的缓存机制， 提高访问系统公共参数的速度
 * 
 * 应用场合：
 * 
 * 版本：V0.1
 */
public class Config {

	private static String filename = "config";
	
	
	/**
	 * 功能：获取yy3.properties文件下的配置信息。
	 * 
	 * @param key配置健
	 * @return 健值
	 */
	public static synchronized String getFileConfig(String key) {
		ResourceBundle rb = ResourceBundle.getBundle(filename);
		return rb.getString(key);
	}

	public static Properties getProperties() {
		try {
			Properties props = new Properties();
			props.load(UrlLoader.loadResource(filename));
			return props;
		} catch (Exception e) {
			throw new RuntimeException("cic.properties文件读取错误！",e);
		}
	}

	public static String getFilename() {
		return filename;
	}

	public static void setFilename(String filename) {
		Config.filename = filename;
	}

}