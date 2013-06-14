package dev.emmaguy.higherorlower.ui;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import dev.emmaguy.higherorlower.HigherOrLowerGame;
import dev.emmaguy.higherorlower.HigherOrLowerGame.OnCardChanged;
import dev.emmaguy.higherorlower.HigherOrLowerGame.OnScoreChanged;
import dev.emmaguy.higherorlower.R;
import dev.emmaguy.higherorlower.Score;
import dev.emmaguy.higherorlower.card.Card;

public class HigherOrLowerFragment extends Fragment implements View.OnClickListener, OnCardChanged, OnScoreChanged {

    private HigherOrLowerGame currentGame;
    private boolean isGameInitialised = false;
    private boolean isFirstCard = true;

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

	return v;
    }

    @Override
    public void onStart() {
	super.onStart();
	if (!isGameInitialised) {
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
    public void onCardChanged(final Card currentCard) {

	final ImageView lastCardView = (ImageView) getView().findViewById(R.id.imageview_last_card);
	final ImageView currentCardView = (ImageView) getView().findViewById(R.id.imageview_current_card);

	if (isFirstCard) {
	    isFirstCard = false;
	    changeCard(currentCard, lastCardView, currentCardView);
	} else {
	    final AnimationSet animationSet = new AnimationSet(false);
	    animationSet.setFillAfter(false);

	    ScaleAnimation scale = new ScaleAnimation(1.0f, 0.8f, 1.0f, 0.8f);
	    scale.setDuration(500);

	    TranslateAnimation trans = new TranslateAnimation(0, 0, TranslateAnimation.ABSOLUTE, 0.8f - currentCardView.getLeft(), 
		    					      0, 0, TranslateAnimation.RELATIVE_TO_SELF, 0.1f);
	    trans.setDuration(500);

	    animationSet.addAnimation(scale);
	    animationSet.addAnimation(trans);

	    animationSet.setAnimationListener(new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
		    changeCard(currentCard, lastCardView, currentCardView);
		}
	    });

	    currentCardView.startAnimation(animationSet);
	}
    }

    private void changeCard(final Card currentCard, final ImageView lastCardView, final ImageView currentCardView) {
	String cardNumber = currentCard.getCardNumber().toString().toLowerCase(Locale.UK);
	String cardSuit = currentCard.getSuit().toString().toLowerCase(Locale.UK);
	String name = "card_" + cardNumber + "_" + cardSuit;
	int currentCardIdentifier = getResources().getIdentifier(name, "drawable", "dev.emmaguy.higherorlower");

	Object tag = currentCardView.getTag();
	if (tag != null) {
	    lastCardView.setImageResource(Integer.parseInt(tag.toString()));
	}

	currentCardView.setImageResource(currentCardIdentifier);
	currentCardView.setTag(currentCardIdentifier);
    }

    @Override
    public void onScoreChanged(Score score) {
	View view = getView();
	if (view == null) {
	    return;
	}

	TextView scoreView = (TextView) view.findViewById(R.id.textview_score);

	if (scoreView != null)
	    scoreView.setText(score.getScore());
    }
}