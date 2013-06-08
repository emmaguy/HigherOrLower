package dev.emmaguy.higherorlower.menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import dev.emmaguy.higherorlower.R;

public class SplashScreenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_splash_screen);

	initialiseSinglePlayerButton();
    }
    
    private void initialiseSinglePlayerButton() {
   	Button buttonSame = (Button) findViewById(R.id.button_single_player);
   	buttonSame.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View view)
               {
        	   startActivity(new Intent(getApplicationContext(), SinglePlayerActivity.class));
               }
           });
       }
}
