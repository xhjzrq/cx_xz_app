/******************************************************************************
 * @(#)RecordSet.java 1.0  2005-6-1
 *
 * 版权(c) 2005-2010  沈阳丰联数码科技有限公司
 * 中国·辽宁·沈阳市和平区中华路188号中进大厦14层
 * 所有权限被保留。
 *
 *     本软件为丰联公司所拥有的保密信息。在未经过本公司许可的情况下，任何人或
 * 机构不可以将该软件的使用权和原代码泄露给其他人或机构。
 ******************************************************************************/
package com.fenglian.tools.extent;

import java.sql.Date;
import java.sql.Time;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.fenglian.exception.DBException;
import com.fenglian.tools.util.CString;
import com.fenglian.tools.util.DateTime;

/**
 * 数据库查询记录集。该类用于保存查询数据库后得到的记录。
 * 
 * @author 丰联数码
 * @version 1.0, 2005-6-2
 * @since JDK1.4.2
 */
public class RecordSet
{
    /** 查询返回的最大记录数，防止内存溢出 */
    public static final int MAX_RECORD_NUMBER = 65535;

    /** 记录总数 */
    private int             Total;

    /** 页面总数 */
    private int             PageTotal;

    /** 页面尺寸 */
    private int             PageSize;

    /** 当前页码 */
    private int             PageNO;

    /** 储存记录的实际数目 */
    private int             RealNumber;

    /** 查询的SQL语句 */
    private String          SQL;

    /** 数据库记录存储列表 */
    private List       m_RecordList;

    /** 记录的当前指针 */
    private int             m_Cursor;

    /** 记录的当前指针 */
    private Map       m_Record;

    /** RecordSet状态信息 */

    /**
     * 初始化类成员。
     */
    private void init()
    {
        Total = 0;
        PageTotal = 0;
        PageSize = 0;
        PageNO = 0;
        RealNumber = 0;
        SQL = null;
        m_RecordList = null;
        m_Cursor = -1;
        m_Record = null;
    }

    /**
     * 构造函数，初始化类成员。
     * 
     * @param al -
     *            记录列表
     * @param sql -
     *            查询用SQL语句
     * @param totalNum -
     *            查询得到的记录总数
     * @param realNum -
     *            RecordSet中保存的记录数
     */
    public RecordSet(List al)
    {
        this.init();

        this.m_RecordList = al;

    }

   

    /**
     * Gets the value of the designated column in the current row of this RecordSet object as an
     * Object in the Java programming language.
     * 
     * @param columnName -
     *            the SQL name of the column
     * @return a java.lang.Object holding the column value
     * @since JDK1.4.2
     */
    public Object getObject(String columnName)
    {
       // columnName = columnName.toLowerCase();

        try
        {
            // 判断列名是否有效
            if (this.m_Record.containsKey(columnName) == false) throw new DBException("列名无效",
                    "列名参数“" + columnName + "”无效。");

            Object obj = this.m_Record.get(columnName);

            return obj;
        }
        catch (NullPointerException e)
        {
            throw new DBException("请在getObject()前，调用next()等方法将指针移动到有效记录",e);
        }

    }

    /**
     * Retrieves the value of the designated column in the current row of this RecordSet object as
     * an int in the Java programming language.
     * 
     * @param columnName -
     *            the SQL name of the column
     * @return the column value; if the value is SQL NULL, the value returned is 0
     * @since JDK1.4.2
     */
    public int getInt(String columnName)
    {

        Object obj = this.getObject(columnName);

        if (obj == null || obj.equals("")) return 0;

        try
        {
            String strObj = obj.toString();
            if (strObj.indexOf(".") != -1)
            {
                strObj = strObj.substring(0, strObj.indexOf("."));
            }
            Integer i = new Integer(strObj);
            return i.intValue();

        }
        catch (NumberFormatException e)
        {
            throw new DBException("SQL字段“" + columnName + "”不是int类型",e);
        }

    }

    /**
     * Retrieves the value of the designated column in the current row of this RecordSet object as a
     * long in the Java programming language.
     * 
     * @param columnName -
     *            the SQL name of the column
     * @return the column - value; if the value is SQL NULL, the value returned is 0
     * @since JDK1.4.2
     */
    public long getLong(String columnName)
    {

        Object obj = this.getObject(columnName);

        if (obj == null || obj.equals("")) return 0;

        try
        {
            Long i = new Long(obj.toString());
            return i.longValue();
        }
        catch (NumberFormatException e)
        {
            throw new DBException("SQL字段“" + columnName + "”不是long类型",e);
        }

    }

