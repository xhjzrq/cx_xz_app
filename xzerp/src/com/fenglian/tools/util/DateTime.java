/******************************************************************************
 * @(#)DateTime.java 1.0  2005-6-2
 *
 * 版权(c) 2005-2010  沈阳丰联数码科技有限公司
 * 中国·辽宁·沈阳市和平区中华路188号中进大厦14层
 * 所有权限被保留。
 *
 *     本软件为丰联公司所拥有的保密信息。在未经过本公司许可的情况下，任何人或
 * 机构不可以将该软件的使用权和原代码泄露给其他人或机构。
 ******************************************************************************/

package com.fenglian.tools.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类。这个类提供一些静态方法，去完成时间及日期转换功能。<br><br>
 * 
 * 日期及时间样式参数：<br>
 * 日期及时间格式是通过pattern参数来指定的。'a'-'z'和'A'-'Z'中的所有字符是这个参数的保留字符。<br>
 * 下面是pattern参数的字符定义：<br>
 * <blockquote>
 * <table border=0 cellspacing=3 cellpadding=0 summary="Chart shows pattern letters, date/time 

component, presentation, and examples.">
 *     <tr bgcolor="#ccccff">
 *         <th align=left>字符
 *         <th align=left>日期或时间构成
 *         <th align=left>描述
 *         <th align=left>举例
 *     <tr>
 *         <td><code>G</code>
 *         <td>Era designator
 *         <td><a href="#text">Text</a>
 *         <td><code>AD</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>y</code>
 *         <td>Year
 *         <td><a href="#year">Year</a>
 *         <td><code>1996</code>; <code>96</code>
 *     <tr>
 *         <td><code>M</code>
 *         <td>Month in year
 *         <td><a href="#month">Month</a>
 *         <td><code>July</code>; <code>Jul</code>; <code>07</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>w</code>
 *         <td>Week in year
 *         <td><a href="#number">Number</a>
 *         <td><code>27</code>
 *     <tr>
 *         <td><code>W</code>
 *         <td>Week in month
 *         <td><a href="#number">Number</a>
 *         <td><code>2</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>D</code>
 *         <td>Day in year
 *         <td><a href="#number">Number</a>
 *         <td><code>189</code>
 *     <tr>
 *         <td><code>d</code>
 *         <td>Day in month
 *         <td><a href="#number">Number</a>
 *         <td><code>10</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>F</code>
 *         <td>Day of week in month
 *         <td><a href="#number">Number</a>
 *         <td><code>2</code>
 *     <tr>
 *         <td><code>E</code>
 *         <td>Day in week
 *         <td><a href="#text">Text</a>
 *         <td><code>Tuesday</code>; <code>Tue</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>a</code>
 *         <td>Am/pm marker
 *         <td><a href="#text">Text</a>
 *         <td><code>PM</code>
 *     <tr>
 *         <td><code>H</code>
 *         <td>Hour in day (0-23)
 *         <td><a href="#number">Number</a>
 *         <td><code>0</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>k</code>
 *         <td>Hour in day (1-24)
 *         <td><a href="#number">Number</a>
 *         <td><code>24</code>
 *     <tr>
 *         <td><code>K</code>
 *         <td>Hour in am/pm (0-11)
 *         <td><a href="#number">Number</a>
 *         <td><code>0</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>h</code>
 *         <td>Hour in am/pm (1-12)
 *         <td><a href="#number">Number</a>
 *         <td><code>12</code>
 *     <tr>
 *         <td><code>m</code>
 *         <td>Minute in hour
 *         <td><a href="#number">Number</a>
 *         <td><code>30</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>s</code>
 *         <td>Second in minute
 *         <td><a href="#number">Number</a>
 *         <td><code>55</code>
 *     <tr>
 *         <td><code>S</code>
 *         <td>Millisecond
 *         <td><a href="#number">Number</a>
 *         <td><code>978</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>z</code>
 *         <td>Time zone
 *         <td><a href="#timezone">General time zone</a>
 *         <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-

