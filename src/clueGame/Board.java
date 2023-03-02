// Sihang Wang, Michael Basey
package clueGame;

import java.util.HashMap;
import java.util.Map;

public class Board {
	// Variable Declaration
	private BoardCell grid[][];
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFiles;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
    private Board() {
           super();
    }
    
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    
	// Sets the locations of the layout and setup text files from parameters
	public void setConfigFiles(String layout, String setup) {
		 layoutConfigFile = layout;
		 setupConfigFiles = setup;
	}
	
	private void initialize() {
		
	}
	
	private void loadSettupConfig() {
		
	}
	
	private void loadLayoutConfig() {
		
	}
	
	
}


