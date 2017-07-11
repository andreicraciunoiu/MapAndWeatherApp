package com.example.android.mymapapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

// exemplu de URL folosit pentru request http://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=b34a682cd1363936187c0f22ec1d1539
public class RequestResult extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView noInternetConnection;
    private TextView temperatureTextView;
    private TextView humidityTextView;

    private static final String API_REQUEST_URL = "http://samples.openweathermap.org/data/2.5/weather?";
    private static final String API_KEY = "b34a682cd1363936187c0f22ec1d1539";
    private static final String API_ID = "appid=";
    private static final String AND = "&";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Result");
        setContentView(R.layout.location_weather_sample);
        String latitude = getIntent().getStringExtra("lat");
        String longitude = getIntent().getStringExtra("lon");
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        noInternetConnection = (TextView) findViewById(R.id.no_internet_connection);
        temperatureTextView = (TextView) findViewById(R.id.temperature_text);
        humidityTextView = (TextView) findViewById(R.id.humidity_text);
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        String weatherUrl = API_REQUEST_URL + "lat" + latitude + AND + "lon" + longitude + AND + API_ID + API_KEY;

        RequestAsyncTask task = new RequestAsyncTask();
        if (isConnected) {
            task.execute(weatherUrl);
        } else {
            progressBar.setVisibility(View.GONE);
            noInternetConnection.setText(getString(R.string.no_internet_connection));
        }
    }

    private class RequestAsyncTask extends AsyncTask<String, Void, LocationWeather> {

        @Override
        protected LocationWeather doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            return Utils.fetchWeather(urls[0]);
        }

        protected void onPostExecute(LocationWeather locationWeather) {

            progressBar.setVisibility(View.GONE);
            if (locationWeather != null) {
                temperatureTextView.setText(getString(R.string.temperature) + String.valueOf(locationWeather.getTemperature()));
                humidityTextView.setText(getString(R.string.humidity) + String.valueOf(locationWeather.getHumidity()));
            } else {
                noInternetConnection.setText(getString(R.string.no_data_found));
            }
        }
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(RequestResult.this, MapsActivity.class));
    }
}