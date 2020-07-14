package com.fenglian.tools.data;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fenglian.tools.extent.ApplicationContextUtil;
import com.fenglian.tools.logger.FileLogger;
import com.fenglian.tools.util.CString;
import com.fenglian.tools.util.XMLBuffer;




/**
 * @author 张阳斌
 * 创建日期： 2005-7-25
 *
 * 类描述：对字典数据的查询和获取操作
 * 
 * 应用场合：单一字典数据查询
 * 
 * 版本：V0.3
 * 2005-09-19：张阳斌修改，使用ofBiz中的findByCondition无法完成，运算字段的查询，可在dictionary/@name属性中出现运算列，对应SQL语句中的查询列名称。
 * 2006-02-21：张阳斌修改字典查询，增加session中出现的字典查询条件值的查询方式，在condition/@type属性增加SESSION设置，由condition/@value属性指定session变量名称。
 * 2006-03-08：张阳斌修改字典静态数据配置方法，增加在字典xml中写入数据，阳斌修改字典数据保存对象从GenericValue 该成Map
 * 2006-05-25：张阳斌修改字典getName方法，增加默认值获取功能，增加default_value值的存储和获取。
 * 2006-06-14：张阳斌修改字典增加空的构造方法，自动获取delegator，无request对象，无法获取session参数。
 * 2006-06-19：张阳斌修改字典修改获取字典数据null参数的判断，增加控制getName（）。
 * 2006-08-14：付新鹏修改getDate方法里面的数据库关闭项
 */
public class DictionaryData {
    

    private HttpServletRequest m_request; //成员变量 请求数据请求对象
    private Hashtable m_namesBuffer = new Hashtable();
    private Map dic_parameter = new Hashtable();
    
    private DataSource ds ;
    
    /**
     * 构造函数：传入请求，获得数据操作对象GenericDelegator
     * @param request 表单请求
     */
    public DictionaryData(HttpServletRequest request) {
        m_request = request;
        ds = (DataSource)ApplicationContextUtil.getInstance().getBean("dataSource");
    }

    /**
     * 增加条件查询时使用。
     * @param 需要条件查询健和值，key是condition->field，value是要加入的条件值，现阶段只支持and查询。
     */
    public DictionaryData(Map dicParameter) {
    	dic_parameter = dicParameter;
    	 ds = (DataSource)ApplicationContextUtil.getInstance().getBean("dataSource");
    }
    
    /**
     * 构造函数：传入请求，获得数据操作对象GenericDelegator
     *	空的request对象，无法获取session配置数据。适应简单字典查询。
     */
    
    public DictionaryData() {
        m_request = null;
        ds = (DataSource)ApplicationContextUtil.getInstance().getBean("dataSource");
    }
    

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

