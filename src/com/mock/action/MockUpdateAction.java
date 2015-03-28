package com.mock.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.mock.services.MockUpdateService;

public class MockUpdateAction implements MockAction {
	
	//请求地址
	private String url;
	//接口返回值
	private String content;
	//创建者
	private String author;
	//mock group
	private String mockGroup;
	//状态，主用于标识可用或不可用，默认是1
	private int mockStatus = 1;
	//mock名称
	private String mockName;
	//mock描述
	private String description;
	//mockID
	private String mockID;
	//更新记录数量
	private int count;
	//具体操作
	private String actionName;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, NumberFormatException, SQLException {
		this.getParameter(request, response);
		
		JSONObject jsonObject = new JSONObject();
		PrintWriter out = response.getWriter();
		
		//添加记录
		if (mockID == null && actionName == null) {
			jsonObject = this.addMockItem();
		} else {
			//编辑操作
			if (actionName != null && actionName.equals("edit")) {
				jsonObject = this.editMockItem(Integer.parseInt(mockID));
			} else {
				jsonObject = this.deleteMockItem(Integer.parseInt(mockID));
			}
		}
		
		out.print(jsonObject.toString());
	}
	
	/**
	 * 获取请求参数
	 * @param request
	 * @param response
	 */
	public void getParameter(HttpServletRequest request, HttpServletResponse response) {
		url = request.getParameter("url");
		content = request.getParameter("content");
		author = request.getParameter("author");
		mockGroup = request.getParameter("mockGroup");
		mockName = request.getParameter("mockName");
		description = request.getParameter("description");
		mockID = request.getParameter("mockID");
		actionName = request.getParameter("action");
		
		if (request.getParameter("mockStatus") != null) {
			mockStatus = Integer.parseInt(request.getParameter("mockStatus"));
		}
	}
	
	/**
	 * 增加一条记录
	 * @return {result: {},success: true}
	 */
	public JSONObject addMockItem() {
		MockUpdateService mus = new MockUpdateService();
		JSONObject jsonObject = new JSONObject();
		
		try {
			count = mus.addMockItem(mockName, url, content, author, mockGroup, mockStatus, description);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (count > 0) {
				jsonObject.put("success", true);
			} else {
				jsonObject.put("success", false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	/**
	 * 编辑记录
	 * @param mockID
	 * @throws SQLException
	 */
	public JSONObject editMockItem(int mockID) throws SQLException {
		MockUpdateService mus = new MockUpdateService();
		JSONObject jsonObject = new JSONObject();
		count = mus.editMockItem(mockID, mockName, url, content, description);
		
		try {
			if (count > 0) {
				jsonObject.put("success", true);
			} else {
				jsonObject.put("success", false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	/**
	 * 删除记录
	 * @param mockID
	 * @throws SQLException
	 */
	public JSONObject deleteMockItem(int mockID) throws SQLException {
		
		MockUpdateService mus = new MockUpdateService();
		count = mus.deleteMockItem(mockID);
		JSONObject jsonObject = new JSONObject();
		
		try {
			if (count > 0) {
				jsonObject.put("success", true);
			} else {
				jsonObject.put("success", false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
}
