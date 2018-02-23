package com.revature.services;

import java.util.List;

import com.revature.dao.UserDAOClass;

public class AllPastTicketsService {

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
	public List<String> allPastTickets(int id) {
		return LoginService.getUserDao().allPastTickets(id);

	}
}
