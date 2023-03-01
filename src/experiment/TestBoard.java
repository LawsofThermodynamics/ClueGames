// Sihang Wang, Michael Basey
package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	// Variable declaration
	private Set<TestBoardCell> targetCells;
	private Set<TestBoardCell> visitedCells;
	private TestBoardCell[][] board;
	final static int COLS = 4;
	final static int ROWS = 4;


	//Default constructor.
	public TestBoard() {
		targetCells = new HashSet<TestBoardCell>();
		visitedCells = new HashSet<TestBoardCell>();
		
		// Initialize the board cell by cell.
		board = new TestBoardCell[ROWS][COLS];
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLS; ++j) {
				board[i][j] = new TestBoardCell(i, j);
			}
		}
		
		// Initialize adjList of each cell by iterating again.
		for (int i = 0; i < COLS; ++i) {
			for (int j = 0; j < ROWS; ++j) {
				if (i > 0) {
					board[i][j].addAdjacency(board[i - 1][j]);
				}
				if (j > 0) {
					board[i][j].addAdjacency(board[i][j - 1]);
				}
				if (i < ROWS - 1) {
					board[i][j].addAdjacency(board[i + 1][j]);
				}
				if (j < COLS - 1) {
					board[i][j].addAdjacency(board[i][j + 1]);
				}
			}
		}
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
				visitedCells.remove(currentCell);
			}
		}
		return;
	}

	// Returns the current cell 
	public TestBoardCell getCell(int row, int col) {
		return board[row][col];
	}

	// Returns current player's targetCell
	public Set<TestBoardCell> getTargets() {
		return this.targetCells;
	}
}
