package dev.emmaguy.higherorlower;

import dev.emmaguy.higherorlower.cards.Card;

public interface HigherOrLowerGame {

    void higherGuessed();
    void sameGuessed();
    void lowerGuessed();
    void startGame();
    
    void setOnCardChangedListener(OnCardChanged cardChanged);    
    void setOnScoreChangedListener(OnScoreChanged scoreChanged);
    
    public interface OnGameOver {
	public void onGameOver(long score, int leaderboardId);
    }
    
    public interface OnCardChanged {
	public void onCardChanged(Card currentCard);
    }
    
    public interface OnScoreChanged {
	public void onScoreChanged(long score);
    }
}
