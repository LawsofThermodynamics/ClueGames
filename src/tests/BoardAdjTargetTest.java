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
		testList = board.getAdjList(4, 10);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(3, 10)));
		assertTrue(testList.contains(board.getCell(5, 10)));

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
	
	@Test
	public void testTargetsInWalkway() {
		// test a roll of 1
		board.calcTargets(board.getCell(19, 3), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(19, 2)));
		assertTrue(targets.contains(board.getCell(20, 3)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(8, 6), 3);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(7, 4)));
		assertTrue(targets.contains(board.getCell(8, 3)));
		assertTrue(targets.contains(board.getCell(8, 5)));	
		assertTrue(targets.contains(board.getCell(7, 6)));
		assertTrue(targets.contains(board.getCell(8, 7)));
		assertTrue(targets.contains(board.getCell(8, 9)));
		assertTrue(targets.contains(board.getCell(6, 7)));
		assertTrue(targets.contains(board.getCell(10, 7)));
		assertTrue(targets.contains(board.getCell(7, 8)));
		assertTrue(targets.contains(board.getCell(9, 8)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 21), 4);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(12, 18)));
		assertTrue(targets.contains(board.getCell(13, 19)));
		assertTrue(targets.contains(board.getCell(8, 22)));
		assertTrue(targets.contains(board.getCell(9, 23)));	
		assertTrue(targets.contains(board.getCell(10, 24)));	
	}
	
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(13, 19), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(13, 18)));
		assertTrue(targets.contains(board.getCell(12, 19)));	
		assertTrue(targets.contains(board.getCell(14, 19)));	
		
		// test a roll of 2, at door
		board.calcTargets(board.getCell(18, 11), 2);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(16, 11)));
		assertTrue(targets.contains(board.getCell(17, 12)));
		assertTrue(targets.contains(board.getCell(18, 13)));
		assertTrue(targets.contains(board.getCell(19, 12)));
		assertTrue(targets.contains(board.getCell(20, 11)));
		assertTrue(targets.contains(board.getCell(19, 10)));	
	}
	
	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 2 with a target cell blocked.
		board.getCell(8, 10).setOccupied(true);
		board.calcTargets(board.getCell(9, 11), 2);
		board.getCell(8, 10).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(10, 12)));
		assertFalse(targets.contains(board.getCell(8, 10)));
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(20, 6).setOccupied(true);
		board.getCell(19, 3).setOccupied(true);
		board.calcTargets(board.getCell(20, 4), 2);
		board.getCell(20, 6).setOccupied(false);
		board.getCell(19, 3).setOccupied(false);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(20, 2)));	
		assertTrue(targets.contains(board.getCell(21, 3)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(14, 19).setOccupied(true);
		board.getCell(16, 19).setOccupied(true);
		board.calcTargets(board.getCell(15, 24), 2);
		board.getCell(14, 19).setOccupied(false);
		board.getCell(16, 19).setOccupied(false);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(10, 23)));
		assertTrue(targets.contains(board.getCell(16, 29)));	
		assertTrue(targets.contains(board.getCell(20, 25)));
		assertFalse(targets.contains(board.getCell(15, 19)));
	}
	
	// Tests out of room center, 1, 2
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInRoom() {
		// test a roll of 1, from room C
		board.calcTargets(board.getCell(3, 27), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(27, 3)));
		assertTrue(targets.contains(board.getCell(5, 23)));
		assertTrue(targets.contains(board.getCell(7, 25)));	
		
		// test a roll of 2, from room F
		board.calcTargets(board.getCell(24, 15), 2);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(25, 10)));
		assertTrue(targets.contains(board.getCell(19, 15)));	
		assertTrue(targets.contains(board.getCell(29, 16)));
		assertTrue(targets.contains(board.getCell(23, 20)));
	}
}
