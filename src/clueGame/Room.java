/* Room class store the name, center cell and label cell of the room.
 * All the variables have getter and setter for interaction.
 * Authors: Michael Basey, Sihang Wang
 */
package clueGame;

import java.util.ArrayList;

public class Room {
	private String name; // Stores the name of the room
	private BoardCell centerCell; // Stores the cell that is the center of the room
	private BoardCell lableCell; // Stores the cell that will be used to display the name of the cell
	private ArrayList<BoardCell> roomCells = new ArrayList<BoardCell>(); // Stores the List of cells for the coloring of the entire room

	// Default Constructor
	public Room(String inName) {
		super();
		name = inName;
		centerCell = null;
		lableCell = null;
	}


	// Adds cells to a room's list of cells
	public void addCell (BoardCell cell){
		roomCells.add(cell);

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

	public ArrayList<BoardCell> getRoomCells() {
		return roomCells;
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
