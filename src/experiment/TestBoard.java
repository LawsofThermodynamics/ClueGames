package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	public static int BOARD_SIZE = 4;
	private Set<TestBoardCell> targetCells;
	private Set<TestBoardCell> visitedCells;
	private TestBoardCell[][] board;
	
	//Default constructor.
	public TestBoard() {
		targetCells = new HashSet<TestBoardCell>();
		visitedCells = new HashSet<TestBoardCell>();
		/*
		board = new TestBoardCell[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; ++i) {
			for (int j = 0; j < BOARD_SIZE; ++j) {
				board[i][j] = new TestBoardCell(i, j);
			}
		}*/
	}
	
	
	//Recursive method.
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		/*
		if (pathlength == 0) {
			if (!startCell.isRoom() && !startCell.getOccupied()) {
				targetCells.add(startCell);
			}
			return;
		}
		else {
			if (visitedCells.contains(startCell)) {
				return;
			}
			
			visitedCells.add(startCell);
			for (TestBoardCell x : startCell.getAdjList()) {
				calcTargets(x, pathlength - 1);
			}
			visitedCells.remove(startCell);
			return;
		}
		*/return;
	}
	
	public TestBoardCell getCell(int row, int col) {
		//TODO, add try-catch to test if coordinates in board.
		//return board[row][col];
		TestBoardCell cell = new TestBoardCell(row, col);
		return cell;
	}
	
	public Set<TestBoardCell> getTargets() {
		return targetCells;
	}
}