    /**
     * 
     * 功能：字典数据获取操作，根据字典编号获取指定指定数据列表。
     * @param dicId  指定字典编号
     * @param searchType 查询数据类型是用来显示列表查询还是数据翻译查询。  list|show
     * @return  方法字典数据序列 
     */
    public List getData(String dicId, String searchType) {
        StringBuffer whereStr = new StringBuffer(" where 1=1 ");
        StringBuffer sql = new StringBuffer();
      
        try {
        	/*从缓存中取出，如果在配置中未表明是静态者不存入缓存，每次重新查询*/
        	if(DicBuffer.dicContainsKey(dicId+searchType) )
    		{
//        		System.out.println("使用系统缓存。"+dicId+searchType);
    			return DicBuffer.getDicBuffer(dicId+searchType);
    		}
        	
            Element dic = this.getDicElement(dicId);
            String tableName = dic.attributeValue("table-name");
            String code = dic.attributeValue("code");
            String name = dic.attributeValue("name");
            String orderStr = dic.attributeValue("orderby");
            String isStatic = dic.attributeValue("static");
    		
            /*取出缓存*/
			if("SESSION".equalsIgnoreCase(isStatic))
			{
				List s_list = (List)m_request.getSession().getAttribute("DIC_"+dicId+searchType);
				if(s_list!=null && !s_list.isEmpty())
				{
					return s_list;
				}
			}
			
            

            /* 2006-03-08 张阳斌修改：直接获取配置值 －在xml直接写入字典值可直接获取。 */
            List values = dic.elements("value");
            if (values != null && !values.isEmpty()) {
                List result = new ArrayList();

                for (Iterator it = values.iterator(); it.hasNext();) {
                    Element value = (Element) it.next();

                    DicInfo dicinfo = new DicInfo();
                    dicinfo.setKey(value.attributeValue("code"));
                    dicinfo.setValue(value.getText());
                    result.add(dicinfo);
                }

                /*放入缓存*/
            	if("YES".equalsIgnoreCase(isStatic))
    			{
    				DicBuffer.putDicBuffer(dicId+searchType,result);
    			}
    			
                return result;
            }

            /* 拼装查询条件 */
            List list_wher = dic.elements("condition");

            Iterator it_where = list_wher.iterator();

            while (it_where.hasNext()) {
                Element where = (Element) it_where.next();

                String field = where.attributeValue("field"); //字段名称
                String value = where.attributeValue("value"); //查询值
                String operator = where.attributeValue("operator"); //查询条件
                String type = where.attributeValue("type"); //查询应用类型
                String where_sql = where.attributeValue("sql"); //语句
                
                if("DIC_PARAMETER".equals(value))
                {
                	String paraname = where.attributeValue("name"); //参数名称
                	if(this.dic_parameter.containsKey(paraname))
                	{
                		value = this.dic_parameter.get(paraname).toString();
                	}else
                	{
                		value="";
                	}
                	
                }
                if(where_sql !=null && !"".equals(where_sql))
                {
                	if("SESSION".equals(type))
                	{
                		where_sql = repParameter(where_sql,m_request);
                	}else
                	{
                		where_sql = repParameter(where_sql,dic_parameter);
                	}
                	
                	whereStr.append(where_sql);
                }else{
                
                // LIST|SHOW|SESSION
                //2006-02-21新增session条件查询
                if ("SESSION".equals(type) && m_request != null) {

                    HttpSession session = m_request.getSession();
                    value = (String) session.getAttribute(value);
                    if (value != null) {
                        whereStr
                                .append(" AND "
                                        + field
                                        + getSearch(operator, value,
                                                "VARCHAR"));
                    }
                }

                if ("list".equalsIgnoreCase(searchType)) {
                    if (type == null || type.equals("") || type.equals("LIST")) {
                        whereStr
                                .append(" AND "
                                        + field
                                        + getSearch(operator, value,
                                                "VARCHAR"));
                    }
                }

                if ("show".equalsIgnoreCase(searchType)) {
                    if (type == null || type.equals("") || type.equals("SHOW")) {
                        whereStr
                                .append(" AND "
                                        + field
                                        + getSearch(operator, value,
                                                "VARCHAR"));
                    }
                }
               }

            }

            sql.append(" select  ");
            sql.append(code + ", ");

            sql.append(name + " ");
            sql.append(" from ");
            sql.append(tableName + " ");
            sql.append(whereStr + " ");
            if (!CString.isEmpty(orderStr)) {
                sql.append(" order by " + orderStr);
            }

            FileLogger.debug("字典查询SQL:"+sql.toString());

            /* 执行查询，并处理结果集 */
          
        	
    		JdbcTemplate jt = new JdbcTemplate(ds);
            
            
			
			List dicList = jt.queryForList(sql.toString());

			List list = new ArrayList();
			FileLogger.debug(dicId+"字典查询出:"+dicList.size()+"条");
			
			
			for(Iterator it = dicList.iterator();it.hasNext();)
			{
				Map map = (Map)it.next();
				DicInfo _dicinfo = new DicInfo();
				
				_dicinfo.setKey(map.get(findAS(code)).toString());
			
				if(map.get(findAS(name))!=null)
				_dicinfo.setValue(map.get(findAS(name)).toString());
									
				list.add(_dicinfo);
			}
			
			/*放入缓存*/
			if("YES".equalsIgnoreCase(isStatic))
			{
				DicBuffer.putDicBuffer(dicId+searchType,list);
			}
			/*放入缓存*/
			if("SESSION".equalsIgnoreCase(isStatic))
			{
				m_request.getSession().setAttribute("DIC_"+dicId+searchType,list);
			}
			
            return list;

        } catch (Exception e) {
            throw new RuntimeException( "字典数据查询失败!字典编号：" + dicId
                    + "  查询语句：" + sql.toString(),e);

        }
    }

