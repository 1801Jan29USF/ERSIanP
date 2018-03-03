package com.revature.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.ProfileService;
/*
 * Profile Controller used for receiving Profile Get and Post Requests 
 * and writing profile database information to body of Post Response
 */
public class ProfileController implements HttpController {

	/*******************************************************************************
	 * Login Controller Fields
	 ********************************************************************************/

	private ProfileService ps = new ProfileService();
	private Logger log = Logger.getRootLogger();

	/*******************************************************************************
	 * HTTP Request Interception Methods
	 ********************************************************************************/

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String url = req.getPathInfo();
		if (url.startsWith("/EmployeeProfile")) {
			req.getRequestDispatcher("/static/EmployeeProfile.html").forward(req, resp);
		} else {
			req.getRequestDispatcher("/static/ManagerProfile.html").forward(req, resp);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();
		log.trace("Profile req stream initialized");
		ObjectMapper mapper = new ObjectMapper();
		String r = mapper.writeValueAsString(ps.profile((int) session.getAttribute("id")));
		resp.setContentType("application/json");
		resp.getWriter().println(r);
	}

}
