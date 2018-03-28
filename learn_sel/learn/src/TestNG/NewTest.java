package TestNG;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;

public class NewTest {
	public WebDriver driver;
  @Test
  public void main() throws IOException {
	  driver.findElement(By.id("name_3_firstname")).sendKeys("First name");
	  driver.findElement(By.id("name_3_lastname")).sendKeys("Last name");
	driver.findElement(By.xpath("//input[@class= 'input_fields  radio_fields' and @value='single']")).click() ;
	driver.findElement(By.xpath("//input[@class='input_fields  piereg_validate[required] radio_fields' and @value='dance']")).click() ;
	
	  //driver.findElement(By.className("input_fields  radio_fields")).click();
	  //driver.findElement(By.className("input_fields  piereg_validate[required] radio_fields")).click();
	  Select select=new Select(driver.findElement(By.id("dropdown_7")));
	  select.selectByVisibleText("Bhutan");
	
	  Select mmselect=new Select(driver.findElement(By.id("mm_date_8")));
	  mmselect.selectByVisibleText("7");
	  
	  Select ddselect=new Select(driver.findElement(By.id("dd_date_8")));
	  ddselect.selectByVisibleText("26");
	  Select yyselect=new Select(driver.findElement(By.id("yy_date_8")));
	  yyselect.selectByVisibleText("2011");
	  driver.findElement(By.id("phone_9")).sendKeys("3452345678");
	  driver.findElement(By.id("username")).sendKeys("ygdwf7");
	  driver.findElement(By.id("email_1")).sendKeys("y@t.com");
	  driver.findElement(By.id("profile_pic_10")).click();
	  
	 Runtime.getRuntime().exec("C://Users//thinksysuser//Desktop//newauto1.exe");
	  driver.findElement(By.id("description")).sendKeys("g54ty9i540yui");
	  driver.findElement(By.id("password_2")).sendKeys("password34");
	  driver.findElement(By.id("confirm_password_password_2")).sendKeys("password34");
	  driver.findElement(By.id("profile_pic_10")).click();
  
  }
  
  
  
  
  @BeforeMethod
  public void beforeMethod() {
	  driver= new FirefoxDriver();
	  driver.get("http://demoqa.com/registration/");
  }

  @AfterMethod
  public void afterMethod() {
	  driver.quit();
  }

}
