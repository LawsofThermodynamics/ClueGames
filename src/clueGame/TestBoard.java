// Sihang Wang, Michael Basey
package clueGame;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	// Variable declaration
	public static int BOARD_SIZE = 4;
	private Set<TestBoardCell> targetCells;
	private Set<TestBoardCell> visitedCells;


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


	//Recursive method for testing the possible location the player can move to
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		if (pathLength == 0) {
			if (!startCell.isRoom() && !startCell.getOccupied()) {
				targetCells.add(startCell);
			}
		}
		else {
			for(TestBoardCell currentCell :startCell.getAdjList()) {
				if (pathLength == 1) {
					targetCells.add(currentCell);
				} else {
					calcTargets(currentCell, (pathLength - 1));
				}
				visitedCells.remove(visitedCells.size() - 1);
			}
		}
		return;
	}

	// Returns the current cell 
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell cell = new TestBoardCell(row, col);
		return cell;
	}

	// Returns current player's targetCell
	public Set<TestBoardCell> getTargets() {
		return this.targetCells;
	}
}
