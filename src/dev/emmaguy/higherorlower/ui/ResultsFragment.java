package dev.emmaguy.higherorlower.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.emmaguy.higherorlower.R;

public class ResultsFragment extends Fragment implements View.OnClickListener {

    private final Handler handler = new Handler();
    // private OnLeaderboardAPIAction leaderboardAPIActionListener;

    private float total = 0;
    private long millisecondsRemaining;
    private int score;
    private int leaderboardId;

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

    public void setArguments(int score, long millisecondsRemaining, int leaderboardId) {
	this.score = score;
	this.millisecondsRemaining = millisecondsRemaining;
	this.leaderboardId = leaderboardId;
    }

    private Runnable updateResultTask = new Runnable() {
	public void run() {
	    final TextView resultsView = (TextView) getView().findViewById(R.id.textview_results);

	    if (millisecondsRemaining <= 0) {
		millisecondsRemaining = 0;
		handler.removeCallbacks(updateResultTask);
		return;
	    }
	    millisecondsRemaining -= 500;

	    Date timeRemaining = new Date(millisecondsRemaining);
	    DateFormat formatter = new SimpleDateFormat("mm:ss:SSS", Locale.UK);
	    StringBuilder results = new StringBuilder();
	    results.append("Score: " + score + "\n");
	    results.append("Time: " + formatter.format(timeRemaining) + "\n");
	    results.append("\n");
	    results.append("Total: " + String.format("%.2f", total));
	    resultsView.setText(results.toString());

	    total += 0.03;

	    handler.postDelayed(this, 1);
	}
    };

    @Override
    public void onDetach(){
	super.onDetach();
	
	handler.removeCallbacks(updateResultTask);	    
    }
    
    @Override
    public void onStart() {
	super.onStart();

	score = 12;
	millisecondsRemaining = 50000;

	total = score;
	handler.postDelayed(updateResultTask, 10);
    }

    @Override
    public void onClick(View view) {
	if (view.getId() == R.id.button_post_score) {
	    // leaderboardAPIActionListener.onPostScore(total, leaderboardId);
	}
    }
}