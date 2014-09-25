// ATMInsufficientCashException.java
package cscie55.project;

/**
 * This exception gets thrown whenever the ATM machine gets an order to withdraw a larger amount than what it holds.
 * 
 * @author Antonio Recalde
 *
 */
public class ATMInsufficientCashException extends Exception
{
	public ATMInsufficientCashException()
	{
		super();
	}
}
