package com.revature.services;

import java.util.List;

import com.revature.dao.UserDAOClass;

/*
 * Calls the DAO layer to upload the request to the database
 * 
 */

public class EmployeeSubmitRequestService {

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
	public void submitRequest(String amount, String desc, int id) {
		EmployeeSubmitRequestService.getUserDao().submitRequest(amount, desc, id);

	}
}
