// TransactionNotification.java
package cscie55.project;

import java.io.Serializable;

/**
 * This class creates TransactionNotification objects, which carry data about the transaction being attempted.
 * 
 * @author admin
 *
 */
public class TransactionNotification implements Serializable
{
	Enum<TransactionType> transaction_type;
	Integer main_account;								// the account requesting the transaction
	Integer accessory_account;							// this variable refers to the account we are transferring money to. 
	Float amount;
	
	/**
	 * Class constructor. To be used with GETBALANCE when an exception occurs.
	 * 
	 * @param transaction_type
	 * @param main
	 */
	public TransactionNotification(Enum<TransactionType> transaction_type, Integer main)
	{
		this.transaction_type = transaction_type;
		this.main_account = main;
	}
	
	/**
	 * Class constructor. To be used with DEPOSIT and WITHDRAW.
	 * @param transaction_type
	 * @param main
	 * @param amount
	 */
	public TransactionNotification(Enum<TransactionType> transaction_type, Integer main, float amount)
	{
		this.transaction_type = transaction_type;
		this.main_account = main;
		this.amount = amount;
	}
	
	/**
	 * Class constructor. To be used with TRANSFER.
	 * 
	 * @param transaction_type
	 * @param main
	 * @param accessory
	 * @param amount
	 */
	public TransactionNotification(Enum<TransactionType> transaction_type, Integer main, Integer accessory, float amount)
	{
		this.transaction_type = transaction_type;
		this.main_account = main;							// account we're transferring from
		this.accessory_account = accessory;					// account we're transferring to...
		this.amount = amount;
	}
	
	public String toString()
	{
		String str_accessory = (accessory_account == null)? "" : " -->Transfer to account #: " + accessory_account.toString();
		String str_amount = (amount == null)? "" : ", Amount: " + amount.toString();
		return String.format("\n<Attempting: %s%s, Account #: %d%s>", transaction_type.toString(), str_amount, main_account, str_accessory);
	}
}
