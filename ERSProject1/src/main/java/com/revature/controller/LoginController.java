package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.LoginService;

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
	private Logger log = Logger.getRootLogger();

	/*******************************************************************************
	 * HTTP Request Interception Methods
	 ********************************************************************************/

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.trace("Request successfylly forwarded to Login.html");
		req.getRequestDispatcher("/static/Login.html").forward(req, resp);

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		log.trace("Login req stream initialized");
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		String line = br.readLine();
		while (line != null) {
			json += line;
			line = br.readLine();
		}
		List<String> credentials = mapper.readValue(json,
				mapper.getTypeFactory().constructCollectionType(List.class, String.class));
		log.trace("done converting user's login credentials into arrayList");
		List<Integer> idAndRole = ls.login(credentials.get(0), credentials.get(1));
		String r;
		if (idAndRole == null) {
			r = mapper.writeValueAsString("");
		} else {
			HttpSession session = req.getSession();
			session.setAttribute("id", idAndRole.get(0));
			session.setAttribute("role_id", idAndRole.get(1));
			r = mapper.writeValueAsString(idAndRole.get(1));
		}
		resp.setContentType("application/json");
		resp.getWriter().println(r);

	}

}
