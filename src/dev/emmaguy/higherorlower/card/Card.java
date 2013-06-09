package dev.emmaguy.higherorlower.card;

public class Card {
    private final Suit suit;
    private final CardNumber number;
    
    public Card(Suit suit, CardNumber number) {
	this.suit = suit;
	this.number = number;
    }

    public CardNumber getCardNumber() {
	return number;
    }
    
    public Suit getSuit(){
	return suit;
    }
    
}
