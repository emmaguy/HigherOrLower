package dev.emmaguy.higherorlower;

public class SuddenDeathScore implements Score {

    private final int currentScore;
    
    public SuddenDeathScore(int currentScore) {
	this.currentScore = currentScore;
    }

    @Override
    public String getScore() {
	
	return "Score: " + currentScore;
    }
}
