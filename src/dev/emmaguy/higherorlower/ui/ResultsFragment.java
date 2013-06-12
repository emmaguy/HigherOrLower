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
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import dev.emmaguy.higherorlower.GameOver;
import dev.emmaguy.higherorlower.R;

public class ResultsFragment extends Fragment implements View.OnClickListener {

    private final Handler handler = new Handler();
    private GameOver gameOver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_results, null);
	v.findViewById(R.id.button_post_score).setOnClickListener(this);
	return v;
    }

    public interface OnLeaderboardAPIAction {
	public void onSignInClicked();
	public void onSignOutClicked();
	public void onPostScore(float total, int leaderboardId);
    }

    public void setArguments(GameOver gameOver) {
	this.gameOver = gameOver;
    }

    private Runnable updateResultTask = new Runnable() {
	public void run() {
	    final TextView resultsView = (TextView) getView().findViewById(R.id.textview_results);

	    resultsView.setText(gameOver.getScore());
	    if (gameOver.isScoreCalculationFinished()) {
		handler.removeCallbacks(updateResultTask);
		resultsView.setText(gameOver.getScore());
		return;
	    }

	    handler.postDelayed(this, 1);
	}
    };

    @Override
    public void onStop() {
	super.onStop();
	handler.removeCallbacks(updateResultTask);
    }

    @Override
    public void onStart() {
	super.onStart();

	handler.postDelayed(updateResultTask, 1);
    }

    @Override
    public void onClick(View view) {
	if (view.getId() == R.id.button_post_score) {
	    OKUser user = OpenKit.getCurrentUser();
	    if(user != null) {
		OKScore score = new OKScore();
		score.setScoreValue(gameOver.getFinalScore()); 
		score.setOKLeaderboardID(Integer.parseInt(getResources().getString(gameOver.getLeaderboardId())));
		score.submitScore(new OKScore.ScoreRequestResponseHandler() {
		    @Override
		    public void onSuccess() {
		        Toast.makeText(getActivity(), "Score submission successful", Toast.LENGTH_SHORT).show();
		        Intent launchOKLeaderboard = new Intent(getActivity(), OKLeaderboardsActivity.class);
		        startActivityForResult(launchOKLeaderboard, MainActivity.SHOW_SPLASH_SCREEN_AFTER_LEADERBOARD);
		    }

		    @Override
		    public void onFailure(Throwable error) {
			
			StringWriter sw = new StringWriter();
			error.printStackTrace(new PrintWriter(sw));
		        Log.e("OpenKit", "Score submission failed: " + error.getMessage() + "\n" + sw.toString());
		    }});
	    } else {
		Intent launchOKLogin = new Intent(getActivity(), OKLoginActivity.class);
		startActivity(launchOKLogin);
	    }
	}
    }
}