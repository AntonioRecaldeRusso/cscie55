// ATMImpl.java
package cscie55.hw5;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.HashMap;



/**
 * 	This class implements the ATM interface. It simulates the actual ATM machine which has access to the accounts
 *	This is the class the Client wants to use. Hence, it extends UnicastRemoteObject so
 *	that its objects can be passed remotely after they have been picked up by the client
 *	via the ATMFactory
 * 
 * @author Antonio Recalde
 * @version 10/30/2013
 *
 */
public class ATMImpl extends UnicastRemoteObject implements ATM
{	
	// Collection used to store accounts by id.
	public Map<Integer, Account> accounts_by_id = new HashMap<Integer, Account>();
	
	/**
	 * Class constructor
	 * @throws RemoteException 
	 */
	public ATMImpl() throws RemoteException
	{
		// Call to superclass exports a remote object with JRMP, obtaining a stub.
		super();
		
		
		accounts_by_id.put(1, new Account());
		
		accounts_by_id.put(2, new Account());
		accounts_by_id.get(2).setBalance(100);
		
		accounts_by_id.put(3, new Account());
		accounts_by_id.get(3).setBalance(500);
		
	}
	
	/**
	 * Deposits a float amount to a specific account based on its account_id value
	 * 
	 * @param amount The amount to be deposited
	 */
	@Override
	public void deposit(int account_id, float amount) throws RemoteException
	{
		accounts_by_id.get(account_id).deposit(amount);
	}
	
	/**
	 * Withdraws float amount from a specific account based on its account_id value
	 * 
	 * @param amount	The amount being withdrawn
	 */
	@Override
	public void withdraw(int account_id, float amount) throws RemoteException
	{
		accounts_by_id.get(account_id).withdraw(amount);		
	}
	
	/**
	 * Returns current balance in the account.
	 * 
	 * @return	account.balance	balance in the account
	 */
	@Override
	public Float getBalance(int account_id) throws RemoteException
	{
		return accounts_by_id.get(account_id).getBalance();
	}
}
