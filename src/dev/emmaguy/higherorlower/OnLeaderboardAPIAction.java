package dev.emmaguy.higherorlower;

public interface OnLeaderboardAPIAction {
    public void onSubmitScore(long total, int leaderboardId);
    public void onSignInClicked();
    public void onSignOutClicked();
    public void viewLeaderboards();
}