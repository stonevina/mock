package com.mock.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mock.action.MockAction;

public class MockControlServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 604843000619369571L;

	/**
	 * Constructor of the object.
	 */
	public MockControlServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//PrintWriter默认使用ISO-8859-1编码，需修改
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//获取匹配的Servlet路径，如/test.action
		String pathName = request.getServletPath();
		//获取Action名称
		String actionName = pathName.substring(1, pathName.indexOf("."));
		//获取Action对象
		String actionClass = this.getInitParameter(actionName);
		MockAction mockAction = null;
		
		//错误的action跳转至error页面
		if (actionClass == null) {
			request.getRequestDispatcher("error.jsp").forward(request, response);  
		} else {
			try {
				mockAction = (MockAction) Class.forName(actionClass).newInstance();
				mockAction.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void init() throws ServletException {
		System.out.println("这里获取web.xml的初始化参数");
		
//		this.getServletContext().getInitParameter("dfsd");
	}

}
