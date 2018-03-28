package Step_Definations;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by thinksysuser on 03-04-2017.
 */
public class facebookLogin {


    String driverPath= "C:/Users/thinksysuser/Desktop/Automation/geckodriver.exe";
  //  String driverPath="C:/Users/AppData/Local/Mozilla Firefox/firefox.exe";
    WebDriver driver;


    @Given("^User is on facebook Page$")
    public void userIsOnFacebookPage() throws Throwable {
       // System.setProperty("webdriver.firefox.bin",driverPath);
       System.setProperty("webdriver.gecko.driver",driverPath);
       driver = new FirefoxDriver();
        driver.navigate().to("https://www.facebook.com/");

    }

   

    @Then("^Message displayed Invalid Credentials$")
    public void messageDisplayedInvalidCredentials() throws Throwable {
     driver.quit();
        System.out.println("Invalid login credentials");

    }




    @And("^User enter Password  \"([^\"]*)\"$")
    public void userEnterPassword(String password) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.findElement(By.id("pass")).sendKeys(password);
        driver.findElement(By.id("loginbutton")).click();
        driver.close();
    }


    @When("^User enter email id  \"([^\"]*)\"$")
    public void userEnterEmailId(String username) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.findElement(By.id("email")).sendKeys(username);
    }
}
