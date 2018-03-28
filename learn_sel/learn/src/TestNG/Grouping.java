package TestNG;
import org.testng.annotations.Test;
public class Grouping {
	
	@Test(groups={"Car"})
	
	
		public void car1()
		{
			System.out.println("This is car1");
		}
	@Test (groups={"Car"})
	public void car2()
	{
		System.out.println("This is car2");
	}
	
	@Test(groups={"scooter"})
	public void scooter1()
	{
		System.out.println("This is scooter1");
	}
	@Test(groups={"scooter"})
	public void scooter2()
	{
		System.out.println("This is scooter2");
	}
	@Test(groups={"Car","Sedan Car"})
	public void Sedan1() {
		 
		  System.out.println("Batch Sedan Car - Test sedan 1");

}}
