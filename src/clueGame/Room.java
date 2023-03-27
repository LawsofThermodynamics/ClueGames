// Sihang Wang, Michael Basey
package clueGame;

public class Room {
	private String name; // Stores the name of the room
	private BoardCell centerCell; // Stores the cell that is the center of the room
	private BoardCell lableCell; // Stores the cell that will be used to display the name of the cell
	
	// Default Constructor
	public Room(String inName) {
		super();
		name = inName;
		centerCell = null;
		lableCell = null;
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
}
