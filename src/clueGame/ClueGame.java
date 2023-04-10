/* This class is the main entry point of whole game.
 * 
 * Author: Michael Basey, Sihang Wang 4/10/2023
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame{

	private static Board board;
	
	/* In constructor, initialize whole game board and read in game setting files.
	 * 
	 * Author: Michael Basey 4/10/2023
	 */
	public ClueGame() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt"); // Set both config file names
		board.initialize(); // Load both config files	
		setTitle("Off brand clue");
		setSize(750, 750);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Stops program on window close
	}
	
	
	public static void main(String[] args) {
		
		// Initialize sub-panels
		ClueGame gameBoard = new ClueGame();
		displayCardInterface cardPanel = new displayCardInterface();
		GameControlPanel controlPanel = new GameControlPanel();

		// Place sub-panels in main panel.
		gameBoard.add(board, BorderLayout.CENTER);
		gameBoard.add(cardPanel, BorderLayout.EAST);
		gameBoard.add(controlPanel, BorderLayout.SOUTH);
		
		gameBoard.setVisible(true); // Makes window visible

	}
}
