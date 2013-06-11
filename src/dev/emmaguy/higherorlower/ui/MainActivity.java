package dev.emmaguy.higherorlower.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import dev.emmaguy.higherorlower.GameOver;
import dev.emmaguy.higherorlower.HigherOrLowerGame.OnGameOver;
import dev.emmaguy.higherorlower.HighscoreGame;
import dev.emmaguy.higherorlower.R;
import dev.emmaguy.higherorlower.SuddenDeathGame;
import dev.emmaguy.higherorlower.deck.Deck;
import dev.emmaguy.higherorlower.deck.FullDeckBuilder;
import dev.emmaguy.higherorlower.ui.SinglePlayerFragment.OnSinglePlayerModeChosen;
import dev.emmaguy.higherorlower.ui.SplashScreenFragment.OnPlayersChosen;

public class MainActivity extends FragmentActivity implements OnGameOver, OnSinglePlayerModeChosen, OnPlayersChosen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_main);

	SplashScreenFragment fragment = new SplashScreenFragment();
	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, fragment);
	transaction.commit();
    }

    @Override
    public void onGameOver(GameOver gameOver) {
	ResultsFragment resultsFragment = new ResultsFragment();
	resultsFragment.setArguments(gameOver);

	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, resultsFragment);
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
	higherOrLowerGameFragment.setArguments(new HighscoreGame(new Deck(new FullDeckBuilder().build(), 120000), // 2 minutes
		(OnGameOver) this));

	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, higherOrLowerGameFragment);
	transaction.addToBackStack(null);
	transaction.commit();
    }

    @Override
    public void onSuddenDeathModeButtonClicked() {
	HigherOrLowerFragment higherOrLowerGameFragment = new HigherOrLowerFragment();
	higherOrLowerGameFragment.setArguments(new SuddenDeathGame(new Deck(new FullDeckBuilder().build(), 120000), // 2 minutes
		(OnGameOver) this));

	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, higherOrLowerGameFragment);
	transaction.addToBackStack(null);
	transaction.commit();	
    }
}