// Sihang Wang, Michael Basey
package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell lableCell;
	
	private Room() {
		name = "";
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
}
