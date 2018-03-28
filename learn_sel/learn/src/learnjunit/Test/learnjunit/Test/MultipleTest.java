package learnjunit.Test.learnjunit.Test;

import org.testng.annotations.Test;

public class MultipleTest {
  @Test(priority=0)
  public void one() {
	  System.out.println("This is one");
  }
  @Test(priority=1)
  public void two() {
	  System.out.println("This is two");
  }
  @Test(priority=2,enabled=false)
  public void three() {
	  System.out.println("This is three");
  }
  @Test(priority=3)
  public void four() {
	  System.out.println("This is four");
  }
}
