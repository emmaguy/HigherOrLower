package dev.emmaguy.higherorlower.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundedCornersImageView extends ImageView {

    private static final float CORNER_RADIUS_PX = 9.5f;
    private RoundedCornersDrawable roundedCornersDrawable;
    private int measuredWidth;
    private int measuredHeight;

    public RoundedCornersImageView(Context context) {
	super(context);
    }

    public RoundedCornersImageView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    public RoundedCornersImageView(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
    }

    @Override
    public void setImageResource(int resourceId) {
	if (measuredHeight <= 0 || measuredWidth <= 0) {
	    super.setImageResource(resourceId);
	}
	
	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), Integer.parseInt(Integer.valueOf(resourceId).toString()));

	measuredWidth = getMeasuredWidth();
	measuredHeight = getMeasuredHeight();

	if (measuredHeight <= 0 || measuredWidth <= 0) {
	    measure(0, 0);
	    measuredWidth = getMeasuredWidth();
	    measuredHeight = getMeasuredHeight();
	}

	roundedCornersDrawable = new RoundedCornersDrawable(Bitmap.createScaledBitmap(bitmap, measuredWidth, measuredHeight, true), CORNER_RADIUS_PX);
    }

    @Override
    public void onDraw(Canvas c) {
	if(roundedCornersDrawable != null)
	    roundedCornersDrawable.draw(c);
    }
}
