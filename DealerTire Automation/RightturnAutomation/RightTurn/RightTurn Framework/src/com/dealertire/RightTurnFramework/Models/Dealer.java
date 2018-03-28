package com.dealertire.RightTurnFramework.Models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Utils.Environment;;

/**
 * A model representing a dealer.
 * @author bgreen
  */
public class Dealer {
	/** All dealers loaded from the data sheet	 */
	public static ArrayList<Dealer> allDealers;
	
	/**The unique identifier for the dealer*/
	public String uuid;
	
	/**The name of the dealership, human-readable*/
	public String name;
	/**The dealer's code, so you can look them up on V5 if need be*/
	public String code;
	/**The market the dealer is in*/
	public Market market;
	/**The zipcode associated with the dealer*/
	public int zipcode;
	/**The make the dealer specializes in */
	public String make;
	/**The address line of the dealer*/
	public String address;
	
	private boolean hasCarwash;
	private boolean hasLoaner;
	private boolean hasValet;
	private boolean hasShuttle;
	private boolean hasCoffee;
	private boolean hasWifi;
	private boolean hasLounge;
	private boolean doesInspection;
	private String daysClosed;
	private ArrayList<Calendar> holidaysClosed;
	
	
	//mapping
	static final int UUID_INDEX = 0;
	static final int CODE_INDEX = 1;
	static final int NAME_INDEX = 2;
	static final int MARKET_INDEX = 3;
	static final int ZIPCODE_INDEX = 4;
	static final int MAKE_INDEX = 5;
	static final int CARWASH_INDEX = 6;
	static final int LOANER_INDEX = 7;
	static final int VALET_INDEX = 8;
	static final int SHUTTLE_INDEX = 9;
	static final int COFFEE_INDEX = 10;
	static final int WIFI_INDEX = 11;
	static final int LOUNGE_INDEX = 12;
	static final int INSPECTION_INDEX = 13;
	static final int CLOSED_INDEX = 14;
	static final int HOLIDAYS_INDEX = 15;
	
