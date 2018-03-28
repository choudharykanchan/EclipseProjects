package com.dealertire.RightTurnFramework.Models;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;

import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Utils.Environment;

/**
 * Model for a single tire size.
 * @author bgreen
 *
 */
public class Size {
	private final static boolean USE_DB = false;
	
	/**The type of vehicle and/or service that this size is made for. Can be blank for Euro-Metric, or P, T, LT, or ST */
	public String serviceType;
	/**The width of a cross-section, in millimeters.*/
	public String width;
	/**The aspect ratio of the sidewall*/
	public String ratio;
	/**The wheel diameter*/
	public String diameter;
	/**Identifies load index and speed rating.*/
	public String serviceDescription;

	private boolean isRunflat;
	private boolean inStaggered;
	private boolean weSell;
	
	/**The vehicle make to search for with this size. This is used to make searching our catalog easier, and is RT specific, not part of the size of the tire.*/
	public String make;
	
	private static DB database;
	private static ArrayList<Size> allSizes;

	/**
	 * Constructor for a size from a set of variables. Can be used to parse CSV rows.
	 * @param variables
	 */
	public Size(String[] variables) {
		serviceType = variables[0];
		width = variables[1];
		ratio = variables[2];
		diameter = variables[3];
		serviceDescription = variables[4];
		make = variables[5];
		
		isRunflat = variables[6].equalsIgnoreCase("yes");
		inStaggered = variables[7].equalsIgnoreCase("yes");
		weSell = variables[8].equalsIgnoreCase("yes");
	}
	
	/**
	 * Constructor for a Size from a String. Do not use for staggered sizes!
	 * @param sizeDescription The string to parse
	 */
	public Size(String sizeDescription) {
		StringBuilder temp = new StringBuilder();
		int charPointer = 0;
		
		if (sizeDescription.contains(":")) {
			throw new IllegalArgumentException("You are trying to turn a staggered size into a single Size object! Don't do that.");
		}
		//Service type is the first letters before any numbers
		while (!Character.isDigit(sizeDescription.charAt(charPointer))) {
			temp.append(sizeDescription.charAt(charPointer));
			charPointer++;
		}
		
		serviceType = temp.toString();
		temp = new StringBuilder();
		
		//width is numbers before slash
		while (Character.isDigit(sizeDescription.charAt(charPointer))) {
			temp.append(sizeDescription.charAt(charPointer));
			charPointer++;
		}
		
		width = temp.toString();
		temp = new StringBuilder();
		
		charPointer++; //ignore slash
		
		//aspect is numbers after slash
		while (Character.isDigit(sizeDescription.charAt(charPointer))) {
			temp.append(sizeDescription.charAt(charPointer));
			charPointer++;
		}
		
		ratio = temp.toString();
		temp = new StringBuilder();
		
		//not storing constructor
		while (!Character.isDigit(sizeDescription.charAt(charPointer))) {
			charPointer++;
		}
		
		//Diameter is numbers after constructor
		//Sometimes there's no service description
		while (charPointer < sizeDescription.length() && Character.isDigit(sizeDescription.charAt(charPointer))) {
			temp.append(sizeDescription.charAt(charPointer));
			charPointer++;
		}
		
		diameter = temp.toString();
		temp = new StringBuilder();
		
		//Descriptor is characters after space
		charPointer++; //ignore space if it exists
		
		while (charPointer < sizeDescription.length()) {
			temp.append(sizeDescription.charAt(charPointer));
			charPointer++;
		}
		
		serviceDescription = temp.toString();
	}
	
	public String toString() {
		return serviceType + width + "/" + ratio + "R" + diameter + " " + serviceDescription;
	}
	
	/**
	 * Returns true if this has a service type listed, false if not.
	 * @return true if this has a service type listed, false if not.
	 */
	public boolean hasServiceType() {
		return serviceType != null && !serviceType.isEmpty();
	}
	
