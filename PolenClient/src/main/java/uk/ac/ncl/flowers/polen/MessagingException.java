package uk.ac.ncl.flowers.polen;


/**
 * Signals that a problem occurred when using the POLEN messaging system.
 * <p>
 * This class is a sublass of {@link Exception}.
 * </p>
 * @author Goksel Misirli
 *
 */
public class MessagingException extends Exception {
	
	/**
	 * Constructs a new {@code MessagingException} with the detailed message.
	 * @param message the detail message
	 */
	public MessagingException(String message)
	{
		super(message);
	}
	
	/**
	 * Constructs a new {@code MessagingException} with the specified detail message and the cause of the exception.
	 * @param message 	the detail message
	 * @param exception	the cause of the exception
	 */
	public MessagingException(String message,Exception exception)
	{
		super(message,exception);
	}
	
}
