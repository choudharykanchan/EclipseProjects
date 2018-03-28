package com.dealertire.RightTurnTesting;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * This suite exists to run only the "smoke" tests.
 * This is a small subset designed to run rapidly rather than be thorough. 
 * @author bgreen
 *
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
   AddToCartTests.class,
   CheckoutTests.class,
   ProductDetailsTests.class
})
public class SmokeTest {

}
