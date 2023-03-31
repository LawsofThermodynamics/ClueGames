package clueGame;

public class Solution {
	private Card room;
	private Card person;
	private Card weapon;

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
