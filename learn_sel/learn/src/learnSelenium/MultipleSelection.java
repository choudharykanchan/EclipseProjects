package learnSelenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class MultipleSelection 
{
	public static void main(String args[]) throws InterruptedException

{WebDriver driver= new FirefoxDriver();
driver.get("http://toolsqa.com/automation-practice-form/");
Select mSelect= new Select(driver.findElement(By.name("selenium_commands")));
mSelect.selectByVisibleText("Navigation Commands");
mSelect.deselectByVisibleText("Navigation Commands");

mSelect.selectByIndex(0);
mSelect.deselectByIndex(0);

List<WebElement> AllOptions= mSelect.getOptions();
int NoofOption= AllOptions.size();
System.out.println("Total no of options is = "+NoofOption );
for (int i=0;i< NoofOption;i++)
{
	String OptionName= mSelect.getOptions().get(i).getText();
System.out.println("Current Option is"+ OptionName);	
mSelect.deselectByVisibleText(OptionName);
Thread.sleep(2000);

}
mSelect.deselectAll();
driver.quit();



	

}
}