package com.revature.frontcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;

import com.revature.controller.EmployeeHomeController;
import com.revature.controller.EmployeeProfileController;
import com.revature.controller.EmployeeSubmitRequestController;
import com.revature.controller.LoginController;
import com.revature.util.LogSingleton;

/*
 * Dispatcher Servlet class receives all requests and delegates the request to the specific controller
 * based on the url.
 * 
 */
public class DispatcherServlet extends DefaultServlet {

	/*******************************************************************************
	 * DispatcherServlet Fields
	 ********************************************************************************/

	LoginController lc = new LoginController();
	EmployeeHomeController ehc = new EmployeeHomeController();
	EmployeeProfileController epc = new EmployeeProfileController();
	EmployeeSubmitRequestController src = new EmployeeSubmitRequestController();
	PastTicketsController ptc = new pastTicketsController();

	/*******************************************************************************
	 * DispatcherServlet Methods
	 ********************************************************************************/

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String url = request.getPathInfo();
		LogSingleton.getLogger().trace("Get request made with path " + url);
		if (url.startsWith("/static/")) {
			super.doGet(request, response);
		} else {
			if (url.startsWith("/Login")) {

				LogSingleton.getLogger().trace("Request successfylly forwarded to Login.html");
				request.getRequestDispatcher("/static/Login.html").forward(request, response);
			}
			if (url.startsWith("/EmployeeHome")) {

				LogSingleton.getLogger().trace("Request successfylly forwarded to EmployeeHome.html");
				ehc.doGet(request, response);
			}
			if (url.startsWith("/Profile")) {

				LogSingleton.getLogger().trace("Request successfylly forwarded to EmployeeProfile.html");
				epc.doPost(request, response);
			}
			if (url.startsWith("/SubmitRequest")) {

				LogSingleton.getLogger().trace("Request successfylly forwarded to EmployeeSubmitRequest.html");
				src.doPost(request, response);
			}
			if (url.startsWith("/PendingRequests")) {

				LogSingleton.getLogger().trace("Request successfylly forwarded to EmployeeSubmitRequest.html");
				prc.doPost(request, response);
			}
			if (url.startsWith("/PastTickets")) {

				LogSingleton.getLogger().trace("Request successfylly forwarded to EmployeeSubmitRequest.html");
				ptc.doPost(request, response);
			}

		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String url = request.getPathInfo();
		LogSingleton.getLogger().trace("Post request made with path " + url);
		if (url.startsWith("/Login")) {
			lc.doPost(request, response);
		}
	}
}
