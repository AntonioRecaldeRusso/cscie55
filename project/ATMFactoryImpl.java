// ATMFactoryImpl.java
package cscie55.project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *	This class provides the logic required by the getATM method from the ATMFactory.
 *	It overrides ATMFactory's getATM method to return a remote reference to an 
 *	ATM instance.
 *	
 *	@author: Antonio Recalde
 *	@version: 11/10/2013
 */
public class ATMFactoryImpl extends UnicastRemoteObject implements ATMFactory
{
	public ATMFactoryImpl() throws RemoteException
	{
		// Call to superclass exports a remote object with JRMP, obtaining a stub.
		super();
	}
	
	/**
	 * 	This method Overrides the getATM method declared in the ATMFactory interface.
	 * 	The return value of this method grants access to ATMImpl's methods, which in turn
	 * 	means access to the accounts.
	 * 	
	 * 	@return new ATMImpl()	A new instance of ATMImpl
	 */
	@Override
	public ATM getATM() throws RemoteException {
		
		// returning the ATM instance. This instance grants access to methods that modify accounts.
		return new ATMImpl();
	}
}
