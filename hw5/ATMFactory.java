// ATMFactory.java
package cscie55.hw5;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *	This interface returns a remote reference to the serverside ATM instance.
 * 
 * @author Antonio Recalde
 * @version 11/10/2013
 *
 */
public interface ATMFactory extends Remote
{
	public ATM getATM() throws RemoteException;
}
