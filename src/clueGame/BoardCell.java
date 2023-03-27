/* BoardCell is the class responsible for holding the raw data of each individual cell of the board game.
 * Each cell contains data regarding location, room type, door type, and list of rooms that the cell is adjacent to.
 * 
 * Authors: Sihang Wang, Michael Basey 
 *  
 * */
package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int row; // Stores the row number of each cell (Never used within BoardCell class, but are necessary)
	private int col; // Stores the column number of each cell (Never used within BoardCell class, but are necessary)
	private char initial; // Stores the letter of each cell
	private DoorDirection doorDirection; // Stores the direction each door is facing
	private boolean roomLable; // Boolean storing if the cell is a room label
	private boolean roomCenter; // Boolean storing if the cell is a room center
	private boolean isDoor; // Boolean storing if the cell is a door
	private boolean isOccupied; // Boolean storing if the cell is a occupied by another player
	private char secretPassage; // Stores the char of the other secret passage this cell connects to
	private Set<BoardCell> adjList; // Stores the list of cells that the current cell is adjacent to

	// Default constructor
	BoardCell() {
		super();
		roomLable = false;
		roomCenter = false;
		isDoor = false;
		secretPassage = '0';
		adjList = new HashSet<BoardCell>();
	}

	// Constructor that takes position parameters and initializes cell with an x and y position
	BoardCell(int x, int y) {
		super();
		row = x;
		col = y;
		
		roomLable = false;
		roomCenter = false;
		isDoor = false;
		secretPassage = '0';
		adjList = new HashSet<BoardCell>();
	}

	// Adds a cell into the adjacent list of current cell
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}

	
	// Getters
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public boolean isLabel() {
		return roomLable;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public boolean isDoorway() {
		return isDoor;
	}

	public boolean getOccupied() {
		return isOccupied;
	}	

	public char getSecretPassage() {
		return secretPassage;
	}
	
	public char getInitial() {
		return initial;
	}
	
	

	
	// Setters
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
	public void setInitial(char initial) {
		this.initial = initial;
	}

	public void setRoomLable(boolean roomLable) {
		this.roomLable = roomLable;
	}

	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public void setDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public void setOccupied(boolean o) {
		isOccupied = o;
	}

}
