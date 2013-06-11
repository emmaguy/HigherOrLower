package dev.emmaguy.higherorlower;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HighscoreScore implements Score {

    private final int currentScore;
    private final long millisecondsRemaining;

    public HighscoreScore(int currentScore, long millisecondsRemaining) {
	this.currentScore = currentScore;
	this.millisecondsRemaining = millisecondsRemaining;
    }

    @Override
    public String getScore() {

	Date timeRemaining = new Date(millisecondsRemaining);
	DateFormat formatter = new SimpleDateFormat("mm:ss:SSS", Locale.UK);
	
	StringBuilder sb = new StringBuilder();
	sb.append("Score: " + currentScore + "\n");
	sb.append("Time: " + formatter.format(timeRemaining));
	
	return sb.toString();
    }
}
