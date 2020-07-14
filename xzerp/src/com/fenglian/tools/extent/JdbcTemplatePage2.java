package com.fenglian.tools.extent;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.fenglian.tools.logger.FileLogger;
import com.fenglian.tools.util.PageInfo;


public class JdbcTemplatePage2 extends JdbcTemplatePage {

	private DataSource dataSource;

	private PageInfo pageInfo = new PageInfo();
	
	private String countSql = null;
	
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 默认构�?�器，调用此方法初始化，�?要调用setDataSource设置数据�?
	 */
	public JdbcTemplatePage2() {
		
	}

	/**
	 * 初始构�?�器
	 * 
	 * @param dataSource
	 *            数据�?
	 */
	public JdbcTemplatePage2(DataSource dataSource) {
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
		this.pageInfo.setRowCount(sps.rowCount);
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
		
		if(countSql==null)
		{			
			countSql = 	"SELECT count(0) from ("+sql+") a";
		}
		long a = System.currentTimeMillis();
		int rowCount = super.queryForInt(countSql);
		long b = System.currentTimeMillis();
		FileLogger.ylDebug("queryForPage count time :"+(b-a));
		
		
		this.pageInfo.setRowCount(rowCount);
		this.pageInfo.setPageNo(pageNo);
		this.pageInfo.setPageSize(pageSize);
		
		String limitSql  = "select * from ("+sql+") _a limit "+pageInfo.getFirstResult()+","+pageInfo.getPageSize();
		long a1 = System.currentTimeMillis();
		List list =super.queryForList(limitSql);	
		long b1 = System.currentTimeMillis();
		FileLogger.ylDebug("queryForPage time :"+(b1-a1));
		System.out.println("2查询用时"+(b1-a1)+"�?");
		return list;
		
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

}
