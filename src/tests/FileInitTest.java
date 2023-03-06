package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTest{
	// Constants used to test if the file was loaded correctly
	public static final int LEGEND_SIZE = 12;
	public static final int NUM_ROWS_X = 31;
	public static final int NUM_COLUMNS_X = 31;

	private static Board board;

	@BeforeAll
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt"); // Set both config file names
		board.initialize(); // Load both config files
	}

	@Test
	public void testRoomLabels() {
		// To ensure data is correctly loaded, test retrieving a few rooms
		// from the hash, including the first and last in the file and a few others
		assertEquals("Room-A", board.getRoom('A').getName());
		assertEquals("Room-B", board.getRoom('B').getName());
		assertEquals("Room-C", board.getRoom('C').getName());
		assertEquals("Room-I", board.getRoom('I').getName());
	}

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS_X, board.getNumRows());
		assertEquals(NUM_COLUMNS_X, board.getNumColumns());
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
	// a cell that are not a doorway
	@Test
	public void FourDoorDirections() {
		BoardCell cell = board.getCell(5, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(7, 5);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(6, 10);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(12, 13);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test that walkways are not doors
		cell = board.getCell(7, 4);
		assertFalse(cell.isDoorway());
	}


	// Test for the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway()) {
					numDoors++;
				}
			}
		Assert.assertEquals(32, numDoors);
	}

	// Test room cells to ensure the room data is correct
	@Test
	public void testRooms() {
		// random room location
		BoardCell cell = board.getCell( 5, 1);
		Room room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Room-A") ;
		assertFalse( cell.isLabel() );
		assertFalse( cell.isRoomCenter() ) ;
		assertFalse( cell.isDoorway()) ;

		// label cell test
		cell = board.getCell(2, 27);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Room-B") ;
		assertTrue( cell.isLabel() );
		assertTrue( room.getLabelCell() == cell );

		// room center cell test
		cell = board.getCell(6, 15);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Room-C") ;
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );

		// secret passage test
		cell = board.getCell(30, 30);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Room-E") ;
		assertTrue( cell.getSecretPassage() == 'A' );

		// walkway test
		cell = board.getCell(8, 8);
		room = board.getRoom( cell ) ;
		// Note for our purposes, walkways and closets are rooms
		assertTrue( room != null );
		assertEquals( room.getName(), "Walkway") ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );

		// test non traversable cell
		cell = board.getCell(9, 9);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Not Traversable") ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );

	}

}
