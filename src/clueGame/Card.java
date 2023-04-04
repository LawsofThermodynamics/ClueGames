/* Card class defines the objects that players can be dealt or can be shown throughout the game
 * 	Card includes a name and its card type ie. Room, player or weapon
 * 	
 * Authors: Sihang Wang, Michael Basey
 * */
package clueGame;

public class Card {
	private String cardName; // Stores the name of card that is being referenced
	private CardType type; // Stores the type of card that is being referenced
	
	// Default Constructor
	public Card(){
		super();
	}
	
	// Parametric Constructor
	public Card(String cardName, CardType type){
		super();
		this.cardName = cardName;
		this.type = type ;
	}
	
	public boolean equals(Card target) {
		if(this.cardName.equals(target.cardName) && this.type.equals(target.type)) {
			return true;
		}
		return false;
	}
	

	public CardType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", type=" + type + "]";
	}
	
	
}
