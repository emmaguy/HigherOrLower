package dev.emmaguy.higherorlower.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundedCornersImageView extends ImageView {

    private static final float CORNER_RADIUS_PX = 9.5f;

    public RoundedCornersImageView(Context context) {
	super(context);
    }

    public RoundedCornersImageView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    public RoundedCornersImageView(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
    }
    
//    @Override
//    public void setImageResource(int resourceId) {
//	updateCard(Integer.valueOf(resourceId).toString());
//    }
//    
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    @SuppressWarnings("deprecation")
//    private void updateCard(String resourceId) {
//	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), Integer.parseInt(resourceId));
//
//	int measuredWidth = getMeasuredWidth();
//	int measuredHeight = getMeasuredHeight();
//
//	if(measuredHeight <= 0 || measuredWidth <= 0)
//	    return;
//
//	RoundedCornersDrawable roundedCornersDrawable = 
//		new RoundedCornersDrawable(Bitmap.createScaledBitmap(bitmap, measuredWidth, measuredHeight, true), CORNER_RADIUS_PX);
//
//	if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//	    setBackgroundDrawable(roundedCornersDrawable);
//	} else {
//	    setBackground(roundedCornersDrawable);
//	}
//    }
}
