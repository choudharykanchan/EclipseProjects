package learnjunit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

import learnjunit.Application.MathProvider;

import org.junit.runner.Result;

public class TestRunner {
	
	public static void main()
	{
		Result result= JUnitCore.runClasses(Test11.class);
		System.out.println("Total number of tests " + result.getRunCount());
		System.out.println("Total number of tests " + result.getFailureCount());
	
	for(Failure failure: result.getFailures())
	{
		System.out.println(failure.getMessage());
	}
	System.out.println(result.wasSuccessful());

	}
	
	
	
}
