package com.revature.services;

import com.revature.dao.UserDAOClass;

/*
 * Calls the DAO layer to upload the request to the database
 * 
 */

public class SubmitRequestService {

	/*******************************************************************************
	 * User Service Fields
	 ********************************************************************************/
	private static UserDAOClass dao = new UserDAOClass();

	/*******************************************************************************
	 * User Service Methods
	 ********************************************************************************/

	// will return a user matching credentials from the database
	// otherwise will return null
	public void submitRequest(int id, int amount, String desc, String type) {
		// assume 0 represents type = OTHER
		int typeForDB = 0;
		if (type.equals("Lodging")) {
			typeForDB = 1;
		}
		if (type.equals("Travel")) {
			typeForDB = 2;
		}
		if (type.equals("Food")) {
			typeForDB = 3;
		}
		dao.submitRequest(id, amount, desc, typeForDB);
	}
}
