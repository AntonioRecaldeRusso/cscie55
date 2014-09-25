// BankImpl.java
package cscie55.project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 * This is the implementation class for the Bank. It implements the Bank interface, through which it can call methods in the Account class
 * 
 * @author Antonio Recalde
 * @version 12/10/2013
 *
 */
public class BankImpl extends UnicastRemoteObject implements Bank
{
	public static HashMap <Integer, Account> accounts_map = new HashMap<Integer, Account>();	// stores <account # / PIN combination>
	public Security security = new Security();		// This object will be used to authenticate accounts.

	public BankImpl() throws RemoteException
	{
		super();
		initializeDefaultAccounts();	// Initializing default accounts
	}
	
	/**
	 * Initializes the accounts required by the homework specifications. In total, 3. #0000001, #0000002, #0000003.
	 * It also initializes the values of their permissions (deposit, withdraw, getBalance)
	 */
	public void initializeDefaultAccounts()
	{
		try {
			createAccount(new AccountInfo(0000001, 1234), 0, new PermissionsData(true, true, true));		// initializes account # 1, with PIN 1234, with all permissions
			createAccount(new AccountInfo(0000002, 2345), 100, new PermissionsData(true, false, true));	
			createAccount(new AccountInfo(0000003, 3456), 500, new PermissionsData(false, true, true));
		} catch (RemoteException re) {
			re.printStackTrace();
		}
		
	}
	
	/**
	 * Creates an individual account, with it's permissions data.
	 * 
	 * @param accountInfo			provides account# and PIN
	 * @param starting_deposit		how much money will be in it at start
	 * @param permissions			what operations it is allowed to perform
	 */
	public void createAccount(AccountInfo accountInfo, float starting_deposit, PermissionsData permissions)
	{
		accounts_map.put(accountInfo.getAccountNumber(), new Account(starting_deposit));		// store object in collection
		security.set_numbers_and_pins(accountInfo);												// store # and PIN combination in collection inside the Security class
		security.setPermissions(accountInfo.getAccountNumber(), permissions);					// initialize permissions data for this account nuber
	} 

	/**
	 * Returns the current balance in the account.
	 */
	@Override
	public float getBalance(AccountInfo accountInfo) throws IncorrectPINException, InsufficientPermissionsException 
	{		
		// first, check if the account number and PIN combination matches.
		if (security.authenticateAccountInfo(accountInfo))
		{
			// check if the account has permission to perform this operation
			if (security.hasPermission(accountInfo.getAccountNumber(), TransactionType.GETBALANCE))
			{
				Account account = accounts_map.get(accountInfo.getAccountNumber());
				float balance = account.getBalance();
				return balance;
			}
			else
				throw new InsufficientPermissionsException();		// security.hasPermission() returned false.
		}
		else
			throw new IncorrectPINException();						// security.authenticateAccountInfo() returned false.
	}
	
	/**
	 * Withdraws money from account granted the user is allowed to perform the operation.
	 */
	@Override
	public void withdraw(AccountInfo accountInfo, float amount)	throws RemoteException, InsufficientFundsException, IncorrectPINException, InsufficientPermissionsException 
	{
		if (security.authenticateAccountInfo(accountInfo))		// check if pin is correct
		{
			if (security.hasPermission(accountInfo.getAccountNumber(), TransactionType.WITHDRAW))		// check if it has permission for the transaction
			{
				Account account = accounts_map.get(accountInfo.getAccountNumber());
				account.withdraw(amount);
			}
			else
				throw new InsufficientPermissionsException();
		}
		else
			throw new IncorrectPINException();
	}

	/**
	 * Deposits granted the account has permission to perform that operation. PIN validation is not requrired to deposit.
	 */
	@Override
	public void deposit(AccountInfo accountInfo, float amount) throws RemoteException, IncorrectPINException, InsufficientPermissionsException 
	{
		if (security.hasPermission(accountInfo.getAccountNumber(), TransactionType.DEPOSIT))
		{
			Account account = accounts_map.get(accountInfo.getAccountNumber());
			account.deposit(amount);
		}
	}

	/**
	 * Transfers from account origin to account destination granted the permissions are true. This method performs the operation by combining
	 * the use of WITHDRAW and DEPOSIT.
	 */
	@Override
	public void transfer(AccountInfo origin_account, AccountInfo destination_account, float amount) throws RemoteException, InsufficientFundsException
	{
		Account from = null;
		Account to = null;
		
		// only the withdrawal needs pin authentication. Thus only origin checked.
		if (security.authenticateAccountInfo(origin_account)) 		
		{
			// The origin account should be able to WITHDRAW...
			if (security.hasPermission(origin_account.getAccountNumber(), TransactionType.WITHDRAW))	
			{
				// ...and, the destination account should be able to accept deposits.
				if (security.hasPermission(destination_account.getAccountNumber(), TransactionType.DEPOSIT))
				{
					from = accounts_map.get(origin_account.getAccountNumber());	
					to = accounts_map.get(destination_account.getAccountNumber());
					
					from.withdraw(amount);								// withdraw from origin, and 
					to.deposit(amount);									// deposit in destination.
				}
			}
		}
	}
}
