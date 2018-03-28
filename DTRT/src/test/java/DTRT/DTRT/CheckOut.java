package DTRT.DTRT;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.BorderUIResource.TitledBorderUIResource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import RTFrameWork.BaseTest;
import RTFrameWork.Utilities;
import RTPages.CheckoutPage;
import RTPages.HomePage;
import RTPages.InstallerPage;
import RTPages.NeedsPage;
import RTPages.SchedulePage;
import RTPages.TiresPage;
import RTPages.VerifyPage;
import junit.framework.Assert;

public class CheckOut extends BaseTest{
public  CheckOut()
	{
		super("FF");
	}
	
	@Test
	public void example() throws InterruptedException
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    homePage=new HomePage(driver);
	   // verifyPage=new VerifyPage(driver);
		homePage.selectVehicle();
		homePage.enterzipcode("44444");
		verifyPage= (VerifyPage) homePage.next();
		WebDriverWait wait=new WebDriverWait(driver, 10000);
		wait.until(ExpectedConditions.visibilityOf(verifyPage.ConfirmButton));
		if(verifyPage.RadioButton.get(1).isDisplayed())
		{
			System.out.println("Entered if");
			verifyPage.RadioButton.get(1).click();
			verifyPage.RadioButton.get(3).click();
		}
		needsPage=(NeedsPage) verifyPage.next();
		wait.until(ExpectedConditions.visibilityOf(needsPage.viewTires));
		tiresPage=(TiresPage) needsPage.next();
		wait.until(ExpectedConditions.visibilityOf(tiresPage.addToCartButton));
	    installerPage=(InstallerPage) tiresPage.next();
	    wait.until(ExpectedConditions.visibilityOf(installerPage.makeAppointment));
	    //installerPage.makeAppointment.click();
	    schedulePage=(SchedulePage) installerPage.next();
	    wait.until(ExpectedConditions.elementToBeClickable( schedulePage.reviewOrder));
	    schedulePage.selectTime.click();
	   checkoutPage= (CheckoutPage) schedulePage.next();
	    checkoutPage.enterCustomerInformation("Test1", "Test2","3456543234");
	    checkoutPage.enterBillingAddress("test", "Test", "AK", "44444");
	    checkoutPage.enterCreditCardAndEmail("American Express", "378282246310005", "345", "09", "2018");
	    checkoutPage.placeOrder.click();
	    
		
		
		
	}
/*@Test
public void readvaluefromexcel() 
{
	int rowno=Utilities.getRandonNumber(1,20);
	String value=Utilities.readFromExcel("C:/Users/thinksysuser/Desktop", "demoVehicles.xlsx", rowno, "make");
System.out.println(value);
}*/

}
