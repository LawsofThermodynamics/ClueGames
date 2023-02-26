package experiment;

import java.util.Set;

public class TestBoard {
	public static int BOARD_SIZE = 4;
	private Set<TestBoardCell> targetCells;
	
	//Default constructor.
	public TestBoard() {
		
	}
	
	public void calcTargets( TestBoardCell startCell, int pathlength) {
		
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell tmpCell = new TestBoardCell(row, col);
		return tmpCell;
	}
	
	public Set<TestBoardCell> getTargets() {
		return targetCells;
	}
}
