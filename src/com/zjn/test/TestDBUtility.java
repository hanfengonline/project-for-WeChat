package com.zjn.test;

import java.sql.SQLException;

import org.junit.Test;

import com.zjn.util.DBUtility;

public class TestDBUtility {
	/*
	 * �������ݿ��Ƿ�����
	 */
	@Test
	public void testgetConnection() throws SQLException {
		DBUtility db = new DBUtility();
		System.out.println(db.getConnection());
	}
}
