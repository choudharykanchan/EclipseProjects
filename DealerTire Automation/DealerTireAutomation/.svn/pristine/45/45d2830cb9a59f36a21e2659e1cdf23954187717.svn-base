package com.dealertire.SMARTFramework;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.MDC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.dealertire.SMARTFramework.Environment.EnvironmentLevel;

/**
 * The base test class from which all test cases inherit
 * @author bgreen
 */
public class BaseTest {
	/**WebDriver instance*/
	protected WebDriver driver;
	
	private String remoteHost;
	
	/** The testing environment variables, for global access */
	public static Environment testEnvironment;
	
	/**
	 * The name of the browser
	 */
	protected String browserName;
		
	//Logging
	/** The current logger instance, so you can log details of test execution.*/
	protected static Logger logger;

	private static boolean fromCI;

	private static boolean useSauce;
	
	/**
	 * Default constructor. Initializes the logger, sets the remote host, and sets up the test environment.
	 * @param remoteHost The remote host to test against
	 * @param browser The browser to use
	 * @param version The version to test
	 */
	public BaseTest(String remoteHost, String browser, String version) {
		this.browserName = browser;
		logger = LogManager.getLogger(this.getClass().getSimpleName());
    	MDC.put("module", this.getClass().getSimpleName());
    	MDC.put("Browser", browser);
    	
    	this.remoteHost = remoteHost;
    	
    	testEnvironment = new Environment(EnvironmentLevel.DEMO,
    											EnvironmentLevel.DEMO,
    											"http://demosmart");
	}
	
	/**
	 * Get environment variables needed to run tests. Will read from the following command-line properties:
	 *  - remoteHost         A string documenting the remote host to use
	 *  - browserDatasheet   A csv file containing a list of browsers and versions to use
	 * @return A datasheet of environment variables.
	 * @throws IOException if the datasheet pass in the command-line arguments cannot be read
	 */
	public static ArrayList<String[]> getEnvironmentDatasheet() throws IOException {
		String remoteHost; 
		
		//Detect if we're using sauce
    	fromCI = System.getenv("SAUCE_BAMBOO_BUILDNUMBER") != null;
    	
    	//CI always uses sauce
    	useSauce = (fromCI ? true : System.getProperty("useSauce") != null);
		
		//Command-line override for remoteHost
		if (System.getProperty("remoteHost") ==  null) {
			if (useSauce) {
    			remoteHost = "http://" + System.getenv("SAUCE_USER_NAME") + ":" + System.getenv("SAUCE_API_KEY") 
    					+ "@ondemand.saucelabs.com:80/wd/hub";
    		} else {
    			remoteHost = "http://localhost:4444/wd/hub";
    		}
		} else {
			remoteHost = System.getProperty("remoteHost");
		}
		
		ArrayList<String[]> environmentVars = new ArrayList<String[]>();
		environmentVars.add(new String[]{remoteHost});
		
		//Command-line override for browsers
		ArrayList<String[]> browsers;
		
		if (System.getProperty("browserDatasheet") !=  null) {
			browsers = Utils.readFromCSV(System.getProperty("browserDatasheet"));
		} else {
			//defaults
			if (useSauce) {
				browsers = getBrowsersFromSauce();
			} else {
				browsers = new ArrayList<String[]>();
				browsers.add(new String[]{"IE","10"});
			}
		}
	
		return Utils.mergeDatasheets_cartesian(environmentVars, browsers);
	}
	
	private static ArrayList<String[]> getBrowsersFromSauce() {
		ArrayList<String[]> browsers = new ArrayList<String[]>();
		//read from bamboo_SAUCE_ONDEMAND_BROWSERS
		/*[{"platform":"VISTA","os":"Windows 2008","browser":"internet explorer","url":"sauce-ondemand:?os=Windows 2008&browser=internet explorer&browser-version=10","browser-version":"10"},
		 * {"platform":"VISTA","os":"Windows 2008","browser":"internet explorer","url":"sauce-ondemand:?os=Windows 2008&browser=internet explorer&browser-version=11","browser-version":"11"},
		 * {"platform":"VISTA","os":"Windows 2008","browser":"internet explorer","url":"sauce-ondemand:?os=Windows 2008&browser=internet explorer&browser-version=8","browser-version":"8"},
		 * {"platform":"VISTA","os":"Windows 2008","browser":"internet explorer","url":"sauce-ondemand:?os=Windows 2008&browser=internet explorer&browser-version=9","browser-version":"9"}]
		 * */
		
		JSONArray array=(JSONArray) JSONValue.parse(System.getenv("bamboo_SAUCE_ONDEMAND_BROWSERS"));
		for (Object object : array) {
			JSONObject config = (JSONObject) object;
			browsers.add(new String[] {config.get("browser").toString(), config.get("browser-version").toString()});	
		}
		return browsers;
	}
	
	/**
	 * Set up before each test. 
	 * This will create the connection to the RemoteWebDriver instance and request a driver.
	 * It will then delete the cookies and navigate to the home page. 
     * @throws IOException if there's an issue with the config file
	 */
	@Before
	public void setUpWebDriver() throws IOException {
			
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName("Internet Explorer");
		
		if (useSauce) {
        	capabilities.setCapability("name", "SMART Functional Testing: " + this.getClass().getSimpleName());
        }
		
        logger.info("Connecting to remote host at " + remoteHost);
        
		// Create a new instance of the driver
		try {
			driver = new RemoteWebDriver(new URL(remoteHost), capabilities);
		} catch (MalformedURLException e) {
			logger.fatal("Error creating remote web driver: " + e.getLocalizedMessage());
			throw e;
		}
		
		//Correct context
		MDC.put("Browser",  browserName + " "  + ((RemoteWebDriver) driver).getCapabilities().getVersion());
		
		//Put that cookie down!
		driver.manage().deleteAllCookies();
		
		//Output jobID for bamboo
		if (fromCI) {
			String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s",
					(((RemoteWebDriver) driver).getSessionId()).toString(), capabilities.getCapability("name"));
		    System.out.println(message);
		}
	}

	/**
	 * Runs after every test.
	 * This will close the browser window and exit the remote web driver session.
	 * @throws MalformedURLException 
	 */
	
	@After
	public void tearDown() throws InterruptedException {
		if (driver != null) {
			try{
				Thread.sleep(3000);
				driver.close();
				driver.quit();
			} catch  (WebDriverException e) {
				if (e.getMessage().equalsIgnoreCase("Not yet implemented")) {
					return;
				} else if (e.getCause() instanceof java.lang.UnsupportedOperationException) {
					return;
				} else {
					logger.fatal("Error closing remote web driver session: " + e.getLocalizedMessage());
					throw e;
				}
			}
		}
	}
	
	/** 
	 * Logging
	 */
	@Rule 
	public TestWatcher watchman = new TestWatcher() {
		@Override
		protected void succeeded(Description description) {
			logger.info("Test "+ description.getMethodName()  +" succeeded.");
		}

		@Override
		protected void failed(Throwable e, Description description) {
			logger.error("Test "+ description.getMethodName() + " failed with " + e.getMessage() + ".");
		}

		@Override
		protected void starting(Description description) {
			logger.info("Beginning Test " + description.getMethodName());
		}

	};
}