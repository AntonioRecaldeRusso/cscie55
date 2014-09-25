// IncorrectPinException.java
package cscie55.project;

/**
 * This exception gets thrown when the Account# and PIN combination in a transaction request is incorrect.
 * 
 * @author Antonio Recalde
 *
 */
public class IncorrectPINException extends Exception 
{
	public IncorrectPINException()
	{
		super();
	}
}
