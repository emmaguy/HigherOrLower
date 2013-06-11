package dev.emmaguy.higherorlower.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dev.emmaguy.higherorlower.HigherOrLowerGame;
import dev.emmaguy.higherorlower.HigherOrLowerGame.OnCardChanged;
import dev.emmaguy.higherorlower.HigherOrLowerGame.OnScoreChanged;
import dev.emmaguy.higherorlower.HigherOrLowerGame.OnTimeRemainingChanged;
import dev.emmaguy.higherorlower.R;
import dev.emmaguy.higherorlower.card.Card;

public class HigherOrLowerFragment extends Fragment implements View.OnClickListener, OnCardChanged, OnScoreChanged,
	OnTimeRemainingChanged {

    private HigherOrLowerGame currentGame;
    private boolean isGameInitialised = false;
    
    public void setArguments(HigherOrLowerGame currentGame) {
	this.currentGame = currentGame;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_higher_or_lower, null);

	v.findViewById(R.id.button_higher).setOnClickListener(this);
	v.findViewById(R.id.button_same).setOnClickListener(this);
	v.findViewById(R.id.button_lower).setOnClickListener(this);

	this.currentGame.setOnCardChangedListener(this);
	this.currentGame.setOnScoreChangedListener(this);
	this.currentGame.setOnTimeRemainingChangedListener(this);
	
	return v;
    }

    @Override
    public void onStart() {
	super.onStart();
	if(!isGameInitialised) {
	    this.currentGame.startGame();
	    isGameInitialised = true;
	}
    }

    @Override
    public void onResume() {
	this.currentGame.resumeGame();
	super.onResume();
    }
    
    @Override
    public void onStop() {
	super.onStop();
	this.currentGame.stopGame();
    }
    
    @Override
    public void onClick(View view) {
	if (view.getId() == R.id.button_higher) {
	    this.currentGame.higherGuessed();
	} else if (view.getId() == R.id.button_same) {
	    this.currentGame.sameGuessed();
	} else if (view.getId() == R.id.button_lower) {
	    this.currentGame.lowerGuessed();
	}
    }
    
    @Override
    public void onCardChanged(Card currentCard) {
	ImageView cardView = (ImageView) getView().findViewById(R.id.imageview_current_card);

	String cardNumber = currentCard.getCardNumber().toString().toLowerCase(Locale.UK);
	String cardSuit = currentCard.getSuit().toString().toLowerCase(Locale.UK);
	String name = "card_" + cardNumber + "_" + cardSuit;

	cardView.setImageResource(getResources().getIdentifier(name, "drawable", "dev.emmaguy.higherorlower"));
    }

    @Override
    public void onScoreChanged(long score) {
	TextView scoreView = (TextView) getView().findViewById(R.id.textview_score);
	
	if(scoreView != null)
	    scoreView.setText(Long.toString(score));
    }

    @Override
    public void onTimeRemainingChanged(long millisecondsRemaining) {
	View view = getView();
	if(view == null){
	    return;
	}
	
	TextView timeView = (TextView) view.findViewById(R.id.textview_time);

	if (timeView != null) {
	    Date timeRemaining = new Date(millisecondsRemaining);
	    DateFormat formatter = new SimpleDateFormat("mm:ss:SSS", Locale.UK);
	    timeView.setText(formatter.format(timeRemaining));
	}
    }
}