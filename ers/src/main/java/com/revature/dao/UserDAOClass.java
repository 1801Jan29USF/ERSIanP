package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.beans.ErsUser;
import com.revature.util.ConnectionUtil;
import com.revature.util.LogSingleton;

/*
 * Implementation of the Account DAO containing methods for
 * creating and deleting Accounts
 */

public class UserDAOClass implements UserDAO {

	/*******************************************************************************
	 * Class Fields
	 ********************************************************************************/

	private ConnectionUtil connUtil = ConnectionUtil.getConnectionUtil();

	/*******************************************************************************
	 * Log Getter
	 ********************************************************************************/

	@Override
	public ErsUser login(String u, String p) {

		String success = "";

		LogSingleton.getLogger().trace("method called to create login");
		LogSingleton.getLogger().trace("Attempting to get connection to database");
		try (Connection conn = connUtil.getConnection()) {

			LogSingleton.getLogger().trace("connection established with db, creating prepared statement to login");
			PreparedStatement ps = conn
					.prepareStatement("SELECT *FROM ers_users WHERE ers_username = ? AND ers_password = ?");
			ps.setString(1, u);
			ps.setString(2, p);
			LogSingleton.getLogger().trace("login prepared statement executed");
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				LogSingleton.getLogger().trace("user's credentials found. ");
				
				//create new User
				 new ErsUser(rs.getInt("ers_users_id"), rs.getString("ers_username"),
						rs.getString("ers_password"), rs.getString("user_first_name"), rs.getString("user_last_name"),
						rs.getString("user_email"), rs.getInt("user_role_id"));
				 return rs.getInt("ers_users_id");

			}

		} catch (SQLException e) {
			LogSingleton.getLogger().warn("failed to establish connection with database during login");

		}
		return success;

	}

	//
	// @Override
	// public void deleteAccount(int id, String acctName) {
	// log.trace("method called to delete an account");
	// log.trace("Attempting to get connection to db");
	// try (Connection conn = connUtil.getConnection()) {
	// log.trace("connection established with db, creating prepared statement to
	// save account");
	// PreparedStatement ps = conn.prepareStatement("DELETE FROM accounts WHERE
	// account_name = ?");
	// ps.setString(1, acctName);
	// ps.executeUpdate();
	// ps = conn.prepareStatement("INSERT INTO transactions (user_id, trans) VALUES
	// (?,?)");
	// ps.setInt(1, id);
	// String t = "Deletion of " + acctName + " account created by user with id: " +
	// id;
	// ps.setString(2, t);
	// ps.executeUpdate();
	// } catch (SQLException e) {
	// log.warn("failed to insert new account");
	//
	// }
	// }
}
