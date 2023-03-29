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
	private ArrayList<Card> cardList;
	
	// Default constructor
	public Player(String name, Color color, int row, int col) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
		cardList = new ArrayList<Card> ();
	}
	
	// Hand cards updating method with a card argument.
	public void updateHand(Card card) {
		
	}
}
