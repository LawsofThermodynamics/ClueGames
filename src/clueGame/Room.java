// Sihang Wang, Michael Basey
package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell lableCell;
	
	public Room(String inName) {
		name = inName;
		centerCell = null;
		lableCell = null;
	}

	public String getName() {
		return name;
	}

	public BoardCell getLabelCell() {
		return lableCell;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

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
