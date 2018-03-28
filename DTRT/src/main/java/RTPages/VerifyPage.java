package RTPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import RTFrameWork.PageFactory;
import RTFrameWork.SmartPage;

public class VerifyPage extends SmartPage{

	
	public VerifyPage(WebDriver driver)
	{
		super(driver);
		
	}
	@FindBy(xpath="//*[@class='btn btn-brand btn-cta js-btn-next']")
	public WebElement ConfirmButton;
	
	/*@FindBy(xpath="//*[@type='radio'][@id='stagg2']//parent::*")
	public WebElement RadioButtonNo;*/
	
	@FindBy(xpath="//*[@class='radio']")
	public List<WebElement> RadioButton;
	 
	 @FindBy(xpath="//*[@class='btn btn-brand btn-cta js-btn-next']")
		public WebElement selectTireSize;
	 
	 public SmartPage next()
		{
			ConfirmButton.click();
			po=PageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
			return po;
		}
}
