package com.zjn.test;

import java.sql.SQLException;

import org.junit.Test;

import com.zjn.util.DBUtility;

public class TestDBUtility {
	/*
	 * 测试数据库是否链接
	 */
	@Test
	public void testgetConnection() throws SQLException {
		DBUtility db = new DBUtility();
		System.out.println(db.getConnection());
	}
}
