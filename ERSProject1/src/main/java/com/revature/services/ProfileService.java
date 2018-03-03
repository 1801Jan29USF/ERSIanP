package com.revature.services;

import java.util.List;

import com.revature.dao.UserDAOClass;

/*
 * Service layer providing logic to operate on the data
 * sent to and from the DAO and the client.
 * 
 */

public class ProfileService {

	/*******************************************************************************
	 * Profile Service Fields
	 ********************************************************************************/
	private static UserDAOClass dao = new UserDAOClass();

	/*******************************************************************************
	 * Profile Service Methods
	 ********************************************************************************/

	// will return a user matching credentials from the database
	// otherwise will return null
	public List<String> profile(int id) {
		return dao.profile(id);

	}
}
