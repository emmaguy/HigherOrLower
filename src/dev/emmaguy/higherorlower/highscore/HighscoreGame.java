package dev.emmaguy.higherorlower.highscore;

import android.os.Handler;
import dev.emmaguy.higherorlower.HigherOrLowerGame;
import dev.emmaguy.higherorlower.card.Card;
import dev.emmaguy.higherorlower.deck.Deck;
import dev.emmaguy.higherorlower.ui.MainActivity;
import dev.emmaguy.higherorlower.ui.OnAudioAction;

public class HighscoreGame implements HigherOrLowerGame {

    public static final int SCORE_INTERVAL = 100;
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
    private OnAudioAction audioActionListener;
    
    private final Runnable updateTimeRemainingTask = new Runnable() {
	   public void run() {       
	       millisecondsRemaining = deck.getMaxTimeToSolve() - (getTime() - startTime);
	       if(millisecondsRemaining <= 0){
		   millisecondsRemaining = 0;
		   scoreChangedListener.onScoreChanged(new HighscoreScore(currentScore, millisecondsRemaining));
		   gameOverListener.onGameOver(new HighscoreGameOver(currentScore, 0));
		   
		   stopGame();
		   return;
	       }
	       
	       scoreChangedListener.onScoreChanged(new HighscoreScore(currentScore, millisecondsRemaining));
	       handler.postDelayed(this, 1);
	   }
	};
    
    public HighscoreGame(Deck deck, OnGameOver gameOverListener, OnAudioAction audioAction) {
	this.deck = deck;
	this.gameOverListener = gameOverListener;
	this.audioActionListener = audioAction;
    }
    
    public void setOnCardChangedListener(OnCardChanged cardChanged) {
	this.cardChangedListener = cardChanged;
    }
    
    public void setOnScoreChangedListener(OnScoreChanged scoreChanged) {
	this.scoreChangedListener = scoreChanged;
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
	
	scoreChangedListener.onScoreChanged(new HighscoreScore(currentScore, millisecondsRemaining));
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
	    correctAnswer();
	}
	moveToNextCard();
    }

    private void correctAnswer() {
	currentScore += SCORE_INTERVAL;
	scoreChangedListener.onScoreChanged(new HighscoreScore(currentScore, millisecondsRemaining));
	audioActionListener.onPlaySound(MainActivity.CORRECT_ANSWER);
    }

    @Override
    public void sameGuessed() {
	if(nextCard.getCardNumber().getNumber() == currentCard.getCardNumber().getNumber()) {
	    correctAnswer();
	}
	moveToNextCard();
    }
    
    @Override
    public void lowerGuessed() {
	if(nextCard.getCardNumber().getNumber() < currentCard.getCardNumber().getNumber()) {
	    correctAnswer();
	}
	moveToNextCard();
    }
}