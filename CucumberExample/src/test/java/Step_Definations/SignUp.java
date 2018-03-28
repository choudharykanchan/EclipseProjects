package Step_Definations;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


/**
 * Created by thinksysuser on 04-04-2017.
 */
public class SignUp {
    String driverPath= "C:/Users/thinksysuser/Desktop/Automation/geckodriver.exe";
    WebDriver driver = null;
    @Then("^the user registration should be unsuccessful$")
    public void theUserRegistrationShouldBeUnsuccessful() throws Throwable {
       System.out.println("Invalid Signup");

    }

    @When("^I enter  valid data on the page$")
    public void iEnterValidDataOnThePage(DataTable table) throws Throwable {
      // ArrayList list=new ArrayList(table);
      // List<String> data= table.raw();
        List<List<String>> data= table.raw();
        System.out.println(data.get(1).get(1));

      driver.findElement(By.name("firstname")).sendKeys(data.get(1).get(1));
        driver.findElement(By.name("lastname")).sendKeys(data.get(2).get(1));
        driver.findElement(By.name("reg_email__")).sendKeys(data.get(3).get(1));
        driver.findElement(By.name("reg_passwd__")).sendKeys(data.get(4).get(1));
        Select dropdownB = new Select(driver.findElement(By.name("birthday_day")));
        dropdownB.selectByValue("15");

        Select dropdownM = new Select(driver.findElement(By.name("birthday_month")));
        dropdownM.selectByValue("6");

        Select dropdownY = new Select(driver.findElement(By.name("birthday_year")));
        dropdownY.selectByValue("1990");
    }

    @Given("^On Facebook page$")
    public void onFacebookPage() throws Throwable {
        System.setProperty("webdriver.gecko.driver",driverPath);
        driver = new FirefoxDriver();
        driver.navigate().to("https://www.facebook.com/");
    }
}
