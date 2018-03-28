package PageObjects;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SearchResults {
	public SearchResults(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		
	}
	
	//@AndroidFindBy(className="android.support.v7.app.a$c")
	@AndroidFindBy(xpath="//*[@class='android.support.v7.app.a$c']/*[@class='android.widget.TextView']")
	//@AndroidFindBy(xpath="//*[@resource-id='com.fabhotels.guests:id/constraintLayout']/[@class='android.support.v7.app.a$c']")
	public  List<WebElement> localities;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/iv_property")
	public List<WebElement> hotelImage;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/tvSearchItemCity")
	public List<WebElement> AllCities;

	@AndroidFindBy(id="com.fabhotels.guests:id/tvSearchItemDescription")
	public List<WebElement> numberofHotels;
	
	@AndroidFindBy(xpath="//android.support.v7.widget.RecyclerView[@resource-id='com.fabhotels.guests:id/rvSearch']")
	public WebElement ALL;
	
	
	
	
	
}
