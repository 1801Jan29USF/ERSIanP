package com.revature.util;

import org.apache.log4j.Logger;

/*
 * Singleton class for the Logger
 *
 */

public class LogSingleton {

	/*******************************************************************************
	 * Class Fields
	 ********************************************************************************/

	private static Logger log = Logger.getRootLogger();

	/*******************************************************************************
	 * Log Getter
	 ********************************************************************************/
	
	public static Logger getLogger() {
		return log;
	}

}
