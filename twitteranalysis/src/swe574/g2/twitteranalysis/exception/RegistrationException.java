package swe574.g2.twitteranalysis.exception;

@SuppressWarnings("serial")
public class RegistrationException extends Exception {
	public static final int DIFFERENT_PASSWORDS = 0;
	public static final int EXISTING_USER = 1; 
	
	private int type;
	
	public RegistrationException(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	@Override
	public String getMessage() {
		if (type == RegistrationException.DIFFERENT_PASSWORDS)
			return "Entered passwords must be the same.";
		
		if (type == RegistrationException.EXISTING_USER)
			return "Choose a different username.";
		
		return "Registration exception occurred.";
	}
}
