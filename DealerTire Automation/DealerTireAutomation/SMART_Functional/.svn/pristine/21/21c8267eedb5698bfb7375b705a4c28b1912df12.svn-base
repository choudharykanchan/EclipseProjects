package SMART_SmokeTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;







//import org.apache.bcel.generic.Select;
//import org.apache.bcel.generic.Select;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.dealertire.SMARTFramework.BaseTest;
import com.dealertire.SMARTFramework.Utils;
import com.dealertire.SMARTFramework.Pages.IssuePage;
import com.dealertire.SMARTFramework.Pages.MyPage;

@RunWith(Parameterized.class)
public class IssuePageSmokeTest extends BaseTest {
	private IssuePage issuePage;
	private String dealerCode;
	private String cat;
	private String pCat;
	private String dist;
	private MyPage mypage;
	private String distName;
	private String distAddr;
	
	/**
	* Parameter generation
	* @return the parameters from the datasheet
	* @throw IOException is the datasheet cannot be read
	*/
	@Parameters(name= "{index}: {3}")
	public static synchronized  Collection<String[]> parameters() throws IOException {
		
		ArrayList<String[]> allParams = null;
		try {
			ArrayList<String[]> dataSheet = Utils.readFromCSV("IssuePage.csv");
			allParams = Utils.mergeDatasheets_cartesian(getEnvironmentDatasheet(), dataSheet);
	    	
		} catch (IOException e) {
			fail("Could not read dealer list to test.");
		}
	
		return allParams;
	}

	/**String dealerCode, String cat,String pCat, String dist
	 * Parameterized constructor for testing
	 * @param remoteHost The remote host to use
	 * @param browser The browser to use
	 * @param version The version to request
	 * @param communicationMethod for that dealer
	 * @param activityType for that dealer
	 * @param dealerCode The dealerCode for that dealer
	 * @param set the distributor as dist
	 * @param set the category as cat
	 */
	public IssuePageSmokeTest (String remoteHost, String browser, String version, String dealerCode, String cat, String pCat, String distName, String distCity, String distAddr){
		super(remoteHost, browser, version);
		this.dealerCode = dealerCode;
		this.cat = cat;
		this.pCat = pCat;
		this.distAddr = distCity + "," + distAddr; //hack to avoid having to rework my utils method
		this.distName = distName;
	}	
	
	/**TestCase-1 
	   Scenario: Non-dealer Issue
         Given I am on the Issue editing page
         And I am creating an issue
         And I have not selected a dealer
         When I click “save”
         Then the save should be successful
	 * */
	@Test
	public void noDealerIssuePage() {
		       //1. Navigate to the Issue page
		       logger.info("1. Navigate to the Issue page");
		       issuePage = new IssuePage(driver);
		       issuePage.navigateTo();
		
		       //2. Select a category
		       logger.info("2. Select a category");
		       issuePage.selectCategory("Issue - Pricing");
		
		       //3. Select a Product Category
		       logger.info("3. Select a product category");
		       issuePage.setProductCategory("Tires");
		
		       //4. Save and Close
		       logger.info("4. Save and close the issue");
		       issuePage.saveIssue();
		
		       //5. Verify issue has saved successfully or not 
		       mypage = new MyPage(driver);
		       mypage.waitForPageLoad();
		       
		       logger.info("5. Verifying  Issue has Saved successfully  with no Dealer then mypage will be loaded");
		       assertTrue("issue did not save!",mypage.isLoaded());	
	}
	
	/*TestCase-2
	 * Scenario: Dealer Issue
	   Given I am on the Issue editing page
	   And I am creating an issue
	   And I have selected a dealer
	   When I click “save”
	   Then the save should be successful*/
	     @Test
	     public void DealerIssuePage(){
	           //1. Navigate to the Issue page
				logger.info("1. Navigate to the Issue page");
				issuePage = new IssuePage(driver);
				issuePage.navigateTo();
				
				//2. Select a category
				logger.info("2. Select a category");
				issuePage.selectCategory("Issue - Pricing");
				
				//3. Select a Dealer
			    logger.info("3. Select a Dealer");
				issuePage.addDealer("AUD425A08");
				
				//4. Select a Product Category
				logger.info("4. Select a product category");
				issuePage.setProductCategory("Tires");
				
				//5. Save and Close
				logger.info("5. Save and close the issue");
				issuePage.saveIssue();
				
				//6. Verify
				mypage = new MyPage(driver);
			       mypage.waitForPageLoad();
			       
			    logger.info("6. Verifying  Issue has Saved with Dealer then mypage will be loaded");
				assertTrue("issue did not save!",mypage.isLoaded());
				}
	
	/*TestCase-3
	 * Scenario: Distributor suggestion box
	   Given I am on the Issue editing page 
	   When I start typing into the Distributor field
	   Then I should see suggestions appear
	   When I click a suggestion
	   Then I should see the other fields under distributor information become filled out*/
	@Test
	public void distributorInformation(){
		       //1. Navigate to the Issue page
		       logger.info("1. Navigate to the Issue page");
		       issuePage = new IssuePage(driver);
		       issuePage.navigateTo();
		       		
		       //2. Set the distributor name
		       logger.info("2. Set Distributor information");
		       issuePage.setDistributor(distName);
		       issuePage.selectDistributorByAddress(distAddr);
		       
		       logger.info("3. Verifying distributor Name");
		       assertEquals(distName.toUpperCase(),issuePage.getDistributorName().toUpperCase());
		
		       logger.info("4. Verifying distributor's address");
		       assertEquals(distAddr.toUpperCase(),issuePage.getTextCityOfDistributor().toUpperCase().trim() + ", " + issuePage.getTextStateOfDistributor().trim() + " " + issuePage.getTextZipOfDistributor().trim());
	}
	
}
