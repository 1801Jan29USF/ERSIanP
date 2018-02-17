package com.revature.front.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;

/*
 * The Dispatcher receives an HttpServletRequest and parses the url 
 * to determine which specific Servlet will handle the request.
 * 
 */

public class DispatcherServlet extends DefaultServlet {
	private Logger log = Logger.getRootLogger();
	private UserController uc = new UserController();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// get the extra path information after the URI
		String url = request.getPathInfo();
		if (url.startsWith("/static/")) {

			log.trace("Get request made with path starting with '/static/' ");
			super.doGet(request, response);
			return;
		} else {
			if (url.startsWith("/Login")) {

				log.trace("Get request made with path" + url);
				uc.doGet(request, response);
			}
		}
	}
}
