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
import com.revature.services.EmployeeSubmitRequestService;
import com.revature.util.LogSingleton;

/*
 *    /SubmitRequest submits a user's expense reinbursement request to the database
 * 
 */

public class EmployeeSubmitRequestController implements HttpController {

	/*******************************************************************************
	 * Login Controller Fields
	 ********************************************************************************/

	private EmployeeSubmitRequestService srs = new EmployeeSubmitRequestService();

	/*******************************************************************************
	 * HTTP Request Interception Methods
	 ********************************************************************************/

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher("/static/EmployeeSubmitRequest.html").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();

		LogSingleton.getLogger().trace("SubmitRequest req stream initialized");
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
		List<String> data = mapper.readValue(json,
				mapper.getTypeFactory().constructCollectionType(List.class, String.class));
		LogSingleton.getLogger().trace("done converting user's reinbursement request into arrayList");
		System.out.println(data);

		// send password to Login Service layer
		// convert user's full credentials to json
		srs.submitRequest((int) session.getAttribute("id"), Integer.parseInt(data.get(0)), data.get(1), data.get(2));

	}

}