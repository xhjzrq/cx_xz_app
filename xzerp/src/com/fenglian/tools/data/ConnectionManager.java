package com.fenglian.tools.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.fenglian.tools.logger.FileLogger;



public class ConnectionManager {

	public static void closeAll(Connection con,Statement stm,ResultSet rs)
	{		
		try{
		
			if(rs!=null)
				rs.close();	
			if(stm !=null)
				stm.close();
			if(con!=null)
				con.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			FileLogger.error("Connection close error!",e);
		}
		
	}
}
