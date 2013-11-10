package com.example.weatherforecast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

public class WeatherView extends TextView {
  
  private String city;
  private String temp_c;
  private String temp_f;
  private WeatherChangeListener mListener;
  private SharedPreferences sp;
  private String mScale;

  public WeatherView(Context context, AttributeSet attrs) {
    super(context, attrs);
    float textSize =  getResources().getDimensionPixelSize(R.dimen.weather_text_size);
    this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    
    this.setIncludeFontPadding(false);
    sp = PreferenceManager.getDefaultSharedPreferences(context);
    mScale = sp.getString("weather_units", "C°");
  }

  @SuppressWarnings("deprecation")
  public void setWeather(String city, String temp, Bitmap icon) {
    String temperature = temp + mScale + "\n";
    Spannable text = new SpannableString(temperature + city);
    text.setSpan(new StyleSpan(Typeface.BOLD), 0, temp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    text.setSpan(new RelativeSizeSpan(0.3f), temperature.length(), text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    this.setLineSpacing(0f, 0.83f);
    this.setText(text);
    this.setTextColor(getResources().getColor(R.color.white));
    this.setShadowLayer(1.5f, 1, 1, getResources().getColor(R.color.black));

    this.setBackgroundDrawable(new InsetDrawable(icon));
  }
  
  Location loc = null;
  private Location getCurrentLocation() {
    LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
    Criteria crit = new Criteria();
    
    crit.setAccuracy(Criteria.ACCURACY_FINE);
    String provider = lm.getBestProvider(crit, true);
    if(provider != null){
    lm.requestLocationUpdates(provider, 0, 0,  new LocationListener() {
      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {}
      @Override
      public void onProviderEnabled(String provider) {}
      @Override
      public void onProviderDisabled(String provider) {}
      @Override
      public void onLocationChanged(Location location) {
    	  loc = location;
      }
    });
    loc = lm.getLastKnownLocation(provider);
    }
    return loc;
  }

  public void requestWeather(WeatherChangeListener listener){
    mListener = listener;
    mScale = sp.getString("weather_units", "C°");
    String url = null;
    Location location = getCurrentLocation();
    
    if(location!= null && !sp.getBoolean("use_geolocation", false)){
      url = "http://api.wunderground.com/api/f839a3245d4ab7fe/conditions/q/"
        + String.valueOf(location.getLatitude()) + "," 
        + String.valueOf(location.getLongitude()) + ".json";
    }
    else {
      url = "http://api.wunderground.com/api/f839a3245d4ab7fe/conditions/q/autoip.json";
    }
      
    new GetWeather().execute(url);
  }

  private void readWeather(String json){
    JSONObject curObservationData;
    JSONObject displayLocationData;
    String icon_url;
    try {
      curObservationData = new JSONObject(json).getJSONObject("current_observation");
      displayLocationData = curObservationData.getJSONObject("display_location");
      city = displayLocationData.getString("city");
      temp_c = roundTemp(curObservationData.getString("temp_c"));
      temp_f = roundTemp(curObservationData.getString("temp_f"));
      icon_url = curObservationData.getString("icon_url");
      new GetBitmapFromURL(getContext()).execute(icon_url);
    } catch (JSONException e) {
    }
  }
  
  private String roundTemp(String str){
    if(str.contains("."))
      return str.substring(0, str.indexOf("."));
    else return str;
  }

  class GetWeather extends AsyncTask<String, String, String> {
    String responseStr = null;
    @Override
    protected String doInBackground(String... param) {
      Integer result = null;
      String requestUrl = param[0];
      Log.wtf("URL", param[0]);
      try {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(requestUrl);
        HttpResponse responseGet = client.execute(get);
        result = responseGet.getStatusLine().getStatusCode();
        HttpEntity resEntityGet = responseGet.getEntity();
        Log.wtf("result", "lol "+result);
        if (resEntityGet != null && result == 200)
          responseStr = EntityUtils.toString(resEntityGet);
        Log.wtf("responseStr", responseStr);
      } catch (Exception e) {
    	  e.printStackTrace();
      }
      return responseStr;
    }
    @Override
    protected void onPostExecute(String result) {
      super.onPostExecute(result);
      if(result != null)
        readWeather(result);
    }
  }
  
  class GetBitmapFromURL extends AsyncTask<String, String, Bitmap> {

    Context mContext;

    public GetBitmapFromURL(Context context) {
      mContext = context;
    }

    @Override
    protected Bitmap doInBackground(String... param) {
      Bitmap bmp = null;
      try {
        URL url = new URL(param[0]);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        bmp = BitmapFactory.decodeStream(input);
        input.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
      super.onPostExecute(bmp);
      if(bmp != null) {
        if(mScale.equals("C°"))
          setWeather(city, temp_c, bmp);
        else if (mScale.equals("F"))
          setWeather(city, temp_f, bmp);
      }
      if (mListener != null) {
        mListener.weatherChanged();
      }
    }
  }
}