    /**
     * Retrieves the value of the designated column in the current row of this RecordSet object as a
     * double in the Java programming language.
     * 
     * @param columnName -
     *            the SQL name of the column
     * @return the column value; if the value is SQL NULL, the value returned is 0
     * @since JDK1.4.2
     */
    public double getDouble(String columnName)
    {

        Object obj = this.getObject(columnName);

        if (obj == null || obj.equals("")) return 0;

        try
        {
            Double d = new Double(obj.toString());
            return d.doubleValue();
        }
        catch (NumberFormatException e)
        {
            throw new DBException("SQL字段“" + columnName + "”不是long类型",e);
        }
    }

    /**
     * 获得列值，并以 String 类型返回。
     * 
     * @param columnName -
     *            SQL列名
     * @return 返回列值。如果列指为null；则返回空串""。
     * @since JDK1.4.2
     */
    public String getString(String columnName)
    {

        Object obj = this.getObject(columnName);

        if (obj == null) return null;
        else
        {
            String str = obj.toString();
            if ((str == null) || (str.toLowerCase().equals("null")) || (str.equals(""))) str = "";
            return CString.stoh(str);
        }

    }

    
   

    /**
     * 获得列值，并以 com.fenglina.ext.DateTime 类型返回。 <br>
     * 
     * @param columnName -
     *            SQL列名
     * @return 返回列值。如果这个值为SQL NULL，则返回 null 。
     * @since JDK1.4.2
     */
    public DateTime getDateTime(String columnName)
    {

        Object obj = this.getObject(columnName);
        if (obj == null) return null;

        String str = obj.toString();
        if ((str == null) || (str.toLowerCase().equals("null")) || (str.equals(""))) return null;

        try
        {
            return new DateTime((java.util.Date) obj);
        }
        catch (ClassCastException e)
        {
            throw new DBException("SQL字段“" + columnName + "”不是Date类型",e);
        }

    }

    /**
     * 获得列值，并以 java.sql.Date 类型返回。 <br>
     * 
     * @param columnName -
     *            SQL列名
     * @return 返回列值。如果这个值为SQL NULL，则返回 null 。
     * @since JDK1.4.2
     */
    public Date getDate(String columnName)
    {

        Object obj = this.getObject(columnName);

        if (obj == null) return null;
        java.util.Date tmp = (java.util.Date) obj;
        try
        {
            return new Date(tmp.getTime());
        }
        catch (ClassCastException e)
        {
            throw new DBException("SQL字段“" + columnName + "”不是Date类型",e);
        }

    }

    /**
     * 获得列值，并以 java.sql.Time 类型返回。 <br>
     * 
     * @param columnName -
     *            SQL列名
     * @return 返回列值。如果这个值为SQL NULL，则返回 null 。
     * @since JDK1.4.2
     */
    public Time getTime(String columnName)
    {

        Object obj = this.getObject(columnName);

        if (obj == null) return null;

        try
        {
            return (Time) obj;
        }
        catch (ClassCastException e)
        {
            throw new DBException("SQL字段“" + columnName + "”不是Time类型",e);
        }

    }

    /**
     * 获得日期列值，并以 String 类型返回。 <br>
     * 其日期串的格式，格式形式为“yyyy年M月d日”。例如：2005年2月14日
     * 
     * @param columnName -
     *            SQL列名
     * @return 返回列值。如果这个值为SQL NULL，则返回空串（""） 。
     * @since JDK1.4.2
     */
    public String getDateStr(String columnName)
    {

        DateTime dt = this.getDateTime(columnName);

        if (dt == null) return "";

        return dt.format("yyyy年M月d日");

    }

    /**
     * 获得日期格式串，格式形式由 <code>pattern</code> 参数指定。
     * 
     * @param columnName
     *            SQL字段名称
     * @param pattern
     *            日期及时间样式串
     * @return 返回日期及时间样式串。 <br>
     *         如果 d=null ，则返回null； <br>
     *         如果 <code>pattern</code> ，返回"日期样式参数 pattern 为null"； <br>
     *         如果 <code>pattern</code> ，返回"日期样式参数 pattern 无效格式"； <br>
     * @since JDK1.4.2
     */
    /**
     * 获得日期列值，并以 String 类型返回。 <br>
     * 其日期格式形式由 <code>pattern</code> 参数指定。
     * 
     * @param columnName -
     *            SQL列名
     * @return 返回列值。如果这个值为SQL NULL，则返回空串（""） 。
     * @since JDK1.4.2
     */
    public String getDateStr(String columnName, String pattern)
    {

        DateTime dt = this.getDateTime(columnName);

        if (dt == null) return "";

        return dt.format(pattern);

    }

    

