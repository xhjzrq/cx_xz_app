/******************************************************************************
 * @(#)AppException.java 1.0  2005-6-20
 *
 * 版权(c) 2005-2010  沈阳丰联数码科技有限公司
 * 中国·辽宁·沈阳市和平区中华路188号中进大厦14层
 * 所有权限被保留。
 *
 *     本软件为丰联公司所拥有的保密信息。在未经过本公司许可的情况下，任何人或
 * 机构不可以将该软件的使用权和原代码泄露给其他人或机构。
 ******************************************************************************/
package com.fenglian.exception;

import com.fenglian.tools.logger.FileLogger;

/**
 * 所有应用及系统级异常均使用该异常抛出。
 * @author zhang.yb
 *
 */
public class AppException extends RuntimeException
{

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -4563572578216957033L;

    /**
     * 构造函数。
     * @param message - 提示关键字
     */
    public AppException(String message)
    {
        super(message);
        FileLogger.error(message);
    }
    /**
     * 手工抛出异常
     * @param message 异常描述
     * @param errorCode 异常编码，编码自动对应配置文件。向外传递异常编码
     */
    public AppException(String message,String errorCode)
    {
        super(errorCode);
        FileLogger.error(errorCode+""+message);
    }
    /**
     * 构造函数，初始化类成员。
     * @param message - 异常文字描述
     * @param e - 异常实例 
     */
    public AppException(String message,Exception e)
    {
        super(message);
        FileLogger.error(message,e);
    }
    
    public AppException( String message,Exception e,String errorCode)
    {
        super(errorCode);
        FileLogger.error(errorCode+":"+message,e);
    }
}