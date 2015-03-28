package com.mock.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.mock.services.MockQueryService;

public class MockQueryAction implements MockAction {

	//请求地址
	private String url;
	//创建者
	private String author;
	//mock名称
	private String mockName;
	//mock描述
	private String description;
	//mockId
	private String mockID;
	//具体操作
	private String actionName;
	
	//用于获取数量不确定参数
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	//http://servername/webname/pagename.csp?paramName=paramValue
	private String queryString;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException, IOException {
		
		PrintWriter out = response.getWriter();
		JSONArray jsonResult = new JSONArray();
		String scriptResult = null;
		
		this.getParameter(request, response);
		
		if ((queryString = request.getQueryString()) != null) {
			if (mockID != null) {
				//编辑操作
				if (actionName != null && actionName.equals("edit")) {
					jsonResult = this.filterMockByID(Integer.parseInt(mockID));
				} else {//默认是预览接口
					//查询结果预览接口返回值
					scriptResult = this.queryMockByID(mockID);
				}
			} else {
				//按条件查询
				jsonResult = this.filterMock();
			}
		} else {
			//显示所有记录
			jsonResult = this.showMockList();
		}
		
		//判断返回结果类型
		if (scriptResult == null) {
			out.print(jsonResult.toString());
		} else {
			out.print(scriptResult);
		}
		out.close();
	}
	
	/**
	 * 获取请求参数
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	public void getParameter(HttpServletRequest request, HttpServletResponse response) {
		url = request.getParameter("url");
		author = request.getParameter("author");
		mockName = request.getParameter("mockName");
		description = request.getParameter("description");
		mockID = request.getParameter("mockID");
		actionName = request.getParameter("action");
		
//		Enumeration params = request.getParameterNames();
//		
//		while(params.hasMoreElements()) {
//			String paramName = (String) params.nextElement();
//			paramHashMap.put(paramName, request.getParameter(paramName));
//		}
	}
	
	/**
	 * 查询mock
	 * @return
	 * @throws SQLException
	 * @throws JSONException 
	 */
	public JSONArray filterMock() throws SQLException, JSONException {
		MockQueryService mqs = new MockQueryService();
		return mqs.filterMock(mockName, url, author, description);
	}
	
	/**
	 * 
	 * @param mockID
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public JSONArray filterMockByID(int mockID) throws SQLException, JSONException {
		MockQueryService mqs = new MockQueryService();
		return mqs.filterMockByID(mockID);
	}
	
	/**
	 * 根据mockid返回查询结果，查询结果预览接口返回值，支持多个mockid
	 * @param mockId
	 * @return
	 * @throws NumberFormatException
	 * @throws SQLException
	 * @throws JSONException
	 */
	public String queryMockByID(String mockId) throws NumberFormatException, SQLException, JSONException {
		MockQueryService mqs = new MockQueryService();
		return mqs.queryMockByID(mockID);
	}
	
	/**
	 * 显示mock列表
	 * @return
	 * @throws SQLException
	 * @throws JSONException 
	 */
	public JSONArray showMockList() throws SQLException, JSONException {
		MockQueryService mqs = new MockQueryService();
		return mqs.showMockList();
	}
}
