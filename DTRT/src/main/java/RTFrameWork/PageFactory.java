package RTFrameWork;

import org.openqa.selenium.WebDriver;

import RTInterfaces.PageObject;
import RTPages.CheckoutPage;
import RTPages.InstallerPage;
import RTPages.NeedsPage;
import RTPages.SchedulePage;
import RTPages.TiresPage;
import RTPages.VerifyPage;

public class PageFactory {
	public WebDriver driver;
	static SmartPage po;

	/*PageFactory(WebDriver driver)
	{
		this.driver=driver;
	}
	*/
	public static  SmartPage getPageFromURL(String url, WebDriver driver)
	{
		if(url.contains("/app/verify/"))
		{
			po= new VerifyPage(driver);
			
		}
		else if(url.contains("/app/tire-coach/")) 
		{
			po= new NeedsPage(driver);
		}
		else if(url.contains("/app/tire-coach/")) 
		{
			po= new NeedsPage(driver);
		}
		else if(url.contains("/app/product/")) 
		{
			po= new TiresPage(driver);
		}
		else if(url.contains("/app/installer/")) 
		{
			po= new InstallerPage(driver);
		}
		else if(url.contains("/app/schedule/")) 
		{
			po= new SchedulePage(driver);
		}
		else if(url.contains("/app/checkout/")) 
		{
			po= new CheckoutPage(driver);
		}
		else if(url.contains("/app/confirmation/")) 
		{
			po= new CheckoutPage(driver);
		}
		return po;
	}
}
