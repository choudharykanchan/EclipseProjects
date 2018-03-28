package com.dealertire.RightTurnFramework.Models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;

import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Utils.Environment;

/**
 * A model to represent a vehicle in our system. Most useful on the VehicleSelect page.
 * @author bgreen
 *
 */
public class Vehicle {
	/** All vehicles loaded from the data sheet	 */
	public static ArrayList<Vehicle> allVehicles;

	/** The year of the vehicle*/
	public String year;
	/**The make of the vehicle*/
	public String make;
	/**The model of the vehicle*/
	public String model;
	/**The trim of the vehicle*/
	public String trim;
	/**The option of the vehicle*/
	public String option;

	/**Whether the vehicle's sizes are staggered*/
	public Boolean isStaggered;
	/**Whether the vehicle supports runflat tires*/
	public Boolean isRunflat;
	/**Whether the vehicle is considered a truck*/
	public Boolean isTruck;
	/**Whether the vehicle supports winter tires*/
	public Boolean isWinter;
	/**Whether the vehicle has a winter tire package*/
	public Boolean hasWinterPackage;
	/**Whether the vehicle comes in multiple tire sizes*/
	public Boolean hasMultipleSizes;
	/**Whether we sell original factory fitment tires for this vehicle*/
	public Boolean hasOEMTires;
	/**Whether we sell tires for this vehicle at all */
	public Boolean hasFitment;
	/**The list of sizes that this vehicle's tires come in.*/
	public ArrayList<String> sizes;
	
	//mapping
	static final int YEAR_INDEX = 0;
	static final int MAKE_INDEX = 1;
	static final int MODEL_INDEX = 2;
	static final int TRIM_INDEX = 3;
	static final int OPTION_INDEX = 4;
	static final int STAGGERED_INDEX = 5;
	static final int RUNFLAT_INDEX = 6;
	static final int TRUCK_INDEX = 7;
	static final int WTPKG_INDEX = 8;
	static final int WINTER_INDEX = 9;
	static final int FITMENT_INDEX = 10;
	static final int OEM_INDEX = 11;
	static final int MULTISIZE_INDEX = 12;
	static final int SIZE1_INDEX = 13;
	static final int SIZE2_INDEX = 14;
	static final int SIZE3_INDEX = 15;

	

	/**
	 * Public constructor for making vehicles from other places
	 * @param year The year
	 * @param make The make
	 * @param model The model
	 * @param trim The trim
	 * @param option The option
	 */
	public Vehicle(String year, String make, String model, String trim,
			String option) {
		super();
		this.year = year;
		this.make = make;
		this.model = model;
		this.trim = trim;
		this.option = option;
	}

	private Vehicle(String[] variables) {
		// Decode from an array
		int numVariables = variables.length;		
		year = variables[YEAR_INDEX];
		make = variables[MAKE_INDEX];
		model = variables[MODEL_INDEX];
		trim = variables[TRIM_INDEX];
		option = variables[OPTION_INDEX];
		if (option.isEmpty()) option = null;
		isStaggered = variables[STAGGERED_INDEX].equalsIgnoreCase("yes");
		isRunflat = variables[RUNFLAT_INDEX].equalsIgnoreCase("yes");
		isTruck = variables[TRUCK_INDEX].equalsIgnoreCase("yes");
		isWinter = variables[WINTER_INDEX].equalsIgnoreCase("yes");
		hasWinterPackage = variables[WTPKG_INDEX].equalsIgnoreCase("yes");
		hasMultipleSizes = variables[MULTISIZE_INDEX].equalsIgnoreCase("yes");
		hasOEMTires = variables[OEM_INDEX].equalsIgnoreCase("yes");
		hasFitment = variables[FITMENT_INDEX].equalsIgnoreCase("yes");
		
		sizes = new ArrayList<String>();
		addSize(variables[SIZE1_INDEX]);

		if (hasMultipleSizes) {
			addSize(variables[SIZE2_INDEX]);
			if (numVariables >= SIZE3_INDEX+1) {
				addSize(variables[SIZE3_INDEX]);
			}
		}
	}

	private void addSize(String size) {
		if (size.equals("")) return; //Do not add empty sizes
		if (isStaggered) {
			sizes.addAll(Arrays.asList(size.split(":")));
		} else {
			sizes.add(size);
		}
	}
	
	/**
	 * Get the make of this vehicle
	 * @return The make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * Whether the vehicle matches a find criteria. Used in searching
	 * @param criteria The criteria to match
	 * @return True if it fulfills the criteria, False if not.
	 */
	public Boolean matchesCriteria(Criteria criteria) {
		switch (criteria) {
		case MULTISIZE:
			return hasMultipleSizes;
		case RUNFLAT:
			return isRunflat;
		case STAGGERED:
			return isStaggered;
		case OPTION:
			return option != null;
		case TRUCK:
			return isTruck;
		case WINTER:
			return isWinter;
		case WTPKG:
			return hasWinterPackage;
		case OEM:
			return hasOEMTires;
		case FITMENT:
			return hasFitment;
		default:
			return false;
		}
	}
	
