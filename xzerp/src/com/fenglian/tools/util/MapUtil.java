package com.fenglian.tools.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapUtil {

	public static Map toLower(Map map)
	{
		Map info = new HashMap();
		Iterator it = map.keySet().iterator();
		while(it.hasNext())
		{
			String key = (String)it.next();
			info.put(key.toLowerCase(), map.get(key));
		}
		return info;
	}
}
