// Floor.java
package cscie55.hw3;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is used in conjuction with Elevator.java in order to run a simulation.
 * It creates Floor objects via its constructor.
 * Each floor keeps track of the number of passengers waiting for an elevator. It
 * unloads passengers from the elevator when they have reached their destination.
 * It the calls boardPassenger(int floor) from the Elevator class, in order to board
 * passengers from its passenger_queue.
 *
 * @author Antonio Recalde
 * @version 09/30/2013
 */
public class Floor
{
    private int floor_number;											// answers: what floor is this?
	public ArrayList<Passenger> up_service_queue = new ArrayList<Passenger>();	// queue for up service
	public ArrayList<Passenger> down_service_queue = new ArrayList<Passenger>();// queue for down service
	private ArrayList<Passenger> residents	= new ArrayList<Passenger>();


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
	 * Routs passenger into ArrayList in accordance to his desired direction. E.g. up service, or down service.
	 * @param passenger   	The passenger that wants to go somewhere
	 */
	public void queueRouter(Passenger passenger)
	{
		if (passenger.getDestination() > passenger.getPassenger_current_floor())
			up_service_queue.add(passenger);

		else if (passenger.getDestination() < passenger.getPassenger_current_floor())
			down_service_queue.add(passenger);
	}

    /**
     * This method unloads passengers from the elevator into the floor. It then calls
     * board passenger in order to board passengers from the queue into the elevator
     *
     * @param elevator		Elevator object that has stopped at this floor
     */
    public void unloadPassengers(Elevator elevator)
    {
		// Loop until iterator has reached its last element
		for (Iterator<Passenger> p = elevator.passengers_list.iterator(); p.hasNext(); )
		{
			Passenger temp = p.next();									// creating temporary placeholder for disembarking Passenger
			if (temp.getDestination() == floor_number)					// if this floor is the destination...
			{
				System.out.printf("\t\t%s -> Unloading passenger with id: %d\n", getClass(),temp.getId());
				p.remove();												// remove passenger from the elevator
				temp.arrive(floor_number);								// update passenger's state
				this.residents.add(temp);								// add removed passenger to the residents list
			}

		}

		/*
		 *	The following conditional operator makes a copy of the relevant queue of passengers based on the elevators
		 *	direction, into the variable "queue."
		 *	If "ascending" is true, we only want to make use of "up_service_queue" because passengers going to lower
		 *	floors won't board the elevator in this pass.
		 *	Likewise, when the elevator is descending, passengers going up will not board.
		 *	When the elevator reaches the bottommost, or top floor, the move() method will immediately update the
		 *	direction of the elevator so that this conditional operator always works.
		 */
		ArrayList<Passenger> queue = ( elevator.getAscending() )? up_service_queue : down_service_queue;
		Iterator<Passenger> passenger_iterator = queue.iterator();
		while (passenger_iterator.hasNext())
		{
			try
			{
				System.out.printf("\t\t%s  -> calling myElevator.boardPassenger(...)\n", getClass());
				elevator.boardPassenger(passenger_iterator.next());		// throws ElevatorFullException

				// if here, the passenger boarded. So, remove him from the queue he was in.
				passenger_iterator.remove();
			} catch (ElevatorFullException e) {
				System.out.println(e);
				break;
			}
		}

		/*
		 * As floors can never get full, we assume that all the passengers destined to this floor disembarked.
		 * Thus, this floor is no longer a destination for any of the elevator's current passengers.
		 */
		elevator.removeAsDestination(floor_number);
	}

	/**
	 * Getter for up_service_queue
	 * @return up_service_queue
	 */
	public ArrayList<Passenger> getUp_service_queue()
	{
		return up_service_queue;
	}

	/**
	 * Setter for up_service_queue
	 * @param up_service_queue
	 */
	public void setUp_service_queue(ArrayList<Passenger> up_service_queue)
	{
		this.up_service_queue = up_service_queue;
	}

	/**
	 * Getter for down_service_queue
	 * @return down_service_queue
	 */
	public ArrayList<Passenger> getDown_service_queue()
	{
		return down_service_queue;
	}

	/**
	 * Setter for down_service_queue
	 * @param down_service_queue
	 */
	public void setDown_service_queue(ArrayList<Passenger> down_service_queue)
	{
		this.down_service_queue = down_service_queue;
	}

	/**
	 * Getter for residents
	 * @return residents
	 */
	public ArrayList<Passenger> getResidents()
	{
		return residents;
	}

	/**
	 * Setter for residents
	 * @param residents
	 */
	public void setResidents(ArrayList<Passenger> residents)
	{
		this.residents = residents;
	}
}