package com.dealertire.RightTurnFramework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


/**
 * Utility methods that don't have a better place to live.
 * @author bgreen
 */
public class Utils {
	
	/**
	 * The location to look for data scripts. Added in 2.0. BaseTest sets this when it reads the config file.
	 */
	public static String dataLocation = "";
	/**
	 * Wait for AJAX to complete. Requires jQuery. This will return when jQuery.active is false.
	 * @param driver The driver to hold up
	 */
	public static void WaitForAjax(final WebDriver driver)
	{
		 Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
				    .withTimeout(60, TimeUnit.SECONDS);
				 
				 
		  wait.until( new ExpectedCondition<Boolean>() { 
		    @Override 
		    public Boolean apply( WebDriver webDriver ) {
		    	return (boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");
		    }
		  } );
	
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
		    @Override 
		    public Boolean apply( WebDriver webDriver ) {
		    	return !(driver.getCurrentUrl().equalsIgnoreCase(oldURL));
		    }
		  } );
	}
	
	/**
	 * Wait for animation to finish. Requires jQuery
	 * @param driver The driver to hold up
	 * @param elementLocator The locator for the element that is animated. Should be jQuery understandable, so a CSS locator
	 */
	public static void WaitForJQueryAnimationCompletion(final WebDriver driver, final String elementLocator)
	{
		 Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
				    .withTimeout(60, TimeUnit.SECONDS);
				 
				 
		  wait.until( new ExpectedCondition<Boolean>() { 
		    @Override 
		    public Boolean apply( WebDriver webDriver ) {
		    	return !(boolean) ((JavascriptExecutor) driver).executeScript("return jQuery('" + elementLocator + "').is(':animated')");
		    }
		  } );
	
	}
	
	/**
	 * Wait for animation to finish. Requires jQuery
	 * @param driver The driver to hold up
	 */
	public static void WaitForAllAnimationComplete(final WebDriver driver)
	{
		 Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
				    .withTimeout(60, TimeUnit.SECONDS);
				 
				 
		  wait.until( new ExpectedCondition<Boolean>() { 
		    @Override 
		    public Boolean apply( WebDriver webDriver ) {
		    	return (boolean) ((JavascriptExecutor) driver).executeScript("return $(':animated').length == 0");
		    }
		  } );
	
	}

	/** The testing environment*/
	public enum Environment {
		/**Dev, shared or developer instance*/
		DEV,
		
		/**Demo*/
		DEMO,
		
		/**Staging*/
		STAGE,
		
		/**Production*/
		PROD
	}
	
	/**
	 * Read variables out of a CSV and return the raw data, ready to be fed to an object constructor
	 * @param filename The file to read
	 * @return The list of variable sets
	 * @throws IOException If there's trouble reading from the file.
	 */
	public static ArrayList<String[]> readFromCSV(String filename) throws IOException {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		
		File file = new File(dataLocation + File.separatorChar + filename);
		//InputStream is = Utils.class.getClassLoader().getResourceAsStream(filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
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
	 * Append a line to a CSV, saving it for future reads
	 * @param filename The filename to write to
	 * @param vars The line, as an array of variables to implode 
	 * @throws IOException If there's trouble writing to the file.
	 */
	public static void writeToCSV(String filename, String[] vars) throws IOException {
		String line = StringUtils.join(vars,",");
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(dataLocation + "\\" + filename, true)))) {
		    out.println(line);
		    out.flush();
		}
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
	 * Click using Javascript. Use whenever clicking fails reliably. 
	 * @param element The element to click
	 * @param driver The driver to use to do the clicking.
	 */
	public static void clickUsingJavascript(WebElement element, WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
	}
}
