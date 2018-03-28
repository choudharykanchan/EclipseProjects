package com.dealertire.RightTurnFramework;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.log4j.MDC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dealertire.RightTurnFramework.Models.Dealer;
import com.dealertire.RightTurnFramework.Models.Size;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Pages.CheckoutPage;
import com.dealertire.RightTurnFramework.Pages.ComparePage;
import com.dealertire.RightTurnFramework.Pages.HomePage;
import com.dealertire.RightTurnFramework.Pages.InstallPage;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductDetailsPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;
import com.dealertire.RightTurnFramework.Pages.WinterTireQuestionPage;
import com.dealertire.RightTurnFramework.Utils.Environment;
import com.dealertire.RightTurnFramework.Pages.SchedulePage;
import com.dealertire.RightTurnFramework.Pages.Modals.MapModal;
import com.dealertire.RightTurnFramework.Pages.Modals.PUWYLOModal;
import com.saucelabs.saucerest.SauceREST;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.junit.Parallelized;


/**
 * This is the base class for all Right Turn test classes. All test classes should extend from here.
 * @author bgreen
 *
 */
@RunWith(Parallelized.class)
//@RunWith(Parameterized.class)
public abstract class BaseTest {
	private String browserName;
	private String version;
	private String platform;
	private static volatile String remoteHost; 
	private static volatile String environmentFilename;
	
	/** The webdriver reference, for use in the tests */
	protected WebDriver driver;
	
	/** The environment we're in, in case you need to know. */
	protected static volatile Environment dataEnv;
	
	/** The rootURL; use this to return to the home page */
	protected static volatile String rootURL;
	
	//PageObjects
	/** A reference to the home page. Please lazy-populate this when you navigate. */
	protected HomePage homePage;
	/** A reference to the location page. Please lazy-populate this when you navigate. */
	protected LocationPage locationPage;
	/** A reference to the vehicle selection page. Please lazy-populate this when you navigate. */
	protected VehiclePage vehiclePage;
	/** A reference to the vehicle verification page. Please lazy-populate this when you navigate. */
	protected VerifyPage verifyPage;
	/** A reference to the tire coach page. Please lazy-populate this when you navigate. */
	protected TireCoachPage tireCoachPage;
	/** A reference to the tire coach "Do you need winter tires?" page. Please lazy-populate this when you navigate. */
	protected WinterTireQuestionPage winterTireQuestionPage;
	/** A reference to the product list page. Please lazy-populate this when you navigate. */
	protected ProductPage productPage;
	/** A reference to the product details page. Please lazy-populate this when you navigate. */
	protected ProductDetailsPage detailsPage;
	/** A reference to the product comparison page. Please lazy-populate this when you navigate. */
	protected ComparePage comparePage;
	/** A reference to the installer page. Please lazy-populate this when you navigate. */
	protected InstallPage installPage;
	/**A reference to the schedule page. Pleas lazy-populate this when you navigate */
	protected SchedulePage schedulePage;
	/**A reference to the schedule page. Pleas lazy-populate this when you navigate */
	protected CheckoutPage checkoutPage;
	
	/**A reference to the PUWYLO modal. Please lazy-populate this on the page you want to check modals.*/
	protected PUWYLOModal puwyloModal;
	/**A reference to the map modal. Please lazy-populate this on the page you want to check modals.*/
	protected MapModal mapModal;
	
	//Sauce variables
	/** Use sauce labs */
	protected volatile static boolean useSauce;
	private String sessionID;
	private SauceREST sauceREST;
	static volatile String sauceUser;
	static volatile String sauceKey;

	private static File outputFile;
	
	//Logging
	/** The current logger instance, so you can log details of test execution.*/
	protected static Logger logger;
	
	
	/**
	 * Generates list of environments to use for the test. This allows cross-browser testing with jUnit's paramaterization features.
	 * @return The list of environments to pass to the constructor.
	 * @throws IOException if the config file goes missing
	 */
	@Parameters(name= "{index}: {1} {2}")
    public static synchronized  Collection<String[]> environmentsToUse() throws IOException {
    	if (remoteHost == null) loadConfigFile();
    	
    	try {
			return Utils.readFromCSV(environmentFilename);
		} catch (IOException e) {
			// fallback
			if (logger == null) {
				logger = LogManager.getLogger(BaseTest.class.getSimpleName());
			}
			logger.fatal("Could not load environment CSV file at " + environmentFilename + ", falling back to default config.");
			logger.fatal("Message was: " + e.getLocalizedMessage());
			LinkedList<String[]> environments = new LinkedList<String[]>();
			environments.add(new String[]{"WINDOWS", "firefox", null});
			environments.add(new String[]{"WINDOWS", "chrome", null});
			return environments;	
		}
    }
    
