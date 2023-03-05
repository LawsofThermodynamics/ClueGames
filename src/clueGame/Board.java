// Sihang Wang, Michael Basey
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {
	// Variable Declaration
	private BoardCell grid[][];
	private int numRows;
	private int numColumns;
	private String layoutConfigFile = "data//";
	private String setupConfigFiles = "data//";
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	
	private boolean debugger = true; // True for console debug statements

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
		loadSetupConfig();
		loadLayoutConfig();
		if(debugger) {
			for(int rowCount = 0; rowCount < grid.length; rowCount++)
			{

				for(int colCount = 0; colCount < grid[rowCount].length; colCount++) {
					System.out.print("|" + grid[rowCount][colCount].getInitial());
				}
				System.out.println("|");
			}
		}
		

	}

	// Sets the locations of the layout and setup text files from parameters
	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile += layout;
		setupConfigFiles += setup;
	}

	// Loads data from setupConfigFiles
	// Note!! currently just prints to console
	public void loadSetupConfig() {
		try {
			FileReader reader = new FileReader(setupConfigFiles);// Opens file
			Scanner in = new Scanner(reader);
			
			String tempStr = "";

			while(in.hasNextLine()) {
				tempStr = in.nextLine();
				if(debugger) {System.out.println(tempStr);}
			}
			in.close(); // Close file

		} catch (FileNotFoundException e){
			System.out.println("Setup Config File Not Loaded Correctly");
		}
	}

	// Loads in data from layoutConfigFile.csv, sends data to cells and updates cell information
	public void loadLayoutConfig() {
		String tempStr = "";
		try {
			FileReader reader = new FileReader(layoutConfigFile);// Opens file
			Scanner in = new Scanner(reader);



			// Reads data from file
			while(in.hasNextLine()) {
				numRows++;
				tempStr = in.nextLine();
			}

			in.close(); // Close file			

		} catch (FileNotFoundException e){
			System.out.println("Layout Config File Not Loaded Correctly");
		}

		String[] arrOfStr = tempStr.split(",");
		numColumns = arrOfStr.length;
		if(debugger) {System.out.println("Rows: " + numRows); }
		if(debugger) {System.out.println("Columns: " + numColumns);	}		

		grid = new BoardCell[numRows][numColumns];

		int rowCount;
		int colCount;


		for (rowCount = 0; rowCount < numRows; rowCount++) {
			for (colCount = 0; colCount < numColumns; colCount++) {
				grid[rowCount][colCount] = new BoardCell();
			}
		}

		rowCount = 0;
		colCount = 0;

		try {
			FileReader reader = new FileReader(layoutConfigFile);// Opens file
			Scanner in = new Scanner(reader);

			in.useDelimiter("[,\n]"); // Tells scanner that a comma separates each data cell

			// Reads data from file
			while(in.hasNext()) {
				String temp = in.next();

				if (colCount == numColumns) {
					colCount = 0;
					rowCount++;
				}
				
				if(debugger) {System.out.println(temp); }

				grid[rowCount][colCount].setInitial(temp.charAt(0));

				// Checks data read in to determine how to process data
				switch (temp.length()){
				case 2: // If string in cell is 2 chars long, it must be a special cell
					if (temp.charAt(1) == '*') { // Sets cell as center
						grid[rowCount][colCount].setRoomCenter(true);
					} else if (temp.charAt(1) == '#') { // Sets cell as label
						grid[rowCount][colCount].setRoomLable(true);
					} else if (temp.charAt(1) != '\r') { // Sets door status and direction based on arrow direction
						grid[rowCount][colCount].setDoor(true);
						if (temp.charAt(1) == '<') {
							grid[rowCount][colCount].setDoorDirection(DoorDirection.LEFT);
						} else if (temp.charAt(1) == '^') {
							grid[rowCount][colCount].setDoorDirection(DoorDirection.UP);
						} else if (temp.charAt(1) == '>') {
							grid[rowCount][colCount].setDoorDirection(DoorDirection.RIGHT);
						} else if (temp.charAt(1) == 'v') {
							grid[rowCount][colCount].setDoorDirection(DoorDirection.DOWN);
						}
					}
					break;
				case 3: // If string in cell is 3 chars long, it must be a secret passage
					grid[rowCount][colCount].setSecretPassage(temp.charAt(2)); // Sets passage location to third char
					break;

				default:
					break;
				}
				colCount++;
			}
			in.close(); // Close file

		} catch (FileNotFoundException e){
			System.out.println("File not found");
		}
	}


	// Getters and setters
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

	public Room getRoom(char c) {
		Room room1 = new Room("Test Room"); // For tests to fail
		return room1;
	}



}


