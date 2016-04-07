package pl.m4.test.accel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class Surface extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder sh;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private float x, y;
	
	public Surface(Context context) {
		super(context);
		sh = getHolder();
		sh.addCallback(this);
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.FILL);
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		float width = display.getWidth();  // deprecated
		float height = display.getHeight();  // deprecated
		x = width/2-10;
		y = height/2-10;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas = sh.lockCanvas();
		canvas.drawColor(Color.BLACK);
		canvas.drawCircle(100, 200, 50, paint);
		sh.unlockCanvasAndPost(canvas);
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setColor(Color.RED);
		canvas.drawCircle(x, y, 15f, paint);
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}
