package com.revature.frontcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlets.DefaultServlet;

import com.revature.controller.AllPastTicketsController;
import com.revature.controller.EmployeeHomeController;
import com.revature.controller.PastTicketsController;
import com.revature.controller.ProfileController;
import com.revature.controller.EmployeeSubmitRequestController;
import com.revature.controller.LoginController;
import com.revature.controller.ManagerHomeController;
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
	ProfileController pc = new ProfileController();
	EmployeeSubmitRequestController src = new EmployeeSubmitRequestController();
	PastTicketsController ptc = new PastTicketsController();
	ManagerHomeController mhc = new ManagerHomeController();
	AllPastTicketsController aptc = new AllPastTicketsController();

	/*******************************************************************************
	 * DispatcherServlet Methods
	 ********************************************************************************/

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		String url = request.getPathInfo();
		LogSingleton.getLogger().trace("Get request made with path " + url);
		if (url.startsWith("/static/")) {
			super.doGet(request, response);
		} else {
			if (url.startsWith("/Login")) {

				LogSingleton.getLogger().trace("Request successfylly forwarded to Login.html");
				request.getRequestDispatcher("/static/Login.html").forward(request, response);
			}

			// check that a current session exists
			if (session.getAttribute("id") != null) {

				if (url.startsWith("/EmployeeHome")) {

					LogSingleton.getLogger().trace("Request successfylly forwarded to EmployeeHome.html");
					ehc.doGet(request, response);

				}
				if (url.startsWith("/ManagerHome")) {

					LogSingleton.getLogger().trace("Request successfylly forwarded to ManagerHome.html");
					mhc.doGet(request, response);

				}
				if (url.startsWith("/EmployeeProfile") || url.startsWith("/ManagerProfile")) {

					LogSingleton.getLogger().trace("Request successfylly forwarded to User's profile");
					pc.doGet(request, response);
				}
				if (url.startsWith("/SubmitRequest")) {

					LogSingleton.getLogger().trace("Request successfylly forwarded to EmployeeSubmitRequest.html");
					src.doGet(request, response);
				}

				if (url.startsWith("/PastTickets")) {

					LogSingleton.getLogger().trace("Request successfylly forwarded to PastTickets.html");
					ptc.doGet(request, response);
				}

				if (url.startsWith("/AllPastTickets")) {

					LogSingleton.getLogger().trace("Request successfylly forwarded to AllPastTickets.html");
					aptc.doGet(request, response);
				}
				if (url.startsWith("/Logout")) {

					LogSingleton.getLogger().trace("Request successfylly forwarded to Login.html");
					request.getRequestDispatcher("/static/Login.html").forward(request, response);
					session.invalidate();

				}

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
		if (url.startsWith("/EmployeeProfile") || url.startsWith("/ManagerProfile")) {
			pc.doPost(request, response);
		}
		if (url.startsWith("/SubmitRequest")) {
			src.doPost(request, response);
		}
		if (url.startsWith("/PastTickets")) {
			ptc.doPost(request, response);
		}
		if (url.startsWith("/AllPastTickets")) {
			aptc.doPost(request, response);
		}
	}
}
