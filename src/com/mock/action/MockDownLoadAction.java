package com.mock.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

public class MockDownLoadAction implements MockAction {
	
	private String rootPath;
	private String filePath;
	private String fileName;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, JSONException, IOException {
		
		//获取根目录
		rootPath = request.getSession().getServletContext().getRealPath("/");
		//文件名称
		fileName = URLEncoder.encode("mock.data.js", "UTF-8");
		//获取下载文件
		filePath = rootPath + fileName;
		
		this.downFile(request, response, filePath);
	}
	
	//下载文件
	public void downFile(HttpServletRequest request, HttpServletResponse response, String filePath) {
		File file = new File(filePath);
		
		if (file.exists()) {
			
			response.setContentType("application/x-download");
			response.setContentLength((int) file.length());
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			
			try {
				ServletOutputStream servletOutputStream = response.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(file);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
				
				int size = 0;
				byte[] b = new byte[1024];
				while((size = bufferedInputStream.read(b)) != -1) {
					servletOutputStream.write(b, 0, size);
				}
				
				servletOutputStream.flush();
				servletOutputStream.close();
				bufferedInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
