package com.revature.dao;

import com.revature.beans.User;

/*
 * Transaction DAO containing methods for transactions on user accounts
 
 */
public interface TransactionsDAO {

	int save(User u);

	User login(String input, String password);

	User register(String u, String p);

	void deposit(int id, int amt, String acctName);

	void withdraw(int id, int amt, String acctName);

	int balance(int id, String type);

	// allows a user to transfer money between his/her accounts
	void transfer(int id, int amt, String acctFrom, String acctTo);

	// allows a user to send money to another user's account
	void quickPay(String acct, String receiver, int id, int amount);

	// allows a user to update their current name and password
	boolean updateUserPass(int id, String username, String password);

	// allows a user to print his/her transaction history
	void printTrans(int id);

	// allows a user to delete his/her user profile
	void deleteUserProfile(int id);

}
