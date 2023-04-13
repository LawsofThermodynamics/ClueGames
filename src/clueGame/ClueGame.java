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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame{

	private static Board board;
	private static GameControlPanel ctrlPanel;
	private static CardInfoPanel cardPanel;
	
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
		
		ctrlPanel = GameControlPanel.getCtrlPanel();
		cardPanel = CardInfoPanel.getCardPanel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Stops program on window close
	}
	
	// Splash screen at the beginning.
	public void splash() {
		String title = "Welcome to CLUE";
		String text = "You are Miss Scarlett\nCan you find solution\nbefore computer players?";
		JOptionPane.showMessageDialog(this, text, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	public static void main(String[] args) {
		
		// Initialize sub-panels
		ClueGame gameBoard = new ClueGame();
		gameBoard.splash();
		
		// Place sub-panels
		gameBoard.add(board, BorderLayout.CENTER);
		gameBoard.add(cardPanel, BorderLayout.EAST);
		gameBoard.add(ctrlPanel, BorderLayout.SOUTH);
		
		gameBoard.setVisible(true); // Makes window visible

	}
}
