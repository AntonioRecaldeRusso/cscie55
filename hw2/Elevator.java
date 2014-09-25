// Elevator.java
package cscie55.hw2;

/**
 * This class runs a deterministic Elevator simulation. It runs in conjunction with 
 * Floor.java and ElevatorFullExeption.java. It utilizes a simple algorithm, changing
 * directions only when the elevator reaches the top or bottom floors.
 * 
 * @author Antonio Recalde
 * @version Last modified 09/24/2013
 *
 */
public class Elevator 
{
	public static final int CAPACITY = 10;							
	public static final int NO_OF_FLOORS = 7;
	private boolean ascending;						// direction variable
	private int current_floor;
	private int no_of_passengers;					// passengers currently in elevator	
	private Floor [] floors = new Floor [NO_OF_FLOORS];
	private int [] passenger_destinations = new int [NO_OF_FLOORS];
	private boolean [ ] floor_requests = new boolean [NO_OF_FLOORS];
	
	
	/**
	 * main method
	 * @param args
	 */
	public static void main(String [] args)
	{
		Elevator myElevator = new Elevator();
		
		/*
		 *  Endowing each Floor with a data member indicating how many passengers are 
		 *  waiting on the Floor to board the Elevator.
		 */
		myElevator.initializefloor(0, 9);
		myElevator.initializefloor(3, 4);
		myElevator.initializefloor(4, 8);
		myElevator.initializefloor(6, 9);
			
		
		System.out.println("\n------- Starting Elevator -------\n" + myElevator.toString());
		
		for (int i = 0; i < 99; i++)
			myElevator.move();					
		
		System.out.println(myElevator.toString() + "------- End of simulation --------\n");
	}
	
	
	
	
	/**
	 * Constructor method for the Elevator class
	 */
	public Elevator()
	{
		current_floor = 0;
		no_of_passengers = 0;
		initializeFloorsArray();
	}
	
	/**
	 * Initializes floors array with Floor objects
	 */
	private void initializeFloorsArray()
	{
		for (int i = 0; i < NO_OF_FLOORS; i++)
			floors[i] = new Floor(i);
	}
	
	/**
	 * Initializes a floor in the floors array by loading it with passengers and 
	 * setting requests for this floor to "true"
	 * 
	 * @param floor_number			The floor to be initialized
	 * @param number_of_passengers	How many passengers will be loaded into the floor
	 */
	private void initializefloor(int floor_number, int number_of_passengers)
	{
		floors[floor_number].passenger_queue = number_of_passengers;
		initializeFloorRequests(floor_number);
	}
	 
	/**
	 * Initializes all current request for an elevator. If there are passengers in 
	 * queue at a particular floor, it sets floor_requests[some_floor] to true
	 * 
	 * @param floor_number			Floor to be set to true
	 */
	private void initializeFloorRequests(int floor_number)
	{
		floor_requests[floor_number] = true;
	}
	
	/**
	 * Moves the elevator up or down depending on its current direction. Calls the stop
	 * method whenever there are requests for the elevator to stop at the current 
	 * floor, or some passengers inside the elevator are destined for the current floor
	 */
	public void move()
	{
		// Check if there are any requests for the Elevator to stop on this floor.
		if (floor_requests[current_floor] || (passenger_destinations[current_floor] > 0) )	
			stop();
		
		if (ascending)							
		{
			// Upon reaching the last floor (6), change direction.
			if (current_floor == (NO_OF_FLOORS - 1) )
				changeDirection();
			else
			current_floor++;
		}
		else										// means we are descending.
		{
			if (current_floor == 0)								
				changeDirection();					// Bottommost, so change direction
			else
				current_floor--;							
		}
	}
	
	/**
	 * Changes the current direction of the Elevator object
	 */
	public void changeDirection() 
	{
		ascending = !ascending;
	}
	
	/**
	 * Stops the elevator so that it can be loaded and/or unloaded
	 */
	public void stop()
	{
		System.out.printf("\nStopping on floor %d\n", current_floor + 1);
		floors[current_floor].unloadPassengers(this);
		System.out.println(this.toString());
	}
	
	/**
	 * Boards a passenger from Floor.passenger_queue into the elevator
	 * 
	 * @param floor	-- corresponds to the destination floor
	 * @throws ElevatorFullException -- whenever a full elevator tries to be loaded
	 */
	public void boardPassenger(int floor) throws ElevatorFullException 
	{
		no_of_passengers++;
		passenger_destinations[floor]++;
		if (no_of_passengers > CAPACITY)
		{
			no_of_passengers--;
			passenger_destinations[floor]--;
			throw new ElevatorFullException();
		}
	}
	
	/**
	 *  Accessor for "no_of_passengers" variable
	 * @return	no_of_passengers
	 */
	public int get_no_of_passengers()
	{
		return no_of_passengers;
	}
	
	/**
	 * Mutator for "no_of_passengers" variable
	 * @param number	-- new value for no_of_passengers
	 */
	public void set_no_of_passengers(int number)
	{
		no_of_passengers = number;
	}
	
	/**
	 *  Accessor for "passenger_destination"
	 *  
	 * @param floor	
	 * @return passenger_destinations[floor]
	 */
	public int getPassenger_destinations(int floor)
	{
		return passenger_destinations[floor];
	}
	
	/**
	 *  Mutator for "passenger_destinations []" array
	 *  
	 * @param floor	--Which floor is to be set
	 * @param value --New value of the particular floor
	 */
	public void setPassenger_destinations(int floor, int value)
	{
		passenger_destinations[floor] = value;
	}
	
	/**
	 * Mutator for floor_requsts array
	 * 
	 * @param floor	--Which floor is to be set
	 * @param bool	--new Boolean value for the variable in the specified index
	 */
	public void setFloor_requests(int floor, boolean bool)
	{
		floor_requests[floor] = bool;
	}
	
	/**
	 * Returns a string object for the class
	 */
	public String toString()
	{	
		return String.format(
				"Currently %d passengers onboard\n" +
				"Current Floor: %d\n", no_of_passengers, current_floor + 1);
	}
}
