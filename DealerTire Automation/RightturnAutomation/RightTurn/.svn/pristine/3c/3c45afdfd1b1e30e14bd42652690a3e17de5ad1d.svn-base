package com.dealertire.RightTurnTesting.experimental;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.dealertire.RightTurnFramework.BaseTest;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;
import com.dealertire.RightTurnFramework.Pages.HomePage;
import com.dealertire.RightTurnFramework.Pages.LocationPage;
import com.dealertire.RightTurnFramework.Pages.ProductPage;
import com.dealertire.RightTurnFramework.Pages.TireCoachPage;
import com.dealertire.RightTurnFramework.Pages.VehiclePage;
import com.dealertire.RightTurnFramework.Pages.VerifyPage;

/**
 * Tests to verify that links are valid. This attempts to click every link in a section and fails if there's any 
 * that break the rules of links or return 404s. 
 * WARNING: These tests are experimental. They have been commented out to remove them from the test suite.
 * If you want to work with them, uncomment the @Test annotations.
 * @author bgreen
 */
@SuppressWarnings("unused")
public class LinkCheckTests extends BaseTest {


	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public LinkCheckTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
	}	
	
	/**
	 * Test all home page links to verify that they point to valid resources. 
	 * This does not validate that they go to the correct resource, 
	 * just a valid one.
	 */
	//@Test
	public void linkCheckHomePage() {
		driver.navigate().to(rootURL);
		homePage = (HomePage) PageFactory.initElements(driver, HomePage.class);
		assertTrue(verifyLinks(homePage.getAllLinksOnPage()));
	}
	
	/**
	 * Test all location page links to verify that they point to valid resources. 
	 * This does not validate that they go to the correct resource, 
	 * just a valid one.
	 */
	//@Test
	public void linkCheckLocationPage() {
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		assertTrue(verifyLinks(locationPage.getAllLinksOnPage()));
	}
	
	/**
	 * Test all links in the About section to verify that they point to valid resources. 
	 * This does not validate that they go to the correct resource, 
	 * just a valid one.
	 */
	//@Test
	public void linkCheckAboutSection() {
		List<WebElement> links;
		boolean shouldFail;
		
		//Main about-us page
		driver.navigate().to(rootURL + "/about/about-us");
		links = driver.findElements(By.tagName("a"));
		shouldFail = verifyLinks(links);
		
		//History
		driver.navigate().to(rootURL + "/about/history");
		links = driver.findElements(By.tagName("a"));
		if (!verifyLinks(links)) shouldFail = true;
		
		//Mission
		driver.navigate().to(rootURL + "/about/mission");
		links = driver.findElements(By.tagName("a"));
		if (!verifyLinks(links)) shouldFail = true;
		
		//Team
		driver.navigate().to(rootURL + "/about/team");
		links = driver.findElements(By.tagName("a"));
		if (!verifyLinks(links)) shouldFail = true;
		
		//Careers
		driver.navigate().to(rootURL + "/about/careers");
		links = driver.findElements(By.tagName("a"));
		if (!verifyLinks(links)) shouldFail = true;
		
		//Press
		driver.navigate().to(rootURL + "/about/press-releases");
		links = driver.findElements(By.tagName("a"));
		if (!verifyLinks(links)) shouldFail = true;
		
		//Testimonials
		driver.navigate().to(rootURL + "/about/testimonials");
		links = driver.findElements(By.tagName("a"));
		if (!verifyLinks(links)) shouldFail = true;
		
		//Contact
		driver.navigate().to(rootURL + "/about/contact-us");
		links = driver.findElements(By.tagName("a"));
		if (!verifyLinks(links)) shouldFail = true;
		
		//Privacy
		driver.navigate().to(rootURL + "/about/privacy");
		links = driver.findElements(By.tagName("a"));
		if (!verifyLinks(links)) shouldFail = true;
		
		//Terms
		driver.navigate().to(rootURL + "/about/terms");
		links = driver.findElements(By.tagName("a"));
		if (!verifyLinks(links)) shouldFail = true;
		
		//Secure
		driver.navigate().to(rootURL + "/about/secure");
		links = driver.findElements(By.tagName("a"));
		if (!verifyLinks(links)) shouldFail = true;
		
		//Guarantee
		driver.navigate().to(rootURL + "/about/guarantee");
		links = driver.findElements(By.tagName("a"));
		if (!verifyLinks(links)) shouldFail = true;
		
		if (shouldFail) {
			fail("Errors were found in links.");
		}
	}
	
	/**
	 * Test all vehicle page links to verify that they point to valid resources. 
	 * This does not validate that they go to the correct resource, 
	 * just a valid one.
	 */
	//@Test
	public void linkCheckVehiclePage() {
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		assertTrue(verifyLinks(vehiclePage.getAllLinksOnPage()));
	}
	
	/**
	 * Test all verify page links to verify that they point to valid resources. 
	 * This does not validate that they go to the correct resource, 
	 * just a valid one.
	 */
	//@Test
	public void linkCheckVerifyPage() {
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		assertTrue(verifyLinks(verifyPage.getAllLinksOnPage()));
	}
	
	/**
	 * Test all tire coach page links to verify that they point to valid resources. 
	 * This does not validate that they go to the correct resource, 
	 * just a valid one.
	 */
	//@Test
	public void linkCheckTireCoachPage() {
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		assertTrue(verifyLinks(tireCoachPage.getAllLinksOnPage()));
	}
	
	/**
	 * Test all product page links to verify that they point to valid resources. 
	 * This does not validate that they go to the correct resource, 
	 * just a valid one.
	 */
	//@Test
	public void linkCheckProductPage() {
		locationPage = (LocationPage) PageFactory.initElements(driver, LocationPage.class).navigateTo();
		locationPage.enterZipCode("44114");
		vehiclePage = (VehiclePage) locationPage.clickNext();
		
		Map<Criteria, Boolean> criteriaList = new HashMap<Criteria, Boolean>();
		criteriaList.put(Criteria.MULTISIZE, false);
		Vehicle vehicle = Vehicle.getRandomVehicleByCriteria(criteriaList);
		logger.info("Using vehicle " + vehicle);
		
		vehiclePage.selectVehicle(vehicle);
		verifyPage = (VerifyPage) vehiclePage.clickNext();
		
		tireCoachPage = (TireCoachPage) verifyPage.clickNext();
		productPage = (ProductPage) tireCoachPage.clickNext();
		assertTrue(verifyLinks(productPage.getAllLinksOnPage()));
	}

	/**
	 * Verify that all links are valid
	 * @param links The list of links to validate
	 * @return false if any errors are found, otherwise true
	 */
	private boolean verifyLinks(List<WebElement> links) {
		boolean foundErrors = false;
		
		for (WebElement link : links) {
			String url = link.getAttribute("href");
			if (!isLinkOK(url, link.getText())) foundErrors = true;
		}
		
		return !foundErrors;
	}
	
	/**
	 * Checks if a given link is okay. If you want to add additional rules for all links, add them here.
	 * @param url The URL to check
	 * @param linkText The text of the link, for pretty error messages
	 * @return true if the link is okay, false if it is not.
	 */
	private boolean isLinkOK(String url, String linkText) {
		try {
			if (url == null) {
				logger.warn("Empty href attribute on link for " + linkText + " on page "  + driver.getCurrentUrl());
				return true;
			}
			if (url.startsWith("http")){
				Integer responseCode = getResponseCode(url);
				if (! (responseCode == 200 || responseCode.toString().startsWith("3"))) {
					logger.error("Page " + url + " responded with " + responseCode);
					return false;
				}
			} else if (url.startsWith("tel")) {
				if (!url.contains(linkText)) {
					logger.error("Potentially misleading phone number: " + linkText + " does not match " + url + " on page "  + driver.getCurrentUrl());
					return false;
				}
			} else if (url.startsWith("mailto:")) {
				//Basic mail checks
				if (!url.contains("@") || !url.contains(".")) {
					logger.error("Invalid email address: " + url + " on page "  + driver.getCurrentUrl());
					return false;
				}
				if (!url.toUpperCase().contains("RIGHTTURN.COM") && !url.toUpperCase().contains("DEALERTIRE.COM")) {
					logger.error("Linked to uncontrolled email address " + url + " on page "  + driver.getCurrentUrl());
					return false;
				}
			} else if (url.trim().equalsIgnoreCase("javascript:void(0);")
					|| url.trim().equalsIgnoreCase("javascript:void(0)") ) {
				return true;
			}else {
				logger.warn("Did not know how to handle link " + linkText + " with url "+ url + " on page "  + driver.getCurrentUrl());
				return false;
			}
			
		} catch (IOException e) {
			logger.error("Error checking link " + linkText + ": " + e.getMessage() + " on page "  + driver.getCurrentUrl());
			return false;
		}
		
		return true;
	}
	
	/**
	 * From StackOverflow: http://stackoverflow.com/questions/1378199/how-to-check-if-a-url-exists-or-returns-404-with-java
	 * @param urlString The URL to check
	 * @return The response code for that URL
	 * @throws MalformedURLException If the URL is malformed
	 * @throws IOException  if an I/O error occurs while opening the connection.
	 */
	private static int getResponseCode(String urlString) throws MalformedURLException, IOException {
	    URL u = new URL(urlString); 
	    HttpURLConnection huc =  (HttpURLConnection)  u.openConnection(); 
	    huc.setRequestMethod("GET"); 
	    huc.connect(); 
	    return huc.getResponseCode();
	}

}
