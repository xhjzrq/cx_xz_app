package com.fenglian.tools.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 获取默认唯一线程池实例，并从配置文件中获取最大线程数和最小线程数。
 * @author zhang.yb
 *
 */
public class ThreadPoolMgr {

	private static ThreadPool inpool ;
	private static ThreadPool uppool ;
	private static ThreadPool up2pool ;
	private static ExecutorService javapool ;
	
	/**
	 * socket接收线程控制，20-50个线程。
	 * @return
	 */
	public synchronized static ThreadPool getReceivePool() {
		 
		if ( inpool == null ){
//			int minSize = new Integer(Config.getFileConfig("MinReceivePoolSize"));
//			int maxSize = new Integer(Config.getFileConfig("MaxReceivePoolSize"));
			
			inpool = new ThreadPool("inpool",1,100);
		}
		return inpool;
	}
	
	
	/**
	 * 数据同步上传线程，单一线程
	 * @return
	 */
	public synchronized static ThreadPool getUpPool() {
		 
		if ( uppool == null ){
//			int minSize = new Integer(Config.getFileConfig("MinPoolSize"));
//			int maxSize = new Integer(Config.getFileConfig("MaxPoolSize"));
			
			uppool = new ThreadPool("uppool",1,1);
		}
		return uppool;
	}
	
	/**
	 * 字典数据处理线程池，单一线程
	 * @return
	 */
	public synchronized static ThreadPool getUp2Pool() {
		 
		if ( up2pool == null ){
//			int minSize = new Integer(Config.getFileConfig("MinPoolSize"));
//			int maxSize = new Integer(Config.getFileConfig("MaxPoolSize"));
			
			up2pool = new ThreadPool("up2pool",1,1);
		}
		return up2pool;
	}
	
	/**
	 * 字典数据处理线程池，单一线程
	 * @return
	 */
	public synchronized static ExecutorService getJavaPool() {
		
		if ( javapool == null ){
			javapool = Executors.newSingleThreadExecutor(); 
		}
		return javapool;
	}

}
