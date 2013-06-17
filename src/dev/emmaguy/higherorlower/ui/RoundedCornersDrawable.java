package dev.emmaguy.higherorlower.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public class RoundedCornersDrawable extends Drawable {
    private final float cornerRadius;
    private final RectF rect;
    private final Paint paint;

    RoundedCornersDrawable(Bitmap bitmap, float cornerRadius) {
	this.cornerRadius = cornerRadius;

	rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
	paint = new Paint();
	paint.setAntiAlias(true);
	paint.setShader(new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP));
    }

    @Override
    public void draw(Canvas canvas) {
	canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);
    }

    @Override
    public int getOpacity() {
	return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
	paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
	paint.setColorFilter(cf);
    }
}
