package cscie55.hw5;

import java.rmi.RemoteException;

import org.junit.Test;

import cscie55.hw5.ATMImplJUnitTest;
import cscie55.hw5.ATMImpl;
import junit.framework.*;

public class ATMImplJUnitTest extends TestCase {
	
	private ATMImpl atm = null;

	public ATMImplJUnitTest()
	{
		super();
	}
	
	public void setUp() throws RemoteException
	{
		atm = new ATMImpl();
		atm.accounts_by_id.put(0, new Account());			// this will be our test account
	}
	
	public void tearDown()
	{
		atm = null;
	}
	
	@Test
	public void testGetBalance() throws RemoteException {
		atm.accounts_by_id.get(0).setBalance(5000);
		atm.getBalance(0);
		assertTrue("getBalance failed: Expected 5000, got " + atm.getBalance(0), atm.getBalance(0) == 5000);
	}
		
	@Test
	public void testDeposit() throws RemoteException {
		atm.accounts_by_id.get(0).setBalance(0);
		atm.deposit(0, 1000);
		assertTrue("Deposit failed: Expected 1000, got " + atm.getBalance(0), atm.getBalance(0) == 1000);
	}

	@Test
	public void testWithdraw() throws RemoteException {
		atm.accounts_by_id.get(0).setBalance(1000);
		atm.withdraw(0, 500);
		assertTrue("Withdrawal failed: Expected 500, got " + atm.getBalance(0), atm.getBalance(0) == 500);
	}
	
	public static void main(String [] argv)
	{
		junit.textui.TestRunner.run(ATMImplJUnitTest.class);
		System.exit(0);
	}
}
