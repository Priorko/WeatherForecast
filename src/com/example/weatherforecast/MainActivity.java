package com.example.weatherforecast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	public static final String KEY_TODAYS_WEATHER = "todays_weather";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onWeatherSet(View v) {
		int id = v.getId();
		Intent intent = new Intent(this, ResultActivity.class);
		intent.putExtra(KEY_TODAYS_WEATHER, id);
		startActivity(intent);
	}

}
