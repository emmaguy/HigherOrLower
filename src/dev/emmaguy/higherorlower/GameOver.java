package dev.emmaguy.higherorlower;

public interface GameOver {

    boolean isScoreCalculationFinished();
    int getLeaderboardId();
    long getFinalScore();
    String getScore();
}
