// ATMRunnable.java
package cscie55.hw6;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * The class ATMRunnable is responsible for executing client requests. ATMRunnable is a work-order object. It defines
 * a method run() that does the following:
 * 1) Parses the request string to determine which operation (deposit, withdraw, balance) is required and the amount for deposit/withdraw.
 * 2) Invokes the appropriate method through the ATM interface.
 * 3) Writes a message back to the client, reporting the result.
 *
 * @author Antonio Recalde Russo
 * @version 11/23/2013
 */
public class ATMRunnable implements Runnable
{
	public static ATMImplementation atmImplementation = new ATMImplementation();	// this is static because account is the same to all instances of ATMRunnable

	String transaction_string;								// these will store the commands sent by the client
	Socket client_connection;								// the socket connection we will use to write back to the client

	OutputStream outputStream;
	PrintStream printStream;


	/**
	 * Class constructor
	 * @param transaction_string String that carries the client's command to the ATM
	 * @param client_connection The connection between client and server. It is used when writing back to the client.
	 * @throws IOException
	 */
	public ATMRunnable(String transaction_string, Socket client_connection) throws IOException
	{
		this.transaction_string = transaction_string;
		this.client_connection = client_connection;
	}

	/**
	 * This method runs when the tread starts.
	 */
	@Override
	public void run()
	{
		// Arrange to write result across Socket back to client
		try {
			outputStream = client_connection.getOutputStream();
			printStream = new PrintStream(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}


		try {
			// Parse transaction string into command then executes appropriate Account method through ATM interface, then store returned value
			Float result = executeCommand(transaction_string);


			if(result != null)  								// Only BALANCE command returns non-null
			{
				printStream.println(result);  					// Write it back to the client
			}


		} catch (ATMException atmex) {
			System.out.println("ERROR: " + atmex);
		}
	}


	/**
	 *  The logic here is specific to our protocol.  We parse the string
	 *  according to that protocol.
	 */
	private Float executeCommand(String commandLine) throws ATMException {
		// Break out the command line into String[]
		StringTokenizer tokenizer = new StringTokenizer(commandLine);
		String commandAndParam[] = new String[tokenizer.countTokens()];
		int index = 0;
		while(tokenizer.hasMoreTokens()) {
			commandAndParam[index++] = tokenizer.nextToken();
		}
		String command = commandAndParam[0];
		// Dispatch BALANCE request without further ado.
		if(command.equalsIgnoreCase(Commands.BALANCE.toString())) {
			return atmImplementation.getBalance();
		}
		// Must have 2nd arg for amount when processing DEPOSIT/WITHDRAW commands
		if(commandAndParam.length < 2) {
			throw new ATMException("Missing amount for command \"" + command + "\"");
		}
		try {
			float amount = Float.parseFloat(commandAndParam[1]);
			if(command.equalsIgnoreCase(Commands.DEPOSIT.toString())) {
				atmImplementation.deposit(amount);
			}
			else if(command.equalsIgnoreCase(Commands.WITHDRAW.toString())) {
				atmImplementation.withdraw(amount);
			} else {
				throw new ATMException("Unrecognized command: " + command);
			}
		} catch (NumberFormatException nfe) {
			throw new ATMException("Unable to make float from input: " + commandAndParam[1]);
		}
		// BALANCE command returned result above.  Other commands return null;
		return null;
	}
}
