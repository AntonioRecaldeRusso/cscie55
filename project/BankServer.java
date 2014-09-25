// BankServer
package cscie55.project;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * This class starts the Bank's server.
 * 
 * @author Antonio Recalde
 * @version 12/10/2013
 */
public class BankServer 
{
    public static void main(String [] args)  throws RemoteException
	{
		try{
			BankFactoryImpl object = new BankFactoryImpl();
			Naming.rebind("//localhost/bankfactory", object);
			System.out.println("--- Bound! ---");
		} catch (Exception e) {
			System.out.println("BankFactory instantiation or binding error");
			e.printStackTrace();
		}
	}
}