package com.mock.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;

public class SqlExecute {
	PreparedStatement psmt = null;
	Connection con = new ConnectDatabase().getConnection();
	ResultSet rs = null;
	ResultSetToJson resultSetToJson = new ResultSetToJson();
	
	/**
	 * 查询操作
	 * @param psmt
	 * @return ResultSet
	 * @throws SQLException
	 * @throws JSONException
	 */
	public JSONArray query(PreparedStatement psmt) throws SQLException, JSONException {
		rs = psmt.executeQuery();
		JSONArray jsonArray = resultSetToJson.toJson(rs);
		
		psmt.close();
		con.close();
		
		return jsonArray;
	}
	
	/**
	 * 更新操作
	 * @param psmt
	 * @return
	 * @throws SQLException
	 */
	public int update(PreparedStatement psmt) throws SQLException {
		int count = 0;
		
		count = psmt.executeUpdate();
		
		psmt.close();
		con.close();
		
		return count;
	}
	
	/**
	 * 获取Connection对象
	 * @return
	 */
	public Connection getConnection() {
		return con;
	}
	
	public static void main(String[] args) throws SQLException, JSONException {
//		String sql = "insert into user(id, name, sex) values(2, 'dsfs', 'df')";
		SqlExecute se = new SqlExecute();
		
		String sql = "SELECT * FROM `mock`";
		PreparedStatement psmt = se.getConnection().prepareStatement(sql);
		
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		sdf.format(dt);
		
		try {
			System.out.println(sdf.format(dt));
			System.out.println(se.query(psmt));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
