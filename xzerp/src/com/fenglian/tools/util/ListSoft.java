package com.fenglian.tools.util;

import java.util.Comparator;
import java.util.Map;

/**
 * list 对象排序方法,
 * @author zhagn.yb
 *
 */
public class ListSoft  implements Comparator{

	String key="";
	int order = 0;
	
	/**
	 * @param key  列名
	 * @param order 升降排序方式。 1正序 。-1倒序，
	 */
	public ListSoft(String key,int order)
	{
		this.key = key;
		this.order = order;
	}
	
	public int compare(Object o1, Object o2) {

		Map m1 = (Map)o1;
		Map m2 = (Map)o2;
		int i1 = CObject.getInt(m1.get(key));
		int i2 = CObject.getInt(m2.get(key));
		
		if(i1<i2)
		{
			return order;
		}else
		{
			return 0;
		}
		
	}

}
