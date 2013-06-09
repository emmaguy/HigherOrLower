package dev.emmaguy.higherorlower.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.emmaguy.higherorlower.R;

public class SinglePlayerFragment extends Fragment implements View.OnClickListener {
    
    private OnHighscoreModeButtonClicked highscoreButtonClickedListener;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_single_player, null);
	v.findViewById(R.id.button_highscore_mode).setOnClickListener(this);
	return v;
    }
    
    public interface OnHighscoreModeButtonClicked {
	public void onHighscoreModeButtonClicked();
    }
    
    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);

	try {
	    highscoreButtonClickedListener = (OnHighscoreModeButtonClicked) activity;
	} catch (ClassCastException e) {
	    throw new ClassCastException(activity.toString() + " must implement OnHighscoreModeButtonClicked");
	}
    }

    @Override
    public void onClick(View view) {
	if (view.getId() == R.id.button_highscore_mode) {
	    highscoreButtonClickedListener.onHighscoreModeButtonClicked();
	}
    }
}