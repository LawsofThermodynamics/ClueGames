package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

class BoardTestsExp {
	TestBoard board;
	@BeforeEach
	public void setup() {
		board = new TestBoard();
	}
	
	@Test
	//Test cell at top left corner.
	void testAdj_0_0() {
		TestBoardCell cellX = board.getCell(0, 0);
		Set<TestBoardCell> testList = cellX.getAdjList();
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertEquals(2, testList.size());
	}
	
	@Test
	//Test cell at top right corner.
	void testAdj_0_3() {
		TestBoardCell cellX = board.getCell(0, 3);
		Set<TestBoardCell> testList = cellX.getAdjList();
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertEquals(2, testList.size());
	}
	
	@Test
	//Test cell at bottom right corner.
	void testAdj_3_3() {
		TestBoardCell cellX = board.getCell(3, 3);
		Set<TestBoardCell> testList = cellX.getAdjList();
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(2, testList.size());
	}
	
	@Test
	//Test cell at side line.
	void testAdj_3_1() {
		TestBoardCell cellX = board.getCell(3, 1);
		Set<TestBoardCell> testList = cellX.getAdjList();
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(3, testList.size());
	}
	
	@Test
	//Test cell at middle.
	void testAdj_1_1() {
		TestBoardCell cellX = board.getCell(1, 1);
		Set<TestBoardCell> testList = cellX.getAdjList();
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(0, 1)));
	}
	
	@Test
	//Test simple targets from (0,0) within 3 steps.
	void testTargets_1() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));
	}
	

}
