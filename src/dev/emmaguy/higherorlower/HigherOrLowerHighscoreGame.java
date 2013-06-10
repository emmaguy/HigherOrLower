package dev.emmaguy.higherorlower;

import dev.emmaguy.higherorlower.card.Card;
import dev.emmaguy.higherorlower.deck.Deck;

public class HigherOrLowerHighscoreGame implements HigherOrLowerGame {

    private final Deck deck;
    private final OnGameOver gameOverListener;
    
    private long currentScore = 0;
    
    private Card currentCard;
    private Card nextCard;
    private OnCardChanged cardChangedListener;
    private OnScoreChanged scoreChangedListener;
    
    public HigherOrLowerHighscoreGame(Deck deck, OnGameOver gameOverListener) {
	this.deck = deck;
	this.gameOverListener = gameOverListener;
    }
    
    public void setOnCardChangedListener(OnCardChanged cardChanged) {
	this.cardChangedListener = cardChanged;
    }
    
    public void setOnScoreChangedListener(OnScoreChanged scoreChanged) {
	this.scoreChangedListener = scoreChanged;
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
	    gameOverListener.onGameOver(currentScore, R.string.leaderboard_id_highscore);
	    return;
	}
	
	nextCard = deck.getNextCard();
	
	cardChangedListener.onCardChanged(currentCard);
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