package com.mock.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mock.util.SqlExecute;

public class MockQueryService {
	SqlExecute se = new SqlExecute();
	PreparedStatement psmt = null;
	Connection con = se.getConnection();
	
	/**
	 * 显示mock记录列表
	 * @return
	 * @throws SQLException
	 * @throws JSONException 
	 */
	public JSONArray showMockList() throws SQLException, JSONException {
		String sql = "SELECT * FROM `mock`";
		psmt = con.prepareStatement(sql);
		
		return se.query(psmt);
	}
	
	/**
	 * 通过id检索mock
	 * @param mockID
	 * @param args
	 * @return
	 * @throws SQLException
	 * @throws JSONException 
	 */
	public JSONArray filterMock(int mockID) throws SQLException, JSONException {
		String sql = "SELECT * FROM `mock` m WHERE m.mockID = ? or m.mockName LIKE ? or m.url like ? or m.author LIKE ? or m.description LIKE ?";
		
		psmt = con.prepareStatement(sql);
		psmt.setInt(1, mockID);
		
		return se.query(psmt);
	}
	
	/**
	 * 模糊查找
	 * @param args
	 * @return
	 * @throws SQLException
	 * @throws JSONException 
	 */
	public JSONArray filterMock(String... args) throws SQLException, JSONException {
		String sql = "SELECT * FROM `mock` m WHERE m.mockName LIKE ? or m.url like ? or m.author LIKE ? or m.description LIKE ?";
		
		psmt = con.prepareStatement(sql);
		
		for (int i = 0; i < args.length; i++) {
			psmt.setString((i + 1), args[i]);
		}
		
		return se.query(psmt);
	}
	
	/**
	 * 根据mockID查询单条记录，用于编辑记录
	 * @param mockID
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public JSONArray filterMockByID(int mockID) throws SQLException, JSONException {
		String sql = "SELECT * FROM `mock` m WHERE mockID = ?";
		
		psmt = con.prepareStatement(sql);
		psmt.setInt(1, mockID);
		
		return se.query(psmt);
	}
	
	/**
	 * 根据mockid获取接口的url以及接口返回结果，支持多个mockid
	 * @param mockID
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public String queryMockByID(String mockID) throws SQLException, JSONException {
		String sql = "SELECT m.mockID mockID, m.url url, m.content content, m.mockName mockName, m.author author, m.modifyTime time FROM mock m WHERE m.mockID in (";
		String[] mockArray = mockID.split(",");
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < mockArray.length; i++) {
			builder.append("?,");
		}
		
		sql += builder.deleteCharAt( builder.length() -1 ).toString() + ")";
		psmt = con.prepareStatement(sql);
		for(int i = 0; i < mockArray.length; i++) {
			psmt.setInt((i+1), Integer.parseInt(mockArray[i]));
		}
		
		JSONArray jsonArray = se.query(psmt);
		JSONObject jsonObject = new JSONObject();
		
		StringBuffer sb = new StringBuffer();
		
		//be inited
		String url = null;
		Object content = null;
		String mockName = null;
		String time = null;
		String author = null;
		int mockId;
		
		if (jsonArray.length() > 0) {
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				url = (String) jsonObject.get("url");
				content = jsonObject.get("content");
				mockName = (String) jsonObject.get("mockName");
				time = (String) jsonObject.get("time");
				author = (String) jsonObject.get("author");
				mockId = jsonObject.getInt("mockID");
				sb.append(generateMockResult(url, content, mockName, time, author, mockId));
				sb.append("\r\n");
			}
		} else {
			sb.append("no result");
		}
		
		return sb.toString();
	}
	
	/**
	 * 生成mock返回结果
	 * @param url
	 * @param content
	 * @return
	 */
	public String generateMockResult(String url, Object content, String mockName, String time, String author, int mockId) {
		StringBuffer sb = new StringBuffer();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		sb.append("//修改时间：" + time + "\n");
		sb.append("//接口名：" + mockName + "\n");
		sb.append("//创建者：" + author + "\n");
		sb.append("//接口ID：" + mockId + "\n");
		sb.append("$.mockjax({\n");
		sb.append("\turl: '" + url + "',\n");
		sb.append("\tresponse: function () {\n");
		sb.append("\t\tthis.responseText = " + content + ";\n");
		sb.append("\t}\n");
		sb.append("})");
		
		return sb.toString();
	}
	
	public static void main(String[] args) throws SQLException, JSONException {
		MockQueryService mqs = new MockQueryService();
		System.out.println(mqs.queryMockByID("42"));
	}
}
