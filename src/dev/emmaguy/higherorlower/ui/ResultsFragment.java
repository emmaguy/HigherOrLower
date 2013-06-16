package dev.emmaguy.higherorlower.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import dev.emmaguy.higherorlower.GameOver;
import dev.emmaguy.higherorlower.OnLeaderboardAPIAction;
import dev.emmaguy.higherorlower.R;

public class ResultsFragment extends Fragment implements View.OnClickListener {

    private final Handler handler = new Handler();
    private OnLeaderboardAPIAction leaderboardActionListener;
    private GameOver gameOver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_results, null);
	v.findViewById(R.id.button_post_score).setOnClickListener(this);
	v.findViewById(R.id.button_back).setOnClickListener(this);
	return v;
    }

    public void setArguments(GameOver gameOver) {
	this.gameOver = gameOver;
    }

    private Runnable updateResultTask = new Runnable() {
	public void run() {
	    final TableLayout resultsTableLayout = (TableLayout) getView().findViewById(R.id.tablelayout_results);

	    updateResultsUi(resultsTableLayout);

	    if (!gameOver.isScoreBeingCalculated()) {
		handler.removeCallbacks(updateResultTask);
		updateResultsUi(resultsTableLayout);
		return;
	    }

	    handler.postDelayed(this, 1);
	}

	private void updateResultsUi(final TableLayout resultsTableLayout) {

	    resultsTableLayout.removeAllViews();
	    TableRow[] rows = gameOver.getScoreCountdownUi(getActivity().getApplicationContext());
	    for (TableRow row : rows) {
		resultsTableLayout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
			TableLayout.LayoutParams.WRAP_CONTENT));
	    }
	}
    };

    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);

	try {
	    leaderboardActionListener = (OnLeaderboardAPIAction) activity;
	} catch (ClassCastException e) {
	    throw new ClassCastException(activity.toString() + " must implement OnLeaderboardAPIAction");
	}
    }

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
	    leaderboardActionListener.onSubmitScore(gameOver.getFinalScore(),
		    Integer.parseInt(getResources().getString(gameOver.getLeaderboardId())));
	} else if (view.getId() == R.id.button_back) {
	    getActivity().onBackPressed();
	}
    }
}