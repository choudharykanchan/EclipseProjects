package TestNG;

import org.testng.annotations.Test;

public class DependsOnMethods {
@Test(dependsOnMethods={"openBrowser"})
public void signIn()
{
	System.out.println("It will sign in");
}
@Test
public void openBrowser()
{
	System.out.println("It will Open Browser");
}
@Test(dependsOnMethods={"signIn"})
public void signOut()
{
	System.out.println("It will sign out");
}
}
