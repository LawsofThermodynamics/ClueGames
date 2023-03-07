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
	private boolean isOccupied;
	private char secretPassage;
	Set<BoardCell> adjList;

	BoardCell() {
		roomLable = false;
		roomCenter = false;
		isDoor = false;
		secretPassage = '0';
		adjList = new HashSet<BoardCell>();
	}

	// Constructor that takes position parameters and initializes cell with an x and y position
	BoardCell(int x, int y) {
		row = x;
		col = y;
		
		roomLable = false;
		roomCenter = false;
		isDoor = false;
		secretPassage = '0';
		adjList = new HashSet<BoardCell>();
	}


	public void addAdj(BoardCell adj) {
		adjList.add(adj);
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
	
	public char getInitial() {
		return initial;
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

	public void setOccupied(boolean o) {
		isOccupied = o;
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	


}
