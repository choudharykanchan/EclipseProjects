package SMART_SmokeTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.dealertire.SMARTFramework.BaseTest;
import com.dealertire.SMARTFramework.Utils;
import com.dealertire.SMARTFramework.Pages.DealerPage;
import com.dealertire.SMARTFramework.Pages.MyPage;

@RunWith(Parameterized.class)
public class MyPageSmokeTest extends BaseTest {

	private String dealerName;
	private String dealerCode;
	private MyPage myPage;
	private String dealerID;

	
	/**
	 * Parameter generation
	 * @return The parameters from the datasheet
	 * @throws IOException If the datasheet cannot be read
	 */
	@Parameters(name= "{index}: {3}")
    public static synchronized  Collection<String[]> parameters() throws IOException {
    	
    	ArrayList<String[]> allParams = null;
		try {
			ArrayList<String[]> dataSheet = Utils.readFromCSV("dealers.csv");
			allParams = Utils.mergeDatasheets_cartesian(getEnvironmentDatasheet(), dataSheet);
	    	
		} catch (IOException e) {
			fail("Could not read dealer list to test.");
		}
	
		return allParams;
    }
    
    /**
	 * Parameterized constructor for testing
	 * @param remoteHost The remote host to use
	 * @param browser The browser to use
	 * @param version The version to request
	 * @param dealerName The name of the dealer to use
	 * @param dealerCode The dealercode for that dealer
	 * @param dealerID the dealer ID for that dealer
	 */
	public MyPageSmokeTest(String remoteHost, String browser, String version, String dealerName, String dealerCode, String dealerID) {
		super(remoteHost, browser, version);
		this.dealerName = dealerName;
		this.dealerCode = dealerCode;
		this.dealerID = dealerID;
	}
	
	/**
	 * 1. Navigate to MyPage
	 * 2. Simple search full name of dealership
	 * 3. Simple search partial name of dealership
	 * 4. Simple search full dealer code
	 * 5. Simple search partial dealer code
	 * 6. Select a dealer
	 * 7. Edit a dealer
	 * 8. Verify dealer is populated in activity grid;
	 **/
	@Test
	public void myPageSearchandEdit() {
		//1. Navigate to MyPage
		logger.info("1. Navigating to My Page");
		myPage = new MyPage(driver);
		myPage.navigateTo();
		
		//2. Simple search full name of dealership
		logger.info("1. Searching by dealership name: '" + dealerName + "'");
		myPage.performDealerSimpleSearch(dealerName);
		
		logger.info("2. Verifying search results.");
		assertTrue("Dealership " + this.dealerID + " did not appear in search results",
				myPage.dealershipIsInResults(dealerID));
		
		//3. Simple search partial name of dealership
		String partialName = dealerName.substring(0, dealerName.length()/2);
		logger.info("3a. Searching by partial dealership name: '" + partialName + "'");
		myPage.performDealerSimpleSearch(partialName);
		
		logger.info("3b. Verifying search results.");
		assertTrue("Dealership " + this.dealerID + " did not appear in search results",
				myPage.dealershipIsInResults(dealerID));
		
		//4. Simple search full dealer code
		logger.info("4a. Searching by dealership code: '" + dealerCode + "'");
		myPage.performDealerSimpleSearch(dealerCode);
		
		logger.info("4b. Verifying search results.");
		assertTrue("Dealership " + this.dealerID + " did not appear in search results",
				myPage.dealershipIsInResults(dealerID));
		
		//5. Simple search partial dealer code
		String partialCode = dealerCode.substring(0, 4);
		logger.info("5a. Searching by partial dealership code: '" + partialCode + "'");
		myPage.performDealerSimpleSearch(partialCode);
		
		logger.info("5b. Verifying search results.");
		assertTrue("Dealership " + this.dealerID + " did not appear in search results",
				myPage.dealershipIsInResults(dealerID));
		
		
		//6. Select a dealer
		logger.info("6a. Searching by dealership code: '" + dealerCode + "'");
		myPage.performDealerSimpleSearch(dealerCode);
		
		logger.info("6b. Clicking dealership.");
		myPage.clickDealershipInSearchResults(dealerID);
		
		logger.info("6c. Verifying selection.");
		assertTrue("Dealer ID was not selected!",
				myPage.getSelectedDealerSearchResults().contains(dealerID));
		
		//7. Edit a dealer
		logger.info("7a. Searching by dealership code: '" + dealerCode + "'");
		myPage.performDealerSimpleSearch(dealerCode);
		
		logger.info("7b. Clicking dealership Name.");
		DealerPage dealerPage = myPage.clickDealerNameInSearchResults(dealerID);
		
		logger.info("7c. Verifying navigation.");
		assertTrue("Dealer page did not load!",
				dealerPage.isLoaded());
		
		assertEquals(dealerID, dealerPage.getDealerIDDisplayed());
		
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    