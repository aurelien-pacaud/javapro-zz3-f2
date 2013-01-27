package fr.isima.exception;

/**
 * Exception levée si le binding ne peut être créé.
 */
public class CannotCreateBindingException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CannotCreateBindingException(String message) {
		
		super(message);
	}
}
