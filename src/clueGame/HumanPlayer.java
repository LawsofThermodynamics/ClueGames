/* Human player is a subset of the abstract Player class
 * 	Will contain gui and movement for the player to interact with the game
 * 
 * Authors: Sihang Wang, Michael Basey
 */
package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player {

	// Default constructor
	public HumanPlayer() {
		super();
	}
	
	// Parametric constructor
	public HumanPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}

}
