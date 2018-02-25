package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.PastTicketsService;
import com.revature.util.LogSingleton;

/*
 * /login searches Database for matching Login information. If the Login
 * has entered the correct credentials a login message is returned otherwise
 * an incorrect credentials message is returned.
 * 
 */

public class EmployeePastTicketsController implements HttpController {

	/*******************************************************************************
	 * Login Controller Fields
	 ********************************************************************************/

	private PastTicketsService pts = new PastTicketsService();

	/*******************************************************************************
	 * HTTP Request Interception Methods
	 ********************************************************************************/

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher("/static/EmployeePastTicketsAndPending.html").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		LogSingleton.getLogger().trace("Employee Past Tickets req stream initialized");
		ObjectMapper mapper = new ObjectMapper();
		HttpSession session = req.getSession();
		String r = mapper.writeValueAsString(pts.pastTickets((int) session.getAttribute("id")));
		System.out.println(r);

		// actually write the json to the body of the request
		resp.setContentType("application/json");
		resp.getWriter().println(r);
	}

}
