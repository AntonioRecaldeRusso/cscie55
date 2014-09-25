// ATMImplementationJUnitTest.java
package cscie55.hw4;

/**
 * This class tests ATMImplementation.java
 * 
 * @author Antonio Recalde
 * @version 10/30/2013
 */

import junit.framework.*;
import org.junit.Test;

public class ATMImplementationJUnitTest  extends TestCase
{
	private ATMImplementation atm = null;
	private float AMOUNT = 100000;
	
	/**
	 * Class constructor
	 * 
	 * @param name
	 */
	public ATMImplementationJUnitTest(String name)
	{
		super(name);
	}
	
	public void setUp()
	{
		atm = new ATMImplementation();
	}
	
	public void tearDown()
	{
		atm = null;
	}
	
	/**
	 * Tests deposit method. Gets current balance, deposits an amount, then checks if change in balance is correct
	 * @throws ATMException
	 */
	@Test
	public void testDeposit() throws ATMException
	{
		float temp_balance = atm.getBalance();
		float expected;
		atm.deposit(AMOUNT);
		expected = temp_balance + AMOUNT;
		assertTrue("Deposit failed: Expected " + expected + ", got " + atm.getBalance(), atm.getBalance() == expected); 
	}
	
	/**
	 * Test withdraw method. It checks the balance, makes a deposit, withdraws a smaller amount, then checks if the new balance is correct
	 * 
	 * @throws ATMException
	 */
	@Test
	public void testWithdraw() throws ATMException
	{
		float temp_balance = atm.getBalance();
		float withdraw = 100f;
		float expected;
		atm.deposit(AMOUNT);				// just in case.. not to go to negative values
		atm.withdraw(withdraw);
		expected = (temp_balance + AMOUNT) - withdraw; 
		assertTrue("Withdraw failed: Expected " + expected + ", got " + atm.getBalance(), atm.getBalance() == expected);
	}
	
	public static void main(String [] argv)
	{
		junit.textui.TestRunner.run(ATMImplementationJUnitTest.class);
	}
}
