// Server.java
package cscie55.hw5;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 *	This class starts up the Server process. It creates an instance of the factory,
 *	exports it, and registers it with the RMI registry.
 */
public class Server 
{
    
    public static void main(String [] args)  throws RemoteException
	{
		try{
			/* *****************************************************************
			 *	The next line: ATMFactoryImpl object = new ATMFactoryImpl();
			 
			 *	Instantiates a remote object... By creating an ATMFactoryImpl object, 
			 *	a stub is created via UnicastRemoteObject's constructor. Thus, this object
			 *	can be accessed remotely. 
			 *	Furthermore, the method getATM() which returns a ATM instance is 
			 *	defined in this class. This means that the remote object can grant remote access
			 * 	to the ATMImpl object, whose reference is indirectly acquired.
			 *
			 *******************************************************************/
			
			ATMFactoryImpl object = new ATMFactoryImpl();
			
			// Register object with the RMI registry so it can be looked up.
			Naming.rebind("//localhost/atmfactory", object);
			System.out.println("--- Bound! ---");
		} catch (Exception e) {
			System.out.println("ATMFactory instantiation or binding error");
			e.printStackTrace();
		}
	}
}