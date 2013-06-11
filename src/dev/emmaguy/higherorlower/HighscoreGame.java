package dev.emmaguy.higherorlower;

import android.os.Handler;
import dev.emmaguy.higherorlower.card.Card;
import dev.emmaguy.higherorlower.deck.Deck;

public class HighscoreGame implements HigherOrLowerGame {

    private final Deck deck;
    private OnGameOver gameOverListener;
    
    private int currentScore = 0;
    private long startTime = 0;
    private long elapsedTime = 0;
    private long millisecondsRemaining = 0;
    
    private final Handler handler = new Handler();
    private Card currentCard;
    private Card nextCard;
    private OnCardChanged cardChangedListener;
    private OnScoreChanged scoreChangedListener;
    private OnTimeRemainingChanged timeRemainingListener;
    
    private final Runnable updateTimeRemainingTask = new Runnable() {
	   public void run() {       
	       millisecondsRemaining = deck.getMaxTimeToSolve() - (getTime() - startTime);
	       if(millisecondsRemaining <= 0){
		   timeRemainingListener.onTimeRemainingChanged(0);
		   gameOverListener.onGameOver(new HighscoreGameOver(currentScore, 0));
		   
		   stopGame();
		   return;
	       }
	       
	       timeRemainingListener.onTimeRemainingChanged(millisecondsRemaining);
	       handler.postDelayed(this, 1);
	   }
	};
    
    public HighscoreGame(Deck deck, OnGameOver gameOverListener) {
	this.deck = deck;
	this.gameOverListener = gameOverListener;
    }
    
    public void setOnCardChangedListener(OnCardChanged cardChanged) {
	this.cardChangedListener = cardChanged;
    }
    
    public void setOnScoreChangedListener(OnScoreChanged scoreChanged) {
	this.scoreChangedListener = scoreChanged;
    }
    
    public void setOnTimeRemainingChangedListener(OnTimeRemainingChanged timeRemainingChanged) {
	this.timeRemainingListener = timeRemainingChanged;
    }
    
    private long getTime() {
	return System.currentTimeMillis();
    }
    
    public void startGame() {
	currentCard = deck.getNextCard();
	nextCard = deck.getNextCard();
	
	handler.removeCallbacks(updateTimeRemainingTask);
	handler.postDelayed(updateTimeRemainingTask, 1);
	startTime = getTime();
	elapsedTime = 0;
	
	scoreChangedListener.onScoreChanged(currentScore);
	cardChangedListener.onCardChanged(currentCard);
    }
    
    @Override
    public void stopGame() {
	elapsedTime = getTime() - startTime;
	handler.removeCallbacks(updateTimeRemainingTask);
    }
    
    @Override
    public void resumeGame() {
	startTime = getTime() - elapsedTime;
	handler.removeCallbacks(updateTimeRemainingTask);
	handler.postDelayed(updateTimeRemainingTask, 1);
    }
    
    private void moveToNextCard() {
	currentCard = nextCard;
	
	if(!deck.hasCardsRemaining()){
	    gameOverListener.onGameOver(new HighscoreGameOver(currentScore, millisecondsRemaining));
	    stopGame();
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