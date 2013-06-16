package dev.emmaguy.higherorlower.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.emmaguy.higherorlower.R;

public class SinglePlayerFragment extends Fragment implements View.OnClickListener {
    
    private OnSinglePlayerModeChosen buttonClickedListener;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_single_player, null);
	v.findViewById(R.id.button_highscore_mode).setOnClickListener(this);
	v.findViewById(R.id.button_sudden_death).setOnClickListener(this);
	v.findViewById(R.id.button_back_to_splash_screen).setOnClickListener(this);
	return v;
    }
    
    public interface OnSinglePlayerModeChosen {
	public void onHighscoreModeButtonClicked();
	public void onSuddenDeathModeButtonClicked();
    }
    
    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);

	try {
	    buttonClickedListener = (OnSinglePlayerModeChosen) activity;
	} catch (ClassCastException e) {
	    throw new ClassCastException(activity.toString() + " must implement OnSinglePlayerModeChosen");
	}
    }

    @Override
    public void onClick(View view) {
	if (view.getId() == R.id.button_highscore_mode) {
	    buttonClickedListener.onHighscoreModeButtonClicked();
	} else if (view.getId() == R.id.button_sudden_death) {
	    buttonClickedListener.onSuddenDeathModeButtonClicked();
	} else if(view.getId() == R.id.button_back_to_splash_screen) {
	    getActivity().onBackPressed();
	}
    }
}