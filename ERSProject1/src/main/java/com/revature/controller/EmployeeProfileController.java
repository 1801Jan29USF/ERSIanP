package com.revature.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.ProfileService;
import com.revature.util.LogSingleton;

public class EmployeeProfileController implements HttpController {

	/*******************************************************************************
	 * Login Controller Fields
	 ********************************************************************************/

	private ProfileService ps = new ProfileService();

	/*******************************************************************************
	 * HTTP Request Interception Methods
	 ********************************************************************************/

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
		String r = mapper.writeValueAsString(ps.profile(id));

		// actually write the json to the body of the request
		resp.setContentType("application/json");
		resp.getWriter().println(r);
	}

}
