package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	@Test
	public void testAdjacencyWalkways() {
		// Test a cell at bottom line with 3 adjacency cells.
		Set<BoardCell> testList = board.getAdjList(0, 12);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(0, 11)));
		assertTrue(testList.contains(board.getCell(0, 13)));
		assertTrue(testList.contains(board.getCell(1, 12)));
		
		// Test a cell at wall corner only has 2 adjacency.
		testList = board.getAdjList(2, 9);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(2, 9)));
		assertTrue(testList.contains(board.getCell(3, 8)));

	}
	
	@Test
	public void testAdjacencyRooms() {
		// Adjacency list of room center contains entrances and center of secret connected room.
		Set<BoardCell> testList = board.getAdjList(3, 3);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(5, 7)));
		assertTrue(testList.contains(board.getCell(7, 5)));
		assertTrue(testList.contains(board.getCell(27, 27)));
		
		// Non-center room cell has size 0 adjacency list.
		testList = board.getAdjList(15, 17);
		assertEquals(0, testList.size());
	}
	
	@Test
	public void testAdjacencyEdge() {
		Set<BoardCell> testList = board.getAdjList(21, 30);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(20, 30)));
		assertTrue(testList.contains(board.getCell(21, 29)));
		
		//This edge cell is a non-center room cell.
		testList = board.getAdjList(30, 25);
		assertEquals(0, testList.size());
		
		//TODO: corner cell, secret passage, need double check.
		testList = board.getAdjList(30, 0);
		assertEquals(0, testList.size());
	}
	
	@Test
	public void testAdjacencyDoor() {
		Set<BoardCell> testList = board.getAdjList(23, 5);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(23, 4)));
		assertTrue(testList.contains(board.getCell(23, 6)));
		assertTrue(testList.contains(board.getCell(22, 5)));
		assertTrue(testList.contains(board.getCell(27, 3)));

		testList = board.getAdjList(19, 14);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(19, 13)));
		assertTrue(testList.contains(board.getCell(19, 15)));
		assertTrue(testList.contains(board.getCell(24, 15)));
	}
}
