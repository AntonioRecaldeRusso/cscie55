// Account.java
package cscie55.project;

/**
 * This class creates an account object. It has a variable "balance" that keeps track of the amount of money put into the account.
 * 
 * @author Antonio Recalde
 * @version 10/30/2013
 * @modified 12/14/2013
 *
 */
public class Account
{
	private float balance;
	
	/**
	 * Class constructor
	 */
	public Account()
	{
		balance = 0;
	}
	
	/**
	 * Class constructor
	 * @param balance
	 */
	public Account(float balance)
	{
		this.balance = balance;
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
	 * @return 
	 */
	public void deposit(float amount)
	{
		balance += amount;
	}
	
	/**
	 * Withdraws from account. Decreases balance by amount given as parameter
	 * 
	 * @param amount	Amount to be withdrawn
	 * @throws InsuficientFundsException 
	 */
	public void withdraw(float amount) throws InsufficientFundsException
	{
		if (this.getBalance() < amount)
			throw new InsufficientFundsException();	
		
		else
			balance -= amount;
	}
	
	@Override
	public String toString()
	{
		return String.format("Balance = %f", balance);
	}
}