package com.dealertire.RightTurnFramework;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.Models.Product;
import com.dealertire.RightTurnFramework.Models.Size;
import com.dealertire.RightTurnFramework.Models.Vehicle;

/**
 * The manager for restoring sessions.
 * @author bgreen
 *
 */
public class SessionManager {
	
	private final static int page_index = 0;
	private final static int zip_index = 1;
	private final static int size_index = 2;
	private final static int year_index = 3;
	private final static int make_index = 4;
	private final static int model_index = 5;
	private final static int trim_index = 6;
	private final static int option_index = 7;
	private final static int prod_name_index = 8;
	private final static int prod_qty_index = 9;
	private final static int session_index = 10;
	private final static int vars_per_line = 10;
	
	/**
	 * Use sessions. Disabling this will result in no sessions being restored.
	 */
	public static boolean USE_SESSIONS = true;
	
	/**
	 * Restore a session without a vehicle or size.
	 * @param pageDesired The page you want to land on
	 * @param zipcode The zipcode. Null for any zipcode. 
	 * @param driver The webdriver to point at the page
	 * @return True if the session was restored. False if not.
	 */
	public static boolean restoreSession(String pageDesired, String zipcode, WebDriver driver) {
		if (!USE_SESSIONS) return false;
		
		ArrayList<String[]> csvValues = null;
		String filename = getCSV();
		try {
			csvValues = Utils.readFromCSV(filename);
		} catch (IOException e) {
			LogManager.getLogger(SessionManager.class.getSimpleName()).error("Could not read from CSV file: " + filename);
			return false;
		}
	
		for (String[] values : csvValues) {
			String page = values[page_index];
			String zip = values[zip_index];
			String session = values[session_index];
			
			if (page.trim().equalsIgnoreCase(pageDesired)
					&& (zip == null || zip.trim().equalsIgnoreCase(zipcode))
					) {
					driver.navigate().to(BaseTest.rootURL + "/app/" + page + "?" + session);
					if (driver.getCurrentUrl().equalsIgnoreCase(BaseTest.rootURL)) return false;
					return true;
			}		
		}
		return false;
	}
	


	/**
	 * Restore a session without products where it was searched by vehicle
	 * @param pageDesired The page you want to land on
	 * @param zipcode The zipcode. Null for any zipcode. 
	 * @param vehicle The vehicle to search for. 
	 * @param driver The webdriver to point at the page
	 * @return True if the session was restored. False if not.
	 */
	public static boolean restoreSession(String pageDesired, String zipcode, Vehicle vehicle, WebDriver driver) {
		if (!USE_SESSIONS) return false;
		
		ArrayList<String[]> csvValues = null;
		String filename = getCSV();
		try {
			csvValues = Utils.readFromCSV(filename);
		} catch (IOException e) {
			LogManager.getLogger(SessionManager.class.getSimpleName()).error("Could not read from CSV file: " + filename);
			return false;
		}
	
		for (String[] values : csvValues) {
			//page	zip	size	year	make	model	trim	option	sku	qty	session
			String page = values[page_index];
			String zip = values[zip_index];
			String year = values[year_index];
			String make = values[make_index];
			String model = values[model_index];
			String trim = values[trim_index];
			String option = values[option_index];
			String session = values[session_index];
			
			if (page.trim().equalsIgnoreCase(pageDesired)
					&& (zipcode == null || zip.trim().equalsIgnoreCase(zipcode))
					) {
				Vehicle actualVehicle = new Vehicle(year, make, model, trim, option);
				if (actualVehicle.equals(vehicle)) {
					driver.navigate().to(BaseTest.rootURL + "/app/" + page + "?" + session);
					return true;
				}
			}		
		}
		return false;
	}
	
	/**
	 * Restore a session without products where it was searched by vehicle
	 * @param pageDesired The page you want to land on
	 * @param zipcode The zipcode. Null for any zipcode. 
	 * @param sizeDesired The size to search for.
	 * @param driver The webdriver to point at the page
	 * @return True if the session was restored. False if not. 
	 */
	public static boolean restoreSession(String pageDesired, String zipcode, Size sizeDesired, WebDriver driver) {
		if (!USE_SESSIONS) return false;
		
		ArrayList<String[]> csvValues = null;
		String filename = getCSV();
		try {
			csvValues = Utils.readFromCSV(filename);
		} catch (IOException e) {
			LogManager.getLogger(SessionManager.class.getSimpleName()).error("Could not read from CSV file: " + filename);
			return false;
		}
	
		for (String[] values : csvValues) {
			//page	zip	size	year	make	model	trim	option	sku	qty	session
			String page = values[page_index];
			String zip = values[zip_index];
			String size = values[size_index];
			String session = values[session_index];
			
			if (page.trim().equalsIgnoreCase(pageDesired)
					&& (zip == null || zip.trim().equalsIgnoreCase(zipcode))
					) {
				Size actualSize = new Size(size);
				if (actualSize.equals(sizeDesired)) {
					driver.navigate().to(BaseTest.rootURL + "/app/" + page + "?" + session);
					return true;
				}
			}		
		}
		return false;
	}
	
