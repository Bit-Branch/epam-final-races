package by.malinouski.horserace.exception;

public class UserDAOException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserDAOException() {
		// TODO Auto-generated constructor stub
	}

	public UserDAOException(String reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	public UserDAOException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


	public UserDAOException(String reason, Throwable cause) {
		super(reason, cause);
		// TODO Auto-generated constructor stub
	}

}
