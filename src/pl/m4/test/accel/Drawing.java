package pl.m4.test.accel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class Drawing extends View {
	private Paint paint, recPaint;
	private float x, y;
	private int width;
	private int height;
	private int left, right, bot, top;

	public Drawing(Context activity) {
		super(activity);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		recPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setShadowLayer(15f, x, y, Color.MAGENTA);
		paint.setColor(Color.RED);
		paint.setShadowLayer(8, 0, 0, Color.DKGRAY);
		setLayerType(LAYER_TYPE_SOFTWARE, paint);
		
		recPaint.setColor(Color.BLUE);
		recPaint.setStyle(Style.STROKE);
		
		WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		width = display.getWidth();  // deprecated
		height = display.getHeight();  // deprecated
		
		x = width/2-10;
		y = height/2-10;
		
		left = width /2 - 50; top = height /2 - 40; right = width / 2 + 50; bot = height /2 + 20;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRect(left, top, right, bot, recPaint);
		canvas.drawCircle(x, y, 15f, paint);
		//canvas.drawLine(100, 100, x, y, paint);
		this.invalidate();
	}
	
	@Override
	public void setX(float x) {
		this.x = x;
	}
	
	@Override
	public float getX() {
		return this.x;
	}
	
	@Override
	public void setY(float y) {
		// TODO Auto-generated method stub
		super.setY(y);
		//this.y = y;
	}
	
	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return super.getY();
	}
}
