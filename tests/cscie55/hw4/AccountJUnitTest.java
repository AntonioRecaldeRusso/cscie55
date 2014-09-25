// AccountJUnitTest.java

package cscie55.hw4;

/**
 * This class runs tests on the Account class.
 * 
 * @author Antonio Recalde
 * @version 10/30/2013
 */
import org.junit.Test;
import junit.framework.*;

public class AccountJUnitTest extends TestCase
{
	private Account account = null;
	private float AMOUNT = 10000f;
	
	/**
	 * Class constructor
	 * @param name
	 */
	public AccountJUnitTest(String name)
	{
		super(name);
	}
	
	public void setUp()
	{
		account = new Account();
	}
	
	public void tearDown()
	{
		account = null;
	}
	
	/**
	 * Tests setBalance() method. It calls setBalance(AMOUNT), then checks if the new balance equals AMOUNT with the getBalance() method.
	 */
	@Test
	public void testSetBalance()
	{
		Account account = new Account();
		float temp_balance = account.getBalance();
		float expected;
		account.setBalance(AMOUNT);
		expected =  temp_balance + AMOUNT;
		assertTrue("setBalance failed: Expected " + expected + ", got " + account.getBalance(), account.getBalance() == expected);
	}
	
	/**
	 * Tests deposit() method. It gets the current balance. Makes a deposit, and then checks if the change in balance is correct.
	 */
	@Test
	public void testDeposit()
	{
		Account account = new Account();	
		float temp_balance = account.getBalance();
		account.deposit(AMOUNT);
		assertTrue("deposit failed: Expected " + (temp_balance + AMOUNT) + ", got " + account.getBalance(), account.getBalance() == (temp_balance + AMOUNT));
	}
	
	/**
	 * Tests withdraw() method. It sets the current balance to an arbitrary value. Calls withdraw(AMOUNT). Checks if the change in balance
	 * equals the expected.
	 */
	@Test
	public void testWithdraw()
	{
		Account account = new Account();
		float temp_balance = 1000000f;

		account.setBalance(temp_balance);
		account.withdraw(AMOUNT);
		assertTrue("withdraw failed: Expected " + (temp_balance - AMOUNT) + ", got " + account.getBalance(), account.getBalance() == (temp_balance - AMOUNT));
	}
	
	public static void main(String [] argv)
	{
		junit.textui.TestRunner.run(AccountJUnitTest.class);
	}
}
