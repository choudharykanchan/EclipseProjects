package RTPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import RTFrameWork.PageFactory;
import RTFrameWork.SmartPage;

public class NeedsPage extends SmartPage{

	
	public NeedsPage(WebDriver driver)
	{
		super(driver);
	}
	@FindBy(xpath="//*[@class='btn btn-brand btn-cta btn-next js-btn-next next-btn']")
	public WebElement viewTires;
	
	public SmartPage next()
	{
		viewTires.click();
		po=PageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		return po;
	}

}

