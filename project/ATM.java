// ATM.java
package cscie55.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface declares methods to be implemented remotely by Client, ATMImpl.
 * 
 * @author Antonio Recalde
 * @version 11/10/2013
 *
 */
public interface ATM extends Remote 
{
    public void deposit(AccountInfo accountInfo, float amount) throws RemoteException, InsufficientPermissionsException, IncorrectPINException;
    public void withdraw(AccountInfo accountInfo, float amount) throws RemoteException, InsufficientFundsException, InsufficientPermissionsException, IncorrectPINException, ATMInsufficientCashException;
    public void transfer(AccountInfo origin_account, AccountInfo destination_account, float amount) throws RemoteException, InsufficientFundsException;
    public float getBalance(AccountInfo accountInfo) throws RemoteException, IncorrectPINException, InsufficientPermissionsException;
	public void registerATMListener(ATMListener client) throws RemoteException;
    public void unregisterATMListener(ATMListener client) throws RemoteException;
}

