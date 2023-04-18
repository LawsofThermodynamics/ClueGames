/* Player Class stores data of all player human or computer during the game 
 * 	Player stores its name as a string, color for gui, position, and cards it was dealt and shown throughout the game
 * 
 * Authors: Sihang Wang, Michael Basey
 * */
package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Player {
	private String name; // Stores the name of the player, read from clueSettup
	private Color color; // Stores color of the player, read from clueSettup
	private int row, col; // Stores position of player, read from clueSettup
	private ArrayList<Card> dealtList; // Stores cards the player was dealt at the beginning of the game, only allowed cards the player can show other players
	private ArrayList<Card> seenList; // Stores cards the player has been shown by other players, updates throughout the game

	// Default constructor
	public Player() {

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
	
	// Method definition for computer player override
	public BoardCell selectTarget(int steps) {
		return null;
	}
	
	// Moves the player to row, col in grid
	public void move(int row, int col) {
		this.row = row;
		this.col = col;
	}

	// Adds cards that was dealt to player
	public void deltCard(Card card) {
		this.dealtList.add(card);
	}

	// Update cards seen by player
	public void deltCard(ArrayList<Card> cards) {
		this.dealtList.addAll(cards);
	}

	// Update cards seen by player
	public void seenCard(Card card) {
		this.seenList.add(card);
	}

	// Update cards seen by player
	public void seenCard(ArrayList<Card> cards) {
		this.seenList.addAll(cards);
	}

	/* Check every cards of player if match to cards in suggestion, if matched, add to matchList.
	 * Return null if no match, else a random card in matchList.
	 * 
	 * Michael, Sihang 4/3/2023
	 */
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> matchList = new ArrayList<Card>();
		for (Card item:dealtList) { //TODO: the check method may need modification, check string or check card itself.
			if (item.toString().equals(suggestion.getRoom().toString())) {
				matchList.add(item);
			}
			if (item.toString().equals(suggestion.getWeapon().toString())) {
				matchList.add(item);
			}
			if (item.toString().equals(suggestion.getPerson().toString())) {
				matchList.add(item);
			}
		}
		if (matchList.size() == 0) {
			return null;
		}
		else {
			return matchList.get((int)(Math.random()*(matchList.size())));
		}
	}
	
	// Draw player with specific color in oval shape with black edge.
	public void draw (Graphics g, int size, int stacks) {
		g.setColor(Color.BLACK);
        g.fillOval((col * size) + (int)(stacks * size / 2), (row * size), size, size);
        g.setColor(color);
        g.fillOval((col * size) + 1 + (int)(stacks * size / 2), (row * size) + 1, (size - 2), (size - 2));
    }
	

	// Getters
	public String getName() {
		return name;
	}

	public ArrayList<Card> getDealtList() {
		return dealtList;
	}

	public ArrayList<Card> getSeenList() {
		return seenList;
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

	@Override
	public String toString() {
		return "Player [name=" + name + ", color=" + color + ", row=" + row + ", col=" + col + ", dealtList="
				+ dealtList + ", seenList=" + seenList + "]";
	}


}
