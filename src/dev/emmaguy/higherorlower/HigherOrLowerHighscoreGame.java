package dev.emmaguy.higherorlower;

import dev.emmaguy.higherorlower.cards.Card;
import dev.emmaguy.higherorlower.cards.Deck;

public class HigherOrLowerHighscoreGame implements HigherOrLowerGame {

    private final Deck deck;
    private Card currentCard;
    private Card nextCard;
    
    private int currentScore = 0;
    
    private OnCardChanged cardChangedListener;
    private OnScoreChanged scoreChangedListener;
    private OnGameOver gameOverListener;
    
    public HigherOrLowerHighscoreGame(Deck deck, OnCardChanged cardChangedListener, OnScoreChanged scoreChangedListener, OnGameOver gameOverListener) {
	this.deck = deck;
	this.cardChangedListener = cardChangedListener;
	this.scoreChangedListener = scoreChangedListener;
	this.gameOverListener = gameOverListener;
    }
    
    public void startGame() {
	currentCard = deck.getNextCard();
	nextCard = deck.getNextCard();
	
	scoreChangedListener.onScoreChanged(currentScore);
	cardChangedListener.onCardChanged(currentCard);
    }
    
    private void moveToNextCard() {
	currentCard = nextCard;
	
	if(!deck.hasCardsRemaining()){
	    gameOverListener.onGameOver();
	    return;
	}
	
	nextCard = deck.getNextCard();
	
	cardChangedListener.onCardChanged(currentCard);
    }
    
    public interface OnGameOver {
	public void onGameOver();
    }
    
    public interface OnCardChanged {
	public void onCardChanged(Card currentCard);
    }
    
    public interface OnScoreChanged {
	public void onScoreChanged(int score);
    }

    @Override
    public void higherGuessed() {
	if(nextCard.getCardNumber().getNumber() > currentCard.getCardNumber().getNumber()) {
	    currentScore++;
	    scoreChangedListener.onScoreChanged(currentScore);
	}
	moveToNextCard();
    }

    @Override
    public void sameGuessed() {
	if(nextCard.getCardNumber().getNumber() == currentCard.getCardNumber().getNumber()) {
	    currentScore++;
	    scoreChangedListener.onScoreChanged(currentScore);
	}
	moveToNextCard();
    }
    
    @Override
    public void lowerGuessed() {
	if(nextCard.getCardNumber().getNumber() < currentCard.getCardNumber().getNumber()) {
	    currentScore++;
	    scoreChangedListener.onScoreChanged(currentScore);
	}
	moveToNextCard();
    }
}
