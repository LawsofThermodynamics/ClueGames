/* Board Class stores the raw data of the clue game within a two dimensional grid of BoardCells. 
 * Each Board Cell contains an initial for the room, booleans to indicate what kind of room each cell is,
 * as well as information regarding which cells each cell is adjacent.
 * Authors: Sihang Wang, Michael Basey
 * */
package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel{
	// Debugger switch
	private boolean debugger = false; // True prints debugging messages to console

	// Variable Declaration
	private int numRows; // Number of rows that the board is made up of
	private int numColumns; // Number of columns that the board is made up of
	private int diceVal; // Stores the dice value
	private int currPlayer; // Stores the index of current player in playerList
	private boolean currTurnDone = false; // Stores information used to track when a player's turn ends
	private boolean compMakeAccu = false; // Stores flag that whether computer player propose an accusation
	private String layoutConfigFile, setupConfigFiles; // Location of the layout Config File and setup Config File
	private String fileLocation = "data//"; // Stores the relative path of the file names proved by the user
	private ArrayList<Card> cardList = new ArrayList<Card>(); // Stores the list of cards for dealing to players
	private static ArrayList<Card> allRoom, allPerson , allWeapon; // Stores the list of cards for dealing to players
	private ArrayList<Player> playerList = new ArrayList<Player>(); // Stores the list of players
	private ArrayList<BoardCell> targetCells; // Set responsible for storing temporary cells that the adjacency lists method uses
	private Set<BoardCell> visitedCells; // Set responsible for storing the cells that the adjacency lists method has already visited
	private Map<Character, Room> roomMap = new HashMap<Character, Room>(); // Set that stores the relationships between each room and the initials. Data is read in from setup Config File
	private Map<String, Color> colorMap = new HashMap<String, Color>(); // Set that stores the relationships between each room and the initials. Data is read in from setup Config File
	private BoardCell grid[][]; // 2d Array that stores the BoardCell that makes up the games board
	private Solution solution; // Stores the correct set of cards that the players must choose
	private Solution tmpSolution; // Stores the solution that cannot be disprove by anyone
	private Random rand = new Random(); // Random generator	
	private PlayerMove playerMove = new PlayerMove(); // Used to track player's interactions with the board
	private Player disprover;

	private static Board theInstance = new Board(); // The singleton of the Board instance


	// Default constructor
	private Board() {
		super();
		targetCells = new ArrayList<BoardCell> ();
		visitedCells = new HashSet<BoardCell> ();
	}

	// Returns the singleton Board instance
	public static Board getInstance() {
		return theInstance;
	}


	/* Function called to initialize the clue game
	 * 	Initializes: Board, players and cards  
	 * 
	 * -Sihang Wang, Michael Basey 3/5/2023
	 * */
	public void initialize() {
		try {
			// Reset variables for multiple tests
			grid = null; 

			allRoom = new ArrayList<Card>();
			allPerson = new ArrayList<Card>();
			allWeapon = new ArrayList<Card>();

			playerList = new ArrayList<Player>();
			roomMap = new HashMap<Character, Room>();

			targetCells = new ArrayList<BoardCell>();
			visitedCells = new HashSet<BoardCell>();

			tmpSolution = new Solution();

			// Load data from config files
			loadSetupConfig();
			loadLayoutConfig();

			// Deals cards to player from cardList
			dealToPlayers();


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



	/* Sets the locations of the layout and setup text files from parameters
	 *  Relies on files always being within relative path stored within fileLocation//Filename 
	 * -Sihang Wang, Michael Basey 3/5/2023
	 * */
	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile = fileLocation + layout;
		setupConfigFiles = fileLocation + setup;
	}



	/* Loads data from setupConfigFiles
	 * 	Initializes: rooms with name and initials, Players with colors and positions, and all cards
	 * -Sihang Wang, Michael Basey 3/5/2023
	 * */
	public void loadSetupConfig() throws BadConfigFormatException {
		try {
			// Stores file information per line 
			String tempStr = "";

			// Stores the data read in by the function from the file for card definition
			ArrayList<Card> roomList = new ArrayList<Card>();
			ArrayList<Card> personList = new ArrayList<Card>();
			ArrayList<Card> weaponList = new ArrayList<Card>();

			FileReader reader = new FileReader(setupConfigFiles); // Opens file
			Scanner in = new Scanner(reader);

			defineColorMap(); // Calls function to initiate color map for translation between String to Color class for players

			// Reads in data line by line
			while (in.hasNextLine()) {
				tempStr = in.nextLine();
				if (debugger) {	System.out.println(tempStr); } // Prints line if debugger
				if(tempStr.charAt(0) == '/') { // Skips line if it contains comment
					if (debugger) {System.out.println("Found comment, skipping line");}
					continue;
				} else {
					String[] arrFromStr = tempStr.split(", "); // Splits line up for data manipulation	

					if(arrFromStr[0].equals("Room") || arrFromStr[0].equals("Space")) { // Adds room to roomMap if setup is configured with string room or space
						roomMap.put(arrFromStr[2].charAt(0), new Room(arrFromStr[1])); 

						if(arrFromStr[0].equals("Room")) { // If room is not a blank space, add it as a room card
							roomList.add(new Card(arrFromStr[1], CardType.ROOM)); // Adds room cards to roomList
						}
					}
					else if (arrFromStr[0].equals("Player")) {
						personList.add(new Card(arrFromStr[1], CardType.PERSON)); // Adds person card to personList 

						Color playerColor = Color.CYAN; // Default color for player if color in file is invalid or unspecified

						if(colorMap.containsKey(arrFromStr[2])) { // Overrides color if one is given
							playerColor = colorMap.get(arrFromStr[2]);
						}


						if(playerList.size() == 0) { // Initializes the first player as a human, and the remaining players as computers
							playerList.add(new HumanPlayer(arrFromStr[1], playerColor, Integer.parseInt(arrFromStr[3]), Integer.parseInt(arrFromStr[4])));
						} else {
							playerList.add(new ComputerPlayer(arrFromStr[1], playerColor, Integer.parseInt(arrFromStr[3]), Integer.parseInt(arrFromStr[4])));
						}

					}
					else if (arrFromStr[0].equals("Weapon")) {
						weaponList.add(new Card(arrFromStr[1], CardType.WEAPON)); // Adds weapon card to weaponList 
					}
					else { // Throws error if word in file is not recognized
						in.close(); // Close file
						throw new BadConfigFormatException("setupConfigFiles Failed, Invalid Format Detected");
					}
				}
			}
			in.close(); // Close file
			cardList = solutionAndMerge(roomList, personList, weaponList);

		} catch (FileNotFoundException e) {
			throw new BadConfigFormatException("loadSetupConfig Failed, File Not Loaded Correctly");
		}
	}



	/* Takes the three lists of each different card type, picks one card of each type for the solution
	 * 	 and merges the data into one array for dealing	 
	 *  
	 *   -Sihang, Michael 3/30/2023
	 * */
	private ArrayList<Card> solutionAndMerge(ArrayList<Card> roomList, ArrayList<Card> personList, ArrayList<Card> weaponList) {
		allRoom = new ArrayList<Card>();
		allPerson = new ArrayList<Card>();
		allWeapon = new ArrayList<Card>();

		allRoom.addAll(roomList);
		allPerson.addAll(personList);
		allWeapon.addAll(weaponList);

		// Picks the random numbers within the bounds of each card list to deal the solution 
		int roomCard = (int)(Math.random()*(roomList.size()));  
		int personCard = (int)(Math.random()*(personList.size()));  
		int weaponCard = (int)(Math.random()*(weaponList.size()));  		

		// Initializes the solution using the random card selection
		solution = new Solution(roomList.get(roomCard), personList.get(personCard), weaponList.get(weaponCard));	
		System.out.println(solution);
		if (debugger) {System.out.println(solution);} // Prints card if debugger is true

		// Removes the solution cards from the deck to prevent the players from getting the cards within the solution
		roomList.remove(roomCard);
		personList.remove(personCard);
		weaponList.remove(weaponCard);

		// Merges lists for dealing player hands
		ArrayList<Card> finalCardList = new ArrayList<Card>();
		finalCardList.addAll(roomList);
		finalCardList.addAll(personList);
		finalCardList.addAll(weaponList);

		return finalCardList;
	}



	/* Initializes the color hash map for assigning player color from string input
	 * 
	 * -Sihang, Michael 3/30/2023
	 */
	private void defineColorMap() {
		colorMap.put("RED", Color.RED);
		colorMap.put("YELLOW", Color.YELLOW);
		colorMap.put("WHITE", Color.WHITE);
		colorMap.put("GREEN", Color.GREEN);
		colorMap.put("BLUE", Color.BLUE);
		colorMap.put("MAGENTA", Color.MAGENTA);
	}


	/* Deals cards evenly to players within playerList from cardList
	 * 
	 * -Sihang, Michael 3/30/2023
	 */
	private void dealToPlayers() {
		int numPlayers = 0;
		while(cardList.size() > 0) {
			int randCard = (int)(Math.random()*(cardList.size()));
			playerList.get(numPlayers % 6).deltCard(cardList.get(randCard));
			cardList.remove(randCard);

			numPlayers++;
		}

		System.out.println(playerList.get(0).getDealtCards());
	}


	/* Loads in data from layoutConfigFile.csv, and performs three loops to complete board initiation
	 * 	 First loop calculates size of array, and initializes size
	 * 	 Second loop sends data to cells, and updates each cell's data
	 * 	 Third loop calculates all valid adjacent cells
	 * 
	 *   -Sihang, Michael 3/7/2023
	 *   
	 * */
	public void loadLayoutConfig() throws BadConfigFormatException {
		loadLayoutInitialSettup();

		int rowCount = 0;
		int colCount = 0;

		try {
			FileReader reader = new FileReader(layoutConfigFile);// Opens file
			Scanner in = new Scanner(reader);

			in.useDelimiter("[,\n]"); // Tells scanner that a comma or endline separates each data cell

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

					grid[rowCount][colCount].setRoom(roomMap.get(temp.charAt(0)).getName());
					roomMap.get(temp.charAt(0)).addCell(grid[rowCount][colCount]);




				}
				else {
					in.close(); // Close file
					throw new BadConfigFormatException("LayoutConfigFile Failed, Invalid Room Name Detected");
				}

				// Checks data read in to determine how to process data
				// If string in cell is 2 chars long, it must be a special cell, else it will be handled like a normal cell
				if (temp.length() > 1){
					// Sets cell as center
					if (temp.charAt(1) == '*') { 
						grid[rowCount][colCount].setRoomCenter(true);
						roomMap.get(temp.charAt(0)).setCenterCell(grid[rowCount][colCount]);

						// Sets cell as label
					} else if (temp.charAt(1) == '#') { 
						grid[rowCount][colCount].setRoomLable(true);
						roomMap.get(temp.charAt(0)).setLableCell(grid[rowCount][colCount]);
					}
					// Sets door status and direction based on arrow direction
					else if (temp.charAt(1) == '<') {
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

					// Sets special room status depending on characters
					else if (roomMap.containsKey(temp.charAt(1))){
						grid[rowCount][colCount].setSecretPassage(temp.charAt(1)); // Sets passage location to second char if second char is a room in roomMap
					} else if (temp.charAt(1) == '\r'){ // Skips the endline at the end of every csv file line
						if(debugger) {System.out.println("endLine");}
					} else { // Throws an error if an invalid character is found 
						if(debugger) {System.out.println("Invalid Char: " + temp.charAt(1));}
						in.close(); // Close file
						throw new BadConfigFormatException("LayoutConfigFile Failed, Invalid Character Detected");
					}

				}
				colCount++;
			}
			in.close(); // Close file

		} catch (FileNotFoundException e){
			throw new BadConfigFormatException("LayoutConfigFile Failed, File Not Loaded Correctly");
		} catch (StringIndexOutOfBoundsException e){
			throw new BadConfigFormatException("LayoutConfigFile Failed, empty cell detected");
		}

		loadLayoutFinal();
	}





	/* Helper function for loadLayoutConfig
	 * 	Loads in data from layoutConfigFile.csv, and performs the initial data read to initialize the board
	 * 	 Reads data line by line counting to detirmin the number of rows, then counts the number of cells in the final row to determine column count
	 *   -Michael 3/27/2023
	 * */
	private void loadLayoutInitialSettup() throws BadConfigFormatException{
		// Reset variables for multiple tests
		numRows = 0;
		numColumns = 0;
		String tempStr = "";

		try {
			FileReader reader = new FileReader(layoutConfigFile);// Opens file
			Scanner in = new Scanner(reader);

			// Reads data from file
			while(in.hasNextLine()) {
				numRows++; // Counts rows
				tempStr = in.nextLine(); // Stores string for column count
			}
			in.close(); // Close file	

		} catch (FileNotFoundException e){
			throw new BadConfigFormatException("loadLayoutConfig Failed, File Not Loaded Correctly");
		}

		String[] arrFromStr = tempStr.split(","); // Splits string into columns to determine column count
		numColumns = arrFromStr.length;
		if(debugger) {System.out.println("Rows: " + numRows); }
		if(debugger) {System.out.println("Columns: " + numColumns);	}		

		grid = new BoardCell[numRows][numColumns];


		int rowCount;
		int colCount;


		for (rowCount = 0; rowCount < numRows; rowCount++) {
			for (colCount = 0; colCount < numColumns; colCount++) {
				grid[rowCount][colCount] = new BoardCell(rowCount, colCount);
			}
		}		
	}

	/* Secondary helper function for loadLayoutConfig
	 * 	Loads in data from layoutConfigFile.csv for the last time
	 *  Reads in data relating to doorways and room data
	 *   -Sihang, Michael 4/17/2023
	 * */
	private void loadLayoutFinal(){
		for (int rowCount = 0; rowCount < numRows; rowCount++) {
			for (int colCount = 0; colCount < numColumns; colCount++) {

				BoardCell currentCell = grid[rowCount][colCount];
				char currentCellInitial = currentCell.getInitial();

				// If the room is a doorway, get the center cell based on the initial of the cell in the direction the door is from the current cell
				if(currentCell.isDoorway()) {
					// Variables to make the following method more legible
					DoorDirection roomLocation = currentCell.getDoorDirection();
					BoardCell centerCell = new BoardCell();

					// Assigns links between cells based on door direction
					switch (roomLocation) {
					case LEFT:
						centerCell = roomMap.get(grid[rowCount][colCount - 1].getInitial()).getCenterCell();
						break;
					case UP:
						centerCell = roomMap.get(grid[rowCount - 1][colCount].getInitial()).getCenterCell();
						break;
					case RIGHT:
						centerCell = roomMap.get(grid[rowCount][colCount + 1].getInitial()).getCenterCell();
						break;
					case DOWN:
						centerCell = roomMap.get(grid[rowCount + 1][colCount].getInitial()).getCenterCell();
						break;
					default:
						break;
					}

					// Links the currently selected cell with the center of the room, and vice versa
					currentCell.addAdj(centerCell);
					centerCell.addAdj(currentCell);
				}

				// If the cell is a secret passage, the char saved in SecretPassage will be changed from the default '0'
				if(currentCell.getSecretPassage() != '0') {					
					(roomMap.get(currentCellInitial)).getCenterCell().addAdj(roomMap.get(currentCell.getSecretPassage()).getCenterCell());
				}

				// If the current cell is a walkway check the adjacent cells to see if they are valid walkways
				if(currentCellInitial == 'W') {
					if(rowCount < (numRows - 1) && grid[rowCount + 1][colCount].getInitial() == 'W') {
						currentCell.addAdj(grid[rowCount + 1][colCount]);
					}
					if(rowCount > 0 && grid[rowCount - 1][colCount].getInitial() == 'W') {
						currentCell.addAdj(grid[rowCount - 1][colCount]);
					}
					if(colCount < (numColumns - 1) && grid[rowCount][colCount + 1].getInitial() == 'W') {
						currentCell.addAdj(grid[rowCount][colCount + 1]);
					}
					if(colCount > 0 && grid[rowCount][colCount - 1].getInitial() == 'W') {
						currentCell.addAdj(grid[rowCount][colCount - 1]);
					}
				}
			}
		}
	}



	/* Preform before each calculation as targetList must be cleared before each run
	 *  Then call the private recursive method calcRecursive
	 * 
	 * -Sihang, 3/8/2023
	 * */
	public void calcTargets(BoardCell cell, int steps) {
		targetCells.clear();
		visitedCells.clear();
		calcRecursive(cell, steps);
	}


	/* Nested method.
	 *  Loops through every possible path that the player can move through, then record the end locations that are valid
	 *  spaces that the player can end their movement phase to into targetCells.
	 *  Continues to loop through each individual paths until player runs out of steps
	 * 
	 * -Sihang, 3/8/2023
	 * 
	 * */
	private void calcRecursive(BoardCell cell, int steps) {
		if (steps == 0) {
			if(targetCells.contains(cell)) {
				return;
			}
			cell.setTarget(true); // Set the flag variable isTarget of cell.
			targetCells.add(cell);
		}
		else {
			visitedCells.add(cell);
			for (BoardCell nextCell : cell.getAdjList()) {
				// If next cell is an unvisited room center, execute as final step.
				if (!visitedCells.contains(nextCell) && nextCell.isRoomCenter()) {
					calcRecursive(nextCell, 0);
				}
				// If next cell is unvisited and is an unoccupied normal cell, execute recursive step.
				else if (!visitedCells.contains(nextCell) && !nextCell.isOccupied()) {
					calcRecursive(nextCell, steps - 1);					
				}
			}
			visitedCells.remove(cell);
		}
		return;
	}

	/* Check if accusation is true, return false if anything not match, and give feedback.
	 * 
	 * Sihang 4/3/2023
	 */
	public boolean checkAccusation(Solution accusation) {
		
		for(int i = 0 ; i < playerList.size(); i++) {
			playerList.get(i).move(15, 15);
		}
		targetCells.clear();		
		repaint();
		currTurnDone = true;
		ClueGame.setGameDone(true); // No matter human or computer propose a accusation, correct/wrong, the game will end.
		
		// Correct accusation.
		if (accusation.getRoom().equals(solution.getRoom())  && accusation.getPerson().equals(solution.getPerson()) && accusation.getPerson().equals(solution.getPerson())) {
			if(currPlayer % 6 == 0) {
				splashScreen("Congragulations!! You have won clue!!");
				ClueGame.getControlPanel().setGuess("Congragulations!! You have solved the mystery!!");

			} 
			else {
				splashScreen("An AI has won clue!! The correct accusation was:\n" + accusation.getPerson() + "in the " + accusation.getRoom() + " with the " + accusation.getWeapon());
				ClueGame.getControlPanel().setGuess("An AI has solved the mystery!! Better luck next time!!");

			}
			currTurnDone = true;
			return true;
			
		} 
		// Wrong accusation, only human player could make wrong, so no branch for human/computer here.
		else {
			splashScreen("Unfortunatly, you guess incorrectly. The correct accusation was: " + solution.getPerson() + "in the " + solution.getRoom() + " with the " + solution.getWeapon());
			return false;
		}
	}

	/* Loop player list, return first non-null result, if all players cannot disprove, return null.
	 * 
	 * Michael, Sihang 4/3/2023
	 */
	public Card handleSuggestion(Player suggester, Solution suggestion) {

		ClueGame.getControlPanel().setGuess(suggester + " suggested \"" + suggestion.getPerson().getName() + "\"\nin the \"" + suggestion.getRoom().getName() + "\" with the \"" + suggestion.getWeapon() + "\"");

		for(int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getName().equals(suggestion.getPerson().getName()) && !playerList.get(i).getName().equals(suggester.getName())){
				playerList.get(i).move(suggester.getRow(), suggester.getCol());
				playerList.get(i).setMoved(true);
				repaint();
				break;
			}
		}

		for (Player player : playerList) {
			if ((player.disproveSuggestion(suggestion) != null) && !(player.getName().equals(suggester.getName()))) {
				disprover = player;
				currTurnDone = true;
				return player.disproveSuggestion(suggestion);
			}
		}
		currTurnDone = true;
		return null;
	}


	/* Draw the board and players
	 * 
	 * Author: Sihang, Michael 4/10/2023
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int rectLength = getHeight() / numRows;
		int rectWidth = getWidth() / numColumns;

		int size = 0;

		if (rectLength < rectWidth) {
			size = rectLength;
		} else {
			size = rectWidth;
		}

		// Iterate every board cell, draw basic cells.
		for (int drawRowNum = 0; drawRowNum < numRows; drawRowNum++) {
			for (int drawColNum = 0; drawColNum < numColumns; drawColNum++) {
				BoardCell currCell = grid [drawRowNum][drawColNum];

				if(currCell.getInitial() == 'W') {
					currCell.draw(g, size, Color.YELLOW);
				} else if(currCell.getInitial() == 'X') {
					currCell.draw(g, size, Color.BLACK);
				} else {
					currCell.draw(g, size, Color.LIGHT_GRAY);
				}
			}
		}

		for (int targetCounter = 0; targetCounter < targetCells.size(); targetCounter++) {
			targetCells.get(targetCounter).draw(g, size, Color.CYAN);
			if(targetCells.get(targetCounter).isRoomCenter()) { 
				for (int targetRoomCounter = 0; targetRoomCounter < roomMap.get(targetCells.get(targetCounter).getInitial()).getRoomCells().size(); targetRoomCounter++) {
					roomMap.get(targetCells.get(targetCounter).getInitial()).getRoomCells().get(targetRoomCounter).draw(g, size, Color.CYAN);
				}
			}
		}

		// Iterate every board cell, draw overlays like doorway and room name.
		for (int drawRowNum = 0; drawRowNum < numRows; drawRowNum++) {
			for (int drawColNum = 0; drawColNum < numColumns; drawColNum++) {
				grid [drawRowNum][drawColNum].drawOverlay(g, size);
			}
		}

		// Draw players.
		ArrayList<BoardCell> playerLocation = new ArrayList <BoardCell>();
		for (int players = 0; players < playerList.size(); players++) {
			playerLocation.add(grid[playerList.get(players).getRow()][playerList.get(players).getCol()]);
			int offset = 0;
			for (int i = 0; i < players; i++) {
				if(playerLocation.get(i).equals(playerLocation.get(players))) {
					offset++;
				}
			}
			playerList.get(players).draw(g, size, offset);


		}



	}

	/* Method called by GameControlPanel, execute NEXT flow
	 * 
	 * Sihang, Michael 4/12/2023
	 */
	public void playerTurn() {			
		// New turn.
		currTurnDone = false;	
		ClueGame.update();	
		rollDice();


		calcTargets(this.getCell(playerList.get(currPlayer % 6)), diceVal); // Calculate possible moving targets.

		if(playerList.get(currPlayer % 6).isMoved()) {
			playerList.get(currPlayer % 6).setMoved(false);
			targetCells.add(grid[playerList.get(currPlayer % 6).getRow()][playerList.get(currPlayer % 6).getCol()]);
			repaint();
		}

		GameControlPanel.getCtrlPanel().setTurn(playerList.get(currPlayer % 6), diceVal); // Update GameControlPanel with new player index and dice value.


		// Human player
		if ((currPlayer % 6) == 0) {
			addMouseListener(playerMove);
		}

		// Computer player
		else {
			removeMouseListener(playerMove);
			
			if (compMakeAccu) {
				makeAccusation(playerList.get(currPlayer));
			}
			BoardCell cellToMove = playerList.get(currPlayer % 6).selectTarget(diceVal);
			playerList.get(currPlayer % 6).move(cellToMove.getRow(), cellToMove.getCol());
			targetCells.clear();

			if (grid[playerList.get(currPlayer % 6).getRow()][playerList.get(currPlayer % 6).getCol()].isRoomCenter()) {
				computerMakeSuggestion(playerList.get(currPlayer % 6));
			}

			currTurnDone = true;
		}		
		repaint();
	}


	/* Method called by GameControlPanel, execute NEXT flow
	 * 
	 * Sihang, Michael 4/12/2023
	 */
	public void nextFlow() {
		// First check if current turn finished.
		if (isCurrTurnDone()) {
			currPlayer = (currPlayer + 1) % 6;
			playerTurn();
		} else {
			splashScreen("Please finish current turn fisrt.");
		}
	}

	/* Mouselistener for human player interacting with board area. 
	 * 
	 */
	private class PlayerMove implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int currentCellX = (int) (e.getX() / (getHeight() / numRows));
			int currentCellY = (int) (e.getY() / (getHeight() / numRows));

			if(currentCellY > numRows || currentCellX > numRows) {
				splashScreen("Invalid location, out of bounds.");
				return;
			}

			System.out.println("Clicked: " + e.getY() + ", " + e.getX());
			System.out.println("Calc Cell: " + currentCellY + ", " + currentCellX);


			// Case that clicking on the target cell.
			if (targetCells.contains(grid[currentCellY][currentCellX])) {
				playerList.get(currPlayer % 6).move(currentCellY, currentCellX);

				if (grid[playerList.get(currPlayer % 6).getRow()][playerList.get(currPlayer % 6).getCol()].isRoomCenter()) {
					makeSuggestion(playerList.get(currPlayer % 6));
				}

				targetCells.clear();

				currTurnDone = true;

				repaint();
				return;
			} 
			
			// Case that didn't click on target cell.
			else {
				// Check if click on the room cell of the target room.
				if(!(grid[currentCellY][currentCellX].getInitial() == 'W')) {
					for (int targetCounter = 0; targetCounter < targetCells.size(); targetCounter++) {

						if(roomMap.get(targetCells.get(targetCounter).getInitial()).getRoomCells().contains(grid[currentCellY][currentCellX])) {
							playerList.get(currPlayer % 6).move(targetCells.get(targetCounter).getRow(), targetCells.get(targetCounter).getCol());

							targetCells.clear();

							repaint();

							makeSuggestion(playerList.get(currPlayer % 6));

							return;

						}
					}
				}
			}
			splashScreen("Invalid location, cannot move there with a roll of " + diceVal);
		}

		@Override public void mousePressed(MouseEvent e) {}
		@Override public void mouseReleased(MouseEvent e) {}
		@Override public void mouseEntered(MouseEvent e) {}
		@Override public void mouseExited(MouseEvent e) {}

	}


	// Method that splash screen with string parameter.
	public void splashScreen(String str) {
		JFrame tmpF = new JFrame();
		JOptionPane.showMessageDialog(tmpF, str);
		tmpF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Assign dice value with integer [1, 6].
	public void rollDice() {
		diceVal = rand.nextInt(6) + 1;
	}


	/* Human player make a suggestion. Splash a panel with 3 combo box and buttons.
	 * 
	 * Author: Michael, 4/24/2023
	 */
	public Solution makeSuggestion(Player player) {
		String[] room = {roomMap.get(grid[player.getRow()][player.getCol()].getInitial()).getName()};
		String[] playerOp = new String[allPerson.size()];
		String[] weapOp = new String[allWeapon.size()];

		for(int i = 0; i < allPerson.size(); i++) {
			playerOp[i] = allPerson.get(i).getName();
		}

		for(int i = 0; i < allWeapon.size(); i++) {
			weapOp[i] = allWeapon.get(i).getName();
		}

		JFrame jFrame = new JFrame("Make a Suggestion");

		JComboBox<String> jComboroom = new JComboBox<>(room);
		jComboroom.setEnabled(false);
		jComboroom.setBounds(150, 0, 150, 30);
		JLabel roomLable = new JLabel();
		roomLable.setText("Room");
		roomLable.setBounds(0, 0, 150, 30);

		JComboBox<String> jComboplayer = new JComboBox<>(playerOp);
		jComboplayer.setBounds(150, 30, 150, 30);
		JLabel personLable = new JLabel();
		personLable.setText("Person");
		personLable.setBounds(0, 30, 150, 30);

		JComboBox<String> jComboweapon = new JComboBox<>(weapOp);
		jComboweapon.setBounds(150, 60, 150, 30);
		JLabel weaponLable = new JLabel();
		weaponLable.setText("Weapon");
		weaponLable.setBounds(0, 60, 150, 30);


		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(150, 90, 150, 30);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(0, 90, 150, 30);


		jFrame.add(jComboroom);
		jFrame.add(jComboplayer);
		jFrame.add(jComboweapon);

		jFrame.add(roomLable);
		jFrame.add(personLable);
		jFrame.add(weaponLable);

		jFrame.add(submitButton);
		jFrame.add(cancelButton);

		jFrame.setLayout(null);
		jFrame.setSize(320, 160);
		jFrame.setVisible(true);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Card roomSug = new Card(jComboroom.getItemAt(jComboroom.getSelectedIndex()), CardType.ROOM);
				Card playerSug = new Card(jComboplayer.getItemAt(jComboplayer.getSelectedIndex()), CardType.PERSON);
				Card weaponSug = new Card(jComboweapon.getItemAt(jComboweapon.getSelectedIndex()), CardType.WEAPON);


				Card matchingCard = handleSuggestion(player, new Solution(roomSug, playerSug, weaponSug));
				if (matchingCard == null) {
					ClueGame.getControlPanel().setGuessResult(playerList.get(0).getName() + "'s guess could not be disproved");
					compMakeAccu = true;
					tmpSolution.modify(roomSug, playerSug, weaponSug);
				} 
				else {
					ClueGame.getControlPanel().setGuessResult(playerList.get(0).getName() + "'s guess was disproved by " + disprover.getName());
					if(!playerList.get(0).getSeenCards().contains(matchingCard) && !playerList.get(0).getDealtCards().contains(matchingCard)) {
						playerList.get(0).seenCard(matchingCard);
					}
					ClueGame.update();
				}

				jFrame.dispose();
			}

		});


		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currTurnDone = true;
				jFrame.dispose();
			}

		});

		return null;

	}

	/* Computer player propose a suggestion. The room must be the room player at.
	 * 
	 * Author: Michael 4/24/2023
	 */
	public Solution computerMakeSuggestion(Player player) {
		String[] playerOp = new String[allPerson.size()];
		String[] weapOp = new String[allWeapon.size()];

		for(int i = 0; i < allPerson.size(); i++) {
			playerOp[i] = allPerson.get(i).getName();
		}

		for(int i = 0; i < allWeapon.size(); i++) {
			weapOp[i] = allWeapon.get(i).getName();
		}


		Card roomSug = new Card(roomMap.get(grid[player.getRow()][player.getCol()].getInitial()).getName(), CardType.ROOM);
		Card playerSug = allPerson.get(rand.nextInt(allPerson.size()));
		Card weaponSug = allWeapon.get(rand.nextInt(allWeapon.size()));


		Card matchingCard = handleSuggestion(player, new Solution(roomSug, playerSug, weaponSug));
		if (matchingCard == null) {
			ClueGame.getControlPanel().setGuessResult(player.getName() + "'s guess could not be disproved");
			compMakeAccu = true;
			tmpSolution.modify(roomSug, playerSug, weaponSug);
		} else {
			ClueGame.getControlPanel().setGuessResult(player.getName() + "'s guess was disproved by " + disprover.getName());
			if(!player.getSeenCards().contains(matchingCard) && !player.getDealtCards().contains(matchingCard)) {
				player.seenCard(matchingCard);
			}
			ClueGame.update();
		}

		return null;

	}



	/* Make accusation method, this method has two branches for human/computer player.
	 * 
	 * Author: Michael 4/24/2023
	 */
	public void makeAccusation(Player player) {
		
		// Human player make accusation
		if(currPlayer % 6 == 0) {
			String[] roomOp = new String[allRoom.size()];
			String[] playerOp = new String[allPerson.size()];
			String[] weapOp = new String[allWeapon.size()];

			for(int i = 0; i < allRoom.size(); i++) {
				roomOp[i] = allRoom.get(i).getName();
			}		

			for(int i = 0; i < allPerson.size(); i++) {
				playerOp[i] = allPerson.get(i).getName();
			}

			for(int i = 0; i < allWeapon.size(); i++) {
				weapOp[i] = allWeapon.get(i).getName();
			}

			JFrame jFrame = new JFrame("Make an Accusation");

			JComboBox<String> jComboroom = new JComboBox<>(roomOp);
			jComboroom.setBounds(150, 0, 150, 30);
			JLabel roomLable = new JLabel();
			roomLable.setText("Room");
			roomLable.setBounds(0, 0, 150, 30);

			JComboBox<String> jComboplayer = new JComboBox<>(playerOp);
			jComboplayer.setBounds(150, 30, 150, 30);
			JLabel personLable = new JLabel();
			personLable.setText("Person");
			personLable.setBounds(0, 30, 150, 30);

			JComboBox<String> jComboweapon = new JComboBox<>(weapOp);
			jComboweapon.setBounds(150, 60, 150, 30);
			JLabel weaponLable = new JLabel();
			weaponLable.setText("Weapon");
			weaponLable.setBounds(0, 60, 150, 30);


			JButton submitButton = new JButton("Submit");
			submitButton.setBounds(150, 90, 150, 30);

			JButton cancelButton = new JButton("Cancel");
			cancelButton.setBounds(0, 90, 150, 30);



			jFrame.add(jComboroom);
			jFrame.add(jComboplayer);
			jFrame.add(jComboweapon);

			jFrame.add(roomLable);
			jFrame.add(personLable);
			jFrame.add(weaponLable);

			jFrame.add(submitButton);
			jFrame.add(cancelButton);

			jFrame.setLayout(null);
			jFrame.setSize(320, 160);
			jFrame.setVisible(true);

			submitButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Card roomSug = new Card(jComboroom.getItemAt(jComboroom.getSelectedIndex()), CardType.ROOM);
					Card playerSug = new Card(jComboplayer.getItemAt(jComboplayer.getSelectedIndex()), CardType.PERSON);
					Card weaponSug = new Card(jComboweapon.getItemAt(jComboweapon.getSelectedIndex()), CardType.WEAPON);


					System.out.println(checkAccusation(new Solution(roomSug, playerSug, weaponSug)));
					jFrame.dispose();
				}

			});


			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					jFrame.dispose();
				}

			});

		} 
		
		// Computer player make accusation. Update info in guess panel and check accusation.
		else {
			ClueGame.getControlPanel().setGuess(playerList.get(currPlayer) + " accused " + tmpSolution.getPerson() + "\"\nin the \"" + tmpSolution.getRoom() + "\" with the \"" + tmpSolution.getWeapon() + "\"");
			System.out.println(checkAccusation(tmpSolution));
		}

	}





	// Getters
	// Returns rooms based on cell initial or cell type
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}
	// Returns cell based on cell position
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	// Returns cell based on player
	public BoardCell getCell(Player player) {
		return grid[player.getRow()][player.getCol()];
	}

	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public ArrayList<Card> getCardList() {
		return cardList;
	}

	public Solution getSolution() {
		return solution;
	}

	public ArrayList<BoardCell> getTargets() {
		return targetCells;
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	// Return adjacency list of specific cell in grid based on coordinates
	public Set<BoardCell> getAdjList(int x, int y) {	
		return grid[x][y].getAdjList();
	}

	public static ArrayList<Card> getAllRoom() {
		return allRoom;
	}

	public static ArrayList<Card> getAllPerson() {
		return allPerson;
	}

	public static ArrayList<Card> getAllWeapon() {
		return allWeapon;
	}

	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}

	public boolean isCurrTurnDone() {
		return currTurnDone;
	}

}
