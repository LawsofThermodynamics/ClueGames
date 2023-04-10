/* BoardCell is the class responsible for holding the raw data of each individual cell of the board game.
 * Each cell contains data regarding location, room type, door type, and list of rooms that the cell is adjacent to.
 * 
 * Authors: Sihang Wang, Michael Basey 
 *  
 * */
package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;
import java.awt.*;
import javax.swing.*;


public class BoardCell extends JPanel{
	private int row; // Stores the row number of each cell (Never used within BoardCell class, but are necessary)
	private int col; // Stores the column number of each cell (Never used within BoardCell class, but are necessary)
	private char initial; // Stores the letter of each cell
	private String room;
	private Color color;
	private DoorDirection doorDirection; // Stores the direction each door is facing
	private boolean roomLable; // Boolean storing if the cell is a room label
	private boolean roomCenter; // Boolean storing if the cell is a room center
	private boolean isDoor; // Boolean storing if the cell is a door
	private boolean isOccupied; // Boolean storing if the cell is a occupied by another player
	private char secretPassage; // Stores the char of the other secret passage this cell connects to
	private Set<BoardCell> adjList; // Stores the list of cells that the current cell is adjacent to
	
	// Default constructor
	public BoardCell() {
		super();
		initVars();
	}

	// Constructor that takes position parameters and initializes cell with an x and y position
	public BoardCell(int x, int y) {
		super();
		row = x;
		col = y;
		initVars();
	}
	
	// Variables initialization method, private only for constructors.
	private void initVars() {
		roomLable = false;
		roomCenter = false;
		isDoor = false;
		secretPassage = '0';
		adjList = new HashSet<BoardCell>();
		doorDirection = DoorDirection.NONE;	
	}
	
	// Adds a cell into the adjacent list of current cell
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	
	
	public void draw (Graphics g, int size) {
		if(color == Color.LIGHT_GRAY)
		{
			g.setColor(Color.LIGHT_GRAY);
		} else {
			g.setColor(Color.BLACK);
		}
		
        g.fillRect((col * size), (row * size), size, size);
        g.setColor(color);
        g.fillRect((col * size) + 1, (row * size) + 1, (size - 2), (size - 2));
    }
	
	public void drawOverlay (Graphics g, int size) {
		if(isDoor == true) {
			g.setColor(Color.BLUE);
        	if (doorDirection == DoorDirection.LEFT) {
                g.fillRect((col * size) - (int)(size * .2), (row * size), (int)(size * .2), (size));
			} else if (doorDirection == DoorDirection.UP) {
                g.fillRect((col * size), (row * size) - (int)(size * .2), (size), (int)(size * .2));
			} else if (doorDirection == DoorDirection.RIGHT) {
                g.fillRect(((col + 1) * size), (row * size), (int)(size * .2), (size));
			} else if (doorDirection == DoorDirection.DOWN) {
                g.fillRect((col * size), ((row + 1) * size), (size), (int)(size * .2));
			}
        }
		
		if (roomLable) {	
			g.setColor(Color.BLACK);
			Font newFont = new Font("Bold", Font.BOLD, (int) (size / 1.5));
			FontMetrics metrics = g.getFontMetrics(newFont);
			
			g.setFont(newFont); 		    
			g.drawString(room, (int) ((col * size) - (metrics.stringWidth(room) / 2) + (size / 2)), ((row) * size));
		}
    }
	

	
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + ", initial=" + initial + "]";
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

	public boolean isOccupied() {
		return isOccupied;
	}	

	public char getSecretPassage() {
		return secretPassage;
	}
	
	public char getInitial() {
		return initial;
	}
		
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
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

	public void setColor(Color color) {
		this.color = color;
		
	}

	public void setRoom(String room) {
		this.room = room;
	}
	

}
