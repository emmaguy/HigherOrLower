package dev.emmaguy.higherorlower.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundedCornersImageView extends ImageView {

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
    protected void onDraw(Canvas canvas) {
	BitmapDrawable drawable = (BitmapDrawable) getDrawable();

	if (drawable == null) {
	    return;
	}

	if (getWidth() == 0 || getHeight() == 0) {
	    return;
	}

	Bitmap fullSizeBitmap = drawable.getBitmap();

	int scaledWidth = getMeasuredWidth();
	int scaledHeight = getMeasuredHeight();

	Bitmap scaledBitmap;
	if (scaledWidth == fullSizeBitmap.getWidth() && scaledHeight == fullSizeBitmap.getHeight()) {
	    scaledBitmap = fullSizeBitmap;
	} else {
	    scaledBitmap = Bitmap.createScaledBitmap(fullSizeBitmap, scaledWidth, scaledHeight, true);
	}

	Bitmap roundBitmap = getRoundedCornerBitmap(getContext(), scaledBitmap, 5, scaledWidth, scaledHeight);
	canvas.drawBitmap(roundBitmap, 0, 0, null);
    }

    public Bitmap getRoundedCornerBitmap(Context context, Bitmap bitmap, int roundPixels, int width, int height) {

	final float scaledRoundPixels = roundPixels * context.getResources().getDisplayMetrics().density;
	Bitmap roundedBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
	Canvas canvas = new Canvas(roundedBitmap);
	Paint paint = new Paint();
	paint.setAntiAlias(true);
	paint.setShader(new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP));
	canvas.drawRoundRect((new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight())), scaledRoundPixels, scaledRoundPixels, paint);
	return roundedBitmap;
    }
}