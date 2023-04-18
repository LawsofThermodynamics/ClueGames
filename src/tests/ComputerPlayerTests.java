package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;


class ComputerPlayerTests {
	private static Board board;
	
	@BeforeEach
	public void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt"); // Set both config file names
		board.initialize(); // Load both config files
	}

	@Test
	void createSuggestionTest() {
		// Initialization for test
		
		ComputerPlayer testPlayer = new ComputerPlayer("Jimmy", Color.RED, 2, 2);
		
		Card testRoom = new Card("Hall", CardType.ROOM);
		
		ArrayList<Card> fullRoom = new ArrayList<Card>();
		fullRoom.addAll(Board.getAllRoom());
		
		ArrayList<Card> fullPerson = new ArrayList<Card>();
		fullPerson.addAll(Board.getAllPerson());
		
		ArrayList<Card> fullWeapon = new ArrayList<Card>();
		fullWeapon.addAll(Board.getAllWeapon());
		
		
		int roomCard = (int)(Math.random()*(fullRoom.size()));  
		int personCard = (int)(Math.random()*(fullPerson.size()));  
		int weaponCard = (int)(Math.random()*(fullWeapon.size()));
		
		Solution Solution = new Solution(testRoom, fullPerson.get(personCard), fullWeapon.get(weaponCard));	

		// Removes the solution cards from the deck to prevent the players from getting the cards within the solution
		fullRoom.remove(roomCard);
		fullPerson.remove(personCard);
		fullWeapon.remove(weaponCard);
		
		testPlayer.seenCard(fullRoom);
		testPlayer.seenCard(fullPerson);
		testPlayer.seenCard(fullWeapon);
		
		Solution guessSolution = testPlayer.createSuggestion();
		
		assertTrue(guessSolution.equals(Solution));
	}
	
	

}

