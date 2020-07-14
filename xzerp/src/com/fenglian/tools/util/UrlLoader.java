/***************************************************************************
 * Confidential.
 * ShangHai Topcheer Information Corporation
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of ShangHai
 * Topcheer Information Corporation ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with ShangHai Topcheer Information Corporation.
 ***************************************************************************/
package com.fenglian.tools.util;

import java.io.InputStream;
import java.net.URL;

/**
 * 使用URL装载资源
 * 
 * @author zyb
 * @version 3.0
 * @since 2.0
 */
public class UrlLoader {

	/**
	 * 装载资源
	 * 
	 * @param location
	 *            URL形式资源位置
	 * @return
	 * @throws RuntimeException
	 */
	public static InputStream loadResource(String location)
			throws RuntimeException {
		
		URL url = fromResource(location,null);
		
		if (url == null) {
			throw new RuntimeException("URL Resource not found: " + location);
		}
		try {
			return url.openStream();
		} catch (java.io.IOException e) {
			throw new RuntimeException(
					"Error opening URL resource at location ["
							+ url.toExternalForm() + "]", e);
		}
	}
	
	
	
	public static URL fromResource(String resourceName, ClassLoader loader) {
		URL url = null;

		if (loader != null && url == null)
			url = loader.getResource(resourceName);
		if (loader != null && url == null)
			url = loader.getResource(resourceName + ".properties");

		if (loader == null && url == null) {
			try {
				loader = Thread.currentThread().getContextClassLoader();
			} catch (SecurityException e) {
				
				UrlLoader urlloader = new UrlLoader();
				loader = urlloader.getClass().getClassLoader();
			}
		}

		if (url == null)
			url = loader.getResource(resourceName);
		if (url == null)
			url = loader.getResource(resourceName + ".properties");

		if (url == null)
			url = ClassLoader.getSystemResource(resourceName);
		if (url == null)
			url = ClassLoader.getSystemResource(resourceName + ".properties");

		// Debug.log("[fromResource] got URL " + url + " from resourceName " +
		// resourceName);
		return url;
	}

}
