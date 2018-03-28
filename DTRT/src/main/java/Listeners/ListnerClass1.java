package Listeners;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ListnerClass1 {

	@Test
	public void test1()
	{
		Assert.assertEquals("Hii", "Hii");
	}
	
	@Test
	public void test2()
	{
		Assert.assertEquals("Hii", "Bye");
	}
}