    /**
     * 
     * 功能：将字典数据格式化成 html形式并返回。
     * 
     * @param dicId  要获得的字典编号
     * @param name html选择框名称，用来提交数据名称。
     * @param value 当前下拉菜单的默认选定值
     * @return 返回格式化后的字典数据。
     * 		<select id=”01”>	
     * 		<option vlaue="">name</option>
     * 		</select>
     */
    public Element getHTMLElement(String dicId, String name, String value,String defaultShow) {
        try {
      
            Element dictionary = DocumentHelper.createElement("select");
            dictionary.addAttribute("id", name);
            dictionary.addAttribute("name", name);
            
            if(defaultShow != null)
            {
            	Element defaultOption = DocumentHelper.createElement("option").addAttribute(
                    "value", "");
            	defaultOption.setText(defaultShow);            
            	dictionary.add(defaultOption);
            }
            
            //查找字典中所有数据
            Element dic = this.getDicElement(dicId);
            String default_select = CString.rep(dic
                    .attributeValue("default-select")); //默认值，但翻译code找不到对应的值时，使用默认值代替。 
           
            List dic_list = this.getData(dicId, "list");
            Iterator it = dic_list.iterator();
            while (it.hasNext()) {
                //GenericValue element = (GenericValue) it.next();
                DicInfo element = (DicInfo) it.next();

                Element option = DocumentHelper.createElement("option");
                option.addAttribute("value",element.getKey());
                option.setText(element.getValue());

                //判断值是否选取
                if (value != null)                	
                {
                     if( value.equals(element.getKey())) 
                     {
                    	 option.addAttribute("selected", "true");
                     }
                     
                }else if(element.getKey().equals(default_select)){
                	option.addAttribute("selected", "true");
                }

                dictionary.add(option);

            }

            return dictionary;

        } catch (Exception e) {
            throw new RuntimeException("字典数据获取失败!字典编号：" + dicId,e);
        }

    }

    /**
     * 
     * 功能：将字典数据格式化成 html形式并返回。
     * 
     * @param dicId  要获得的字典编号
     * 
     * @return 返回格式化后的字典数据字符串。
     * 		<select id=”01”>	
     * 		<option vlaue="">name</option>
     * 		</select>
     */
    public String getHTMLSelect(String dicId, String name, String value) {
        Element e = this.getHTMLElement(dicId, name, value,"");
        return e.asXML();
    }

    /**
     * 
     * 功能：将字典数据格式化成 html形式并返回。
     * 
     * @param dicId  要获得的字典编号
     * 
     * @return 返回格式化后的字典数据字符串。
     * 		<select id=”01”>	
     * 		<option vlaue="">name</option>
     * 		</select>
     */
    public String getHTMLSelect(String dicId, String name, String value,String defualtShow) {
        Element e = this.getHTMLElement(dicId, name, value,defualtShow);
        return e.asXML();
    }

    
    /**
     * 
     * 功能：将字典数据格式化成 html形式并返回。
     * 
     * @param dicId  要获得的字典编号
     * @param event JavaScript事件
     * @return 返回格式化后的字典数据字符串。
     * 		<select id=”01” OnChange('alert("dd")')>	
     * 		<option vlaue="">name</option>
     * 		</select>
     */
    public String getHTMLSelect(String dicId, String name, String value,String defualtShow,String event) {
        Element e = this.getHTMLElement(dicId, name, value,defualtShow);
        String sel = e.asXML();
        sel = sel.replaceFirst("><option","  "+event+" ><option");
        return sel;
    }
    
    /**
     * 
     * 功能： 获得指定查询字典xml元素
     * @param dicId  指定的描述编号
     * @return  字典元素
     * @throws JDOMException
     */
    public Element getDicElement(String dicId) {
        XMLBuffer xml = new XMLBuffer();
        Document doc = xml.getDocument(XMLBuffer.DICTIONARE_FILE);
        Element dic = (Element) doc.selectSingleNode("/querys/dictionary[@id='"
                + dicId + "']");
        if (dic == null)
            throw new RuntimeException("字典配置错误！无法找到指定字典项:" + dicId);
        return dic;
    }

