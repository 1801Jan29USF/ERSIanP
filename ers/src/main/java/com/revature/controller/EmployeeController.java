package com.revature.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.beans.ErsUser;
import com.revature.beans.Sword;
import com.revature.services.EmployeeService;
import com.revature.util.ResponseUtil;

/**
 * /swords gives all swords /swords/id/{id} give the sword with that id
 * /swords/name/{name} give the swords with that name /swords/killcount/{count}
 * 
 * @author USER
 *
 */
public class EmployeeController implements HttpController {

	private static final String BY_ID_URL = "/employee/id/";
	private EmployeeService es = new EmployeeService();
	private ResponseUtil ru = new ResponseUtil();
	private Logger log = Logger.getRootLogger();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String url = req.getPathInfo();
		log.trace("get request delegated to employee controller");
		if (url.equals("/employee")) {

			List<ErsUser> users = es.findAll();

			ru.writeObjectToResponse(users, resp);
		} else if (url.startsWith(BY_ID_URL)) {
			int id = Integer.parseInt(url.substring(BY_ID_URL.length()));
			Sword sw = ss.findById(id);
			ru.writeObjectToResponse(sw, resp);
		}

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

}
