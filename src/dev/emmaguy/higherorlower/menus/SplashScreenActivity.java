package dev.emmaguy.higherorlower.menus;

import com.google.example.games.basegameutils.BaseGameActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import dev.emmaguy.higherorlower.R;

public class SplashScreenActivity extends BaseGameActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_splash_screen);
	findViewById(R.id.sign_in_button).setOnClickListener(this);
	findViewById(R.id.sign_out_button).setOnClickListener(this);

	initialiseSinglePlayerButton();
    }

    private void initialiseSinglePlayerButton() {
	Button buttonSame = (Button) findViewById(R.id.button_single_player);
	buttonSame.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View view) {
		startActivity(new Intent(getApplicationContext(), SinglePlayerActivity.class));
	    }
	});
    }

    @Override
    public void onSignInSucceeded() {
	findViewById(R.id.sign_in_button).setVisibility(View.GONE);
	findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
	if (view.getId() == R.id.sign_in_button) {
	    beginUserInitiatedSignIn();
	} else if (view.getId() == R.id.sign_out_button) {
	    signOut();

	    findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
	    findViewById(R.id.sign_out_button).setVisibility(View.GONE);
	}
    }

    @Override
    public void onSignInFailed() {
	findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
	findViewById(R.id.sign_out_button).setVisibility(View.GONE);
    }

}
