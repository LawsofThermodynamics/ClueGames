// Sihang Wang, Michael Basey
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
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
		numRows = 50;
		numColumns = 50;

		grid = new BoardCell[numRows][numColumns];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				grid[i][j] = new BoardCell();
			}
		}

		loadLayoutConfig();
		loadSetupConfig();
	}

	// Sets the locations of the layout and setup text files from parameters
	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile += layout;
		setupConfigFiles += setup;
	}

	public void loadSetupConfig() {
		try {
			FileReader reader = new FileReader(setupConfigFiles);// Opens file
			Scanner in = new Scanner(reader);

			while(in.hasNextLine()) {
				System.out.println(in.nextLine());
			}
			in.close(); // Close file

		} catch (FileNotFoundException e){
			System.out.println("File not found");
		}
	}

	// Loads in data from layoutConfigFile.csv, sends data to cells and updates cell information
	// NOTE!! This is currently not functional as all data is continually overwriting location 0,0 as
	// im not sure yet how to initialize the grid to the correct size without knowing how large the file is before hand
	public void loadLayoutConfig() {
		try {
			FileReader reader = new FileReader(layoutConfigFile);// Opens file
			Scanner in = new Scanner(reader);

			in.useDelimiter(","); // Tells scanner that a comma separates each data cell

			int counterRow = 0;
			int counterCol = 0;
			// Reads data from file
			while(in.hasNext()) {
				grid[counterRow][counterCol] = new BoardCell();

				String temp = in.next();
				grid[counterRow][counterCol].setInitial(temp.charAt(0));
				System.out.print(grid[counterRow][counterCol].getInitial());

				switch (temp.length()){
				case 1:
					break;
				case 2:
					if (temp.charAt(1) == '*') {
						grid[counterRow][counterCol].setRoomCenter(true);
					} else if (temp.charAt(1) == '#') {
						grid[counterRow][counterCol].setRoomLable(true);
					} else {
						grid[counterRow][counterCol].setDoor(true);
						if (temp.charAt(1) == '<') {
							grid[counterRow][counterCol].setDoorDirection(DoorDirection.LEFT);
						} else if (temp.charAt(1) == '^') {
							grid[counterRow][counterCol].setDoorDirection(DoorDirection.UP);
						} else if (temp.charAt(1) == '>') {
							grid[counterRow][counterCol].setDoorDirection(DoorDirection.RIGHT);
						} else if (temp.charAt(1) == 'v') {
							grid[counterRow][counterCol].setDoorDirection(DoorDirection.DOWN);
						}
					}
					break;
				case 3:
					grid[counterRow][counterCol].setSecretPassage(temp.charAt(2));
					break;

				default:
					break;
				}

			}
			in.close(); // Close file

		} catch (FileNotFoundException e){
			System.out.println("File not found");
		}
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

	public Room getRoom(char c) {
		Room room1 = new Room();
		return room1;
	}



}


