/* Room class store the name, center cell and label cell of the room.
 * All the variables have getter and setter for interaction.
 * Authors: Michael Basey, Sihang Wang
 */
package clueGame;

import java.util.ArrayList;

public class Room {
	private int occupants;
	private String name; // Stores the name of the room
	private BoardCell centerCell; // Stores the cell that is the center of the room
	private BoardCell lableCell; // Stores the cell that will be used to display the name of the cell
	private ArrayList<BoardCell> roomCells = new ArrayList<BoardCell>();

	// Default Constructor
	public Room(String inName) {
		super();
		name = inName;
		centerCell = null;
		lableCell = null;
		occupants = 0;
	}

	public ArrayList<BoardCell> getRoomCells() {
		return roomCells;
	}

	public void addCell (BoardCell cell){

		roomCells.add(cell);

	}

	public void enter() {
		occupants++;
	}
	
	public void leave() {
		occupants--;
	}
	
	

	// Getters
	public String getName() {
		return name;
	}

	public BoardCell getLabelCell() {
		return lableCell;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

	public int getOccupants() {
		return occupants;
	}

	// Setters
	public void setLableCell(BoardCell lableCell) {
		this.lableCell = lableCell;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	@Override
	public String toString() {
		return "Room [name=" + name + "]";
	}
}
