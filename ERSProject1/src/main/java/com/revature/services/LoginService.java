package com.revature.services;

import com.revature.dao.UserDAOClass;

/*
 * Service layer providing logic to operate on the data
 * sent to and from the DAO and the client.
 * 
 */

public class LoginService {

	/*******************************************************************************
	 * User Service Fields
	 ********************************************************************************/
	private static UserDAOClass dao = new UserDAOClass();

	/*******************************************************************************
	 * User Service Methods
	 ********************************************************************************/

	public static UserDAOClass getUserDao() {
		return dao;
	}

	public void login(String str) {
		// parse str for username and password
		
		String username = "";
		String password = "";
		LoginService.getUserDao().login(username, password);

	}
}
