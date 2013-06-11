package dev.emmaguy.higherorlower.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.emmaguy.higherorlower.R;

public class SplashScreenFragment extends Fragment implements View.OnClickListener {
    
    private OnPlayersChosen singlePlayerButtonClickedListener;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_splash_screen, null);
	v.findViewById(R.id.button_single_player).setOnClickListener(this);
	return v;
    }
    
    public interface OnPlayersChosen {
	public void onSinglePlayerButtonClicked();
    }
    
    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);

	try {
	    singlePlayerButtonClickedListener = (OnPlayersChosen) activity;
	} catch (ClassCastException e) {
	    throw new ClassCastException(activity.toString() + " must implement OnPlayersChosen");
	}
    }

    @Override
    public void onClick(View view) {
	if(view.getId() == R.id.button_single_player){
	    singlePlayerButtonClickedListener.onSinglePlayerButtonClicked();
	}
    }
}