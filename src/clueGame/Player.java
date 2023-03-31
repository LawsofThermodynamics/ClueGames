/* 
 * 
 */
package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	private int row, col;
	private ArrayList<Card> dealtList;
	private ArrayList<Card> seenList;
	
	// Default constructor
	public Player() {
		super();
	}

	// Parametric constructor
	public Player(String name, Color color, int row, int col) {
		super();
		this.name = name;
		this.color = color;
		dealtList = new ArrayList<Card>();
		seenList = new ArrayList<Card>();
		this.row = row;
		this.col = col;
	}

	// Adds cards that was dealt to player
		public void deltCard(Card card) {
			this.dealtList.add(card);
		}
	
	// Update cards seen by player
	public void seenCard(Card card) {
		this.dealtList.add(card);
	}

	
	// Getters
	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public ArrayList<Card> getDealtCards() {
		return dealtList;
	}
	
	public ArrayList<Card> getSeenCards() {
		return seenList;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	
}
