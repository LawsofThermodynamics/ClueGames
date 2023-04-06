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



class SelectTargetTest {
	private static Board board;

	@BeforeAll
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt"); // Set both config file names
		board.initialize(); // Load both config files
	}

	
	// Tests access to 2 rooms, and one secret passage
	@Test
	void SelectTargetTest() {
		// Initialization for test
		ComputerPlayer testPlayer = new ComputerPlayer("Jimmy", Color.RED, 3, 3);
		ArrayList<String> visitedList = new ArrayList<String>();
		BoardCell testLoc = new BoardCell();
		Card newCard;
		
		// Player visits 10 rooms
		for(int roomsToVisit = 0; roomsToVisit < 10; roomsToVisit++) {
			// Find the first room the player should visit
			testLoc = testPlayer.selectTarget(8);

			// Add the room to the player's seen list
			newCard = new Card(board.getRoomMap().get(testLoc.getInitial()).getName(), CardType.ROOM);
			testPlayer.seenCard(newCard);

			// Add room to test list if it is unique
			if(!visitedList.contains(board.getRoomMap().get(testLoc.getInitial()).getName())) {
				visitedList.add(board.getRoomMap().get(testLoc.getInitial()).getName());
			}

		}
		// Tests to see if the player has visited all 3 possible rooms
		assertTrue(visitedList.size() == 3);
		assertTrue(visitedList.contains("Conservatory"));
		assertTrue(visitedList.contains("Lounge"));
		assertTrue(visitedList.contains("Dining room"));
	}


	
	// Tests access to 5 rooms at once
	@Test
	void SelectTargetTest2() {
		// Initialization for test
		ComputerPlayer testPlayer = new ComputerPlayer("Jimmy", Color.RED, 15, 6);
		ArrayList<String> visitedList = new ArrayList<String>();
		BoardCell testLoc = new BoardCell();
		Card newCard;
		
		// Player visits 10 rooms
		for(int roomsToVisit = 0; roomsToVisit < 10; roomsToVisit++) {
			// Find the first room the player should visit
			testLoc = testPlayer.selectTarget(8);

			// Add the room to the player's seen list
			newCard = new Card(board.getRoomMap().get(testLoc.getInitial()).getName(), CardType.ROOM);
			testPlayer.seenCard(newCard);

			// Add room to test list if it is unique
			if(!visitedList.contains(board.getRoomMap().get(testLoc.getInitial()).getName())) {
				visitedList.add(board.getRoomMap().get(testLoc.getInitial()).getName());
			}
		}
		
		// Tests to see if the player has visited all 3 possible rooms
		assertTrue(visitedList.size() == 5);
		assertTrue(visitedList.contains("Billiard Room"));
		assertTrue(visitedList.contains("Library"));
		assertTrue(visitedList.contains("Hall"));
		assertTrue(visitedList.contains("Study"));
		assertTrue(visitedList.contains("Lounge"));
	}
	
	
	
	// Tests no room access
	@Test
	void SelectTargetTest3() {
		// Initialization for test
		ComputerPlayer testPlayer = new ComputerPlayer("Jimmy", Color.RED, 10, 1);
		ArrayList<String> visitedList = new ArrayList<String>();
		BoardCell testLoc = new BoardCell();
		Card newCard;
		
		// Player visits 10 rooms
		for(int roomsToVisit = 0; roomsToVisit < 10; roomsToVisit++) {
			// Find the first room the player should visit
			testLoc = testPlayer.selectTarget(2);

			// Add the room to the player's seen list
			newCard = new Card(board.getRoomMap().get(testLoc.getInitial()).getName(), CardType.ROOM);
			testPlayer.seenCard(newCard);

			// Add room to test list if it is unique
			if(!visitedList.contains(board.getRoomMap().get(testLoc.getInitial()).getName()) && !board.getRoomMap().get(testLoc.getInitial()).getName().equals("Walkway")) {
				visitedList.add(board.getRoomMap().get(testLoc.getInitial()).getName());
			}
		}
				
		// Tests to see if the player has visited all 3 possible rooms
		assertTrue(visitedList.size() == 0);
		
	}
}