    /**
     * 
     * 功能：根据代码获取获取名称返回，根据字典配置文件完成对字典数据的检索，并返回所对应的名称
     * 
     * @param dicId
     *            字典编号
     * @param code
     *            数据编码 
     * @return 返回数据名称
     */
    public String getName(String dicId, String code) {

        try {
            
        	/*判断本实例缓冲中时候存在该字典数据，如果存在则从缓冲中获取，否则重新查找。*/
        	
        	 Hashtable ht =  DicBuffer.getNamesBuffer(dicId);
        	 
        	 //如果从公共缓存中取不到，则说明该字典项是动态的，可在实例缓存中取。
        	 if(ht == null)
        	{
        		 ht = (Hashtable)m_namesBuffer.get(dicId);
//        		if(ht != null) FileLogger.debug("取本地缓存！");
        	}else
        	{
//        		FileLogger.debug("取全局缓存！");
        	}
        	 
            if (ht !=null) {
              
                if (code != null && ht.get(code) != null) {
                    return (String) ht.get(code);
                } else {
                    return CString.rep((String) ht.get("default_value"));
                }

            } else {
            	
            	
                //查找字典中所有数据
                Element dic = this.getDicElement(dicId);
                String isStatic = dic.attributeValue("static");
                
                String default_value = CString.rep(dic
                        .attributeValue("default-value")); //默认值，但翻译code找不到对应的值时，使用默认值代替。 

            
                Hashtable dicHt = new Hashtable();
                List l = this.getData(dicId, "show");
                Iterator it = l.iterator();
                while (it.hasNext()) {
                    DicInfo element = (DicInfo) it.next();
                    dicHt.put(element.getKey(), element.getValue());
                }

                dicHt.put("default_value", default_value); //保存默认值到缓存中。
                
                //
               
                //保存缓存，如果数据是静态则保存到全局缓存，否则保存到本实例缓存。
        		if("YES".equalsIgnoreCase(isStatic))
        		{	               
	                DicBuffer.putNamesBuffer(dicId, dicHt);
        		}else
        		{
        			m_namesBuffer.put(dicId, dicHt);
        		}

                if (code != null && dicHt.get(code) != null) {
                    return (String) dicHt.get(code);
                } else {
//
//                    if (CString.isEmpty((String) dicHt.get("default_value"))) {
//                        return code;
//                    }else
//                    {                        
                        return (String) dicHt.get("default_value");
//                    }
                }

            }

        } catch (Exception e) {
            FileLogger.debug(dicId + "号字段数据名称查询出现异常！");
            Logger log = Logger.getLogger(this.getClass().getName());
			log.error("", e);
            return "";
        }

    }
    
    
    /**
	 * 功能：完成查询条件的拼装，根据传入查询内容不同，拼装合适的查询条件
	 * @param search  操作符Operator 值：LIKE|%LIKE|LIKE%|%LIKE%|LOW|UP| EQU|LEQU|UEQU

	 * @param value  查询值
	 * @param type 字段类型 VARCHAR|NUMBER|DATE|DIC
	 * @return
	 */
	public static String getSearch(String search,String value,String type)
	{
		//没有给出查询条件的都按照“like”来查询。
		if (search==null || search.equals(""))	search = "LIKE";
		
		/*标识为like的项按模糊条件查询*/
		if (search.equals("LIKE"))
		{
			return " like "+getStr(type,value);
		}
		/*标识为like的项按模糊条件查询*/
		if (search.equals("%LIKE"))
		{
			return " like '%" + value + "'";
		}
		/*标识为like的项按模糊条件查询*/
		if (search.equals("LIKE%"))
		{
			return  " like '" + value + "%'";
		}
		/*标识为like的项按模糊条件查询*/
		if (search.equals("%LIKE%"))
		{
			return  " like '%" + value + "%'";
		}
		
		/*小于查询*/
		if (search.equals("LOW"))
		{
			return  " < "+getStr(type,value);
		}
		
		/*大于查询*/
		if (search.equals("UP"))
		{
			return  " > "+getStr(type,value);
		}
		
		/*等于查询*/
		if (search.equals("EQU"))
		{
			return  " = "+getStr(type,value);
		}
		
		/*不等于查询*/
		if (search.equals("NOT"))
		{
			return  " <> "+getStr(type,value);
		}
		
		/*小于等于查询*/
		if (search.equals("LEQU"))
		{
			return  " <= "+getStr(type,value);
		}
		
		/*大于等于查询*/
		if (search.equals("UEQU"))
		{
			return  " >= "+getStr(type,value);
		}
				
		
		/*默认返回配置好的查询条件，与输入字段的查询*/
		return  " like " + getStr(type, value);
	}
	
	
	/**
	 * 功能：根据类型获得不同类型的拼装串。
	 * @param type 数据类型：VARCHAR|NUMBER|DATE|DIC
	 * @param value 插入数据值
	 * @return 拼装后的字符串
	 */
	public static String getStr(String type, String value)
	{
		if (type == null)
			return value;
		if (type.equals("VARCHAR"))
		{
			return "'" + value + "'";
		}
			
		if (type.equals("DATE"))
		{
			//return "to_date('" + value + "','" + dateMark + "')";
			return "'" + value + "'";

		}
		if (type.equals("NUMBER"))
		{
			return "'" + value + "'";
		}
		if (type.equals("DIC"))
		{
			return "'" + value + "'";
		}
		
		return  value ;
	}

