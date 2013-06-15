package dev.emmaguy.higherorlower.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
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

	final ImageView nextCardView = (ImageView) getView().findViewById(R.id.imageview_next_card);
	final ImageView lastCardView = (ImageView) getView().findViewById(R.id.imageview_last_card);
	final ImageView currentCardView = (ImageView) getView().findViewById(R.id.imageview_current_card);

	final AnimationSet animationSet = new AnimationSet(false);
	animationSet.setFillAfter(true);

	ScaleAnimation scale = new ScaleAnimation(1.0f, 0.8f, 1.0f, 0.8f);
	scale.setDuration(500);

	TranslateAnimation slideCurrentCardToRight = new TranslateAnimation(0, 0, TranslateAnimation.ABSOLUTE,
		0.8f - currentCardView.getLeft(), 0, 0, TranslateAnimation.RELATIVE_TO_SELF, 0.1f);
	slideCurrentCardToRight.setDuration(500);

	animationSet.addAnimation(scale);
	animationSet.addAnimation(slideCurrentCardToRight);

	animationSet.setAnimationListener(new AnimationListener() {
	    @Override
	    public void onAnimationStart(Animation animation) { }

	    @Override
	    public void onAnimationRepeat(Animation animation) { }

	    @Override
	    public void onAnimationEnd(Animation animation) {
		
		Object tag = nextCardView.getTag();
		if (tag != null && !isFirstCard) {
		    lastCardView.setImageResource(Integer.parseInt(tag.toString()));
		}
		
		Object currentCardTag = currentCardView.getTag();
		if(currentCardTag != null) {
		    lastCardView.setTag(currentCardTag);
		}
		
		currentCardView.setTag(tag);
		isFirstCard = false;
	    }
	});

	TranslateAnimation dealNewCard = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
	dealNewCard.setDuration(500);
	dealNewCard.setInterpolator(new DecelerateInterpolator());
	
	int currentCardIdentifier = getResources().getIdentifier(currentCard.getResourceName(), "drawable", "dev.emmaguy.higherorlower");

	nextCardView.setImageResource(currentCardIdentifier);
	nextCardView.setTag(currentCardIdentifier);
	
	Object tag = currentCardView.getTag();
	if (tag != null) {
	    currentCardView.setImageResource(Integer.parseInt(tag.toString()));
	}
	
	Object lastTag = lastCardView.getTag();
	if (lastTag != null) {
	    lastCardView.setImageResource(Integer.parseInt(lastTag.toString()));
	}
	
	nextCardView.startAnimation(dealNewCard);
	currentCardView.startAnimation(animationSet);
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