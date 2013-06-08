package dev.emmaguy.higherorlower.menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import dev.emmaguy.higherorlower.HigherOrLowerActivity;
import dev.emmaguy.higherorlower.R;

public class SinglePlayerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_single_player);

	initialiseHighscoreModeButton();
    }
    
    private void initialiseHighscoreModeButton() {
   	Button buttonSame = (Button) findViewById(R.id.button_highscore_mode);
   	buttonSame.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View view)
               {
        	   startActivity(new Intent(getApplicationContext(), HigherOrLowerActivity.class));
               }
           });
       }
}
