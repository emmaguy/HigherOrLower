package dev.emmaguy.higherorlower.card;

import java.util.Locale;

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
    
    private Suit getSuit(){
	return suit;
    }

    public String getResourceName() {
	String cardNumber = getCardNumber().toString().toLowerCase(Locale.UK);
	String cardSuit = getSuit().toString().toLowerCase(Locale.UK);
	return cardNumber + "_of_" + cardSuit;
    }
}
