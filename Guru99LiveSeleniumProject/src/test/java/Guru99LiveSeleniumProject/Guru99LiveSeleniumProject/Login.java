package Guru99LiveSeleniumProject.Guru99LiveSeleniumProject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.errorprone.annotations.Var;

public class Login {
	public static WebDriver driver;
	public static String baseUrl;

	
@Test(dataProvider="testData")
public void test(String username,String password) throws NoAlertPresentException
{
	
	/*String a[][];
	 a=Utility.readDataFromExcel(VariablesName.filePath, VariablesName.sheetName, VariablesName.tableName);
	 int size=a.length;
	 System.out.println(a.length);
	 String username, password;
	 String actualBoxtitle,actualTitle;
	for(int i=0;i<size;i++)
	{
		username = a[i][0]; // get username
	    password = a[i][1]; // get password
	    Login login =new Login();
		login.setup();
	    login.enterCredentials(username, password);
	    login.clickLoginButton();*/
	/*login.enterCredentials(VariablesName.username, VariablesName.pass);
	login.clickLoginButton();
	//System.out.println("at login page");
	WebDriverWait wait =new WebDriverWait(driver, 60);
	wait.until(ExpectedConditions.urlContains("Managerhomepage.php"));
	String actualTitle=driver.getTitle();
	System.out.println(actualTitle);
	if(actualTitle.equalsIgnoreCase(VariablesName.expectedTitle))
	{
		System.out.println("We are on home page");
	
	}
	else
	{
		System.out.println("Invalid Login");
	}
	login.teardown();}*/
	Login login =new Login();
	login.enterCredentials(username, password);
    login.clickLoginButton();
    WebDriverWait wait =new WebDriverWait(driver, 60);
	wait.until(ExpectedConditions.urlContains("Managerhomepage.php"));
    String actualBoxtitle = null,actualTitle;
    Alert alt=null;;
	    try{ 
	    	
		    
	       	 alt = driver.switchTo().alert();
			actualBoxtitle = alt.getText(); // get content of the Alter Message
				 alt.accept();
           
		    
			if (actualBoxtitle.contains(VariablesName.EXPECT_ERROR)) { // Compare Error Text with Expected Error Value
			    System.out.println("Test case : Passed"); 
			} else {
			    System.out.println("Test case  Failed");
			}
		}    
	    catch (NoAlertPresentException Ex){ 
	    	actualTitle = driver.getTitle();
			// On Successful login compare Actual Page Title with Expected Title
			 WebElement managerId=  driver.findElement(By.xpath("//*[contains(text(),'Manger Id : mngr')]"));
	    String actualmanagerId[]=managerId.getText().split(":");
	    String welcomeName=actualmanagerId[1].trim();
	    if(welcomeName.equalsIgnoreCase(username))
	    {
	    	System.out.println(actualmanagerId[1]);
	    	System.out.println("Manager Id matched");
	    }
	    else
	    {
	    	System.out.println(actualmanagerId[1]);
	    	System.out.println("Manager Id did not matched");
	    }
	    }
			
        } 
	 
	    
	 
		
	
	@DataProvider(name="testData")
	public  Object[][] testData()
	{
		
		/*return Utility.readDataFromExcel(VariablesName.filePath, VariablesName.sheetName, VariablesName.tableName);*/
		return new Object[][]{
			{"mngr97984","Ymebetu"},
			{"invalid","valid"},
			{"valid","invalid"},
			{"invalid","invalid"}
		};
	}
	
		
	

	

@BeforeMethod
public static void setup()
{
	System.setProperty("webdriver.gecko.driver",VariablesName.driverPath);
	
	/*ProfilesIni profile=new ProfilesIni();
	FirefoxProfile myProfile=profile.getProfile("SeleniumProfile");
	driver=new FirefoxDriver(myProfile);*/
	
	DesiredCapabilities cap=new DesiredCapabilities();
	File file=new File(VariablesName.fireFoxPath);
	FirefoxProfile profile=new FirefoxProfile(file);
	
	cap.setCapability(FirefoxDriver.PROFILE, profile);
	driver=new FirefoxDriver(cap);
	
   
	driver.get(VariablesName.baseUrl);
	driver.manage().timeouts().implicitlyWait(VariablesName.wait, TimeUnit.SECONDS);
}
public void enterCredentials (String userName,String password)
{
	WebElement useridbox,passwordbox;
	useridbox=driver.findElement(By.name("uid"));
	passwordbox=driver.findElement(By.name("password"));
    useridbox.sendKeys(userName);
	passwordbox.sendKeys(password);
	
	
}
public void clickLoginButton()
{
	WebElement loginButton;
	loginButton=driver.findElement(By.name("btnLogin"));
	loginButton.click();
	
}

@AfterMethod
public void teardown()
{
	
	driver.close();
	
	
}
}