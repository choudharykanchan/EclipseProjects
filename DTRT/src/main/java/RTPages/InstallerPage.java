package RTPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import RTFrameWork.PageFactory;
import RTFrameWork.SmartPage;

public class InstallerPage extends SmartPage{
	
	public InstallerPage (WebDriver driver)
	{
		super(driver);
	}
	@FindBy(xpath="//*[@class='btn btn-brand btn-cta next-btn js-btn-next']")
	public WebElement makeAppointment;
	public SmartPage next()
	{
		makeAppointment.click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		po=PageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		return po;
	}

}
