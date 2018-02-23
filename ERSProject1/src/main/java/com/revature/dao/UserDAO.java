package com.revature.dao;

import java.util.List;

/*Interface for the AccountDAO containing methods that
 * communicate with database
 */
public interface UserDAO {

	String login(String u, String p);

	List<String> profile(String u, String p);

	void submitRequest(String amt, String desc, int id);

	List<String> pastTickets(int id);

}
