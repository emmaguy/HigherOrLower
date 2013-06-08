package dev.emmaguy.higherorlower;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import dev.emmaguy.higherorlower.HigherOrLowerHighscoreGame.OnCardChanged;
import dev.emmaguy.higherorlower.HigherOrLowerHighscoreGame.OnGameOver;
import dev.emmaguy.higherorlower.HigherOrLowerHighscoreGame.OnScoreChanged;
import dev.emmaguy.higherorlower.cards.Card;
import dev.emmaguy.higherorlower.cards.Deck;
import dev.emmaguy.higherorlower.cards.FullDeckBuilder;

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
	
    }

    @Override
    public void onCardChanged(Card currentCard) {
	ImageView cardView = (ImageView)findViewById(R.id.imageview_current_card);
	String cardNumber = "card_" + currentCard.getCardNumber().toString().toLowerCase(Locale.UK) + "_" + currentCard.getSuit().toString().toLowerCase(Locale.UK);
	cardView.setImageResource(getResources().getIdentifier(cardNumber, "drawable", "dev.emmaguy.higherorlower"));
    }

    @Override
    public void onScoreChanged(int score) {
	TextView scoreView = (TextView)findViewById(R.id.textview_score);
	scoreView.setText(Integer.toString(score));
    }

    private void initialiseHigherButton() {
   	Button buttonHigher = (Button) findViewById(R.id.button_higher);
   	buttonHigher.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View view)
               {
                   currentGame.higherGuessed();
               }
           });
       }
    
    private void initialiseSameButton() {
   	Button buttonSame = (Button) findViewById(R.id.button_same);
   	buttonSame.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View view)
               {
                   currentGame.sameGuessed();
               }
           });
       }
    
    private void initialiseLowerButton() {
	Button buttonLower = (Button) findViewById(R.id.button_lower);
	buttonLower.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                currentGame.lowerGuessed();
            }
        });
    }
}
