package experiment;

import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private Set<TestBoardCell> adjCellList;
	private boolean inRoom;
	private boolean isOccupied;
	
	//Basic constructor.
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	//Add cell to adjacency list of current cell.
	public void addAdjacency(TestBoardCell cell ) {
		adjCellList.add(cell);
	}
	//Get current adjacency cell list.
	public Set<TestBoardCell> getAdjList() {
		return adjCellList;
	}
	
	//Set inRoom boolean to identify if this cell is in a room.
	public void setRoom(boolean room) {
		inRoom = room;
	}
	//Get boolean inRoom.
	public boolean isRoom() {
		return inRoom;
	}
	
	//Set isOccupied boolean to identify if this cell is occupied by player.
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	//Get boolean isOccupied.
	public boolean getOccupied() {
		return isOccupied;
	}
}
