// Server.java
package cscie55.hw6;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * This class starts the ATM server. This server works as a asynchronous request-dispatching engine.
 * It creates a pool of threads responsible for executing the client commands. The Server
 * listens for client commands and then passes them to an ATMRunnable object for execution.
 *
 * @author Antonio Recalde Russo
 * @version 11/23/2013
 */
public class Server {

	private ServerSocket serverSocket;
	private BufferedReader bufferedReader;

	// creating thread pool. This pool will run the threads
	private ATMThread [] thread_pool = new ATMThread[5];
	private ArrayList<ATMRunnable> request_queue = new ArrayList<ATMRunnable>();

	/**
	 * Class constructor
	 *
	 * @param port
	 * @throws java.io.IOException
	 */
	public Server(int port) throws java.io.IOException
	{
		serverSocket = new ServerSocket(port);
		for (int i = 0; i < 5; i++)
		{
			thread_pool[i] = new ATMThread(request_queue);
			thread_pool[i].start();
		}
	}

	/**
	 *  This class accepts a client connection and reads lines from the socket.
	 */
	public void serviceClient () throws java.io.IOException {
		System.out.println("Starting server socket on port " + serverSocket.getLocalPort());
		System.out.println("Listening for a client request...");
		while (true)
		{
			Socket clientConnection = serverSocket.accept();
			// Arrange to read input from the Socket
			InputStream inputStream = clientConnection.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			// This variable  will be used to store the string incoming from the Buffered reader, one line at a time.
			String commandLine;

			/**************************************************************************
			 *
			 * The following try block will read from BufferedReader, start a ATMRunnable with
			 * the client's command as a parameter, and notify all objects competing for the
			 * request_queue monitor to resume.
			 * Each time we add an object to request_queue, a thread inside the thread_pool
			 * will start executing the client commands via the ATMRunnable.
			 *
			 **************************************************************************/
			try {
				while((commandLine = bufferedReader.readLine()) != null) {

					synchronized (request_queue) {
						request_queue.add(new ATMRunnable(commandLine, clientConnection));
						request_queue.notifyAll(); 			// indicates the element of work ATMRunnable requires processing
					}
				}
			} catch (SocketException se) {
				// There are no more commands. End.
			}

		}
	}

	public static void main(String argv[]) {
		int port = 1099;
		if(argv.length > 0) {
			try {
				port = Integer.parseInt(argv[0]);
			} catch (Exception e) { }
		}
		try {
			Server server = new Server(port);
			server.serviceClient();
			System.out.println("Client serviced");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
