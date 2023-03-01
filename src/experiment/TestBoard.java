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


	/* Recursive method for testing the possible location the player can move to
	 * Player can pass by occupied cell, but not the room/visited cell.
	 * Player must finally move to unoccupied cell.
	 */
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		if (pathLength == 0) {
			if (!startCell.getOccupied()) {
				targetCells.add(startCell);
			}
		}
		else {
			visitedCells.add(startCell);
			for(TestBoardCell currCell : startCell.getAdjList()) {
				// If the adjacent cell is not room and not visited, continue recursive.
				if (!visitedCells.contains(currCell) && !startCell.isRoom()) {
					calcTargets(currCell, pathLength - 1);
				}
			}
			visitedCells.remove(startCell);
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
