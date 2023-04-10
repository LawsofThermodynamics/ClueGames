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
	
	public ClueGame() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt"); // Set both config file names
		board.initialize(); // Load both config files	
		
		setSize(750, 750);  // Sets size of the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Stops program on window close
	}
	
	
	public static void main(String[] args) {
		ClueGame gameBoard = new ClueGame();
		
		gameBoard.setTitle("Off brand clue");
		gameBoard.add(board);
		gameBoard.setVisible(true); // Makes window visible

	}
}
