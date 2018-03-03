package com.revature.services;

import java.util.List;

import com.revature.dao.UserDAOClass;

/*
 * Controller used for receiving GET and POST requests for all of manager's 
 * tickets
 * 
 */
public class AllPastTicketsService {

	/*******************************************************************************
	 * User Service Fields
	 ********************************************************************************/
	private static UserDAOClass dao = new UserDAOClass();

	/*******************************************************************************
	 * User Service Methods
	 ********************************************************************************/

	// will return a user matching credentials from the database
	// otherwise will return null
	public List<String> allPastTickets() {
		return dao.allPastTickets();
	}

	public void updateTickets(int status, int ticket_id, int id) {
		dao.updateTickets(status, ticket_id, id);

	}
}
