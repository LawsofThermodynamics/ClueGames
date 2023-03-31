/* Computer player is a subset of the abstract Player class
 * 	Will contain logic for the computer players to play the game against the human players
 * 
 * Authors: Sihang Wang, Michael Basey
 */
package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player {

	// Default constructor
	public ComputerPlayer() {
		super();
	}

	// Parametric constructor
	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);

	}

}
