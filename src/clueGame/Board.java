// Sihang Wang, Michael Basey
package clueGame;

import java.util.HashMap;
import java.util.Map;

public class Board {
	// Variable Declaration
	private BoardCell grid[][];
	private int numRows;
	private int numColumns;
	private String layoutConfigFile = "ClueLayout.csv";
	private String setupConfigFiles = "ClueSetup.txt";
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private static Board theInstance;
	
	
	private void initialize() {
		
	}
	
	private void loadSettupConfig() {
		
	}
	
	private void loadLayoutConfig() {
		
	}
}


