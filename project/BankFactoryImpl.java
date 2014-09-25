// BankFactoryImpl.java
package cscie55.project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 	This class provides remote access to the BankImpl object via a Factory pattern 
 * 
 * @author Antonio Recalde
 * @version 12/10/2013
 *
 */
public class BankFactoryImpl extends UnicastRemoteObject implements BankFactory
{
	public BankFactoryImpl() throws RemoteException
	{
		super();
	}
	
	/**
	 * This method returns a new instance of the BankImpl class. This instance, allows access to methods that call functions in the
	 * Bank server.
	 */
	@Override
	public Bank getBank() throws RemoteException {
		return new BankImpl();
	}
}
