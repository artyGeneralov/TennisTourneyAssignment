public class EmptyListException extends Exception {

	public EmptyListException() {
		this("EmptyListException");
	}

	public EmptyListException(String m) {
		super(m);
	}
}
