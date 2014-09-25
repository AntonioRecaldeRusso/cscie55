// Elevator.java
package cscie55.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	private Floor [] floors = new Floor [NO_OF_FLOORS];
	private boolean [] destination_requests = new boolean [NO_OF_FLOORS];		// tracks if floor is a passengers destination
	public ArrayList<Passenger> passengers_list = new ArrayList<Passenger>();	// passengers currently in elevator
	private boolean ascending = true;											// direction variable
    private int current_floor;


    /**
     * main method
     * @param args
     */
    public static void main(String [] args)
    {
        Elevator myElevator = new Elevator();

		/*
		 * The following segment allows for customization of the simulation.
		 * The first parameter in the method corresponds the the "floor number."
		 * The second parameter corresponds to the "number of passengers" to load this floor with
		 */
		myElevator.initializeFloor(0, 11);
		myElevator.initializeFloor(1, 5);
		myElevator.initializeFloor(2, 1);
		myElevator.initializeFloor(3, 1);
		myElevator.initializeFloor(4, 1);
		myElevator.initializeFloor(5, 1);
		myElevator.initializeFloor(6, 11);

        System.out.println("\n------- Starting Elevator -------\n");

        System.out.println(myElevator.toString());
        for (int i = 0; i < 96; i++)
			myElevator.move();

        System.out.println(myElevator.toString() + "------- End of simulation --------\n");

	/*	for (Floor f : floors)
		{
			System.out.println(f.floor_number + 1 + "----------------------------\n");
			System.out.println(f.residents);
		}
     */
    }

    /**
     * Constructor method for the Elevator class
     */
    public Elevator()
    {
        current_floor = 0;
		initializeFloorsArray();
	}

    /**
     * Moves the elevator up or down depending on its current direction. Calls the stop
     * method whenever there are requests for the elevator to stop at the current
     * floor, or some passengers inside the elevator are destined for the current floor
     */
    private void move()
    {
        if (ascending)
        {
			if ( (!floors[current_floor].up_service_queue.isEmpty()) || destination_requests[current_floor])
				stop();

			if (current_floor < NO_OF_FLOORS - 1)
				current_floor++;

			// Upon reaching the last floor (6), change direction.
			if (current_floor == (NO_OF_FLOORS - 1) )
				changeDirection();
        }
        else																	// means we are descending.
        {
			if ( (!floors[current_floor].down_service_queue.isEmpty()) || destination_requests[current_floor])
				stop();

			if (current_floor > 0)
				current_floor--;

			// bottom floor reached, change direction
			if (current_floor == 0)
				changeDirection();
        }
    }

	/**
	 * Initializes the floors array with Floor objects
	 */
	public void initializeFloorsArray()
	{
		for (int i = 0; i < NO_OF_FLOORS; i++)
			floors[i] = new Floor(i);
	}

	/**
	 * Initializes a floor in the floors array by loading it with passengers and
	 * setting requests for this floor to "true"
	 *
	 * @param starting_floor		The floor to be initialized with Passenger objects
	 * @param number_of_passengers	How many passengers will be loaded into the floor
	 */
	public void initializeFloor(int starting_floor, int number_of_passengers)
	{
		for (int i = 0; i < number_of_passengers; i++, Passenger.incrementIdCount())
		{
			int destination_floor;
			do {
				// Randomly assign a floor from 0 to 6
				destination_floor = (int) ( Math.random() * (NO_OF_FLOORS) );

			/*
			 * The while() statement checks to see if the destination_floor is equal to the current floor of the
			 * passenger.If this condition is true, a new random number shall be generated.
			 */
			} while (starting_floor == destination_floor);

			/*
			 * Next, creating a new Passenger object and routing him to his corresponding queue depending on whether he
			 * needs up or down service from the elevator.
			 */
			Passenger temp = new Passenger(Passenger.getIdCount(), starting_floor, destination_floor);
			floors[starting_floor].queueRouter(temp);
		}
	}


	/**
	 * This method simulates a passenger inside the elevator pushing the button that corresponds to his floor number
	 * in the elevator's console. The destination_requests[] array is updated to true in the index that corresponds to
	 * the intended destination.
	 *
	 * @param passenger		-The passenger that is requesting to stop at the particular destination
	 */
	public void requestDestination(Passenger passenger)
	{
		destination_requests[passenger.getDestination()] = true;
	}

	/**
	 * This method removes the current floor as a passenger destination for the elevator
	 *
	 * @param floor			-The floor that is to be removed as part of the itinerary
	 */
	public void removeAsDestination(int floor)
	{
		destination_requests[floor] = false;
	}

    /**
     * Changes the current direction of the Elevator object
     */
    private void changeDirection()
    {
        ascending = !ascending;
    }

    /**
     * Stops the elevator so that it can be loaded and/or unloaded
     */
    private void stop()
    {
        System.out.printf("\nStopping on floor %d\n", current_floor + 1);
		System.out.printf("\t\t%s -> calling floors[%d].unloadPassengers(...)\n", getClass(), current_floor);
        floors[current_floor].unloadPassengers(this);
        System.out.println(this.toString());
    }

    /**
     * Boards a passenger from Floor.passenger_queue into the elevator
     *
     * @param passenger_boarding	-- passenger getting into the elevator
     * @throws ElevatorFullException -- whenever a full elevator tries to be loaded
     */
    void boardPassenger(Passenger passenger_boarding) throws ElevatorFullException
    {
        if (passengers_list.size() >= CAPACITY)
        	throw new ElevatorFullException();

		System.out.printf("\t\t%s -> boarding passenger with id: %d\n", getClass(), passenger_boarding.getId() );
		passengers_list.add(passenger_boarding);								// equivalent to boarding passenger
		requestDestination(passenger_boarding);									// add passenger's destination to itinerary
    }

	/**
	 * Getter method for ascending variable
	 */
	public boolean getAscending()
	{
		return ascending;
	}
  

    /**
     * Returns a string object for the class
     */
    public String toString()
    {
        return String.format("\nCurrent floor: %d\n" +
				"Passengers on board: %d\n%s\n", current_floor + 1, passengers_list.size(), passengers_list.toString());
    }
}
