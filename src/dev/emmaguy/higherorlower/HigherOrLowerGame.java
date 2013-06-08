package dev.emmaguy.higherorlower;

import dev.emmaguy.higherorlower.cards.Card;

public interface HigherOrLowerGame {

    void higherGuessed();
    void sameGuessed();
    void lowerGuessed();
    void startGame();
    
    public interface OnGameOver {
	public void onGameOver();
    }
    
    public interface OnCardChanged {
	public void onCardChanged(Card currentCard);
    }
    
    public interface OnScoreChanged {
	public void onScoreChanged(int score);
    }
}
