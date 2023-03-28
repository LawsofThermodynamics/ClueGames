// Sihang Wang, Michael Basey
package clueGame;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException(){
		super("Bad formating of file detected");
	}

	public BadConfigFormatException(String e){
		super("Bad Config Format detected" + e);
	}


}
