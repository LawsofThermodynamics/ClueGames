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
	
	public void initialize() {
		
	}
	
	// Sets the locations of the layout and setup text files from parameters
	public void setConfigFiles(String layout, String setup) {
		 layoutConfigFile = layout;
		 setupConfigFiles = setup;
	}

	public void loadSetupConfig() {
		
	}
	
	public void loadLayoutConfig() {
		
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	public Room getRoom(BoardCell cell) {
		return null;
	}
}


