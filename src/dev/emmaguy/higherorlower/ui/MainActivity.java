package dev.emmaguy.higherorlower.ui;

import java.io.PrintWriter;
import java.io.StringWriter;

import io.openkit.OKLoginActivity;
import io.openkit.OKScore;
import io.openkit.OKUser;
import io.openkit.OpenKit;
import io.openkit.leaderboards.OKLeaderboardsActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;
import dev.emmaguy.higherorlower.GameOver;
import dev.emmaguy.higherorlower.OnLeaderboardAPIAction;
import dev.emmaguy.higherorlower.HigherOrLowerGame.OnGameOver;
import dev.emmaguy.higherorlower.R;
import dev.emmaguy.higherorlower.deck.Deck;
import dev.emmaguy.higherorlower.deck.FullDeckBuilder;
import dev.emmaguy.higherorlower.highscore.HighscoreGame;
import dev.emmaguy.higherorlower.suddendeath.SuddenDeathGame;
import dev.emmaguy.higherorlower.ui.SinglePlayerFragment.OnSinglePlayerModeChosen;
import dev.emmaguy.higherorlower.ui.SplashScreenFragment.OnPlayersChosen;

public class MainActivity extends FragmentActivity implements OnGameOver, OnSinglePlayerModeChosen, OnPlayersChosen,
	OnLeaderboardAPIAction {

    protected static final int SHOW_SPLASH_SCREEN_AFTER_ACTIVITY = 1;
    protected static final int SUBMIT_SCORE_AFTER_LOGIN_LEADERBOARD = 2;
    private static final int TWO_MINUTES_MILLISECONDS = 120000;
    private long score;
    private int leaderboardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	OpenKit.initialize(this, getResources().getString(R.string.open_kit_app_id));
	setContentView(R.layout.activity_main);

	showSplashScreen();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);

	if (requestCode == SHOW_SPLASH_SCREEN_AFTER_ACTIVITY) {
	    showSplashScreen();
	} else if (requestCode == SUBMIT_SCORE_AFTER_LOGIN_LEADERBOARD) {
	    submitScore(score, leaderboardId);
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
	higherOrLowerGameFragment.setArguments(new HighscoreGame(new Deck(new FullDeckBuilder().build(),
		TWO_MINUTES_MILLISECONDS), (OnGameOver) this));

	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, higherOrLowerGameFragment);
	transaction.addToBackStack(null);
	transaction.commit();
    }

    @Override
    public void onSuddenDeathModeButtonClicked() {
	HigherOrLowerFragment higherOrLowerGameFragment = new HigherOrLowerFragment();
	higherOrLowerGameFragment.setArguments(new SuddenDeathGame(new Deck(new FullDeckBuilder().build(),
		TWO_MINUTES_MILLISECONDS), (OnGameOver) this));

	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, higherOrLowerGameFragment);
	transaction.addToBackStack(null);
	transaction.commit();
    }

    @Override
    public void onSignInClicked() {
	Intent launchOKLogin = new Intent(getApplicationContext(), OKLoginActivity.class);
	startActivityForResult(launchOKLogin, MainActivity.SHOW_SPLASH_SCREEN_AFTER_ACTIVITY);
    }

    @Override
    public void onSignOutClicked() {
	OKUser.logoutCurrentUser(getApplicationContext());
	showSplashScreen();
    }

    @Override
    public void onSubmitScore(long score, int leaderboardId) {
	OKUser user = OpenKit.getCurrentUser();
	if (user != null) {
	    submitScore(score, leaderboardId);
	} else {
	    Intent launchOKLogin = new Intent(getApplicationContext(), OKLoginActivity.class);
	    this.score = score;
	    this.leaderboardId = leaderboardId;
	    startActivityForResult(launchOKLogin, MainActivity.SUBMIT_SCORE_AFTER_LOGIN_LEADERBOARD);
	}
    }
    
    private void viewLeaderboard() {
	Intent launchOKLeaderboard = new Intent(getApplicationContext(), OKLeaderboardsActivity.class);
	startActivityForResult(launchOKLeaderboard, MainActivity.SHOW_SPLASH_SCREEN_AFTER_ACTIVITY);
    }

    private void submitScore(long total, int leaderboardId) {
	OKScore score = new OKScore();
	score.setScoreValue(total);
	score.setOKLeaderboardID(leaderboardId);
	score.submitScore(new OKScore.ScoreRequestResponseHandler() {
	    @Override
	    public void onSuccess() {
		Toast.makeText(getApplicationContext(), "Score submission successful", Toast.LENGTH_SHORT).show();
		viewLeaderboard();
	    }

	    @Override
	    public void onFailure(Throwable error) {
		Toast.makeText(getApplicationContext(), "Score submission failed", Toast.LENGTH_SHORT).show();
		StringWriter sw = new StringWriter();
		error.printStackTrace(new PrintWriter(sw));
		Log.e("OpenKit", "Score submission failed: " + error.getMessage() + "\n" + sw.toString());
	    }
	});
    }

    @Override
    public void viewLeaderboards() {
	viewLeaderboard();
    }
}