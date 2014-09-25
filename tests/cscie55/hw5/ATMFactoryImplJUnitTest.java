package cscie55.hw5;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ATMFactoryImplJUnitTest extends TestCase {

	ATMFactory factory;
	@Before
	public void setUp() {
		try {
			factory = new ATMFactoryImpl();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		factory = null;
	}

	@Test
	public void testGetATM() throws RemoteException {
		ATM getAtm = factory.getATM();
		assertNotNull(getAtm);
	}

	public static void main(String [] argv)
	{
		junit.textui.TestRunner.run(AccountJUnitTest.class);
	}
}
