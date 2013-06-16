package dev.emmaguy.higherorlower.suddendeath;

import dev.emmaguy.higherorlower.HigherOrLowerGame;
import dev.emmaguy.higherorlower.card.Card;
import dev.emmaguy.higherorlower.deck.Deck;
import dev.emmaguy.higherorlower.ui.MainActivity;
import dev.emmaguy.higherorlower.ui.OnAudioAction;

public class SuddenDeathGame implements HigherOrLowerGame {

    private final Deck deck;    
    
    private int currentScore = 0;

    private Card currentCard;
    private Card nextCard;
    private OnCardChanged cardChangedListener;
    private OnScoreChanged scoreChangedListener;
    private OnGameOver gameOverListener;
    private OnAudioAction audioActionListener;

    public SuddenDeathGame(Deck deck, OnGameOver gameOverListener, OnAudioAction onAudioAction) {
	this.deck = deck;
	this.gameOverListener = gameOverListener;
	this.audioActionListener = onAudioAction;
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
	
	scoreChangedListener.onScoreChanged(new SuddenDeathScore(currentScore));
	cardChangedListener.onCardChanged(currentCard);
    }
    
    private void moveToNextCard() {
	currentCard = nextCard;
	
	if(!deck.hasCardsRemaining()){
	    gameOver();
	    return;
	}
	
	nextCard = deck.getNextCard();
	cardChangedListener.onCardChanged(currentCard);
    }

    private void gameOver() {
	gameOverListener.onGameOver(new SuddenDeathGameOver(currentScore));
	stopGame();
    }
    
    @Override
    public void higherGuessed() {
	if(nextCard.getCardNumber().getNumber() > currentCard.getCardNumber().getNumber()) {
	    correctAnswer();
	} else { 
	    gameOver(); 
	}
    }

    @Override
    public void sameGuessed() {
	if(nextCard.getCardNumber().getNumber() == currentCard.getCardNumber().getNumber()) {
	    correctAnswer();
	} else { 
	    gameOver(); 
	}
    }

    private void correctAnswer() {
	currentScore += 100;
	audioActionListener.onPlaySound(MainActivity.CORRECT_ANSWER);
	scoreChangedListener.onScoreChanged(new SuddenDeathScore(currentScore));
	moveToNextCard();
    }
    
    @Override
    public void lowerGuessed() {
	if(nextCard.getCardNumber().getNumber() < currentCard.getCardNumber().getNumber()) {
	    correctAnswer();
	} else { 
	    gameOver(); 
	}
    }

    @Override
    public void resumeGame() {
	
    }

    @Override
    public void stopGame() {

    }
}