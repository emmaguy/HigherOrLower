package dev.emmaguy.higherorlower.ui;

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
import dev.emmaguy.higherorlower.R;
import dev.emmaguy.higherorlower.card.Card;

public class HigherOrLowerFragment extends Fragment implements View.OnClickListener, OnCardChanged, OnScoreChanged {

    private HigherOrLowerGame currentGame;
    
    public void setArguments(HigherOrLowerGame currentGame){
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
	
	return v;
    }
    
    @Override
    public void onStart() {
	super.onStart();
	this.currentGame.startGame();
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
	scoreView.setText(Long.toString(score));
    }
}