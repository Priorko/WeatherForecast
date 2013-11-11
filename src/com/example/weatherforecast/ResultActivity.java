package com.example.weatherforecast;

import java.util.Random;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class ResultActivity extends MainActivity {

	/**
	 * array with integer ID of resources. 
	 */
	private static final int[] WEATHER_TYPE = {R.id.btnSun, R.id.btnCloud, 
			R.id.btnRain, R.id.btnSnow};
	
	/**
	 * Array of multipliers. Used for getting coefficient ({@link #MULTIPLIER}.id == {@link #WEATHER_TYPE}.id) 
	 */
	private static final float[] MULTIPLIER = {0.9f, 0.8f, 0.7f, 0.7f};

	public static final String KEY_RANDOMIZED = "randomized";
	float rand;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		ImageView image = (ImageView) findViewById(R.id.ivResult);

		// Getting the id of view for yesterdays weather
		int yesterdaysWeather = 0;
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(MainActivity.KEY_TODAYS_WEATHER)) {
			yesterdaysWeather = extras.getInt(MainActivity.KEY_TODAYS_WEATHER);
		}

		for (int i = 0; i < WEATHER_TYPE.length; i++) {
			if (WEATHER_TYPE[i] == yesterdaysWeather) {
				if (savedInstanceState != null && savedInstanceState.containsKey(KEY_RANDOMIZED)) {
					rand = savedInstanceState.getFloat(KEY_RANDOMIZED);
				} else {
					rand = new Random().nextFloat();
					rand *= MULTIPLIER[i];
				}
				image.setImageResource(rand > 0.5 ? R.drawable.sun 
						: rand > 0.35 ? R.drawable.cloud 
						: rand > 0.1 ? R.drawable.rain : R.drawable.snow);
				Toast.makeText(this, "random*weater - " + rand, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putFloat(KEY_RANDOMIZED, rand);
	}

}
