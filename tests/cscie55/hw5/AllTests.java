package cscie55.hw5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccountJUnitTest.class, ATMFactoryImplJUnitTest.class,
		ATMImplJUnitTest.class })
public class AllTests {

}