    private static synchronized void loadConfigFile() throws IOException {
    	String environment;
    	rootURL = "http://www.rightturn.com";
    	Logger logger = LogManager.getLogger("BaseTest");
    	
    	/* Read environment from config file */
    	Properties configFile = new Properties();
    	String parentDirectory = java.net.URLDecoder.decode(BaseTest.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "utf-8");
    	try {
    		String configfilename = System.getProperty("config");
    		File config;
    		if (configfilename == null) {
	    		//Get file
	    		File parentDir = new File(parentDirectory);
	        	if (parentDirectory.endsWith(".jar")) {
	        		//Look outside the jar
	        		parentDir = parentDir.getParentFile();
	        	}
	        	
	        	//Because of this newfangled Convention over Configuration, 
	        	//Maven will rename the config.properties file
	        	//to include a string which includes the version of the product.
	        	//It will still end in config.properties
	        	//so we use a wildcard to find it.
	    		FileFilter fileFilter = new WildcardFileFilter("*config.properties");
	    		File[] files = parentDir.listFiles(fileFilter);
	    	
	    		if (files == null || files.length <= 0 || files[0] == null) {
	    			logger.fatal("Could not find a config file at " + parentDir);
	    			throw new IllegalStateException("No config file found.");
	    		}
	    		config = files[0];
    		} else {
    			config = new File(configfilename);
    		}
    		
    		configFile.load(new FileInputStream(config));
    		
    		//Environment
    		environment = System.getProperty("environment");
    		if (environment == null) {
    			environment = configFile.getProperty("environment");
    		}
    		
    		//rootURL
    		rootURL = System.getProperty("rootURL");
    		if (rootURL == null) {
    			rootURL = configFile.getProperty("rootURL");
    		}
    		
    		//useSauce
    		if (System.getProperty("useSauce") == null) {
    			useSauce = Boolean.valueOf(configFile.getProperty("useSauce"));
    		} else {
    			useSauce = Boolean.valueOf(System.getProperty("useSauce"));
    		}
    		
    		//useSauce
    		boolean useSessions;
    		if ((System.getProperty("useSessions") == null || System.getProperty("useSessions").isEmpty()) 
    			&& (configFile.getProperty("useSessions") == null || configFile.getProperty("useSessions").isEmpty())) {
    			useSessions = true; //default to on
    		}
    		else if (System.getProperty("useSessions") == null) {
    			useSessions = Boolean.valueOf(configFile.getProperty("useSessions"));
    		} else {
    			useSessions = Boolean.valueOf(System.getProperty("useSessions"));
    		}
    		
    		if  (!useSessions) {
    			SessionManager.USE_SESSIONS = false;
    		}
    		
    		//environments
    		environmentFilename = System.getProperty("environments");
    		if (environmentFilename == null) {
    			environmentFilename = configFile.getProperty("environments");
    		}
    		environmentFilename +=".csv"; 
    		
    		//Sauce properties
			if (useSauce) {
				sauceUser = System.getProperty("SauceUserName");
	    		if (sauceUser == null) {
	    			sauceUser = configFile.getProperty("SauceUserName");
	    		}
	    		
	    		sauceKey = System.getProperty("SauceAccessKey");
	    		if (sauceKey == null) {
	    			sauceKey = configFile.getProperty("SauceAccessKey");
	    		}
	    		
	    		remoteHost = System.getProperty("SauceRemoteHost");
	    		if (remoteHost == null) {
	    			remoteHost = configFile.getProperty("SauceRemoteHost");
	    		}
	    		
			} else {
				remoteHost = System.getProperty("remoteHost");
	    		if (remoteHost == null) {
	    			remoteHost = configFile.getProperty("remoteHost");
	    		}
			}
			//data location
			Utils.dataLocation = System.getProperty("dataLocation");
    		if (Utils.dataLocation == null) {
    			Utils.dataLocation = configFile.getProperty("dataLocation");
    		}
    		
		} catch (IOException e) {
			logger.fatal("Could not load config file at " + parentDirectory + ": " + e.getLocalizedMessage());
			throw e;
		}	
    	
    	
		//dataEnv
		if (environment.equalsIgnoreCase("prod") || environment.equalsIgnoreCase("production")) {
    		dataEnv = Environment.PROD;
    	} else if (environment.equalsIgnoreCase("staging") || environment.equalsIgnoreCase("stage")) {
    		dataEnv = Environment.STAGE;
    	} else if (environment.equalsIgnoreCase("demo") || environment.equalsIgnoreCase("qa")) {
    		dataEnv = Environment.DEMO;
    	} else if (environment.equalsIgnoreCase("dev") || environment.equalsIgnoreCase("development")) {
    		dataEnv = Environment.DEV;
    	} else {
    		dataEnv = Environment.DEMO;
    	}
			
		//Output file
		//Log output
		String outputFilename = System.getProperty("outputFile");
		if (outputFilename == null) {
			outputFilename = configFile.getProperty("outputFile");
		}
		
		if (outputFilename != null) {
			outputFile = new File(outputFilename);
	        Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			long yesterday = cal.getTimeInMillis();
			
			if (outputFile.exists() && (outputFile.lastModified() <= yesterday)) {
				logger.info("Deleting existing output file, as it is outdated.");
				try {
					outputFile.delete();
				} catch (SecurityException e) {
					logger.error("Could not delete old output file, output file may be invalid");
				}
			}
			
			if (!outputFile.exists()) {
				outputFile.createNewFile();
			}
		}
    }
    
