package com.dealertire.RightTurnFramework.testing;
import static org.junit.Assert.*;

import org.junit.Test;

import com.dealertire.RightTurnFramework.BaseTest;


/**
 * Tests to run during build
 * @author bgreen
 *
 */
public class buildTests extends BaseTest {

	/**
     * @see com.dealertire.RightTurnFramework.BaseTest#BaseTest(String, String, String)
     */
	public buildTests(String os, String browserName, String browserVersion) {
		super(os, browserName, browserVersion);
		useSauce =  false;
	}
	
	/**
	 *  A test so that during build we can ensure the base test did not break.
	 */
	@Test
	public void ensureBaseTestWorks() {
		assertTrue(true); //Will pass if the test set up okay
	}

}
