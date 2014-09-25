// BankFactory
package cscie55.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface allows ATMImpl to get BankImpl objects using the Factory pattern.
 * @author Antonio Recalde
 * @version 12/10/2013
 *
 */
public interface BankFactory extends Remote
{
	public Bank getBank() throws RemoteException;
}
