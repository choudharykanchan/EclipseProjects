package RTPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import RTFrameWork.PageFactory;
import RTFrameWork.SmartPage;

public class CheckoutPage extends SmartPage{
	
	public CheckoutPage(WebDriver driver)
	{
		super(driver);
	}
	@FindBy(xpath="//*[@class='btn btn-brand btn-cta btn-next js-btn-next next-btn']")
	public WebElement viewTires;
	
	@FindBy(id="ship_to_forename")
	public WebElement firstname;

	
	@FindBy(id="ship_to_surname")
	public WebElement lastname;
	
	@FindBy(id="bill_to_phone")
	public WebElement phoneNo;
	
	@FindBy(id="bill_to_address_line1")
	public WebElement address1;
	
	@FindBy(id="bill_to_address_line2")
	public WebElement address2;
	
	@FindBy(id="bill_to_address_city")
	public WebElement city;
	
	@FindBy(id="bill_to_address_state")
	public WebElement state;
	
	@FindBy(id="bill_to_address_postal_code")
	public WebElement zipCode;
	
	@FindBy(id="card_type")
	public WebElement cardType;
	
	@FindBy(id="card_number")
	public WebElement cardNumber;
	
	@FindBy(id="card_cvn")
	public WebElement cvn;
	
	@FindBy(id="card_expirationmonth")
	public WebElement expMonth;
	
	@FindBy(id="card_expirationyear")
	public WebElement expYear;
	
	@FindBy(id="bill_to_email")
	public WebElement email;
	
	@FindBy(id="confirm_email")
	public WebElement confirmEmail;
	
	@FindBy(id="submit_order")
	public WebElement placeOrder;
	
	public void enterCustomerInformation(String firstName,String LastName,String phNo)
	{
	firstname.sendKeys(firstName);
	lastname.sendKeys(LastName);
	phoneNo.sendKeys(phNo);
	
	}
	public void enterBillingAddress(String addr1,String city,String state,String zipcode)
	{
	address1.sendKeys(addr1);
	this.city.sendKeys(city);
	Select st=new Select(this.state);
	st.selectByValue(state);
	phoneNo.sendKeys(zipcode);
	
	}
	public void enterCreditCardAndEmail(String cardType,String cardNo,String cvn,String expMonth,String expYear)
	{
		Select st=new Select(this.cardType);;
		st.selectByVisibleText(cardType);
		cardNumber.sendKeys(cardNo);
		this.cvn.sendKeys(cvn);
		Select m=new Select(this.expMonth);
		m.selectByValue(expMonth);
		Select y=new Select(this.expYear);
		y.selectByValue(expYear);
		email.sendKeys("choudhary.kanchan@dealertire.com");
		confirmEmail.sendKeys("choudhary.kanchan@dealertire.com");
	}
	public SmartPage next()
	{
		placeOrder.click();
		po=PageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		return po;
	}
}

