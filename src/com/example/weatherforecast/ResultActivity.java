package com.example.weatherforecast;

import java.util.Random;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ResultActivity extends MainActivity {

	ImageButton rb1;
	float tweather;
	float rand = ((Random) new Random()).nextFloat();
	double coef;
	String str;
	int degree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		rb1 = (ImageButton) ResultActivity.this.findViewById(R.id.rb1);
		rb1.setOnClickListener(this);
		Bundle extras = getIntent().getExtras();
		int[] data = extras.getIntArray("data");
		degree = data[0];
		switch (data[1]) {
		case 1:
			coef = 0.9;
			break;
		case 2:
			coef = 0.8;
			break;
		case 3:
		case 4:
			coef = 0.7;
			break;
		}
		generateWeater();
		displayResults();

	}

	void generateWeater() {
		str = getResources().getString(R.string.tommorov_text) + " ";
		
		if (degree > 0)
			str += "+";
		
		degree = (int) (degree * coef);
		tweather = (float) (rand * coef);
		str += String.valueOf(degree);
		str += "\u00B0Ñ";
	}

	void displayResults() {
		if (tweather > 0.5) {
			rb1.setImageResource(R.drawable.sun);
		} else {
			if (0.35 < tweather) {
				rb1.setImageResource(R.drawable.cloud);
			} else {
				if (0.1 < tweather) {
					rb1.setImageResource(R.drawable.rain);
				} else {
					rb1.setImageResource(R.drawable.snow);
				}
			}
		}
		showToast("random*weater - " + String.valueOf(tweather));
		degree = (int) (degree * rand);
		((TextView) ResultActivity.this.findViewById(R.id.rText1)).setText(str);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.rb1)
			finish();
	}

}
