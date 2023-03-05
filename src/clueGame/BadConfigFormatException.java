// Sihang Wang, Michael Basey
package clueGame;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException(){
		super("Bad format detected");
	}

	public BadConfigFormatException(String error){
		super("Bad format detected: " + error);
	}


}
