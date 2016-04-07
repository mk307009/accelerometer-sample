package pl.m4.test.accel;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class Accel extends Activity implements SensorEventListener{
	private SensorManager mSensorManager;
	private Sensor mSensorAccel;
	private float[] gravity = {0,0,0};
	private float[] linear_acceleration = {0,0,0};
	private TextView x,y,z;
	Drawing mDraw;
	Surface surf;
	float [] history = new float[2];
	float s;
	long sTime, eTime, dTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accel);
		
		x = (TextView) findViewById(R.id.x);
		y = (TextView) findViewById(R.id.y);
		z = (TextView) findViewById(R.id.z);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//		mSensorAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mSensorAccel , SensorManager.SENSOR_DELAY_NORMAL);
		
		mDraw = new Drawing(this);
//		surf = new Surface(this);
		addContentView(mDraw, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mDraw.bringToFront();
		mDraw.invalidate();
		sTime = System.nanoTime();
			
//		addContentView(surf, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		surf.bringToFront();
//		surf.invalidate();		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mSensorManager.registerListener(this, mSensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
		//unregisterReceiver(receiver);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mSensorManager.unregisterListener(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mSensorManager.unregisterListener(this);
	}

	
	@Override
	public void onSensorChanged(SensorEvent event) {
		final float alpha = 0.8f;
		eTime = System.nanoTime(); 
		// Isolate the force of gravity with the low-pass filter.
		gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
		gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
		gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

		// Remove the gravity contribution with the high-pass filter.
		linear_acceleration[0] = event.values[0] - gravity[0];
		linear_acceleration[1] = event.values[1] - gravity[1];
		linear_acceleration[2] = event.values[2] - gravity[2];
		
		java.text.DecimalFormat df=new java.text.DecimalFormat(); //tworzymy obiekt DecimalFormat
		df.setMaximumFractionDigits(2); //dla df ustawiamy najwi�ksz� ilo�� miejsc po przecinku
		df.setMinimumFractionDigits(1);
		x.setText("X: "+df.format(linear_acceleration[0]*100));
		y.setText("Y: "+df.format(linear_acceleration[1]*100));
		z.setText("Z: "+df.format(linear_acceleration[2]*100));
		
		Float x = mDraw.getX();
		Float y = mDraw.getY();
		
		float xChange = history[0] - event.values[0];
        float yChange = history[1] - event.values[1];
        
        history[0] = event.values[0];
        history[1] = event.values[1];
      
//        if (yChange > 0.01){
//        	mDraw.setX(x+Math.abs(linear_acceleration[1]*100));
//        }else if (yChange < -0.01){
//        	mDraw.setX(x-Math.abs(linear_acceleration[1]*100));
//        }
        
        //z.setText("y: "+df.format(yChange)+"   ---x    "+df.format(xChange));
        //y = round(y,2).floatValue();
		mDraw.setX(x-linear_acceleration[1]*100);
		mDraw.setY(y);
		//mDraw.invalidate();
	        
//			x.setText("X: "+df.format(mAccel) );
//			y.setText("Y: "+df.format(delta) );
//			z.setText("Z: "+df.format(mAccelCurrent));
		
		dTime = sTime - eTime;
		dTime /= 1000000000;
		double s = (linear_acceleration[1] * (dTime * dTime)) / 2;
//		if (s > 0){
//			z.setText("Z: "+s+" RIGHT");
//		}else if (s < 0){
//			z.setText("Z: "+s+" LEFT");
//		}

		z.setText("Z: "+s+" w: "+df.format(((linear_acceleration[1]) * (dTime * dTime)) / 2));
	}
	
	public static BigDecimal round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
}
