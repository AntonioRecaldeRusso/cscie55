// ATMListener.java
package cscie55.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * This interface allows the communication between Client and ATMImpl. Additionally, allows the casting of objects passed 
 * via RMI
 * 
 * @author Antonio Recalde
 *
 */
public interface ATMListener extends Remote
{
	public void receiveTransactionNotification(TransactionNotification notification) throws RemoteException;
    public void registerClient(ATMListener client) throws RemoteException;
    public void transactionCompleted(TransactionNotification notification) throws RemoteException;
}
