// Sihang Wang, Michael Basey
package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLable;
	private boolean roomCenter;
	private boolean isDoor;
	private char secretPassage;
	Set<BoardCell> adjList;

	BoardCell() {
		roomLable = false;
		roomCenter = false;
		isDoor = false;
	}


	private void addAdj(BoardCell adj) {

	}


	// Getters and setters
	public boolean isLabel() {
		return roomLable;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public Boolean isDoorway() {
		return isDoor;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getSecretPassage() {
		return secretPassage;
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

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	



}
