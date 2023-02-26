package experiment;

import java.util.Set;

public class TestBoard {
	public static int BOARD_SIZE = 4;
	private Set<TestBoardCell> targetCells;
	private Set<TestBoardCell> visitedCells;
	private TestBoardCell[][] board;
	
	//Default constructor.
	public TestBoard() {
		board = new TestBoardCell[BOARD_SIZE][BOARD_SIZE];
	}
	
	
	//Recursive method.
	public void calcTargets(TestBoardCell startCell, int pathlength) {
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
	}
	
	public TestBoardCell getCell(int row, int col) {
		//TODO, add try-catch to test if coordinates in board.
		return board[row][col];
	}
	
	public Set<TestBoardCell> getTargets() {
		return targetCells;
	}
}
