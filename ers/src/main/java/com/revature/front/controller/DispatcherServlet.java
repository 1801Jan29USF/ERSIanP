package com.revature.front.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;

import com.revature.controller.LoginController;
import com.revature.util.LogSingleton;

/*
 * The Dispatcher receives an HttpServletRequest and parses the url 
 * to determine which specific Servlet will handle the request.
 * 
 */

public class DispatcherServlet extends DefaultServlet {

	/*******************************************************************************
	 * Class Fields
	 ********************************************************************************/

	private LoginController lc = new LoginController();

	/*******************************************************************************
	 * Class Methods
	 ********************************************************************************/

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// get the extra path information after the URI
		String url = request.getPathInfo();
		if (url.startsWith("/static/")) {

			LogSingleton.getLogger().trace("Post request made with path starting with '/static/' ");
			super.doGet(request, response);
			return;
		} else {
			if (url.startsWith("/Login")) {
				System.out.println("hello");
				LogSingleton.getLogger().trace("Post request made with path" + url);
				lc.doPost(request, response);
			}
		}
	}
}
