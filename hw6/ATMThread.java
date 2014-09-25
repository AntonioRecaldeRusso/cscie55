// ATMThread.java
package cscie55.hw6;

import java.util.ArrayList;

/**
 * This class is responsible for executing the requests in a request queue through ATMRunnable objects.
 *
 * @author Antonio Recalde Russo
 * @version 11/23/2013
 *
 */
public class ATMThread extends Thread
{
	private ArrayList<ATMRunnable> request_queue;
	private static int thread_counter = 0;						// this variable will be used to determine the id number
	private int thread_id;

	public ATMThread(ArrayList<ATMRunnable> request_queue)
	{
		this.request_queue = request_queue;
		thread_id = ++thread_counter;
	}

	@Override
	public void run()
	{
		while (true)
		{
			synchronized (request_queue) {

				/********************************************************************************
				 *
				 * If the queue is empty, it calls the wait() method of the request queue,
				 * thereby waiting to be notified when another thread puts a request on the queue.
				 *
				 ********************************************************************************/
				if (request_queue.isEmpty())
				{
					// System.out.println("WAIT:       " + this);
					try {
						request_queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				/*********************************************************************************
				 *
				 * When notified, this Thread begins its second and primary responsibility:
				 * It retrieves the ATMRunnable from the list and executes the request contained in it.
				 *
				 *********************************************************************************/
				else
				{
					ATMRunnable temp = request_queue.get(0);
					request_queue.remove(0);			// remove it from the list. If list is empty again, other threads will call wait.

					System.out.println("Running request in thread: " + this);
					new Thread(temp).start();			// starts the ATMRunnable, so it executes the request contained in it.



					/*****************************************************************************
					 *
					 * Finally, when the request has been executed, this Thread once again calls wait()
					 * on the request queue, ready to be notified of additional incoming requests.
					 *
					 ******************************************************************************/
					try {
						request_queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} // end else
			} // end synchronized
		} // end while
	} // end run


	public String toString()
	{
		return String.format("Thread-%d", thread_id);
	}
}
