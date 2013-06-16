package dev.emmaguy.higherorlower.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
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
    public void setImageBitmap(Bitmap bitmap) {
	
    	int width = getMeasuredWidth();
    	int height = getMeasuredHeight();

	super.setImageBitmap(getRoundedCornerBitmap(getContext(), bitmap, 5, width, height));
    }
     
    private Bitmap getRoundedCornerBitmap(Context context, Bitmap bitmap, int roundPixels, int width, int height) {

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