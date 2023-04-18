/* Computer player is a subset of the abstract Player class
 * 	Will contain logic for the computer players to play the game against the human players
 * 
 * Authors: Sihang Wang, Michael Basey
 */
package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class ComputerPlayer extends Player {
	
	private static Board board;

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

		ArrayList<Card> mergeCards = new ArrayList<Card>();
		mergeCards.addAll(this.getDealtList());
		mergeCards.addAll(this.getSeenList());

		Room currentRoom = Board.getInstance().getRoom(Board.getInstance().getCell(getRow(),getCol()).getInitial());

		Card tempRoomCard = new Card(currentRoom.getName(), CardType.ROOM);


		// If the card is already known by the computer, do not use
		for(Card card : Board.getAllWeapon()) {
			if (!mergeCards.contains(card)) {
				unseenWep.add(card);
			} 
		}

		// If the card is already known by the computer, do not use
		for(Card card : Board.getAllPerson()) {
			if (!mergeCards.contains(card)) {
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
	@Override
	public BoardCell selectTarget(int steps) {
		board = Board.getInstance();
		
		// Variable initialization
		ArrayList<BoardCell> randTargets = new ArrayList<BoardCell>();
		ArrayList<BoardCell> targets = new ArrayList<BoardCell>();

		ArrayList<Card> cardList = new ArrayList<Card>();

		cardList.addAll(getDealtList());
		cardList.addAll(getSeenList());

		ArrayList<Card> roomCards = new ArrayList<Card>();

		for(Card testCard: cardList) {
			if(testCard.getType() == CardType.ROOM) {
				roomCards.add(testCard);
			}
		}

		// Calculates all valid moves the player can make
		Board.getInstance().calcTargets(Board.getInstance().getCell(getRow(), getCol()), steps);

		targets = Board.getInstance().getTargets();


		// Checks each possible room it can move to, if it has not visited the room yet, move to the room
		for(BoardCell possibleTarget: targets) {
			
			if(possibleTarget.isRoomCenter()) {	
				boolean found = false;

				if(cardList.size() == 0) {
					return possibleTarget;
				}

				// Checks all rooms for a unique room it has not seen yet
				for(Card seenCards: cardList) {
					if(seenCards.getName().equals(board.getRoom(possibleTarget.getInitial()).getName())) {
						found = true;
						break;
					}
				}

				// Skips current room if room is already known, else, visit the room
				if(found) {
					randTargets.add(possibleTarget);
				} else {
					return possibleTarget;
				}
			}
		}
		
		if (!randTargets.isEmpty()) {
			return randTargets.get((int)(Math.random()*(randTargets.size())));
		} else {
			return targets.get((int)(Math.random()*(targets.size())));
		}
		

	}
}
