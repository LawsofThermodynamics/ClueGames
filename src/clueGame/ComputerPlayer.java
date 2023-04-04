/* Computer player is a subset of the abstract Player class
 * 	Will contain logic for the computer players to play the game against the human players
 * 
 * Authors: Sihang Wang, Michael Basey
 */
package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class ComputerPlayer extends Player {

	// Default constructor
	public ComputerPlayer() {
		super();
	}

	// Parametric constructor
	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);

	}

	// Create suggestion for the computer player
	public Solution createSuggestion(){
		Room currentRoom = Board.getInstance().getRoom(Board.getInstance().getCell(getRow(),getCol()).getInitial());

		Card tempRoomCard = new Card(currentRoom.getName(), CardType.ROOM);

		ArrayList<Card> temp = new ArrayList<Card>();
		temp.addAll(this.getDealtList());
		temp.addAll(this.getSeenList());

		

		ArrayList<Card> unseenWep = new ArrayList<Card>();
		ArrayList<Card> unseenPer = new ArrayList<Card>();

		System.out.println(temp);
		System.out.println(Board.getAllWeapon());

		for(Card card : Board.getAllWeapon()) {
			if (!temp.contains(card)) {
				unseenWep.add(card);
				System.out.println("added unseen wep");
			} 
		}

		for(Card card : Board.getAllPerson()) {
			if (!temp.contains(card)) {
				unseenPer.add(card);
				System.out.println("added unseen per");
			} 

		}

		int tempWeponCard = (int)(Math.random()*(unseenWep.size()));  
		int tempPersonCard = (int)(Math.random()*(unseenPer.size()));  

		Solution guess = new Solution(tempRoomCard, unseenPer.get(tempPersonCard), unseenWep.get(tempWeponCard));

		return guess;
	}

	public BoardCell selectTarget() {
		return null;
	}
}
