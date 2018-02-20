package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.services.LoginService;
import com.revature.util.LogSingleton;
import com.revature.util.ResponseUtil;

/*
 * /login searches Database for matching Login information. If the Login
 * has entered the correct credentials a login message is returned otherwise
 * an incorrect credentials message is returned.
 * 
 */

public class LoginController implements HttpController {

	/*******************************************************************************
	 * Class Fields
	 ********************************************************************************/

	private LoginService ls = new LoginService();
	private ResponseUtil ru = new ResponseUtil();

	/*******************************************************************************
	 * HTTP Request Interception Methods
	 ********************************************************************************/

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// parse url for correct path
		String url = req.getPathInfo();

		LogSingleton.getLogger().trace("post request delegated to login controller");

		final StringBuilder str = new StringBuilder();
		// read the body of HttpServletRequest
		try (BufferedReader reader = req.getReader()) {
			if (reader == null) {

				LogSingleton.getLogger().debug("Request body could not be read because it's empty.");
				// write an empty string to response
				ru.writeObjectToResponse("", resp);
			}
			String line = "";
			while ((line = reader.readLine()) != null) {
				// create string for response
				str.append(line);
			}
			// send string to login service class
			ls.login(str.toString());
		} catch (Exception e) {

			LogSingleton.getLogger().trace("Could not obtain the saml request body from the http request", e);
			ru.writeObjectToResponse("Could not obtain the saml request body from the http request", resp);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

}
