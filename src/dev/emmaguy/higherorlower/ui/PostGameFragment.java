package dev.emmaguy.higherorlower.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.emmaguy.higherorlower.R;

public class PostGameFragment extends Fragment implements View.OnClickListener {

    private OnPlayAPIAction playAPIActionListener;
    private long score;
    private int leaderboardId;
    private boolean isConnected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_post_game, null);
	v.findViewById(R.id.button_sign_in).setOnClickListener(this);
	v.findViewById(R.id.button_sign_out).setOnClickListener(this);
	v.findViewById(R.id.button_post_score).setOnClickListener(this);
	return v;
    }

    public interface OnPlayAPIAction {
	public void onSignInClicked();
	public void onSignOutClicked();
	public void onPostScore(long score, int leaderboardId);
    }

    public void setArguments(long score, int leaderboardId, boolean isConnected) {
	this.score = score;
	this.leaderboardId = leaderboardId;
	this.isConnected = isConnected;
    }
    
    @Override
    public void onStart() {
	super.onStart();

	showCorrectButtons();
    }

    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);

	try {
	    playAPIActionListener = (OnPlayAPIAction) activity;
	} catch (ClassCastException e) {
	    throw new ClassCastException(activity.toString() + " must implement OnPlayAPIAction");
	}
    }

    @Override
    public void onClick(View view) {
	if (view.getId() == R.id.button_sign_in) {
	    playAPIActionListener.onSignInClicked();
	} else if (view.getId() == R.id.button_sign_out) {
	    playAPIActionListener.onSignOutClicked();

	    isConnected = false;

	    showCorrectButtons();
	} else if (view.getId() == R.id.button_post_score) {
	    playAPIActionListener.onPostScore(score, leaderboardId);
	}
    }

    private void showCorrectButtons() {
	if (isConnected) {
	    getView().findViewById(R.id.button_sign_in).setVisibility(View.GONE);
	    getView().findViewById(R.id.button_sign_out).setVisibility(View.VISIBLE);
	    getView().findViewById(R.id.button_post_score).setVisibility(View.VISIBLE);
	} else {
	    getView().findViewById(R.id.button_sign_in).setVisibility(View.VISIBLE);
	    getView().findViewById(R.id.button_sign_out).setVisibility(View.GONE);
	    getView().findViewById(R.id.button_post_score).setVisibility(View.GONE);
	}
    }

    public void onSignInSucceeded() {
	isConnected = true;

	showCorrectButtons();
    }

    public void onSignInFailed() {
	isConnected = false;

	showCorrectButtons();
    }
}