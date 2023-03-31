package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.DoorDirection;
import clueGame.Room;
import clueGame.Solution;


class GameSetupTests {
	private static Board board;
	
	@BeforeAll
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt"); // Set both config file names
		board.initialize(); // Load both config files
	}

	@Test
	void test() {
		ArrayList<Card> boardCardList = board.getCardList();
		Solution testSolution = board.getSolution();
		
		assertFalse(boardCardList.contains(testSolution.getRoom()));
		assertFalse(boardCardList.contains(testSolution.getPerson()));
		assertFalse(boardCardList.contains(testSolution.getWeapon()));
		
	}

}

