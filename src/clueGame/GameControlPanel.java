package clueGame;

import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameControlPanel extends JPanel {

	public GameControlPanel() {
		setLayout(new GridLayout(3, 0));
		JPanel p = buttonPanel();
		add(p);
	}
	
	private JPanel buttonPanel() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,4));
		JLabel currPlayer = new JLabel("Who's turn?");
		JButton makeAccu = new JButton("Make Accusation");
		JButton next = new JButton("NEXT!");
		buttons.add(currPlayer);
		buttons.add(makeAccu);
		buttons.add(next);
		return buttons;
	}
	
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		/*
		panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
		*/
	}
}
