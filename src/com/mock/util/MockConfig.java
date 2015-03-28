package com.mock.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class MockConfig {
	
	//数据库用户名
	public static String USERNAME;
	//数据库密码
	public static String PASSWORD;
	//数据库驱动
	public static String DBDRIVER;
	//数据库连接地址
	public static String DBURL;
	//mock文件名称
	public static String FileName;
	//保留jQuery123243的前缀
	public static boolean HaveJsonp;
	
	public static Properties prop = new Properties();

	static {
		try {
			//获取配置文件
			prop.load(MockConfig.class.getResourceAsStream("/config.properties"));
			
			USERNAME = prop.getProperty("USERNAME");
			PASSWORD = prop.getProperty("PASSWORD");
			DBDRIVER = prop.getProperty("DBDRIVER");
			DBURL = prop.getProperty("DBURL");
			FileName = prop.getProperty("FileName");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(MockConfig.USERNAME);
	}
	
}
