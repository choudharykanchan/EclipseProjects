package learnjunit.Test;


import static org.junit.Assert.*;

import org.junit.Test;

import learnjunit.Application.MathProvider;



public class Test11 {
	MathProvider provider;
	 
	public Test11(){
 
		provider = new MathProvider();
		}
	
	@Test
	public void addTest(){
	MathProvider.Add(10, 20);
	assertEquals(10+20,provider.Add(10, 20));
	System.out.println(MathProvider.Add(10, 20));

	
	}
}


