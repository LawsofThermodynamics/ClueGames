/* This class is to create game control panel.
 * 
 * Author: Michael Basey, Sihang Wang 4/6/2023
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class displayCardInterface extends JPanel {

	// Variable declaration
	private String[] infoPanelNames = {"People", "Rooms", "Weapons"};
	private CardType[] cardTypes = {CardType.PERSON, CardType.ROOM, CardType.WEAPON};

	private static HumanPlayer testPerson = new HumanPlayer("testPerson", Color.RED, 0, 0);	// Test player, REMOVE LATER


	/* Creates outer panel, and initiates the three sub-panel creations
	 *  Sets the inner panel to contain the 3 different panels for cards
	 *  Calls helper function to initiate the inner sub-sub-panels
	 *  
	 * Author: Michael, Sihang 4/7/2023
	 */
	public displayCardInterface() {	
		setSize(250, 750);
		setLayout(new GridLayout(1, 0)); // Sets outer size to 1 large panel

		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(infoPanelNames.length, 0)); 
		outerPanel.setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));

		// Loops through the inner sub-panel creation ["People", "Rooms", "Weapons"]
		for(int panes = 0; panes < infoPanelNames.length; panes++) {
			JPanel innerPanel = cardDisplayWindow(infoPanelNames[panes], cardTypes[panes]);
			outerPanel.add(innerPanel, BorderLayout.AFTER_LINE_ENDS);
		}

		add(outerPanel, BorderLayout.AFTER_LINE_ENDS);
	}




	/* Creates inner card panels
	 *  Sets the inner panel's data to the cards of the player
	 *  currently, player is passed in manually as we do not have anything to call this main function
	 *  
	 * Author: Michael, Sihang 4/7/2023
	 */
	private JPanel cardDisplayWindow(String string, CardType type) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 0));
		panel.setBorder(new TitledBorder (new EtchedBorder(), string));


		// Initiates jPanel and labels for in hand card list 
		JLabel inHandText = new JLabel("In Hand:");

		JPanel inHand = new JPanel();
		inHand = assignCardsDealt(inHand, type);


		// Initiates jPanel and labels for seen card list 
		JLabel seenText = new JLabel("Seen:");

		JPanel seen = new JPanel();
		seen = assignCardsSeen(seen, type);


		// Adds all panels and structures to upper sub-panel
		panel.add(inHandText, BorderLayout.AFTER_LINE_ENDS);
		panel.add(inHand, BorderLayout.AFTER_LINE_ENDS);

		panel.add(seenText, BorderLayout.AFTER_LINE_ENDS);
		panel.add(seen, BorderLayout.AFTER_LINE_ENDS);


		return panel;
	}


	/* Loads cards from player and into a jpanel for displaying to gui
	 *  Loads provided type of cards to proviced panel
	 *  
	 * Author: Michael, Sihang 4/7/2023
	 */
	private JPanel assignCardsDealt(JPanel panel, CardType type) {
		panel.setLayout(new GridLayout(2, 0));

		int addedCards = 0;

		// Loops through player's cards for matching type to add to panel
		for(int i = 0; i < testPerson.getDealtCards().size(); i++)
		{
			if(testPerson.getDealtCards().get(i).getType() == type) {
				JPanel cardPanel = new JPanel();
				cardPanel.setLayout(new GridLayout(1, 0));
				cardPanel.setBorder(new TitledBorder (new EtchedBorder()));

				JLabel newCard = new JLabel(testPerson.getDealtCards().get(i).getName());
				cardPanel.add(newCard);
				panel.add(cardPanel);
				addedCards++;
			}
		}
		// Adds special none panel if no cards were found that matched criteria
		if(addedCards == 0) {
			JPanel cardPanel = new JPanel();
			cardPanel.setLayout(new GridLayout(1, 0));
			cardPanel.setBorder(new TitledBorder (new EtchedBorder()));

			JLabel newCard = new JLabel("None");
			cardPanel.add(newCard);
			panel.add(cardPanel);
		}

		return panel;
	}


	private JPanel assignCardsSeen(JPanel panel, CardType type) {
		panel.setLayout(new GridLayout(2, 0));

		int addedCards = 0;
		
		// Loops through player's cards for matching type to add to panel
		for(int i = 0; i < testPerson.getSeenCards().size(); i++)
		{
			if(testPerson.getSeenCards().get(i).getType() == type) {
				JPanel cardPanel = new JPanel();
				cardPanel.setLayout(new GridLayout(1, 0));
				cardPanel.setBorder(new TitledBorder (new EtchedBorder()));

				JLabel newCard = new JLabel(testPerson.getSeenCards().get(i).getName());
				cardPanel.add(newCard);
				panel.add(cardPanel);
				addedCards++;
			}
		}

		// Adds special none panel if no cards were found that matched criteria
		if(addedCards == 0) {
			JPanel cardPanel = new JPanel();
			cardPanel.setLayout(new GridLayout(1, 0));
			cardPanel.setBorder(new TitledBorder (new EtchedBorder()));

			JLabel newCard = new JLabel("None");
			cardPanel.add(newCard);
			panel.add(cardPanel);
		}
		
		return panel;
	}

	/* Loads cards into test player
	 *  
	 * Author: Michael, Sihang 4/7/2023
	 */
	private static void configurePlayer() {
		Card testCard = new Card();
		ArrayList<Card> cardList = new ArrayList<Card>();

		testCard = new Card("TestPerson1", CardType.PERSON);
		cardList.add(testCard);
		testCard = new Card("TestPerson2", CardType.PERSON);
		cardList.add(testCard);

		testCard = new Card("TestRoom2", CardType.ROOM);
		cardList.add(testCard);
		testCard = new Card("TestRoom1", CardType.ROOM);
		cardList.add(testCard);

		//testCard = new Card("TestWeapon1", CardType.WEAPON);
		//cardList.add(testCard);
		//testCard = new Card("TestWeapon2", CardType.WEAPON);
		//cardList.add(testCard);

		testPerson.deltCard(cardList);
		
	
		
		cardList = new ArrayList<Card>();
		
		//testCard = new Card("TestPerson3", CardType.PERSON);
		//cardList.add(testCard);
		//testCard = new Card("TestPerson4", CardType.PERSON);
		//cardList.add(testCard);

		testCard = new Card("TestRoom3", CardType.ROOM);
		cardList.add(testCard);
		testCard = new Card("TestRoom4", CardType.ROOM);
		cardList.add(testCard);

		testCard = new Card("TestWeapon3", CardType.WEAPON);
		cardList.add(testCard);
		testCard = new Card("TestWeapon4", CardType.WEAPON);
		cardList.add(testCard);
		
		testPerson.seenCard(cardList);
	}


	/* Updates the current panel when called
	 *  
	 * Author: Michael, Sihang 4/7/2023
	 */
	private static void updatePanels(JFrame frame, displayCardInterface panel){
		panel.removeAll();
		panel = new displayCardInterface();  // create the panel
		frame.add(panel);
	}



	/* Loads player's card interface
	 *  
	 * Author: Michael, Sihang 4/7/2023
	 */
	public static void main(String[] args) {
		displayCardInterface panel = new displayCardInterface();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame


		frame.setSize(300, 750);  // Sets size of the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Stops program on window close
		frame.setVisible(true); // Makes window visible

		configurePlayer();

		updatePanels(frame, panel);

	}
}
