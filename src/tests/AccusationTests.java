package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class AccusationTests {
	private static Board board;
	
	@BeforeAll
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt"); // Set both config file names
		board.initialize(); // Load both config files
	}
	Card hall = new Card("hall", CardType.ROOM);
	Card lounge = new Card("lounge", CardType.ROOM);
	Card kitchen = new Card("kitchen", CardType.ROOM);
	
	Card Jim = new Card("Jim", CardType.PERSON);
	Card Kate = new Card("Kate", CardType.PERSON);
	Card Tom = new Card("Tom", CardType.PERSON);
	
	Card lead = new Card("lead", CardType.WEAPON);
	Card rope = new Card("rope", CardType.WEAPON);
	Card wrench = new Card("wrench", CardType.WEAPON);
	
	
	@Test
	void accusationTest() {
		Solution sol = board.getSolution();
		Solution solW = new Solution(sol.getRoom(), sol.getPerson(), rope);
		Solution solP = new Solution(sol.getRoom(), Jim, sol.getWeapon());
		Solution solR = new Solution(hall, sol.getPerson(), sol.getWeapon());
		assertTrue(sol.equals(board.getSolution()));
		assertFalse(sol.equals(solR));
		assertFalse(sol.equals(solP));
		assertFalse(sol.equals(solW));
		
	}
	
	@Test
	void playerDisprove() {
		HumanPlayer player = new HumanPlayer("P", Color.WHITE, 0, 0);
		player.deltCard(hall);
		player.deltCard(Jim);
		player.deltCard(lead);
		Solution accusation1 = new Solution(lounge, Kate, wrench); // Case that player has no matching card
		Solution accusation2 = new Solution(hall, Kate, wrench); // Case that player has 1 matching card
		Solution accusation3 = new Solution(hall, Jim, lead); // Case that player has 3 matching card
		
		Card result;
		result = player.disproveSuggestion(accusation1);
		assertTrue(result==null);
		
		result = player.disproveSuggestion(accusation2);
		assertTrue(result == hall);
		
		int[] times = new int[3];
		int testTimes = 50;
		for (int i = 0; i < testTimes; ++i) {
			result = player.disproveSuggestion(accusation3);
			if (result.toString().equals(hall.toString())) {
				++times[0];
			}
			else if (result.toString().equals(Jim.toString())) {
				++times[1];
			}
			else if (result.toString().equals(lead.toString())) {
				++times[2];
			}
		}
		// Theoretically times[0/1/2] should equals = testTimes/3, test with testTimes/5 for random result.
		assertTrue(times[0] > testTimes/5);
		assertTrue(times[1] > testTimes/5);
		assertTrue(times[2] > testTimes/5);
		
		// When have more than 1 cards matching, total disprove times equals testTimes.
		int total = times[0] + times[1] + times[2];
		assertTrue(total == testTimes);
	}
	
	@Test
	void handleSuggestions() {
		HumanPlayer pJim = new HumanPlayer("Jim", Color.WHITE, 0, 0);
		pJim.deltCard(hall);
		pJim.deltCard(Jim);
		pJim.deltCard(lead);
		ComputerPlayer pKate = new ComputerPlayer("Kate", Color.WHITE, 0, 0);
		pKate.deltCard(lounge);
		pKate.deltCard(Kate);
		pKate.deltCard(rope);
		ComputerPlayer pTom = new ComputerPlayer("Tom", Color.WHITE, 0, 0);
		pTom.deltCard(kitchen);
		pTom.deltCard(Tom);
		pTom.deltCard(wrench);
		ArrayList<Player> tmpList = new ArrayList<Player>();
		
	}

}
