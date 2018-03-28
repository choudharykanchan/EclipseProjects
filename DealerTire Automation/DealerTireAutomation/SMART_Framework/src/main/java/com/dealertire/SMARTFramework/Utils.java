package com.dealertire.SMARTFramework;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.dealertire.SMARTFramework.Pages.ActivityPage;


/**
 * Utility functions.
 * @author bgreen
 *
 */
public class Utils {
	/**
	 * Read variables out of a CSV and return the data.
	 * @param filename The file to read
	 * @return The list of variable sets
	 * @throws IOException If there's trouble reading from the file.
	 */
	public static ArrayList<String[]> readFromCSV(String filename) throws IOException {
		ArrayList<String[]> lines = new ArrayList<String[]>();

		InputStream is = Utils.class.getClassLoader().getResourceAsStream(filename);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		
		//Skip line one, it has headers
		line = br.readLine();
		
		while ((line = br.readLine()) != null) {
			String[] variables = line.split(",", -1);
			for (int i = 0; i < variables.length; i++) {
				//clean up CSV formatting
				String token = variables[i];
				if (token.startsWith("\"")) {
					token = token.substring(1, token.length()-1); //chop off quotes
					//quotes are added when a " has to be escaped, so remove escaping
					variables[i] = token.replaceAll("\"\"", "\"");
				}
			}
			lines.add(variables);
		}

		br.close();
		return lines;
	}
	
	/**
	 * Cartesian merge two datasheets. This results in a new datasheet with every single line of the old file combined with each line of the new file.
	 * It will be Size(file1) * Size(file2) long. 
	 * @param file1 The first file
	 * @param file2 The second file
	 * @return The cartesian product. 
	 */
	public static ArrayList<String[]> mergeDatasheets_cartesian(ArrayList<String[]> file1, ArrayList<String[]> file2) {
		ArrayList<String[]> crossProduct = new ArrayList<String[]>();
		for (String[] line1 : file1) {
			for (String[] line2 : file2) {
				String[] newLine = ArrayUtils.addAll(line1, line2);
				crossProduct.add(newLine);
			}
		}
		
		return crossProduct;
	}
	
	/**
	 * Wait for AJAX to complete. Requires jQuery. This will return when jQuery.active is false.
	 * @param driver The driver to hold up
	 */
	public static void WaitForAjax(final WebDriver driver)
	{
		 Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
				    .withTimeout(120, TimeUnit.SECONDS);
				 
				 
		  wait.until( new ExpectedCondition<Boolean>() { 
		    public Boolean apply( WebDriver webDriver ) {
		    	return (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");
		    }
		  } );
	
	}
	
	/**
	 * Scroll the viewpoint to make a certain element visible. This should happen automatically before clicking, but sometimes it can be fiddly. 
	 * @param driver The webdriver to scroll
	 * @param element The element to make visible.
	 */
	public static void ScrollElementIntoView(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	/**
	 * Click at a given point
	 * @param p The point to click at
	 * @param driver The driver to use when clicking
	 */
	public static void clickAtPoint(Point p, WebDriver driver) {
		WebElement topLeftCorner = driver.findElement(By.tagName("body"));
		Actions builder = new Actions(driver);
		Action action = builder
		    .moveToElement(topLeftCorner, p.x, p.y)
		    .click()
		    .build();
		action.perform();
	}
	
	/**
	 * Drag and drop action
	 * @param from The element to drag
	 * @param to The element on which to drop
	 * @param driver The driver to use when dragging
	 */
	public static void dragAndDrop(WebElement from, WebElement to, WebDriver driver) {
		Actions builder = new Actions(driver);
		Action action = builder
		    .moveToElement(from)
		    .clickAndHold()
		    .moveToElement(to)
		    .release()
		    .build();
		action.perform();
	}
	
	/**
	 * Drag and drop action
	 * @param from The element to drag
	 * @param to The element on which to drop
	 * @param driver The driver to use when dragging
	 */
	public static void dragAndDrop(WebElement from, Point to, WebDriver driver) {
		WebElement topLeftCorner = driver.findElement(By.tagName("body"));
		Actions builder = new Actions(driver);
		Action action = builder
		    .moveToElement(from)
		    .clickAndHold()
		    .moveToElement(topLeftCorner, to.x, to.y)
		    .release()
		    .build();
		action.perform();
	}

	/**
	 * Wait for the URL to change. This can be used when WebDriver does not pick up a page transition happening for whatever reason.
	 * @param driver The driver to watch
	 * @param oldURL The URL that we want to no longer be present by the end of the wait.
	 */
	public static void WaitForUrlChange(final WebDriver driver, final String oldURL) {
		 Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
		    .withTimeout(60, TimeUnit.SECONDS);
		 
		 
		  wait.until( new ExpectedCondition<Boolean>() {   
		    public Boolean apply( WebDriver webDriver ) {
		    	return !(driver.getCurrentUrl().equalsIgnoreCase(oldURL));
		    }
		  } );
	}
	
	/**
	 * Click using Javascript. Use whenever clicking fails reliably. 
	 * @param element The element to click
	 * @param driver The driver to use to do the clicking.
	 */
	public static void clickUsingJavascript(WebElement element, WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
	}
	
}
