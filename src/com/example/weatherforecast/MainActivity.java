package com.example.weatherforecast;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, WeatherChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b = (Button) findViewById(R.id.b2);
		b.setVisibility(View.GONE);
		Button button1 = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		Button button3 = (Button) findViewById(R.id.button3);
		Button button4 = (Button) findViewById(R.id.button4);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
	}
	
	 @Override
	  protected void onResume() {
	    WeatherView weather = (WeatherView) findViewById(R.id.weather_view);
	    weather.requestWeather(this);
	    super.onResume();
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		float tweather = 0;
		switch (v.getId()) {
		case R.id.button1:
			tweather = (float) 0.9;
			break;
		case R.id.button2:
			tweather = (float) 0.8;
			break;
		case R.id.button3:
		case R.id.button4:
			tweather = (float) 0.7;
			break;
// ???
//		case R.id.b2:
//			Toast.makeText(this, "b pushed ", Toast.LENGTH_SHORT).show();
//
//			// Intent i = new Intent(this, MainActivity.class);
//			Intent i = getBaseContext().getPackageManager()
//					.getLaunchIntentForPackage(
//							getBaseContext().getPackageName());
//			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(i);
//			break;
//		default:
//
//			break;
		}
		int back;

		Random randomGenerator = new Random();

		float rand = randomGenerator.nextFloat();

		float tom = rand * tweather;

		String str = String.valueOf(rand);
		str = str + "___" + String.valueOf(tweather) + "___"
				+ String.valueOf(tom);
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

		if (tom > 0.5) {
			back = R.drawable.sun;
		} else {
			if (0.35 < tom) {
				back = R.drawable.cloud;
			} else {
				if (0.1 < tom) {
					back = R.drawable.rain;
				} else {
					back = R.drawable.snow;
				}
			}
		}

		finalAnim(back);
	}

	public void finalAnim(int back) {
		TextView text1 = (TextView) findViewById(R.id.textView1);
		Button b = (Button) findViewById(R.id.b2);
		text1.setText(R.string.tommorov_text);
		b.setBackgroundResource(back);
		b.setVisibility(View.VISIBLE);
		LinearLayout ll1 = (LinearLayout) findViewById(R.id.linearlayout1);
		LinearLayout ll2 = (LinearLayout) findViewById(R.id.linearlayout2);
		ll1.setVisibility(View.GONE);
		ll2.setVisibility(View.GONE);
	}

	@Override
	public void weatherChanged() {
		findViewById(R.id.weather_progress).setVisibility(View.GONE);
	}
}
