// Passenger.java

package cscie55.hw3;

/**
 * This class creates passenger objects in order to load them into the elevator
 * 
 * @author Antonio Recalde
 * Date: 26/09/13
 * Time: 10:21 AM
 *
 */
public class Passenger
{
	private static int id_count = 0;                     		// keeps track of the total number of passenger objects.
	private int id;                                            	// the id of this passenger objects.
    private int passenger_current_floor;                        // the floor the passenger is in
    private int destination;                                    // the intended destination when in the elevator

	/**
	 * Class constructor
	 */
	public Passenger() 	{}

	/**
	 * Class constructor
	 *
	 * @param id				-The id to be assignned to the passenger created
	 * @param current_floor		-The starting or current floor the passenger is in
	 * @param destination		-The intended destination of this passenger once he is in the elevator
	 */
	public Passenger(int id, int current_floor, int destination)
	{
		this.id = id;
		this.passenger_current_floor = current_floor;
		this.destination = destination;
	}

	/**
	 * This method updates the state of the passenger once he arrives at his destination floor
	 * @param floor_number		-The floor the passenger just arrived to
	 */
	public void arrive(int floor_number)
	{
		passenger_current_floor = floor_number;
	}

	/**
	 * This method increments the id_count variable.
	 */
	public static void incrementIdCount()
	{
		id_count++;
	}

	/**
	 * Getter method for id_count
	 * @return id_count
	 */
	public static int getIdCount()
	{
		return id_count;
	}

	/**
	 * Setter method for the id_count variable
	 * @param newId		-The new value of id_count
	 */
	public static void setId_count(int newId)
	{
		id_count = newId;
	}

	/**
	 * Getter method for the id variable
	 * @return id
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Setter method for the id variable
	 * @param newId		-The new value of id
	 */
	public void setId(int newId)
	{
		id = newId;
	}

	/**
	 * Getter method for passenger_current_floor
	 *
	 * @return passenger_current_floor
	 */
    public int getPassenger_current_floor()
    {
        return passenger_current_floor;
    }

	/**
	 * Getter method for the destination variable
	 * @return destination
	 */
    public int getDestination()
    {
        return destination;
    }

	/**
	 * Setter method for the destination variable
	 * @param new_destination	-The new value for destination
	 */
	public void setDestination(int new_destination)
	{
		destination = new_destination;
	}

	/**
	 * toString method for this class. Overrides Object.toString()
	 * @return
	 */
	public String toString()
	{
		return String.format("\tid: %d, start: %d, dest: %d\n", id, passenger_current_floor + 1, destination + 1);
	}
}
