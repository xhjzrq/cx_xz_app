package com.fenglian.tools.extent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.fenglian.tools.logger.FileLogger;
import com.fenglian.tools.util.PageInfo;
import com.google.gson.Gson;


public class JdbcTemplatePage extends JdbcTemplate {

	private DataSource dataSource;

	private PageInfo pageInfo ;
	
	private String countSql = null;
	
		
	/**
	 * 默认构�?�器，调用此方法初始化，�?要调用setDataSource设置数据�?
	 */
	public JdbcTemplatePage() {
		
	}

	/**
	 * 初始构�?�器
	 * 
	 * @param dataSource
	 *            数据�?
	 */
	public JdbcTemplatePage(DataSource dataSource) {
		this.dataSource = dataSource;
		super.setDataSource(dataSource);
	}

	/**
	 * 普�?�分页查�?<br>
	 * <b>如果结果结合比较大应该调用setFetchsize() 和setMaxRow两个方法来控制一下，否则会内存溢�?</b>
	 * 
	 * @see #setFetchSize(int)
	 * @see #setMaxRows(int)
	 * @param sql
	 *            查询的sql语句
	 * @param startRow
	 *            起始�?
	 * @param rowsCount
	 *            获取的行�?
	 * @return
	 * @throws DataAccessException
	 */
	public List querySP(String sql, int startRow, int rowsCount)
			throws DataAccessException {
		
		return querySP(sql, startRow, rowsCount, super.getColumnMapRowMapper());
	}

	/**
	 * 自定义行包装器查�?<br>
	 * <b>如果结果结合比较大应该调用setFetchsize() 和setMaxRow两个方法来控制一下，否则会内存溢�?</b>
	 * 
	 * @see #setFetchSize(int)
	 * @see #setMaxRows(int)
	 * @param sql
	 *            查询的sql语句
	 * @param startRow
	 *            起始�?
	 * @param rowsCount
	 *            获取的行�?
	 * @param rowMapper
	 *            行包装器
	 * @return
	 * @throws DataAccessException
	 */
	public List querySP(String sql, int startRow, int rowsCount,
			RowMapper rowMapper) throws DataAccessException {
		SplitPageResultSetExtractor sps = 	new SplitPageResultSetExtractor(rowMapper,startRow, rowsCount);
		List list =  (List) query(sql,sps);
		
		//如果查询记录内容超过5000则直接调用count查询。
		if(sps.isToomuch==true)
		{
			FileLogger.debug("记录过多，执行Count查询。");
			if(countSql==null)
			{	
				String sql2=sql.toUpperCase();
				int order=sql2.indexOf("ORDER");
				if(order!=-1){
					sql2=sql.substring(0, order);
				}
//				System.out.println(sql2);
				countSql = 	"SELECT count(0) from ("+sql2+") a";
			}
			
			long a = System.currentTimeMillis();
			int rowCount = super.queryForInt(countSql);
			long b = System.currentTimeMillis();
			logger.debug("queryForPage count time :"+(b-a));
			
			this.pageInfo.setRowCount(rowCount);	
		}else
		{
			this.pageInfo.setRowCount(sps.rowCount);	
		}
		return list;
	}
	
	
	
	/**
	 * 执行分页查询，并自动计算总记录数，页面信息存放在pageinfo中�??
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List queryForPage(String sql,int pageNo,int pageSize)
	{
		pageInfo = new PageInfo();
		this.pageInfo.setPageNo(pageNo);
		this.pageInfo.setPageSize(pageSize);
		
		long a1 = System.currentTimeMillis();
		List list =querySP(sql,pageInfo.getFirstResult(),pageInfo.getPageSize());
		long b1 = System.currentTimeMillis();
		FileLogger.ylDebug("queryForPage time :"+(b1-a1));
		//System.out.println("查询用时 :"+(b1-a1)+"�?");
		return list;

	}
	
	/**
	 * 执行分页查询，不计算总记录数。根据返回为空判断是否有下一页
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List queryForSize(String sql,int pageNo,int pageSize)
	{
		pageInfo = new PageInfo();
		this.pageInfo.setPageNo(pageNo);
		this.pageInfo.setPageSize(pageSize);
		
		long a1 = System.currentTimeMillis();
		
		SplitPageResultSetExtractor sps = 	new SplitPageResultSetExtractor(super.getColumnMapRowMapper(),pageInfo.getFirstResult(), pageInfo.getPageSize());
		List list =  (List) query(sql,sps);
		
		long b1 = System.currentTimeMillis();
		FileLogger.ylDebug("queryForPage time :"+(b1-a1));
		//System.out.println("查询用时 :"+(b1-a1)+"�?");
		return list;

	}
	
	
	public Map queryForMap(String sql)
	{
		try{
			return super.queryForMap(sql);
		}catch(Exception e)
		{
			return null;
		}
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		super.setDataSource(dataSource);
	}

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}
	
	
	public String writeToText(String filename,String sql){
		super.query(sql,new WriteToFileRs(super.getColumnMapRowMapper(),filename));
		return filename;
	}
	
	final class WriteToFileRs implements ResultSetExtractor{
		RowMapper rowMapper;
		String f;
		public WriteToFileRs(RowMapper r,String filename){
			this.rowMapper=r;
			this.f=filename;
		}
		public Object extractData(ResultSet rs) throws SQLException,
				DataAccessException {
			// TODO Auto-generated method stub
			Gson gson = new Gson();
			try {				
				File file = new File(f);
				if(file.exists()){
					file.delete();
				}
				BufferedWriter writer=new BufferedWriter(new FileWriter(file));
				int i=0;
				while(rs.next()){
					writer.append(gson.toJson(rowMapper.mapRow(rs, i)));
					writer.newLine();
					i++;
				}
				writer.flush();
				writer.close();
				writer=null;
			} catch (Exception e) {
			}
			return null;
		}
	}
}
