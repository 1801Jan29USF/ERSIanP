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
import com.revature.services.SubmitRequestService;

/*
 *  Parses the request body for expense request information and passes it to service layer.
 * 
 */

public class RequestController implements HttpController {

	/*******************************************************************************
	 * Login Controller Fields
	 ********************************************************************************/

	private SubmitRequestService srs = new SubmitRequestService();
	private Logger log = Logger.getRootLogger();

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
		log.trace("SubmitRequest req stream initialized");
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		String line = br.readLine();
		while (line != null) {
			json += line;
			line = br.readLine();
		}
		List<String> data = mapper.readValue(json,
				mapper.getTypeFactory().constructCollectionType(List.class, String.class));
		log.trace("done converting user's reinbursement request into arrayList");
		srs.submitRequest((int) session.getAttribute("id"), Integer.parseInt(data.get(0)), data.get(1), data.get(2));
	}
}