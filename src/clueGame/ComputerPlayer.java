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

	/* Create suggestion for the computer player by finding a list of weapon and player cards the computer has not seen yet
	 * and picking one from each at random to add to the suggested solution, in addition to the current room the player is occupying
	 * 
	 * Michael, Sihang 4/3/2023
	 */
	public Solution createSuggestion(){
		// Variable initialization
		ArrayList<Card> unseenWep = new ArrayList<Card>();
		ArrayList<Card> unseenPer = new ArrayList<Card>();
		
		ArrayList<Card> temp = new ArrayList<Card>();
		temp.addAll(this.getDealtList());
		temp.addAll(this.getSeenList());

		Room currentRoom = Board.getInstance().getRoom(Board.getInstance().getCell(getRow(),getCol()).getInitial());

		Card tempRoomCard = new Card(currentRoom.getName(), CardType.ROOM);


		// If the card is already known by the computer, do not use
		for(Card card : Board.getAllWeapon()) {
			if (!temp.contains(card)) {
				unseenWep.add(card);
			} 
		}
		
		// If the card is already known by the computer, do not use
		for(Card card : Board.getAllPerson()) {
			if (!temp.contains(card)) {
				unseenPer.add(card);
			} 

		}

		// Picks a card at random from the valid cards for the computer player
		int tempWeponCard = (int)(Math.random()*(unseenWep.size()));  
		int tempPersonCard = (int)(Math.random()*(unseenPer.size()));  

		// Picks a possible solution to suggest for the computer player
		Solution guess = new Solution(tempRoomCard, unseenPer.get(tempPersonCard), unseenWep.get(tempWeponCard));

		return guess;
	}


	/* Selects a room for a computer player to move to by picking a room at random from a list of rooms it has yet to eliminate as a possibility for the solution
	 * If no new rooms can be visited, it then selects a room to move to at random
	 * If no rooms are in range, it picks a random walkway to move to
	 * 
	 * Michael, Sihang 4/3/2023
	 */
	public BoardCell selectTarget(int steps) {
		// Variable initialization
		ArrayList<BoardCell> possibleTargets = new ArrayList<BoardCell>();
		ArrayList<BoardCell> targets = new ArrayList<BoardCell>();
		ArrayList<Card> cardList = new ArrayList<Card>();
		
		targets.addAll(Board.getInstance().getTargets());
		
		cardList.addAll(getDealtList());
		cardList.addAll(getSeenList());
		
		// Calculates all valid moves the player can make
		Board.getInstance().calcTargets(Board.getInstance().getCell(getRow(), getCol()), steps);
		
		// Checks each possible room it can move to, if it has not visited the room yet, move to the room
		for(BoardCell possibleRoom: Board.getInstance().getTargets()) {
			if(possibleRoom.isRoomCenter()) {
				Card newCard = new Card(Board.getInstance().getRoom(possibleRoom.getInitial()).getName(), CardType.ROOM);
				
				if(cardList.size() == 0) {
					return possibleRoom;
				}
				boolean found = false;
				
				// Checks all rooms for a unique room it has not seen yet
				for(Card seenCards: cardList) {
					if(newCard.equals(seenCards)) {
						 found = true;
						 break;
					}
				}
				
				// Skips current room if room is already known, else, visit the room
				if(found) {
					possibleTargets.add(possibleRoom);
					continue;
				} else {
					return possibleRoom;
				}
				
			}
		}

		if (possibleTargets.size() > 0) {
			return possibleTargets.get((int)(Math.random()*(possibleTargets.size())));
		} else {
			return targets.get((int)(Math.random()*(possibleTargets.size())));
		}

	}
}