	private Dealer(String[] variables) {
		uuid = variables[UUID_INDEX];
		code = variables[CODE_INDEX];
		name = variables[NAME_INDEX];
		market = Market.valueOf(variables[MARKET_INDEX].toUpperCase());
		zipcode = Integer.parseInt(variables[ZIPCODE_INDEX]);
		make = variables[MAKE_INDEX];
		hasCarwash = variables[CARWASH_INDEX].equalsIgnoreCase("yes");
		hasLoaner = variables[LOANER_INDEX].equalsIgnoreCase("yes");
		hasValet = variables[VALET_INDEX].equalsIgnoreCase("yes");
		hasShuttle = variables[SHUTTLE_INDEX].equalsIgnoreCase("yes");
		hasCoffee = variables[COFFEE_INDEX].equalsIgnoreCase("yes");
		hasWifi = variables[WIFI_INDEX].equalsIgnoreCase("yes");
		hasLounge = variables[LOUNGE_INDEX].equalsIgnoreCase("yes");
		doesInspection = variables[INSPECTION_INDEX].equalsIgnoreCase("yes");
		daysClosed = variables[CLOSED_INDEX];
		
		holidaysClosed = new ArrayList<Calendar>();
		
		String[] holidaysClosedList = variables[HOLIDAYS_INDEX].split(",");
		for (String holiday : holidaysClosedList) {
			Calendar holidayRepresented = Calendar.getInstance();
			if (holiday.equalsIgnoreCase("New years day") || holiday.equalsIgnoreCase("New year's day")) {
				holidayRepresented.set(Calendar.MONTH, Calendar.JANUARY);
				holidayRepresented.set(Calendar.DAY_OF_MONTH, 1);
				holidaysClosed.add(holidayRepresented);
			} else if (holiday.equalsIgnoreCase("Independance Day")) {
				holidayRepresented.set(Calendar.MONTH, Calendar.JULY);
				holidayRepresented.set(Calendar.DAY_OF_MONTH, 4);
				holidaysClosed.add(holidayRepresented);
			} else if (holiday.equalsIgnoreCase("Christmas")) {
				holidayRepresented.set(Calendar.MONTH, Calendar.DECEMBER);
				holidayRepresented.set(Calendar.DAY_OF_MONTH, 25);
				holidaysClosed.add(holidayRepresented);
			} else if (holiday.equalsIgnoreCase("Memorial day")) {
				holidayRepresented.set(Calendar.MONTH, Calendar.MAY);
				
				//Get last monday
				holidayRepresented.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				holidayRepresented.set(Calendar.DAY_OF_MONTH, 31);
				
				//Go backward from the last day until we get to a monday
				while (holidayRepresented.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
					holidayRepresented.set(Calendar.DATE, holidayRepresented.get(Calendar.DATE)-1);
				}
				holidaysClosed.add(holidayRepresented);
			} else if (holiday.equalsIgnoreCase("Labor day")) {
				holidayRepresented.set(Calendar.MONTH, Calendar.SEPTEMBER);
				
				//Get first monday
				holidayRepresented.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				holidayRepresented.set(Calendar.DAY_OF_MONTH, 1);
				
				//Go forward from the first day until we get to a monday
				while (holidayRepresented.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
					holidayRepresented.set(Calendar.DATE, holidayRepresented.get(Calendar.DATE)+1);
				}
				holidaysClosed.add(holidayRepresented);
			} else if (holiday.equalsIgnoreCase("Thanksgiving")) {
				holidayRepresented.set(Calendar.MONTH, Calendar.NOVEMBER);
				
				//Get first thursday
				holidayRepresented.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				holidayRepresented.set(Calendar.DAY_OF_MONTH, 1);
				
				//Go forward from the first day until we get to a thursday
				while (holidayRepresented.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
					holidayRepresented.set(Calendar.DATE, holidayRepresented.get(Calendar.DATE)+1);
				}
				
				//Then add three weeks to get fourth thursday
				holidayRepresented.add(Calendar.WEEK_OF_MONTH, 3);
				
				holidaysClosed.add(holidayRepresented);
			}
		}

	}
	
	
	
	/**
	 * Basic constructor.
	 * @param name The dealer's name
	 * @param market The market the dealer's in
	 * @param zipcode The zipcode for the dealer
	 * @param make The make the dealer specialises in
	 * @param address The address of the dealer, for comparison purposes
	 */
	public Dealer(String name, Market market, int zipcode, String make, String address) {
		this.name = name;
		this.code = null;
		this.market = market;
		this.zipcode = zipcode;
		this.make = make;
		this.address = address;
	}

	


