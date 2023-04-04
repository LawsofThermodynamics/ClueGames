/* Player Class stores data of all player human or computer during the game 
 * 	Player stores its name as a string, color for gui, position, and cards it was dealt and shown throughout the game
 * 
 * Authors: Sihang Wang, Michael Basey
 * */
package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name; // Stores the name of the player, read from clueSettup
	private Color color; // Stores color of the player, read from clueSettup
	private int row, col; // Stores position of player, read from clueSettup
	private ArrayList<Card> dealtList; // Stores cards the player was dealt at the beginning of the game, only allowed cards the player can show other players
	private ArrayList<Card> seenList; // Stores cards the player has been shown by other players, updates throughout the game

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