	/**
	 * Returns true if this has a service description listed, false if not.
	 * @return true if this has a service description listed, false if not.
	 */
	public boolean hasServiceDescription() {
		return serviceDescription != null && !serviceDescription.isEmpty();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Size) {
			Size otherSize = (Size) obj;
			return (this.make != null && otherSize.make != null) ? this.make == otherSize.make : true
					&& (this.hasServiceType() && otherSize.hasServiceType()) ? this.serviceType == otherSize.serviceType : true
					&& this.width.equalsIgnoreCase(otherSize.width)
					&& this.ratio.equalsIgnoreCase(otherSize.ratio)
					&& this.diameter.equalsIgnoreCase(otherSize.diameter)
					&& (this.hasServiceDescription() && otherSize.hasServiceDescription()) ? this.serviceDescription.equalsIgnoreCase(otherSize.serviceDescription) : true
					;
		} else return false;
	}

	/**
	 * Whether the size matches a find criteria. Used in searching
	 * @param criteria The criteria to match
	 * @return True if it fulfills the criteria, False if not.
	 */
	public boolean matchesCriteria(Criteria criteria) {
		switch(criteria) {
		case RUNFLAT:
			return isRunflat;
		case STAGGERED:
			return inStaggered;
		case FITMENT:
			return weSell;
		default:
			return false;
		}
	}
	
	/**
	 * Load data from the data sheet for the environment.
	 * @param environment The environment to load from
	 */
	public static void loadData(Environment environment) {
		String filename;

		if (USE_DB) {
			DB.setEnvironment(environment);
			database = DB.getInstance();
		} else {
			allSizes = new ArrayList<Size>();
	
			switch (environment) {
			case DEMO:
				filename = "demoSizes.csv";
				break;
			case DEV:
				filename = "devSizes.csv";
				break;
			default:
			case STAGE:
			case PROD:
				filename = "prodSizes.csv";
				break;
			}
	
			// Load from CSV
				ArrayList<String[]> lines;
				try {
					lines = Utils.readFromCSV(filename);
				} catch (IOException e) {
					return;
				}
				for (String[] line : lines) {
					allSizes.add(new Size(line));
				}
		}
	}
	
	/**
	 * Searches for and returns all sizes with a given criterium or set of criteria
	 * @param criteriaList The list of criteria to match. Will be treated as an AND condition
	 * @return An arrayList of sizes
	 */
	public static ArrayList<Size> getSizesByCriteria(Map<Criteria, Boolean> criteriaList) {
		//default to fitment
		if (!criteriaList.containsKey(Criteria.FITMENT)) {
			criteriaList.put(Criteria.FITMENT, true);
		}
				
		if (USE_DB) {
			return getSizesByCriteriaFromDB(criteriaList);
		} else {
			ArrayList<Size> matches = new ArrayList<Size>();
			
			for (Size s : allSizes) {
				boolean invalid = false;
				for (Criteria criteria : criteriaList.keySet()) {
					if (s.matchesCriteria(criteria) != criteriaList.get(criteria)) {
						invalid = true;
					}
				}
				if (!invalid) matches.add(s);
			}
			
			if (matches.size() <= 0) throw new IllegalArgumentException("No size available that matches criteria");
			
			return matches;
		}
	}
	
	/**
	 * Like {@link #getSizesByCriteria(Map) getSizesByCriteria}, but gives a single size. Useful when you want to just pick one.
	 * @param criteriaList The list of criteria, mapped to whether you want that criteria to be true or false.
	 * @return A single size that matches all parameters.
	 */
	public static Size getRandomSizeByCriteria(Map<Criteria, Boolean> criteriaList) {
		ArrayList<Size> sizes = getSizesByCriteria(criteriaList);
		Random rand = new Random();
		Size size = sizes.get(rand.nextInt(sizes.size()));
		LogManager.getLogger(Size.class.getSimpleName()).info("Using size " + size);
		return size;
	}
	
	/**
	 * Gets a random size without criteria.
	 * @return A single size.
	 */
	public static Size getRandomSize() {
		Random rand = new Random();
		return allSizes.get(rand.nextInt(allSizes.size()));
	}

	/**
	 * Get all the sizes
	 * @return All possible sizes
	 */
	public static ArrayList<Size> getAllSizes() {
		return allSizes;
	}
	
	private static ArrayList<Size> getSizesByCriteriaFromDB(Map<Criteria, Boolean> criteriaList) {
		
		ArrayList<Size> sizes = new ArrayList<Size>();
		try {
			database.connect();
			sizes = database.getSizesFromDB(criteriaList);
		} catch (SQLException e) {
			LogManager.getLogger(Size.class.getSimpleName()).fatal("Could not connect to DB: error " + e.getErrorCode() + " " + e.getLocalizedMessage());
		} catch (ClassNotFoundException e) {
			LogManager.getLogger(Size.class.getSimpleName()).fatal("Error: " + e.getMessage());
		} finally {
			try {database.disconnect();} catch (SQLException e) {}
		}
		
		return sizes;
	}
	
	/**
	 * Search criteria for sizes
	 * @author bgreen
	 */
	public enum Criteria {
		/**Whether a tire of this size can be part of a staggered set. Almost all sizes are also sold loose.*/
		STAGGERED,
		/**Whether a tire in this size supports runflat*/
		RUNFLAT,
		/**Whether we sell a tire in this size.*/
		FITMENT
	}
}
