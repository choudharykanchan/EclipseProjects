package com.dealertire.SMARTFramework;

/**
 * The environment the script is running under. Useful for constructing relative information.
 * @author bgreen
 */
public class Environment {

	/** The level the codebase is at. Can differ from database level. */
	public EnvironmentLevel codebaseLevel;
	/**The level the database is at. Can differ from codebase level.*/
	public EnvironmentLevel dataLevel;
	/**The base URL to access Internal Apps. Can be a real instance or a developer instance or whatever.*/
	public String baseURL;
	
		
	/**
	 * Constructor for the environment object
	 * @param codebaseLevel See {@link #codebaseLevel}
	 * @param dataLevel See {@link #dataLevel}
	 * @param baseURL See {@link #baseURL}
	 */
	public Environment(EnvironmentLevel codebaseLevel,
			EnvironmentLevel dataLevel, String baseURL) {
		super();
		this.codebaseLevel = codebaseLevel;
		this.dataLevel = dataLevel;
		this.baseURL = baseURL;
	}

	/**
	 * Enum for what I'm calling the "level" here, which we usually call "environment" itself. 
	 * @author bgreen
	 *
	 */
	public enum EnvironmentLevel {
		/**Development*/
		DEV,
		/**Demo/QA/PY/Consolidated Demo*/
		DEMO,
		/**Staging*/
		STG,
		/**Production*/
		PROD,
		/**Not used yet. */
		TEST
	}
}

