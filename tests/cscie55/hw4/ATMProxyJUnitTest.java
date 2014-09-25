// ATMProxyJUnitTest.java
package cscie55.hw4;

/**
 * This class runs a JUnit test on ATMProxy.java
 * 
 * @author Antonio Recalde
 * @version 10/30/2013
 */

import java.net.UnknownHostException;

import junit.framework.*;
import org.junit.Test;

public class ATMProxyJUnitTest extends TestCase
{
	private final float AMOUNT = 10000f;
	private ATM atm = null;
	
	/**
	 * Class constructor
	 * 
	 * @param name
	 */
	public ATMProxyJUnitTest(String name)
	{
		super(name);
	}
	
	public void setUp()
	{
		try {
			atm = new ATMProxy("localhost", 1099);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	public void tearDown()
	{
		atm = null;
	}
	
	/**
	 * Tests deposit method. It gets the current balance, makes a deposit, and checks if the change in value equals the deposit.
	 */
	@Test
	public void testDeposit()
	{
		try {
			float temp_balance = atm.getBalance();
			atm.deposit(AMOUNT);
			assertTrue("Deposit failed: Expected " + (temp_balance + AMOUNT) + ", got " + atm.getBalance(), atm.getBalance() == (temp_balance + AMOUNT));
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/**
	 * 	Tests withdrawal method. It gets the current balance. Deposits AMOUNT value, withdraws "withdraw" value, then checks for the change in balance.
	 */
	@Test
	public void testWithdrawal() 
	{
		try {
			float withdraw = 200;
			float temp_balance = atm.getBalance();
			float expected;
			atm.deposit(AMOUNT);									// deposit more than we are going to withdraw
			atm.withdraw(withdraw);									
			expected = (temp_balance + AMOUNT) - withdraw;			// this is what we should get with atm.getbalance()
			assertTrue("Withdraw failed: Expected " + expected + ", got " + atm.getBalance(), atm.getBalance() == expected);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String [] argv)
	{
		junit.textui.TestRunner.run(ATMProxyJUnitTest.class);
	}
}
