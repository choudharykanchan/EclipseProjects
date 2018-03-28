package debug;

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
import com.dealertire.SMARTFramework.Pages.VisitPage;

@RunWith(Parameterized.class)
public class debugtests extends BaseTest {

	public debugtests(String remoteHost, String browser, String version) {
		super(remoteHost, browser, version);
	}
	
	/**
	 * Parameter generation
	 * @return The parameters from the datasheet
	 * @throws IOException If the datasheet cannot be read
	 */
	@Parameters(name= "{index}: {1}")
    public static synchronized  Collection<String[]> parameters() throws IOException {
    	return getEnvironmentDatasheet();
    }

	@Test
	public void debugTest1() {
		driver.navigate().to("http://qaappwebsrv1");
		VisitPage vp = new VisitPage(driver);
		vp.navigateTo();
		
		vp.openDealerPopup();
		assertTrue(vp.isDealerPopupShowing());
		vp.closeDealerPopup();
		assertFalse(vp.isDealerPopupShowing());
		vp.openDealerPopup();
		
		vp.setDealerSearchString("TSTACCT");
		ArrayList<String> suggestedDealers = vp.getSuggestedDealers();
		for (String dealer : suggestedDealers) {
			System.out.println(dealer);
			assertTrue(dealer.contains("TSTACCT"));
		}
		
		vp.addDealer("LOCTSTACCTM1");
		assertTrue(true);
	}
}
