package dev.emmaguy.higherorlower;

import android.os.Handler;
import android.os.SystemClock;
import dev.emmaguy.higherorlower.card.Card;
import dev.emmaguy.higherorlower.deck.Deck;

public class HigherOrLowerHighscoreGame implements HigherOrLowerGame {

    private final Deck deck;
    private OnGameOver gameOverListener;
    
    private int currentScore = 0;
    private long startTime = 0;
    private long millisecondsRemaining = 0;
    
    private final Handler handler = new Handler();
    private Card currentCard;
    private Card nextCard;
    private OnCardChanged cardChangedListener;
    private OnScoreChanged scoreChangedListener;
    private OnTimeRemainingChanged timeRemainingListener;
    
    private final Runnable updateTimeRemainingTask = new Runnable() {
	   public void run() {
	       final long start = startTime;
	       final int maxTime = 2000;//120000;
	       
	       millisecondsRemaining = maxTime - (SystemClock.uptimeMillis() - start);
	       if(millisecondsRemaining <= 0){
		   timeRemainingListener.onTimeRemainingChanged(0);
		   gameOverListener.onGameOver(currentScore, 0, R.string.leaderboard_id_highscore);
		   handler.removeCallbacks(updateTimeRemainingTask);
		   return;
	       }
	       
	       timeRemainingListener.onTimeRemainingChanged(millisecondsRemaining);
	       handler.postDelayed(this, 1);
	   }
	};
    
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
    
    public void setOnTimeRemainingChangedListener(OnTimeRemainingChanged timeRemainingChanged) {
	this.timeRemainingListener = timeRemainingChanged;
    }
    
    public void startGame() {
	currentCard = deck.getNextCard();
	nextCard = deck.getNextCard();
	
	startTime = SystemClock.uptimeMillis();
        handler.removeCallbacks(updateTimeRemainingTask);
        handler.postDelayed(updateTimeRemainingTask, 100);
	
	scoreChangedListener.onScoreChanged(currentScore);
	cardChangedListener.onCardChanged(currentCard);
    }
    
    private void moveToNextCard() {
	currentCard = nextCard;
	
	if(!deck.hasCardsRemaining()){
	    gameOverListener.onGameOver(currentScore, millisecondsRemaining, R.string.leaderboard_id_highscore);
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

    @Override
    public void stopGame() {
	handler.removeCallbacks(updateTimeRemainingTask);
	cardChangedListener = null;
	scoreChangedListener = null;
	timeRemainingListener = null;
	gameOverListener = null;
    }
}