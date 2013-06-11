package dev.emmaguy.higherorlower;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HighscoreGameOver implements GameOver {

    private final DateFormat formatter = new SimpleDateFormat("mm:ss:SSS", Locale.UK);
    private final int score;
    
    private float total = 0;
    private long millisecondsRemaining;

    public HighscoreGameOver(int score, long millisecondsRemaining) {
	this.score = score;
	this.total = score;
	this.millisecondsRemaining = millisecondsRemaining;
    }

    public boolean isScoreCalculationFinished() {
	return millisecondsRemaining <= 0;
    }
    
    public int getLeaderboardId(){
	return R.string.leaderboard_id_highscore;
    }

    @Override
    public String getScore() {

	if(!isScoreCalculationFinished()) {
	    millisecondsRemaining -= 500;
	    total += 0.03f;
	} else { 
	    millisecondsRemaining = 0; 
	}
	
	Date timeRemaining = new Date(millisecondsRemaining);

	StringBuilder results = new StringBuilder();
	results.append("Score: " + score + "\n");
	results.append("Time: " + formatter.format(timeRemaining) + "\n");
	results.append("\n");
	results.append("Total: " + String.format("%.2f", total));

	return results.toString();
    }
}