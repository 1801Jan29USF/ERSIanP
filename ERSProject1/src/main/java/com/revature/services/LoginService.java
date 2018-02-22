package com.revature.services;

import java.util.List;

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

	// for Singleton
	public static UserDAOClass getUserDao() {
		return dao;
	}

	// will return a user matching credentials from the database
	// otherwise will return null
	public List<String> login(String username, String password) {
		return LoginService.getUserDao().login(username, password);

	}
}
