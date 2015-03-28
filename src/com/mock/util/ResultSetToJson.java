package com.mock.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultSetToJson {
	
	/**
	 * ResultSet转化为Json
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public JSONArray toJson(ResultSet resultSet) throws SQLException, JSONException {
		JSONArray jsonArray = new JSONArray();
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		
		while(resultSet.next()) {
			JSONObject jsonObject = new JSONObject();
			
			for (int i = 1; i <= columnCount; i++) {
				String columnLabel = metaData.getColumnLabel(i);
				jsonObject.put(columnLabel, resultSet.getObject(columnLabel));
			}
			
			jsonArray.put(jsonObject);
		}
		
		return jsonArray;
		
	}
}
