package exceptions;

/**
 * This class is used to signal that CSV does not meet requested format.
 * Empty CSV is an example 
 * 
 * @author Stefan Boskovic
 */
@SuppressWarnings("serial")
public class InvalidCSVFormat extends Exception {
	private String message;
	
	public InvalidCSVFormat(String message) {
		this.message = message;
	}
	
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
}
