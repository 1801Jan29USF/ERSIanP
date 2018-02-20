package com.revature.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * Class reads our aws database url, username, and password from database.properties
 * and returns a connection utility for access to our database
 */

public class ConnectionUtil {

	/*******************************************************************************
	 * Class Fields
	 ********************************************************************************/

	private Properties prop = new Properties();
	private static ConnectionUtil connUtil = new ConnectionUtil();

	/*******************************************************************************
	 * Database Connection Methods
	 ********************************************************************************/

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// loads the correct databse properties file
	private ConnectionUtil() {
		super();
		try {
			InputStream is = getClass().getResourceAsStream("/database.properties");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			prop.load(reader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// returns a DriverManager giving us connection and access to Statements
	// appends pertinent information from our database properties file to our
	// connection url
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
				prop.getProperty("pass"));
	}

	public static ConnectionUtil getConnectionUtil() {
		return connUtil;
	}
}