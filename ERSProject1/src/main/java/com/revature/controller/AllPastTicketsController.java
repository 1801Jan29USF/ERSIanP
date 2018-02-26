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
import com.revature.services.AllPastTicketsService;
import com.revature.util.LogSingleton;

/*
 * /login searches Database for matching Login information. If the Login
 * has entered the correct credentials a login message is returned otherwise
 * an incorrect credentials message is returned.
 * 
 */

public class AllPastTicketsController implements HttpController {

	/*******************************************************************************
	 * Login Controller Fields
	 ********************************************************************************/

	private AllPastTicketsService apts = new AllPastTicketsService();

	/*******************************************************************************
	 * HTTP Request Interception Methods
	 ********************************************************************************/

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher("/static/AllPastTickets.html").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		ObjectMapper mapper = new ObjectMapper();
		HttpSession session = req.getSession();

		String json = "";
		String line = br.readLine();
		// read from stream into string
		while (line != null) {
			json += line;
			line = br.readLine();
		}
		if (!json.equals("")) {
			List<String> info = mapper.readValue(json,
					mapper.getTypeFactory().constructCollectionType(List.class, String.class));
			apts.updateTickets(Integer.parseInt(info.get(1)), Integer.parseInt(info.get(0)),
					(int) session.getAttribute("id"));
		} else {
			LogSingleton.getLogger().trace("All Past Tickets req stream initialized");
			String r = mapper.writeValueAsString(apts.allPastTickets());
			System.out.println(r);

			// actually write the json to the body of the request
			resp.setContentType("application/json");
			resp.getWriter().println(r);
		}

	}

}
