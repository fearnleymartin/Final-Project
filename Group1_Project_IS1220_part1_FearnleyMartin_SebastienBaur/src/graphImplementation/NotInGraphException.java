package graphImplementation;

public class NotInGraphException extends Exception {

	/**
	 *  This Exception is thrown when a method attempts to have access to a Node in a graph which doesn't contain it
	 */
	
	
	public NotInGraphException() {
		// TODO Auto-generated constructor stub
	}

	public NotInGraphException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotInGraphException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NotInGraphException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotInGraphException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
