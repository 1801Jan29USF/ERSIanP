package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.LoginService;
import com.revature.util.LogSingleton;

/*
 * /login searches Database for matching Login information. If the Login
 * has entered the correct credentials a login message is returned otherwise
 * an incorrect credentials message is returned.
 * 
 */

public class LoginController implements HttpController {

	/*******************************************************************************
	 * Login Controller Fields
	 ********************************************************************************/

	private LoginService ls = new LoginService();

	/*******************************************************************************
	 * HTTP Request Interception Methods
	 ********************************************************************************/

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		LogSingleton.getLogger().trace("Login req stream initialized");
		// initialize stream
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		// mapper used for mapping json objects into credentials ArrayList
		ObjectMapper mapper = new ObjectMapper();

		String json = "";
		String line = br.readLine();
		// read from stream into string
		while (line != null) {
			json += line;
			line = br.readLine();
		}
		List<String> credentials = mapper.readValue(json,
				mapper.getTypeFactory().constructCollectionType(List.class, String.class));
		LogSingleton.getLogger().trace("done converting user's login credentials into arrayList");
		// write username and password to session
		HttpSession session = req.getSession();
		session.setAttribute("username", credentials.get(0));
		session.setAttribute("password", credentials.get(1));


		// send password and username to Login Service layer
		// convert user's role to JSON
		String r = mapper.writeValueAsString(ls.login(credentials.get(0), credentials.get(1)));
		System.out.println(r);

		// actually write the json to the body of the request
		resp.setContentType("application/json");
		resp.getWriter().println(r);
	}

}
