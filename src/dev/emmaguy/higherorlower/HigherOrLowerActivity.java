package dev.emmaguy.higherorlower;

import dev.emmaguy.higherorlower.HigherOrLowerGame.OnCardChanged;
import dev.emmaguy.higherorlower.HigherOrLowerGame.OnGameOver;
import dev.emmaguy.higherorlower.HigherOrLowerGame.OnScoreChanged;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import dev.emmaguy.higherorlower.cards.Card;
import dev.emmaguy.higherorlower.cards.Deck;
import dev.emmaguy.higherorlower.cards.FullDeckBuilder;
import dev.emmaguy.higherorlower.menus.SplashScreenActivity;

public class HigherOrLowerActivity extends Activity implements OnCardChanged, OnScoreChanged, OnGameOver {

    private HigherOrLowerGame currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_higher_or_lower);

	initialiseHigherButton();
	initialiseSameButton();
	initialiseLowerButton();

	currentGame = new HigherOrLowerHighscoreGame(new Deck(new FullDeckBuilder().build()), this, this, this);
	currentGame.startGame();
    }

    @Override
    public void onGameOver() {
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	alertDialogBuilder.setTitle("Game Over!");
	alertDialogBuilder.setMessage("Again?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int id) {

			startActivity(new Intent(getApplicationContext(), HigherOrLowerActivity.class));
		    }
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int id) {
			startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
		    }
		});
	
	alertDialogBuilder.create().show();
    }

    @Override
    public void onCardChanged(Card currentCard) {
	ImageView cardView = (ImageView) findViewById(R.id.imageview_current_card);
	String cardNumber = "card_" + currentCard.getCardNumber().toString().toLowerCase(Locale.UK) + "_"
		+ currentCard.getSuit().toString().toLowerCase(Locale.UK);
	cardView.setImageResource(getResources().getIdentifier(cardNumber, "drawable", "dev.emmaguy.higherorlower"));
    }

    @Override
    public void onScoreChanged(int score) {
	TextView scoreView = (TextView) findViewById(R.id.textview_score);
	scoreView.setText(Integer.toString(score));
    }

    private void initialiseHigherButton() {
	Button buttonHigher = (Button) findViewById(R.id.button_higher);
	buttonHigher.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View view) {
		currentGame.higherGuessed();
	    }
	});
    }

    private void initialiseSameButton() {
	Button buttonSame = (Button) findViewById(R.id.button_same);
	buttonSame.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View view) {
		currentGame.sameGuessed();
	    }
	});
    }

    private void initialiseLowerButton() {
	Button buttonLower = (Button) findViewById(R.id.button_lower);
	buttonLower.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View view) {
		currentGame.lowerGuessed();
	    }
	});
    }
}
