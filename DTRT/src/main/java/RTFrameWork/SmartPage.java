package RTFrameWork;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class SmartPage  {
	public WebDriver driver;
	public SmartPage po;
	public SmartPage(WebDriver driver)
	
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public abstract SmartPage next();
}
