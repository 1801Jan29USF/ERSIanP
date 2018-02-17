package com.revature.beans;


/* 
 * ERS Users class containing Profile information and Login Credentials
 * associated with a specific User
 */

public class ErsUser {

	/*******************************************************************************
	 * Class Fields
	 ********************************************************************************/

	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int role_id;

	/*******************************************************************************
	 * Class Constructor
	 ********************************************************************************/

	public ErsUser(int id, String username, String password, String firstName, String lastName, String email,
			int role_id) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role_id = role_id;
	}

	/*******************************************************************************
	 * Getters and Setters
	 ********************************************************************************/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

}
