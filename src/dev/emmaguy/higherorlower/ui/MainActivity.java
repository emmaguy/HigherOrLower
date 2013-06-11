package dev.emmaguy.higherorlower.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import dev.emmaguy.higherorlower.HigherOrLowerGame.OnGameOver;
import dev.emmaguy.higherorlower.HigherOrLowerHighscoreGame;
import dev.emmaguy.higherorlower.R;
import dev.emmaguy.higherorlower.deck.Deck;
import dev.emmaguy.higherorlower.deck.FullDeckBuilder;
import dev.emmaguy.higherorlower.ui.PostGameFragment.OnLeaderboardAPIAction;
import dev.emmaguy.higherorlower.ui.SinglePlayerFragment.OnHighscoreModeButtonClicked;
import dev.emmaguy.higherorlower.ui.SplashScreenFragment.OnSinglePlayerButtonClicked;

public class MainActivity extends FragmentActivity implements OnGameOver, OnHighscoreModeButtonClicked,
	OnSinglePlayerButtonClicked, OnLeaderboardAPIAction {

    //static final int SHOW_LEADERBOARD = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_main);
    }

    @Override
    public void onGameOver(int score, long millisecondsRemaining, int leaderboardId) {
	PostGameFragment postGameFragment = new PostGameFragment();
	postGameFragment.setArguments(score, millisecondsRemaining, leaderboardId, false);

	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, postGameFragment);
	transaction.addToBackStack(null);
	transaction.commit();
    }

    @Override
    public void onSinglePlayerButtonClicked() {
	SinglePlayerFragment singlePlayerFragment = new SinglePlayerFragment();

	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, singlePlayerFragment);
	transaction.addToBackStack(null);
	transaction.commit();
    }

    @Override
    public void onHighscoreModeButtonClicked() {
	HigherOrLowerFragment higherOrLowerGameFragment = new HigherOrLowerFragment();
	higherOrLowerGameFragment.setArguments(new HigherOrLowerHighscoreGame(new Deck(new FullDeckBuilder().build()),
		(OnGameOver) this));

	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, higherOrLowerGameFragment);
	transaction.addToBackStack(null);
	transaction.commit();
    }

    @Override
    public void onSignInClicked() {
	
    }

    @Override
    public void onSignOutClicked() {

    }

    @Override
    public void onPostScore(float score, int leaderboardId) {
	//String leaderboardString = getResources().getString(leaderboardId);
	//mHelper.getGamesClient().submitScore(leaderboardString, score);
	//startActivityForResult(mHelper.getGamesClient().getLeaderboardIntent(leaderboardString), SHOW_LEADERBOARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	if (requestCode == SHOW_LEADERBOARD) {
//	    SplashScreenFragment fragment = new SplashScreenFragment();
//
//	    FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
//	    transaction.replace(R.id.fragment_container, fragment);
//	    transaction.commitAllowingStateLoss();
//	}
    }
}