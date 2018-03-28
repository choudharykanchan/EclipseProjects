package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Payment {
	public Payment(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		
	}
	
	@AndroidFindBy(id="com.fabhotels.guests:id/tv_total_value")
	public  WebElement totalvalue;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/tv_discount_value")
	public  WebElement discountvalue;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/et_coupon_code")
	public  WebElement enterCouponfield;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/btn_apply")
	public  WebElement applybtn;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/btn_continue")
	public  WebElement continuebtn;
	
	//Proceed to payment page
	
	@AndroidFindBy(id="com.fabhotels.guests:id/et_full_name")
	public  WebElement fullname;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/et_email")
	public  WebElement email;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/et_mobile_number")
	public  WebElement mobilenumber;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/btn_pay")
	public  WebElement proceedToPaybtn;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/btnPayAtHotel")
	public  WebElement payAthotel;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/ivClose")
	@AndroidFindBy(xpath="android.widget.ImageView[@resource-id='com.fabhotels.guests:id/ivClose' AND @index='1']")
	
	public  WebElement otpCloseButton;
	
	@AndroidFindBy(id="com.fabhotels.guests:id/btnPay")
	public  WebElement finalPaymnet;
	
	
}
