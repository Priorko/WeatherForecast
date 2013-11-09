package com.example.weatherforecast;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class InsetDrawable extends Drawable {

	private Paint paint;
	  private Bitmap icon;
	  private Paint p;
	  
	  public InsetDrawable(Bitmap icon) {
	    this.icon = icon;
	    paint = new Paint();
	    p = new Paint();
	    p.setColor(Color.RED);
	  }

	  @Override
	  public void draw(Canvas canvas) {
	    double koeff = (double)canvas.getHeight() / (double)icon.getHeight();
	    int diff = 20;
	    Rect srcRect = new Rect(0, 0, icon.getWidth(), icon.getHeight());
	    Rect dstRect = new Rect(0, 0, (int)(icon.getWidth()*koeff)-diff, (int)(icon.getHeight()*koeff)-diff);
	    canvas.drawBitmap(icon, srcRect, dstRect, paint);
	  }

	  @Override
	  public void setAlpha(int alpha) {
	  }

	  @Override
	  public void setColorFilter(ColorFilter cf) {
	  }

	  @Override
	  public int getOpacity() {
	    return 255;
	  }

}
