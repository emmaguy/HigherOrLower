package dev.emmaguy.higherorlower.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import dev.emmaguy.higherorlower.HigherOrLowerGame.OnGameOver;
import dev.emmaguy.higherorlower.HigherOrLowerHighscoreGame;
import dev.emmaguy.higherorlower.R;
import dev.emmaguy.higherorlower.deck.Deck;
import dev.emmaguy.higherorlower.deck.FullDeckBuilder;
import dev.emmaguy.higherorlower.ui.SinglePlayerFragment.OnHighscoreModeButtonClicked;
import dev.emmaguy.higherorlower.ui.SplashScreenFragment.OnSinglePlayerButtonClicked;

public class MainActivity extends FragmentActivity implements OnGameOver, OnHighscoreModeButtonClicked,
	OnSinglePlayerButtonClicked {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_main);
    }

    @Override
    public void onGameOver(int score, long millisecondsRemaining, int leaderboardId) {
	ResultsFragment postGameFragment = new ResultsFragment();
	postGameFragment.setArguments(score, millisecondsRemaining, leaderboardId);

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
}