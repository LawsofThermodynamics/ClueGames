package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;


class GameSetupTests {
	private static Board board;
	
	@BeforeAll
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt"); // Set both config file names
		board.initialize(); // Load both config files
	}

	@Test
	void solutionChosen() {
		// Initialization for test
		ArrayList<Card> boardCardList = board.getCardList();
		Solution testSolution = board.getSolution();
		
		// Tests to insure the desired card are not within the deal deck
		assertFalse(boardCardList.contains(testSolution.getRoom()));
		assertFalse(boardCardList.contains(testSolution.getPerson()));
		assertFalse(boardCardList.contains(testSolution.getWeapon()));	
	}
	
	
	@Test
	void playerData() {
		// Initialization for test
		ArrayList<Player> playerList = board.getPlayerList();
		
		// Tests to insure the players have been initialized correcly
		assertTrue(playerList.get(0).getColor() == Color.RED);
		assertTrue(playerList.get(3).getColor() == Color.GREEN);
		
		assertTrue(playerList.get(1).getName().equals("Colonel Mustard"));
		assertTrue(playerList.get(4).getName().equals("Mrs. Peacock"));
		
		assertTrue(playerList.get(2).getRow() == 28);
		assertTrue(playerList.get(5).getCol() == 2);
	}
	
	@Test
	void playerHands() {
		// Initialization for test
		ArrayList<Player> playerList = board.getPlayerList();
		
		// Tests to insure the players have been dealt the same amount of cards 
		for(int players = 0; players < playerList.size(); players++) {
			System.out.println(playerList.get(0).getDealtCards().size());
			System.out.println(playerList.get(players).getDealtCards().size());
			assertTrue(playerList.get(0).getDealtCards().size() == playerList.get(players).getDealtCards().size());
		}
	}
	
	

}

