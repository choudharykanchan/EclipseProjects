package RTPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import RTFrameWork.PageFactory;
import RTFrameWork.SmartPage;

public class SchedulePage extends SmartPage{

	
	public SchedulePage(WebDriver driver)
	{
		super(driver);
	}
	@FindBy(xpath="//*[@class='comp--btn comp--calendar-btn  ']")
	public WebElement selectTime;
	
	@FindBy(xpath="//*[@class='btn btn-brand btn-cta next-btn js-btn-next']")
	public WebElement reviewOrder;
	

	public SmartPage next()
	{
		reviewOrder.click();
		po=PageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		return po;
	}

}