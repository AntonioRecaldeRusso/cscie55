// InsufficientPermissionsException
package cscie55.project;

/**
 * This Exception gets thrown whenever a transaction request is made and the accounts involved are not allowed to perform
 * such operation.
 * 
 * @author Antonio Recalde
 *
 */
public class InsufficientPermissionsException extends Exception 
{
	public InsufficientPermissionsException()
	{
		super();
	}
}
