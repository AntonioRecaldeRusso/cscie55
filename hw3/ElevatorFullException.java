// ElevatorFullException.java
package cscie55.hw3;

/**
 * This class extends Exception. It creates an ElevatorFullException object that gets
 * triggered when a full elevator tries to be loaded with more passengers, in the
 * Elevator class.
 *
 * @author Antonio Recalde
 * @version 09/24/2013
 */
@SuppressWarnings("serial")
public class ElevatorFullException extends Exception
{
    public ElevatorFullException()
    {
        super("\n\n---------------------------------------------------\n" +
				"-----------------------------------------------------\n" +
				"-----------------ELEVATOR FULL!----------------------\n" +
				"-----------------------------------------------------\n" +
				"-----------------------------------------------------\n");
    }

}
