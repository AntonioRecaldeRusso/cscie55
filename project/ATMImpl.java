// ATMImpl.java
package cscie55.project;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;


/**
 * 	This class implements the ATM interface. It simulates the actual ATM machine which has access to the accounts
 *	This is the class the Client wants to use. Hence, it extends UnicastRemoteObject so
 *	that its objects can be passed remotely after they have been picked up by the client
 *	via the ATMFactory
 * 
 * @author Antonio Recalde
 * @version 10/30/2013
 *
 */
public class ATMImpl extends UnicastRemoteObject implements ATM, Bank//, ATMListener
{	
	
	private float cash_available;					// how much cash this atm has
	Bank bank = null;								// placeholder for the bank we are going to lookup via rmi
	HashSet<ATMListener> atmListeners = new HashSet<ATMListener>();	//placeholder for clients registered as listeners

	
	/**
	 * Class constructor
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 */
	public ATMImpl() throws RemoteException
	{
		// Call to superclass exports a remote object with JRMP, obtaining a stub.
		super();
		cash_available = 500f;


		try {
			BankFactory factory = (BankFactory)Naming.lookup("//localhost/bankfactory");
			bank = factory.getBank();					// get the bank Remote object.
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
	
	/**
	 * Primarily calls the bank's rmi object's deposit() method. Also, sends a Transaction notification to the registered ATMListeners before and after the operation. 
	 */
	@Override
	public void deposit(AccountInfo accountInfo, float amount) throws RemoteException, IncorrectPINException, InsufficientPermissionsException 
	{
		TransactionNotification notification = new TransactionNotification(TransactionType.DEPOSIT, accountInfo.getAccountNumber(), amount);
		sendNotification(notification);
		
		bank.deposit(accountInfo, amount);
		
		transactionCompleted(notification);
	}

	/**
	 * Primarily calls the bank's rmi object's withdraw() method. Also, sends a Transaction notification to the registered ATMListeners before and after the operation. 
	 */
	@Override
	public void withdraw(AccountInfo accountInfo, float amount) throws RemoteException, InsufficientFundsException, InsufficientPermissionsException, IncorrectPINException, ATMInsufficientCashException 
	{
		TransactionNotification notification = new TransactionNotification(TransactionType.WITHDRAW, accountInfo.getAccountNumber(), amount);
		sendNotification(notification);
		
		if (cash_available < amount)
			throw new ATMInsufficientCashException();
		
		bank.withdraw(accountInfo, amount);
		cash_available -= amount;
		
		transactionCompleted(notification);
	}

	/**
	 * 	Primarily calls the bank's rmi object's getBalance() method. Also, sends a Transaction notification to the registered ATMListeners in the case that an exception occurs.
	 *	This method only notifies the ATMListeners whenever the operation fails. Otherwise, the Client prints the returned balance to stdout via
	 *	the println() method. This is done in such a way in order to match the output given in the project's specifications.
	 */
	@Override
	public float getBalance(AccountInfo accountInfo) throws RemoteException, InsufficientPermissionsException, IncorrectPINException 
	{
		TransactionNotification notification = new TransactionNotification(TransactionType.GETBALANCE, accountInfo.getAccountNumber());
		
		try {
			return bank.getBalance(accountInfo);
		} catch (IncorrectPINException e) {
			sendNotification(notification);					// If there is an exception, notify what type of transaction was going to take place.  and...
			throw e;										// re-throw the exception.. Otherwise, the Client would not know which transaction failed.
		}
	}

	/**
	 * 	Primarily calls the bank rmi object's transfer() method. It also notifies the user before the transaction takes place, and upon success.
	 * 	
	 */
	@Override
	public void transfer(AccountInfo origin_account, AccountInfo destination_account, float amount)	throws RemoteException, InsufficientFundsException 
	{
		TransactionNotification notification = new TransactionNotification(TransactionType.TRANSFER, origin_account.getAccountNumber(), destination_account.getAccountNumber(), amount);
		sendNotification(notification);
		
		bank.transfer(origin_account, destination_account, amount);
		
		transactionCompleted(notification);
	}

	/**
	 * 	Registers a ATMListener so that it may receive notifications
	 */
	@Override
	public void registerATMListener(ATMListener client) throws RemoteException 
	{
		atmListeners.add(client);
	}
	
	/**
	 * 	Unregisters ATMListener
	 */
	public void unregisterATMListener(ATMListener client) throws RemoteException
	{
		atmListeners.remove(client);
	}
	
	/**
	 * Calls receiveTransactionNotification() so that the client may get the notification printed to stdout.
	 * @param notification
	 * @throws RemoteException
	 */
	public void sendNotification(TransactionNotification notification) throws RemoteException
	{
		for (ATMListener atmListener : atmListeners)
		{
			atmListener.receiveTransactionNotification(notification);
		}
	}
	
	/**
	 * Calls transactionCompleted() in the Client class, with the intent of printing a notification message upon transaction success.
	 * @param notification
	 * @throws RemoteException
	 */
	public void transactionCompleted(TransactionNotification notification) throws RemoteException
	{
		for (ATMListener atmListener : atmListeners)
		{
			atmListener.transactionCompleted(notification);
		}
	}
}