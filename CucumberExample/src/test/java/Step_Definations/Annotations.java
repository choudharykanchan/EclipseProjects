package Step_Definations;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by thinksysuser on 04-04-2017.
 */
public class Annotations {
    WebDriver driver=null;

    @Before public void setup()
    {

        String driverPath = "C:/Users/thinksysuser/Desktop/Automation/geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", driverPath);
        driver = new FirefoxDriver();
    }

    @But("^Relogin option should be available$")
    public void reloginOptionShouldBeAvailable() throws Throwable {
        driver.get("https://www.facebook.com/login.php?login_attempt=1&lwv=110") ;

    }

    @Given("^I am on Facebook login page$")
    public void iAmOnFacebookLoginPage() throws Throwable {

        driver.get("https://www.facebook.com/");

    }

    @When("^I enter username as \"([^\"]*)\"$")
    public void iEnterUsernameAs(String username) throws Throwable {
        driver.findElement(By.id("email")).sendKeys(username);
    }

    @And("^I enter password as \"([^\"]*)\"$")
    public void iEnterPasswordAs(String password) throws Throwable {
        driver.findElement(By.id("pass")).sendKeys(password);
        driver.findElement(By.id("loginbutton")).click();
    }

    @Then("^Login should fail$")
    public void loginShouldFail() throws Throwable {
        System.out.println("Login failed");
    }

    @After public void cleanUp()
    {
        driver.close();
    }
}
