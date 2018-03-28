package Step_Definations;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by thinksysuser on 31-03-2017.
 */

public class MyStepdefs {

    @Given("^User is on Home Page$")
    public void userIsOnHomePage() throws Throwable {
      System.out.println("We are on Home page");

    }
    @When("^User Navigate to LogIn Page$")
    public void userNavigateToLogInPage() throws Throwable {
        System.out.println("User Navigate to LogIn Page");

    }

    @And("^User enters UserName and Password$")
    public void userEntersUserNameAndPassword() throws Throwable {
        System.out.println("enters UserName and Password");

    }

    @Then("^Message displayed Login Successfully$")
    public void messageDisplayedLoginSuccessfully() throws Throwable {
        System.out.println("Message displayed Login Successfully");

    }
}
