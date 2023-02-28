// Sihang Wang, Michael Basey
package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.TestBoard;
import clueGame.TestBoardCell;

class BoardTestsExp {
	TestBoard board;
	@BeforeEach
	public void setup() {
		board = new TestBoard();
	}
	
	@Test
	// Test cell at top left corner.
	void testAdj_TopLeft() {
		TestBoardCell cellX = board.getCell(0, 0);
		Set<TestBoardCell> testList = cellX.getAdjList();
		
		assertEquals(2, testList.size()); // Valid list size
		
		// Valid locations
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
	}
	
	@Test
	// Test cell at top right corner.
	void testAdj_TopRight() {
		TestBoardCell cellX = board.getCell(0, 3);
		Set<TestBoardCell> testList = cellX.getAdjList();
		
		assertEquals(2, testList.size()); // Valid list size
		
		// Valid locations
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertTrue(testList.contains(board.getCell(1, 3)));
	}
	
	@Test
	// Test cell at bottom right corner.
	void testAdj_BottomRight() {
		TestBoardCell cellX = board.getCell(3, 3);
		Set<TestBoardCell> testList = cellX.getAdjList();
		assertEquals(2, testList.size()); // Valid list size
		
		// Valid locations
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
	}
	
	@Test
	// Test cell at side line.
	void testAdj_RightEdge() {
		TestBoardCell cellX = board.getCell(3, 1);
		Set<TestBoardCell> testList = cellX.getAdjList();
		
		assertEquals(3, testList.size()); // Valid list size
		
		// Valid locations
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertTrue(testList.contains(board.getCell(2, 1)));
	}
	
	@Test
	// Test cell at middle.
	void testAdj_Middle() {
		TestBoardCell cellX = board.getCell(1, 1);
		Set<TestBoardCell> testList = cellX.getAdjList();
		
		assertEquals(4, testList.size()); // Valid list size
		
		// Valid locations
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(0, 1)));
	}
	
	@Test
	// Test simple targets from (0,0) within 3 steps.
	void testTargets_Distance3() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		
		assertEquals(6, targets.size()); // Valid list size
		
		// Valid locations
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));
	}
	
	@Test
	// Test simple targets from (0,0) within 6 steps.
	void testTargets_Distance6() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		
		assertEquals(7, targets.size()); // Valid list size
		
		// Valid locations
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	@Test
	// Test targets from (0,0) within 6 steps with room as obstacle
	void testTargets_Rooms() {
		// Sets Invalid cells
		board.getCell(2, 2).setRoom(true);
		
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		
		assertEquals(6, targets.size()); // Valid list size
		
		// Valid locations
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	@Test
	// Test targets from (0,0) within 6 steps with player occupied cells as obstacle
	void testTargets_Occupied() {
		// Sets Invalid cells
		board.getCell(3, 3).setOccupied(true);
		
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size()); // Valid list size
		
		// Valid locations
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
	}

}
