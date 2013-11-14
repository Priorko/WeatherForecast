package com.example.weatherforecast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

public class MainActivity extends Activity implements OnClickListener {
	protected static final int DEFAULT_DEGREE = 50;
	Button btnNext, btnPrevious;
	ViewAnimator viewAnimator;
	SeekBar sbar;
	int curentWeater = 1;
	Toast toast = null;
	int degree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sbar = (SeekBar) findViewById(R.id.seekBar1);
		sbar.setOnSeekBarChangeListener(new ProgressHandler());
		viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator1);
		((ImageButton) MainActivity.this.findViewById(R.id.imageButton1))
				.setOnClickListener(this);
		((ImageButton) MainActivity.this.findViewById(R.id.imageButton2))
				.setOnClickListener(this);
		((Button) MainActivity.this.findViewById(R.id.button1))
				.setOnClickListener(this);

		if (savedInstanceState != null){
			if(savedInstanceState.containsKey("curentWeater"))
				curentWeater = savedInstanceState.getInt("curentWeater");
			if(savedInstanceState.containsKey("degreeVal")) {
				degree = savedInstanceState.getInt("degreeVal");
				sbar.setProgress(degree + DEFAULT_DEGREE);
			}
		}
		else 
			sbar.setProgress(DEFAULT_DEGREE);
			
		showDegree();
		viewAnimator.setDisplayedChild(curentWeater - 1);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("curentWeater", curentWeater);
		outState.putInt("degreeVal", degree);
		showToast(String.valueOf(curentWeater) + "____"
				+ String.valueOf(degree));
		super.onSaveInstanceState(outState);
	}

	public void showDegree() {
		int progress = sbar.getProgress();

		degree = progress - DEFAULT_DEGREE;

		if (progress <= DEFAULT_DEGREE)
			((TextView) MainActivity.this.findViewById(R.id.textView2))
					.setText(String.valueOf(degree) + " \u00B0Ñ");
		else
			((TextView) MainActivity.this.findViewById(R.id.textView2))
					.setText("+" + String.valueOf(degree) + " \u00B0Ñ");

	}

	private class ProgressHandler implements OnSeekBarChangeListener {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (fromUser) {
				showDegree();

			}
		}
	}

	public void showToast(String text) {
		try {
			toast.cancel();

		} catch (Exception e) {
			// TODO: handle exception
		}
		toast = Toast.makeText(getApplicationContext(), text,
				Toast.LENGTH_SHORT);
		toast.show();

	}

	private void setCurentWheather() {
		switch (viewAnimator.getCurrentView().getId()) {
		case R.id.imageView1:
			curentWeater = 1;
			break;
		case R.id.imageView2:
			curentWeater = 2;
			break;

		case R.id.imageView3:
			curentWeater = 3;
			break;

		case R.id.imageView4:
			curentWeater = 4;
			break;

		default:
			break;
		}
		// showToast(String.valueOf(curentWeater));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		final Animation inAnimLeft = AnimationUtils.loadAnimation(this,
				R.anim.slide_in_left);
		final Animation outAnimRight = AnimationUtils.loadAnimation(this,
				R.anim.slide_out_right);
		final Animation outAnimLeft = AnimationUtils.loadAnimation(this,
				R.anim.slide_out_left);
		final Animation inAnimRight = AnimationUtils.loadAnimation(this,
				R.anim.slide_in_right);
		switch (v.getId()) {
		case R.id.imageButton1:

			viewAnimator.setInAnimation(inAnimLeft);
			viewAnimator.setOutAnimation(outAnimRight);
			viewAnimator.showPrevious();
			setCurentWheather();
			break;
		case R.id.imageButton2:

			viewAnimator.setInAnimation(inAnimRight);
			viewAnimator.setOutAnimation(outAnimLeft);
			viewAnimator.showNext();
			setCurentWheather();
			break;
		case R.id.button1:
			Intent i = new Intent(this, ResultActivity.class);
			int[] data = { degree, curentWeater };
			i.putExtra("data", data);
			startActivity(i);
			break;
		default:

			break;
		}

	}

	@Override
	protected void onStop() {
		super.onStop();
		try {
			toast.cancel();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
