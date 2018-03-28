package SMART_SmokeTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.Alert;

import com.dealertire.SMARTFramework.BaseTest;
import com.dealertire.SMARTFramework.Utils;
import com.dealertire.SMARTFramework.Pages.VisitPage;

@RunWith(Parameterized.class)
public class VisitPageSmokeTest extends BaseTest {
	
	 private VisitPage visitpage;
	 private String activityType;
	 private String dealerCode;

/**
* Parameter generation
* @return the parameters from the datasheet
* @throw IOException is the datasheet cannot be read
*/
@Parameters(name= "{index}: {3}")	
public static synchronized  Collection<String[]> parameters() throws IOException {
	
	ArrayList<String[]> allParams = null;
	try {
		ArrayList<String[]> dataSheet = Utils.readFromCSV("VisitPage.csv");
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
	 * @param dealerCode The dealercode for that dealer
	 * @param the activityType for that dealer
	 */
	public VisitPageSmokeTest(String remoteHost, String browser, String version,String dealerCode, String activityType) {
		super(remoteHost, browser, version);

		this.activityType = activityType;
		this.dealerCode = dealerCode;
	}	
	
	/*TestCase-1
	 Scenario: Remove Activity 
	           Given I am on the New Visit page
	           And I have added activities to the visit
	           When I click “remove” on an activity
	           Then the activity should vanish (confirm dialog?)*/
	    @Test
        public void RemoveActivity(){
		       visitpage = new VisitPage(driver);
		       //1. Navigate to visit page
		       logger.info("1. Navigate to visitpage");
		       visitpage.navigateTo();
		
		       logger.info("2 accept dealer popup");
		       visitpage.acceptDealerPopup();
		
		       //2. Added activity
		       logger.info("3. Added activity method");
		       visitpage.addActivityToVisit(activityType);
		
		       //3. Click remove
		       logger.info("4. Verifying remove activity is working or not");
		       visitpage.removeActivity(1);
		       visitpage.handleActivityPopup(true);
		
		      assertEquals(0,visitpage.getNumActivitiesInVisit());
		}
		
	  /*TestCase-2
	   * Given I am on the New Visit page
	         And I have selected one or more dealers
	         When I add one or more Non-Dealer Activities
	         And I click save
	         Then the save should succeed
	         And I should not see any errors*/
     	@Test
	    public void SavePerfectly(){
     		   //1. Navigate to visit page
     		   visitpage = new VisitPage(driver);
     	       logger.info("1. Navigate to visit page");
			   visitpage.navigateTo();
			
			   //2. Add dealer
			   logger.info("2. Add dealer by dealerCode");
			   visitpage.addDealer(dealerCode);
			   visitpage.acceptDealerPopup();
			
		       //3. add activity
			   logger.info("3. Add activity method");
			   visitpage.addActivityToVisit(activityType);
			   
			   //4. add activity
			   logger.info("4. Add communication method");
			   visitpage.selectCommunicationMethod("In Person");
			
			   //5. Save activity
               logger.info("5. Save activity");
			   assertTrue("Visit should save successfully",visitpage.saveVisit());
     		}

	     /*TestCase-3
		 * Scenario: New Visit, only NDA
		     Given I am on the New Visit page
		     And I have not selected any dealers
		     When I add one or more Non-Dealer Activities
		     And I click save
		     Then the save should fail
		     And I should see an error*/
		@Test
		public void SaveAndGetErrorMessage(){
			  //1. Navigate to visit page
			  visitpage = new VisitPage(driver);
			  logger.info("1. Navigate to visitpage");
			  visitpage.navigateTo();
			
			  //2. accepting dealer popup
			  logger.info("2. accept dealer popup");
			  visitpage.acceptDealerPopup(); 
			
			  //3. add activity
			  logger.info("3. Added activity");
			  visitpage.addActivityToVisit(activityType);
						
			  //4. add one more activity
			  logger.info("4. Added one more activity");
			  visitpage.addActivityToVisit(activityType);
			
			  //5. Save activity
			  logger.info("5. Saving activity");
			  visitpage.saveVisit();
			
			  //6. Verify Saving
			  logger.info("6. Verifying the error message is showing or not");
			  assertTrue("Incorrect error message; received " + visitpage.getErrorMessage(), visitpage.getErrorMessage().contains("At least one dealer is required"));
			  
		}
	}
