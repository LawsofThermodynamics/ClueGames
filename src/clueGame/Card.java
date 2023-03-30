/*
 * 
 */
package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
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
		return false;
	}
}
