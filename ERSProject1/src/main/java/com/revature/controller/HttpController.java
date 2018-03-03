package com.revature.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 /*
  * Http servlet interface with GET and POST methods
  * 
  */

public interface HttpController {
	void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;

	void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;

}
