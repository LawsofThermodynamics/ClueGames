package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Room;
import clueGame.Solution;


class SelectTargetTest {
	private static Board board;
	
	@BeforeAll
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt"); // Set both config file names
		board.initialize(); // Load both config files
	}

	@Test
	void createSuggestionTest() {
		// Initialization for test
		
		ComputerPlayer testPlayer = new ComputerPlayer("Jimmy", Color.RED, 3, 3);
		
		BoardCell testLoc = testPlayer.selectTarget(8);
		
		assertTrue(testLoc.getCol() == 15);
		assertTrue(testLoc.getRow() == 6);
		
		Card newCard = new Card("Conservatory", CardType.ROOM);
		testPlayer.seenCard(newCard);
		
		testLoc = testPlayer.selectTarget(8);System.out.println("test" + testLoc.getCol());
		assertTrue(testLoc.getCol() == 15);
		assertTrue(testLoc.getRow() == 6);
		
		newCard = new Card("Lounge", CardType.ROOM);
		testPlayer.seenCard(newCard);
		
		testLoc = testPlayer.selectTarget(8);
		assertTrue(testLoc.getCol() == 6);
		assertTrue(testLoc.getRow() == 15);
		
	}
	

	
	

}

