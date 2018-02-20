package com.revature.frontcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;

import com.revature.controller.LoginController;
import com.revature.util.LogSingleton;

public class DispatcherServlet extends DefaultServlet {

	LoginController lc = new LoginController();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String url = request.getPathInfo();
		LogSingleton.getLogger().trace("Get request made with path " + url);
		if (url.startsWith("/static/")) {
			
			if (url.startsWith("/static/Login.html")) {
				lc.doGet(request, response);
			}
			super.doGet(request, response);
			return;
		} else {
			if (url.startsWith("/Login")) {
				request.getRequestDispatcher("/static/Login.html").forward(request,response);
			}
		}
	}
}
