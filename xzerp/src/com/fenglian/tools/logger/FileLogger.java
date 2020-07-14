package com.fenglian.tools.logger;

import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;

public class FileLogger {
	private static Logger logger = getLogger("PLAT");
	private static Logger logger_yl = getLogger("YL");

	private static String caller = "com.fenglian.tools.logger.FileLogger";

	private static Logger getLogger(String level) {
		boolean flag = initConfigure();
		if (flag) {
			return Logger.getLogger(level);
		} else {
			return null;
		}
		
	}

	/**
	 * ��������ļ�������־��¼��
	 * 
	 * @return ���ɹ���������־��¼�����true ,���򷵻�false
	 */
	private static boolean initConfigure() {
		// ���������ļ�
		URL url = getClassLoader().getResource("log4j.properties");
		if (url != null) {
			// ��������ļ�������־��¼�����
			PropertyConfigurator.configure(url);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ������Դ���������ڼ��ر��������ļ�
	 * 
	 * @return ���ɹ�����һ�����Դ������
	 */
	private static ClassLoader getClassLoader() {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (classLoader == null) {
			classLoader = FileLogger.class.getClassLoader();
		}
		return classLoader;
	}

	public static void debug(String msg) {
		logger.log(caller, Priority.DEBUG, msg, null);
	}

	public static void debug(String msg, Throwable cause) {
		logger.log(caller, Priority.DEBUG, msg, cause);
	}

	public static void debug(String msg, Map map) {
		logger.log(caller, Priority.DEBUG, msg + "\n\t" + map, null);
	}

	public static void info(String msg) {
		logger.log(caller, Priority.INFO, msg, null);
	}

	public static void info(String msg, Throwable cause) {
		logger.log(caller, Priority.INFO, msg, cause);
	}

	public static void info(String msg, Map map) {
		logger.log(caller, Priority.INFO, msg + "\n\t" + map, null);
	}

	public static void warn(String msg) {
		logger.log(caller, Priority.WARN, msg, null);
	}

	public static void warn(String msg, Throwable cause) {
		logger.log(caller, Priority.WARN, msg, cause);
	}

	public static void warn(String msg, Map map) {
		logger.log(caller, Priority.WARN, msg + "\n\t" + map, null);
	}

	public static void error(String msg) {
		logger.log(caller, Priority.ERROR, msg, null);
	}

	public static void error(String msg, Throwable cause) {
		logger.log(caller, Priority.ERROR, msg, cause);
	}

	public static void error(String msg, Map map) {
		logger.log(caller, Priority.ERROR, msg + "\n\t" + map, null);
	}

	public static void fatal(String msg) {
		logger.log(caller, Priority.FATAL, msg, null);
	}

	public static void fatal(String msg, Throwable cause) {
		logger.log(caller, Priority.FATAL, msg, cause);
	}

	public static void fatal(String msg, Map map) {
		logger.log(caller, Priority.FATAL, msg + "\n\t" + map, null);
	}

	public static void ylDebug(String msg) {
		logger_yl.log(caller, Priority.INFO, "" + msg, null);
	}

	public static boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public static boolean isWarnEnabled() {
		return logger.isEnabledFor(Priority.WARN);
	}

	public static boolean isErrorEnabled() {
		return logger.isEnabledFor(Priority.ERROR);
	}

	public static boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public static boolean isYLDebugEnabled() {
		return logger_yl.isDebugEnabled();
	}

	public static boolean isYLWarnEnabled() {
		return logger_yl.isEnabledFor(Priority.WARN);
	}

	public static boolean isYLErrorEnabled() {
		return logger_yl.isEnabledFor(Priority.ERROR);
	}

	public static boolean isYLInfoEnabled() {
		return logger_yl.isInfoEnabled();
	}
	
}
