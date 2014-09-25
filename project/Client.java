// Client.java
package cscie55.project;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.server.UnicastRemoteObject;

import cscie55.project.AccountInfo;

/**
 * 
 *  This class implements the Client side of this App. It obtains a reference of a remote object, and subsequently performs a series of tests.
 *  
 * @author Antonio Recalde
 * @version 12/14/2013
 *
 */
public class Client implements ATMListener 
{
	ATM atm = null;
	/**
	 * Class constructor
	 * 
	 * @throws RemoteException
	 */
	public Client() throws RemoteException 
	{
        UnicastRemoteObject.exportObject(this, 0);		// Exporting this object so its methods can be called from the server side.			
    }
	
	/**
	 * Kicks off the functionality of this program.
	 */
	public void start()
	{
		try {
	    	
		       ATMFactory factory = (ATMFactory)Naming.lookup("//localhost/atmfactory");
		       atm = factory.getATM();
		       
		       /*
		        * 	Registering this client object as an ATMListener. This object will be passed remotely, to the ATMImpl class, which will use the
		        * 	receiveTransactionNotification to print messages to this client's stdout. 
		        */
			   atm.registerATMListener(this);
		       
			   if (atm != null)
				   testATM(atm);								// run tests
			   
			   atm.unregisterATMListener(this);				// remove this client as a listener

		    } catch (MalformedURLException mue) {
		       mue.printStackTrace();
		    } catch (NotBoundException nbe) {
		       nbe.printStackTrace();
		    } catch (UnknownHostException uhe) {
		       uhe.printStackTrace();
		    } catch (RemoteException re) {
		       re.printStackTrace();
		    } 
	}
	
//-----------------------------------------------------------------------------------------------------------------------------//	
//------------------------------------------------- Required code -------------------------------------------------------------//
//-----------------------------------------------------------------------------------------------------------------------------//
	
	 public static void testATM(ATM atm) {
	      if (atm!=null) {
	         printBalances(atm);
	         performTestOne(atm);
	         performTestTwo(atm);
	         performTestThree(atm);
	         performTestFour(atm);
	         performTestFive(atm);
	         performTestSix(atm);
	         performTestSeven(atm);
	         performTestEight(atm);
	         performTestNine(atm);
	         printBalances(atm);				
	      }
	   }
	 
	   public static void printBalances(ATM atm) {        
	      try {
	    	  System.out.println();
	    	  
	    	  System.out.println("Balance(0000001): "+atm.getBalance(getAccountInfo(0000001, 1234)));
	    	  System.out.println("Balance(0000002): "+atm.getBalance(getAccountInfo(0000002, 2345)));
	    	  System.out.println("Balance(0000003): "+atm.getBalance(getAccountInfo(0000003, 3456)));
	    	  System.out.println();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	   public static void performTestOne(ATM atm) {       
	      try {
	         atm.getBalance(getAccountInfo(0000001, 5555));
	      } catch (Exception e) {
	         System.out.println("Failed as expected: "+e);
	      }
	   }
	   public static void performTestTwo(ATM atm) {       
	      try {
	         atm.withdraw(getAccountInfo(0000002, 2345), 500);
	      } catch (Exception e) {
	         System.out.println("Failed as expected: "+e);
	      }
	   }
	   public static void performTestThree(ATM atm) {        
	      try {
	         atm.withdraw(getAccountInfo(0000001, 1234), 50);
	      } catch (Exception e) {
	         System.out.println("Failed as expected: "+e);
	      }
	   }
	   public static void performTestFour(ATM atm) {         
	      try {
	         atm.deposit(getAccountInfo(0000001, 1234), 500);
	      } catch (Exception e) {
	         System.out.println("Unexpected error: "+e);
	      }
	   }
	   public static void performTestFive(ATM atm) {         
	      try {
	         atm.deposit(getAccountInfo(0000002, 2345), 100);
	      } catch (Exception e) {
	         System.out.println("Unexpected error: "+e);
	      }
	   }
	   public static void performTestSix(ATM atm) {       
	      try {
	         atm.withdraw(getAccountInfo(0000001, 1234), 100);
	      } catch (Exception e) {
	         System.out.println("Unexpected error: "+e);
	      }
	   }
	   public static void performTestSeven(ATM atm) {        
	      try {
	         atm.withdraw(getAccountInfo(0000003, 3456), 300);
	      } catch (Exception e) {
	         System.out.println("Unexpected error: "+e);
	      }
	   }
	   public static void performTestEight(ATM atm) {        
	      try {
	         atm.withdraw(getAccountInfo(0000001, 1234), 200);
	      } catch (Exception e) {
	         System.out.println("Failed as expected: "+e);
	      }
	   }
	   public static void performTestNine(ATM atm) {        
	      try {
	         atm.transfer(getAccountInfo(0000001, 1234),getAccountInfo(0000002, 2345), 100);
	      } catch (Exception e) {
	         System.out.println("Unexpected error: "+e);
	      }
	   }
	   
 //----------------------------------------------------------------------------------------------------------------------------------//
 //----------------------------------------------------------------------------------------------------------------------------------//
 //----------------------------------------------------------------------------------------------------------------------------------//
  
	   
	   
	   
	   /**
	    * This method returns an Account info object.
	    * @param account_number
	    * @param PIN
	    * @return
	    */
	   public static AccountInfo getAccountInfo(int account_number, int PIN)
	   {
		   AccountInfo account = null;
		try {
			account = new AccountInfo(account_number, PIN);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		   return account;
	   }
	   
	   /**
	    * 
	    */
	   @Override
		public void registerClient(ATMListener client) throws RemoteException {
		}	
	   
	   /**
	    * This method prints the {@link TransactionNotification} sent by the ATMImpl into stdout.
	    */
		@Override
		public void receiveTransactionNotification(TransactionNotification notification) throws RemoteException 
		{
			System.out.println(notification);
		}

		/**
		 * This method prints to stdout a message upon a successful transaction.
		 */
		public void transactionCompleted(TransactionNotification notification)
		{
			if (notification.transaction_type.toString() == "TRANSFER")
				System.out.println(notification.transaction_type.toString() + " $" + notification.amount + " from account " + notification.main_account + " into account " + notification.accessory_account);
			else if (notification.transaction_type.toString() == "DEPOSIT")
				System.out.println(notification.transaction_type.toString() + " $" + notification.amount + " into account " + notification.main_account);
			else
				System.out.println(notification.transaction_type.toString() + " $" + notification.amount + " from account " + notification.main_account);
		}
		
		
		public static void main(String [] args) throws RemoteException
		{
			 Client client = new Client();					
			 client.start();								
			 
			 System.exit(0);								// exit the program, otherwise the object will be on standby for calls due that it was exported.
		}
}