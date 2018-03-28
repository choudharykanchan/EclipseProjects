import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
public class SwitchToAlert {
	public static void main(String args[])
	{
		WebDriver driver=new FirefoxDriver();
		String URL="http://www.toolsqa.com/automation-practice-switch-windows/";
		driver.get(URL);
	    WebElement NewWindow=driver.findElement(By.id("alert"));
	    NewWindow.click();
	    Alert myAlert=driver.switchTo().alert();
	   String alertText=myAlert.getText();
		System.out.println(alertText);
	}

}
