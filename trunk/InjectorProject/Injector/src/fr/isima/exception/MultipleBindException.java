package fr.isima.exception;

/**
 * Exception levée si une même interface doit être injecté par plusieurs implémentations.
 */
public class MultipleBindException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public MultipleBindException(String message) {
		
		super(message);
	}
}
