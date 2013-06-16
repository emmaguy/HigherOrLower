package dev.emmaguy.higherorlower.highscore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.widget.TableRow;
import dev.emmaguy.higherorlower.GameOver;
import dev.emmaguy.higherorlower.R;
import dev.emmaguy.higherorlower.ui.ResultsRowBuilder;

public class HighscoreGameOver implements GameOver {

    private static final int MILLISECONDS_INTERVAL = 3000;
    private static final long SCORE_PER_MILLISECOND_INTERVAL = 3;

    private final DateFormat formatter = new SimpleDateFormat("mm:ss", Locale.UK);
    private final int points;
    private final long millisecondsRemaining;

    private Integer currentPoints;
    private long currentMillisecondsRemaining;

    private Long score = (long) 0;

    public HighscoreGameOver(int points, long millisecondsRemaining) {
	this.points = points;
	this.millisecondsRemaining = millisecondsRemaining;

	this.currentPoints = points;
	this.currentMillisecondsRemaining = millisecondsRemaining;
    }

    public int getLeaderboardId() {
	return R.string.leaderboard_id_highscore;
    }

    public long getFinalScore() {
	return points + (millisecondsRemaining / MILLISECONDS_INTERVAL) * SCORE_PER_MILLISECOND_INTERVAL;
    }

    @Override
    public TableRow[] getScoreCountdownUi(Context context) {

	if (isCountingDownPoints()) {
	    currentPoints -= HighscoreGame.SCORE_INTERVAL;
	    score += HighscoreGame.SCORE_INTERVAL;
	} else {
	    if (isCountingDownTime()) {

		long difference = currentMillisecondsRemaining - MILLISECONDS_INTERVAL;
		if (difference < 0) {
		    score += currentMillisecondsRemaining / MILLISECONDS_INTERVAL * SCORE_PER_MILLISECOND_INTERVAL;
		    currentMillisecondsRemaining = 0;
		} else {
		    currentMillisecondsRemaining -= MILLISECONDS_INTERVAL;
		    score += SCORE_PER_MILLISECOND_INTERVAL;
		}
	    }
	}

	ResultsRowBuilder rowBuilder = new ResultsRowBuilder(context);

	TableRow pointsRow = rowBuilder.createRow("Points:", currentPoints.toString(), isCountingDownPoints() ? Color.RED : Color.BLACK);
	TableRow timeRow = rowBuilder.createRow("Time:", formatter.format(new Date(currentMillisecondsRemaining)), isCountingDownTime() && !isCountingDownPoints() ? Color.RED : Color.BLACK);
	TableRow blankRow = rowBuilder.createRow("", "", Color.BLACK);
	TableRow scoreRow = rowBuilder.createRow("Score:", score.toString(), isScoreBeingCalculated() ? Color.RED : Color.BLACK);

	return new TableRow[] { pointsRow, timeRow, blankRow, scoreRow };
    }

    private boolean isCountingDownPoints() {
	return currentPoints > 0;
    }

    private boolean isCountingDownTime() {
	return currentMillisecondsRemaining > 0;
    }

    public boolean isScoreBeingCalculated() {
	return isCountingDownPoints() || isCountingDownTime();
    }
}