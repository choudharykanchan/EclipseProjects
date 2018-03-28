package learnSelenium;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SelectOptions {
	public static void main (String args[])
	{
		WebDriver driver= new FirefoxDriver();
		driver.get("http://toolsqa.com/automation-practice-form/");
		Select oSelect= new Select(driver.findElement(By.id("continents")));
		oSelect.selectByVisibleText("Europe");
		oSelect.selectByIndex(2);
		oSelect.selectByVisibleText("Africa");
		List<WebElement> AllOptions = oSelect.getOptions();
		int noOfOptions= AllOptions.size();
		System.out.println("No of options"+ ""+ noOfOptions);
	for (int i=0;i<noOfOptions;i++)
	{
		String sValue= oSelect.getOptions().get(i).getText();
		
		System.out.println(sValue);
	
	if(sValue.equals("Europe"))
	{
		oSelect.selectByIndex(i);
		break;
	}
	}driver.quit();
				}

}
