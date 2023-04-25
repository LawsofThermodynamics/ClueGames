/* Solution class stores the set of cards the players are trying to guess during play 
 * 	Is dealt from the full list of cards before any are dealt to players 
 * 	
 * Authors: Sihang Wang, Michael Basey
 * */
package clueGame;

public class Solution {
	private Card room; // Stores the room card players must deduce from playing
	private Card person; // Stores the person card players must deduce from playing
	private Card weapon; // Stores the weapon card players must deduce from playing

	// Default constructor
	public Solution() {
		super();
	}

	// Parametric constructor
	public Solution(Card room, Card person, Card weapon) {
		super();
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}

	
	// Equals command for evaluation
	public boolean equals(Solution target) {
		if(this.room.equals(target.room) && this.person.equals(target.person) && this.weapon.equals(target.weapon)) {
			return true;
		}
		return false;
	}
	
	public void modify(Card room, Card person, Card weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}
	
	// Getters (setters excluded as cards should never be overwritten once picked)
	public Card getRoom() {
		return room;
	}

	public Card getPerson() {
		return person;
	}

	public Card getWeapon() {
		return weapon;
	}

	@Override
	public String toString() {
		return "Solution \nroom=" + room + "\nperson=" + person + "\nweapon=" + weapon;
	}
	
	
}
