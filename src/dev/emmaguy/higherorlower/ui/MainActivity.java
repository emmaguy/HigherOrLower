package dev.emmaguy.higherorlower.ui;

import io.openkit.OpenKit;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import dev.emmaguy.higherorlower.GameOver;
import dev.emmaguy.higherorlower.R;
import dev.emmaguy.higherorlower.HigherOrLowerGame.OnGameOver;
import dev.emmaguy.higherorlower.deck.Deck;
import dev.emmaguy.higherorlower.deck.FullDeckBuilder;
import dev.emmaguy.higherorlower.highscore.HighscoreGame;
import dev.emmaguy.higherorlower.suddendeath.SuddenDeathGame;
import dev.emmaguy.higherorlower.ui.SinglePlayerFragment.OnSinglePlayerModeChosen;
import dev.emmaguy.higherorlower.ui.SplashScreenFragment.OnPlayersChosen;

public class MainActivity extends FragmentActivity implements OnGameOver, OnSinglePlayerModeChosen, OnPlayersChosen {

    protected static final int SHOW_SPLASH_SCREEN_AFTER_LEADERBOARD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	OpenKit.initialize(this, getResources().getString(R.string.open_kit_app_id)); 
	setContentView(R.layout.activity_main);

	showSplashScreen();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (requestCode == SHOW_SPLASH_SCREEN_AFTER_LEADERBOARD) {
	    showSplashScreen();
	}
    }
    
    private void showSplashScreen() {
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