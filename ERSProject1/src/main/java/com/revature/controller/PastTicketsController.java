package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.PastTicketsService;

/*
 * /login searches Database for matching Login information. If the Login
 * has entered the correct credentials a login message is returned otherwise
 * an incorrect credentials message is returned.
 * 
 */

public class PastTicketsController implements HttpController {

	/*******************************************************************************
	 * Login Controller Fields
	 ********************************************************************************/

	private PastTicketsService pts = new PastTicketsService();
	private Logger log = Logger.getRootLogger();

	/*******************************************************************************
	 * HTTP Request Interception Methods
	 ********************************************************************************/

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher("/static/PastTickets.html").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		log.trace("Employee Past Tickets req stream initialized");
		ObjectMapper mapper = new ObjectMapper();
		HttpSession session = req.getSession();
		String r = mapper.writeValueAsString(pts.pastTickets((int) session.getAttribute("id")));
		resp.setContentType("application/json");
		resp.getWriter().println(r);
	}

}
