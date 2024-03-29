// Sihang Wang, Michael Basey

package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	// Variable declaration
	private int row;
	private int col;
	private Set<TestBoardCell> adjCellList;
	private boolean inRoom;
	private boolean isOccupied;

	//Default constructor
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		adjCellList = new HashSet<TestBoardCell>();
	}

	//Add cell to adjacency list of current cell
	public void addAdjacency(TestBoardCell cell ) {
		adjCellList.add(cell);
	}

	/* Check 4 adjacency cells and, if valid spaces within the board boundaries, add the cell to the list
	   Returns list of cells
	 */
	public Set<TestBoardCell> getAdjList() {
		return adjCellList;
	}

	//Set inRoom boolean to identify if this cell is in a room
	public void setRoom(boolean room) {
		inRoom = room;
	}
	//Get boolean inRoom.
	public boolean isRoom() {
		return inRoom;
	}

	//Set isOccupied boolean to identify if this cell is occupied by player
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	//Get boolean isOccupied.
	public boolean getOccupied() {
		return isOccupied;
	}

	@Override
	public String toString() {
		return "[" + row + ", " + col + "]";
	}

}
