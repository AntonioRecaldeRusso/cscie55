// Bank.java
package cscie55.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface enables methods in BankImpl to be called by the ATMImpl's server side. Additionally, it allows the casting of Remote BankImpl objects
 * to be used in ATMImpl
 * @author admin
 *
 */
public interface Bank extends Remote
{
	public void deposit(AccountInfo accountInfo, float amount) throws RemoteException, IncorrectPINException, InsufficientPermissionsException;
    public void withdraw(AccountInfo accountInfo, float amount) throws RemoteException, InsufficientFundsException, IncorrectPINException, InsufficientPermissionsException, ATMInsufficientCashException;
    public void transfer(AccountInfo origin_account, AccountInfo destination_account, float amount) throws RemoteException, InsufficientFundsException;
	public float getBalance(AccountInfo accountInfo) throws RemoteException, IncorrectPINException, InsufficientPermissionsException;
}
