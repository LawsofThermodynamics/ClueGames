/* This class is the main entry point of whole game.
 * 
 * Author: Michael Basey, Sihang Wang 4/10/2023
 */
package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame{

	private static Board board;
	private static ClueGame gameFrame;
	private static GameControlPanel ctrlPanel;
	private static CardInfoPanel cardPanel;
	private static boolean gameDone = false;
	
	/* In constructor, initialize whole game board and read in game setting files.
	 * 
	 * Author: Sihang, Michael 4/10/2023
	 */
	public ClueGame() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt"); // Set both config file names
		board.initialize(); // Load both config files	
		//System.out.println(board.getPlayerList().get(0).getDealtList());
		setTitle("Off brand clue");
		setSize(750, 750);
		
		ctrlPanel = GameControlPanel.getCtrlPanel();
		cardPanel = CardInfoPanel.getCardPanel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Stops program on window close
	}
	
	/* Display opening splash screen
	 * 
	 * Author: Sihang Wang 4/12/2023
	 */
	public void splash() {
		String title = "Welcome to CLUE";
		String text = "You are " + board.getPlayerList().get(0) + "\nCan you find solution\nbefore the computer players?";
		JOptionPane.showMessageDialog(this, text, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void update() {
		CardInfoPanel.updatePanels(gameFrame, cardPanel);
	}
	
	
	public static void main(String[] args) {
		
		// Initialize game frame
		gameFrame = new ClueGame();
		
		// Place board and sub-panels
		gameFrame.add(board, BorderLayout.CENTER);
		gameFrame.add(cardPanel, BorderLayout.EAST);
		gameFrame.add(ctrlPanel, BorderLayout.SOUTH);
		
		gameFrame.setVisible(true); // Makes window visible
		gameFrame.splash(); // Display splash screen
		
		//board.getPlayerList().get(0).seenCard(new Card("Room", CardType.ROOM));
		//board.getPlayerList().get(0).seenCard(new Card("Room2", CardType.ROOM));
		//board.getPlayerList().get(0).seenCard(new Card("Room3", CardType.ROOM));
		//System.out.println(board.getPlayerList().get(0).getSeenCards());
		//System.out.println(board.getPlayerList().get(0).getDealtCards());
		
		board.playerTurn();

	}

	public static CardInfoPanel getCardPanel() {
		return cardPanel;
	}
	public static GameControlPanel getControlPanel(){
		return ctrlPanel;
	}

	public static boolean isGameDone() {
		return gameDone;
	}

	public static void setGameDone(boolean gameVal) {
		gameDone = gameVal;
	}
	
	
	
}
