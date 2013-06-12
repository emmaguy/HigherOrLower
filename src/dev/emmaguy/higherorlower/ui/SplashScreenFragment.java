package dev.emmaguy.higherorlower.ui;

import io.openkit.OKUser;
import io.openkit.OpenKit;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import dev.emmaguy.higherorlower.OnLeaderboardAPIAction;
import dev.emmaguy.higherorlower.R;

public class SplashScreenFragment extends Fragment implements View.OnClickListener {
    
    private OnPlayersChosen singlePlayerButtonClickedListener;
    private OnLeaderboardAPIAction leaderboardActionListener;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_splash_screen, null);
	v.findViewById(R.id.button_single_player).setOnClickListener(this);
	v.findViewById(R.id.button_view_leaderboards).setOnClickListener(this);
	
	OKUser currentUser = OpenKit.getCurrentUser();
	TextView textViewSignedInStatus = (TextView)v.findViewById(R.id.textview_signed_in_username);
	Button buttonSignInOrOut = (Button) v.findViewById(R.id.button_sign_in_or_out);
	buttonSignInOrOut.setOnClickListener(this);
	
	if(currentUser == null) {
	    buttonSignInOrOut.setText("Sign In");
	    textViewSignedInStatus.setText("");
	} else { 
	    buttonSignInOrOut.setText("Sign Out");
	    textViewSignedInStatus.setText("Signed in as " + currentUser.getUserNick());
	}
	
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
	    leaderboardActionListener = (OnLeaderboardAPIAction) activity;
	} catch (ClassCastException e) {
	    throw new ClassCastException(activity.toString() + " must implement OnPlayersChosen and OnLeaderboardAPIAction");
	}
    }

    @Override
    public void onClick(View view) {
	if(view.getId() == R.id.button_single_player){
	    singlePlayerButtonClickedListener.onSinglePlayerButtonClicked();
	} else if(view.getId() == R.id.button_sign_in_or_out) {
	    if(OpenKit.getCurrentUser() == null){
		leaderboardActionListener.onSignInClicked();
	    } else { 
		leaderboardActionListener.onSignOutClicked();
	    }
	} else if(view.getId() == R.id.button_view_leaderboards) {
	    leaderboardActionListener.viewLeaderboards();
	}
    }
}