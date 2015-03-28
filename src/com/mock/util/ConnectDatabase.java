package com.mock.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
	
	private final static String USERNAME = MockConfig.USERNAME;
	private final static String PASSWORD = MockConfig.PASSWORD;
	private final static String DBDRIVER = MockConfig.DBDRIVER;
	//jdbc:mysql://IP:端口号/数据库名称
	private final static String DBURL = MockConfig.DBURL;
	
	public Connection getConnection() {
		
		Connection con = null;
		
		//加载驱动
		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//连接数据库
		try {
			con = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
}
