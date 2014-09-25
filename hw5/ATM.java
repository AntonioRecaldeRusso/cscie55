// ATM.java
package cscie55.hw5;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface declares methods to be implemented remotely.
 * 
 * @author Antonio Recalde
 * @version 11/10/2013
 *
 */
public interface ATM extends Remote 
{
    public void deposit(int account_id, float amount) throws RemoteException;
    public void withdraw(int account_id, float amount) throws RemoteException;
    public Float getBalance(int account_id) throws RemoteException;
}

