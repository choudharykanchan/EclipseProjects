package RTPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import RTFrameWork.PageFactory;
import RTFrameWork.SmartPage;

public class TiresPage extends SmartPage{
	
	
	public TiresPage(WebDriver driver)
	{
		super(driver);
	}
	@FindBy(xpath="//*[@class='btn btn-brand btn-block btn-add hasPopover comp_ui_selectFancyBtn']")
	public WebElement addToCartButton;
	
	public SmartPage next()
	{
		addToCartButton.click();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		po=PageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		return po;
	}

}
