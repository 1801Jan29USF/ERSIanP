package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.services.UserService;
import com.revature.util.LogSingleton;
import com.revature.util.ResponseUtil;

/*
 * /login searches Database for matching Login information. If the User
 * has entered the correct credentials a login message is returned otherwise
 * an incorrect credentials message is returned.
 * 
 */

public class UserController implements HttpController {

	/*******************************************************************************
	 * Class Fields
	 ********************************************************************************/

	private UserService us = new UserService();
	private ResponseUtil ru = new ResponseUtil();

	/*******************************************************************************
	 * HTTP Request Interception Methods
	 ********************************************************************************/

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		String url = req.getPathInfo();

		LogSingleton.getLogger().trace("post request delegated to login controller");
		if (url.equals("/Login")) {

			String str = us.login();
			ru.writeObjectToResponse(str, resp);
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

}