08:00</code>
 *     <tr>
 *         <td><code>Z</code>
 *         <td>Time zone
 *         <td><a href="#rfc822timezone">RFC 822 time zone</a>
 *         <td><code>-0800</code>
 * </TABLE>
 * </blockquote>
 * Pattern letters are usually repeated, as their number determines the
 * exact presentation:
 * <ul>
 * <li><strong><a name="text">Text:</a></strong>
 *     For formatting, if the number of pattern letters is 4 or more,
 *     the full form is used; otherwise a short or abbreviated form
 *     is used if available.
 *     For parsing, both forms are accepted, independent of the number
 *     of pattern letters.
 * <li><strong><a name="number">Number:</a></strong>
 *     For formatting, the number of pattern letters is the minimum
 *     number of digits, and shorter numbers are zero-padded to this amount.
 *     For parsing, the number of pattern letters is ignored unless
 *     it's needed to separate two adjacent fields.
 * <li><strong><a name="year">Year:</a></strong>
 *     For formatting, if the number of pattern letters is 2, the year
 *     is truncated to 2 digits; otherwise it is interpreted as a
 *     <a href="#number">number</a>.
 *     <p>For parsing, if the number of pattern letters is more than 2,
 *     the year is interpreted literally, regardless of the number of
 *     digits. So using the pattern "MM/dd/yyyy", "01/11/12" parses to
 *     Jan 11, 12 A.D.
 *     <p>For parsing with the abbreviated year pattern ("y" or "yy"),
 *     <code>SimpleDateFormat</code> must interpret the abbreviated year
 *     relative to some century.  It does this by adjusting dates to be
 *     within 80 years before and 20 years after the time the <code>SimpleDateFormat</code>
 *     instance is created. For example, using a pattern of "MM/dd/yy" and a
 *     <code>SimpleDateFormat</code> instance created on Jan 1, 1997,  the string
 *     "01/11/12" would be interpreted as Jan 11, 2012 while the string "05/04/64"
 *     would be interpreted as May 4, 1964.
 *     During parsing, only strings consisting of exactly two digits, as defined by
 *     {@link Character#isDigit(char)}, will be parsed into the default century.
 *     Any other numeric string, such as a one digit string, a three or more digit
 *     string, or a two digit string that isn't all digits (for example, "-1"), is
 *     interpreted literally.  So "01/02/3" or "01/02/003" are parsed, using the
 *     same pattern, as Jan 2, 3 AD.  Likewise, "01/02/-3" is parsed as Jan 2, 4 BC.
 * <li><strong><a name="month">Month:</a></strong>
 *     If the number of pattern letters is 3 or more, the month is
 *     interpreted as <a href="#text">text</a>; otherwise,
 *     it is interpreted as a <a href="#number">number</a>.
 * <li><strong><a name="timezone">General time zone:</a></strong>
 *     Time zones are interpreted as <a href="#text">text</a> if they have
 *     names. For time zones representing a GMT offset value, the
 *     following syntax is used:
 *     <pre>
 *     <a name="GMTOffsetTimeZone"><i>GMTOffsetTimeZone:</i></a>
 *             <code>GMT</code> <i>Sign</i> <i>Hours</i> <code>:</code> <i>Minutes</i>
 *     <i>Sign:</i> one of
 *             <code>+ -</code>
 *     <i>Hours:</i>
 *             <i>Digit</i>
 *             <i>Digit</i> <i>Digit</i>
 *     <i>Minutes:</i>
 *             <i>Digit</i> <i>Digit</i>
 *     <i>Digit:</i> one of
 *             <code>0 1 2 3 4 5 6 7 8 9</code></pre>
 *     <i>Hours</i> must be between 0 and 23, and <i>Minutes</i> must be between
 *     00 and 59. The format is locale independent and digits must be taken
 *     from the Basic Latin block of the Unicode standard.
 *     <p>For parsing, <a href="#rfc822timezone">RFC 822 time zones</a> are also
 *     accepted.
 * <li><strong><a name="rfc822timezone">RFC 822 time zone:</a></strong>
 *     For formatting, the RFC 822 4-digit time zone format is used:
 *     <pre>
 *     <i>RFC822TimeZone:</i>
 *             <i>Sign</i> <i>TwoDigitHours</i> <i>Minutes</i>
 *     <i>TwoDigitHours:</i>
 *             <i>Digit Digit</i></pre>
 *     <i>TwoDigitHours</i> must be between 00 and 23. Other definitions
 *     are as for <a href="#timezone">general time zones</a>.
 *     <p>For parsing, <a href="#timezone">general time zones</a> are also
 *     accepted.
 * </ul>
 * <code>SimpleDateFormat</code> also supports <em>localized date and time
 * pattern</em> strings. In these strings, the pattern letters described above
 * may be replaced with other, locale dependent, pattern letters.
 * <code>SimpleDateFormat</code> does not deal with the localization of text
 * other than the pattern letters; that's up to the client of the class.
 * <p>
 *
 *
 * <h4>Examples</h4>
 *
 * The following examples show how date and time patterns are interpreted in
 * the U.S. locale. The given date and time are 2001-07-04 12:08:56 local time
 * in the U.S. Pacific Time time zone.
 * <blockquote>
 * <table border=0 cellspacing=3 cellpadding=0 summary="Examples of date and time patterns 

interpreted in the U.S. locale">
 *     <tr bgcolor="#ccccff">
 *         <th align=left>Date and Time Pattern
 *         <th align=left>Result
 *     <tr>
 *         <td><code>"yyyy.MM.dd G 'at' HH:mm:ss z"</code>
 *         <td><code>2001.07.04 AD at 12:08:56 PDT</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>"EEE, MMM d, ''yy"</code>
 *         <td><code>Wed, Jul 4, '01</code>
 *     <tr>
 *         <td><code>"h:mm a"</code>
 *         <td><code>12:08 PM</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>"hh 'o''clock' a, zzzz"</code>
 *         <td><code>12 o'clock PM, Pacific Daylight Time</code>
 *     <tr>
 *         <td><code>"K:mm a, z"</code>
 *         <td><code>0:08 PM, PDT</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>"yyyyy.MMMMM.dd GGG hh:mm aaa"</code>
 *         <td><code>02001.July.04 AD 12:08 PM</code>
 *     <tr>
 *         <td><code>"EEE, d MMM yyyy HH:mm:ss Z"</code>
 *         <td><code>Wed, 4 Jul 2001 12:08:56 -0700</code>
 *     <tr bgcolor="#eeeeff">
 *         <td><code>"yyMMddHHmmssZ"</code>
 *         <td><code>010704120856-0700</code>
 * </TABLE>
 * </blockquote>
 *
 * @author  丰联数码
 * @version 1.0, 2005-6-2
 * @since   JDK1.4.2
 */
public class DateTime extends java.util.Date
{

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 5681451946899733263L;

    /**
     * 构造一个<code>DateTime</code>对象。其值为当前系统时间及日期。
     */
    public DateTime()
    {
        Date d = new Date();
        super.setTime(d.getTime());
    }

    /**
     * 构造一个<code>DateTime</code>对象。
     *  
     * @param d - 日期对象
     */
    public DateTime(java.util.Date d)
    {
        super(d.getTime());
    }

    /**
     * 将java.util.Date对象<code>d</code>转换成日期或时间串。转换的格式由

<code>pattern</code>参数指定。
     * 
     * 字母  日期或时间元素  表示  示例  <br>
     * G  Era 标志符  Text  AD  <br>
     * y  年  Year  1996; 96  <br>
     * M  年中的月份  Month  July; Jul; 07<br>  
     * w  年中的周数  Number  27  <br>
     * W  月份中的周数  Number  2  <br>
     * D  年中的天数  Number  189  <br>
     * d  月份中的天数  Number  10  <br>
     * F  月份中的星期  Number  2  <br>
     * E  星期中的天数  Text  Tuesday; Tue<br>  
     * a  Am/pm 标记  Text  PM  <br>
     * H  一天中的小时数（0-23）  Number  0<br>  
     * k  一天中的小时数（1-24）  Number  24  <br>
     * K  am/pm 中的小时数（0-11）  Number  0  <br>
     * h  am/pm 中的小时数（1-12）  Number  12  <br>
     * m  小时中的分钟数  Number  30  <br>
     * s  分钟中的秒数  Number  55  <br>
     * S  毫秒数  Number  978  <br>
     * z  时区  General time zone  Pacific Standard Time; PST; GMT-08:00<br>  
     * Z  时区  RFC 822 time zone  -0800  <br>
     *
     * @param pattern - 日期及时间样式串
     * @return 返回日期及时间样式串。<br>
     *          如果 d=null ，则返回null；<br>
     *          如果<code>pattern</code>，返回"日期样式参数 pattern 为null"；<br>
     *          如果<code>pattern</code>，返回"日期样式参数 pattern 无效格式"；<br>
     * 
     * @since JDK1.4.2
     */
    public String format(String pattern)
    {

        if (this == null) return null;

        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(this);
        }
        catch (NullPointerException e)
        {
            return "日期样式参数 pattern 为null";
        }
        catch (IllegalArgumentException e)
        {
            return "日期样式参数 pattern 无效格式";
        }

    }

    /**
     * 比较日期早晚及间隔。返回值的绝对值为两个日期的相差天数。<br>
     * 该方法只比较到天为止，不对天以下的时间进行比较。如：“2005年3月21日 14:32”与
     * “2005年3月22日 11:12”的返回值为-1
     *
     * @param anotherDate - 要比较的日期
     * 
     * @return 如果返回0表示两个日期相同；<br>
     * 如果返回小于0的整数，表示DateTime比<code>anotherDate</code>早；<br>
     * 如果返回小于0的整数，表示DateTime比<code>anotherDate</code>晚。<br>
     * 
     * @since JDK1.4.2
     */
    public long compareDate(Date anotherDate)
    {
        // 86400000=1000*60*60*24(毫秒×秒×分×小时)
        return (this.getTime() / 86400000 - anotherDate.getTime() / 86400000);
    }

    /**
     * 比较时间早晚及间隔。返回值的绝对值为两个时间的间隔数。<br>
     * 
     * 参数<code>mode</code>由下表字符构成；如果<code>mode</code>为除下面以外的字符，则

默认其为
     * <code>mode='d'</code>：
     * <blockquote>
     * <table border=0 cellspacing=3 cellpadding=0>
     *     <tr bgcolor="#ccccff">
     *         <th align=left>字符</th>
     *         <th align=left>含义</th>
     *         <th align=left>返回值单位</th>
     *     </tr>
     *     <tr>
     *         <td><code>y</code></td>
     *         <td><code>以 年 为单位</code></td>
     *         <td><code>年</code></td>
     *     </tr>
     *     <tr bgcolor="#eeeeff">
     *         <td><code>M</code></td>
     *         <td><code>以 月 为单位</code></td>
     *         <td><code>月</code></td>
     *     </tr>
     *     <tr>
     *         <td><code>d</code></td>
     *         <td><code>以 天 为单位</code></td>
     *         <td><code>天</code></td>
     *     </tr>
     *     <tr bgcolor="#eeeeff">
     *         <td><code>w</code></td>
     *         <td><code>以 周 为单位</code></td>
     *         <td><code>周</code></td>
     *     </tr>
     *     <tr>
     *         <td><code>h</code></td>
     *         <td><code>以 小时 为单位</code></td>
     *         <td><code>小时</code></td>
     *     </tr>
     *     <tr bgcolor="#eeeeff">
     *         <td><code>m</code></td>
     *         <td><code>以 分 为单位</code></td>
     *         <td><code>分</code></td>
     *     </tr>
     *     <tr>
     *         <td><code>s</code></td>
     *         <td><code>以 秒 为单位</code></td>
     *         <td><code>秒</code></td>
     *     </tr>
     * </TABLE>
     * </blockquote>
     * 
     * <h4>举例：</h4>
     * “2004年3月21日”与“2005年4月22日”进行比较，<br>
     * 　　<code>y</code>方式比较：返回值为-1<br>
     * 　　<code>M</code>方式比较：返回值为-13<br>
     * 
     * “2005年3月21日 14:00”与“2005年3月22日 15:00”进行比较，<br>
     * 　　<code>d</code>方式比较：返回值为-1<br>
     * 　　<code>w</code>方式比较：返回值为0<br>
     * 　　<code>h</code>方式比较：返回值为25<br>
     * 
     *
     * @param anotherTime - 要比较的时间
     * @param mode - 比较模式
     * 
     * @return 
     *   如果返回0表示两个时间相同；<br>
     *   如果返回小于0的整数，表示DateTime比<code>anotherDate</code>早；<br>
     *   如果返回小于0的整数，表示DateTime比<code>anotherDate</code>晚；<br>
     * 
     * @since JDK1.4.2
     */
    public long compareTime(Date anotherTime, char mode)
    {

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(this);
        c2.setTime(anotherTime);

        switch (mode)
        {
        case 'y':// 比年
            return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);

        case 'M':// 比月
            int year = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
            int month = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
            return (year * 12 + month);

        case 'd':// 比天
            return (this.getTime() / 86400000 - anotherTime.getTime() / 86400000);

        case 'w':// 比周
            return (this.getTime() / 604800000 - anotherTime.getTime() / 604800000);

        case 'h':// 比小时
            return (this.getTime() / 3600000 - anotherTime.getTime() / 3600000);

        case 'm':// 比分
            return (this.getTime() / 60000 - anotherTime.getTime() / 60000);

        case 's':// 比秒
            return (this.getTime() / 1000 - anotherTime.getTime() / 1000);

        default://默认为‘ｄ’方式比较
            return (this.getTime() / 86400000 - anotherTime.getTime() / 86400000);
        }
    }

    /**
     * 获得明天日期。小时、分、秒计算在内。
     *
     * @return 以DateTime类型返回明天日期。
     * 
     * @since JDK1.4.2
     */
    public DateTime tomorrow()
    {
        Date date = new Date(this.getTime() + 86400000);
        return new DateTime(date);
    }

    /**
     * 获得昨天日期。小时、分、秒计算在内。
     *
     * @return 以DateTime类型返回昨天日期。
     * 
     * @since JDK1.4.2
     */
    public DateTime yesterday()
    {
        Date date = new Date(this.getTime() - 86400000);
        return new DateTime(date);
    }

}