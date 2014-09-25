// Security.java
package cscie55.project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 * This class manages an account's authorization to perform a given transaction.
 * 
 * @author Antonio Recalde
 * @version 12/10/2013
 *
 */
public class Security extends UnicastRemoteObject
{
	protected Security() throws RemoteException 
	{
		super();
	}

	private static HashMap <Integer, Integer> numbers_and_pins = new HashMap<Integer, Integer>();				// stores account#-PIN combination
	private static HashMap <Integer, PermissionsData> permissions = new HashMap<Integer, PermissionsData>();		// stores permissions based on account#
	
	/**
	 * This method checks for a valid combination of account # and PIN. It returns true if the account# and PIN are correct
	 * 
	 * @param accountInfo
	 * @return
	 */
	public boolean authenticateAccountInfo(AccountInfo accountInfo)
	{		
		if (!numbers_and_pins.containsKey(accountInfo.getAccountNumber()))	
			return false;
		
		if (numbers_and_pins.get(accountInfo.getAccountNumber()).equals(accountInfo.getPIN()))
			return true;
		
		else return false;
	}
	
	/**
	 * This method returns true of the account has the permissions required to execute a given transaction
	 * 
	 * @param account_number
	 * @param command
	 * @return
	 */
	public boolean hasPermission(int account_number, Enum<TransactionType> command)
	{
		// Get the permissions data from the account whose # equals to the account_number parameter
		PermissionsData temp = permissions.get(account_number);				
		
		/*
		 *   Switch the command given by the Client, and depending on its value, return the variable that has the information about
		 *   their current permission status with regards to that type of transaction. 
		 */
		switch(command.toString())
		{
		case "DEPOSIT":
			return temp.canDeposit;
		case "WITHDRAW":
			return temp.canWithdraw;
		case "TRANSFER":
			return temp.canGetBalance;
		}
		return true;
	}
	
	/**
	 * Setter for account numbers and pin combination data.
	 * @param account
	 */
	public void set_numbers_and_pins(AccountInfo accountInfo)
	{
		numbers_and_pins.put(accountInfo.getAccountNumber(), accountInfo.getPIN());
	}
	
	/**
	 * Setter for account permissions data.
	 * @param account_number
	 * @param new_permissions
	 * @return
	 */
	public void setPermissions(int account_number, PermissionsData new_permissions)
	{
			permissions.put(account_number, new_permissions);
	}
}

/**
 * This inner class creates PermissionsData objects, which manage permission information for each account. The ATMImpl class knows whether
 * an account has the necessary permissions to execute a given task based on the values of the boolean fields defined within this class.
 */
class PermissionsData
{
	/*
	 *  The following variables store the state of the account's permission with regards to the type of transaction.
	 *  When true, it means that the account has permission to execute the transaction. E.g.: canDeposit is TRUE, the account can make deposits.
	 */
	boolean canDeposit;	
	boolean canWithdraw;
	boolean canGetBalance;
	
	/**
	 * Class constructor. 
	 * 
	 * @param deposit	determines whether deposit will be allowed
	 * @param withdraw	determines whether withdraw will be allowed
	 * @param balance	determines whether getBalance will be allowed
	 */
	PermissionsData(boolean deposit, boolean withdraw, boolean balance)
	{
		canDeposit = deposit;
		canWithdraw = withdraw;
		canGetBalance = balance;
	}
}