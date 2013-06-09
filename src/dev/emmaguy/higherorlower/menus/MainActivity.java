package dev.emmaguy.higherorlower.menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.google.example.games.basegameutils.BaseGameActivity;

import dev.emmaguy.higherorlower.HigherOrLowerFragment;
import dev.emmaguy.higherorlower.HigherOrLowerGame.OnGameOver;
import dev.emmaguy.higherorlower.HigherOrLowerHighscoreGame;
import dev.emmaguy.higherorlower.R;
import dev.emmaguy.higherorlower.cards.Deck;
import dev.emmaguy.higherorlower.cards.FullDeckBuilder;
import dev.emmaguy.higherorlower.menus.PostGameFragment.OnPlayAPIAction;
import dev.emmaguy.higherorlower.menus.SinglePlayerFragment.OnHighscoreModeButtonClicked;
import dev.emmaguy.higherorlower.menus.SplashScreenFragment.OnSinglePlayerButtonClicked;

public class MainActivity extends BaseGameActivity implements OnGameOver, OnHighscoreModeButtonClicked,
	OnSinglePlayerButtonClicked, OnPlayAPIAction {

    static final int SHOW_LEADERBOARD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_main);
    }

    @Override
    public void onSignInClicked() {
	beginUserInitiatedSignIn();
    }

    @Override
    public void onSignOutClicked() {
	signOut();
    }

    @Override
    public void onPostScore(long score, int leaderboardId) {
	String leaderboardString = getResources().getString(leaderboardId);

	mHelper.getGamesClient().submitScore(leaderboardString, score);
	startActivityForResult(mHelper.getGamesClient().getLeaderboardIntent(leaderboardString), SHOW_LEADERBOARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (requestCode == SHOW_LEADERBOARD) {
	    SplashScreenFragment fragment = new SplashScreenFragment();

	    FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	    transaction.replace(R.id.fragment_container, fragment);
	    transaction.commitAllowingStateLoss();
	}
    }

    @Override
    public void onSignInSucceeded() {
	PostGameFragment fragment = (PostGameFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
	if (fragment != null)
	    fragment.onSignInSucceeded();
    }

    @Override
    public void onSignInFailed() {
	PostGameFragment fragment = (PostGameFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
	if (fragment != null)
	    fragment.onSignInFailed();
    }

    @Override
    public void onGameOver(long score, int leaderboardId) {
	PostGameFragment postGameFragment = new PostGameFragment();
	postGameFragment.setArguments(score, leaderboardId, mHelper.getGamesClient().isConnected());

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