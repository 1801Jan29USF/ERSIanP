package com.revature.dao;

import com.revature.beans.ErsUser;

/*Interface for the AccountDAO containing methods that
 * communicate with database
 */
public interface UserDAO {
	
	ErsUser login(String u, String p);
}
