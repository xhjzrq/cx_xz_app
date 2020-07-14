/*********************************************************************************************************************************
 * @(#)XsyncS.java 1.0  2010-8-13
 *
 * 版权(c) 2010-2015  沈阳丰联数码科技有限公司
 * 中国·辽宁·沈阳市和平区中华路188号中进大厦14层
 * 所有权限被保留。
 *
 *     本软件为丰联公司所拥有的保密信息。在未经过本公司许可的情况下，任何人或机构不可以将该软件的使用权和原代码泄露给其他人或机构。
 ********************************************************************************************************************************/
package com.fenglian.tools.threadpool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;



/**
 * 
 * 描述：线程池类
 * 
 * @author 潘瑜
 * 
 * @version v1.0
 * 
 * 建立日期：2006-11-1
 * 
 * 历史修改：
 * 
 */
public class ThreadPool {

	private int minPools = 10;

	private int maxPools = 20;

	private ArrayList workThreadList; // 工作线程列表，保存所有的线程

	private LinkedList taskList = null; // 工作任务列表，保存将要执行的工作任务

	private int freeThreadCount = 0; // 未被使用的线程数目

	private Object syncobj = new Object();
	
	private String name;
 

	public ThreadPool(String name,int minPool,int maxPool)
	{
		minPools = minPool;
		maxPools = maxPool;
		this.name = name;
		
		workThreadList = new ArrayList();
		taskList = new LinkedList();
		// 初始化线程池
		for (int i = 0; i < minPools; i++) {
			WorkerThread temp = new WorkerThread(name);
			workThreadList.add(temp);
			temp.start(); 
		}
		 
	}

 

	public void stop()
	{
		for ( Iterator iter = workThreadList.iterator(); iter.hasNext();)
		{
			WorkerThread wt = (WorkerThread) iter.next();
			wt.stopThread();
		}
		synchronized (taskList) {
			taskList.notifyAll();
		}
	}
	/**
	 * 运行一个任务项
	 * @param work
	 */
	public void run(WorkIF work) {
	
		synchronized (taskList) {
			if (freeThreadCount == 0) {
				// 1.没有空闲线程，且总线程数小于最大线程数
				if (workThreadList.size() < maxPools) {
					WorkerThread temp = new WorkerThread(name);
					workThreadList.add(temp);
					temp.start();
					 
				} 
			}
			taskList.addLast(work);
			taskList.notify();
		}
	}


 
	/**
	 * 线程池中的工作线程类，由工作线程执行我们要进行的操作
	 */
	class WorkerThread extends Thread {
		boolean running = true;

		WorkIF work;
		
		public WorkerThread(String name){
			this.setDaemon(true);
			if ( name == null)
				this.setName("ThreadPool.WorkerThread");
			else
				this.setName(name);
		}
		
		public void stopThread()
		{
			running = false;
		}
		public void run() {
			while (running) {
				synchronized (syncobj)
				{
					freeThreadCount++;
				}
				synchronized (taskList) {
					
					 // 一进来说明多了一个可用线程
					while (taskList.size() == 0) // 当工作任务列表为空时，等待
					{
						try {
							taskList.wait(500);
							if (!running)
								return;
						} catch (InterruptedException e) {
						}
					}
					synchronized (syncobj)
					{
						freeThreadCount--;
					}
					// 得到一个工作，可用线程要减1
					work = (WorkIF) taskList.removeFirst(); // 从任务列表处获得一个任务
				 
				}
				try{		 
					work.doWork();
				}catch(Throwable e){
					e.printStackTrace();
				}
			}
		}
	}
    
    /**
     * 
     * 功能：获取工作线程数量。
     *  
     * @return
     */
    public int getWorkThreadCount()
    {
    	synchronized (taskList)
    	{
    		return workThreadList.size();
    	}
    }

    /**
     * 
     * 功能：获取当前正在执行的任务数量
     *  
     * @return
     */
    public int getTaskCount()
    {
    	synchronized (taskList){
    		return taskList.size();
    	}
    }
    /**
     * 
     * 功能：获取当前空闲线程数。
     *  
     * @return
     */
    public int getFreeThreadCount()
    {
    	synchronized (taskList){
    		return freeThreadCount;
    	}
    }
    
    


 
    
}