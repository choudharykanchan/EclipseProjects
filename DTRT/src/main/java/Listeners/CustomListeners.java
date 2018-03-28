package Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListeners implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
//	System.out.println("My Test Passed" +result.getName());
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Passed "+ result.getName());
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Failed "+result.getName());
		System.out.println(result.getClass() );
		System.out.println("Error message "+ result.getThrowable());
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
