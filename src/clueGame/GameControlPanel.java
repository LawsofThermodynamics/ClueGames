/* Class contains the instructions to create the games gui control panel including interfaces for next turn and information regarding the players turn
 * 
 * Author: Sihang Wang 4/17/2023
 */
package clueGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {

	private JTextField name, rollVal;
	JTextArea guess;
	JTextArea guessResult;
	private NextListener nextListener = new NextListener();
	private AccuListener AccuListener = new AccuListener();


	// The singleton of GameControlPanel instance
	private static GameControlPanel ctrlPanel = new GameControlPanel();

	private GameControlPanel() {
		super();
		setLayout(new GridLayout(2, 0));
		JPanel p1 = buttonPanel();
		add(p1, BorderLayout.NORTH);
		JPanel p2 = infoPanel();
		add(p2, BorderLayout.SOUTH);

	}

	// Return the singletong of GameControlPanel instance
	public static GameControlPanel getCtrlPanel() {
		return ctrlPanel;
	}

	/* Create the first row of panel, include current player, rolled value
	 * And two buttons.
	 * 
	 * Author: Michael, Sihang 4/6/2023
	 */
	private JPanel buttonPanel() {
		JPanel buttonP = new JPanel();
		buttonP.setLayout(new GridLayout(1,4));

		// The panel shows current player in vertical structure
		JPanel turnPanel = new JPanel();
		JLabel currPlayer = new JLabel("Who's turn?");
		name = new JTextField(10);
		name.setEditable(false);
		turnPanel.add(currPlayer, BorderLayout.NORTH);
		turnPanel.add(name, BorderLayout.SOUTH);

		// This panel shows roll result
		JPanel rollPanel = new JPanel();
		JLabel roll = new JLabel("Roll: ");
		rollVal = new JTextField(10);
		rollVal.setEditable(false);
		rollPanel.add(roll, BorderLayout.WEST);
		rollPanel.add(rollVal, BorderLayout.EAST);

		// Create two buttons and add mouse listeners.
		JButton makeAccu = new JButton("Make Accusation");
		makeAccu.addMouseListener(AccuListener);
		JButton next = new JButton("NEXT!");
		next.addMouseListener(nextListener);

		buttonP.add(turnPanel);
		buttonP.add(rollPanel);
		buttonP.add(makeAccu);
		buttonP.add(next);
		return buttonP;
	}

	/* Create second row of panel, contain guess and guess result.
	 * 
	 * Author: Michael, Sihang 4/6/2023
	 */
	private JPanel infoPanel() {
		JPanel infoP = new JPanel();
		infoP.setLayout(new GridLayout(0,2));

		JPanel guessPanel = new JPanel();
		guess = new JTextArea();
		guess.setBounds(40,75,250,200);
		guess.setEditable(false);
		guessPanel.add(guess);
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));

		JPanel guessResPanel = new JPanel();
		guessResult = new JTextArea();
		guessResult.setBounds(40,75,250,200);
		guessResult.setEditable(false);
		guessResPanel.add(guessResult);
		guessResPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));

		infoP.add(guessPanel);
		infoP.add(guessResPanel);
		return infoP;
	}


	// Setters to update the data in various text fields
	public void setTurn(Player player, int val) {
		name.setText(player.getName());
		rollVal.setText(Integer.toString(val));
	}

	public void setGuess(String str) {
		guess.setText(str);
	}

	public void setGuessResult(String str) {
		guessResult.setText(str);
	}

	public class NextListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(ClueGame.isGameDone()) {
				Board.getInstance().splashScreen("The game is over, there is no more mystery to solve");
				return;
			}
			Board.getInstance().nextFlow();
		}

		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}

	}

	public class AccuListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(ClueGame.isGameDone()) {
				Board.getInstance().splashScreen("The game is over, there is no more mystery to solve");
				return;
			}

			if(!Board.getInstance().isCurrTurnDone()) {
				Board.getInstance().makeAccusation(Board.getInstance().getPlayerList().get(0));
			} else {
				Board.getInstance().splashScreen("You may not make an accusation at this time");
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}

	}


	//	public static void main(String[] args) {
	//		GameControlPanel panel = new GameControlPanel();  // create the panel
	//		JFrame frame = new JFrame();  // create the frame 
	//		frame.setContentPane(panel); // put the panel in the frame
	//		frame.setSize(750, 180);  // size the frame
	//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
	//		frame.setVisible(true); // make it visible
	//
	//		// test filling in the data
	//
	//		panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.ORANGE, 0, 0), 5);
	//		panel.setGuess( "I have no guess!");
	//		panel.setGuessResult( "So you have nothing?");
	//
	//	}
}