	@Override
	public String toString() {
		return "Dealer [name=" + name + ", code=" + code + ", zipcode="
				+ zipcode + ", make=" + make + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Dealer) {
			Dealer d = (Dealer) obj;
			return this.name.equalsIgnoreCase(d.name) 
					&&	this.make.equalsIgnoreCase(d.make)
					&&  this.zipcode == d.zipcode
					&&  this.market == d.market;
		}
		return false; //not even the same class means not equal
	}

	/**
	 * Determine if this dealer matches a given criteria
	 * @param c The criteria to match
	 * @return True if so, false if not
	 */
	public boolean matchesCriteria(Criteria c) {
		switch (c) {
			case CARWASH:
				return hasCarwash;
			case LOANER:
				return hasLoaner;
			case SHUTTLE:
				return hasShuttle;
			case COFFEE:
				return hasCoffee;
			case WIFI:
				return hasWifi;
			case LOUNGE:
				return hasLounge;
			case INSPECTION:
				return doesInspection;
			case VALET:
				return hasValet;
			default:
				return false;
		}
	}
	
	
	/**
	 * For a given date, determines if this dealership is open
	 * @param cal The date to check
	 * @return True if it is open, false if it is closed.
	 */
	public boolean isOpenOn(Calendar cal) {

		//First, day of week
		if (daysClosed.contains(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US))) {
			return false;
		}
		
		//Now holidays
		for (Calendar holiday : holidaysClosed) {
			if (holiday.get(Calendar.MONTH) == cal.get(Calendar.MONTH)
					&& holiday.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
				return false;
			}
		}
		
		return true;
	}
	/**
	 * Load data from the data sheet for the environment.
	 * @param environment The environment to load from
	 * @return The list of vehicles, which is also cached for searching later.
	 */
	public static ArrayList<Dealer> loadData(Environment environment) {
		String filename;

		allDealers = new ArrayList<Dealer>();

		switch (environment) {
		case DEMO:
			filename = "demoDealers.csv";
			break;
		case DEV:
			filename = "devDealers.csv";
			break;
		default:
		case STAGE:
		case PROD:
			filename = "prodDealers.csv";
			break;
		}

		// Load from CSV
		try {
			ArrayList<String[]> lines = Utils.readFromCSV(filename);
			for (String[] line : lines) {
				allDealers.add(new Dealer(line));
			}		
		} catch (FileNotFoundException e) {
			return allDealers; // empty list
		} catch (IOException e) {
			return allDealers; // empty list
		}

		return allDealers;
	}
	
	
	
	/**
	 * Find dealers by a list of criteria. This is considered an AND operation; dealers will match ALL criteria. 
	 *
	 * @param criteriaList The list of criteria, mapped to whether you want that criteria to be true or false.
	 * @return The list of dealers according to the parameters.
	 * @see Vehicle#findDealers(Map)
	 */
	public static ArrayList<Dealer> findDealers(Map<Criteria, Boolean> criteriaList) {
		ArrayList<Dealer> matches = new ArrayList<Dealer>();

		for (Dealer d : allDealers) {
			boolean invalid = false;
			for (Criteria criteria : criteriaList.keySet()) {
				if (d.matchesCriteria(criteria) != criteriaList.get(criteria)) {
					invalid = true;
				}
			}
			if (!invalid) matches.add(d);
		}
		
		if (matches.size() <= 0) throw new IllegalArgumentException("No dealer available that matches criteria");
		
		return matches;
	}

	/**
	 * Find dealers by a list of criteria and a specific market. This is considered an AND operation; dealers will match ALL criteria. 
	 *
	 * @param criteriaList The list of criteria, mapped to whether you want that criteria to be true or false.
	 * @param m The market to filter by
	 * @return The list of dealers according to the parameters.
	 * @see Vehicle#findDealers(Map)
	 */
	public static ArrayList<Dealer> findDealers(Map<Criteria, Boolean> criteriaList, Market m) {
		ArrayList<Dealer> matches = new ArrayList<Dealer>();

		for (Dealer d : allDealers) {
			boolean invalid = false;
			if (d.market == m) {
				for (Criteria criteria : criteriaList.keySet()) {
					if (d.matchesCriteria(criteria) != criteriaList.get(criteria)) {
						invalid = true;
					}
				}
				if (!invalid) matches.add(d);
			}
		}
		
		if (matches.size() <= 0) throw new IllegalArgumentException("No dealer available that matches criteria");
		
		return matches;
	}
	
	/**
	 * Find dealers by a list of criteria and a specific market and vehicle make. This is considered an AND operation; dealers will match ALL criteria. 
	 *
	 * @param criteriaList The list of criteria, mapped to whether you want that criteria to be true or false.
	 * @param m The market to filter by
	 * @param make The specific make to look for
	 * @return The list of dealers according to the parameters.
	 * @see Vehicle#findDealers(Map)
	 */
	public static ArrayList<Dealer> findDealers(Map<Criteria, Boolean> criteriaList, Market m, String make) {
		ArrayList<Dealer> matches = new ArrayList<Dealer>();

		for (Dealer d : allDealers) {
			boolean invalid = false;
			if (d.market == m && d.make.equalsIgnoreCase(make)) {
				for (Criteria criteria : criteriaList.keySet()) {
					if (d.matchesCriteria(criteria) != criteriaList.get(criteria)) {
						invalid = true;
					}
				}
				if (!invalid) matches.add(d);
			}
		}
		
		if (matches.size() <= 0) throw new IllegalArgumentException("No dealer available that matches criteria");
		
		return matches;
	}
	
	/**
	 * Find dealers by a list of criteria and a specific vehicle make. This is considered an AND operation; dealers will match ALL criteria. 
	 *
	 * @param criteriaList The list of criteria, mapped to whether you want that criteria to be true or false.
	 * @param make The specific make to look for
	 * @return The list of dealers according to the parameters.
	 * @see Vehicle#findDealers(Map)
	 */
	public static ArrayList<Dealer> findDealers(Map<Criteria, Boolean> criteriaList, String make) {
		ArrayList<Dealer> matches = new ArrayList<Dealer>();

		for (Dealer d : allDealers) {
			boolean invalid = false;
			if (d.make.equalsIgnoreCase(make)) {
				for (Criteria criteria : criteriaList.keySet()) {
					if (d.matchesCriteria(criteria) != criteriaList.get(criteria)) {
						invalid = true;
					}
				}
				if (!invalid) matches.add(d);
			}
		}
		
		if (matches.size() <= 0) throw new IllegalArgumentException("No dealer available that matches criteria");
		
		return matches;
	}
	
	
	/**
	 * Find dealers that are either open or closed on a given date
	 * @param date The date to check
	 * @param shouldBeOpen True for open, false for closed
	 * @return The list of dealers
	 */
	public static ArrayList<Dealer> findDealersByDate(Calendar date, boolean shouldBeOpen) {
		ArrayList<Dealer> matches = new ArrayList<Dealer>();
		
		for (Dealer d : allDealers) {
			if (d.isOpenOn(date) == shouldBeOpen) {
				matches.add(d);
			}
		}
		
		if (matches.size() <= 0) throw new IllegalArgumentException("No dealer available that matches criteria");
		return matches;
	}
	
	/**
	 * Like {@link #findDealersByDate(Map, String) findDealersByDate}, but gives a single dealer. Useful when you want to just pick one.
	 * @param date The date to check
	 * @param shouldBeOpen True for open, false for closed
	 * @return A single vehicle that matches all parameters.
	 */
	public static Dealer getRandomDealerByDate(Calendar date, boolean shouldBeOpen) {
		ArrayList<Dealer> dealers = findDealersByDate(date, shouldBeOpen);
		Random rand = new Random();
		return dealers.get(rand.nextInt(dealers.size()));
	}
	
	/**
	 * Like {@link #findDealers(Map, String) findDealers}, but gives a single dealer. Useful when you want to just pick one.
	 * @param criteriaList The criteria to choose
	 * @return A single dealer
	 */
	public static Dealer getRandomDealerByCriteria(Map<Criteria, Boolean> criteriaList) {
		ArrayList<Dealer> dealers = findDealers(criteriaList);
		Random rand = new Random();
		return dealers.get(rand.nextInt(dealers.size()));
	}
	
	/**
	 * Get a random dealer
	 * @return A single dealer
	 */
	public static Dealer getRandomDealer() {
		Random rand = new Random();
		return allDealers.get(rand.nextInt(allDealers.size()));
	}
	
	/**
	 * Which market a dealer is in
	 * @author bgreen
	 */
	public enum Market {
		/** Dallas	 */
		DALLAS,
		
		/** Cleveland	 */
		CLEVELAND
	}
	
	/**
	 * Criteria to search by
	 */
	
	public enum Criteria {
		/** Dealer offers a carwash */
		CARWASH,
		
		/**Dealer offers a loaner car */
		LOANER,
		
		/**Dealer offers a shuttle service */
		SHUTTLE,
		
		/**Dealer offers a valet service*/
		VALET,
		
		/**Dealer offers free coffee and snacks */
		COFFEE,
		
		/**Dealer offers free wifi*/
		WIFI,
		
		/**Dealer offers a lounge to wait in with TV*/
		LOUNGE,
		
		/**Dealer offers mutli-point inspections*/
		INSPECTION
	}
}
