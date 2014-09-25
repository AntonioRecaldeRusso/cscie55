package cscie55.hw5;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import cscie55.hw5.AccountJUnitTest;

public class AccountJUnitTest extends TestCase {

	private Account account;
	
	@Before
	public void setUp() {
		account = new Account();
	}
	
	@After
	public void tearDown() {
		account = null;
	}

	@Test
	public void testSetBalance() {
		account.setBalance(5000);
		assertTrue("setBalance failed: Expected 5000, got " + account.getBalance(), account.getBalance() == 5000);
	}
	
	@Test
	public void testGetBalance() {
		account.setBalance(1000);
		assertTrue("getBalance failed: Expected 1000, got " + account.getBalance(), account.getBalance() == 1000);
	}

	@Test
	public void testDeposit() {
		account.setBalance(0);
		account.deposit(1000);
		assertTrue("deposit failed: Expected 1000, got " + account.getBalance(), account.getBalance() == 1000);
	}

	@Test
	public void testWithdraw() {
		account.setBalance(1000);
		account.withdraw(100);
		assertTrue("withdraw failed: Expected 900, got " + account.getBalance(), account.getBalance() == 900);
	}

	public static void main(String [] argv)
	{
		junit.textui.TestRunner.run(AccountJUnitTest.class);
	}
}