	/**
	 * Restore a session without products where it was searched by vehicle
	 * @param pageDesired The page you want to land on
	 * @param zipcode The zipcode. Null for any zipcode. 
	 * @param vehicle The vehicle to search for. Null for any vehicle. 
	 * @param product The product to select. Null for any product
	 * @param qty The quantity of the product to select. Null for any quantity
	 * @param driver The webdriver to point at the page
	 * @return True if the session was restored. False if not.
	 */
	public static boolean restoreSession(String pageDesired, String zipcode, Vehicle vehicle, Product product, Integer qty, WebDriver driver) {
		if (!USE_SESSIONS) return false;
		
		ArrayList<String[]> csvValues = null;
		String filename = getCSV();
		try {
			csvValues = Utils.readFromCSV(filename);
		} catch (IOException e) {
			LogManager.getLogger(SessionManager.class.getSimpleName()).error("Could not read from CSV file: " + filename);
			return false;
		}
	
		for (String[] values : csvValues) {
			//page	zip	size	year	make	model	trim	option	sku	qty	session
			String page = values[page_index];
			String zip = values[zip_index];
			String year = values[year_index];
			String make = values[make_index];
			String model = values[model_index];
			String trim = values[trim_index];
			String option = values[option_index];
			String prodName = values[prod_name_index];
			String quantity = values[prod_qty_index];
			String session = values[session_index];
			
			if (page.trim().equalsIgnoreCase(pageDesired)
					&& (zipcode == null || zip.trim().equalsIgnoreCase(zipcode))
					&& (product == null || product.getName().equalsIgnoreCase(prodName))
					&& (qty == null || qty.toString().equalsIgnoreCase(quantity))
					) {
				Vehicle actualVehicle = new Vehicle(year.trim(), make.trim(), model.trim(), trim.trim(), (option.trim().isEmpty() ? null : option.trim()));
				if (vehicle == null || actualVehicle.equals(vehicle)) {
					driver.navigate().to(BaseTest.rootURL + "/app/" + page + "?" + session);
					return true;
				}
			}		
		}
		return false;
	}
	
	/**
	 * Save a session by vehicle
	 * @param page The page to save this under
	 * @param zipcode The zipcode used
	 * @param vehicle The vehicle used
	 * @param driver The webdriver to use to retrieve information
	 */
	public static void saveSession(String page, String zipcode, Vehicle vehicle, WebDriver driver) {
		saveSession(page, zipcode, vehicle, null, null, driver);
	}
	
	/**
	 * Save a session by vehicle. Used when saving sessions after the product page. 
	 * @param page The page to save this under
	 * @param zipcode The zipcode used
	 * @param vehicle The vehicle used
	 * @param product The product to have selected
	 * @param qty The quantity of the product to select.
	 * @param driver The webdriver to use to retrieve information
	 */
	public static void saveSession(String page, String zipcode, Vehicle vehicle, Product product, Integer qty, WebDriver driver) {
		if (!USE_SESSIONS) return;
		
		String[] vars = new String[vars_per_line+1];
		//Encode lookup vars
		vars[page_index] = page;
		vars[zip_index] = zipcode;
		vars[year_index] = vehicle.year;
		vars[make_index] = vehicle.make;
		vars[model_index] = vehicle.model;
		vars[trim_index] = vehicle.trim;
		vars[option_index] = vehicle.option;
		
		if (product != null) vars[prod_name_index] = product.getName();
		if (qty != null) vars[prod_qty_index] = qty.toString();
		
		//Get session token
		String currPage = driver.getCurrentUrl();
		driver.navigate().to(currPage + 
					(currPage.contains("?") ? "&" : "?")
					+ "gettoken"
				);
		
		Utils.WaitForAjax(driver);
		WebElement link = driver.findElement(By.id("getToken"));
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOf(link));
		Utils.ScrollElementIntoView(driver,link);
		link.click();
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*3).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.modal-body")));
		
		WebElement modal = driver.findElement(By.cssSelector("div.modal-body"));
		vars[session_index] = modal.getText().split("\\?",0)[1];
		System.err.println("DEBUG: Saving session token " + vars[session_index]);
		
		WebElement closebutton = driver.findElement(By.cssSelector("#RT_Modal button.close"));
		closebutton.click();
		Utils.WaitForAllAnimationComplete(driver);
		try {
			Utils.writeToCSV(getCSV(), vars);
		} catch (IOException e) {
			LogManager.getLogger(SessionManager.class.getSimpleName()).error("Could not save session to CSV! Error was: " + e.getLocalizedMessage());
		}
	}
	
	private static String getCSV() {
		switch (BaseTest.dataEnv) {
		case DEMO:
			return "demoSessions.csv";
		case DEV:
			return "devSessions.csv";
		case PROD:
			return "prodSessions.csv";
		case STAGE:
			return "prodSessions.csv";
		default:
			return null;
		}
	}
	

}
