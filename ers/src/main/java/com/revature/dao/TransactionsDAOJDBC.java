package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;

import com.revature.beans.User;
import com.revature.util.ConnectionUtil;

public class TransactionsDAOJDBC implements TransactionsDAO {
	
	private Logger log = Logger.getRootLogger();
	private ConnectionUtil connUtil = ConnectionUtil.getConnectionUtil();

	@Override
	public int save(User u) {

		log.trace("method called to insert new user");
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			log.trace("connection established with db, creating prepared statement to save user");
			PreparedStatement ps = conn.prepareStatement("INSERT INTO bank_users (username, pass) VALUES (?,?)",
					new String[] { "user_id" });
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());

			int rowsInserted = ps.executeUpdate();
			log.trace("query inserted " + rowsInserted + " rows into the bank_users");
			ResultSet rs = ps.getGeneratedKeys();

			log.trace("updating users id");
			// set user's id from the string returned by prepare statement
			if (rs.next()) {
				u.setId(rs.getInt(1));
				return rs.getInt(1);
			}

		} catch (SQLException e) {
			log.warn("failed to insert new user");
		}
		return 0;
	}

	@Override
	public User login(String u, String p) {
		log.trace("method called to check for login credentials and log user in");
		log.trace("Attempting to get connection to db");

		try (Connection conn = connUtil.getConnection()) {

			log.trace("connection established with db, creating prepared statement to access login credentials");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM bank_users WHERE username = ?");
			ps.setString(1, u);

			log.trace("obtaining result set of user matching input credentials");
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				log.trace("user's credentials found. initializing credentials for current user");

				User user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("pass"));
				return user;
			} else {
				log.trace("No User found with Username: " + u + " and Password: " + p);
			}

		} catch (SQLException e) {
			log.warn("failed to retrieve user from database");
		}
		return null;
	}

	@Override
	public User register(String u, String p) {

		log.trace("method called to check for uniqueness of registration credentials");
		log.trace("Attempting to get connection to db");

		try (Connection conn = connUtil.getConnection()) {

			log.trace("connection established with db, creating prepared statement to register user");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM bank_users WHERE username = ?");
			ps.setString(1, u);
			ResultSet rs = ps.executeQuery();

			log.trace("username is unique, creating user");
			if (rs.next() == false) {
				User user = new User(0, u, p);
				save(user);
				return user;

			} else {
				log.trace("User credentials are not uniqe.");
			}

		} catch (SQLException e) {
			log.warn("failed to retrieve user");
		}
		return null;
	}

	@Override
	public void deposit(int id, int amt, String acctName) {

		log.trace("method called to deposit money into account");
		log.trace("Attempting to get connection to db");

		try (Connection conn = connUtil.getConnection()) {

			log.trace("connection established with db, creating prepared statement to deposit money for user");

			PreparedStatement ps = conn
					.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_name = ?");
			ps.setInt(1, amt);
			ps.setString(2, acctName);
			if (ps.executeUpdate() == 0) {

				log.warn("No update occured most likely because the user entered an invalid account name");

				System.out.println("Account name does not exist. \n");
				return;
			}

			PreparedStatement ps2 = conn.prepareStatement("INSERT INTO transactions (user_id, trans) VALUES (?,?)");
			ps2.setInt(1, id);
			String t = "Deposited " + amt + "$ into " + acctName + "------" + LocalDateTime.now();
			ps2.setString(2, t);

			log.trace("adding deposit transaction to database");
			ps2.executeUpdate();

		} catch (SQLException e) {
			log.warn("failed to retrieve user");
		}
	}

	@Override
	public void withdraw(int id, int amt, String acctName) {

		log.trace("method called to withdraw money from account");
		log.trace("Attempting to get connection to db");

		try (Connection conn = connUtil.getConnection()) {

			log.trace("connection established with db, creating prepared statement to save user");
			PreparedStatement ps = conn
					.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_name = ?");
			ps.setInt(1, amt);
			ps.setString(2, acctName);
			if (ps.executeUpdate() == 0) {

				log.warn("No update occured most likely because the user entered an invalid account name");

				System.out.println("Account name does not exist. \n");
				return;
			}

			log.trace("creating prepare statement for transaction");
			PreparedStatement ps2 = conn.prepareStatement("INSERT INTO transactions (user_id, trans) VALUES (?,?)");
			ps2.setInt(1, id);
			String t = "Withdrew " + amt + "$ from " + acctName + "------" + LocalDateTime.now();
			ps2.setString(2, t);

			log.trace("adding deposit transaction to database");
			ps2.executeUpdate();

		} catch (SQLException e) {
			System.out.println(
					"The amount you entered will cause an overdraft to occur on your account. Please enter another number.\n");

			log.warn("amount that user entered would cause an overdraft of his/her account");
		}
	}

	@Override
	public int balance(int id, String acctName) {

		log.trace("method called to view account balance");
		log.trace("Attempting to get connection to db");

		int balance = 0;
		try (Connection conn = connUtil.getConnection()) {

			log.trace("connection established with db, creating prepared statement to save user");
			PreparedStatement ps = conn.prepareStatement("SELECT balance FROM accounts WHERE account_name = ?");
			ps.setString(1, acctName);
			ResultSet rs = ps.executeQuery();
			log.trace("selecting balance from result set");
			if (rs.next()) {
				balance = rs.getInt("balance");
			} else {
				System.out.println("The account name that you have chosen already exists. "
						+ "Please select a different name and try again.\n");
			}
		} catch (SQLException e) {
			log.warn("failed to retrieve user");
		}
		return balance;

	}

	@Override
	public void transfer(int id, int amt, String acctFrom, String acctTo) {

		log.trace("method called to transfer money");
		log.trace("Attempting to get connection to db");

		try (Connection conn = connUtil.getConnection()) {

			log.trace("connection established with db, creating prepared statement to save user");
			PreparedStatement ps = conn
					.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_name = ?");
			ps.setInt(1, amt);
			ps.setString(2, acctFrom);
			if (ps.executeUpdate() == 0) {

				log.trace("account to transfer from does not exist");
				System.out.println("The account you are trying to transfer from does not exist.\n");
			}
			PreparedStatement ps2 = conn
					.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_name = ?");
			ps2.setInt(1, amt);
			ps2.setString(2, acctTo);
			if (ps2.executeUpdate() == 0) {

				log.trace("the entered account to transfer to does not exist");
				System.out.println("The account you are trying to transfer to does not exist.\n");
				return;
			}
			log.trace("preparing to insert transaction");
			
			PreparedStatement ps3 = conn.prepareStatement("INSERT INTO transactions (user_id, trans) VALUES (?,?)");
			ps3.setInt(1, id);
			String t = "Transferred " + amt + "$ from " + acctFrom + "to " + acctTo + "------" + LocalDateTime.now();
			ps3.setString(2, t);
			log.trace("transaction inserted");
			ps3.executeUpdate();

		} catch (SQLException e) {
			log.warn("failed to retrieve user");
		}
	}

	@Override
	public void quickPay(String acct, String receiver, int id, int amount) {
		
		log.trace("method called to quickPay money");
		log.trace("Attempting to get connection to db");
		
		try (Connection conn = connUtil.getConnection()) {
			log.trace("connection established with db, creating prepared statement for quickpay");

			PreparedStatement ps = conn
					.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_name = ?");
			ps.setInt(1, amount);
			ps.setString(2, acct);
			if (ps.executeUpdate() == 0) {
				
				log.trace("entered account for quickpay sender doesn not exist");
				System.out.println("The account that you have chosen to QuickPay from does not exist.\n");
				return;
			}
			
			log.trace("creating prepare statement to quickpay into receiver's account");
			ps = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_name = ?");
			ps.setInt(1, amount);
			ps.setString(2, receiver);
			if (ps.executeUpdate() == 0) {
				
				log.trace("entered account for quickpay receiver does not exist");
				System.out.println("The account that you have chosen to QuickPay to does not exist. ");
				return;
			}
			
			log.trace("preparing to insert transaction into database");
			PreparedStatement ps2 = conn.prepareStatement("INSERT INTO transactions (user_id, trans) VALUES (?,?)");
			ps2.setInt(1, id);
			String t = "Quickpayed " + amount + "$ to " + receiver + "------" + LocalDateTime.now();
			ps2.setString(2, t);
			ps2.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			
			log.trace("overdrafting has occured during quickpay");			
			System.out.println(
					"The amount you entered will cause you to overdraft you account. Please enter a different amount.\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean updateUserPass(int id, String u, String p) {
		boolean added = true;
		
		log.trace("method called to update username and password");
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			
			log.trace("connection established with db, creating prepared statement to save user");

			PreparedStatement ps = conn.prepareStatement("SELECT *FROM bank_users WHERE username = ?");
			ps.setString(1, u);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				added = false;

			} else {
				
				log.trace("updating users account information");
				PreparedStatement ps2 = conn
						.prepareStatement("UPDATE bank_users SET username = ? , pass = ? WHERE user_id = " + id);
				ps2.setString(1, u);
				ps2.setString(2, p);
				
				ps2.executeUpdate();
				log.trace("users account information has been added");
			}

		} catch (SQLException e) {
			
			log.trace("unable to retreive users information from database");
			e.printStackTrace();
		}
		return added;
	}

	@Override
	public void printTrans(int id) {
		
		log.trace("method called to print user transactions");
		log.trace("Attempting to get connection to db");
		
		try (Connection conn = connUtil.getConnection()) {
			log.trace("connection established with db, creating prepared statement to print user's transaction history");

			PreparedStatement ps = conn.prepareStatement("SELECT *FROM transactions WHERE user_id = " + id);
			ResultSet rs = ps.executeQuery();
			System.out.println("YOUR TRANSACTION HISTORY:\n");
			while (rs.next()) {
				System.out.println(rs.getString("trans") + "\n");
			}
		} catch (SQLException e) {
			
			log.trace("unable to retreive user's credentials from database");
			e.printStackTrace();
		}

	}

	@Override
	public void deleteUserProfile(int id) {
		
		log.trace("method called to delete user");
		log.trace("Attempting to get connection to db");
		
		try (Connection conn = connUtil.getConnection()) {
			
			log.trace("connection established with db, creating prepared statement to delete user's profile");
			PreparedStatement ps = conn.prepareStatement("INSERT INTO transactions (user_id, trans) VALUES (?,?)");
			ps.setInt(1, id);
			String t = "Deletion of user profile with id: " + id + "------" + LocalDateTime.now();
			ps.setString(2, t);
			ps.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("DELETE FROM bank_users WHERE user_id = ?");
			ps2.setInt(1, id);
			ps2.executeUpdate();
			log.trace("users account has been deleted");
			return;
		} catch (SQLException e) {
			log.warn("failed to delete profile");
		}

	}


}
