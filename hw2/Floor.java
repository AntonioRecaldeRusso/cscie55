// Floor.java
package cscie55.hw2;

/**
 * This class is used in conjuction with Elevator.java in order to run a simulation.
 * It creates Floor objects via its constructor. 
 * Each floor keeps track of the number of passengers waiting for an elevator. It 
 * unloads passengers from the elevator when they have reached their destination. 
 * It the calls boardPassenger(int floor) from the Elevator class, in order to board 
 * passengers from its passenger_queue.
 * 
 * @author Antonio Recalde
 * @version 09/24/2013
 */
public class Floor 
{
	private final int DESTINATION = 1;				// arbitrary passenger destination
	private int floor_number;						// answers: what floor is this?
	public int passenger_queue;						// people waiting for the lift
	
	/**
	 * Class constructor declaration
	 */
	public Floor(){}
	
	/**
	 * Class constructor
	 * @param number --this parameter assigns floors with their number for id purposes
	 */
	public Floor(int number)
	{
		floor_number = number;
	}
	
	/**
	 * This method unloads passengers from the elevator into the floor. It then calls 
	 * board passenger in order to board passengers from the queue into the elevator
	 * 
	 * @param elevator		Elevator object that has stopped at this floor
	 */
	public void unloadPassengers(Elevator elevator)
	{
		/* This line is equivalent to saying: 
		 * elevator.no_of_passengers -= elevator.passenger_destinations[floor_number];
		 * It basically removes the passengers who arrived at their destination from 
		 * the elevator passenger count
		 */
		elevator.set_no_of_passengers( 
				elevator.get_no_of_passengers() - 
				elevator.getPassenger_destinations(floor_number) );
		
		/* 
		 * Since we must assume that all passengers destined to this floor got off the 
		 * elevator, the number of passengers in the elevator currently having this 
		 * floor as their destination must be 0. Hence, passenger_destination for this
		 * floor is set to 0.
		`*/
		elevator.setPassenger_destinations(floor_number, 0);
	
		while (passenger_queue > 0)
		{
			try
			{
				elevator.boardPassenger(DESTINATION);	// throws ElevatorFullException
				
				// No exception means the passenger got boarded. Update queue
				passenger_queue--;
			} catch (ElevatorFullException e) {
				System.out.println(e);					// Prints message
				break;
			}
		}
		
		/* 
		 * If there are no more passengers in the queue. This floor is no longer an
		 * elevator stop. Update foor_requests array 
		 */
		if (passenger_queue == 0)									
			elevator.setFloor_requests(floor_number, false);		
	}
}