	public String toString() {
		String s =  year + " " + make + " " + model + " (" + trim;
		if (option != null) {
			s += " " + option;
		}		
		s += ")";
		return s;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((option == null) ? 0 : option.hashCode());
		result = prime * result + ((trim == null) ? 0 : trim.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (option == null) {
			if (other.option != null)
				return false;
		} else if (!option.equals(other.option))
			return false;
		if (trim == null) {
			if (other.trim != null)
				return false;
		} else if (!trim.equals(other.trim))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	/**
	 * Load data from the data sheet for the environment.
	 * @param environment The environment to load from
	 * @return The list of vehicles, which is also cached for searching later.
	 */
	public static ArrayList<Vehicle> loadData(Environment environment) {
		String filename;

		switch (environment) {
		case DEMO:
			filename = "demoVehicles.csv";
			break;
		case DEV:
			filename = "devVehicles.csv";
			break;
		default:
		case STAGE:
		case PROD:
			filename = "prodVehicles.csv";
			break;
		}

		// Load from CSV
		try {
			ArrayList<String[]> lines = Utils.readFromCSV(filename);
			loadData(lines);
		} catch (FileNotFoundException e) {
			return allVehicles; // empty list
		} catch (IOException e) {
			return allVehicles; // empty list
		}

		return allVehicles;
	}
	
	/**
	 * Load data, not from CSV. For unit testing
	 * @param data The data to parse. 
	 */
	public static void loadData(ArrayList<String[]> data) {
		allVehicles = new ArrayList<Vehicle>();
		for (String[] line : data) {
			allVehicles.add(new Vehicle(line));
		}
	}

	/**
	 * Find vehicles by single criteria
	 * @param criteria The criteria to verify
	 * @param criteriaIsTrue True if you want the vehicles to match the criteria. False for a list of vehicles that do NOT match the criteria.
	 * @return The list of vehicles according to the parameters.
	 */
	public static ArrayList<Vehicle> findVehicles(Criteria criteria, Boolean criteriaIsTrue) {
		ArrayList<Vehicle> matches = new ArrayList<Vehicle>();
		
		for (Vehicle v : allVehicles) {
			if (v.matchesCriteria(criteria) == criteriaIsTrue) {
				matches.add(v);
			}
		}
		
		return matches;
	}
	
	/**
	 * Returns a specific Vehicle. Useful for debugging.
	 * @param year The year for the vehicle
	 * @param make The make of the vehicle
	 * @param model The model of the vehicle
	 * @param trim The trim of the vehicle
	 * @param option The option of the vehicle, or null for none.
	 * @return The vehicle
	 */
	public static Vehicle getSpecificVehicle(String year, String make, String model, String trim, String option) {
		for (Vehicle v : allVehicles) {
			if (v.year.equalsIgnoreCase(year)
					&& v.make.equalsIgnoreCase(make)
					&& v.model.equalsIgnoreCase(model)
					&& v.trim.equalsIgnoreCase(trim)
					&& (v.option == null && option == null || v.option.equalsIgnoreCase(option))
					) {
				return v;
			}
		}
		
		LogManager.getLogger(Vehicle.class.getSimpleName()).info("Vehicle not found.");
		throw new IllegalArgumentException("No such vehicle found.");
	}
	
	/**
	 * Find vehicles by a list of criteria. This is considered an AND operation; vehicles will match ALL criteria. 
	 * Example usage:
	 * To find a list of vehicles with staggered sets and runflat tires but only one size option to choose:
	 * 
	 * Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
	 *	criteriaList.put(Criteria.RUNFLAT, true);
	 *	criteriaList.put(Criteria.MULTISIZE, false);
	 *	criteriaList.put(Criteria.STAGGERED, true);
	 *	findVehicles(criteriaList);
	 *
	 * @param criteriaList The list of criteria, mapped to whether you want that criteria to be true or false.
	 * @return The list of vehicles according to the parameters.
	 */
	public static ArrayList<Vehicle> findVehicles(Map<Criteria, Boolean> criteriaList) {
		ArrayList<Vehicle> matches = new ArrayList<Vehicle>();
		
		//default to fitment
		if (!criteriaList.containsKey(Criteria.FITMENT)) {
			criteriaList.put(Criteria.FITMENT, true);
		}
		
		for (Vehicle v : allVehicles) {
			boolean invalid = false;
			for (Criteria criteria : criteriaList.keySet()) {
				if (v.matchesCriteria(criteria) != criteriaList.get(criteria)) {
					invalid = true;
				}
			}
			if (!invalid) matches.add(v);
		}
		
		if (matches.size() <= 0) throw new IllegalArgumentException("No vehicle available that matches criteria");
		
		return matches;
	}
	
	/**
	 * Find vehicles by a list of criteria as well as a make This is considered an AND operation; vehicles will match ALL criteria. 
	 * Example usage:
	 * To find a list of vehicles with staggered sets and runflat tires but only one size option to choose:
	 * 
	 * Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
	 *	criteriaList.put(Criteria.RUNFLAT, true);
	 *	criteriaList.put(Criteria.MULTISIZE, false);
	 *	criteriaList.put(Criteria.STAGGERED, true);
	 *	findVehicles(criteriaList);
	 *
	 * @param criteriaList The list of criteria, mapped to whether you want that criteria to be true or false.
	 * @param make The make you want to match
	 * @return The list of vehicles according to the parameters.
	 */
	public static ArrayList<Vehicle> findVehicles(Map<Criteria, Boolean> criteriaList, String make) {
		ArrayList<Vehicle> matches = new ArrayList<Vehicle>();
		
		//default to fitment
		if (!criteriaList.containsKey(Criteria.FITMENT)) {
			criteriaList.put(Criteria.FITMENT, true);
		}
		
		for (Vehicle v : allVehicles) {
			boolean invalid = false;
			for (Criteria criteria : criteriaList.keySet()) {
				if (!v.make.equalsIgnoreCase(make) || v.matchesCriteria(criteria) != criteriaList.get(criteria)) {
					invalid = true;
				}
			}
			if (!invalid) matches.add(v);
		}
		
		if (matches.size() <= 0) throw new IllegalArgumentException("No vehicle available that matches criteria");
		
		return matches;
	}
	
	/**
	 * Like {@link #findVehicles(Map) findVehicles}, but gives a single vehicle. Useful when you want to just pick one.
	 * @param criteriaList The list of criteria, mapped to whether you want that criteria to be true or false.
	 * @return A single vehicle that matches all parameters.
	 */
	public static Vehicle getRandomVehicleByCriteria(Map<Criteria, Boolean> criteriaList) {
		ArrayList<Vehicle> vehicles = findVehicles(criteriaList);
		Random rand = new Random();
		return vehicles.get(rand.nextInt(vehicles.size()));
	}
	
	/**
	 * Like {@link #findVehicles(Map, String) findVehicles}, but gives a single vehicle. Useful when you want to just pick one.
	 * @param criteriaList The list of criteria, mapped to whether you want that criteria to be true or false.
	 * @param make The make you want to match
	 * @return A single vehicle that matches all parameters.
	 */
	public static Vehicle getRandomVehicleByCriteria(Map<Criteria, Boolean> criteriaList, String make) {
		ArrayList<Vehicle> vehicles = findVehicles(criteriaList, make);
		Random rand = new Random();
		return vehicles.get(rand.nextInt(vehicles.size()));
	}
	
	/**
	 * Gives a single vehicle. Useful when you want to just pick one and aren't choosy at all.
	 * @param criteriaList The list of criteria, mapped to whether you want that criteria to be true or false.
	 * @param make The make you want to match
	 * @return A single vehicle that matches all parameters.
	 */
	public static Vehicle getRandomVehicle() {
		Random rand = new Random();
		return allVehicles.get(rand.nextInt(allVehicles.size()));
	}
	
	/**
	 * Get ALL the vehicles. For reasons. 
	 * @return Every vehicle int he list.
	 */
	public static ArrayList<Vehicle> getAllVehicles() {
		return allVehicles;
	}
	
	/**Search criteria for vehicles*/
	public enum Criteria {
		/**Whether the vehicle has multiple sizes to choose from.
		 * This will not match for a vehicle with only one set of staggered sizes,
		 * but will match if there are two or more sets of staggered sizes that could fit this vehicle.
		 */
		MULTISIZE,
		
		/**Whether the vehicle has staggered sizes*/
		STAGGERED,
		
		/**Whether the vehicle has runflat tires*/
		RUNFLAT,
		
		/**Whether the vehicle needs to be specified with an option */
		OPTION,
		
		/**Whether the vehicle is considered a truck*/
		TRUCK,
		
		/**Whether the vehicle has winter tires*/
		WINTER,
		
		/**Whether the vehicle has a Winter Tire Package*/
		WTPKG,
		
		/**Whether the vehicle will show original fitment tires in the list*/
		OEM, 
		
		/**Whether we sell tires for this vehicle at all */
		FITMENT
	}
}
