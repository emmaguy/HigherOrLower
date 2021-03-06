package dev.emmaguy.higherorlower;

import dev.emmaguy.higherorlower.card.Card;

public interface HigherOrLowerGame {

    void higherGuessed();
    void sameGuessed();
    void lowerGuessed();
    
    void startGame();
    void resumeGame();
    void stopGame();
    
    void setOnCardChangedListener(OnCardChanged cardChanged);    
    void setOnScoreChangedListener(OnScoreChanged scoreChanged);
    
    public interface OnGameOver {
	public void onGameOver(GameOver gameOver);
    }
    
    public interface OnCardChanged {
	public void onCardChanged(Card currentCard);
    }
    
    public interface OnScoreChanged {
	public void onScoreChanged(Score score);
    }
}