    /**
     * Base constructor, for parameterized testing.
     * @param os The operating system to use
     * @param browserName The name of the browser to request
     * @param browserVersion The version of the browser to request
     */
    public BaseTest(String os, String browserName, String browserVersion) {
    	this.browserName = browserName;
    	this.platform = os;
    	this.version = browserVersion;
    	
    	if (!useSauce && os.toUpperCase().startsWith("WINDOWS")) {
    		this.platform = os = "WINDOWS";
    	}
    	
    	MDC.put("OS", os);
    	MDC.put("Browser", browserName + " " + (browserVersion == null ? "*" : browserVersion));
    	
    	logger = LogManager.getLogger(this.getClass().getSimpleName());
    	MDC.put("module", this.getClass().getSimpleName());
    	
    }

	
	/**
	 * Set up before each test. 
	 * This will create the connection to the RemoteWebDriver instance and request a driver.
	 * It will then delete the cookies and navigate to the home page. 
     * @throws IOException if there's an issue with the config file
	 */
	@Before
	public void setUp() throws IOException {
			
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(browserName);
		if (version != null && !version.equalsIgnoreCase("any")) {
            capabilities.setCapability(CapabilityType.VERSION, version);
        }
        capabilities.setCapability(CapabilityType.PLATFORM, platform);
        if (useSauce) {
        	capabilities.setCapability("name", "Right Turn Functional Testing: " + this.getClass().getSimpleName());
        	capabilities.setCapability("parent-tunnel", "dbichsel_dealertire");
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
		if (version == null || version.equalsIgnoreCase("any")) {
			this.version = ((RemoteWebDriver) driver).getCapabilities().getVersion();
			MDC.put("Browser", browserName + " " + this.version);
		}
		
		//Configure sauce reporting.
		if (useSauce) {
    		sauceREST = new SauceREST(sauceUser, sauceKey);
    		sessionID = (((RemoteWebDriver) driver).getSessionId()).toString();
    		
    		 //Output jobID for bamboo
      		String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s",
      				sessionID, capabilities.getCapability("name"));
      	    System.out.println(message);
    	}
		
		//Load data for test
        Vehicle.loadData(dataEnv);
        Dealer.loadData(dataEnv);
        Size.loadData(dataEnv);
    
    
		//Put that cookie down!
		driver.manage().deleteAllCookies();
		driver.navigate().to(rootURL + "?login&resetVersion=default"); //Disables A/B testing and clears session tokens
	}
	

	/**
	 * Runs after every test.
	 * This will close the browser window and exit the remote web driver session.
	 */
	@After
	public void tearDown() {
		if (driver != null) {
			try{
				driver.close();
				driver.quit();
			} catch (UnhandledAlertException e) {
				Alert alert = driver.switchTo().alert();
				alert.dismiss();
				driver.switchTo().defaultContent();
				tearDown();//Retry
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
		long starttime; 
		String passed = "Not Run";
		
		@Override
		protected void succeeded(Description description) {
			passed = "Pass";
			logger.info("Test "+ description.getMethodName()  +" succeeded.");
			
			if (useSauce) {
				Map<String, Object> updates = new HashMap<String, Object>();
	            updates.put("passed", true);
	            com.saucelabs.common.Utils.addBuildNumberToUpdate(updates);
	            sauceREST.updateJobInfo(sessionID, updates);
			}			
		}

		@Override
		protected void failed(Throwable e, Description description) {
			passed = "Fail";
			logger.error("Test "+ description.getMethodName() + " failed with " + e.getMessage() + ".");
			
			if (useSauce) {
				Map<String, Object> updates = new HashMap<String, Object>();
	            updates.put("passed", false);
	            com.saucelabs.common.Utils.addBuildNumberToUpdate(updates);
	            sauceREST.updateJobInfo(sessionID, updates);
			}
		}

		@Override
		protected void starting(Description description) {
			logger.info("Beginning Test " + description.getMethodName());
			starttime = System.currentTimeMillis();
		}
		
		@Override
		protected void finished(Description description) {
			long endtime = System.currentTimeMillis();
			long duration = (endtime - starttime) / 1000;
			
			String suiteName = description.getClassName();
			suiteName = suiteName.substring(suiteName.lastIndexOf ('.') + 1); //just the name, thanks
			
			String testName = description.getMethodName();
			testName = testName.substring(0, testName.indexOf('['));
			
			String browser = description.getMethodName();
			browser = browser.substring(browser.indexOf(':') + 2, browser.lastIndexOf(']'));
			
			if (outputFile != null) {
				try(FileWriter writer = new FileWriter(outputFile, true);) {
					writer.write(suiteName + "," + testName +  "," + browser + "," + passed + "," + duration + "\n");
				} catch (IOException e) {
					logger.error("Failed to write to output file! " + e.getLocalizedMessage());
				}
			}
		}

	};
}
