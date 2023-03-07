// Sihang Wang, Michael Basey
package clueGame;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException(){
		super("Bad format detected");
	}

	public BadConfigFormatException(String e){
		super("Bad format detected: " + e);
	}


}
