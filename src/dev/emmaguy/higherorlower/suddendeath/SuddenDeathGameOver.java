package dev.emmaguy.higherorlower.suddendeath;

import dev.emmaguy.higherorlower.GameOver;
import dev.emmaguy.higherorlower.R;

public class SuddenDeathGameOver implements GameOver {

    private final int score;

    public SuddenDeathGameOver(int score) {
	this.score = score;
    }

    public boolean isScoreCalculationFinished() {
	return true;
    }
    
    public int getLeaderboardId(){
	return R.string.leaderboard_id_suddendeath;
    }

    @Override
    public String getScore() {

	return "Final score: " + score;
    }

    @Override
    public long getFinalScore() {
	return score;
    }
}