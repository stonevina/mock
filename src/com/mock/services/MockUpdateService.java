package com.mock.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mock.util.SqlExecute;

public class MockUpdateService {
	
	SqlExecute se = new SqlExecute();
	PreparedStatement psmt = null;
	Connection con = se.getConnection();
	
	/**
	 * 添加一条mock记录
	 * @param mockName
	 * @param url
	 * @param content
	 * @param author
	 * @param mockGroup
	 * @param mockStatus
	 * @param description
	 * @throws SQLException
	 */
	public int addMockItem(String mockName, String url, String content, String author, String mockGroup, int mockStatus, String description) throws SQLException {
		String sql = "INSERT INTO `mock` (mockName, url, content, createTime, modifyTime, author, mockGroup, mockStatus, description) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		psmt = con.prepareStatement(sql);
		psmt.setString(1, mockName);
		psmt.setString(2, url);
		psmt.setString(3, content);
		psmt.setString(4, sdf.format(date));
		psmt.setString(5, sdf.format(date));
		psmt.setString(6, author);
		psmt.setString(7, mockGroup);
		psmt.setInt(8, mockStatus);
		psmt.setString(9, description);
		
		return se.update(psmt);
	}
	
	/**
	 * 编辑一条mock
	 * @param mockID
	 * @param url
	 * @param content
	 * @param author
	 * @param mockGroup
	 * @param mockStatus
	 * @throws SQLException
	 */
	public int editMockItem(int mockID, String mockName, String url, String content, String description) throws SQLException {
//		String sql = "UPDATE mock SET mockName =?, url =?, content =?, author =?, mockGroup =?, mockStatus =?, description =? WHERE mockID =?";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String sql = "UPDATE mock SET mockName =?, url =?, content =?, description =?, modifyTime =? WHERE mockID =?";
		
		psmt = con.prepareStatement(sql);
		psmt.setString(1, mockName);
		psmt.setString(2, url);
		psmt.setString(3, content);
		psmt.setString(4, description);
		psmt.setString(5, dateFormat.format(new Date()));
		psmt.setInt(6, mockID);
		
		return se.update(psmt);
	}
	
	/**
	 * 删除一条mock
	 * @param mockID
	 * @throws SQLException
	 */
	public int deleteMockItem(int mockID) throws SQLException {
		String sql = "DELETE FROM mock WHERE mockID =?";
		
		psmt = con.prepareStatement(sql);
		psmt.setInt(1, mockID);
		
		return se.update(psmt);
	}
	
	public static void main(String[] args) throws SQLException {
		MockUpdateService mus = new MockUpdateService();
		//mus.addMockItem("礼品购节日列表", "http://gift.jd.com/init.action", "{a:1,b:3}", "shijianguo", "user", 1, "礼品购的测试数据");
		mus.deleteMockItem(3);
	}
}