    /**
     * 把指针从当前记录下移一个记录。
     * 
     * @return 如果当前记录有效，返回true；否则返回false。
     * @since JDK1.4.2
     */
    public boolean next()
    {
        (this.m_Cursor)++;

        if (this.m_Cursor >= this.RealNumber) return false;
        else
        {
            try
            {
                this.m_Record = (Map) this.m_RecordList.get(this.m_Cursor);
                return true;
            }
            catch (IndexOutOfBoundsException e)
            {
                throw new DBException("RecordSet指针移动失败");
            }
        }
    }

    /**
     * 移动RecordSet指针到指定的行号。
     * 
     * @param row -
     *            指定的行号。
     * @return 如果当前记录有效，返回true；否则返回false。
     * @since JDK1.4.2
     */
    public boolean absolute(int row)
    {
        if (row >= this.RealNumber) return false;
        else
        {
            try
            {
                this.m_Cursor = row;
                this.m_Record = (Hashtable) this.m_RecordList.get(row);
                return true;
            }
            catch (IndexOutOfBoundsException e)
            {
                throw new DBException("RecordSet指针移动失败",e);
            }

        }
    }

    /**
     * 关闭RecordSet对象实例。释放所有Hashtable和ArrayList实例。
     * 
     * @since JDK1.4.2
     */
    public void close()
    {
        if (this.m_RecordList == null) return;

        for (int i = 0; i < this.m_RecordList.size(); i++)
        {
            Hashtable ht = (Hashtable) this.m_RecordList.get(i);
            if (ht != null)
            {
                ht.clear();
                ht = null;
            }
        }

        this.m_RecordList.clear();
        this.m_RecordList = null;

        this.init();
    }

    /**
     * 从RecordSet删除中的当前记录。并将记录指针移动到首记录的前面。
     * 
     * @since JDK1.4.2
     */
    public void delete()
    {
        if (this.m_Cursor == -1) return;

        try
        {
            this.m_RecordList.remove(this.m_Cursor);

            this.m_Cursor = -1;// 将指针自动移到首记录的前面

            if (this.m_Record != null) this.m_Record.clear();// 清楚当前记录

        }
        catch (IndexOutOfBoundsException e)
        {
            throw new DBException("RecordSet记录删除失败",e);
        }

    }

    /**
     * 将记录指针移动到首记录的前面。
     * 
     * @since JDK1.4.2
     */
    public void beforeFirst()
    {
        if (this.m_Cursor == -1)
        {
            return;
        }
        else
        {
            this.m_Cursor = -1;// 将指针移到首记录的前面
        }
    }

    /**
     * 将记录指针移动到末尾记录的后面。
     * 
     * @since JDK1.4.2
     */
    public void afterLast()
    {
        if (this.m_Cursor == this.Total)
        {
            return;
        }
        else
        {
            this.m_Cursor = this.Total;// 将指针移到末尾记录的后面
        }
    }

    /**
     * 判断记录指针是否移动到记录末尾。
     * 
     * @since JDK1.4.2
     */
    public boolean isLast()
    {
        if ((this.m_Cursor + 1) == this.Total)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // ///////////////////////////////
    // getter和setter方法 //
    // //////////////////////////////

    /**
     * 当分页查询时，用该方法获得当前页码。
     * 
     * @return 返回当前页码。
     * @since JDK1.4.2
     */
    public int getPageNO()
    {
        return PageNO;
    }

    /**
     * 当分页查询时，用该方法获得页面尺寸。
     * 
     * @return 返回 pageSize。
     * @since JDK1.4.2
     */
    public int getPageSize()
    {
        return PageSize;
    }

    /**
     * 当分页查询时，用该方法获得总页面数。
     * 
     * @return 返回总页面数。
     * @since JDK1.4.2
     */
    public int getPageTotal()
    {
        return PageTotal;
    }

    /**
     * 获得RecordSet中存储的实际记录数。
     * 
     * @return 返回实际记录数。
     * @since JDK1.4.2
     */
    public int getRealNumber()
    {
        return RealNumber;
    }

    /**
     * 获得查询用的SQL语句。
     * 
     * @return 返回SQL语句。
     * @since JDK1.4.2
     */
    public String getSQL()
    {
        return SQL;
    }

    /**
     * 获得符合SQL条件的记录总数。
     * 
     * @return 返回记录总数。
     * @since JDK1.4.2
     */
    public int getTotal()
    {
        return Total;
    }

}