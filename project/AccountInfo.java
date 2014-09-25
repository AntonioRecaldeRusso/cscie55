// AccountInfo.java
package cscie55.project;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 * 
 * 	This account creates AccountInfo objects that are used to transport account data and make them available to the relevant methods
 * 
 * @author Antonio Recalde
 * @version 12/14/2013
 *
 */
public class AccountInfo implements Serializable
{
	private int account_number;							// The account on which the operation will be performed
	private int PIN;									// The pin associated with this account
	
	public AccountInfo(int account_number, int PIN) throws RemoteException
	{
		super();
		this.setAccountNumber(account_number);
		this.setPIN(PIN);
	}
	
	/**
	 * 	This method returns account number
	 * 
	 * @return account_number		the int value that individualizes the account
	 */
	public int getAccountNumber() {
		return account_number;
	}

	/**
	 * 	Sets the account number
	 * 
	 * @param account_number
	 */
	public void setAccountNumber(int account_number) {
		this.account_number = account_number;
	}
	
	/**
	 * 	Returns PIN
	 * @return PIN
	 */
	public int getPIN() {
		return PIN;
	}

	/**
	 * Sets PIN
	 * @param pIN
	 */
	public void setPIN(int pIN) {
		PIN = pIN;
	}
}
