package Practice.Practice;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;



@Test
public class SnapShot {
WebDriver driver;
String pathAddress="C:/Users/thinksysuser/Desktop/error.png";

	public void startup()
	
	{
		System.setProperty("webdriver.gecko.driver", "C:/Users/thinksysuser/Desktop/Automation soft/geckodriver.exe");
		driver=new FirefoxDriver();
		driver.get("http://toolsqa.com/automation-practice-form/");
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		takesnapshot(driver, pathAddress);
		driver.close();
	}
	
	public void takesnapshot(WebDriver driver,String path)
	{
		TakesScreenshot sc=(TakesScreenshot) driver;
		File image=sc.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(image,new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
