package dev.emmaguy.higherorlower.deck;

import java.util.List;
import java.util.Random;

import dev.emmaguy.higherorlower.card.Card;

public class Deck {
    private final List<Card> cards;
    private final Random random = new Random();
    
    public Deck(List<Card> cards) {
	this.cards = cards;
    }

    public boolean hasCardsRemaining(){ 
	return cards.size() > 0;
    }
    
    public Card getNextCard() {
	int index = random.nextInt(cards.size());
	Card c = cards.get(index);
	cards.remove(index);
	return c;
    }
}
