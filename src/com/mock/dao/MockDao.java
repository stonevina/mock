package com.mock.dao;

import java.util.Date;

public class MockDao {
	
	private int mockID;
	private String url;
	private String content;
	private String createTime;
	private String author;
	private String mockgroup;
	private int mockstatus;
	
	public int getMockID() {
		return mockID;
	}
	public void setMockID(int mockID) {
		this.mockID = mockID;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getMockgroup() {
		return mockgroup;
	}
	public void setMockgroup(String mockgroup) {
		this.mockgroup = mockgroup;
	}
	public int getMockstatus() {
		return mockstatus;
	}
	public void setMockstatus(int mockstatus) {
		this.mockstatus = mockstatus;
	}
	
}
