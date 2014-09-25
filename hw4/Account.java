// Account.java
package cscie55.hw4;

/**
 * This class creates an account object, which interacts with the ATM given the client instructions.
 * 
 * @author Antonio Recalde
 * @version 10/30/2013
 *
 */
public class Account
{
	float balance;
	
	/**
	 * Class constructor
	 */
	public Account()
	{
		balance = 0;
	}
	
	/**
	 * Returns the current balance in the account
	 * 
	 * @return balance Account balance
	 */
	public float getBalance()
	{
		return balance;
	}
	
	/**
	 * Sets the balance of the account to the given parameter
	 * 
	 * @param amount	New value of the account's balance
	 */
	public void setBalance(float amount)
	{
		balance = amount;
	}
	
	/**
	 * Deposits into account by incrementing the current balance by the value given as parameter
	 * 
	 * @param amount	Amount to be deposited
	 */
	public void deposit(float amount)
	{
		balance += amount;
	}
	
	/**
	 * Withdraws from account. Decreases balance by amount given as parameter
	 * 
	 * @param amount	Amount to be withdrawn
	 */
	public void withdraw(float amount)
	{
		balance -= amount;
	}
	
}
