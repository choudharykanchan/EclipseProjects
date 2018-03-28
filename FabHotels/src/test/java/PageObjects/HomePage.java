package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage {
	public HomePage(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		
	}
	
	@AndroidFindBy(id="com.fabhotels.guests:id/tv_whereTo")
	public WebElement searchBtn;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/iv_cityImage")
	public WebElement newDelhiIcon;

	
	@AndroidFindBy(className="android.widget.ImageButton")
	public WebElement phoneIcon;
	
	//Added dialer objects in Home page on temp basis
	@AndroidFindBy(id="android:id/button_once")
	public WebElement justOnceBtn;
	
	@AndroidFindBy(id="com.android.dialer:id/digits")
	public WebElement dialerPhoneNumber;
}
