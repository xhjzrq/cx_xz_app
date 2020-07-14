package com.fenglian.tools.extent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class SplitPageResultSetExtractor implements ResultSetExtractor {
	private final int start;// 起始行号

	private final int len;// 结果集合的长�?
	private final int maxResult = 5000;

	public int rowCount;// 结果集合的长�?
	public boolean isToomuch = false;// 结果集合的长�?

	private final RowMapper rowMapper;// 行包装器

	public SplitPageResultSetExtractor(RowMapper rowMapper, int start, int len) {
		Assert.notNull(rowMapper, "RowMapper is required");
		this.rowMapper = rowMapper;
		this.start = start + 1;
		this.len = len;
	}

	/**
	 * 处理结果集合,被接口自动调用，该类外边不应该调�?
	 */
	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		List result = new ArrayList();
		int rowNum = 0;
		int end = start + len;
		point: while (rs.next()) {
			++rowNum;
			if (rowNum < start) {
				continue point;
			} else if (rowNum >= end) {
				if (rowNum > maxResult) {
					isToomuch = true;
					break;
				}
				// break point;
				continue point;
			} else {
				result.add(this.rowMapper.mapRow(rs, rowNum));
			}
		}
		this.rowCount = rowNum;
		return result;
	}
}
