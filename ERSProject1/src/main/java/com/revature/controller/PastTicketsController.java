package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.LoginService;
import com.revature.util.LogSingleton;
import com.revature.util.ResponseUtil;

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

	/*******************************************************************************
	 * HTTP Request Interception Methods
	 ********************************************************************************/

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		req.getRequestDispatcher("/static/EmployeeProfile.html").forward(req, resp);
		LogSingleton.getLogger().trace("Profile req stream initialized");
		String url = req.getPathInfo();
		//get the user's id from the url
		int id = Integer.parseInt(url);
		ObjectMapper mapper = new ObjectMapper();
		//write user's profile information to response
		String r = mapper.writeValueAsString(pts.pastTickets(id));

		// actually write the json to the body of the request
		resp.setContentType("application/json");
		resp.getWriter().println(r);
	}

}
