package com.mock.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

public interface MockAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException, IOException;
}
