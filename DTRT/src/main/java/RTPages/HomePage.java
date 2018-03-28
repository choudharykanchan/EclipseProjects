package RTPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import RTFrameWork.PageFactory;
import RTFrameWork.SmartPage;
import RTFrameWork.Utilities;

public class HomePage extends SmartPage {
	Select select;
	
	
	public HomePage(WebDriver driver)
	{
	super(driver);

	}
	@FindBy(xpath="//*[@class='seo--select js-seo-make js-seo-ymm']")
	public WebElement selectMake;
	
	@FindBy(xpath="//*[@class='seo--select js-seo-model js-seo-ymm']")
	public WebElement selectModel;
	
	@FindBy(xpath="//*[@class='seo--select js-seo-year js-seo-ymm']")
	public WebElement selectYear;
	
	@FindBy(xpath="//*[@class='seo--select js-seo-trim js-seo-to']")
	public WebElement selectTrim;
	
	@FindBy(xpath="//*[@class='seo--select js-seo-option js-seo-to']")
	public WebElement selectOption;
	
	@FindBy(xpath="//*[@class='js-seo-zip seo--input']")
	public WebElement enterZipCode;
	
	@FindBy(xpath="//*[@class='js-seo-btn']")
	public WebElement shopNowButton;
	
	
	
	
	public void selectMake(String value)
	{
		Select selectmake= new Select(selectMake);
		selectmake.selectByValue(value);
	}
	
	public void selectModel(String value)
	{
		Select selectmodel= new Select(selectModel);
		selectmodel.selectByValue(value);
	}
	public void selectTrim(String value)
	{
		Select selecttrim= new Select(selectTrim);
		selecttrim.selectByVisibleText(value);
	}
	public void selectOption(String value)
	{
		Select selectoption= new Select(selectOption);
		selectoption.selectByVisibleText(value);
	}
	public void enterzipcode(String value)
	{
		enterZipCode.sendKeys(value);
	}
	
	public void selectYear(String value)
	{
		Select selectyear= new Select(selectYear);
		selectyear.selectByVisibleText(value);
	}
	public void selectVehicle(String make,String model,String year,String trim,String option,String zipcode)
{
		selectMake(make);
		selectModel(model);
		selectYear(year);
		selectTrim(trim);
		selectOption(option);
		enterzipcode(zipcode);
}
	public void selectVehicle()
	{
		int rowNo=Utilities.getRandomNumber(1, 20);
		
		String make=Utilities.readFromExcel(Utilities.vehicleDataFilePath,Utilities.vehicleDataFileName,rowNo,"make");
		System.out.println(make);
		selectMake(make);
		
		String model=Utilities.readFromExcel(Utilities.vehicleDataFilePath,Utilities.vehicleDataFileName,rowNo,"model");
		System.out.println(model);
		selectModel(model);
		
		String year=Utilities.readFromExcel(Utilities.vehicleDataFilePath,Utilities.vehicleDataFileName,rowNo,"year");
		System.out.println(year);
		selectYear(year);
	
		String trim=Utilities.readFromExcel(Utilities.vehicleDataFilePath,Utilities.vehicleDataFileName,rowNo,"trim");
		System.out.println(trim);
		selectTrim(trim);
	
		String option=Utilities.readFromExcel(Utilities.vehicleDataFilePath,Utilities.vehicleDataFileName,rowNo,"option");
		System.out.println(option);
		selectOption(option);
		
	
		
	}
	public SmartPage next()
	{
		shopNowButton.click();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		po=PageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
		return po;
	}
}