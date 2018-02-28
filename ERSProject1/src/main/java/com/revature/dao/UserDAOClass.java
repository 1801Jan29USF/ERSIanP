package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

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
	 * DAO Methods
	 ********************************************************************************/

	@Override
	public List<Integer> login(String u, String p) {

		LogSingleton.getLogger().trace("method called to create login");
		LogSingleton.getLogger().trace("attempting to get connection to database");
		try (Connection conn = connUtil.getConnection()) {

			LogSingleton.getLogger().trace("connection established with db, creating prepared statement to login");
			PreparedStatement ps = conn
					.prepareStatement("SELECT *FROM ers_users WHERE ers_username = ? AND ers_password = ?");
			ps.setString(1, u);
			ps.setString(2, p);
			LogSingleton.getLogger().trace("login prepared statement executed");
			ResultSet rs = ps.executeQuery();
			// user is found
			if (rs.next()) {

				LogSingleton.getLogger().trace("user's credentials found. ");
				// get user's role id
				List<Integer> arr = new ArrayList<Integer>();
				arr.add(rs.getInt("ers_users_id"));
				arr.add(rs.getInt("user_role_id"));
				return arr;

			}

		} catch (SQLException e) {
			LogSingleton.getLogger().warn("failed to establish connection with database during login");

		}
		// User with entered credentials doesn't exist
		LogSingleton.getLogger().trace("user's credentials not found.");

		return null;

	}

	@Override
	public List<String> profile(int id) {

		LogSingleton.getLogger().trace("method called to obtain profile information");
		LogSingleton.getLogger().trace("attempting to get connection to database");
		try (Connection conn = connUtil.getConnection()) {

			LogSingleton.getLogger().trace(
					"connection established with db, creating prepared statement to obtain user's profile information");
			PreparedStatement ps = conn.prepareStatement("SELECT *FROM ers_users WHERE ers_users_id = ?");
			ps.setInt(1, id);
			LogSingleton.getLogger().trace("profile prepared statement executed");
			ResultSet rs = ps.executeQuery();
			// user is found
			if (rs.next()) {

				LogSingleton.getLogger().trace("user's credentials found. ");
				// create new User
				List<String> arr = new ArrayList<String>();
				arr.add(rs.getString("ers_username"));
				arr.add(rs.getString("ers_password"));
				arr.add(rs.getString("user_first_name"));
				arr.add(rs.getString("user_last_name"));
				arr.add(rs.getString("user_email"));

				int role = rs.getInt("user_role_id");
				PreparedStatement ps2 = conn.prepareStatement("SELECT *FROM ers_user_roles WHERE ers_user_role_id = ?");
				ps2.setInt(1, role);
				ResultSet rs2 = ps2.executeQuery();

				if (rs2.next()) {
					arr.add(rs2.getString("user_role"));
					return arr;
				}
			}

		} catch (SQLException e) {
			LogSingleton.getLogger().warn("failed to establish connection with database during login");

		}
		// User with entered credentials doesn't exist
		LogSingleton.getLogger().trace("user's credentials not found.");
		return null;

	}

	@Override
	public void submitRequest(int id, int amount, String desc, int type) {
		LogSingleton.getLogger().trace("method called to obtain profile information");
		LogSingleton.getLogger().trace("attempting to get connection to database");
		try (Connection conn = connUtil.getConnection()) {

			LogSingleton.getLogger()
					.trace("connection established with db, creating prepared statement to submit request information");
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id) VALUES (?, ?,null,?,?,null, 0, ?)");
			ps.setInt(1, amount);
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setString(3, desc);
			ps.setInt(4, id);
			ps.setInt(5, type);
			ps.executeUpdate();
			LogSingleton.getLogger().trace("request prepared statement executed");

		} catch (SQLException e) {
			LogSingleton.getLogger().warn("failed to establish connection with database during login");

		}
	}

	@Override
	public List<String> pastTickets(int id) {
		LogSingleton.getLogger().trace("method called to retrieve past employee tickets");
		LogSingleton.getLogger().trace("attempting to get connection to database");
		try (Connection conn = connUtil.getConnection()) {

			LogSingleton.getLogger().trace("connection established with db, creating prepared statement to login");
			PreparedStatement ps = conn.prepareStatement(
					"SELECT *FROM ers_reimbursement WHERE reimb_author = ? ORDER BY reimb_status_id asc");
			ps.setInt(1, id);
			LogSingleton.getLogger().trace("past tickets prepared statement executed");
			ResultSet rs = ps.executeQuery();

			// initialize two arrays. one for pending requests
			// and one for all the others
			List<String> arr = new ArrayList<String>();
			// user is found
			while (rs.next()) {

				arr.add(Integer.toString(rs.getInt("reimb_amount")));
				// convert the timestamp to a date and the date to a string
				Date date = new Date();
				date.setTime(rs.getTimestamp("reimb_submitted").getTime());
				String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

				arr.add(formattedDate);

				if (rs.getTimestamp("reimb_resolved") != null) {
					Date date2 = new Date();
					date2.setTime(rs.getTimestamp("reimb_resolved").getTime());
					formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date2);
					arr.add(formattedDate);
				} else {
					arr.add("N/A");
				}

				arr.add(rs.getString("reimb_description"));

				String resolver = Integer.toString(rs.getInt("reimb_resolver"));
				System.out.println(resolver);
				if (!resolver.equals("0")) {
					int resolver_id = rs.getInt("reimb_resolver");
					PreparedStatement ps4 = conn
							.prepareStatement("SELECT *FROM ers_users WHERE ers_users_id = " + resolver_id);
					ResultSet rs4 = ps4.executeQuery();
					if (rs4.next()) {
						arr.add(rs4.getString("user_first_name") + " " + rs4.getString("user_last_name"));
					}
				} else {
					arr.add("N/A");
				}
				arr.add(rs.getString("reimb_status_id"));
				arr.add(rs.getString("reimb_type_id"));

			}
			LogSingleton.getLogger().trace("user's past tickets found. ");
			return arr;

		} catch (SQLException e) {
			LogSingleton.getLogger().warn("failed to establish connection with database during login");

		}
		// User with entered credentials doesn't exist
		LogSingleton.getLogger().trace("user's credentials not found.");
		return null;
	}

	@Override
	public List<String> allPastTickets() {
		LogSingleton.getLogger().trace("method called to retrieve all past tickets");
		LogSingleton.getLogger().trace("attempting to get connection to database");
		try (Connection conn = connUtil.getConnection()) {

			LogSingleton.getLogger()
					.trace("connection established with db, creating prepared statement to retrieve all past tickets");
			PreparedStatement ps = conn.prepareStatement("SELECT *FROM ers_reimbursement ORDER BY reimb_status_id asc");
			LogSingleton.getLogger().trace("past tickets prepared statement executed");
			ResultSet rs = ps.executeQuery();

			// initialize two arrays. one for pending requests
			// and one for all the others
			List<String> arr = new ArrayList<String>();
			// user is found
			while (rs.next()) {

				arr.add(Integer.toString(rs.getInt("reimb_amount")));
				// convert the timestamp to a date and the date to a string
				Date date = new Date();
				date.setTime(rs.getTimestamp("reimb_submitted").getTime());
				String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

				arr.add(formattedDate);

				if (rs.getTimestamp("reimb_resolved") != null) {
					Date date2 = new Date();
					date2.setTime(rs.getTimestamp("reimb_resolved").getTime());
					formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date2);
					arr.add(formattedDate);
				} else {
					arr.add("N/A");
				}

				arr.add(rs.getString("reimb_description"));

				int author_id = rs.getInt("reimb_author");
				PreparedStatement ps2 = conn
						.prepareStatement("SELECT *FROM ers_users WHERE ers_users_id = " + author_id);
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) {
					arr.add(rs2.getString("user_first_name") + " " + rs2.getString("user_last_name"));
				}

				String resolver = Integer.toString(rs.getInt("reimb_resolver"));
				if (!resolver.equals("0")) {

					int resolver_id = rs.getInt("reimb_resolver");
					PreparedStatement ps3 = conn
							.prepareStatement("SELECT *FROM ers_users WHERE ers_users_id = " + resolver_id);
					ResultSet rs3 = ps3.executeQuery();
					if (rs3.next()) {
						arr.add(rs3.getString("user_first_name") + " " + rs3.getString("user_last_name"));
					}
				} else {
					arr.add("N/A");
				}
				arr.add(rs.getString("reimb_status_id"));
				arr.add(rs.getString("reimb_type_id"));
				arr.add(Integer.toString(rs.getInt("reimb_id")));

			}
			LogSingleton.getLogger().trace("all past tickets found. ");
			return arr;

		} catch (SQLException e) {
			LogSingleton.getLogger().warn("failed to establish connection with database during all tickets retrieval");

		}
		// User with entered credentials doesn't exist
		LogSingleton.getLogger().trace("user's credentials not found.");
		return null;
	}

	@Override
	public void updateTickets(int status, int ticket_id, int id) {
		LogSingleton.getLogger().trace("method called to approve or deny ticket");
		LogSingleton.getLogger().trace("attempting to get connection to database");
		try (Connection conn = connUtil.getConnection()) {

			LogSingleton.getLogger()
					.trace("connection established with db, creating prepared statement to update ticket information");
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ers_reimbursement SET reimb_status_id = ?, reimb_resolved = ?, reimb_resolver = ? WHERE reimb_id = ?");

			ps.setInt(1, status);
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, id);
			ps.setInt(4, ticket_id);

			ps.executeUpdate();
			LogSingleton.getLogger().trace("ticket update prepared statement executed");

		} catch (SQLException e) {
			LogSingleton.getLogger().warn("failed to establish connection with database during login");

		}

	}
}
