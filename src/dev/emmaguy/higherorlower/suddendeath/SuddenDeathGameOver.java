package dev.emmaguy.higherorlower.suddendeath;

import android.content.Context;
import android.graphics.Color;
import android.widget.TableRow;
import dev.emmaguy.higherorlower.GameOver;
import dev.emmaguy.higherorlower.R;
import dev.emmaguy.higherorlower.ui.ResultsRowBuilder;

public class SuddenDeathGameOver implements GameOver {

    private final Integer score;

    public SuddenDeathGameOver(int score) {
	this.score = score;
    }

    public boolean isScoreBeingCalculated() {
	return false;
    }
    
    public int getLeaderboardId(){
	return R.string.leaderboard_id_suddendeath;
    }

    @Override
    public TableRow[] getScoreCountdownUi(Context context) {
	ResultsRowBuilder rowBuilder = new ResultsRowBuilder(context);

	return new TableRow[] { rowBuilder.createRow("Score:", score.toString(), Color.BLACK) };
    }

    @Override
    public long getFinalScore() {
	return score;
    }
}