package Exceptions;

public class NothingSelectedExceptions extends Exception{
	
	private static final long serialVersionUID = 1L;

	public NothingSelectedExceptions() {
		super("You need to choose a flight for edit");
		
	}
	
}
