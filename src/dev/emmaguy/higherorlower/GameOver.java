package dev.emmaguy.higherorlower;

import android.content.Context;
import android.widget.TableRow;

public interface GameOver {

    boolean isScoreBeingCalculated();
    int getLeaderboardId();
    long getFinalScore();
    
    TableRow[] getScoreCountdownUi(Context context);
}
