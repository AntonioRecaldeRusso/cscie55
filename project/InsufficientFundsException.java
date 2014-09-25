// InsufficientFundsException.java
package cscie55.project;

/**
 * 
 * 	This Exception gets thrown whenever an attept to withdraw money is made, and the subject account does not have sufficient
 * 	funds to perform the operation.
 * 
 * @author Antonio Recalde
 *
 */
public class InsufficientFundsException extends Exception 
{
	public InsufficientFundsException()
	{
		super();
	}
}
