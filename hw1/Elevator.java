// Elevator.java
package cscie55.hw1;

/**
 * This class runs a deterministic Elevator simulation.
 * It utilizes a simple algorithm, changing directions only when the elevator reaches the top or bottom floors.
 * @author Antonio Recalde
 * @version Last modified 09/15/2013
 *
 */
public class Elevator 
{
	private static final int CAPACITY = 10;
	private static final int NO_OF_FLOORS = 7;
	
	private boolean ascending;									// this is the Direction variable. If true, Elevator is going up.					 	
	private int [] destination_requests = {0, 0, 0, 0, 0, 0, 0};// this array tracks how many passengers are destined to each floor.							
	private int current_floor;									// this variable tracks the floor the elevator is currently in.
	private int no_of_passengers;								// number of passengers currently in the elevator.				
	
	/**
	 * main method
	 * @param args
	 */
	public static void main(String [] args)
	{
		Elevator myElevator = new Elevator();
		myElevator.boardPassenger(2);
		myElevator.boardPassenger(2);
		myElevator.boardPassenger(3);
		
		System.out.println(myElevator.toString());
		
		for (int i = 0; i < (NO_OF_FLOORS * 6); i++)
			myElevator.move();
	}
	
	/**
	 * Constructor
	 */
	public Elevator()
	{
		current_floor = 0;										
		no_of_passengers = 0;
	}
	
	/**
	*	This method moves the elevator by incrementing/decrementing the current_floor variable. It changes the direction of the elevator via
	*	changeDirection() every time it reaches the top or bottom floors.
	*/
	private void move()
	{
		if (destination_requests[current_floor] > 0)			// Check if there are any requests for the Elevator to stop on this floor.	
			stop();
		if (ascending)											// if Direction is UP...
		{
			if (current_floor == (NO_OF_FLOORS - 1) )			// Upon reaching the last floor (6), change direction.
				changeDirection();
			current_floor++;
		}
		else													// means we are descending.
		{
			if (current_floor == 0)								
				changeDirection();								// Since we are in floor 0, change direction.
			else
				current_floor--;							
		}
	}
	
	/**
	 * This method stops the Elevator in order to either discharge and/or board passengers.
	 */
	private void stop()
	{
		System.out.printf("\nStopping on floor %d\n", current_floor + 1);
		dischargePassengers();
		System.out.println(this.toString());
	}
	
	/**
	 * This method discharges passengers from the Elevator. It uses the destination_requests[] array in order to determine
	 * how many passengers should be discharged.
	 */
	private void dischargePassengers()
	{
		int unloading = destination_requests[current_floor];		// extracting value of array as it will mutate inside for loop.
		for (int i = 0; i < unloading; i++)
		{
			no_of_passengers--;	
			destination_requests[current_floor]--;
		}
	}
	
	/**
	 * This method boards passengers destined to a given floor into the Elevator.
	 * @param Floor 	this parameter indicates the floor destination of the passenger
	 */
	private void boardPassenger(int floor)
	{
		no_of_passengers++;										
		destination_requests[floor - 1]++;							// update destinations. Using (floor-1) since floor 0 is first floor.
	}
	
	/**
	 * This method changes the direction of the Elevator.
	 */
	private void changeDirection()
	{
		ascending = !ascending;
		move();
	}
	
	/**
	 * Creates a String Object for the class
	 */
	public String toString()
	{
		return String.format(
				"Currently %d passengers onboard\n" +
				"Current Floor: %d\n", no_of_passengers, current_floor + 1);
	}
}