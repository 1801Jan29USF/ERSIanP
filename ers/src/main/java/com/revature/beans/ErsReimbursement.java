package com.revature.beans;

import java.time.LocalDateTime;

/* 
 * ERS Reimbursement class containing Reinbursement information 
 * associated with a specific Reinbursement
 */

public class ErsReimbursement {

	/*******************************************************************************
	 * Class Fields
	 ********************************************************************************/

	private int id;
	private int amount;
	private LocalDateTime submitted;
	private LocalDateTime resolved;
	private String description;
	private int author;
	private int resolver;
	private int status_id;
	private int type_id;

	/*******************************************************************************
	 * Class Constructor
	 ********************************************************************************/

	public ErsReimbursement(int id, int amount, LocalDateTime submitted, LocalDateTime resolved, String description,
			int author, int resolver, int status_id, int type_id) {
		super();
		this.id = id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status_id = status_id;
		this.type_id = type_id;
	}

	/*******************************************************************************
	 * Getters And Setters
	 ********************************************************************************/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public LocalDateTime getSubmitted() {
		return submitted;
	}

	public void setSubmitted(LocalDateTime submitted) {
		this.submitted = submitted;
	}

	public LocalDateTime getResolved() {
		return resolved;
	}

	public void setResolved(LocalDateTime resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public int getResolver() {
		return resolver;
	}

	public void setResolver(int resolver) {
		this.resolver = resolver;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
}