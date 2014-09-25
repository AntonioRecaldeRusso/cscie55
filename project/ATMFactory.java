// ATMFactory.java
package cscie55.project;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *	This interface returns a remote reference to the server-side ATM instance.
 * 
 * @author Antonio Recalde
 * @version 11/10/2013
 *
 */
public interface ATMFactory extends Remote
{
	public ATM getATM() throws RemoteException;
}
