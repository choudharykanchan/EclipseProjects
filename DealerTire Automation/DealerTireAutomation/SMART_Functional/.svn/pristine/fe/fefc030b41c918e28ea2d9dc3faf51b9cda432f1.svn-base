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
import com.dealertire.SMARTFramework.Pages.ActivityPage;
import com.dealertire.SMARTFramework.Pages.DealerPage;
import com.dealertire.SMARTFramework.Pages.MyPage;

@RunWith(Parameterized.class)
public class ActivityPageSmokeTest extends BaseTest {

	private String dealerCode;
	private String commnuicationMethod;
	private String activityType;
	private ActivityPage activityPage;
	
	/* * Parameter generation
	 * @return The parameters from the datasheet
	 * @throws IOException If the datasheet cannot be read;*/
	 
	@Parameters(name= "{index}: {3}")	
    public static synchronized  Collection<String[]> parameters() throws IOException {
    	ArrayList<String[]> allParams = null;
		try {
			ArrayList<String[]> dataSheet = Utils.readFromCSV("ActivityPage.csv");
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
	 * @param dealerCode The dealerCode for that dealer
     * @param communicationMethod for that dealer 
	 * @param activityType for that dealer
	 */
	public ActivityPageSmokeTest(String remoteHost,String browser, String version, String dealerCode,String commnuicationMethod, String activityType) {
		super(remoteHost, browser, version);
		this.dealerCode = dealerCode;
		this.commnuicationMethod = commnuicationMethod;
		this.activityType = activityType;
		
	}
	
	   /**TestCase-1
	       Navigate to the activity page
	       Open dealer popup
	       Search and select a dealer
	       Select a communication method
	       Select an Acitivity type from the menu
	       Save the new activity*/
	    @Test
 	    public void activityPageAddandSave(){
		        //1. Navigate to the Activity Page
		        logger.info("1. Navigating to the activity page");
		        activityPage = new ActivityPage(driver);
		        activityPage.navigateTo();
		        logger.info("2. Opening the dealer popup");
		        activityPage.addDealer(dealerCode);
		        activityPage.acceptDealerPopup();
		
		        //4. Select a communication method
		        logger.info("3. Selecting a communication method");
		        activityPage.selectCommunicationMethod(commnuicationMethod);
		
		        //5. Select an activity type from the menu
		        logger.info("4. Selecting an activity from the menu");
		        activityPage.selectActivityDropdown(activityType);
		
		        //6. Save
		        logger.info("5. Saving the activity");
	            activityPage.saveActivity();
	    
	            //7. Verifying save activity 
		        assertTrue("did not save!",activityPage.SavedSuccessfully());
		}
	
     /*TestCase-2
	        Given I am on the Activity page
	        And I am creating a new activity
	        And I have not entered a selection from the “Activity” dropdown
	        When I try to save
	        Then I should see an error
	        And the save should not succeed*/
       @Test
	   public void SaveActivityAndGetError(){
		        //1. Navigate to the Activity Page
				logger.info("1. Navigating to the activity page");
				activityPage = new ActivityPage(driver);
				activityPage.navigateTo();
				
			    //2. Add Dealer
				logger.info("2. Added dealer by dealercode");
				activityPage.addDealer(dealerCode);
				
			    //3. Select Communication Method
				logger.info("3. Added communication method");
				activityPage.selectCommunicationMethod(commnuicationMethod);
				
			    //4. Save Activity
				logger.info("4. Try to save activity");
				activityPage.saveActivity();
				 
			    //5. Verify Error Message is showing or not
			    logger.info("5. Verifying error message is coming or not");	
		        assertTrue("Error Message is not showing!",activityPage.VerifyErrorMessage());
	}

	   /*TestCase-3
	        Scenario: No Dealer on Dealer Activity
	             Given I am on the Activity page
	             And I am creating a new Dealer activity
	             And I have not selected a Dealer
	             When I try to save
	             Then I should see an error
	             And the save should not succeed*/
         @Test
	     public void SaveActivityGetErrorWithNoDealer(){
		        //1. Navigate to the Activity Page
			    logger.info("1. Navigating to the activity page");
			    activityPage = new ActivityPage(driver);
			    activityPage.navigateTo();
			
			    //2. Select Communication Method
			    logger.info("2. Added communication method");
			    activityPage.selectCommunicationMethod(commnuicationMethod);
			
			    //3. Save Activity
			    logger.info("3. Try to save activity");
			    activityPage.saveActivity();
			
			    //4. verify Error Message comes or not
			    logger.info("4. verifying Error Message is showing or not");
			    assertTrue("Error Message was not showing!",activityPage.VerifyErrorMessage());
	}	
}
