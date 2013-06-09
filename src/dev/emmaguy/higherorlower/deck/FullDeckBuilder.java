package dev.emmaguy.higherorlower.deck;

import java.util.ArrayList;
import java.util.List;

import dev.emmaguy.higherorlower.card.Card;
import dev.emmaguy.higherorlower.card.CardNumber;
import dev.emmaguy.higherorlower.card.Suit;

public class FullDeckBuilder implements DeckBuilder {

    public List<Card> build() {

	List<Card> cards = new ArrayList<Card>(52);

	for (Suit currentSuit : Suit.values()) {
	    for (CardNumber currentCardNumber : CardNumber.values()) {
		cards.add(new Card(currentSuit, currentCardNumber));
	    }
	}

	return cards;
    }
}
