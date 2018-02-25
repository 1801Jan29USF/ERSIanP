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
	public List<String> allPastTickets() {
		return LoginService.getUserDao().allPastTickets();

	}

	public void updateTickets(int status, int ticket_id, int id) {
		AllPastTicketsService.getUserDao().updateTickets(status, ticket_id, id);

	}
}
