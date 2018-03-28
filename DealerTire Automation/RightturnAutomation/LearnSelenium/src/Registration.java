
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
public class Registration  {
	public static void main (String args[])
	{
		WebDriver driver= new FirefoxDriver();
		String baseUrl="http://demoqa.com/";
		driver.get(baseUrl);
		WebElement linkName;
		linkName= driver.findElement(By.linkText("Registration"));
		linkName.click();
		WebElement firstName= driver.findElement(By.id("name_3_firstname"));
		firstName.sendKeys("ABC");
		WebElement lastName= driver.findElement(By.id("name_3_lastname"));
		firstName.sendKeys("LABC");
		WebElement maritalStatus= driver.findElement(By.xpath("name_3_lastname"));
		
	}
	
	

}
