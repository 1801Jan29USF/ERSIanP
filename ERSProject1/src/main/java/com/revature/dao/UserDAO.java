package com.revature.dao;

import java.util.List;

/*Interface for the AccountDAO containing methods that
 * communicate with database
 */
public interface UserDAO {

	List<Integer> login(String u, String p);

	List<String> profile(int id);

	void submitRequest(int id, int amt, String desc, int type);

	List<String> pastTickets(int id);

	List<String> allPastTickets();

	void updateTickets(int status, int ticket_id, int id);

}
