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
		boolean valid = false;

		LogSingleton.getLogger().trace("Get request made with path " + url);
		if (url.startsWith("/static/")) {
			valid = true;
			super.doGet(request, response);
		} else {

			if (url.startsWith("/Login")) {
				System.out.println(session.getAttribute("id"));
				if (session.getAttribute("id") != null) {
					session.invalidate();
				}
				valid = true;
				if (!url.equals("/Login")) {
					response.sendRedirect("/ERSProject1/Login");
					return;
				}
				lc.doGet(request, response);
				return;
			}

			// check that a current session exists
			if (session.getAttribute("id") != null) {
				if (session.getAttribute("role_id").equals(0)) {
					if (url.startsWith("/EmployeeProfile")) {
						valid = true;
						if (!url.equals("/EmployeeProfile")) {
							response.sendRedirect("/ERSProject1/EmployeeProfile");
							return;
						}

						LogSingleton.getLogger().trace("Request successfylly forwarded to User's profile");
						pc.doGet(request, response);
					}
					if (url.startsWith("/SubmitRequest")) {
						valid = true;
						if (!url.equals("/SubmitRequest")) {
							response.sendRedirect("/ERSProject1/SubmitRequest");
							return;
						}

						LogSingleton.getLogger().trace("Request successfylly forwarded to EmployeeSubmitRequest.html");
						src.doGet(request, response);
					}

					if (url.startsWith("/PastTickets")) {
						valid = true;
						if (!url.equals("/PastTickets")) {
							response.sendRedirect("/ERSProject1/PastTickets");
							return;
						}

						LogSingleton.getLogger().trace("Request successfylly forwarded to PastTickets.html");
						ptc.doGet(request, response);
					}
				}
				if (session.getAttribute("role_id").equals(1)) {
					if (url.startsWith("/ManagerProfile")) {
						valid = true;
						if (!url.equals("/ManagerProfile")) {
							response.sendRedirect("/ERSProject1/ManagerProfile");
							return;
						}

						LogSingleton.getLogger().trace("Request successfylly forwarded to User's profile");
						pc.doGet(request, response);
					}

					if (url.startsWith("/AllPastTickets")) {
						valid = true;
						if (!url.equals("/AllPastTickets")) {
							response.sendRedirect("/ERSProject1/AllPastTickets");
							return;
						}

						LogSingleton.getLogger().trace("Request successfylly forwarded to AllPastTickets.html");
						aptc.doGet(request, response);
					}
				}
				if (url.startsWith("/Logout")) {
					valid = true;
					if (!url.equals("/Logout")) {
						response.sendRedirect("/ERSProject1/Login");
						return;
					}

					LogSingleton.getLogger().trace("Request successfylly forwarded to Login.html");
					response.sendRedirect("/ERSProject1/Login");

				}

			}

			if (valid == false) {
				session = null;
				response.sendRedirect("/ERSProject1/Login");
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