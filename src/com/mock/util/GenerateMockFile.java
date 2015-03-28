package com.mock.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class GenerateMockFile {
	
	/**
	 * 生成mock文件
	 * @param url 请求地址
	 * @param content 接口返回结果
	 * @throws IOException 
	 */
	public void write(String url, String content) {
		String fileName = "D:\\mock.data.js";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(fileName));
	
			buffer.write("//time: " + dateFormat.format(new Date()));
			buffer.newLine();
			buffer.write("$.mockjax({");
			buffer.newLine();
			buffer.write("\turl: '" + url + "',");
			buffer.newLine();
			buffer.write("\tresponse: function () {");
			buffer.newLine();
			buffer.write("\t\tthis.responseText = " + content + ";");
			buffer.newLine();
			buffer.write("\t}");
			buffer.newLine();
			buffer.write("})");
			
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void downFile() {
		
	}
	
	public static void main(String args[]) {
		GenerateMockFile gmf = new GenerateMockFile();
		gmf.write("/response-callback", "{a:1,b:3}");
	}
}