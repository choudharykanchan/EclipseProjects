package PageObjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HotelDetails {
	public HotelDetails(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		
	}
	
	@AndroidFindBy(id="com.fabhotels.guests:id/btn_bookNow")
	public  WebElement selectDetails;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/btn_done")
	public  WebElement calenderDonebtn;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/btn_bookNow")
	public  WebElement soldout;
	
	
	@AndroidFindBy(id="com.fabhotels.guests:id/btn_bookNow")
	public  WebElement bookNow;
	
	@AndroidFindBy(className="android.widget.ImageButton")
	public  WebElement backbtn;
}
