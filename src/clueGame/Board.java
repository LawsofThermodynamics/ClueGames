// Sihang Wang, Michael Basey
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import experiment.TestBoardCell;

import java.lang.Character;

public class Board {
	// Variable Declaration
	private BoardCell grid[][];
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFiles;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private Set<BoardCell> targetCells = new HashSet<BoardCell> ();

	private boolean debugger = false; // True for console debug statements

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
		try {
			grid = null; // Reset grid for multiple tests
			
			// Load data from config files
			loadSetupConfig();
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
		}

		// Prints grid data to console if debugger true
		if (debugger) {
			for (int rowCount = 0; rowCount < grid.length; rowCount++) {
				for (int colCount = 0; colCount < grid[rowCount].length; colCount++) {
					System.out.print("|" + grid[rowCount][colCount].getInitial());
				}
				System.out.println("|");
			}
		}

	}

	// Sets the locations of the layout and setup text files from parameters
	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile = "data//" + layout;
		setupConfigFiles = "data//" + setup;
	}

	// Loads data from setupConfigFiles and initializes rooms
	public void loadSetupConfig() throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(setupConfigFiles); // Opens file
			Scanner in = new Scanner(reader);

			String tempStr = "";

			// Reads in data line by line
			while (in.hasNextLine()) {
				tempStr = in.nextLine();
				if (debugger) {	System.out.println(tempStr ); } // Prints line if debugger
				if(tempStr.charAt(0) == '/') { // Skips line if it contains comment
					if (debugger) {System.out.println("Found comment, skipping line");}
					continue;
				} else {
					String[] arrFromStr = tempStr.split(", "); // Splits line up for data manipulation	
					
					if(arrFromStr[0].equals("Room") || arrFromStr[0].equals("Space")) { 
						roomMap.put(arrFromStr[2].charAt(0), new Room(arrFromStr[1])); 
					} else {
						in.close(); // Close file
						throw new BadConfigFormatException("Invalid Format Detected within setupConfigFiles");
					}
				}
			}
			in.close(); // Close file

		} catch (FileNotFoundException e) {
			throw new BadConfigFormatException("loadSetupConfig Failed");
		}
	}

	// Loads in data from layoutConfigFile.csv, sends data to cells and updates cell information
	public void loadLayoutConfig() throws BadConfigFormatException {
		// Reset variables for multiple tests
		numRows = 0;
		numColumns = 0;
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
			throw new BadConfigFormatException();
		}

		String[] arrFromStr = tempStr.split(",");
		numColumns = arrFromStr.length;
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

				if (roomMap.containsKey(temp.charAt(0))){
					grid[rowCount][colCount].setInitial(temp.charAt(0));
				}
				else {
					throw new BadConfigFormatException("Invalid Room Name Detected in LayoutConfigFile");
				}

				// Checks data read in to determine how to process data
				if (temp.length() > 1){
					// If string in cell is 2 chars long, it must be a special cell
					if (temp.charAt(1) == '*') { // Sets cell as center
						grid[rowCount][colCount].setRoomCenter(true);
						roomMap.get(temp.charAt(0)).setCenterCell(grid[rowCount][colCount]);
					} else if (temp.charAt(1) == '#') { // Sets cell as label
						grid[rowCount][colCount].setRoomLable(true);
						roomMap.get(temp.charAt(0)).setLableCell(grid[rowCount][colCount]);
					} 
					
					else if (temp.charAt(1) == '<') {// Sets door status and direction based on arrow direction
						grid[rowCount][colCount].setDoor(true);
						grid[rowCount][colCount].setDoorDirection(DoorDirection.LEFT);
					} else if (temp.charAt(1) == '^') {
						grid[rowCount][colCount].setDoor(true);
						grid[rowCount][colCount].setDoorDirection(DoorDirection.UP);
					} else if (temp.charAt(1) == '>') {
						grid[rowCount][colCount].setDoor(true);
						grid[rowCount][colCount].setDoorDirection(DoorDirection.RIGHT);
					} else if (temp.charAt(1) == 'v') {
						grid[rowCount][colCount].setDoor(true);
						grid[rowCount][colCount].setDoorDirection(DoorDirection.DOWN);
					} 
					
					else if (roomMap.containsKey(temp.charAt(1))){
						grid[rowCount][colCount].setSecretPassage(temp.charAt(1)); // Sets passage location to second char if second char is a room in roomMap
					} else if (temp.charAt(1) == '\r'){
						if(debugger) {System.out.println("endLine");}
					} else {
						if(debugger) {System.out.println("Invalid Char: " + temp.charAt(1));}
						throw new BadConfigFormatException("Invalid Character Detected in LayoutConfigFile");
					}
				}
				colCount++;
			}
			in.close(); // Close file

		} catch (FileNotFoundException e){
			throw new BadConfigFormatException();
		} catch (StringIndexOutOfBoundsException e){
			throw new BadConfigFormatException("EmptyCell");
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
		return roomMap.get(cell.getInitial());
	}

	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	public Set<BoardCell> getAdjList(int x, int y) {
		Set<BoardCell> testList = new HashSet<BoardCell>();
		return testList;
	}
	
	public void calcTargets(BoardCell cell, int steps) {
		return;
	}
	
	public Set<BoardCell> getTargets() {
		return targetCells;
	}
}