	public void refurbish()
	{
		synchronized (m_namesBuffer) {
			m_namesBuffer.clear();
		}		
	}
	
	
	public void refurbishAll()
	{
		synchronized (m_namesBuffer) {
			m_namesBuffer.clear();
			DicBuffer.refurbish();
		}		
	}
	
	public String findAS(String str)
	{
		if(str.indexOf(" AS ")>1)
		{
			str = str.substring(str.indexOf(" AS ")+4);
		}
		return str;
	}
	
	public String repParameter(String sql,Map para)
	{
		int init=0;
		while(sql.indexOf("${",init)>0)
		{
			int begin = sql.indexOf("${")+2;
			int end = sql.indexOf("}");
			if(end<0) break;
			String name = sql.substring(begin,end);
			String value = para.get(name)+"";
			if(value !=null)
			{
				sql = sql.replaceAll("\\u0024\\u007B"+name+"}",value);
			}
			init = end;
		}
		
		return sql;
	}
	
	public String repParameter(String sql,HttpServletRequest request)
	{
		int init=0;
		while(sql.indexOf("${",init)>0)
		{
			int begin = sql.indexOf("${")+2;
			int end = sql.indexOf("}");
			if(end<0) break;
			String name = sql.substring(begin,end);
			
			String value = request.getSession().getAttribute(name)+"";
			
			if(value !=null)
			{
				sql = sql.replaceAll("\\u0024\\u007B"+name+"}",value);
			}
			init = end;
		}
		
		return sql;
	}
	
	
	public static void main(String[] args) {
		DictionaryData dic = new DictionaryData();
//		List list = dic.getData("101","List");
//		FileLogger.debug(dic.getName("01","0"));
//		FileLogger.debug(dic.getName("01","1"));
//		FileLogger.debug(dic.getName("101","3"));
//		FileLogger.debug(dic.getName("101","4"));
//		
		String sql = "sdfsfsdf${ZHS}sdf${ddf}";
		Map map = new HashMap();
		map.put("ZHS","1111");
		map.put("ddf","2222");
		System.out.println(dic.repParameter(sql,map));
		
//		DictionaryData dic1 = new DictionaryData();
//		dic.getData("101","List");
//		dic1.getData("101","List");
//		
//		dic1.getData("101","List");
//		dic.getData("101","List");
//		
//		FileLogger.debug(dic.getHTMLSelect("101","sdf",""));
//		XMLBuffer.print(dic.getHTMLElement("02","sex","CN",""));
//		Hashtable ht = new Hashtable();
//		FileLogger.debug(ht.get("sd"));
//		
//		
//		  Session session = HibernateSessionFactory.currentSession();
//			session.beginTransaction();
////			List list = session.createSQLQuery("select mailid as code,threadid as name from Mail as DicInfo").list();
//			List list = session.createSQLQuery(" select mailid as code,threadid as name from Mail").list();
//			session.getTransaction().commit();
//		
//		
//		for(Iterator it = list.iterator();it.hasNext();)
//		{
//			Object ss[] = (Object[])it.next();
//			
//			FileLogger.debug(ss[0].getClass().getName());
//			FileLogger.debug(ss[1].toString());
////			DicInfo dicinfo= (DicInfo)it.next();
////			FileLogger.debug("字典代码:"+dicinfo.getCode());
////			FileLogger.debug("字典代码:"+dicinfo.getCode());
//		}
//		
//		FileLogger.debug("查询出:"+list.size()+"条");
	}

	public void setDic_parameter(Map dic_parameter) {
		this.dic_parameter = dic_parameter;
	}

}