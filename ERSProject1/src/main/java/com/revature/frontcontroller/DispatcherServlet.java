package com.revature.frontcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;

import com.revature.controller.AllPastTicketsController;
import com.revature.controller.LoginController;
import com.revature.controller.PastTicketsController;
import com.revature.controller.ProfileController;
import com.revature.controller.RequestController;

/*
 * Dispatcher Servlet class receives all requests and delegates the request to the specific controller
 * based on the url.
 * 
 */
public class DispatcherServlet extends DefaultServlet {

	/*******************************************************************************
	 * Dispatcher Servlet Controller Fields
	 ********************************************************************************/

	LoginController lc = new LoginController();
	ProfileController pc = new ProfileController();
	RequestController rc = new RequestController();
	PastTicketsController ptc = new PastTicketsController();
	AllPastTicketsController aptc = new AllPastTicketsController();
	private Logger log = Logger.getRootLogger();

	/*******************************************************************************
	 * DispatcherServlet Methods
	 ********************************************************************************/

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		String url = request.getPathInfo();
		
		boolean valid = false;

		log.trace("Get request made with path " + url);
		if (url.startsWith("/static/")) {
			valid = true;
			super.doGet(request, response);
		} else {

			if (url.startsWith("/Login")) {
				
				//if a user logs in and then presses back button to go
				//back to log in screen, invalidate their session
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
				//only allow employees to have access to these urls
				if (session.getAttribute("role_id").equals(0)) {
					if (url.startsWith("/EmployeeProfile")) {
						valid = true;
						if (!url.equals("/EmployeeProfile")) {
							response.sendRedirect("/ERSProject1/EmployeeProfile");
							return;
						}

						log.trace("Request successfully forwarded to User's profile");
						pc.doGet(request, response);
					}
					if (url.startsWith("/SubmitRequest")) {
						valid = true;
						if (!url.equals("/SubmitRequest")) {
							response.sendRedirect("/ERSProject1/SubmitRequest");
							return;
						}

						log.trace("Request successfully forwarded to EmployeeSubmitRequest.html");
						rc.doGet(request, response);
					}

					if (url.startsWith("/PastTickets")) {
						valid = true;
						if (!url.equals("/PastTickets")) {
							response.sendRedirect("/ERSProject1/PastTickets");
							return;
						}

						log.trace("Request successfully forwarded to PastTickets.html");
						ptc.doGet(request, response);
					}
				}
				//only allow managers to have access to these urls
				if (session.getAttribute("role_id").equals(1)) {
					if (url.startsWith("/ManagerProfile")) {
						valid = true;
						if (!url.equals("/ManagerProfile")) {
							response.sendRedirect("/ERSProject1/ManagerProfile");
							return;
						}

						log.trace("Request successfully forwarded to User's profile");
						pc.doGet(request, response);
					}

					if (url.startsWith("/AllPastTickets")) {
						valid = true;
						if (!url.equals("/AllPastTickets")) {
							response.sendRedirect("/ERSProject1/AllPastTickets");
							return;
						}

						log.trace("Request successfully forwarded to AllPastTickets.html");
						aptc.doGet(request, response);
					}
				}
				if (url.startsWith("/Logout")) {
					valid = true;
					if (!url.equals("/Logout")) {
						response.sendRedirect("/ERSProject1/Login");
						return;
					}

					log.trace("Request successfully forwarded to Login.html");
					response.sendRedirect("/ERSProject1/Login");

				}

			}
			//if user goes to /ERSProject/()
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
		log.trace("Post request made with path " + url);
		if (url.startsWith("/Login")) {
			lc.doPost(request, response);
		}
		if (url.startsWith("/EmployeeProfile") || url.startsWith("/ManagerProfile")) {
			pc.doPost(request, response);
		}
		if (url.startsWith("/SubmitRequest")) {
			rc.doPost(request, response);
		}
		if (url.startsWith("/PastTickets")) {
			ptc.doPost(request, response);
		}
		if (url.startsWith("/AllPastTickets")) {
			aptc.doPost(request, response);
		}
	}
}